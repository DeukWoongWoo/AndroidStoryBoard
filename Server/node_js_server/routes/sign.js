var express = require('express');
var router = express.Router();
var db = require('../models/db');
var ejs = require('ejs');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('sign', {signin_result:'', signup_result : ''});
    else
        res.redirect('/home');
});

router.post('/in',function(req, res){
    db.signin(req, function(user_id, message) {
        if (user_id == null) {
            res.render('sign', {signin_result:message, signup_result : ''});
            //res.render('sign', {title : 'a'});
            //res.send(message);
        }
        else {
            req.session.user_id = user_id;
            res.redirect('/home');
            console.log('user come : ' + user_id);
        }
    });
});

router.get('/out', function(req, res, next) {
    req.session.destroy(function(err) {
        res.redirect('/sign');
    });
});

router.post('/up', function(req, res) {
    checkInput(req, function(error){
        if(error){
            res.render('sign', {signin_result:'', signup_result : error});
        }else{
            db.checkIdFromDb(req, function(error) {
                if (error) {
                    res.render('sign', {signin_result: '', signup_result: error});
                } else {
                    db.addUser(req);
                    res.render('sign', {signin_result: '', signup_result: '가입 되었습니다'});
                }
            });
        }
    });
});

function checkInput(param, callback){
    if(id() && password() && confirmPassword()) {
        callback();
    }else
        callback('입력을 확인하세요');

    function id() {
        return param.body.user_id != '';
    }
    function password() {
        return param.body.password != '';
    }
    function confirmPassword() {
        return param.body.password == param.body.confirm_password;
    }
}

module.exports = router;
