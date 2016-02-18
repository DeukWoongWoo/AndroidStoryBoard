function Button() {
    var btnImage;
    var btnText;
    var textSvg;
    var textSize;

    this.create = function (target) {
        own = target.append('rect');
        if (this.getImage())
            btnImage = target.append('image');
        if(this.getText())
            textSvg = target.append('svg');

        this.update();

        return this;
    }

    this.update = function () {

        if (this.getImage()) {
            btnImage.attr("xlink:href", '/image/a/a/ori2.jpg')
                .attr("width", this.width())
                .attr("height", this.height())
                .attr('x', this.x())
                .attr('y', this.y())
                .attr("preserveAspectRatio", "none").attr("object-name", this.name())
                .attr("class", this.activityName() + '-activity-name')
                .attr("id", this.name());
            own.attr('fill', 'none');
        } else
            own.attr('fill', this.color());

        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('x', this.x())
            .attr('y', this.y())
            .attr('stroke-width', 10)
            .attr("object-name", this.name())
            .attr("class", this.activityName() + '-activity-name')
            .attr("id", this.name());

        if (this.getText()) {
            textSvg
                .attr("width", this.width() - 20)
                .attr("height", this.height() - 20)
                .attr('x', this.x() + 10)
                .attr('y', this.y() + 10);

            var textLine = this.height() / this.textSize();
            var w = this.width();
            textSize = this.textSize();

            textSvg.append('path').attr('id', this.name() + '-text').attr('d', function () {
                var d = '';
                for (var i = 0; i < textLine; i++) {
                    d += 'M10,' + (textSize * i + 30) + ' H' + w + ' ';
                }
                return d;
            });
            btnText = textSvg.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
            btnText.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());
        }


        return this;
    }
}

Button.prototype = new EObject();