from flask import Flask,request,jsonify
from sqlalchemy import create_engine, Column, Integer, String, DateTime, Date
from flask_cors import CORS, cross_origin
from flask_sqlalchemy import SQLAlchemy
import sys
import configs
app = Flask(__name__)


cors = CORS(app)
# 加载配置文件
app.config.from_object(configs)
# db绑定app
db = SQLAlchemy(app)


class User(db.Model):
    __tablename__ = 'User'
    id = db.Column(db.Integer, primary_key=True)  # 设置主键, 默认自增
    name=db.Column(db.String(100))
    password=db.Column(db.String(100))
    
class History(db.Model):
    __tablename__ = 'History'
    id = db.Column(db.Integer, primary_key=True)  # 设置主键, 默认自增
    number=db.Column(db.Integer)
    formula=db.Column(db.String(100))
    user_id = db.Column(db.Integer(),db.ForeignKey('User.id'))
    user = db.relationship('User', backref=db.backref('records'))

class Loan(db.Model):
    __tablename__ = 'Loan'
    id = db.Column(db.Integer, primary_key=True)  # 设置主键, 默认自增
    inter=db.Column(db.Float)
    year=db.Column(db.Float)
    
class Deposit(db.Model):
    __tablename__ = 'Deposit'
    id = db.Column(db.Integer, primary_key=True)  # 设置主键, 默认自增
    inter=db.Column(db.Float)
    year=db.Column(db.Float)