document.write("<script src='javascripts/storyboard/Activity.js'></script>");

function Storyboard() {
    var appName;
    var userId;
    var activities = new Array();
    var numOfActivities = 0;
    var target;
    var storyboardData;

    var count = 0;
    var line;

    this.targetDiv = function (arg) {
        if (isDefined(arg)) {
            target = arg;

            return this;
        }
        else return target;
    }

    this.addBackBoard = function(){
        document.getElementById(target.replace(/\#/g,'')).innerHTML = "<rect id='storyboard-background' x='-5000' y='-5000' width='10000' height='10000' style='fill: #8BC5B7; stroke: black;  stroke-width: 100px;' onmousedown='startDrag(event)'/>";
    }

    this.addActivity = function () {
        var num = numOfActivities;
        activities[num] = new Activity();
        //768*1280
        activities[num].width(768).height(1280);
        activities[num].create(target);
        numOfActivities++;

        return activities[num];
    }

    this.allActivitys = function(){
        return activities;
    }

    this.activity = function (index) {
        if (isDefined(index)) return activities[index];
        else return activities[numOfActivities - 1];
    }

    this.storyboardData = function (arg) {
        storyboardData = arg;
    }

    this.setUserIdAppName = function(id, app){
        userId = id;
        appName = app;
    }

    this.drawActivityObject = function () {
        for (var i in storyboardData.activity) {
            var activityData = storyboardData.activity[i];
            var activity = this.addActivity().activityName(activityData.name).x(activityData.x).y(activityData.y).update();
                //.width(activityData.width).height(activityData.height).update();
            for (var j in storyboardData.activity[i].object) {
                var objectData = storyboardData.activity[i].object[j];

                var object = activity.addObject().type(objectData.type).name(objectData.name).activityName(activityData.name).width(objectData.width).height(objectData.height).x(objectData.x).y(objectData.y).color(objectData.color);

                if(isDefined(objectData.image)){
                    object.imageRoute('/image/' + userId + '/' +  appName + '/' );
                    object.setImage(objectData.image);
                }else
                    object.setImage(null);

                if(isDefined(objectData.text)){
                    object.textColor(objectData.textColor ? objectData.textColor : 'black');
                    object.textSize(objectData.textSize ? objectData.textSize : 10);
                    object.setText(objectData.text);
                }else
                    object.setText(null);

                object.create();
            }
        }
    }

    this.removeActivityObject = function(){
        $('svg.activity').remove();
    }

    this.initLine = function () {
        line = new Array();
        for (var i in storyboardData.activity) {
            var activityData = storyboardData.activity[i];
            for (var j in storyboardData.activity[i].object) {
                var objectData = storyboardData.activity[i].object[j];
                if (isDefined(objectData.next)) {
                    line[count] = new Array(6);
                    line[count]['start'] = activityData.name;
                    line[count]['end'] = objectData.next;
                    count++;
                }
            }
        }

    }

    this.drawNextActivityLine = function () {
        var activity = $('svg.activity');
        for (var i in line) {
            for (var j = 0; j < activity.length; j++) {
                if (line[i]['start'] == activity[j].getAttribute('id')) {
                    line[i]['startX'] = activity[j].getAttribute('x') * 1 + 150;
                    line[i]['startY'] = activity[j].getAttribute('y') * 1 + 250;
                } else if (line[i]['end'] == activity[j].getAttribute('id')) {
                    line[i]['endX'] = activity[j].getAttribute('x') * 1 + 150;
                    line[i]['endY'] = activity[j].getAttribute('y') * 1 + 250;
                }
            }
        }

        for (var i in line) {
            d3.select(target).append("line")
                .attr('x1', line[i]['startX']).attr('y1', line[i]['startY'])
                .attr('x2', line[i]['endX']).attr('y2', line[i]['endY'])
                .attr('class', line[i]['start'] + ' ' + line[i]['end'])
                .attr('start', line[i]['start'])
                .attr('end', line[i]['end'])
                .attr("stroke", 'black')
                .attr("stroke-width", 10);
        }
    }

    this.removeStoryboard = function(){
        $(target).children().remove();
    }
}

function isDefined(arg) {
    if (arg == undefined || arg == null)
        return false;
    else return true;
}