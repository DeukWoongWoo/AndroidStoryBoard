var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('join', {join_result : ''});
    else
        res.redirect('/');
});

router.post('/', function(req, res) {
    join(req, res);
});

function join(req, res){
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

function isInputWrong(param){
    if(id() && name() && password() && confirmPassword())
        return false;
    else
        return true;

    function id() {
        return param.body.user_id != '';
    }
    function name(){
        return param.body.name != '';
    }
    function password() {
        return param.body.password != '';
    }
    function confirmPassword() {
        return param.body.password == param.body.confirm_password;
    }
}

module.exports = router;
