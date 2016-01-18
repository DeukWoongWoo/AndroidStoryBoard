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

mysql.addUser = function(param){
    var user = {
        user_id:param.body.user_id,
        name:param.body.name,
        password:param.body.password,
        email:param.body.email
    };

    insertData('user', user);
}

mysql.addApp = function(param){
    var app = {
        app_num:param.body.app_num,
        app_name:param.body.app_name,
        total_time:param.body.total_time,
        user_id:param.body.user_id
    };

    insertData('app', app);
}

mysql.addActivity = function(param){
    var activity = {
        activity_num:param.body.activity_num,
        activity_name:param.body.activity_name,
        total_time:param.body.total_time,
        app_num:param.body.app_num
    };

    insertData('activity', activity);
}

mysql.addObject = function(param){
    var object = {
        object_num:param.body.object_num,
        object_name:param.body.object_name,
        object_frequency:param.body.object_frequency,
        error_frequency:param.body.error_frequency,
        image_num:param.body.image_num,
        activity_num:param.body.activity_num
    };

    insertData('object', object);
}

mysql.addAppUse = function(param){
    var appUse = {
        app_use_num:param.body.app_use_num,
        during_time_start:param.body.during_time_start,
        during_time_end:param.body.during_time_end,
        app_num:null
    };

    /**
     *  app_use_info에 사용 정보를 기록하고
     *  app_info에 total_time을 갱신
     */
    mysql.query('SELECT app_info.app_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND app_info.user_id = \''+ param.body.user_id + '\''
        , function (error, result, fields) {
        if (error) {
            console.error(error);
        } else {
            appUse.app_num = result[0].app_num;
            mysql.query('insert into app_use_info set ?', appUse, throwError);
            updateTotalTime('app', appUse.app_num, appUse.during_time_start, appUse.during_time_end);
        }
    });
}

mysql.addActivityUse = function(param){
    var activityUse = {
        activity_use_num:param.body.activity_use_num,
        during_time_start:param.body.during_time_start,
        during_time_end:param.body.during_time_end,
        activity_num:null
    };

    /**
     *  activity_use_info에 사용 정보를 기록하고
     *  activity_info에 total_time을 갱신
     */
    mysql.query('SELECT activity_info.activity_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND user_info.user_id = \'' + param.body.user_id + '\' '
        + 'INNER JOIN activity_info ON app_info.app_num = activity_info.app_num '
        + 'AND activity_info.activity_name = \'' + param.body.activity_name + '\''
        , function (error, result, fields) {
        if (error) {
            console.error(error);
        } else {
            activityUse.activity_num = result[0].activity_num;
            mysql.query('insert into activity_use_info set ?', activityUse, throwError);
            updateTotalTime('activity', activityUse.activity_num, activityUse.during_time_start, activityUse.during_time_end);
        }
    });
}

mysql.addObjectUse = function(param){
    var objectUse = {
        object_use_num:param.body.object_use_num,
        occur_time:param.body.occur_time,
        event_type:param.body.event_type,
        object_num:null
    };

    recordUseAndUpdateFrequency('object', param, objectUse);
}

mysql.addErrorUse = function(param){
    var errorUse = {
        error_use_num:param.body.error_use_num,
        occur_time:param.body.occur_time,
        object_num:null
    };

    recordUseAndUpdateFrequency('error', param, errorUse);
}

function insertData(stringOfData, data) {
    mysql.query('insert into ' + stringOfData + '_info set ?', data, throwError);
}

function recordUseAndUpdateFrequency(type, param, dataUse) {
    mysql.query('SELECT object_info.object_num FROM user_info '
        + 'INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + 'AND user_info.user_id = ' + '\'' + param.body.user_id + '\' '
        + 'INNER JOIN activity_info ON app_info.app_num = activity_info.app_num '
        + 'AND activity_info.activity_name = \'' + param.body.activity_name + '\' '
        + 'INNER JOIN object_info ON object_info.activity_num = activity_info.activity_num '
        + 'AND object_info.object_name = \'' + param.body.object_name + '\''
        , function (error, result, fields) {
            if (error) {
                console.error(error);
            } else {
                dataUse.object_num = result[0].object_num;
                mysql.query('insert into ' + type + '_use_info set ?', dataUse, throwError);
                updateFrequency(type, result[0].object_num);
            }
        });
}

function updateTotalTime(type, num, start, end){
    mysql.query('update ' + type + '_info set total_time=total_time + ' + calDuringTime(start, end) + ' where app_num like ' + num, throwError);
}

function updateFrequency(type, object_num){
    mysql.query('update object_info set ' + type + '_frequency=' + type + '_frequency+1 where object_num like ' + object_num, throwError);
}

function calDuringTime(start, end){
    var time = NumOfDate(end) - NumOfDate(start);
    return time;
}

function NumOfDate(date){
    /**
     *  날짜, 시간에 문자열을 숫자로 표현된 값으로 반환
     */
    var piece = date.split(" ");
    var standardYears = 1990 * 60 * 60 * 30 * 12;
    var years = piece[0].split("-")[0] * 60 * 60 * 30 * 12 - standardYears;
    var month = piece[0].split("-")[1] * 60 * 60 * 30;
    var days = piece[0].split("-")[2] * 60 * 60 * 24;
    var hours = piece[1].split(":")[0] * 60 * 60;
    var minutes = piece[1].split(":")[1]  * 60;
    var seconds = piece[1].split(":")[2] * 1;

    return years + month + days + hours + minutes + seconds ;
}

function throwError(error, result, fields) {
    if (error) {
        console.error(error);
    } else {
    }
}

module.exports = mysql;
