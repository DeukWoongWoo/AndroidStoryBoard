function EObject(){
    var color;
    var image;
    var text;
    var type;
    var nextActivities;

    this.create = function(target){
        own = target.append('rect');
        target.append('svg').append('text').attr('x', 100).attr('y', 100).attr('fill', 'blue').attr('transform', 'scale(2, 2)').text('gf');
        //this.text('test');
        this.update();
        return this;
    }

    this.update = function(){
        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('x', this.x())
            .attr('y', this.y())
            .attr('fill', this.color());
    }

    this.text = function(arg){
        //own.append('text').attr('x', 0).attr('y', 0).attr('fill', 'red').text(arg);
        own.append('text').attr('x', 100).attr('y', 100).attr('fill', 'blue').attr('stroke', 'red').text('text');
    }
};

EObject.prototype = new Activity();
