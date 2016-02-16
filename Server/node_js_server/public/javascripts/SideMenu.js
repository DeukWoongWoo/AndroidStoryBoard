var sideMenu = function () {
    $('.fa').mouseover(function () {
        $(this).css("color", "gray");
    }).mouseout(function () {
        $(this).css("color", "");
    });

    $('.fa-bars').click(function () {
        $('.sidebar').animate({
            left: "0px"
        }, 200);

        $('.content-body').animate({
            left: "300px"
        }, 200);

        $('.fa-bars').css(
            "visibility", "hidden"
        );
    });

    $('#menu-close').click(function () {
        $('.sidebar').animate({
            left: "-300px"
        }, 200);

        $('.content-body').animate({
            left: "0px"
        }, 200);

        $('.fa-bars').css(
            "visibility", ""
        );
    });


    $('.content').click(function () {
        $('.sidebar').animate({
            left: "-300px"
        }, 200);

        $('.content-body').animate({
            left: "0px"
        }, 200);

        $('.fa-bars').css(
            "visibility", ""
        );
    });

    $('.main-body').click(function () {
        $('.sidebar').animate({
            left: "-300px"
        }, 200);

        $('.content-body').animate({
            left: "0px"
        }, 200);

        $('.fa-bars').css(
            "visibility", ""
        );
    });
}

var registerApp = function () {
    $('#register-app').click(function () {
        $('.register-app-form').show(200);
        $('#register-app').hide(200);
    });

    $('#register-app-close').click(function () {
        $('.register-app-form').hide(200);
        $('#register-app').show(200);
    });
}

var deleteApp = function(){
    $('.delete-app').click(function(){
        var app_name = $(this).parent().siblings('.app-name').text();
        $.get('/delete/' + app_name, function(){

        });
    });
}

var main = function () {
    sideMenu();
    registerApp();
    deleteApp();
    $('#rect-info-close').click(function () {
        $(this).parent().parent().css("visibility", "hidden");
    });

    $('.rect-info').css("left", screen.width * 0.3 + "px").css("top", screen.height * 0.3 + "px");
};

$(document).ready(main);