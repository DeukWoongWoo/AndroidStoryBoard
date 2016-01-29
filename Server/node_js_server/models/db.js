var db = require('mysql');

var mysql = db.createConnection({
    host: 'localhost',
    port: 3306,
    user: 'root',
    password: '1234',
    database: 'storyboard'
});

mysql.connect(function (err) {
    if (err) {
        console.error('mysql connection error');
        console.error(err);
        throw err;
    }
});

mysql.getAppList = function (param, callback) {
    mysql.query('SELECT app_info.app_name FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND app_info.user_id = \'' + param.session.user_id + '\''
        , function (err, result, fields) {
            callback(err, result);
        });
}

mysql.getAppNumList = function (param, callback) {
    mysql.query('SELECT app_info.app_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND app_info.user_id = \'' + param.session.user_id + '\''
        , function (err, result, fields) {
            callback(err, result);
        });
}

mysql.getUserId = function (param, callback) {
    mysql.query('SELECT user_info.user_id FROM user_info '
        + 'where user_info.user_id = \'' + param.body.user_id + '\''
        , function (err, result) {
            if (result[0])
                callback(null, result[0].user_id);
            else callback('user id is not exist');
        });
}

mysql.getUserInfo = function (param, callback) {
    mysql.query('SELECT * FROM user_info '
        + 'where user_info.user_id = \'' + param.body.user_id + '\''
        , function (err, result) {
            if (!err && result[0])
                callback(null, result[0]);
            else callback('user id is not exist');
        });
}

mysql.confirmPassword = function (param, callback) {
    mysql.query('SELECT user_info.password FROM user_info '
        + 'where user_info.user_id = \'' + param.body.user_id + '\''
        , function (err, result) {
            callback(err, param.body.password == result[0].password);
        });
}

mysql.updateUser = function (param) {
    var user = {
        user_id: param.body.user_id,
        name: param.body.name,
        password: param.body.password,
        email: param.body.email
    };

    updateData('user', user);
}

mysql.addUser = function (param) {
    var user = {
        user_id: param.body.user_id,
        name: param.body.name,
        password: param.body.password,
        email: param.body.email
    };

    insertData('user', user);
}

mysql.addApp = function (param) {
    var app = {
        app_name: param.body.app_name,
        total_time: 0,
        user_id: param.session.user_id
    };

    insertData('app', app);
}

mysql.addActivity = function (param) {
    var activity = {
        activity_name: param.body.activity_name,
        total_time: 0,
        app_num: param.body.app_num
    };
    /**
     * TODO:user_id에 따라서 app_num을 찾아서 넣어줘야함
     * TODO:user_id 마다 activity의 이름이 중복이 안되도록 구현
     */

    insertData('activity', activity);
}

mysql.addObject = function (param) {
    var object = {
        object_name: param.body.object_name,
        object_frequency: 0,
        error_frequency: 0,
        image_num: param.body.image_num,
        activity_num: param.body.activity_num
    };
    /**
     * TODO:오프젝트의 위치, 크기, 텍스트, 컬러 설정해줘야함
     * TODO:user_id에 따라서 activity_num, image_num을 찾아서 넣어줘야함
     */
    insertData('object', object);
}

mysql.addAppUse = function (param) {
    var appUse = {
        during_time_start: param.body.during_time_start,
        during_time_end: param.body.during_time_end,
        app_num: null
    };

    /**
     *  app_use_info에 사용 정보를 기록하고
     *  app_info에 total_time을 갱신
     */
    mysql.query('SELECT app_info.app_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND app_info.user_id = \'' + param.body.user_id + '\' '
        + 'AND app_info.app_name = \'' + param.body.app_name + '\''
        , function (error, result, fields) {
            if (error) {
                console.error(error);
            } else {
                appUse.app_num = result[0].app_num;
                insertData('app_use', appUse);
                updateTotalTime('app', appUse.app_num, appUse.during_time_start, appUse.during_time_end);
            }
        });
}

