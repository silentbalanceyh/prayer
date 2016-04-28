/**
 * 生成Expression专用类
 */
// Package: util
// File: Expression.js
// Version: 1.0

(function(){
    // ------------------------Class Definition---------------------
    system["query"] = (function(){
        /** 1.连接Java类 * */
        var __restrict = Java.type("com.prayer.model.query.Restrictions");
        /** 2.共享函数 * */
        var __single = function(fun,arguments){
            var expr = null;
            try{
                if(arguments.length == 1){
                    expr = fun(arguments[0]);
                }
            }catch(error){
                print(error);
            }
            return expr;
        };
        var __double = function(fun,arguments){
            var expr = null;
            try{
                if(arguments.length == 2){
                    expr = fun(arguments[0],arguments[1]);
                }
            }catch(error){
                print(error);
            }
            return expr;
        };
        var __tuple = function(fun,arguments){
            var expr = null;
            try{
                if(arguments.length == 3){
                    expr = fun(arguments[0],arguments[1],arguments[2]);
                }
            }catch(error){
                print(error);
            }
            return expr;
        };
        var __execute = function(fun,arguments){
            var expr = null;
            switch(arguments.length){
            case 1:expr = __single(fun,arguments);break;
            case 2:expr = __double(fun,arguments);break;
            case 3:expr = __tuple(fun,arguments);break;
            }
            return expr;
        };
        // name = value
        _eq = function(){
            return __execute(__restrict.eq,arguments);
        };
        // name <> value
        _neq = function(){
            return __execute(__restrict.neq,arguments);
        };
        // name < value
        _lt = function(){
            return __execute(__restrict.lt,arguments);
        };
        // name <= value
        _le = function(){
            return __execute(__restrict.le,arguments);
        };
        _gt = function(){
            return __execute(__restrict.gt,arguments);
        };
        _ge = function(){
            return __execute(__restrict.ge,arguments);
        };
        _null = function(){
            return __execute(__restrict.isNull,arguments);
        };
        _notnull = function(){
            return __execute(__restrict.isNotNull,arguments);
        };
        _like = function(){
            return __execute(__restrict.like,arguments);
        };
        _and = function(){
            return __execute(__restrict.and,arguments);
        };
        _or = function(){
            return __execute(__restrict.or,arguments);
        };
        /** Like函数 **/
        __mode = Java.type("com.prayer.model.query.MatchMode");
        return function(){
            return {};
        };
    })();
    // -----------------------Interface-----------------------------
    (function()(system["query"] = system["query"].prototype = {
        Cond:{
            EQ:_eq,
            NEQ:_neq,
            LT:_lt,
            LE:_le,
            GT:_gt,
            GE:_ge,
            NULL:_null,
            NOTNULL:_notnull,
            LIKE:_like,
            OR:_or,
            AND:_and
        },
        MatchMode:{
            END:__mode.END,
            EXACT:__mode.EXACT,
            START:__mode.START,
            ANYWHERE:__mode.ANYWHERE
        }
    }))();
})();
