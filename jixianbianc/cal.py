from flask import Flask,request,jsonify
import re
import numpy as np
from sqlalchemy import desc 
import math
from flask_sqlalchemy import SQLAlchemy
from model import app,db,History,Deposit,Loan
from flask import Blueprint
cal_api = Blueprint('cal_app', __name__)

@app.route('/get_result',methods=['POST'])
def get_result():
    data = request.form.get("formula")
    dx = request.form.get("deg")
    fx=data
    data=data.replace("^","**")
    if dx=='yes':
        data=data.replace("atan(", "np.atan(np.pi/180*")
    else:
        data=data.replace("atan(", "np.atan(")
    if dx=='yes':
        data=data.replace("asin(", "np.asin(np.pi/180*")
    else:
        data=data.replace("asin(", "np.asin(")
    if dx=='yes':
        data=data.replace("acos(", "np.acos(np.pi/180*")
    else:
        data=data.replace("acos(", "np.acos(")
    if dx=='yes':
        data=re.sub("(?=\W?)tan(","np.tan(np.pi/180*",data)
    else:
        data=re.sub("(?=\W?)tan(","np.tan(",data)
    if dx=='yes':
        data=re.sub("(?=\W?)sin(","np.tan(np.pi/180*",data)
    else:
        data=re.sub("(?=\W?)sin(","np.tan(",data)
    if dx=='yes':
        data=re.sub("(?=\W?)cos(","np.tan(np.pi/180*",data)
    else:
        data=re.sub("(?=\W?)cos(","np.tan(",data)
    data=data.replace("π", "np.pi")
    data=data.replace("e", "np.exp(1)")
    data=re.sub("\|(\d+)\|",lambda x: "np.abs("+str(x.group(1))+")",data)
    data=data.replace("mod", "%")
    data=data.replace("log(", "np.log10(")
    data=data.replace("ln(", "np.log(")
    data=re.sub("(\d+)!",lambda x: "math.factorial("+str(x.group(1))+")",data)
    data=re.sub("√(\d+)",lambda x: "math.sqrt("+str(x.group(1))+")",data)
    data=re.sub("log(\d+)\((\d+)\)",lambda x: "np.log("+str(x.group(2))+")/np.log("+str(x.group(1))+")",data)
    f=0
    try:
        data=eval(data)
    except (ZeroDivisionError): 
        data=str('Error: 除零错误')
        f=1
    except NameError:
        data=str('Error: 请加上括号')
        f=1
    except SyntaxError:
        data=str('Error: 语法错误，请正确输入')
        f=1
    if f==0:
        if len(data>10):
            data=str('{:g}'.format(float(data)))
        new_history=History(number=data,formula=fx)
        db.session.add(new_history)
        db.session.commit()
    return jsonify({'result': data, 'message': 'success'})
        
@app.route('/read_history',methods=['POST'])
def read_history():
    idd=request.form.get('id')
    h=History.query.get(idd)
    data={
        "number":h.number,
        "formula":h.formula
    }
    return jsonify({'result': data, 'message': 'success'})

@app.route('/read_all_history',methods=['POST'])
def read_all_history():
    h=History.query.order_by(History.id)
    data=""
    for hh in h:
        data=data+str(hh.formula)+"="+str(hh.number)+","
    return jsonify({'result': data, 'message': 'success'})

@app.route('/delete_history',methods=['POST'])
def delete_history():
    idd=request.form.get('id')
    h=History.query.get(idd)
    db.session.delete(h)
    db.session.commit()
    return jsonify({'result': 'success'})

@app.route('/delete_all_history',methods=['POST'])
def delete_all_history():
    db.session.query(History).delete()
    db.session.commit()



@app.route('/loanOrDeposit',methods=['POST'])
def loanOrDeposit():
    money = request.form.get("money",type=float)
    year = request.form.get("year",type=float)
    loanOrDeposit = request.form.get("loanOrDeposit",type=int)
    ints=0
    if loanOrDeposit==0:
        dx=Deposit.query.filter(Deposit.year<=year).order_by(desc(Deposit.year)).first()
        ints=dx.inter/100
        y=math.floor(year/dx.year)
        money=money+money*(ints*y*dx.year+0.5*(year-y*dx.year))
    else:
        dx=Loan.query.filter(Loan.year<=year).order_by(desc(Deposit.year)).first()
        ints=dx.inter/100
        money=money+money*ints*year
    data={
        "interest":ints,
        "total":money
    }
    return jsonify({'message': 'success',"data":data})

@app.route('/change_loanOrDeposit',methods=['POST'])
def change_loanOrDeposit():
    ints = request.form.get("interest",type=float)
    year = request.form.get("year",type=float)
    loanOrDeposit = request.form.get("loanOrDeposit",type=int)
    if loanOrDeposit==0:
        dx=Deposit.query.filter(Deposit.year==year)
        if dx is None:
            return jsonify({'message': '年限错误'})
        dx.inter=ints
        db.session.commit()
        return jsonify({'message': 'success'})
    else:
        dx=Loan.query.filter(Loan.year==year)
        if dx is None:
            return jsonify({'message': '年限错误'})
        dx.inter=ints
        db.session.commit()
        return jsonify({'message': 'success'})
    
@app.route('/test',methods=['POST'])
def test():
    a=[0.25,0.5,1,2,3,5]
    b=[2.85,3.05,3.25,4.15,4.75,5.25]
    for i in range(0,len(a)):
        new_x = Deposit(year=a[i],inter=b[i])
        db.session.add(new_x)
        db.session.commit()
    a=[0,0.50001,1.0001,3.0001,5.0001]
    b=[5.85,6.31,6.40,6.65,6.80]
    for i in range(0,len(a)):
        new_x = Loan(year=a[i],inter=b[i])
        db.session.add(new_x)
        db.session.commit()
    return jsonify({'message': 'success'})