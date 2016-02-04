var express = require('express');
var router = express.Router();
var db = require('../models/db');
var async = require('async');

router.get('/join', function (req, res, next) {
    isLogin(req, function (err) {
        if (err) res.render('join', {join_result: ''});
        else  res.redirect('/');
    });
});

router.post('/join', function (req, res) {
    join(req, res);
});

router.get('/login', function (req, res, next) {
    isLogin(req, function (err) {
        if (err) res.render('login', {login_result: ' '});
        else res.redirect('/');
    });
});

router.post('/login', function (req, res) {
    login(req, res);
});

router.get('/profile', function (req, res, next) {
    async.waterfall([
        function (callback) {
            req.body.user_id = req.session.user_id;
            db.getUserInfo(req, function (err, result) {
                callback(err, result);
            });
        }
    ], function (err, result) {
        if (!err && result) {
            isLogin(req, function (err) {
                if (err) res.redirect('/login');
                else res.render('profile', {
                    user_id: req.session.user_id,
                    name: result.name,
                    password: result.password,
                    email: result.email,
                    update_result: ''
                });
            });
        }
    });
});

router.post('/profile', function (req, res) {
    updateProfile(req, res);
});

function isLogin(req, callback) {
    if (isDefined(req.session.user_id)) callback(null);
    else callback('is not login');
}

function join(req, res) {
    isInputWrong(req, function (err) {
        if (err) res.render('join', {join_result: err});
        else {
            db.getUserId(req, function (err, user_id) {
                if (user_id) res.render('join', {join_result: 'ID is already exist'});
                else {
                    db.addUser(req);
                    res.render('join', {join_result: 'success'});
                }
            });
        }
    });
}

function login(req, res) {
    db.getUserId(req, function (err, user_id) {
        if (isUndefined(user_id)) {
            res.render('login', {login_result: 'ID is not exist'});
        } else {
            db.confirmPassword(req, function (err, success) {
                if (success) {
                    req.session.user_id = user_id;
                    res.redirect('/');
                    console.log('user come : ' + user_id);
                } else res.render('login', {login_result: 'Password is wrong'});
            });
        }
    });
}

function updateProfile(req, res) {
    req.body.user_id = req.session.user_id;
    isInputWrong(req, function (err) {
        if (err) {
            res.render('profile', {user_id: req.session.user_id, update_result: err});
        } else {
            db.getUserId(req, function (err) {
                if (err) res.redirect('/');
                else {
                    db.updateUser(req);
                    res.render('profile', {user_id: req.session.user_id, update_result: 'success'});
                }
            });
        }
    });
}


function isInputWrong(param, callback) {
    async.series([
        function (callback) {
            callback(isWrong(param.body.user_id, 'ID'));
        }, function (callback) {
            callback(isWrong(param.body.name, 'Name'));
        }, function (callback) {
            callback(isWrong(param.body.password, 'Password'));
        }, function (callback) {
            callback(confirmPassword());
        }
    ], function (err) {
        callback(err);
    });

    function isWrong(data, stringOfData) {
        if (isUndefined(data)) return stringOfData + ' is Empty';
        else if (isGreaterThan(data.length, 10)) return stringOfData + ' is long';
        else null;
    }

    function confirmPassword() {
        if (param.body.password == param.body.confirm_password)
            return null;
        else return 'Password is wrong';
    }
}

function isGreaterThan(a, b) {
    return a > b;
}

function isDefined(param) {
    return param != '' && param != undefined;
}

function isUndefined(param) {
    return !isDefined(param);
}

module.exports = router;
