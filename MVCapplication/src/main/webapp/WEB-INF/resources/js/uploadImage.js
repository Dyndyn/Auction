function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('img#blah')
                .attr('src', e.target.result);

            $("#img-container").css("display", "block")
            $("div.img").css("display", "none");
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$( document ).ready(function() {
    $( "input:file" ).on("change", function () {
        readURL($( "input:file" )[0]);
    });
    $( "div.img" ).on("click", function () {
        $( "input:file" ).click();
    });
    $( "span.glyphicon-remove" ).on("click", function () {
        $("#img-container").css("display", "none")
        $("div.img").css("display", "block");
        $( "input:file" ).val("");
    });
});