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
    var svg = d3.select("#graph")
            .append("svg");

    $(window).resize();

    var color = d3.scale.category20();

    var nodes;
    var links = [];

    $.post("FrontController?action=ReadNodes", function (json) {
    //nodes = [{x: 30, y: 50}, {x: 50, y: 80}, {x: 90, y: 120}];
    nodes = json;
    //alert(json);
    }, "json");

    var force = d3.layout.force()
            .charge(-300)
            .linkDistance(60)
            .size([$("#graph").width(), $("#graph").height()]);
//--------------------------------------------------------------------

    force.nodes(nodes)
            .links(links)
            .start();

    var link = svg.selectAll("link")
            .data(links)
            .enter().append("line")
            .attr("class", "link")
            .style("stroke-width", function (d) {
                return Math.sqrt(d.value);
            });

    var node = svg.selectAll("image")
            .data(nodes)
            .enter().append("image")
            //.attr("class", "node")
            .attr("xlink:href", "./img/actor.png")
            .attr("width", "20")
            .attr("height", "20")
            .style("fill", function (d) {
                return color(d.group);
            })
            .call(force.drag);

    node.append("title")
            .text(function (d) {
                return d.name;
            });

    force.on("tick", function () {
        link.attr("x1", function (d) {
            return d.source.x;
        })
                .attr("y1", function (d) {
                    return d.source.y;
                })
                .attr("x2", function (d) {
                    return d.target.x;
                })
                .attr("y2", function (d) {
                    return d.target.y;
                });

        node.attr("x", function (d) {
            return d.x;
        })
                .attr("y", function (d) {
                    return d.y;
                });
    });





    /*
     svg.selectAll("image")
     .data(nodes)
     .enter()
     .append("image")
     .data(nodes)
     .attr("xlink:href", "./img/actor.png")
     .attr("width", "20")
     .attr("height", "20");
     */
});