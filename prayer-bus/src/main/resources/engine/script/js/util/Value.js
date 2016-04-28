/**
 * 字符串工具类
 */
// Package: util
// File: Format.js
// Version: 1.0
(function(){
    // ------------------------Class Definition---------------------
    system["value"] = (function(){
        _get = function(value){
            return (undefined == value || null == value)?"":value;
        };
        _updated = function(value){
            return (undefined == value || null == value)?"$NU$":value;
        };
        return function(){
            return {};
        };
    })();
    // ------------------------Interface----------------------------
    (function()(system["value"] = system["value"].prototype = {
        Get:_get,
        Updated:_updated
    }))();
})();