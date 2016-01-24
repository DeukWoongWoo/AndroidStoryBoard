var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/join', function(req, res, next){
    if (isSession(req))
        res.render('join', {join_result : ''});
    else
        res.redirect('/');
});

router.post('/join', function(req, res) {
    join(req, res);
});

router.get('/login', function(req, res, next){
    if (isSession(req))
        res.render('login', {login_result:' '});
    else
        res.redirect('/');
});

router.post('/login',function(req, res){
    login(req, res);
});

router.get('/profile', function(req, res, next){
    if (isSession(req))
        res.redirect('/login');
    else
        res.render('profile', {user_id : req.session.user_id, update_result : ''});

});

router.post('/profile', function(req, res) {
    updateProfile(req, res);
});

function isSession(req) {
    return req.session.user_id == undefined;
}

function join(req, res){
    /**
     * TODO:아이디 길이 검사()
     * TODO:이름 길이 검사()
     * TODO:패스워드 길이 검사()
     * TODO:이메일 길이 검사()
     */
    if(isInputWrong(req)){
        res.render('join', {join_result : '입력을 확인해주세요'});
    }else{
        db.getUserId(req, function(user_id){
            if(user_id){
                res.render('join', {join_result : '이미 아이디가 있습니다'});
            }else{
                db.addUser(req);
                res.render('join', {join_result : '가입 되었습니다'});
            }
        });
    }
}

function login(req, res){
    db.getUserId(req, function(user_id){
        if(user_id == null){
            res.render('login', {login_result : '아이디가 없습니다'});
        }else{
            db.confirmPassword(req, function(success){
                if(success) {
                    req.session.user_id = user_id;
                    res.redirect('/');
                    console.log('user come : ' + user_id);
                }
                else{
                    res.render('login', {login_result : '비밀번호가 틀렸습니다'});
                }
            });
        }
    });
}

function updateProfile(req, res){
    /**
     * TODO:아이디 길이 검사()
     * TODO:이름 길이 검사()
     * TODO:패스워드 길이 검사()
     * TODO:이메일 길이 검사()
     */
    req.body.user_id = req.session.user_id;
    if(isInputWrong(req)){
        res.render('profile', {user_id : req.session.user_id, update_result : '입력을 확인해주세요'});
    }else{
        db.getUserId(req, function(user_id){
            if(user_id){
                db.updateUser(req);
                res.render('profile', {user_id : req.session.user_id, update_result : '수정 되었습니다'});
            }else{
                res.redirect('/');
            }
        });
    }
}

function isInputWrong(param){
    if(id() && name() && password() && confirmPassword())
        return false;
    else return true;

    function id() {
        return isDefined(param.body.user_id);
    }
    function name(){
        return isDefined(param.body.name);
    }
    function password() {
        return isDefined(param.body.password);
    }
    function confirmPassword() {
        return param.body.password == param.body.confirm_password;
    }
}

function isDefined(param){
    return param != '' && param != undefined;
}

module.exports = router;
