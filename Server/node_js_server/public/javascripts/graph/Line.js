var Line = function () {
    var type = 'line';
    this.draw = function () {
        var data = this.gData;
        var emptyDataColor = this.emptyDataColor;
        var dataColor = this.dataColor;
        var es = this.es;
        var width = this.width;
        var height = this.height;
        var rY = this.rY;
        var length = this.length;

        this.dataObject.data(data)
            .style("stroke", dataColor)
            .transition().duration(500).ease(es)
            .attr("x1", function (d, i) {
                return i * (width / length) + 5;
            })
            .attr("y1", function (d) {
                return height - ((d * rY) * 0.8 + 1) - 10;
            })
            .attr("x2", function (d, i) {
                return (i + 1) * (width / length) + 5;
            })
            .attr("y2", function (d, i) {
                if (i + 1 < length)
                    return height - ((data[i + 1] * rY) * 0.8 + 1) - 10;
                else return height - (d * rY * 0.8 + 1) - 10;
            });

        return this;
    }

    this.create = function () {
        var data = this.gData;
        this.svg.selectAll(type).data(data).enter().append(type);
        this.dataObject = this.svg.selectAll(type);

        return this;
    }

    this.animate = function (es) {
        var data = this.gData;
        var height = this.height;
        this.es = es ? es : this.es;
        this.dataObject.data(data).attr("y1", height / 2).attr("y2", height / 2);
        this.draw();

        return this;
    }

    this.setData = function (data) {
        this.data = data;
        this.length = data.length;
        this.maxY = Math.max.apply(null, data);
        this.rY = this.height / this.maxY;

        return this;
    }

    this.getType = function(){return type;}
}

Line.prototype = new GraphType();