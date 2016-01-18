var express = require('express');
var router = express.Router();



router.get('/', function(req, res, next) {
    if (isSignin(req)){
        res.render('home');
    } else {
        res.redirect('/sign');
    }
});

function isSignin(param){
    var user_id = param.session.user_id;
    if(user_id == '' || user_id == undefined)
        return false;
    else
        return true;
}

module.exports = router;