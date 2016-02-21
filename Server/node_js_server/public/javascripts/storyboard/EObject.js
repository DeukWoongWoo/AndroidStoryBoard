document.write("<script src='javascripts/storyboard/ObjectType/Button.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/CheckBox.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/RadioButton.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/Text.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/Image.js'></script>");

function EObject() {
    var text;
    var type;
    var target;

    this.create = function () {
        type.create(target);
        type.update();
        return this;
    }

    this.update = function () {
        type.update();

        return this;
    }

    this.type = function (objectType) {
        if (objectType == 'button') {
            type = new Button();
            return this;
        } else if (objectType == 'radio button') {
            type = new RadioButton();
            return this;
        } else if (objectType == 'checkbox') {
            type = new CheckBox();
            return this;
        } else if (objectType == 'text') {
            type = new Text();
            return this;
        }else if (objectType == 'image') {
            type = new Image();
            return this;
        }
        else return type;

    }

    this.target = function (arg) {
        target = arg;
    }

}

EObject.prototype = new Activity();
