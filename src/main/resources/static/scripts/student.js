function setRidingCheckbox(riding){
    let checkbox = document.getElementById("ridingCheckBox");

    if(riding){
        checkbox.setAttribute("checked", "");
    } else {
        checkbox.removeAttribute("checked");
    }
}

$(document).ready(
    function () {
        $("#ridingCheckBox").change(function () {
            console.log("user clicked : " + $(this).prop("checked"));
            console.log($("#studentId").val());
            $.ajax({
                url: "/changeriding",
                data: { 
                    id: $("#studentId").val(),
                    riding: $(this).prop("checked")
                },
                success: function (data) {
                    console.log("server responded success with " );
                    console.log(data);
                },
                error: function (data) {
                    console.log("AJAX Call failed ");
                    console.log(data);
                }
            });
        });
    }
);