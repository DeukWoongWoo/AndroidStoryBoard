document.write("<script src='javascripts/storyboard/Activity.js'></script>");

function Storyboard(){
    var appName;
    var activities = new Array();
    var numOfActivities = 0;
    var target;

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
}

function isDefined(arg){
    if(arg == undefined || arg == null)
        return false;
    else return true;
}