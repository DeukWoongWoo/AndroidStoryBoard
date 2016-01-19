var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    if (req.session.user_id == undefined)
        res.redirect('/sign');
    else
        res.render('home', {user_id:req.session.user_id});
});

module.exports = router;