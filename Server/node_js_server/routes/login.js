var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next){
    if (req.session.user_id == undefined)
        res.render('login', {login_result:'', signup_result : ''});
    else
        res.redirect('/home');
});

router.post('/',function(req, res){
    db.login(req, function(user_id, message) {
        if (user_id == null) {
            res.render('login', {login_result:message});
        }
        else {
            req.session.user_id = user_id;
            res.redirect('/home');
            console.log('user come : ' + user_id);
        }
    });
});

module.exports = router;
