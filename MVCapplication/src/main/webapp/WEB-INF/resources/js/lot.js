function subForm(e, elem) {
    e.preventDefault();
    var url = $(elem).closest('form').attr('action'),
        data = $(elem).closest('form').serialize();
    $.ajax({
        url: url,
        type: 'post',
        data: data,
        dataType: 'text',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        success: function (response, textStatus, jqXHR) {
            console.log(response);
        },
        error: function (response, textStatus, jqXHR) {
            console.log(response);
            $("#message").text("something went wrong");
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });
}

$( document ).ready(function() {
    $("#enabled").on("change", function (e) {
        var elem = this;
        $("#success").modal('show');

        $(".btn-confirm").click(function () {

            $("#success").modal('hide');
            subForm(e, elem);
        });

        $(".btn-danger").click(function () {

            $("#success").modal('hide');
            $(elem).prop('checked', !$(elem).prop("checked"));
        });
    });
});
