var validator;
var $goodsAddForm = $("#goods-add-form");
var goodstype_id = $goodsAddForm.find("input[name='goodstype_id']");
var $goodstype_idSelect = $goodsAddForm.find("select[name='goodstype_idSelect']");

$(function () {
    validateRule();
    initgoodstype();//获取商品类型列表
    $("#goods-add .btn-save").click(function () {
        $("#goods-add-button").attr("disabled", true);
        var name = $(this).attr("name");
        validator = $goodsAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "goods/add", $goodsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "goods/update", $goodsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#goods-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#goods-add-button").attr("name", "save");
    $("#goods-add-button").attr("disabled", false);
    $("#goods-add-modal-title").html('新增商品');

    $goodstype_idSelect.multipleSelect('setSelects', []);
    $goodstype_idSelect.multipleSelect("refresh");
    $goodsAddForm.find("input[name='goods_name']").removeAttr("readonly");
    validator.resetForm();
    $MB.closeAndRestModal("goods-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $goodsAddForm.validate({
        rules: {
            goods_name: {
                required: true,
                minlength: 2,
                maxlength: 15,
                remote: {
                    url: "goods/checkgoods_name",
                    type: "get",
                    dataType: "json",
                    data: {
                        goods_name: function () {
                            return $("input[name='goods_name']").val().trim();
                        },
                        oldgoods_name: function () {
                            return $("input[name='oldgoods_name']").val().trim();
                        }
                    }
                }
            },
            goods_num: {
                required: true
            },
            goods_bigimge: {
                required: true,
            },
            goods_littleimge: {
                required: true,
            },
            goodstype_idSelect: {
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
            goods_name: {
                required: icon + "请输入商品名",
                minlength: icon + "商品名长度2到15个字符",
                remote: icon + "商品名已经存在"
            },
            goods_num: icon + "请输入商品优先级码",
            goods_bigimge: icon + "请提交图片",
            goods_littleimge: icon + "请提交图片",
            goodstype_idSelect: icon + "请选择商品类型",
        }
    });
}


function initgoodstype() {
    $.post(ctx + "goodstype/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].goodstype_id + "'>" + data[i].goodstype_name + "</option>"
        }
        $goodstype_idSelect.html("").append(option);
        var options = {
            selectAllText: '所有商品类型',
            allSelected: '所有商品类型',
            width: '70%',
            single: true,//是否单选
            filter: true,//搜索
            onClose: function () {
                goodstype_id.val($goodstype_idSelect.val());
                validator.element("input[name='goodstype_id']");
            },onOpen: function () {//打开当下拉列表被打开时触发。
                $goodstype_idSelect.multipleSelect('uncheckAll');
            },
        };

        $goodstype_idSelect.multipleSelect(options);
    });
}