document.write("<script src='javascripts/storyboard/EObject.js'></script>");

function Activity() {
    var name;
    var activityName;
    var width = 0, height = 0;
    var x = 0, y = 0;
    var objects = new Array();
    var numOfObjects = 0;
    var color = 'white';
    var own;
    var image = null;
    var imageRoute = null;

    var textSize = 10;
    var textColor = 'black';
    var textContent = '';

    var backImage = null;

    this.create = function (target) {
        if (isDefined(target)) {
            this.svg = d3.select(target).append("svg").attr('class', 'activity');//.attr('x', 300).attr('y', 300);
            this.svg.attr('onmousedown', 'startDrag(event, this)');
            own = this.svg.append('rect');

            //if (this.getImage())
            //    backImage = this.svg.append('image');

            this.update();

            return this;
        }
    }

    this.update = function () {
        this.svg.attr('x', x).attr('y', y).attr('id', activityName);
        own.attr("width", 768*2)
            .attr("height", 1280*2)
            .attr('fill', color)
            .attr("stroke", "black")
            .attr('stroke-width', '5')
            .attr("activity-name", activityName);

        if (this.getImage()) {
            if(backImage == null){
                this.svg.append('image').attr("xlink:href", this.imageRoute() + this.getImage())
                    .attr("width", this.width())
                    .attr("height", this.height())
                    .attr("preserveAspectRatio", "none");;
            }else{
                backImage
                    .attr("xlink:href", this.imageRoute() + this.getImage())
                    .attr("width", this.width())
                    .attr("height", this.height())
                    .attr("preserveAspectRatio", "none");
            }

        }

        return this;
    }

    this.addObject = function () {
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

    this.setImage = function(arg){
            image = arg;
            return this;
    }
    this.getImage = function(){
        return image;
    }

    this.imageRoute = function(arg){
        if (isDefined(arg)) {
            imageRoute = arg;
            return this;
        } else return imageRoute;
    }

    this.textSize = function(arg){
        if (isDefined(arg)) {
            textSize = arg;
            return this;
        } else return textSize;
    }

    this.textColor = function(arg){
        if (isDefined(arg)) {
            textColor = arg;
            return this;
        } else return textColor;
    }

    this.setText = function(arg){
            textContent = arg;
            return this;
    }

    this.getText = function(){
        return textContent;
    }
}

function isDefined(arg) {
    if (arg == undefined || arg == null)
        return false;
    else return true;
}