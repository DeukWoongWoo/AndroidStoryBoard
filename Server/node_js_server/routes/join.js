var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('join', {join_result : ''});
    else
        res.redirect('/home');
});

router.post('/', function(req, res) {
    checkInput(req, function(error){
        if(error){
            res.render('join', {join_result : error});
        }else{
            db.checkIdFromDb(req, function(error) {
                if (error) {
                    res.render('join', {ljoin_result : error});
                } else {
                    db.addUser(req);
                    res.render('join', {join_result : '가입 되었습니다'});
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
