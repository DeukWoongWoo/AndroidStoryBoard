function TextEdit() {
    var line;
    var textSvg;

    this.create = function (target) {
        own = target.append('rect');
        line = target.append('line');
        console.log("TextEdit test");
        console.log(line);

        //if(this.getText())
        //    textSvg = target.append('svg');

        this.update();
    }

    this.update = function () {
        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('fill', this.color())
            .attr('x', this.x())
            .attr('y', this.y());
            //.attr("object-name", this.name())
            //.attr("class", this.activityName() + '-activity-name')
            //.attr("id", this.name());

        line.attr('x1', this.x() + 32)
            .attr('y1', this.y() + this.height() - 32)
            .attr('x2', this.x() + this.width() - 32)
            .attr('y2', this.y() + this.height() - 32)
            .attr('stroke', 'black')
            .attr('stroke-width', 10);


        //if (this.getText()) {
        //    textSvg
        //        .attr("width", this.width() - 50)
        //        .attr("height", this.height() - 50)
        //        .attr('x', this.x() + 64 + 10)
        //        .attr('y', this.y() + 10);
        //
        //    var textLine = this.height() / this.textSize();
        //    var w = this.width();
        //    textSize = this.textSize();
        //
        //    textSvg.append('path').attr('id', this.name() + '-text').attr('d', function () {
        //        var d = '';
        //        for (var i = 0; i < textLine; i++) {
        //            d += 'M10,' + (textSize * i) + ' H' + w + ' ';
        //        }
        //        return d;
        //    });
        //    btnText = textSvg.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
        //    btnText.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());
        //}
    }
}

TextEdit.prototype = new EObject();