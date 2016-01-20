var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('login', {login_result:' '});
    else
        res.redirect('/');
});

router.post('/',function(req, res){
    login(req, res);
});

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

module.exports = router;
