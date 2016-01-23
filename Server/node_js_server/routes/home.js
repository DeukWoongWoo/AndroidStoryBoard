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

router.post('/registerapp', function(req, res, next){
    /**
     * TODO:파일 이름 길이 검사()
     * TODO:파일이름 중복검사 ()
     * TODO:파일 업로드 유무 검사()
     */
    res.redirect('/');
});

module.exports = router;