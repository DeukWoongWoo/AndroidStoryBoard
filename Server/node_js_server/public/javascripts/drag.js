var img_L = 0;
var img_T = 0;
var targetObj;

function getLeft(o) {
    return parseInt(o.style.left.replace('px', ''));
}

function getTop(o) {
    return parseInt(o.style.top.replace('px', ''));
}

// 이미지 움직이기
function moveDragAppinfo(e) {
    var e_obj = window.event ? window.event : e;
    var dmvx = parseInt(e_obj.clientX + img_L);
    var dmvy = parseInt(e_obj.clientY + img_T);
    targetObj.style.left = dmvx + "px";
    targetObj.style.top = dmvy + "px";
    return false;
}

// 드래그 시작
function startDragAppinfo(e, obj) {
    targetObj = obj;
    var e_obj = window.event ? window.event : e;
    img_L = getLeft(obj) - e_obj.clientX;
    img_T = getTop(obj) - e_obj.clientY;

    document.onmousemove = moveDragAppinfo;
    document.onmouseup = stopDragAppinfo;
    if (e_obj.preventDefault)e_obj.preventDefault();
}

// 드래그 멈추기
function stopDragAppinfo() {
    document.onmousemove = null;
    document.onmouseup = null;
}

