var validator;
var $goodstypeAddForm = $("#goodstype-add-form");
$(function () {
    validateRule();

    $("#goodstype-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $goodstypeAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "goodstype/add", $goodstypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "goodstype/update", $goodstypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#goodstype-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#goodstype-add-button").attr("name", "save");
    $("#goodstype-add-modal-title").html('新增商品类型');
    $goodstypeAddForm.find("input[name='goodstype_name']").removeAttr("readonly");
    validator.resetForm();
    $MB.closeAndRestModal("goodstype-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $goodstypeAddForm.validate({
        rules: {
            goodstype_name: {
                required: true,
                minlength: 3,
                maxlength: 25,
                remote: {
                    url: "goodstype/checkgoodstype_name",
                    type: "get",
                    dataType: "json",
                    data: {
                        goodstype_name: function () {
                            return $("input[name='goodstype_name']").val().trim();
                        },
                        oldgoodstype_name: function () {
                            return $("input[name='oldgoodstype_name']").val().trim();
                        }
                    }
                }
            },
            goodstype_num: {
                required: true
            },
            goodstype_imageurl: {
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
            goodstype_name: {
                required: icon + "请输入商品类型名",
                minlength: icon + "商品类型名长度3到25个字符",
                remote: icon + "商品类型名已经存在"
            },
            goodstype_num: icon + "请输入商品类型优先级码",
            goodstype_imageurl: icon + "请提交图片",
        }
    });
}


