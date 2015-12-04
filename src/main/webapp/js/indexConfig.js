$(document).ready(function () {
    $.post("FrontController?action=ReadOWLPath", function (string) {
        $("#owlPath").html(string);
    }, "text");

    $("input[type=checkbox][name=inference").change(function () {
        if ($("input[type=checkbox][name=inference").prop("checked")){
            $.post("FrontController?action=ShowInferences");
        }
    });

    $("input[type=radio][name=icon").change(function () {
        var svg = d3.select("#graph");
        if (this.value === "prov") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/activity.png";
                        else if (node.type === "Person" || node.type === "Agent" || node.type === "Organization" || node.type === "SoftwareAgent")
                            return "./img/agent.png";
                        else return "./img/entity.png";
                    });
        } else if (this.value === "bpmn") {
            svg.selectAll("image")
                    .attr("xlink:href", function (node) {
                        if (node.type === "Activity")
                            return "./img/task.png";
                        else if (node.type === "Person" || node.type === "Agent")
                            return "./img/actor.png";
                        else return "./img/data.png";
                    });
        } else {
            svg.selectAll("image").attr("xlink:href", "./img/circle.png");
        }
    });
});


