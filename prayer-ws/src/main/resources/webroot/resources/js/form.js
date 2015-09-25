var FORM = function () {
    var _rules = function (form, path, config, funData) {
        jQuery.ajax({
            url: path,
            dataType: "json",
            success: function (data, status, response) {
                if (200 == response.status) {
                    var finalData = FORM.config(form,data,config,funData);
                    form.validate(finalData);
                }
            }
        })
    };
    var _config = function(form, data, config, funData){
        var jsFields = ["maxlength", "minlength", "rangelength", "range", "max", "min"];
        // 初始化配置属性
        data["errorElement"] = config["errorElement"];
        data["errorClass"] = config["errorClass"];
        data["focusInvalid"] = config["focusInvalid"];
        data["ignore"] = config["ignore"];
        // 初始化配置函数
        data["invalidHandler"] = funData["invalidHandler"];
        data["highlight"] = funData["highlight"];
        data["unhighlight"] = funData["unhighlight"];
        data["success"] = funData["success"];
        data["submitHandler"] = funData["submitHandler"];

        // 验证Rules和Messages
        var array = (data["messages"]);
        for (var key in array) {
            var obj = array[key];
            for (var idx = 0; idx < jsFields.length; idx++) {
                var field = jsFields[idx];
                if (undefined !== obj[field]) {
                    obj[field] = eval(data[field]);
                }
            }
        }
        return data;

    }
    var handleValidation = function (id, path) {
        var form = $(id);
        var error = $(".alert-error", form);
        var success = $(".alert-success", form);
        FORM.rules(form, path, {
            errorElement: 'span', //default input error message container
            errorClass: 'help-inline', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: ""
        }, {
            invalidHandler: function (event, validator) { //display error alert on form submit
                success.hide();
                error.show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.help-inline').removeClass('ok'); // display OK icon
                $(element)
                    .closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.control-group').removeClass('error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
                    .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
            },

            submitHandler: function (form) {
                success.show();
                error.hide();
            }
        })
    };
    var submitSuccess = function (id) {
        var form = $(id);
        var error = $(".alert-error", form);
        var success = $(".alert-success", form);
        success.show();
        error.hide();
        var row = jQuery(id + "DATA").val();
        var fields = row.toString().split(',');
        var data = {};
        for (var idx = 0; idx < fields.length; idx++) {
            data[fields[idx]] = jQuery("#" + fields[idx]).val();
        }
        return data;
    };
    return {
        init: function (id, path) {
            handleValidation(id, path);
        },
        success: function (id) {
            return submitSuccess(id);
        },
        rules: function (form, path, config, funData) {
            return _rules(form, path, config, funData);
        },
        config: function (form, data, config, funData) {
            return _config(form, data, config, funData);
        }
    }
}();

/**
 * Rules:
 required: "This field is required.",
 remote: "Please fix this field.",
 email: "Please enter a valid email address.",
 url: "Please enter a valid URL.",
 date: "Please enter a valid date.",
 dateISO: "Please enter a valid date (ISO).",
 number: "Please enter a valid number.",
 digits: "Please enter only digits",
 creditcard: "Please enter a valid credit card number.",
 equalTo: "Please enter the same value again.",
 accept: "Please enter a value with a valid extension.",
 maxlength: $.validator.format("Please enter no more than {0} characters."),
 minlength: $.validator.format("Please enter at least {0} characters."),
 rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
 range: $.validator.format("Please enter a value between {0} and {1}."),
 max: $.validator.format("Please enter a value less than or equal to {0}."),
 min: $.validator.format("Please enter a value greater than or equal to {0}.")
 **/