var express = require('express');
var session = ('express-session');
var router = express.Router();
var db = require('./../models/db');

/* GET home page. */
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

//
//var mysql = require('mysql');
//
//var connection = mysql.createConnection({
//    host    :'localhost',
//    port : 3306,
//    user : 'root',
//    password : '1234',
//    database:'storyboard'
//});
//
//connection.connect(function(err) {
//    if (err) {
//        console.error('mysql connection error');
//        console.error(err);
//        throw err;
//    }
//});
//
///* GET home page. */
//router.get('/', function(req, res, next) {
//    //res.send('respond with a resource2');
//    res.render('mydb', { title: 'mydb'});
//});
//
//
////insert uesr_info
//router.post('/user',function(req, res){
//    var user = {'user_id':req.body.user_id,
//        'name':req.body.name,
//        'password':req.body.password,
//        'email':req.body.email};
//
//    var query = connection.query('insert into user_info set ?', user, function(err,result){
//        if (err) {
//            console.error(err);
//            throw err;
//        }
//        console.log(query);
//        res.render('mydb', { title: 'success'});
//    });
//});
//
////insert app_use_info
//router.post('/app/use',function(req, res){
//
//    var user = {'user_id':req.body.user_id,
//        'name':req.body.name,
//        'password':req.body.password,
//        'email':req.body.email};
//
//    var query = connection.query('insert into user_info set ?', user, function(err,result){
//        if (err) {
//            console.error(err);
//            throw err;
//        }
//        console.log(query);
//        res.send(200,'success');
//    });
//});

