function CheckBox(){
    this.create = function(target){
        own = target.append('rect');
        this.update();

        return this;
    }

    this.update = function(){
        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('x', this.x())
            .attr('y', this.y())
            .attr('stroke', this.color())
            .attr('stroke-width', this.width() / 10)
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());

        return this;
    }
}

CheckBox.prototype = new EObject();