document.write("<script src='javascripts/storyboard/EObject.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/Button.js'></script>");

function Activity() {
    var name;
    var activityName;
    var width = 0, height = 0;
    var x = 0, y = 0;
    var objects = new Array();
    var numOfObjects = 0;
    var color = 'white';
    var own;

    this.create = function (target) {
        if (isDefined(target)) {
            this.svg = d3.select(target).append("svg").attr('class', 'activity');;//.attr('x', 300).attr('y', 300);
            this.svg.attr('onmousedown', 'startDrag(event, this)');
            own = this.svg.append('rect')
            this.update();

            return this;
        }
    }

    this.update = function () {
        this.svg.attr('x', x).attr('y', y).attr('id', activityName);
        own.attr("width", width)
            .attr("height", height)
            .attr('fill', color)
            .attr("stroke", "black")
            .attr('stroke-width', '5')
            .attr("activity-name", activityName);
        return this;
    }

    this.addObject = function (objectData) {
        var num = numOfObjects;
        objects[num] = new EObject();
        objects[num].target(this.svg);
        numOfObjects++;

        return objects[num];
    }

    this.object = function (index) {
        if (isDefined(index)) return objects[index];
        else return objects[numOfObjects - 1];
    }

    this.objects = function () {
        return objects;
    }

    this.width = function (arg) {
        if (isDefined(arg)) {
            width = arg;
            return this;
        } else return width;
    }

    this.height = function (arg) {
        if (isDefined(arg)) {
            height = arg;
            return this;
        } else return height;
    }

    this.x = function (arg) {
        if (isDefined(arg)) {
            x = arg;
            return this;
        } else return x;
    }

    this.y = function (arg) {
        if (isDefined(arg)) {
            y = arg;
            return this;
        } else return y;
    }

    this.color = function (arg) {
        if (isDefined(arg)) {
            color = arg;
            return this;
        } else return color;
    }

    this.name = function (arg) {
        if (isDefined(arg)) {
            name = arg;
            return this;
        } else return name;
    }

    this.activityName = function(arg){
        if (isDefined(arg)) {
            activityName = arg;
            return this;
        } else return activityName;
    }

    this.nextActivity = function(arg){
        if (isDefined(arg)) {
            nextActivities = arg;
            return this;
        } else return nextActivities;
    }
}

function isDefined(arg) {
    if (arg == undefined || arg == null)
        return false;
    else return true;
}