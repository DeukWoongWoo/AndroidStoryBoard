var Circle =  function (name) {
    var type = 'circle';
    this.graphName = name;

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
            .attr("fill", function (d) {
                if (d == 0) return emptyDataColor;
                else return dataColor;
            })
            .transition().duration(1000).ease(es)
            .attr("cx", function (d, i) {
                return i * (width / length) + 5;
            })
            .attr("cy", function (d) {
                return height - calY(d, rY) - 10;
            })
            .attr("r", function(){
                return width/(length*7);
            })
            .attr(this.graphName + "-data", function(d){return d;});

        return this;
    }

    this.create = function () {
        var data = this.gData;

        this.svg.selectAll(type).data(data).enter().append(type);
        this.dataObject = this.svg.selectAll(type);

        //this.setDataName();
        //this.setDataNum();

        return this;
    }

    this.setDataName = function () {
        var gObjectName = this.gObjectName;
        this.dataObject.data(gObjectName)
            .attr("class", function (d, i) {
                return d + '-object-name';
            })
            .attr("object-name", function (d, i) {
                return d ;
            })
            .attr("num", function (d, i) {
                return i;
            });
    }

    this.setDataNum = function () {
        var gObjectNum = this.gObjectNum;
        this.dataObject.data(gObjectNum)
            .attr("object-num", function (d, i) {
                return d;
            });
    }

    this.animate = function (es) {
        var data = this.gData;
        this.es = es ? es : this.es;
        var height = this.height;

        this.dataObject.data(data).attr("cy", height/2);
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

    this.getType = function () {
        return type;
    }


}

Circle.prototype = new GraphType();