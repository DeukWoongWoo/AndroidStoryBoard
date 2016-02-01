function graph() {
    this.w = screen.width * 0.4;
    this.h = screen.height * 0.4;

    this.rects;
    this.svg;
    this.rY;
    this.maxY;
    this.barColor = 'teal';
}

graph.prototype.drawGraph = function (divId, data, frequency) {
    var length = frequency.length;

    var w = this.w;
    var h = this.h;
    var barColor = this.barColor;
    var maxY = Math.max.apply(null, frequency);
    var rY = h / maxY;
    this.rY = rY;
    this.maxY = maxY;

    this.svg = d3.select("#" + divId).append("svg").attr("width", w).attr("height", h);
    this.rects = this.svg.selectAll("rect").data(frequency).enter().append("rect").attr("fill", function (d) {
        if (d == 0) return 'black';
        else return barColor;
    });

    this.rects.attr("x", function (d, i) {
            return i * (w / length) + 5;
        })
        .attr("y", function (d) {
            return h - ((d * rY) * 0.8 + 1) - 10;
        })
        .attr("width", w / length - ((w / length) * 0.1))
        .attr("height", function (d) {
            return (d * rY) * 0.8 + 5;
        });

    this.rects.data(data)
        .attr("class", function (d) {
            return d.object_name + 'ABS-object-name'
        })
        .attr("number", function (d, i) {
            return i;
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
}

graph.prototype.drawAxis = function(){
    var h = this.h;
    var w = this.w;

    var maxY = this.maxY;
    var rY = this.rY;
    //((maxY * rY) * 0.8 + 1)

    console.log;
    //h - ;
    //rY * 0.8 + 5
    //(maxY - 5)
    //(d * rY) * 0.8 + 5;
    //(d * rY) * 0.8 + 5
    var yScale = d3.scale.linear().domain([maxY, 0]).range([0, (maxY*rY*0.8)]);
    var yAxis = d3.svg.axis();
    yAxis.scale(yScale).orient("right");
    this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + (h - ((maxY * rY) * 0.8 + 1) - 10) + ")")
        .call(yAxis);

    var xAxis = d3.svg.axis();
    var xScale = d3.scale.linear().domain([0, 0]).range([0, w]);
    xAxis.scale(xScale).orient("top");;
    this.svg.append("g")
        .attr("class", "axis")
        .attr("transform", "translate(0," + h + ")")
        .call(xAxis);

}

graph.prototype.setColor = function (color) {
    this.barColor = color;
}
