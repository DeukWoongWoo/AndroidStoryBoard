function RadioButton() {
    var inCircle;
    this.create = function (target) {
        own = target.append('circle');
        inCircle = target.append('circle');
        //target.append('svg').append('text').attr('x', 100).attr('y', 100).attr('fill', 'blue').attr('transform', 'scale(2, 2)');
        this.update();
    }

    this.update = function () {
        own.attr("r", this.width() / 2)
            .attr('cx', this.x() + (this.width()/2))
            .attr('cy', this.y() + (this.height()/2))
            .attr('stroke', this.color())
            .attr('stroke-width', this.width() / 10)
            .attr('fill', 'white')
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());

        inCircle.attr("r", this.width() / 4)
            .attr('cx', this.x() + (this.width()/2))
            .attr('cy', this.y() + (this.height()/2))
            .attr('stroke', this.color())
            .attr('fill', this.color())
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());
    }
}

RadioButton.prototype = new EObject();