document.onmousedown=disableclick;
status="No Right Click...";
function disableclick(event) {
  if(event.button==2) {
     alert(status);
     return false;    
   }
}

if ('matchMedia' in window) {
    // Chrome, Firefox, and IE 10 support mediaMatch listeners
    window.matchMedia('print').addListener(function(media) {
        if (media.matches) {
            beforePrint();
        } else {
            // Fires immediately, so wait for the first mouse movement
            $(document).one('mouseover', afterPrint);
        }
    });
} else {
    // IE and Firefox fire before/after events
    $(window).on('beforeprint', beforePrint);
    $(window).on('afterprint', afterPrint);
}

function beforePrint() {
    $("#AllContent").hide();
    $(".PrintMessage").show();
}

function afterPrint() {
    $(".PrintMessage").hide();
    $("#AllContent").show();
}