mysql.addActivityUse = function (param) {
    var activityUse = {
        during_time_start: param.body.during_time_start,
        during_time_end: param.body.during_time_end,
        activity_num: null
    };

    /**
     *  activity_use_info에 사용 정보를 기록하고
     *  activity_info에 total_time을 갱신
     */
    mysql.query('SELECT activity_info.activity_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND user_info.user_id = \'' + param.body.user_id + '\' '
        + 'INNER JOIN activity_info ON app_info.app_num = activity_info.app_num '
        + 'AND activity_info.activity_name = \'' + param.body.activity_name + '\' '
        + 'AND app_info.app_name = \'' + param.body.app_name + '\''
        , function (error, result, fields) {
            if (error) console.error(error);
            else if(result){
                activityUse.activity_num = result[0].activity_num;
                insertData('activity_use', activityUse)
                updateTotalTime('activity', activityUse.activity_num, activityUse.during_time_start, activityUse.during_time_end);
            }
        });
}

mysql.addObjectUse = function (param) {
    var objectUse = {
        occur_time: param.body.occur_time,
        event_type: param.body.event_type,
        object_num: null
    };

    recordUseAndUpdateFrequency('object', param, objectUse);
}

mysql.addErrorUse = function (param) {
    var errorUse = {
        occur_time: param.body.occur_time,
        object_num: null
    };

    recordUseAndUpdateFrequency('error', param, errorUse);
}

function updateData(table, data) {
    mysql.query('update ' + table + '_info set ?' + ' where user_id=\'' + data.user_id + '\'', data, throwError);
}

function insertData(table, data) {
    mysql.query('insert into ' + table + '_info set ?', data, throwError);
}

//function getData(table, column, ref){
//    mysql.query('SELECT ' + column + ' FROM ' + table + ' WHERE ' + '\'' + ref + '\'', ref, throwError);
//}


function recordUseAndUpdateFrequency(type, param, dataUse) {
    mysql.query('SELECT object_info.object_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND user_info.user_id = ' + '\'' + param.body.user_id + '\' '
        + 'AND app_info.app_name = ' + '\'' + param.body.app_name + '\' '
        + 'INNER JOIN activity_info ON app_info.app_num = activity_info.app_num '
        + 'AND activity_info.activity_name = \'' + param.body.activity_name + '\' '
        + 'INNER JOIN object_info ON object_info.activity_num = activity_info.activity_num '
        + 'AND object_info.object_name = \'' + param.body.object_name + '\''
        , function (error, result, fields) {
            if (error) {
                console.error(error);
            } else {//TODO:result가 없을 때 예외처리
                dataUse.object_num = result[0].object_num;
                mysql.query('insert into ' + type + '_use_info set ?', dataUse, throwError);
                updateFrequency(type, result[0].object_num);
            }
        });
}

function updateTotalTime(type, num, start, end) {
    mysql.query('update ' + type + '_info set total_time=total_time + ' + calDuringTime(start, end) + ' where ' + type + '_num like ' + num, throwError);
}

function updateFrequency(type, object_num) {
    mysql.query('update object_info set ' + type + '_frequency=' + type + '_frequency+1 where object_num like ' + object_num, throwError);
}

function calDuringTime(start, end) {
    var time = NumOfDate(end) - NumOfDate(start);
    return time;
}

function NumOfDate(date) {
    /**
     *  날짜, 시간에 문자열을 숫자로 표현된 값으로 반환
     */
    var piece = date.split(" ");
    var standardYears = 1990 * 60 * 60 * 30 * 12;
    var years = piece[0].split("-")[0] * 60 * 60 * 30 * 12 - standardYears;
    var month = piece[0].split("-")[1] * 60 * 60 * 30;
    var days = piece[0].split("-")[2] * 60 * 60 * 24;
    var hours = piece[1].split(":")[0] * 60 * 60;
    var minutes = piece[1].split(":")[1] * 60;
    var seconds = piece[1].split(":")[2] * 1;

    return years + month + days + hours + minutes + seconds;
}

function throwError(error, result, fields) {
    if (error) {
        console.error(error);
    } else {
    }
}

module.exports = mysql;
