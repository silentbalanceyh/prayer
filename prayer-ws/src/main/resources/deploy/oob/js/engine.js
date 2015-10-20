/** 全局功能函数脚本，原型定义 * */
var $$E = (function(){
    _acquire = function(value){
        return undefined == value?"":value;
    };
    _updated = function(value){
        return undefined == value?"NU":value;
    };
    return function(){
        return {};
    }
})();
/** 查询条件 * */
var $$Q = (function(){
    // 私有变量，引用Java接口
    var __restrict = Java.type("com.prayer.kernel.query.Restrictions");
    _eq = function(){    // NOPMD
        var expr = null;
        if(arguments.length === 2){
            expr = __restrict.eq(arguments[0],arguments[1]);
        }else if(arguments.length === 1){
            expr = __restrict.eq(arguments[0]);
        }
        return expr;
    };
    return function(){
        return {};
    }
})();
/** 值转换 * */
var $$V = (function(){
    var __value = Java.type("com.prayer.bus.script.JSValue");
    // 私有变量，引用Java接口
    _string = function(value){        // NOPMD
        return __value.string(value);
    };
    _xml = function(value){            // NOPMD
        return __value.xml(value);
    };
    _json = function(value){         // NOPMD
        return __value.json(value);
    };
    _script = function(value){        // NOPMD
        return __value.script(value);
    };
    _logical = function(value){     // NOPMD
        return __value.logical(value);
    };
    _number = function(value){         // NOPMD
        return __value.number(value);
    };
    _integer = function(value){        // NOPMD
        return __value.integer(value);
    };
    _binary = function(value){         // NOPMD
        return __value.binary(value);
    };
    _date = function(value){        // NOPMD
        return __value.date(value);
    };
    _decimal = function(value){        // NOPMD
        return __value.decimal(value);
    };
    return function(){
        return {};
    }
})();
// -----------------------闭包造单例----------------------
(function(){$$E = $$E.prototype = {        // NOPMD
    acquire:_acquire,
    updated:_updated
}})();
(function()($$Q = $$Q.prototype = {
    eq:_eq
}))();
(function()($$V = $$V.prototype = {
    string:_string,
    json:_json,
    xml:_xml,
    script:_script,
    date:_date,
    boolean:_logical,    // 接口重命名
    integer:_integer,
    binary:_binary,
    decimal:_decimal,
    long:_number        // 接口重命名
}))();

$$ENV = $env;                	// NOPMD Java记录
$$R = $env.getRecord();        	// NOPMD Java记录，从变量中读取
$$L = $env.getValues();        	// NOPMD Java的值列表，填充参数列表信息
$$O = $env.getOrder();			// NOPMD Java中的OrderBy子句
$$P = $env.getPager();          // NOPMD Java中的Pager信息
// -- 非Collection的主键中PK值
var PK = "uniqueId";
/**
 * $$ENV -- JSEnv $$E -- Engine单例 -- JavaScript $$V -- JS转换值单例 -- JavaScript $$Q --
 * 设置查询条件Expression的函数 -- JavaScript $$R -- Record变量 -- Java :: Record $$L --
 * $$Q中的值列表用于查询参数值 -- Java :: List<Value<?>>
 */
