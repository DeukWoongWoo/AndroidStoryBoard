var express = require('express');
var router = express.Router();
var db = require('../models/db');
var async = require('async');
var fs = require('fs');
var mkdirp = require('mkdirp');    // mkdirp 모듈있는 곳을 설정해주면됩니다.

var dateutils = require('date-utils');


router.get('/str', function (req, res) {
    res.render('webstoryboard');
});

router.get('/ajax', function (req, res) {
    //console.log(req.body.name); // Ajax parameter data
    db.query('select * from user_info', function (error, result) {
        res.send(result);
    });
});

router.get('/ajax/frequency', function (req, res) {
    //console.log(req.body.name); // Ajax parameter data
    db.query('select object_info.object_frequency from object_info', function (error, result) {
        res.send(result);
    });
});

router.get('/makedata', function (req, res) {
    db.getAllUserID(null, function (err, result) {
        res.render('makedata', {user: result});
    });
});

router.post('/makedata/app', function (req, res) {
    getAppAndRender(req, res);
});

router.post('/makedata/app/add', function (req, res) {
    db.addApp(req);
    getAppAndRender(req, res);
});

function getAppAndRender(req, res){
    db.getAppByUserId(req, function (err, result) {
        if (err)res.send(err);
        else res.render('makedataapp', {app: result, user_id: req.body.user_id});
    });
}

router.post('/makedata/activity', function (req, res) {
    getActivityAndRender(req, res);
});

router.post('/makedata/activity/add', function (req, res) {
    var activity = {
        activity_name: req.body.activity_name,
        total_time: 0,
        app_num: req.body.app_num
    };
    db.query('insert into activity_info set ?', activity, function (err) {
        if(err)console.error(err);
        else
            getActivityAndRender(req, res);
    });
});

function getActivityAndRender(req, res){
    db.getActivityByAppNum(req, function (err, result) {
        if (err)res.send(err);
        else
            res.render('makedataactivity', {activity: result, app_num: req.body.app_num, app_name: req.body.app_name});
    });
}


router.post('/makedata/object', function (req, res) {
    getObjectAndRender(req, res);
});

router.post('/makedata/object/add', function (req, res) {
    var object = {
        object_name: req.body.object_name,
        object_frequency: 0,
        error_frequency: 0,
        image_num: 1,
        activity_num: req.body.activity_num
    };
    console.log(object.image_num);
    db.query('insert into object_info set ?', object, function (err) {
        if (err)console.error(err);
        else
            getObjectAndRender(req, res);
    });
});

function getObjectAndRender(req, res){
    //console.log(req.body);
    db.getObjectByActivityNum(req, function (err, result) {
        if (err)res.send(err);
        else {
            res.render('makedataobject', {
                object: result,
                activity_num: req.body.activity_num,
                activity_name: req.body.activity_name
            });
        }
    });
}

router.post('/makedata/object/use', function (req, res) {
    var dt = new Date();
    var d = dt.toFormat('YYYY-MM-DD HH24:MI:SS');

    console.log('[' + d + '] ' + '현재 시간');
    var use = {
        object_num : req.body.object_num,
        occur_time : d,
        event_type : 'button'
    }

    db.query('insert into object_use_info set ?', use, function(err){
        if(err)console.error(err);
    });
    db.query('update object_info set object_frequency=object_frequency+1 where object_num like ' + req.body.object_num, function(err){
        if(err)console.error(err);
    });

    getObjectAndRender(req, res);
});
router.post('/makedata/object/err', function (req, res) {
    //console.log(req.body);
    var use = {
        object_num : req.body.object_num,
        occur_time : '2016-02-01',
    }

    db.query('insert into error_use_info set ?', use, function(err){
        if(err)console.error(err);
    });
    db.query('update object_info set error_frequency=error_frequency+1 where object_num like ' + req.body.object_num, function(err){
        if(err)console.error(err);
    });
    getObjectAndRender(req, res);
});

router.post('/date-search', function (req, res) {
    if (isDefined(req.body.start_date) && isDefined(req.body.end_date)) {

    } else {
        db.getUserAppObject(req, function (err, result) {
            console.log(req.body);
            console.log(err);
            console.log(result);
            if (err) console.log(err);
            else res.send(result);
        });
    }
});

