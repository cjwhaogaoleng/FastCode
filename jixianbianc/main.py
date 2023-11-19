from model import app,db
from cal import cal_api
from user import user_api
app.register_blueprint(cal_api)
app.register_blueprint(user_api)
if __name__ == '__main__':
    db.create_all()
    app.run(host="0.0.0.0", port=443, debug=True)
