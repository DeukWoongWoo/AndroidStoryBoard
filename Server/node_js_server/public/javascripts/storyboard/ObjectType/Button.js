function Button(){
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
            .attr('fill', this.color());

        return this;
    }
}

Button.prototype = new EObject();