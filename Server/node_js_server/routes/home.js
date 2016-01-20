var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    console.log('session : ' + req.session.user_id);
    if (req.session.user_id == undefined)
        res.redirect('/login');
    else
        res.render('home', {user_id:req.session.user_id});
});

router.get('/logout', function(req, res, next) {
    req.session.destroy(function(err) {
        res.redirect('/');
    });
});

module.exports = router;