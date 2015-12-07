$(window).resize(function () {
    var width = $(window).width() - 18;
    var height = 700;//$(window).height() - ($("#config").height() + 32);
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

    $.post("FrontController?action=ReadGraph", function (json) {
        //alert(JSON.stringify(json));
        var graph = json;
        var force = d3.layout.force()
                .charge(-300)
                .linkDistance(80)
                .size([$("#graph").width(), $("#graph").height()]);

        force.nodes(graph.nodes)
                .links(graph.links)
                .start();

        //Defines the arrow for the links
        svg.append("defs").selectAll("marker")
                .data(["inferred", "asserted"])
                .enter().append("marker")
                .attr("id", function (d) {
                    return d;
                })
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 22)
                .attr("refY", -2)
                .attr("markerWidth", 6)
                .attr("markerHeight", 6)
                .attr("orient", "auto")
                .append("path")
                .attr("id", function (d) {
                    return d;
                })
                .attr("d", "M0,-5L10,0L0,5");

        var path = svg.selectAll("path")
                .data(graph.links)
                .enter().append("path")
                .attr("class", function (d) {
                    if (d.inferred)
                        return "inferred";
                    else
                        return "asserted";
                })
                .attr("marker-end", function (d) {
                    if (d.inferred)
                        return "url(#inferred)";
                    else
                        return "url(#asserted)";
                })
                /*.style("stroke-width", function (d) {
                 return Math.sqrt(d.value);
                 })*/;

        path.append("title")
                .text(function (d) {
                    return d.name;
                });

        var node = svg.selectAll("image")
                .data(graph.nodes)
                .enter().append("image")
                .attr("class", function (n) {
                    return n.type;
                })
                .attr("xlink:href", "./img/circle.png")
                .attr("width", "24")
                .attr("height", "24")
                .call(force.drag);

        node.append("title")
                .text(function (d) {
                    return d.name;
                });

        force.on("tick", function () {
            path.attr("x1", function (d) {
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
                    })
                    .attr("d", function (d) {
                        var dx = d.target.x - d.source.x,
                                dy = d.target.y - d.source.y,
                                dr = Math.sqrt(dx * dx + dy * dy);
                        return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
                    });

            node.attr("x", function (d) {
                return d.x - 12;
            })
                    .attr("y", function (d) {
                        return d.y - 12;
                    });
        });

    }, "json");

});

