function RadioButton() {
    var circle;
    var btnText;
    var textSvg;
    var textSize;

    var circleSize = 64;

    this.create = function (target) {
        own = target.append('rect');
        circle = target.append('circle');

        if(this.getText())
            textSvg = target.append('svg');

        this.update();
    }

    this.update = function () {
        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('fill', this.color())
            .attr('x', this.x())
            .attr('y', this.y());

        var temp = (this.height() < this.width() ? this.height() : this.width());
        circle.attr("r", (this.width() < circleSize || this.height() < circleSize) ?  temp : circleSize/2)
            .attr('cx', this.x() + circleSize)
            .attr('cy', this.y() + this.height()/2)
            .attr('stroke', 'black')
            .attr('stroke-width', 10)
            .attr('fill', 'none')
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());

        if (this.getText()) {
            var t = this.width();
            this.width(t - 64);
            textSvg
                .attr("width", this.width() - 20)
                .attr("height", this.height() - 20)
                .attr('x', this.x() + circleSize/2 + circleSize)
                .attr('y', this.y() + circleSize/4)
                .attr("object-name", this.name())
                .attr("class", this.activityName() + '-activity-name')
                .attr("id", this.name());

            var textLine = this.getText().length / (this.width() / (this.textSize()));
            textLine = Math.round(textLine);
            var w = this.width();
            var h = this.height();
            textSize = this.textSize();

            //console.log('textLine');
            //console.log(textLine);
            //console.log(this.getText());
            //console.log(this.getText().length);
            textSvg.append('path').attr('id', this.name() + '-text').attr('d', function () {
                var d = '';
                for (var i = 0; i < textLine; i++) {
                    d += 'M0,' + ((h/(textLine+1))* (i+1)) + ' H' + w + ' ';
                }
                return d;
            });
            btnText = textSvg.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
            btnText.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());
        }
    }
}

RadioButton.prototype = new EObject();