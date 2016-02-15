var Bar = function (name) {
    var type = 'rect';
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
            .attr("x", function (d, i) {
                return i * (width / length) + 5;
            })
            .attr("y", function (d) {
                return height - calY(d, rY) - 10;
            })
            .attr("width", width / length - ((width / length) * 0.1))
            .attr("height", function (d) {
                return calY(d, rY) + 5;
            })
            .attr(this.graphName + "-data", function (d) {
                return d;
            });

        return this;
    }

    this.create = function () {
        var data = this.gData;

        this.svg.selectAll(type).data(data).enter().append(type);
        this.dataObject = this.svg.selectAll(type);

        this.setDataName();
        this.setDataNum();

        this.setActivityName();
        this.setActivityTime();

        return this;
    }

    this.setDataName = function () {
        if(this.gObjectName == null) return;

        var gObjectName = this.gObjectName;
        this.dataObject.data(gObjectName)
            .attr('id', function(d){
                return d;
            })
            .attr("object-name", function (d) {
                return d;
            })
            .attr("num", function (d, i) {
                return i;
            });
    }

    this.setDataNum = function () {
        if(this.gObjectNum == null) return;

        var gObjectNum = this.gObjectNum;
        this.dataObject.data(gObjectNum)
            .attr("object-num", function (d) {
                return d;
            });
    }

    this.setActivityName = function () {
        if(this.activityName == null) return;
        var activityName = this.activityName;

        this.dataObject.data(activityName)
            .attr("activity-name", function (d, i) {
                return d;
            })
            .attr("class", function (d, i) {
                return d + '-activity-name';
            });
    }

    this.setActivityTime = function () {
        if(this.activityTime == null) return;
        var activityTime = this.activityTime;
        this.dataObject.data(activityTime)
            .attr("activity-time", function (d) {
                return d;
            });
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

    this.getType = function () {
        return type;
    }
}

Bar.prototype = new GraphType();