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
function initPopover(trigger){
    if(trigger){
        var popover = $(trigger);
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
}
function hidePopover(trigger){
    if(trigger){
        if($(trigger).attr('rel') == 'popover'){
            $(trigger).popover('hide');
        }else{
            destroyPopover($(trigger).parent());
        }
    }
}
function destroyPopover(trigger){
    if(trigger){
        if($(trigger).attr('rel') == 'popover'){
            $(trigger).popover('destroy');
        }else{
            destroyPopover($(trigger).parent());
        }
    }
}