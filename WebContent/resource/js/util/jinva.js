function Jinva(){
	
}
Jinva.prototype.isEmptyStr = function(str){
	return str == null || str == undefined || str === "";
}
Jinva.prototype.isNotEmptyStr = function(str){
	return !Jinva.prototype.isEmptyStr(str);
}
Jinva.prototype.isEmpty = function(v){
	switch (typeof v){ 
		case 'undefined' : return true; 
		case 'string' : if(v == null || v == undefined || v === "") return true; break; 
		case 'boolean' : if(!v) return true; break; 
		case 'number' : if(0 === v) return true; break; 
		case 'object' : 
		if(null === v) return true; 
		if(undefined !== v.length && v.length==0) return true; 
		for(var k in v){return false;} return true; 
		break; 
	} 
	return false; 
}
Jinva.prototype.isNotEmpty = function(v){
	return !Jinva.prototype.isEmpty(v); 
}
Jinva.prototype.clearForm = function(formId){
	$("#"+formId).find(':input').each(function(){
		 switch(this.type){  
			 case 'checkbox':  
			 case 'radio':  
				 this.checked = false;  
				 break;  
	         case 'passsword':  
	         case 'select-multiple':  
	         case 'select-one':  
	         case 'text':  
	         case 'textarea':  
	             $(this).val('');  
	             break;
	         default:
	        	 $(this).val('');  
		 }
	});
}
Jinva.prototype.fillForm = function(data, preffix){
	for(var key in data){
		if($("#" + preffix + "_" + key)){
			$("#" + preffix + "_" + key).val(data[key]);
		}
	}
}
Jinva.prototype.plus = function(){
	var result = 0;
	for(var i = 0; i < arguments.length; i ++){
		if(arguments[i]){
			result = Math.round((result+Number(arguments[i]))*100)/100;
		}
	}
	return result;
}

/**
 * 闪烁
 * @param speed 速度
 * @param j JQuery对象
 * @param count 闪烁次数
 */
Jinva.prototype.flicker = function(speed, j, count){
    //TODO 改造成给dom加attr，不要递归传count
	if(count == 0){
		if(j.css("display")=="none"){
			j.fadeIn(speed);
		}
		return;
	}
	var _self = this;
	j.fadeToggle(speed, function(){
		_self.flicker(speed, j, --count);
	});
}
Jinva.prototype.getRandom = function(min, max) {
    return parseInt(Math.random() * (max - min + 1) + min);
};

Jinva.prototype.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

var J = new Jinva();
//(function(window) {
//	var Jinva = {
//			isEmptyStr : function(str) {
//				return str == null || str == undefined || str === "";
//			},
//			isEmpty : function(v) {
//				switch (typeof v){ 
//				case 'undefined' : return true; 
//				case 'string' : if(trim(v).length == 0) return true; break; 
//				case 'boolean' : if(!v) return true; break; 
//				case 'number' : if(0 === v) return true; break; 
//				case 'object' : 
//					if(null === v) return true; 
//					if(undefined !== v.length && v.length==0) return true; 
//					for(var k in v){return false;} return true; 
//					break; 
//				} 
//				return false; 
//			}
//	};
//	window.Jinva = Jinva;
//})(window);