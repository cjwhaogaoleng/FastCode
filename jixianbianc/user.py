from flask import Flask,request,jsonify
import re
import numpy as np
import math
from flask_sqlalchemy import SQLAlchemy
from model import app,db,User
from flask import Blueprint
user_api = Blueprint('user_app', __name__)

@app.route('/login',methods=['POST'])
def login():
    name = request.form.get("username")
    password = request.form.get("password")
    if not all([name,password]):
        return jsonify({'status': 400, 'message': '参数不完整', 'data': ''})
    user = User.query.filter(User.name==name,User.password==password).first()
    if user is None:
        return jsonify({'status': 404, 'message': '无此用户', 'data': ''})
    if user:
        use={
        'id': user.id,
        'username' : name,
        'password' : password,
        }
        return jsonify({'status': 200, 'message': 'success', 'data': use})
    else:
        return jsonify({'status': 400, 'message': '用户名或者密码错误', 'data': ''})
    
@app.route('/register',methods=['POST'])
def register():
    username = request.form.get('username')
    password1 = request.form.get('password1')
    password2 = request.form.get('password2')
    if not all([username,password1,password2]):
        return jsonify({'status': 400, 'message': '参数不完整', 'data': ''})
    elif password1 != password2:
        return jsonify({'status': 400, 'message': '两次密码不一致，请重新输入', 'data': ''})
    elif User.query.filter_by(name=username).all():
        return jsonify({'status': 400, 'message': '用户名已存在', 'data': ''})
    else:
        new_user = User(name=username,password=password1)
        db.session.add(new_user)
        db.session.commit()
        use={
            'id':new_user.id,
            'username' : username,
            'password' : password1,
        }
        return jsonify({'status': 201, 'message': 'success', 'data': use})