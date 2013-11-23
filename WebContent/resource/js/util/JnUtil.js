(function(window) {
	function JnUtil(){
		
	}
	JnUtil.prototype.isEmptyStr = function(str){
		return str == null || str == undefined || str === "";
	};
	JnUtil.prototype.isNotEmptyStr = function(str){
		return !JnUtil.prototype.isEmptyStr(str);
	};
	JnUtil.prototype.isEmpty = function(v){
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
	};
	JnUtil.prototype.isNotEmpty = function(v){
		return !JnUtil.prototype.isEmpty(v); 
	};
	JnUtil.prototype.clearForm = function(formId){
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
	};
	JnUtil.prototype.fillForm = function(data, preffix){
		for(var key in data){
			if($("#" + preffix + "_" + key)){
				$("#" + preffix + "_" + key).val(data[key]);
			}
		}
	};
	JnUtil.prototype.plus = function(){
		var result = 0;
		for(var i = 0; i < arguments.length; i ++){
			if(arguments[i]){
				result = Math.round((result+Number(arguments[i]))*100)/100;
			}
		}
		return result;
	};
	
	/**
	 * 闪烁
	 * @param speed 速度
	 * @param j JQuery对象
	 * @param count 闪烁次数
	 */
	JnUtil.prototype.flicker = function(speed, j, count){
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
	};
	JnUtil.prototype.getRandom = function(min, max) {
	    return parseInt(Math.random() * (max - min + 1) + min);
	};
	
	JnUtil.prototype.getUrlParam = function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null)
	        return unescape(r[2]);
	    return null;
	};
	JnUtil.prototype.getIndexUrl = function(contextPath, protocol) {
	    var url = window.location.href;
	    if (protocol) {
            url = protocol + url.substring("http".length, url.length);
        }
        return url.substring(0, url.indexOf(contextPath) + contextPath.length);
	};
	JnUtil.prototype.encodeHTML = function(str) {
	    var div = document.createElement('div');  
	    div.appendChild(document.createTextNode(str));  
	    str = div.innerHTML;
	    $(div).remove();
	    return str;
    };
	JnUtil.prototype.decodeHTML = function(str){  
	    var div = document.createElement('div');  
	    div.innerHTML = s;  
	    str = div.innerText || div.textContent;
	    $(div).remove();
	    return str;
	}  ;
	JnUtil.prototype.substring = function(string, fromStr, toStr){
	    if (string.indexOf(fromStr) != -1) {
	        string = string.substring(string.indexOf(fromStr), string.length);
	        if(string.indexOf(toStr) != -1){
	            string = string.substring(0, string.indexOf(toStr));
	        }
        }
        return string;
	};
	
	window.J = new JnUtil();
})(window);
