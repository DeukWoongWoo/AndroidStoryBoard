var express = require('express');
var router = express.Router();
var db = require('./../models/db');

router.get('/', function(req, res, next) {
    //res.send('respond with a resource2');
    res.render('getpost', { title: 'getpost'});
});

router.post('/user',function(req, res){
    db.addUser(req);
    res.send('success');
});

router.post('/app',function(req, res){
    db.addApp(req);
    res.send('success');
});

router.post('/app/use',function(req, res){
    db.addAppUse(req);
    res.send('success');
});

router.post('/app/activity',function(req, res){
    db.addActivity(req);
    res.send('success');
});

router.post('/app/activity/use',function(req, res){
    db.addActivityUse(req);
    res.send('success');
});

router.post('/app/activity/object',function(req, res){
    db.addObject(req);
    res.send('success');
});

router.post('/app/activity/object/use',function(req, res){
    db.addObjectUse(req);
    res.send('success');
});

router.post('/app/activity/object/error/use',function(req, res){
    db.addErrorUse(req);
    res.send('success');
});


module.exports = router;