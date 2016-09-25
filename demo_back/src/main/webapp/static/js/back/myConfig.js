/*提示框全局默认配置*/
toastr.options = {
  "closeButton": true,
  "debug": false,
  "progressBar": true,
  "positionClass": "toast-top-right",
  "onclick": null,
  "showDuration": "400",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

/* jquery 验证添加自定义方法*/
jQuery.validator.addMethod("phone", function(value, element) { 
	var length = value.length; 
	var mobile = /^((1[0,3,5,8][0-9])|(14[5,7])|(17[0,1,3,6,7,8]))\\d{8}$/;
	return this.optional(element) || (length == 11 && mobile.test(value)); 
	}, "手机号码格式错误");

jQuery.validator.addMethod("zipCode", function(value, element) { 
	var tel = /^[0-9]{6}$/; 
	return this.optional(element) || (tel.test(value)); 
	}, "邮政编码格式错误");

jQuery.validator.addMethod("qq", function(value, element) { 
	var tel = /^[1-9]\d{4,9}$/; 
	return this.optional(element) || (tel.test(value)); 
	}, "qq号码格式错误");

jQuery.validator.addMethod("ip", function(value, element) { 
	var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/; 
	return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256)); 
	}, "Ip地址格式错误");

jQuery.validator.addMethod("chinese", function(value, element) {
	var chinese = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (chinese.test(value));
	}, "只能输入中文");

jQuery.validator.addMethod("loginName", function(value, element) {
	var loginName = /^\w{4,16}$/;
	return this.optional(element) || (loginName.test(value));
	}, "登录名格式错误");

jQuery.validator.addMethod("password", function(value, element) {
	var password = /^\w{6,16}$/;
	return this.optional(element) || (password.test(value));
	}, "密码格式错误");
