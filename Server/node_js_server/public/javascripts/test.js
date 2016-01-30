function graph() {
    this.w = screen.width * 0.4;
    this.h = screen.height * 0.4;

    this.rects;
    this.label;

    this.barColor = 'teal';
}


graph.prototype.drawGraph = function (divId, data, oName) {
    var w = this.w;
    var h = this.h;
    var barColor = this.barColor;
    var maxY = Math.max.apply(null, data);
    var rY = h / maxY;
    this.svg = d3.select("#" + divId).append("svg").attr("width", w).attr("height", h);
    //this.graphDiv = this.svg.append("div").attr("x",0).attr("y",0).attr("width", w).attr("height", h).attr("fill", '#8FBC8F');
    this.rects = this.svg.selectAll("rect").data(data).enter().append("rect").attr("fill", function(d){
        console.log(d);
        if(d == 0) return 0;
        else return barColor;
    });
    var label = this.svg.append("text");
    this.rects.attr("x", function (d, i) {
            return i * (w / data.length);
        })
        .attr("y", function (d) {
            return h - ((d * rY) * 0.8 + 5);
        })
        .attr("width", w / data.length - ((w / data.length) / 10))
        .attr("height", function (d) {
            return (d * rY) * 0.8 + 5;
        })
        //.attr("class", "a")
        .data(oName).append("text")
        .text(function (d) {
            return d;
        });

    this.rects.attr("class", function(d){
       return d + "-ASB-object";
    });


    $('.graph').children('svg').children('rect').mouseover(function () {
        $('rect').css("fill", "");
        var className = '.' + $(this).attr("class");
        $(className).css("fill", "hotpink")
        console.log("x : " + event.clientX );
        $('.rect-info').css("left", event.clientX).css("top", event.clientY + document.body.scrollTop).css("visibility", "visible");
    }).mouseout(function () {

    });
}

graph.prototype.setColor = function (color) {
    this.barColor = color;
}

$(document).ready(function () {
    $("button").click(function () {
        $.get("/ajax", function (data, status) {
            $.each(data, function (i, field) {
                $("#div2").append("<h2>" + field.user_id + " " + i + "</h2>");
                $("#div2").append("<h2>" + field.name + " " + i + "</h2>");
            });
        });
    });
});