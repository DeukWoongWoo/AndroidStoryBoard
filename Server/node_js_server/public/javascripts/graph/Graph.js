function Graph() {
    this.svgWidth = screen.width * 0.4;
    this.svgHeight = screen.height * 0.4;
    this.dataColor = 'teal';
    this.emptyDataColor = 'black';

    this.ease = 'bounce-in';
    this.graphType = 'rect';
    this.selectTarget = null;
    this.rY = 1;
    this.maxY = 1;
    this.data = null;
    //this.dataName = null;
    this.objectName = null;
    this.objectNum = null;

    this.dataObject = null;
    this.svg = null;
    this.yAxis = null;
    this.xAxis = null;

    this.gType = null;

    this.xAxisData = {
        orient: 'top',
        position: {x: this.svgWidth / 2, y: this.svgHeight / 2},
        range: {start: -this.svgWidth, end: this.svgWidth},
        domain: {start: -this.svgWidth, end: this.svgWidth}
    };


    this.yAxisData = {
        orient: 'right',
        position: {x: this.svgWidth / 2, y: this.svgHeight / 2},
        range: {start: -this.svgHeight, end: this.svgHeight},
        domain: {start: -this.svgHeight, end: this.svgHeight}
        //position: {x: 0, y: height - calY(maxY, rY) - 10},
        //range: {start: 0, end: calY(maxY, rY) - 1},
        //domain: {start: maxY, end: 0}
    };
}

Graph.prototype = {

    graphData: function (data) {
        if(isDefined(data[0])){
            this.data = data;
            this.maxY = Math.max.apply(null, data) == 0 ? 1 : Math.max.apply(null, data);
        }

        return this;
    },

    graphDataName: function (objectName) {
        this.objectName = objectName;
        return this;
    },

    graphDataNum: function (objectNum) {
        this.objectNum = objectNum;
        return this;
    },

    graphActivityName: function (activityName) {
        this.activityName = activityName;
        return this;
    },

    graphActivityTime: function (activityTime) {
        this.activityTime = activityTime;
        return this;
    },

    setWidth: function (width) {
        this.svgWidth = width;
        return this;
    },

    setHeight: function (height) {
        this.svgHeight = height;
        return this;
    },

    setDataColor: function (color) {
        this.dataColor = color;
        return this;
    },

    location: function (selectTarget) {
        $(this.selectTarget).empty();
        this.selectTarget = selectTarget;
        return this;
    },

    setEase: function (es) {
        this.ease = es;
        return this;
    },

    setGraphType: function (gType) {
        this.gType = gType;
        this.maxY = Math.max.apply(null, this.data) == 0 ? 1 : Math.max.apply(null, this.data);
        this.rY = this.svgHeight / this.maxY;
        this.length = this.data.length;
        this.package = {
            gData: this.data,
            gObjectName: this.objectName,
            gObjectNum: this.objectNum,
            activityName: this.activityName,
            activityTime: this.activityTime,
            dataColor: this.dataColor,
            emptyDataColor: this.emptyDataColor,
            width: this.svgWidth,
            height: this.svgHeight,
            es: this.ease,
            svg: this.svg,
            rY: this.rY == Infinity ? 1 : this.rY,
            maxY: this.maxY,
            length: this.length
        }
        if (isDefined(this.package))
            gType.setGraphData(this.package);
        return this;
    },

    update: function (data) {
        //this.setEase("circle");
        this.setGraphType(this.gType);
        this.updateAxis();

        return this.gType.draw();
    },

    draw: function () {
        this.gType.draw();
        this.drawAxis();

        return this.gType;
    },

    create: function () {
        this.gType.create().draw().animate(this.ease);
        this.axis().animateAxis();
        return this;
    },

    animate: function () {
        this.animateAxis();
        return this.gType.animate(this.ease);
    },

    createGraph: function () {
        var selectTarget = this.selectTarget;
        var width = this.svgWidth;
        var height = this.svgHeight;

        $(selectTarget).empty();
        this.svg = d3.select(selectTarget).append("svg").attr("width", width).attr("height", height);

        return this;
    },

    setXAxisData: function (axisData) {
        this.xAxisData = axisData;
    },

    setYAxisData: function (axisData) {
        this.yAxisData = axisData;
    },

    axis: function () {
        this.xAxis = this.createAxis();
        this.yAxis = this.createAxis();
        this.setAxisData();

        return this;
    },

    updateAxis: function () {
        this.setAxisData();

        this.drawAxis(this.xAxis, this.xAxisData);
        this.drawAxis(this.yAxis, this.yAxisData);
    },

    setAxisData: function () {
        this.setXAxisData({
            orient: 'top',
            position: {x: 0, y: this.svgHeight},
            range: {start: 0, end: this.svgWidth},
            domain: {start: 0, end: 0}
        });

        this.setYAxisData({
            orient: 'right',
            position: {x: 0, y: this.svgHeight - calY(this.maxY, this.rY) - 10},
            range: {start: 0, end: calY(this.maxY, this.rY) - 1},
            domain: {start: this.maxY, end: 0}
        });
    },

    createAxis: function () {
        return this.svg.append("g").attr("class", "axis");
    },

    drawAxis: function (axis, axisData) {
        var scale = d3.scale.linear().domain([axisData.domain.start, axisData.domain.end]).range([axisData.range.start, axisData.range.end]);
        axis.transition().duration(500).ease(this.ease)
            .attr("transform", "translate(" + axisData.position.x + "," + axisData.position.y + ")")
            .call(d3.svg.axis().scale(scale).orient(axisData.orient));
    },

    animateAxis: function () {
        this.middleAxis();

        this.drawAxis(this.xAxis, this.xAxisData);
        this.drawAxis(this.yAxis, this.yAxisData);
    },

    middleAxis: function () {
        this.drawAxisWithoutAni(this.xAxis, {
            orient: 'top',
            position: {x: this.svgWidth / 2, y: this.svgHeight / 2},
            range: {start: -this.svgWidth, end: this.svgWidth},
            domain: {start: -this.svgWidth, end: this.svgWidth}
        });
        this.drawAxisWithoutAni(this.yAxis, {
            orient: 'right',
            position: {x: this.svgWidth / 2, y: this.svgHeight / 2},
            range: {start: -this.svgHeight, end: this.svgHeight},
            domain: {start: -this.svgHeight, end: this.svgHeight}
        });
    },

    drawAxisWithoutAni: function (axis, axisData) {
        var scale = d3.scale.linear().domain([axisData.domain.start, axisData.domain.end]).range([axisData.range.start, axisData.range.end]);
        axis.attr("transform", "translate(" + axisData.position.x + "," + axisData.position.y + ")")
            .call(d3.svg.axis().scale(scale).orient(axisData.orient));
    }

}

function calY(d, rY) {
    return ((d * rY) * 0.8 + 1);
}

var GraphType = function () {
    this.setGraphData = function (package) {
        this.emptyDataColor = package.emptyDataColor;
        this.width = package.width;
        this.height = package.height;
        this.es = package.es;
        this.svg = package.svg;
        this.type = package.type;

        this.gData = package.gData;
        this.gObjectName = package.gObjectName;
        this.gObjectNum = package.gObjectNum;
        this.activityTime = package.activityTime;
        this.activityName = package.activityName;
        this.dataColor = package.dataColor;

        this.length = package.length;
        this.maxY = package.maxY;//Math.max.apply(null, package.gData);
        this.rY = package.rY;//this.height / this.maxY;
    }
}