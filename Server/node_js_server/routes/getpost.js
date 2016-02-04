var express = require('express');
var router = express.Router();
var db = require('./../models/db');

router.get('/', function(req, res, next) {
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
    db.addActivity(req, function(err){
        if(err)res.send(err);
        else res.send('success');
    });

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
/**
 * json 파싱
 */
router.post('/test', function(req, res){
    console.log('req');
    var user_id = req.session.user_id;
    var activity = req.body.activity;

    activity.forEach(function(activity){
        console.log('activity : ' + activity.name);
        var object = activity.object;
        object.forEach(function(object){
            console.log('object : ' + object.name);
            console.log('w : ' + object.size.w);
            console.log('h : ' + object.size.h);
            console.log('image : ' + object.image);
            console.log('color : ' + object.color);
            console.log('x : ' + object.location.x);
            console.log('y : ' + object.location.y);
        });
    });
    res.send('success');
});

module.exports = router;