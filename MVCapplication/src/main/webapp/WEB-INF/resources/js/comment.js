function subFormComment(e) {
    e.preventDefault();
    var url = $(this).closest('form').attr('action'),
        data = $(this).closest('form').serialize();
    var comment = $(this).closest('form').find("textarea#data.form-control");
    $.ajax({
        url: url,
        type: 'post',
        data: data,
        dataType: 'text',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        success: function (response, textStatus, jqXHR) {
            fillComment(response, comment.val());
        },
        error: function (response, textStatus, jqXHR) {
            $("#message").text(response.responseText);
            $("#error").modal('show');

            $(".btn-confirm").click(function () {

                $("#error").modal('hide');
            });
        }
    });
}

function fillComment(user, comment) {
    var text = '<div class="row">' +
        '<div class="col-md-12">' + user +
        '<span class="pull-right">0 days ago</span><p>' +
        comment +
        '</p></div></div>';
    if($("div.comment").length) {
        $("div.comment").after("<hr>" + text);
    } else {
        $("div.well").prepend(text + "<hr>");
    }

}

$( document ).ready(function() {

    $("#comment").on("submit", subFormComment);
});