function Graph() {
    this.svgWidth = screen.width * 0.4;
    this.svgHeight = screen.height * 0.4;
    this.dataColor = 'teal';
    this.emptyDataColor = 'black';

    this.ease = 'bounce-in';
    this.graphType = 'rect';
    this.selectTarget = null;
    this.rY = 1;
    this.maxY = null;
    this.data = null;
    this.dataName = null;

    this.dataObject = null;
    this.svg = null;
    this.yAxis = null;
    this.xAxis = null;

    this.gType = null;

    this.xAxisData = {
        orient: 'top',
        position: {x: this.svgWidth/2, y: this.svgHeight/2},
        range: {start: -this.svgWidth, end: this.svgWidth},
        domain: {start: -this.svgWidth, end: this.svgWidth}
    };


    this.yAxisData = {
        orient: 'right',
        position: {x: this.svgWidth/2, y: this.svgHeight/2},
        range: {start: -this.svgHeight, end: this.svgHeight},
        domain: {start: -this.svgHeight, end: this.svgHeight}
        //position: {x: 0, y: height - calY(maxY, rY) - 10},
        //range: {start: 0, end: calY(maxY, rY) - 1},
        //domain: {start: maxY, end: 0}
    };
}

Graph.prototype = {

    setData: function (data) {
        this.data = data;
        this.maxY = Math.max.apply(null, data);

        return this;
    },

    graphDataName: function (dataName) {
        this.dataName = dataName;
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
        this.maxY = Math.max.apply(null, this.data);
        this.rY = this.svgHeight / this.maxY;
        //var tmp = ;
        this.length = this.data.length;

        this.package = {
            gData: this.data,
            dataColor: this.dataColor,
            emptyDataColor: this.emptyDataColor,
            width: this.svgWidth,
            height: this.svgHeight,
            es: this.ease,
            svg: this.svg,
            rY: this.rY,
            maxY: this.maxY,
            length: this.length
        }
        gType.setGraphData(this.package);

        return gType;
    },

    update: function (data) {
        //this.setEase("circle");
        this.setGraphType(this.gType);
        this.updateAxis();

        return this.gType.draw();
    },

    draw: function () {
        this.gType.draw()
        this.drawAxis();

        return this.gType;
    },

    create: function () {
        this.gType.create().draw().animate();
        this.axis().animateAxis();

        return this.gType;
    },

    animate: function () {
        this.animateAxis();
        return this.gType.animate('bounce');
    },

    createGraph: function () {
        var selectTarget = this.selectTarget;
        var width = this.svgWidth;
        var height = this.svgHeight;

        $(selectTarget).empty();
        this.svg = d3.select(selectTarget).append("svg").attr("width", width).attr("height", height);

        return this;
    },

    setXAxisData : function(axisData){
        this.xAxisData = axisData;
    },

    setYAxisData : function(axisData){
        this.yAxisData = axisData;
    },

    axis : function(){
        this.xAxis = this.createAxis();
        this.yAxis = this.createAxis();
        this.setAxisData();

        return this;
    },

    updateAxis : function(){
        this.setAxisData();

        this.drawAxis(this.xAxis, this.xAxisData);
        this.drawAxis(this.yAxis, this.yAxisData);
    },

    setAxisData : function(){
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
        ////축 데이터
        var scale = d3.scale.linear().domain([axisData.domain.start, axisData.domain.end]).range([axisData.range.start, axisData.range.end]);
        axis.transition().duration(500).ease(this.ease)
            .attr("transform", "translate(" + axisData.position.x + "," + axisData.position.y + ")")
            .call(d3.svg.axis().scale(scale).orient(axisData.orient));
    },

    animateAxis : function(){
        this.middleAxis();

        this.drawAxis(this.xAxis, this.xAxisData);
        this.drawAxis(this.yAxis, this.yAxisData);
    },

    middleAxis : function(){
        this.drawAxisWithoutAni(this.xAxis, {
            orient: 'top',
            position: {x: this.svgWidth/2, y: this.svgHeight/2},
            range: {start: -this.svgWidth, end: this.svgWidth},
            domain: {start: -this.svgWidth, end: this.svgWidth}
        });
        this.drawAxisWithoutAni(this.yAxis, {
            orient: 'right',
            position: {x: this.svgWidth/2, y: this.svgHeight/2},
            range: {start: -this.svgHeight, end: this.svgHeight},
            domain: {start: -this.svgHeight, end: this.svgHeight}
        });
    },

    drawAxisWithoutAni : function (axis, axisData) {
        var scale = d3.scale.linear().domain([axisData.domain.start, axisData.domain.end]).range([axisData.range.start, axisData.range.end]);
        axis.attr("transform", "translate(" + axisData.position.x + "," + axisData.position.y + ")")
            .call(d3.svg.axis().scale(scale).orient(axisData.orient));
    }

}


