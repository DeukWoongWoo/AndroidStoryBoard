document.write("<script src='javascripts/storyboard/ObjectType/Button.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/RadioButton.js'></script>");

function EObject(){
    var color;
    var image;
    var text;
    var type;
    var nextActivities;
    var target;

    this.create = function(){
        type.create(target);
        type.update();
        return this;
    }

    this.update = function(){
        type.update();

        return this;
    }

    this.type = function(objectType){
        if(objectType == 'button') {
            type = new Button();
            return this;
        }
        else if(objectType == 'radio button'){
            type = new RadioButton();
            return this;
        }
        else return type;

    }

    this.target = function(arg){
        target = arg;
    }

    this.text = function(arg){
        own.append('text').attr('x', 100).attr('y', 100).attr('fill', 'blue').attr('stroke', 'red').text('text');
    }


};

EObject.prototype = new Activity();
