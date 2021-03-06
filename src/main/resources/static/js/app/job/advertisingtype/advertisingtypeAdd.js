var validator;
var $advertisingtypeAddForm = $("#advertisingtype-add-form");
$(function () {
    validateRule();

    $("#advertisingtype-add .btn-save").click(function () {
        $("#advertisingtype-add-button").attr("disabled", true);
        var name = $(this).attr("name");
        validator = $advertisingtypeAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "advertisingtype/add", $advertisingtypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "advertisingtype/update", $advertisingtypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
        $("#advertisingtype-add-button").attr("disabled", false);
    });

    $("#advertisingtype-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#advertisingtype-add-button").attr("name", "save");
    $("#advertisingtype-add-button").attr("disabled", false);
    $("#advertisingtype-add-modal-title").html('新增广告类型');
    $advertisingtypeAddForm.find("input[name='advertisingtype_name']").removeAttr("readonly");
    validator.resetForm();
    $MB.closeAndRestModal("advertisingtype-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $advertisingtypeAddForm.validate({
        rules: {
            advertisingtype_name: {
                required: true,
                minlength: 2,
                maxlength: 10,
                remote: {
                    url: "advertisingtype/checkadvertisingtype_name\"",
                    type: "get",
                    dataType: "json",
                    data: {
                        advertisingtype_name: function () {
                            return $("input[name='advertisingtype_name']").val().trim();
                        },
                        oldadvertisingtype_name: function () {
                            return $("input[name='oldadvertisingtype_name']").val().trim();
                        }
                    }
                }
            },
            advertisingtype_num: {
                required: true
            },
            advertisingtype_imageurl: {
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
            advertisingtype_name: {
                required: icon + "请输入广告类型名",
                minlength: icon + "广告类型名长度2到10个字符",
                remote: icon + "广告类型名已经存在"
            },
            advertisingtype_num: icon + "请输入广告类型优先级码",
            advertisingtype_imageurl: icon + "请提交图片",
        }
    });
}


