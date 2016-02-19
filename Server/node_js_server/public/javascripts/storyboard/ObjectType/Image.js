function Image() {
    var btnImage;
    var btnText;
    var textSvg;
    var textSize;

    this.create = function (target) {
        own = target.append('rect');
        if (this.getImage())
            btnImage = target.append('image');

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
                .attr("preserveAspectRatio", "none");
            own.attr('fill', 'none');
        } else
            own.attr('fill', this.color());

        own.attr("width", this.width())
            .attr("height", this.height())
            .attr('x', this.x())
            .attr('y', this.y());


        return this;
    }
}

Image.prototype = new EObject();