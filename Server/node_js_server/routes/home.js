var express = require('express');
var router = express.Router();
var db = require('../models/db');

router.get('/', function(req, res, next) {
    if (req.session.user_id == undefined)
        res.redirect('/login');
    else{
        db.getAppList(req, function(appList){
            renderAppPage(res, req, appList);
        });
    }
});

router.get('/logout', function(req, res, next) {
    logout(req, res);
});

router.post('/registerapp', function(req, res, next){
    /**
     * TODO:파일 이름 길이 검사()
     * TODO:파일이름 중복검사 ()
     * TODO:파일 업로드 유무 검사()
     * 파일을 데이터베이스에 저장을 할지 웹 서버에 저장을 할지
     */
    res.redirect('/');
});

function renderAppPage(res, req, appList) {
    res.render('home', {
        user_id: req.session.user_id,
        app: appList
    });
}

function logout(req, res) {
    req.session.destroy(function (err) {
        res.redirect('/');
    });
}

module.exports = router;
