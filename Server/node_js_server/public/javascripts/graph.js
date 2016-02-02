function Graph() {
    this.svgWidth = screen.width * 0.4;
    this.svgHeight = screen.height * 0.4;
    this.dataColor = 'teal';
    this.emptyDataColor = 'black';

    this.ease = 'cubic';
    this.graphType = 'rect';
    this.selectTarget = null;
    this.rY = 1;
    this.maxY = null;
    this.data = null;
    this.dataName = null;

    //this.dataObject = null;
    this.svg = null;
    this.yAxis = null;
    this.xAxis = null;
}

Graph.prototype.graphData = function (data) {
    this.data = data;
    this.maxY = Math.max.apply(null, data);
    return this;
}

Graph.prototype.graphDataName = function (dataName) {
    this.dataName = dataName;
    //this.dataObject.data(dataName)
    //    .attr("class", function (d) {
    //        return d + 'ABS-object-name'
    //    })
    //    .attr("number", function (d, i) {
    //        return i;
    //    });
    return this;
}

Graph.prototype.setWidth = function (width) {
    this.svgWidth = width;
    return this;
}

Graph.prototype.setHeight = function (height) {
    this.svgHeight = height;
    return this;
}

Graph.prototype.setDataColor = function (color) {
    this.dataColor = color;
    return this;
}

Graph.prototype.setType = function (type) {
    this.graphType = type;
    return this;
}

Graph.prototype.location = function (selectTarget) {
    this.selectTarget = selectTarget;
    return this;
}

Graph.prototype.setEase = function (es) {
    this.ease = es;
    return this;
}

Graph.prototype.update = function (data) {
    this.graphData(data);
    this.drawBar();
    this.drawYAxis();
    this.drawXAxis();
}

Graph.prototype.draw = function () {
    this.createGraph();

    this.createBar();
    this.drawBar();
    this.animateBar("bounce-in");

    this.createYAxis();
    this.drawYAxis();
    this.animateYAxis("bounce");

    this.createXAxis();
    this.drawXAxis();
    this.animateXAxis("bounce");
}

Graph.prototype.createGraph = function () {
    var selectTarget = this.selectTarget;
    var width = this.svgWidth;
    var height = this.svgHeight;

    $(selectTarget).empty();
    this.svg = d3.select(selectTarget).append("svg").attr("width", width).attr("height", height);
}

Graph.prototype.createBar = function () {
    var type = this.graphType;
    var data = this.data;
    this.svg.selectAll(type).data(data).enter().append(type);
    this.dataObject = this.svg.selectAll(type);
}

Graph.prototype.drawBar = function () {
    var data = this.data;
    var rY = this.rY = this.svgHeight / this.maxY;
    var length = data.length;

    var dataColor = this.dataColor;
    var emptyDataColor = this.emptyDataColor;
    var width = this.svgWidth;
    var height = this.svgHeight;
    var es = this.ease;

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
}

Graph.prototype.animateBar = function (es) {
    var height = this.svgHeight;
    var data = this.data;
    this.ease = es ? es : this.ease;

    this.dataObject.data(data).attr("y", height);
    this.drawBar();
}

Graph.prototype.createYAxis = function () {
    var rY = this.rY = this.svgHeight / this.maxY;
    var height = this.svgHeight;
    var maxY = this.maxY;

    this.yAxis = this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + (height - calY(maxY, rY) - 10) + ")");
}

Graph.prototype.drawYAxis = function () {
    var height = this.svgHeight;
    var maxY = this.maxY;
    var rY = this.rY;
    var es = this.ease;

    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, calY(maxY, rY) - 1]);
    var yAxis = d3.svg.axis();
    yAxis.scale(yScale).orient("right");

    this.yAxis.transition().duration(500).ease(es)
        .attr("transform", "translate(0," + (height - calY(maxY, rY) - 10) + ")")
        .call(yAxis);
}

Graph.prototype.animateYAxis = function (es) {
    var height = this.svgHeight;
    this.ease = es ? es : this.ease;

    this.yAxis.attr("transform", "translate(0," + height / 2 + ")");
    this.drawYAxis();
}

Graph.prototype.createXAxis = function () {
    var height = this.svgHeight;

    this.xAxis = this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + height + ")");
}

Graph.prototype.drawXAxis = function () {
    var height = this.svgHeight;
    var width = this.svgWidth;
    var es = this.ease;
    var xAxis = d3.svg.axis();
    var xScale = d3.scale.linear().domain([0, 0]).range([0, width]);

    xAxis.scale(xScale).orient("top");
    this.xAxis.transition().duration(500).ease(es)
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis);
}

