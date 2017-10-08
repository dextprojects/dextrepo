function click(e) {
if (document.all) {
if (event.button == 2) {
alert('No Right Click Of Mouse, Please !!!');
return false;
}
}
if (document.layers) {
if (e.which == 3) {
alert('No Right Click Of Mouse, Please !!!');
return false;
}
}
}
if (document.layers) {
document.captureEvents(Event.MOUSEDOWN);
}
document.onmousedown=click;
