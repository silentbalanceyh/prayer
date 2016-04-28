/**
 * 值转换，将JS中的值和Java语言中的值做连接
 */
// Package: util
// File: Type.js
// Version: 1.0
(function(){
    // ------------------------Class Definition---------------------
    system["type"] = (function(){
        /** 1.连接Java类 * */
        var __value = Java.type("com.prayer.script.js.JSValue");
        /** 2.私有变量，引用Java接口 * */
        _string = function(value){
            return __value.toString(value);
        };
        _xml = function(value){
            return __value.toXml(value);
        };
        _json = function(value){
            return __value.toJson(value);
        };
        _script = function(value){
            return __value.toScript(value);
        };
        _logical = function(value){
            return __value.toLogical(value);
        };
        _number = function(value){
            return __value.toNumber(value);
        };
        _integer = function(value){
            return __value.toInteger(value);
        };
        _binary = function(value){
            return __value.toBinary(value);
        };
        _date = function(value){
            return __value.toDate(value);
        };
        _decimal = function(value){
            return __value.toDecimal(value);
        };
        return function(){
            return {};
        };
    })();
    // ------------------------Interface----------------------------
    (function()(system["type"] = system["type"].prototype = {
        String:_string,
        Json:_json,
        Xml:_xml,
        Script:_script,
        Boolean:_logical,
        Long:_number,
        Integer:_integer,
        Binary:_binary,
        Date:_date,
        Decimal:_decimal
    }))();
})();

