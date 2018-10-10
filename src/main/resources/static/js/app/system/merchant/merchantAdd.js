
var validator;
var $merchantAddForm = $("#merchant-add-form");
$(function () {
     validateRule();

    $("#merchant-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $merchantAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "merchant/add", $merchantAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "merchant/update", $merchantAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#merchant-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#merchant-add-button").attr("name", "save");
    $("#merchant-add-modal-title").html('新增商户');
    validator.resetForm();
    $MB.closeAndRestModal("merchant-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $merchantAddForm.validate({
        rules: {
            merchant_name: {
                required: true,
                minlength: 3,
                maxlength: 50,
                /*remote: {
                    url: "user/checkUserName",
                    type: "get",
                    dataType: "json",
                    data: {
                        username: function () {
                            return $("input[name='merchant_name']").val().trim();
                        },
                        oldusername: function () {
                            return $("input[name='oldmerchant_name']").val().trim();
                        }
                    }
                }*/
            },
            merchant_mail: {
                email: true
            },
            merchant_corp: {
                required: true,
                minlength: 2,
                maxlength: 15
            },
            merchant_phone: {
                required: true,
                //checkPhone: true
            },
            merchant_addr: {
                required: true,
                minlength: 2,
                maxlength: 100
            },
            merchant_region: {
                required: true,
                minlength: 2,
                maxlength: 10
            },
            merchant_information: {
                required: true,
                minlength: 2,
                maxlength: 50
            },
            merchant_wximge: {
                required: true,
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
            merchant_name: {
                required: icon + "请输入商户名",
                minlength: icon + "商户名长度3到50个字符",
                //remote: icon + "商户名已经存在"
            },
            merchant_mail: icon + "邮箱格式不正确",
            merchant_phone: icon + "电话格式不正确",
            merchant_corp: icon + "长度到15个字符",
            merchant_addr: icon + "长度2到100个字符",
            merchant_region: icon + "长度2到10个字符",
            merchant_information: icon + "长度2到50个字符",
            merchant_wximge: icon + "请选择二维码",
        }
    });
}