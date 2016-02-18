function RadioButton() {
    var circle;
    var btnText;
    var textSvg;
    var textSize;

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
        circle.attr("r", (this.width() < 64 || this.height() < 64) ?  temp : 32)
            .attr('cx', this.x() + 32)
            .attr('cy', this.y() + this.height()/2)
            .attr('stroke', 'black')
            .attr('stroke-width', 10)
            .attr('fill', 'none')
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());

        if (this.getText()) {
            textSvg
                .attr("width", this.width() - 50)
                .attr("height", this.height() - 50)
                .attr('x', this.x() + 64 + 10)
                .attr('y', this.y() + 10);

            var textLine = this.height() / this.textSize();
            var w = this.width();
            textSize = this.textSize();

            textSvg.append('path').attr('id', this.name() + '-text').attr('d', function () {
                var d = '';
                for (var i = 0; i < textLine; i++) {
                    d += 'M10,' + (textSize * i) + ' H' + w + ' ';
                }
                return d;
            });
            btnText = textSvg.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
            btnText.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());
        }
    }
}

RadioButton.prototype = new EObject();