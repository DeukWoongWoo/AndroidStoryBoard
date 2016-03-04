var db = require('mysql');
var async = require('async');

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

mysql.getUserAppObject = function (param, callback) {
    mysql.query('SELECT object_info.*, activity_info.* FROM user_info INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + ' INNER JOIN activity_info ON app_info.app_num = activity_info.app_num '
        + ' INNER JOIN object_info ON activity_info.activity_num = object_info.activity_num '
        + ' AND user_info.user_id = \'' + param.body.user_id + '\' '
        + ' AND app_info.app_name = \'' + param.body.app_name + '\' '
        , function (err, result) {
            callback(err, result);
        });
}
mysql.getErrorUseInfoByNum = function (param, callback) {
    mysql.query('SELECT * FROM error_use_info '
        + ' WHERE object_num = \'' + param.body.object_num + '\' '
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getObjectUseInfoByNum = function (param, callback) {
    mysql.query('SELECT * FROM object_use_info '
        + ' WHERE object_num = \'' + param.body.object_num + '\' '
        , function (err, result) {
            callback(err, result);
        });
}


mysql.getAllUserID = function (param, callback) {
    mysql.query('SELECT * FROM user_info'
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getAppByUserId = function (param, callback) {
    mysql.query('SELECT * FROM app_info WHERE user_id = \''
        + param.body.user_id + '\''
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getObjectByActivityNum = function (param, callback) {
    console.log(param.body.app_num);
    mysql.query('SELECT * FROM object_info '
        + ' WHERE activity_num = ' + param.body.activity_num
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getActivityByAppNum = function (param, callback) {
    console.log(param.body.app_num);
    mysql.query('SELECT * FROM activity_info '
        + ' WHERE app_num = ' + param.body.app_num
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getActivityByUserId = function (param, callback) {
    console.log(param.body.user_id);
    mysql.query('SELECT * FROM activity_info '
        + ' INNER JOIN app_info ON app_info.app_num = activity_info.app_num '
        + ' WHERE user_id = \'' + param.body.user_id + '\' '
        + ' AND app_name = \'' + param.body.app_name + '\''
        , function (err, result) {
            callback(err, result);
        });
}

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
        user_id: isDefined(param.session.user_id) ? param.session.user_id : param.body.user_id
    };

    insertData('app', app);
}

mysql.addActivity = function (param, callback) {
    var activity = {
        activity_name: param.body.activity_name,
        total_time: 0,
        app_num: null
    };

    async.series([
        function (callback) {   //AppNum 찾기
            mysql.getAppNumByUserIdAppName(param, function (err, result) {
                if (err) callback(err);
                else {
                    param.body.app_num = result[0].app_num;
                    callback(null);
                }
            });
        }, function (callback) {    //activity 이름 중복 검사
            mysql.isActivityNameByAppNum(param, function (err, result) {
                if (isDefined(result)) callback('exist activity_name');
                else callback(null);
            });
        }], function (err) {
        if (err) callback(err);
        else {
            activity.app_num = param.body.app_num;
            insertData('activity', activity);
            callback(null);
        }
    });
}

mysql.isActivityNameByAppNum = function (param, callback) {
    console.log(' ' + param.body.app_num);
    mysql.query('SELECT activity_name FROM activity_info '
        + ' WHERE app_num = ' + param.body.app_num
        + ' AND activity_name = \'' + param.body.activity_name + '\' '
        , function (err, result) {
            callback(err, result);
        });
}

mysql.isActivityNameByUserIdAppName = function (param, callback) {
    mysql.query('SELECT activity_name FROM app_info '
        + ' INNER JOIN activity_info ON app_info.user_id = \'' + param.body.user_id + '\' '
        + ' AND app_name = \'' + param.body.app_name + '\''
        + ' AND activity_name = \'' + param.body.activity_name + '\''
        , function (err, result) {
            callback(err, result);
        });
}

mysql.getAppNumByUserIdAppName = function (param, callback) {
    mysql.query('SELECT app_info.app_num FROM user_info '
        + ' INNER JOIN app_info ON user_info.user_id = app_info.user_id '
        + ' AND user_info.user_id = \'' + param.body.user_id + '\' '//session
        + ' AND app_info.app_name = \'' + param.body.app_name + '\' '
        , function (err, result) {
            callback(err, result);
        });
}

mysql.addObject = function (param) {
    var activityName = param.body.activity_name;
    var object = {
        object_name: param.body.object_name,
        //location_x: param.body.location_x,
        //location_y: param.body.location_y,
        //size_width: param.body.size_width,
        //size_height: param.body.size_height,
        type: param.body.type ? param.body.type : 'button',
        //color: param.body.color ? param.body.color : 'gray',
        //image_num: param.body.image_num ? param.body.image_num : 1,//todo
        activity_num: param.body.activity_num,

        object_frequency: 0,
        error_frequency: 0
    };
    /**
     * TODO:오프젝트의 위치, 크기, 텍스트, 컬러 설정해줘야함
     * TODO:user_id에 따라서 activity_num, image_num을 찾아서 넣어줘야함
     */
    mysql.getActivityNumByUserIdAppNameActivityName(param, activityName, function (err, result) {
        if (err)console.error(err);
        else {
            object.activity_num = result[0].activity_num;
            insertData('object', object);
        }
    });
}

mysql.getActivityNumByUserIdAppNameActivityName = function (param, activityName, callback) {
    mysql.getAppNumByUserIdAppName(param, function (err, result) {
        if (err) callback(err);
        else {
            param.body.app_num = result[0].app_num;
            mysql.getActivityNumByAppNumActivityName(param, activityName, function (err, result) {
                if (err) callback(err);
                else {
                    callback(null, result);
                }
            });
        }
    });
}

mysql.getActivityNumByAppNumActivityName = function (param, activityName, callback) {
    mysql.query('SELECT activity_num FROM activity_info '
        + ' WHERE app_num = ' + param.body.app_num
        + ' AND activity_name = \'' + activityName + '\''
        , function (err, result) {
            if (err) callback(err);
            else callback(null, result);
        });
}

mysql.getActivityNumByAppNum = function (app_num, callback) {
    mysql.query('SELECT activity_num FROM activity_info '
        + ' WHERE app_num = ' + app_num
        , function (err, result) {
            if (err) callback(err);
            else callback(null, result);
        });
}

mysql.getObjectNumByActivityNum = function (activity_num, callback) {
    mysql.query('SELECT object_num FROM object_info '
        + ' WHERE activity_num = ' + activity_num
        , function (err, result) {
            if (err) callback(err);
            else callback(null, result);
        });
}

//mysql.getImageNumByObjectNum = function(object_num, callback){
//    mysql.query('SELECT image_num FROM image '
//        + ' WHERE object_num = ' + object_num
//        , function(err, result){
//            if (err) callback(err);
//            else callback(null, result);
//        });
//}

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
            else if (result[0]) {
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

mysql.deleteObjectUseByObjectNum = function (objectNums, callback) {
    var cnt = 0;
    var length = objectNums.length;
    if(length == 0) callback(null);
    for (var i in objectNums) {
        mysql.query('DELETE FROM object_use_info WHERE object_num = ' + objectNums[i]
            , function (err) {
                if (err)callback(err);
            });
        cnt++;
        if (cnt == length) callback(null);
    }
}

mysql.deleteObjectErrByObjectNum = function (objectNums, callback) {
    var cnt = 0;
    var length = objectNums.length;
    if(length == 0) callback(null);
    for (var i in objectNums) {
        mysql.query('DELETE FROM error_use_info WHERE object_num = ' + objectNums[i]
            , function (err) {
                if (err)callback(err);
            });
        cnt++;
        if (cnt == length) callback(null);
    }
}

mysql.deleteActivityUseByActivityNum = function (activityNums, callback) {
    var cnt = 0;
    var length = activityNums.length;
    if(length == 0) callback(null);
    for (var i in activityNums) {
        mysql.query('DELETE FROM activity_use_info WHERE activity_num = ' + activityNums[i]
            , function (err) {
                if (err)callback(err);
            });
        cnt++;
        if (cnt == length) callback(null);
    }
}

mysql.deleteObjectByActivityNum = function (activityNums, callback) {
    var cnt = 0;
    var length = activityNums.length;
    if(length == 0) callback(null);
    for (var i in activityNums) {
        mysql.query('DELETE FROM object_info WHERE activity_num = ' + activityNums[i]
            , function (err) {
                if (err)callback(err);
            });
        cnt++;
        if (cnt == length) callback(null);
    }
}

mysql.deleteActivityByAppNum = function (appNum, callback) {
    mysql.query('DELETE FROM activity_info WHERE app_num = ' + appNum
        , function (err) {
            if (err)callback(err);
            else callback(null);
        });
}

mysql.deleteImageByImageNum = function (imageNums, callback) {
    var cnt = 0;
    var length = imageNums.length;
    if(length == 0) callback(null);
    for (var i in imageNums) {
        mysql.query('DELETE FROM image WHERE image_num = ' + imageNums[i]
            , function (err) {
                if (err)callback(err);
            });
        cnt++;
        if (cnt == length) callback(null);
    }
}

mysql.deleteAppByAppNum = function (appNum, callback) {
    mysql.query('DELETE FROM app_info WHERE app_num = ' + appNum
        , function (err) {
            if (err)callback(err);
            else callback(null);
        });
}

//function getData(table, column, ref){
//    mysql.query('SELECT ' + column + ' FROM ' + table + ' WHERE ' + '\'' + ref + '\'', ref, throwError);
//}

mysql.updateActivityXY = function(activityNum,x , y, callback){
    mysql.query('UPDATE activity_info SET x=' + x
    + ', y=' + y + ' WHERE activity_num=' + activity_num, function(err){
        callback(err);
    });
}

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
            } else if (isDefined(result[0])) {//TODO:result가 없을 때 예외처리
                dataUse.object_num = result[0].object_num;
                mysql.query('insert into ' + type + '_use_info set ?', dataUse, throwError);
                updateFrequency(type, result[0].object_num);
            }
        });
}

function updateTotalTime(type, num, start, end) {
    console.log('updateTotalTime');
    console.log(start);
    console.log(end);
    console.log(calDuringTime(start, end));
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
    var piece = date.split(" ");
    var standardYears = 2015 * 60 * 30 * 12;
    var years = piece[0].split("/")[0] * 60 * 60 * 24 * 30 * 12 - standardYears;
    var month = piece[0].split("/")[1] * 60 * 60 * 24 * 30;
    var days = piece[0].split("/")[2] * 60 * 60 * 24;
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

function isDefined(param) {
    return param != '' && param != undefined;
}

module.exports = mysql;
