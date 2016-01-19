var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next){
    console.log('session : ' + req.session);
    console.log('session.user_id : ' + req.session.user_id);
    if (req.session.user_id == undefined)
        res.redirect('/login');
    else
        res.render('profile', {user_id : req.session.user_id, update_result : ''});

});

router.post('/', function(req, res) {
    updateProfile(req, res);
});

function updateProfile(req, res){
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
    else
        return true;

    function id() {
        return param.body.user_id != '' || param.body.user_id != undefined;
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
