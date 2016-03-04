function TextView() {
    var textElement;
    var textSize;

    this.create = function (target) {
        own = target.append('svg');
        this.update();

        return this;
    }

    this.update = function () {

        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('x', this.x())
            .attr('y', this.y());

        var t = this.width();
        this.width(t - 64);

        var textLine = this.getText().length / (this.width() / (this.textSize()));
        textLine = Math.round(textLine);
        var w = this.width();
        var h = this.height();
        textSize = this.textSize();

        own.append('path').attr('id', this.name() + '-text').attr('d', function () {
            var d = '';
            for (var i = 0; i < textLine; i++) {
                d += 'M0,' + ((h/(textLine+1))* (i+1)) + ' H' + w + ' ';
            }
            return d;
        });
        textElement = own.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
        textElement.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());

        //var textLine = this.height() / this.textSize();
        //var w = this.width();
        //textSize = this.textSize();
        //
        //own.append('path').attr('id', this.name() + '-text').attr('d', function () {
        //    var d = '';
        //    for (var i = 0; i < textLine; i++) {
        //        d += 'M10,' + (textSize * i + 30) + ' H' + w + ' ';
        //    }
        //    return d;
        //});
        //textElement = own.append('text').attr('fill', this.textColor()).attr('font-size', this.textSize());
        //textElement.append('textPath').attr('xlink:href', '#' + this.name() + '-text').text(this.getText());

        return this;
    }
}

TextView.prototype = new EObject();