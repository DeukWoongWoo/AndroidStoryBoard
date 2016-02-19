var express = require('express');
var router = express.Router();
var db = require('./../models/db');

router.get('/', function (req, res, next) {
    res.render('getpost', {title: 'getpost'});
});

router.post('/user', function (req, res) {
    db.addUser(req);
    res.send('success');
});

router.post('/app', function (req, res) {
    db.addApp(req);
    res.send('success');
});

router.post('/app/use', function (req, res) {
    db.addAppUse(req);
    res.send('success');
});

router.post('/app/activity', function (req, res) {
    db.addActivity(req, function (err) {
        if (err)res.send(err);
        else res.send('success');
    });
});

router.post('/app/activity/use', function (req, res) {
    console.log(req.body);
    db.addActivityUse(req);
    res.send('success');
});

router.post('/app/activity/object', function (req, res) {
    db.addObject(req);
    res.send('success');
});

router.post('/app/activity/object/use', function (req, res) {
    console.log(req.body);
    db.addObjectUse(req);
    res.send('success');
});

router.post('/app/activity/object/error/use', function (req, res) {
    console.log(req.body);
    db.addErrorUse(req);
    addErrorLog(req);
    res.send('success');
});

function addErrorLog(req) {
    var fs = require('fs');
    var loc = './users/' + req.body.user_id + '/' + req.body.app_name + '/';
    var file = loc + 'error.log';
    fs.open(file, 'a', function (err, fd) {
        if (err) console.log(err);
        else {
            fs.appendFile(file, req.body.error_log + '\n', function (err) {
                if (err)  console.log(err);
            });
        }
    });
}

module.exports = router;