var express = require('express');
var router = express.Router();
var db = require('../models/db');
var ejs = require('ejs');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('sign');
    else
        res.redirect('/home');
});

router.post('/in',function(req, res){
    db.signin(req, function(result, message) {
        if (result == null) {
            res.render('sign');
            //res.render('sign', {title : 'a'});
            //res.send(message);
        }
        else {
            req.session.user_id = result;
            res.redirect('/home');
            console.log('user come : ' + result);
        }
    });
});

router.get('/out', function(req, res, next) {
    req.session.destroy(function(err) {
        res.redirect('/sign');
    });
});

router.post('/up', function(req, res, next) {
    checkInput(req, function(){
        db.checkIdFromDb(req, function(error){
            if(error){
                console.log('exist');
            }else
                db.addUser(req);
        });
    });
});

function checkInput(param, callback){
    if(id() && password() && confirmPassword()) {
        callback();
    }

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