Graph.prototype.animateXAxis = function (es) {
    var height = this.svgHeight;
    var width = this.svgWidth;
    this.ease = es ? es : this.ease;

    this.xAxis.attr("transform", "translate(" + (-width) +  ", " + height + ")");
    this.drawXAxis();
}

function calY(d, rY) {
    return ((d * rY) * 0.8 + 1);
}


Graph.prototype.setHightlight = function () {
    var data = this.data;
    var dataName = this.dataName;
    $('.graph').children('svg').children('rect').mouseover(function () {
        var className = '.' + $(this).attr("class");

        $('rect').css("fill", "");
        $(className).css("fill", "gold");

        var rectInfo = $('.rect-info').children('div');
        var number = $(this).attr("number");

        $('.rect-info').css("visibility", "visible");
        rectInfo.children('#rect-object-name').text(dataName[number]);
        rectInfo.children('#rect-use-frequency').text(data[number]);
    }).mouseout(function () {

    });
}

/**
 *
 */


Graph.prototype.drawBarGraph__ = function (divId, data, frequency) {
    var w = this.w;
    var h = this.h;
    var barColor = this.barColor;

    var length = frequency.length;
    var maxY = Math.max.apply(null, frequency);
    var rY = h / maxY;
    this.rY = rY;
    this.maxY = maxY;

    this.svg = d3.select("#" + divId).append("svg").attr("width", w).attr("height", h);
    this.dataObject = this.svg.selectAll("rect").data(frequency).enter().append("rect");

    this.dataObject
        .attr("x", function (d, i) {
            return i * (w / length) + 5;
        })
        .attr("y", function (d) {
            return h;// - ((d * rY) * 0.8 + 1) - 10;
        })
        .attr("width", w / length - ((w / length) * 0.1))
        .attr("height", function (d) {
            return (d * rY) * 0.8 + 5;
        })
        .attr("fill", function (d) {
            if (d == 0) return 'black';
            else return barColor;
        });

    this.dataObject.data(data)//data
        .attr("class", function (d) {
            return d.object_name + 'ABS-object-name'
        })
        .attr("number", function (d, i) {
            return i;
        });

    this.setAnimation('rect', frequency).attr("y", function (d) {
        return h - ((d * rY) * 0.8 + 1) - 10;
    });

    $('.graph').children('svg').children('rect').mouseover(function () {
        var className = '.' + $(this).attr("class");

        $('rect').css("fill", "");
        $(className).css("fill", "gold");

        var rectInfo = $('.rect-info').children('div');
        var number = $(this).attr("number");

        $('.rect-info').css("visibility", "visible");
        rectInfo.children('#rect-object-name').text(data[number].object_name);
        rectInfo.children('#rect-use-frequency').text(data[number].object_frequency);
        rectInfo.children('#rect-err-frequency').text(data[number].error_frequency);
        rectInfo.children('#rect-activity-name').text(data[number].activity_name);
        rectInfo.children('#rect-activity-time').text(data[number].total_time);

    }).mouseout(function () {

    });

    return this;
}

Graph.prototype.drawLineGraph = function (divId, data, frequency) {
    var length = frequency.length;
    var w = this.w;
    var h = this.h;
    var barColor = this.barColor;
    var maxY = Math.max.apply(null, frequency);
    var rY = h / maxY;
    this.rY = rY;
    this.maxY = maxY;

    this.svg = d3.select("#" + divId).append("svg").attr("width", w).attr("height", h);
    //this.dataObject = this.svg.selectAll("circle").data(frequency).enter().append("circle");
    //
    //this.dataObject
    //    .attr("cx", function (d, i) {
    //        return i * (w / length) + 5;
    //    }).attr("cy", h/2).attr("r", 0)
    //    .attr("fill", function (d) {
    //        if (d == 0) return 'black';
    //        else return barColor;
    //    });
    //
    //this.setAnimation('circle', frequency).ease('cubic')
    //    .attr("cx", function (d, i) {
    //        return i * (w / length) + 5;
    //    })
    //    .attr("cy", function (d) {
    //        return h - ((d * rY) * 0.8 + 1) - 10;
    //    })
    //    .attr("r", 2);

    this.svg.selectAll("line").data(frequency).enter().append("line")
        .attr("x1", function (d, i) {
            return i * (w / length) + 5;
        })
        .attr("y1", function (d) {
            return h / 2;
        })
        .attr("x2", function (d, i) {
            return (i + 1) * (w / length) + 5;
        })
        .attr("y2", function (d, i) {
            return h / 2;
        })
        .style("stroke", "rgb(6,120,155)");

    this.setAnimation('line', frequency)//.ease('elastic-in')
        .attr("x1", function (d, i) {
            return i * (w / length) + 5;
        })
        .attr("y1", function (d) {
            return h - ((d * rY) * 0.8 + 1) - 10;
        })
        .attr("x2", function (d, i) {
            return (i + 1) * (w / length) + 5;
        })
        .attr("y2", function (d, i) {
            if (i + 1 < length)
                return h - ((frequency[i + 1] * rY) * 0.8 + 1) - 10;
            else return h - (d * rY * 0.8 + 1) - 10;
        });

}

