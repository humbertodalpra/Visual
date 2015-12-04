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

    $.post("FrontController?action=ReadGraph", function (json) {
        var graph = json;

        var force = d3.layout.force()
                .charge(-200)
                .linkDistance(60)
                .size([$("#graph").width(), $("#graph").height()]);

        force.nodes(graph.nodes)
                .links(graph.links)
                .start();

        var link = svg.selectAll("link")
                .data(graph.links)
                .enter().append("path")
                .attr("class", function (d) {
                    return "link " + d.type;
                });

        var node = svg.selectAll("image")
                .data(graph.nodes)
                .enter().append("image")
                .attr("class", "node")
                .attr("xlink:href", "./img/circle.png")
                .attr("width", "24")
                .attr("height", "24")
                .call(force.drag);

        node.append("title")
                .text(function (d) {
                    return d.name;
                });

        force.on("tick", function () {

            node.attr("x", function (d) {
                return d.x - 12;
            })
                    .attr("y", function (d) {
                        return d.y - 12;
                    });
            path.attr("d", linkArc);
        });

        function linkArc(d) {
            var dx = d.target.x - d.source.x,
                    dy = d.target.y - d.source.y,
                    dr = Math.sqrt(dx * dx + dy * dy);
            return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
        }

        function transform(d) {
            return "translate(" + d.x + "," + d.y + ")";
        }

        var path = svg.append("g").selectAll("path")
                .data(force.links())
                .enter().append("path")
                .attr("class", function (d) {
                    return "link " + d.type;
                })
                .attr("marker-end", function (d) {
                    return "url(#" + d.type + ")";
                });
    }, "json");

});
