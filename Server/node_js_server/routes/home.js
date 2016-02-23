var express = require('express');
var router = express.Router();
var db = require('../models/db');
var async = require('async');
var fs = require('fs');
var mkdirp = require('mkdirp');    // mkdirp 모듈있는 곳을 설정해주면됩니다.

var dateutils = require('date-utils');

router.get('/err', function (req, res) {
    res.render('error', {
        message: 'fail',
        error: {}
    });
});

router.get('/delete/image/:app_name', function (req, res) {
    if(!(isDefined(req.session.user_id) && isDefined(req.params.app_name)))
        res.redirect('/');

    var locationOfTarget = './users/' + req.session.user_id + '/' + req.params.app_name + '/';
    fs.readdir(locationOfTarget, function (err, list) {
        if (err) console.error(err);
        async.each(list,
            function (file, callback) {
                if (file == req.params.app_name + '.json') {
                    callback(null);
                } else {
                    fs.unlink(locationOfTarget + file, function (err) {
                        if (err) callback(err);
                        else callback(null);
                    });
                }
            },
            function (err) {
                if (err) console.error(err);
                res.redirect('/');
            }
        );
    });
});

router.get('/delete/:app_name', function (req, res) {
    req.body.user_id = req.session.user_id;
    req.body.app_name = req.params.app_name;

    async.waterfall([
        function (callback) {
            db.getAppNumByUserIdAppName(req, function (err, result) {
                if (err) callback(err);
                else if (isDefined(result[0])) {
                    var appNum = result[0].app_num;
                    callback(null, appNum);
                }
            });
        }, function (appNum, callback) {
            db.getActivityNumByAppNum(appNum, function (err, result) {
                if (err) callback(err);
                else if (isDefined(result)) {
                    var activityNums = [];
                    for (var i in result)
                        activityNums.push(result[i].activity_num);
                    callback(null, appNum, activityNums);
                } else callback('empty', appNum);
            });
        }, function (appNum, activityNums, callback) {
            var objectNums = [];
            var cnt = 0;
            for (var i in activityNums) {
                db.getObjectNumByActivityNum(activityNums[i], function (err, result) {
                    if (err) callback(err);
                    else if (isDefined(result)) {
                        for (var j in result) {
                            if (isDefined(result[j])) {
                                objectNums.push(result[j].object_num);
                            }
                        }
                    }
                    cnt++;
                    if (cnt == activityNums.length)
                        callback(null, appNum, activityNums, objectNums);
                });
            }
        }, function (appNum, activityNums, objectNums, callback) {
            db.deleteObjectUseByObjectNum(objectNums, function (err) {
                if (err) callback(err);
                else {
                    db.deleteObjectErrByObjectNum(objectNums, function (err) {
                        if (err) callback(err);
                        else {
                            db.deleteActivityUseByActivityNum(activityNums, function (err) {
                                if (err) callback(err);
                                else callback(null, appNum, activityNums, objectNums);
                            });
                        }
                    });
                }
            });

        }, function (appNum, activityNums, objectNums, callback) {
            db.deleteObjectByActivityNum(activityNums, function (err) {
                if (err) callback(err);
                else {
                    db.deleteActivityByAppNum(appNum, function (err) {
                        if (err) callback(err);
                        else {
                            callback(null, appNum);
                        }
                    });
                }
            });
        }
    ], function (err, result) {
        if (result || !err) {
            db.deleteAppByAppNum(result, function (err) {
                if (err) console.log(err);
            });
            var dirName = './users/' + req.session.user_id + '/' + req.params.app_name + '/';
            deleteFiles(dirName, function(err){
                if(err) console.log(err);
            });
        } else if (err)console.log(err);
        res.redirect('/');
    });
});

function deleteFiles(dirName, callback) {
    fs.readdir(dirName, function (err, list) {
        if (err) callback(err);
        async.each(list,
            function (file, callback) {
                fs.unlink(dirName + file, function (err) {
                    if (err) throw err;
                    else {
                        console.error('unlink ' + dirName + file);
                        callback(null);
                    }
                });
            },
            function (err) {
                if(err)callback(err);
                fs.rmdir(dirName, function (err) {
                    if (err) {
                        console.error(err);
                        callback(err);
                    }//// throw err;
                    console.log('Removed ' + dirName);
                });
            }
        );
    });
}

router.get('/storyboard/:app_name', function (req, res) {
    //TODO: req.params.user_id -> session.user_id로 변경 해야함
    var fileUrl = './users/' + req.session.user_id + '/' + req.params.app_name + '/' + req.params.app_name + '.json';
    fs.exists(fileUrl, function (exists) {
        if (exists) {
            fs.readFile(fileUrl, function (err, data) {
                var obj;
                try {
                    obj = JSON.parse(data);
                    res.send(obj);
                } catch (e) {
                    console.log(e);
                }
            });
        } else
            res.send('file is not exists');
    })
});

