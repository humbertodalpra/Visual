$(window).resize(function () {
    var width = $(window).width() - 18;
    var height = $(window).height() - ($("#config").height() + 32);
    $("#graph").width(width)
            .height(height);
    d3.select("svg")
            .attr("width", width)
            .attr("height", height);
});

$(document).ready(function () {
    $(window).resize();

    var svg = d3.select("#graph")
            .append("svg");
    var nodes;
    var links;

    //$.post("FrontController?action=ReadNodes", function (json) {
    nodes = [{x: 30, y: 50},
        {x: 50, y: 80},
        {x: 90, y: 120}];
    //alert(json);
    //}, "json");

    svg.selectAll("image")
            .data(nodes)
            .enter()
            .append("image")
            .data(nodes)
            .attr("xlink:href", "./img/actor.png")
            .attr("width", "20")
            .attr("height", "20");
});