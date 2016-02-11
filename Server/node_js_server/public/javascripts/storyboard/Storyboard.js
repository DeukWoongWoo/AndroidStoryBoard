document.write("<script src='javascripts/storyboard/Activity.js'></script>");

function Storyboard(){
    var appName;
    var activities = new Array();
    var numOfActivities = 0;
    var target;
    var storyboardData;

    this.targetDiv = function(arg){
        if(isDefined(arg)){
            target = arg;
            return this;
        }
        else return target;
    }

    this.addActivity = function(){
        var num = numOfActivities;
        activities[num] = new Activity();
        activities[num].width(300).height(300);
        activities[num].create(target);
        numOfActivities++;

        return activities[num];
    }

    this.activity = function(index){
        if(isDefined(index)) return activities[index];
        else return activities[numOfActivities - 1];
    }

    this.activities = function(){
        return activities;
    }

    this.addLine = function(pos){
        d3.select(target)
            .append('line')
            .attr('x1', pos.x1)
            .attr('y1', pos.y1)
            .attr('x2', pos.x2)
            .attr('y2', pos.y2)
            .attr('stroke', 'black');
    }

    this.storyboardData =function(arg){
        storyboardData = arg;
        console.log('storyboardData');
        console.log(storyboardData);
    }

    this.drawActivityObject = function() {
        for (var i in storyboardData.activity) {
            var activityData = storyboardData.activity[i];
            var activity = this.addActivity().activityName(activityData.name).x(activityData.x).y(activityData.y).width(activityData.width).height(activityData.height).update();
            for (var j in storyboardData.activity[i].object) {
                var objectData = storyboardData.activity[i].object[j];
                activity.addObject().type(objectData.type).name(objectData.name).activityName(activityData.name).width(objectData.width).height(objectData.height).x(objectData.x).y(objectData.y).color(objectData.color).create();
            }
        }
    }

    this.drawNextActivityLine = function(){
        //for (var i in storyboardData.activity) {
        //    var activityData = storyboardData.activity[i];
        //    var activityName = storyboardData.activity[i].name;
        //    for (var j in storyboardData.activity[i].object) {
        //        var objectData = storyboardData.activity[i].object[j];
        //        var nextActivity = objectData.next;
        //    }
        //}
    }
}

function isDefined(arg){
    if(arg == undefined || arg == null)
        return false;
    else return true;
}