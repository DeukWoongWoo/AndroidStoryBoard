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
    mysql.query('insert into user_info set ?', user, throwError);
}

//select A.user_id, C.app_num from user_info U inner join app_info A on U.user_id = 'b' inner join activity_info C on A.app_num = C.app_num;
//SELECT US.user_id, AP.app_num, AC.activity_num FROM user_info US INNER JOIN app_info AP ON US.user_id = AP.user_id INNER JOIN activity_info AC ON AP.app_num = AC.app_num;

mysql.addApp = function(param){
    var app = {
        app_num:param.body.app_num,
        app_name:param.body.app_name,
        total_time:param.body.total_time,
        user_id:param.body.user_id
    };

    mysql.query('insert into app_info set ?', app, throwError);
}

mysql.addActivity = function(param){
    var activity = {
        activity_num:param.body.activity_num,
        activity_name:param.body.activity_name,
        total_time:param.body.total_time,
        app_num:param.body.app_num
    };

    mysql.query('insert into activity_info set ?', activity, throwError);
}

mysql.addObject = function(param){
    var object = {
        object_num:param.body.object_num,
        object_name:param.body.object_name,
        frequency:param.body.frequency,
        activity_num:param.body.activity_num
    };

    mysql.query('insert into object_info set ?', object, throwError);
}

mysql.addError = function(param){
    var error = {
        error_num:param.body.error_num,
        frequency:param.body.frequency,
        object_num:param.body.object_num
    };
    mysql.query('insert into error_use_info set ?', error, throwError);
}

mysql.addAppUse = function(param){
    var appUse = {
        app_use_num:param.body.app_use_num,
        during_time_start:param.body.during_time_start,
        during_time_end:param.body.during_time_end,
        app_num:null,
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
            mysql.updateAppTotalTime(appUse.app_num, appUse.during_time_start, appUse.during_time_end);
            //찾은 app_num에 사용시간을 더한다.
        }
    });
}

mysql.updateAppTotalTime = function(num, start, end){
    var time = NumOfDate(end) - NumOfDate(start) ;
    mysql.query('update app_info set total_time=total_time + ' + time + ' where app_num like ' + num, throwError);
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
            mysql.updateActivityTotalTime(activityUse.activity_num, activityUse.during_time_start, activityUse.during_time_end);
            //찾은 activity_num에 사용시간을 더한다.
        }
    });
}

mysql.updateActivityTotalTime = function(num, start, end){
    var time = NumOfDate(end) - NumOfDate(start) ;
    mysql.query('update activity_info set total_time=total_time + ' + time + ' where activity_num like ' + num, throwError);
}

mysql.addObjectUse = function(param){
    var objectUse = {
        object_use_num:param.body.object_use_num,
        occur_time:param.body.occur_time,
        event_type:param.body.event_type,
        object_num:null
    };

    /**
     *  object_use_info에 사용 정보를 기록하고
     *  object_info에 frequency을 갱신
     */
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
                objectUse.object_num = result[0].object_num;
                mysql.query('insert into object_use_info set ?', objectUse, throwError);
                mysql.updateObjectFrequency(result[0].object_num);
                //찾은 object_num에 사용횟수를 늘린다.
            }
        });
}

mysql.updateObjectFrequency  = function(object_num){
    mysql.query('update object_info set frequency=frequency+1 where object_num like ' + object_num, throwError);
}

mysql.addErrorUse = function(param){
    var errorUse = {
        error_use_num:param.body.error_use_num,
        occur_time:param.body.occur_time,
        error_num:param.body.error_num
    };
    mysql.query('insert into error_use_info set ?', errorUse, throwError);
    /*
     *   데이터베이스 설계 검토 후
     *   mysql.query('update error_use_info set frequency=frequency+1 where error_num like ' + errorUse.error_num, throwError);
     */
}


var throwError = function (error, result, fields) {
    if (error) {
        console.error('쿼리 문장에 오류가 있습니다.');
        console.error(error);
    } else {
        //console.log(result);
    }
}

var NumOfDate = function(date){


    var piece = date.split(" ");
    console.log('piece : ' + piece );

    var standardYears = 1990 * 60 * 60 * 30 * 12;
    var years = piece[0].split("-")[0] * 60 * 60 * 30 * 12 - standardYears;
    var month = piece[0].split("-")[1] * 60 * 60 * 30;
    var days = piece[0].split("-")[2] * 60 * 60 * 24;

    var hours = piece[1].split(":")[0] * 60 * 60;
    var minutes = piece[1].split(":")[1]  * 60;
    var seconds = piece[1].split(":")[2] * 1;

    console.log('years : ' + years );
    console.log('month : ' + month );
    console.log('days : ' + days);
    console.log('hours : ' + hours);
    console.log('minutes : ' + minutes);
    console.log('seconds : ' + seconds);

    return years + month + days + hours + minutes + seconds ;
}

module.exports = mysql;
