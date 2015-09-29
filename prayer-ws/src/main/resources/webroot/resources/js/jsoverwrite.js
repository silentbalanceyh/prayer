String.prototype.startWith = function(str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
}
// 添加jQuery自定义验证
jQuery.validator.addMethod("mobile",function(value,element){
    console.log($1);
    console.log($2);
    console.log($3);
    console.log($4);
    console.log(value);
    console.log(element);
    return this.optional(element);
},"XXX");