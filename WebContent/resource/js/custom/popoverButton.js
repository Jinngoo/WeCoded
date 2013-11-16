//$(document).ready(function(){
//    $(function() {
//        $('#2671eade-5450-43e1-b2ff-dbfb6b57d300').popover({
//            html : true,
//            delay : {
//                show : 500
//            }
//        });
//    });
//});
function initPopover(a){
    var popover = $(a);
    if (!popover.attr("init")) {
        popover.attr("init", "true");
        popover.popover({
            html : true,
            delay : {
                show : 500
            }
        });
        popover.trigger("mouseover");
    }
}
function destroyPopover(trigger){
    if($(trigger).attr('rel') == 'popover'){
        $(trigger).popover('destroy');
    }else{
        destroyPopover($(trigger).parent());
    }
}