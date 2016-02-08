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

        return this;
    }

    this.activity = function(index){
        if(isDefined(index)) return activities[index];
        else return activities;
    }
}

function Activity(){
    var name;
    var width, height;
    var x=0, y=0;
    var objects = new Array();
    var numOfObjects = 0;
    var parentSvg;
    var color = 'white';

    this.create = function(target){
        if(isDefined(target)){
            this.svg = d3.select(target).append("svg");//.attr('x', 300).attr('y', 300);
            this.svg.attr('onmousedown', 'startDrag(event, this)');
            this.svg.append('rect').attr("width", width).attr("height", height).attr('x', 0).attr('y', 0).attr('fill', color);
            this.addObject();

            return this;
        }
    }

    this.addObject = function(){
        var num = numOfObjects;
        objects[num] = new EObject();
        objects[num].width(50).height(50);
        objects[num].x(100).y(100);
        objects[num].color('gray');
        objects[num].create(this.svg);
        numOfObjects++;

        return this;
    }

    this.eObject = function(index){
        if(isDefined(index)) return objects[index];
        else return objects;
    }

    this.width = function(arg){
        if(isDefined(arg)){
            width = arg;
            return this;
        }else return width;
    }

    this.height = function(arg){
        if(isDefined(arg)){
            height = arg;
            return this;
        }else return height;
    }

    this.x = function(arg){
        if(isDefined(arg)){
            x = arg;
            return this;
        }else return x;
    }

    this.y = function(arg){
        if(isDefined(arg)){
            y = arg;
            return this;
        }else return y;
    }

    this.color = function(arg){
        if(isDefined(arg)){
            color = arg;
            return this;
        }else return color;
    }
}

function EObject(){
    var color;
    var image;
    var text;
    var type;
    var nextActivities;

    this.create = function(target){
        target.append('rect').attr("width", this.width()).attr("height", this.height()).attr('x', this.x()).attr('y', this.y()).attr('fill', this.color());

        return this;
    }

};

EObject.prototype = new Activity();

function isDefined(arg){
    if(arg == undefined || arg == null)
        return false;
    else return true;
}