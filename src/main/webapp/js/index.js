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
    $("input[type=radio][name=icon").change(function () {
        if (this.value === "prov") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/activity.png";
                        else if (node.type === "Person")
                            return "./img/agent.png";
                        else
                            return "./img/entity.png";
                    });
        } else if (this.value === "bpmn") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/task.png";
                        else if (node.type === "Person")
                            return "./img/actor.png";
                        else
                            return "./img/data.png";
                    });
        } else {
            svg.selectAll("image").attr("xlink:href", "./img/entity.png");
        }
    });
    var svg = d3.select("#graph")
            .append("svg");

    $(window).resize();

    $.post("FrontController?action=ReadGraph", function (json) {
        var graph = json;

        var force = d3.layout.force()
                .charge(-300)
                .linkDistance(60)
                .size([$("#graph").width(), $("#graph").height()]);
//--------------------------------------------------------------------

        force.nodes(graph.nodes)
                .links(graph.links)
                .start();

        var link = svg.selectAll("link")
                .data(graph.links)
                .enter().append("line")
                .attr("class", "link")
                .style("stroke-width", function (d) {
                    return Math.sqrt(d.value);
                });

        var node = svg.selectAll("image")
                .data(graph.nodes)
                .enter().append("image")
                .attr("class", "node")
                .attr("xlink:href", "./img/entity.png")
                .attr("width", "25")
                .attr("height", "25")
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

            node.attr("x", function (d) { return d.x - 12; })
                    .attr("y", function (d) { return d.y -12; });
        });


    }, "json");


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

