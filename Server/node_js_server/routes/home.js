var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    if (req.session.user_id == undefined)
        res.redirect('/login');
    else
        res.render('home', {user_id:req.session.user_id});
});

router.get('/logout', function(req, res, next) {
    req.session.destroy(function(err) {
        res.redirect('/home');
    });
});

module.exports = router;