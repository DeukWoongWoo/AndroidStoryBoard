function layer_open(el, app_name) {
    var temp = $('#' + el);
    var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수

    document.getElementById('upload-app-name').setAttribute("value", app_name);

    if (bg) {
        $('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다.
    } else {
        temp.fadeIn();
    }

    // 화면의 중앙에 레이어를 띄운다.
    if (temp.outerHeight() < $(document).height()) temp.css('margin-top', '-' + temp.outerHeight() / 2 + 'px');
    else temp.css('top', '0px');
    if (temp.outerWidth() < $(document).width()) temp.css('margin-left', '-' + temp.outerWidth() / 2 + 'px');
    else temp.css('left', '0px');

    temp.find('button.cbtn').click(function (e) {
        if (bg) {
            $('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다.
        } else {
            temp.fadeOut();
        }
        e.preventDefault();
    });


    $('.layer .bg').click(function (e) {	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
        $('.layer').fadeOut();
        e.preventDefault();
    });

}

function layer_war(el) {
    var temp = $('#' + el);
    var bg = temp.prev().hasClass('bg');	//dimmed 레이어를 감지하기 위한 boolean 변수


    if (bg) {
        $('.layer').fadeIn();	//'bg' 클래스가 존재하면 레이어가 나타나고 배경은 dimmed 된다.
    } else {
        temp.fadeIn();
    }

    // 화면의 중앙에 레이어를 띄운다.
    if (temp.outerHeight() < $(document).height()) temp.css('margin-top', '-' + temp.outerHeight() / 2 + 'px');
    else temp.css('top', '0px');
    if (temp.outerWidth() < $(document).width()) temp.css('margin-left', '-' + temp.outerWidth() / 2 + 'px');
    else temp.css('left', '0px');

    temp.find('button.cbtn').click(function (e) {
        if (bg) {
            $('.layer').fadeOut(); //'bg' 클래스가 존재하면 레이어를 사라지게 한다.
        } else {
            temp.fadeOut();
        }
        e.preventDefault();
    });

    $('.layer .bg').click(function (e) {	//배경을 클릭하면 레이어를 사라지게 하는 이벤트 핸들러
        $('.layer').fadeOut();
        e.preventDefault();
    });

    $('#error-confirm-btn').click(function(e){
        $('.layer').fadeOut();
        e.preventDefault();
    });

}