var Bar = function () {
    var type = 'rect';
    this.draw = function () {
        var data = this.gData;
        var emptyDataColor = this.emptyDataColor;
        var dataColor = this.dataColor;
        var es = this.es;
        var width = this.width;
        var height = this.height;
        var rY = this.rY;
        var length = this.length;

        //console.log("bar draw : " + data);
        this.dataObject.data(data)
            .attr("fill", function (d) {
                if (d == 0) return emptyDataColor;
                else return dataColor;
            })
            .transition().duration(1000).ease(es)
            .attr("x", function (d, i) {
                return i * (width / length) + 5;
            })
            .attr("y", function (d) {
                return height - calY(d, rY) - 10;
            })
            .attr("width", width / length - ((width / length) * 0.1))
            .attr("height", function (d) {
                return calY(d, rY) + 5;
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
        this.es = es ? es : this.es;
        var height = this.height;

        this.dataObject.data(data).attr("y", height);
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

Bar.prototype = new GraphType();