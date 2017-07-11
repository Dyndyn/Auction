var id = $("#lotId").val();
var userId = $("#userId").val();

var destination = '/queue/notifications/' + id;
var socket = new SockJS('/auction');
var stompClient = Stomp.over(socket);
var mark = false;
var dots = 0;


stompClient.connect({}, function () {
    var count = 0;
    stompClient.subscribe(destination, function (frame) {
        if (frame.headers.userId == null) {
            var comment = JSON.parse(frame.body);
            if (comment.user.id != userId) {
                fillComment(comment.user.name, comment.data);
            }
        } else if (frame.headers.userId != userId) {
            $("#typing").text(frame.body);
            count++;
            mark = true;
            setTimeout(function () {
                if (--count === 0) {
                    $("#typing").text("");
                    mark = false;
                    $('#dots').html('');
                }
            }, 1000);
        }
    });

    $('textarea#data').on('keyup', function () {
        stompClient.send("/app/lot", {'lotId': id}, 'typing');
    });
});

var type = function () {
    if (mark) {
        if (dots < 3) {
            $('#dots').append('.');
            dots++;
        } else {
            $('#dots').html('');
            dots = 0;
        }
    }
}

$(document).ready(function () {
    setInterval(type, 333);
});


