var $$T = (function () {
    var _config = function (model, operation) {
        var l_model = model.toString().toLowerCase();
        var l_operation = operation.toString().toLowerCase();
        var path = "/dynamic/admin/forms/" + l_model + "/" + l_model + l_operation;
        var fid = "#f" + model + operation;
        var lid = "#f" + model + "ListDATA";
        var rule = "/static/resources/rules/validate/" + l_model + "/" + l_model + l_operation + ".json";
        return {
            path: path,
            form: fid,
            list: lid,
            rule: rule
        };
    };
    var _delete = function (data) {
        // 删除记录集
        console.log(data);
    };
    // Show Add Form -> Model: Address, Operation: Add ( Constract )
    _formA = function (model, operation) {
        var config = _config(model, operation);
        TAB.addTab(config["path"], {
            "title": model + " " + operation,
            "data": null
        }, function () {
            FORM.init(config["form"], config["rule"]);
            jQuery("#btnBack").attr("onclick", "closeTabForm()");
        });
    };
    // Show View Form -> Model: Address, Operation: View ( Constract )
    _formV = function (model, operation, callback) {
        var config = _config(model, operation);
        var data = $$U.row(".radio", config["list"]);
        TAB.addTab(config["path"], {
            "title": model + " " + operation,
            "data": data
        }, function () {
            jQuery("#btnBack").attr("onclick", "closeTabView()");
            if (undefined !== callback) {
                callback();
            }
        });
    };
    // Show Update Form -> Model: Address, Operation: Update ( Constract )
    _formU = function (model, operation) {
        var config = _config(model, operation);
        var data = $$U.row(".radio", config["list"]);
        TAB.addTab(config["path"], {
            "title": model + " " + operation,
            "data": data
        }, function () {
            FORM.init(config["form"], config["rule"]);
            jQuery("#btnBack").attr("onclick", "closeTabForm()");
        });
    };
    // Show Delete Form -> Model: Address, Operation: Delete ( Constract )
    _formD = function (model, operation) {
        var config = _config(model, operation);
        var data = $$U.row(".radio", config["list"]);
        $$D.confirm("Do you want to delete this record directly ? ", function () {
            _delete(data);
        }, function () {
            _formV(model, operation, function () {
                // 设置删除按钮
                jQuery("#btnDelete").removeClass("hidden");
            });
        });
    };
    _remove = function (fid) {
        $$D.confirm("Do you want to delete this record ? ", function () {
            var data = FORM.success(fid);
            _delete(data);
            closeTabView();
        });
    };
    _add = function (fid) {
        if ($(fid).valid()) {
            var data = FORM.success(fid);
            // TODO: 添加记录
            console.log(data);
        }
    };
    _update = function (fid) {
        if ($(fid).valid()) {
            var data = FORM.success(fid);
            // TODO: 更新记录
            console.log(data);
        }
    };
    return function () {
        return {};
    }
})();
(function () {
    $$T = $$T.prototype = {
        formA: _formA,
        formV: _formV,
        formU: _formU,
        formD: _formD,
        remove: _remove,
        add: _add,
        update: _update
    }
})();