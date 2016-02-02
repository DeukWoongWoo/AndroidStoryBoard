function graph() {
    this.w = screen.width * 0.4;
    this.h = screen.height * 0.4;

    this.dataObject;
    this.svg;
    this.rY;
    this.maxY;
    this.barColor = 'teal';
}

graph.prototype.drawBarGraph = function (divId, data, frequency) {
    var length = frequency.length;

    var w = this.w;
    var h = this.h;
    var barColor = this.barColor;
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

    this.dataObject.data(data)
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

graph.prototype.drawLineGraph = function (divId, data, frequency) {
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
            return h/2;
        })
        .attr("x2", function (d, i) {
            return (i+1) * (w / length) + 5;
        })
        .attr("y2", function (d, i) {
            return h/2;
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
            if(i + 1 < length)
                return h - ((frequency[i + 1] * rY) * 0.8 + 1) - 10;
            else return h - (d * rY * 0.8 + 1) - 10;
        });

}

graph.prototype.updateLineGraph = function (data, frequency) {
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
            if(i + 1 < length)
                return h - ((frequency[i + 1] * rY) * 0.8 + 1) - 10;
            else return h - (d * rY * 0.8 + 1) - 10;
        });
}

graph.prototype.updateBarGraph = function (data, frequency) {
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

graph.prototype.setAnimation = function (target, data) {
    var ret = this.svg.selectAll(target).data(data).transition().duration(1000).ease("bounce-out");
    return ret;
}

graph.prototype.drawYAxis = function () {
    var h = this.h;
    var w = this.w;

    var maxY = this.maxY;
    var rY = this.rY;

    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, (maxY * rY * 0.8)]);
    var yAxis = d3.svg.axis();
    yAxis.scale(yScale).orient("right");
    this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + (h - ((maxY * rY) * 0.8 + 1) - 10) + ")")
        .call(yAxis);

    var xAxis = d3.svg.axis();
    var xScale = d3.scale.linear().domain([0, 0]).range([0, w]);
    xAxis.scale(xScale).orient("top");
    this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + h + ")")
        .call(xAxis);

}

graph.prototype.drawXAxis = function (data) {
    var h = this.h;
    var w = this.w;
    var maxX = data.length;

    var xAxis = d3.svg.axis();
    var xScale = d3.scale.linear().domain([0, maxX]).range([0, w]);
    xAxis.scale(xScale).orient("top");
    this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + h + ")")
        .call(xAxis);

}

graph.prototype.setColor = function (color) {
    this.barColor = color;
}