//var height = this.svgHeight;
//var rY = this.rY = this.svgHeight / this.maxY;
//var maxY = this.maxY;
//
//this.xAxis = this.svg.append("g")
//    .attr("class", "axis")
//    .attr("transform", "translate(0," + height + ")");
//
//this.yAxis = this.svg.append("g")
//    .attr("class", "axis")
//    .attr("transform", "translate(0," + (height - calY(maxY, rY) - 10) + ")");
//
//return axis;

//createXAxis: function () {
//    var height = this.svgHeight;
//
//    this.xAxis = this.svg.append("g")
//        .attr("class", "axis")
//        .attr("transform", "translate(0," + height + ")");
//},
//
//createYAxis: function () {
//    var rY = this.rY = this.svgHeight / this.maxY;
//    var height = this.svgHeight;
//    var maxY = this.maxY;
//
//    this.yAxis = this.svg.append("g")
//        .attr("class", "axis")
//        .attr("transform", "translate(0," + (height - calY(maxY, rY) - 10) + ")");
//},
//
//drawYAxis: function () {
//    var height = this.svgHeight;
//    var maxY = this.maxY;
//    var rY = this.rY;
//    var es = this.ease;
//
//    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, calY(maxY, rY) - 1]);
//    var yAxis = d3.svg.axis();
//    yAxis.scale(yScale).orient("right");
//
//    this.yAxis.transition().duration(1000).ease(es)
//        .attr("transform", "translate(0," + (height - calY(maxY, rY) - 10) + ")")
//        .call(yAxis);
//},
//
//animateYAxis: function (es) {
//    var height = this.svgHeight;
//    this.ease = es ? es : this.ease;
//
//    this.yAxis.attr("transform", "translate(0," + height / 2 + ")");
//    this.drawYAxis();
//},
//
//createXAxis: function () {
//    var height = this.svgHeight;
//
//    this.xAxis = this.svg.append("g")
//        .attr("class", "axis")
//        .attr("transform", "translate(0," + height + ")");
//},
//
//drawXAxis: function () {
//    var height = this.svgHeight;
//    var width = this.svgWidth;
//    var es = this.ease;
//    var xAxis = d3.svg.axis();
//    var xScale = d3.scale.linear().domain([0, 0]).range([0, width]);
//
//    xAxis.scale(xScale).orient("top");
//    this.xAxis.transition().duration(500).ease(es)
//        .attr("transform", "translate(0," + height + ")")
//        .call(xAxis);
//},


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
        this.dataColor = package.dataColor;

        //this.length = this.gData.length;
        //this.maxY = Math.max.apply(null, package.gData);
        //this.rY = this.height / this.maxY;

        this.length = package.length;
        this.maxY = package.maxY;//Math.max.apply(null, package.gData);
        this.rY = package.rY;//this.height / this.maxY;
    }
}


/**
 *
 */

//
//Graph.prototype.setHighlight = function () {
//    var data = this.data;
//    var dataName = this.dataName;
//    $('.graph').children('svg').children('rect').mouseover(function () {
//        var className = '.' + $(this).attr("class");
//
//        $('rect').css("fill", "");
//        $(className).css("fill", "gold");
//
//        var rectInfo = $('.rect-info').children('div');
//        var number = $(this).attr("number");
//
//        $('.rect-info').css("visibility", "visible");
//        rectInfo.children('#rect-object-name').text(dataName[number]);
//        rectInfo.children('#rect-use-frequency').text(data[number]);
//    }).mouseout(function () {
//
//    });
//}
//
//Graph.prototype.highlight = function (divId, data, frequency) {
//
//    $('.graph').children('svg').children('rect').mouseover(function () {
//        var className = '.' + $(this).attr("class");
//
//        $('rect').css("fill", "");
//        $(className).css("fill", "gold");
//
//        var rectInfo = $('.rect-info').children('div');
//        var number = $(this).attr("number");
//
//        $('.rect-info').css("visibility", "visible");
//        rectInfo.children('#rect-object-name').text(data[number].object_name);
//        rectInfo.children('#rect-use-frequency').text(data[number].object_frequency);
//        rectInfo.children('#rect-err-frequency').text(data[number].error_frequency);
//        rectInfo.children('#rect-activity-name').text(data[number].activity_name);
//        rectInfo.children('#rect-activity-time').text(data[number].total_time);
//
//    }).mouseout(function () {
//
//    });
//
//    return this;
//}