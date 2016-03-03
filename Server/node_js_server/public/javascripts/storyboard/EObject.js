document.write("<script src='javascripts/storyboard/ObjectType/Button.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/CheckBox.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/RadioButton.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/TextView.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/Image.js'></script>");
document.write("<script src='javascripts/storyboard/ObjectType/TextEdit.js'></script>");

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
        if (objectType == 'Button') {
            type = new Button();
            return this;
        } else if (objectType == 'ImageButton') {
            type = new Button();
            return this;
        } else if (objectType == 'RadioButton') {
            type = new RadioButton();
            return this;
        } else if (objectType == 'CheckBox') {
            type = new CheckBox();
            return this;
        } else if (objectType == 'TextView') {
            type = new TextView();
            return this;
        }else if (objectType == 'ImageView') {
            type = new Image();
            return this;
        }else if (objectType == 'RelativeLayout') {
            type = new Image();
            return this;
        }else if (objectType == 'LinearLayout') {
            type = new Image();
            return this;
        }else if(objectType == 'TextEdit'){
            type = new TextEdit();
            return this;
        }
        else return type;

    }

    this.target = function (arg) {
        target = arg;
    }

}

EObject.prototype = new Activity();
