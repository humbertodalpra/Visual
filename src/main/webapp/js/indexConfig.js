$(document).ready(function () {
    $.post("FrontController?action=ReadOWLPath", function (string) {
        $("#owlPath").html(string);
    }, "text");

    $("input[type=checkbox][name=inferredLinks]").change(function () {
        if ($("input[type=checkbox][name=inferredLinks]").prop("checked")) {
            $("path.inferred").fadeIn();
            $("marker.inferred").fadeIn();
        } else {
            $("path.inferred").fadeOut();
            $("marker.inferred").fadeOut();
        }
    });
    
    $("input[type=checkbox][name=assertedLinks]").change(function () {
        if ($("input[type=checkbox][name=assertedLinks]").prop("checked")) {
            $("path.asserted").fadeIn();
            $("marker.asserted").fadeIn();
        } else {
            $("path.asserted").fadeOut();
            $("marker.asserted").fadeOut();
        }
    });

    $("input[type=checkbox][name=actors]").change(function () {
        if ($("input[type=checkbox][name=actors]").prop("checked")) {
            $("image.Agent").fadeIn();
        } else {
            $("image.Agent").fadeOut();
        }
    });

    $("input[type=checkbox][name=tasks]").change(function () {
        if ($("input[type=checkbox][name=tasks]").prop("checked")) {
            $("image.Activity").fadeIn();
        } else {
            $("image.Activity").fadeOut();
        }
    });

    $("input[type=checkbox][name=entities]").change(function () {
        if ($("input[type=checkbox][name=entities]").prop("checked")) {
            $("image.Entity").fadeIn();
        } else {
            $("image.Entity").fadeOut();
        }
    });

    $("input[type=radio][name=icon]").change(function () {
        var svg = d3.select("#graph");
        if (this.value === "prov") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/activity.png";
                        else if (node.type === "Person" || node.type === "Agent" || node.type === "Organization" || node.type === "SoftwareAgent")
                            return "./img/agent.png";
                        else
                            return "./img/entity.png";
                    });
        } else if (this.value === "bpmn") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/task.png";
                        else if (node.type === "Person" || node.type === "Agent")
                            return "./img/actor.png";
                        else
                            return "./img/data.png";
                    });
        } else {
            svg.selectAll("image").attr("xlink:href", "./img/circle.png");
        }
    });
});