router.get('/image/:user_id/:app_name/:file_name', function (req, res) {
    //TODO: req.params.user_id -> session.user_id로 변경 해야함
    var fileUrl = './users/' + req.params.user_id + '/' + req.params.app_name + '/' + req.params.file_name;
    fs.exists(fileUrl, function (exists) {
        if (exists) {
            fs.readFile(fileUrl, function (err, data) {
                res.end(data);
            });
        } else {
            res.end('file is not exists');
        }
    })
});

router.get('/error/log/:app_name', function (req, res) {
    var fileUrl = './users/' + req.session.user_id + '/' + req.params.app_name + '/error.log';
    console.log(fileUrl);
    fs.exists(fileUrl, function (exists) {
        if (exists) {
            fs.readFile(fileUrl, function (err, data) {
                res.send(data);
            });
        } else {
            res.send('no error');
        }
    })
});

router.get('/image/:file_name', function (req, res) {
    console.log("/image/:file_name', function (req, res) {");
    var fileUrl = './users/' + req.params.file_name;
    fs.exists(fileUrl, function (exists) {
        if (exists) {
            fs.readFile(fileUrl, function (err, data) {
                res.end(data);
            });
        } else {
            res.end('file is not exists');
        }
    })
});

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

function getAppAndRender(req, res, msg) {
    db.getAppByUserId(req, function (err, result) {
        if (err)res.send(err);
        else {
            var page_data = {app: result, user_id: req.body.user_id};
            if(isDefined(msg)) page_data.push({warring_result : msg});
            res.render('makedataapp', page_data);
        }
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
        if (err)console.error(err);
        else
            getActivityAndRender(req, res);
    });
});

function getActivityAndRender(req, res) {
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

function getObjectAndRender(req, res) {
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

    var use = {
        object_num: req.body.object_num,
        occur_time: d,
        event_type: 'button'
    }

    db.query('insert into object_use_info set ?', use, function (err) {
        if (err)console.error(err);
    });
    db.query('update object_info set object_frequency=object_frequency+1 where object_num like ' + req.body.object_num, function (err) {
        if (err)console.error(err);
    });

    getObjectAndRender(req, res);
});

router.post('/makedata/object/err', function (req, res) {
    //console.log(req.body);
    var use = {
        object_num: req.body.object_num,
        occur_time: '2016-02-01',
    }

    db.query('insert into error_use_info set ?', use, function (err) {
        if (err)console.error(err);
    });
    db.query('update object_info set error_frequency=error_frequency+1 where object_num like ' + req.body.object_num, function (err) {
        if (err)console.error(err);
    });
    getObjectAndRender(req, res);
});