router.post('/get/object_info', function (req, res) {
    db.getUserAppObject(req, function (err, result) {
        if (err) console.log(err);
        else res.send(result);
    });
});

router.post('/get/object_use_info', function (req, res) {
    db.getObjectUseInfoByNum(req, function (err, result) {
        if (err) console.log(err);
        else res.send(result);
    });
});

router.post('/get/error_use_info', function (req, res) {
    db.getErrorUseInfoByNum(req, function(err, result) {
        if (err) console.log(err);
        else res.send(result);
    });
});

router.get('/d3test', function (req, res, next) {
    isLogin(req, function (err) {
        if (err) res.redirect('/login');
        else
            db.getAppNumList(req, function (err, appList) {
                res.render('d3test', {app: appList});
            });
    });
});

router.get('/', function (req, res, next) {
    renderAppPage(req, res);
});

router.get('/logout', function (req, res, next) {
    logout(req, res);
});

router.post('/registerapp', function (req, res, next) {
    registerApp(req, res, function (err) {
        if (err) console.log(err);//TODO:경고 메시지 띄우기
        else renderAppPage(req, res);
    });
});

function renderAppPage(req, res) {
    isLogin(req, function (err) {
        if (err) res.redirect('/login');
        else
            db.getAppList(req, function (err, appList) {
                res.render('home', {
                    user_id: req.session.user_id,
                    app: appList,
                });
            });
    });
}

function registerApp(req, res, callback) {
    isLogin(req, function (err) {
        if (err) res.redirect('/login');
        else {
            async.series([
                function (callback) {
                    uploadRequest(req, function (err) {
                        if (err) callback(err);
                        else callback(null);
                    });
                }, function (callback) {
                    fileUpload(req, function (err) {
                        if (err) callback(err);
                        else callback(null);
                    });
                }, function (callback) {
                    //parseJSON(req, function(err){
                    callback(null);
                    //});
                }
            ], function (err) {
                if (err) {
                    fs.unlink(req.files.storyboard.path);
                    callback(err);
                } else {
                    db.addApp(req);
                    callback(null);
                }
            });
        }
    });
}

function uploadRequest(req, callback) {
    async.series([
        function (callback) {
            checkFile(req, function (err) {
                callback(err);
            });
        }, function (callback) {
            isExistAppName(req, function (err) {
                callback(err);
            });
        }
    ], function (err) {
        callback(err);
    });
}

function fileUpload(req, callback) {
    var tmpOfTarget = req.files.storyboard.path;
    var locationOfTarget = './users/' + req.session.user_id + '/' + req.body.app_name + '/';
    var target = locationOfTarget + req.files.storyboard.name;

    async.series([
        function (callback) {
            mkdirp(locationOfTarget, function (err) {
                if (err) callback(err);
                else callback(null);
            });
        }, function (callback) {
            fs.rename(tmpOfTarget, target, function (err) {
                if (err) callback(err);
                else callback(null);
            });
        }
    ], function (err) {
        if (err) callback(err);
        else callback(null);
    });
}

function checkFile(req, callback) {
    if (!isDefined(req.body.app_name))
        callback('파일의 이름을 입력해주세요');
    else if (isGreaterThan(req.body.app_name.length, 20))
        callback('파일 이름이 20자보다 큽니다');
    else if (!isDefined(req.files.storyboard.name))
        callback('파일이 없습니다');
    else
        callback(null);
}

function isExistAppName(req, callback) {
    var err = null;
    db.getAppList(req, function (appList) {
        if(isDefined(appList)){
            for (var i = 0; i < appList.length; i++) {
                if (req.body.app_name == appList[i].app_name) {
                    err = '같은 이름이 있습니다';
                }
            }
        }
        callback(err);
    });
}

function isLogin(req, callback) {
    if (isDefined(req.session.user_id))
        callback(null);
    else
        callback('로그인을 해주세요');
}

function isDefined(param) {
    return param != '' && param != undefined;
}

function isGreaterThan(a, b) {
    return a > b;
}

function logout(req, res) {
    req.session.destroy(function (err) {
        res.redirect('/');
    });
}

module.exports = router;
