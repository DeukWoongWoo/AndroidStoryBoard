var express = require('express');
var router = express.Router();
var db = require('../models/sign');

router.get('/', function(req, res, next){
    if (isSignin(req)){
        res.redirect('/home');
    } else {
        res.render('sign');
    }
});

router.post('/in',function(req, res){
    db.signin(req, function(result, message) {
        if (result == null) {
            res.send(message);
        }
        else {
            req.session.user_id = result;
            res.redirect('/home');
        }
        console.log(message);
    });

});

router.get('/out', function(req, res, next) {
    req.session.destroy(function(err) {
        res.redirect('/sign');
    });
});

router.get('/up', function(req, res, next) {
    /**
     *  회원가입 구현해야함.
     */
});

function isSignin(param){
    var user_id = param.session.user_id;
    if(user_id == '' || user_id == undefined)
        return false;
    else
        return true;
}

module.exports = router;
