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
        document.getElementById(target.replace(/\#/g,'')).innerHTML = "<rect id='storyboard-background' x='-50000' y='-50000' width='100000' height='100000' style='fill: white; stroke: white;  stroke-width: 100px;' onmousedown='startDrag(event)'/>";
    }

    this.addActivity = function () {
        var num = numOfActivities;
        activities[num] = new Activity();
        //768*1280
        activities[num].width(768*2).height(1280*2);
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
            var activity = this.addActivity().activityName(activityData.name).x(activityData.x*2).y(activityData.y*2).width(activityData.width*2).height(activityData.height*2);
            activity.color(activityData.color);
            if(isDefined(activityData.image)){
                activity.imageRoute('/image/' + userId + '/' +  appName + '/' );
                activity.setImage(activityData.image);
            }else
                activity.setImage(null);

            activity.update();
                //.width(activityData.width).height(activityData.height).update();
            for (var j in storyboardData.activity[i].object) {
                var objectData = storyboardData.activity[i].object[j];
                if(!isDefined(objectData.color)){
                    if(objectData.type == "Button")
                        objectData.color = 'grey';
                    else objectData.color = 'none';
                }

                var object = activity.addObject().type(objectData.type)
                    .activityName(activityData.name).width(objectData.width*2).height(objectData.height*2)
                    .x(objectData.x*2).y(objectData.y*2).color(objectData.color);
                object.name(objectData.name);

                if(isDefined(objectData.image)){
                    object.imageRoute('/image/' + userId + '/' +  appName + '/' );
                    object.setImage(objectData.image);
                }else
                    object.setImage(null);

                if(isDefined(objectData.text)){
                    object.textColor(objectData.textColor ? objectData.textColor : 'black');
                    object.textSize(objectData.textSize ? objectData.textSize*2 : 5*2);
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
            if(isDefined(storyboardData.activity[i].next)){
                //console.log("initLine");
                //console.log(storyboardData.activity[i].name);
                //console.log(storyboardData.activity[i].next);
                //console.log(storyboardData.activity[i].next.constructor.toString().indexOf("Array"));
                //if(storyboardData.activity[i].next[0].length == 1 && storyboardData.activity[i].next.length )

                if(storyboardData.activity[i].next.constructor.toString().indexOf("Array") > -1){
                    for(var j in storyboardData.activity[i].next) {
                        //console.log(storyboardData.activity[i].next[j]);
                        line[count] = new Array(6);
                        line[count]['start'] = storyboardData.activity[i].name;
                        line[count]['end'] = storyboardData.activity[i].next[j];
                        count++;
                    }
                }else {
                    line[count] = new Array(6);
                    line[count]['start'] = storyboardData.activity[i].name;
                    line[count]['end'] = storyboardData.activity[i].next;
                    count++;
                }

                //for(var j in storyboardData.activity[i].next){
                //    //console.log(storyboardData.activity[i].next);
                //    //console.log(storyboardData.activity[i].next[j]);
                //    line[count] = new Array(6);
                //    line[count]['start'] = storyboardData.activity[i].name;
                //    line[count]['end'] = storyboardData.activity[i].next[j];
                //    count++;
                //
                //
                //    //line[count] = new Array(6);
                //    //line[count]['start'] = storyboardData.activity[i].name;
                //    //line[count]['end'] = storyboardData.activity[i].next;
                //    //count++;
                //}
            }
        }
    }

    this.drawNextActivityLine = function () {
        var activity = $('svg.activity');
        for (var i in line) {
            for (var j = 0; j < activity.length; j++) {//(768*2).height(1280*2);
                if (line[i]['start'] == activity[j].getAttribute('id')) {
                    line[i]['startX'] = activity[j].getAttribute('x') * 1 + 768;
                    line[i]['startY'] = activity[j].getAttribute('y') * 1 + 1280;
                } else if (line[i]['end'] == activity[j].getAttribute('id')) {
                    line[i]['endX'] = activity[j].getAttribute('x') * 1 + 768;
                    line[i]['endY'] = activity[j].getAttribute('y') * 1 + 1280;
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