var db = require('mysql');


var mysql = db.createConnection({
    host    :'localhost',
    port : 3306,
    user : 'root',
    password : '1234',
    database:'storyboard'
});

mysql.connect(function(err) {
    if (err) {
        console.error('mysql connection error');
        console.error(err);
        throw err;
    }
});

mysql.signin = function(param, callback){

    mysql.query('SELECT user_info.password FROM user_info '
        + 'where user_info.user_id = \''+ param.body.user_id + '\''
        , function (error, result) {
            if (error) {
                console.error(error);
            } else if(result[0] == null){
                callback(null, 'can not find user id');
            }else{
                if(result[0].password == param.body.password){
                    callback(param.body.user_id, 'id and password are correct');
                }else{
                    callback(null, 'password is incorrect');
                }
            }
    });
    //return isCorrect;
}

function isUserId(user_id){
    mysql.query('SELECT user_info.user_id, user_info.password FROM user_info '
        + 'where user_info.user_id = \''+ user_id + '\''
        , function (error, result) {
            if (error) {
                console.error(error);
            } else if(result[0] == null){
                console.log('user_id == null : ' + result[0]);
            } else{
                console.log('user_id != null: ' + result[0].user_id);
            }
        });
}

function isCorrectPassword(user_password){

}

function insertData(stringOfData, data) {
    mysql.query('insert into ' + stringOfData + '_info set ?', data, throwError);
}

function throwError(error, result, fields) {
    if (error) {
        console.error(error);
    } else {
    }
}

module.exports = mysql;