router.post('/date-search', function (req, res) {
    if (isDefined(req.body.start_date) && isDefined(req.body.end_date)) {

    } else {
        db.getUserAppObject(req, function (err, result) {
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
    db.getErrorUseInfoByNum(req, function (err, result) {
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
        //if (err) console.log(err);//TODO: 웹에 경고 메시지 띄우기res.send(err);//
        //else
            renderAppPage(req, res, err);
    });
});

router.post('/registerimage', function (req, res, next) {
    console.error("ajax test");
    console.log(req.body);
    console.log(req.session.user_id);
    console.log(req.files);

    uploadImages(req, function (err) {
        //if (err) res.send(err);//console.log(err);//TODO: 웹에 경고 메시지 띄우기
        //else
            renderAppPage(req, res, err);
    });
});

function renderAppPage(req, res, msg) {
    isLogin(req, function (err) {
        if (err) res.redirect('/login');
        else
            db.getAppList(req, function (err, appList) {
                res.render('home', {
                    user_id: req.session.user_id,
                    app: appList,
                    warring_result : msg,
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
                    uploadStoryboard(req, function (err) {
                        if (err) callback(err);
                        else callback(null);
                    });
                }
            ], function (err) {
                if (err) {
                    fs.unlink(req.files.storyboard.path);
                    callback(err);
                } else {
                    registerStoryboardFile(req, function (err) {
                        callback(err);
                    });
                }
            });
        }
    });
}

function registerStoryboardFile(req, callback) {
    var file = './users/' + req.session.user_id + '/' + req.body.app_name + '/' + req.body.app_name + '.json';
    var obj;
    async.series([
        function (callback) {
            fs.readFile(file, function (err, data) {
                try {
                    obj = JSON.parse(data);
                    callback(null);
                } catch (e) {
                    callback(e);
                }
            });
        }, function (callback) {
            db.addApp(req);
            callback(null);
        }, function (callback) {
            req.body.user_id = req.session.user_id;
            for (var i in obj.activity) {
                addActivityObject(req, obj.activity[i], function (err) {
                });
            }
            callback(null);
        }
    ], function (err) {
        callback(err);
    });
}

function addActivityObject(req, activity, callback) {
    var objects = activity.object;
    req.body.activity_name = activity.name;
    db.addActivity(req, function (err) {
        if (err)callback(err);
        else {
            for (var i in objects) {
                setObjectData(req, activity, objects[i]);
                console.log(objects[i].name);
                console.log(req.body);
                //todo:name 말고 여러가지 object의 속성들을 담아줘야한다.
                db.addObject(req, function (err) {
                    if (err)callback(err);
                });
            }
        }
    });
}

function setObjectData(req, activity, objects) {
    req.body.activity_name = activity.name;
    req.body.object_name = objects.name;
    req.body.location_x = objects.x;
    req.body.location_y = objects.y;
    req.body.size_width = objects.width;
    req.body.size_height = objects.height;
    req.body.type = objects.type;
    req.body.color = objects.color;
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

function uploadStoryboard(req, callback) {
    var tmpOfTarget = req.files.storyboard.path;
    var locationOfTarget = './users/' + req.session.user_id + '/' + req.body.app_name + '/';
    var target = locationOfTarget + req.body.app_name + '.json';

    saveFile(locationOfTarget, tmpOfTarget, target, callback);
}

router.post('/update/storyboard', function (req, res) {
    updateStoryboard(req, function (err) {
        console.log(err);
        res.send('test');
    });
});

function updateStoryboard(req, callback) {
    var locationOfTarget = './users/' + req.session.user_id + '/' + req.body.app_name + '/';
    var target = locationOfTarget + req.body.app_name + '.json';
    fs.writeFile(target, req.body.storyboard_data, function (err) {
        callback(err);
        console.log('File write completed');
    });
}

function uploadImages(req, callback) {
    var maxFileSize = 1000000;
    var tmpOfTarget;
    var locationOfTarget = './users/' + req.session.user_id + '/' + req.body.app_name + '/';
    var target;

    async.series([
        function (callback) {
            if (isDefined(req.files.upload_images.name) || req.files.upload_images.length > 1)
                callback(null);
            else callback("파일이 없습니다");
        }, function (callback) {
            fs.readdir(locationOfTarget, function (err, files) {
                var dirSize = 0;
                if (err) callback(err);
                else {
                    async.each(files, function (file, callback) {
                        fs.stat(locationOfTarget + file, function (err, stats) {
                            dirSize += stats.size;
                            if (dirSize > maxFileSize * 100)
                                callback('서버에 파일 용량을 초과하였습니다.');
                            else callback(null);
                        });
                    }, function (err) {
                        callback(err);
                    });
                }
            });
        }, function (callback) {
            if (req.files.upload_images.length > 1) {
                async.each(req.files.upload_images, function (file, callback) {
                    if (file.size > maxFileSize)
                        callback('파일 크기가 너무 큽니다');
                    else callback(null);
                    console.log(file.size);
                }, function (err) {
                    callback(err);
                });
            } else {
                if (req.files.upload_images.size > maxFileSize)
                    callback('파일 크기가 너무 큽니다');
                else callback(null);
            }
        }, function (callback) {
            if (req.files.upload_images.length > 1) {
                async.each(req.files.upload_images, function (file, callback) {
                    var err = confirmJson(file.name.split('.'));
                    callback(err);
                }, function (err) {
                    callback(err);
                });
            } else {
                var err = confirmJson(req.files.upload_images.name.split('.'));
                callback(err);
            }
        }, function (callback) {
            if (req.files.upload_images.length > 1) {
                async.each(req.files.upload_images, function (file, callback) {
                    tmpOfTarget = file.path;
                    renameAndSaveFile(file.originalFilename, tmpOfTarget, function (err) {
                        callback(err);
                    });
                }, function (err) {
                    callback(err);
                });
            } else {
                tmpOfTarget = req.files.upload_images.path;
                renameAndSaveFile(req.files.upload_images.originalFilename, tmpOfTarget, function (err) {
                    callback(err);
                });
            }
        }
    ], function (err) {
        callback(err);
    });

    function confirmJson(ex) {
        var err = null;
        for (var i in ex) {
            if (ex[i] == 'json')
                err = 'json 파일은 업로드 할 수 없습니다';
        }
        return err;
    }

    function renameAndSaveFile(uploadFileName, tmpOfTarget, callback) {
        //locationOfTarget = './users/' + req.session.user_id + '/' + req.body.app_name + '/';
        target = locationOfTarget + uploadFileName;
        saveFile(locationOfTarget, tmpOfTarget, target, function (err) {
            callback(err);
        });
    }
}

router.post('/update/activity', function (req, res) {
    db.getActivityNumByUserIdAppNameActivityName(req, activityName, function (err, result) {
        if (err)console.error(err);
        else if (result[0]) {
            db.updateActivityXY(result[0].activity_num, req.body.x, req.body.y, function (err) {
                console.error(err);
            });
        }
    });
});


function saveFile(locationOfTarget, tmpOfTarget, target, callback) {
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
    db.getAppList(req, function (err, appList) {
        if (isDefined(appList)) {
            for (var i = 0; i < appList.length; i++) {
                if (req.body.app_name == appList[i].app_name) {
                    err = '같은 이름의 앱이 있습니다';
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