Graph.prototype.updateLineGraph = function (data, frequency) {
    var length = frequency.length;
    var h = this.h;
    var w = this.w;
    var maxY = Math.max.apply(null, frequency);
    var rY = h / maxY;
    this.rY = rY;
    this.maxY = maxY;

    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, (maxY * rY * 0.8)]);
    var yAxis = d3.svg.axis();
    yAxis.scale(yScale).orient("right");
    this.svg.select("g").transition().duration(1000).ease("circle")
        .attr("class", "axis")
        .attr("transform", "translate(0," + (h - ((maxY * rY) * 0.8 + 1) - 10) + ")")
        .call(yAxis);

    //this.setAnimation('circle', frequency).ease('circle-in')
    //    .attr("cx", function (d, i) {
    //        return i * (w / length) + 5;
    //    }).attr("cy", function (d) {
    //    return h - ((d * rY) * 0.8 + 1) - 10;
    //});

    this.setAnimation('line', frequency).ease('bounce-out')
        .attr("x1", function (d, i) {
            return i * (w / length) + 5;
        })
        .attr("y1", function (d) {
            return h - ((d * rY) * 0.8 + 1) - 10;
        })
        .attr("x2", function (d, i) {
            return (i + 1) * (w / length) + 5;
        })
        .attr("y2", function (d, i) {
            if (i + 1 < length)
                return h - ((frequency[i + 1] * rY) * 0.8 + 1) - 10;
            else return h - (d * rY * 0.8 + 1) - 10;
        });
}

Graph.prototype.updateBarGraph = function (data, frequency) {
    var h = this.h;
    var maxY = Math.max.apply(null, frequency);
    var rY = h / maxY;
    this.rY = rY;
    this.maxY = maxY;

    this.setAnimation('rect', frequency)
        .attr("y", function (d) {
            return h - ((d * rY) * 0.8 + 1) - 10;
        })
        .attr("height", function (d) {
            return (d * rY) * 0.8 + 5;
        });

    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, (maxY * rY * 0.8)]);
    var yAxis = d3.svg.axis();
    yAxis.scale(yScale).orient("right");
    this.svg.select("g").transition().duration(1000).ease("circle")
        .attr("class", "axis")
        .attr("transform", "translate(0," + (h - ((maxY * rY) * 0.8 + 1) - 10) + ")")
        .call(yAxis);
}

Graph.prototype.setAnimation = function (target, data) {
    var ret = this.svg.selectAll(target).data(data).transition().duration(1000).ease("bounce-out");
    return ret;
}
//
//Graph.prototype.drawYAxis = function () {
//    var h = this.h;
//
//    var maxY = this.maxY;
//    var rY = this.rY;
//
//    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, (maxY * rY * 0.8)]);
//    var yAxis = d3.svg.axis();
//    yAxis.scale(yScale).orient("right");
//    this.svg.append("g")
//        .attr("class", "axis")
//        .attr("transform", "translate(0," + (h - ((maxY * rY) * 0.8 + 1) - 10) + ")")
//        .call(yAxis);
//
//    return this;
//}
//
//Graph.prototype.drawXAxis = function () {
//    var h = this.h;
//    var w = this.w;
//
//    var xAxis = d3.svg.axis();
//    var xScale = d3.scale.linear().domain([0, 0]).range([0, w]);
//    xAxis.scale(xScale).orient("top");
//    this.svg.append("g")
//        .attr("class", "axis")
//        .attr("transform", "translate(0," + h + ")")
//        .call(xAxis);
//
//}

Graph.prototype.setColor = function (color) {
    this.barColor = color;
}
