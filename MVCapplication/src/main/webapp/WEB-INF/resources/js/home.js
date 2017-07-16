$( document ).ready(function() {
    var width = $(".thumbnail img").css("width");
    width = width.substring(0, width.length - 2);
    $(".thumbnail img").css("height", width * 0.46875 + "px");

    width = $("img.slide-image").css("width");
    width = width.substring(0, width.length - 2);
    $("img.slide-image").css("height", width * 0.375 + "px");
});