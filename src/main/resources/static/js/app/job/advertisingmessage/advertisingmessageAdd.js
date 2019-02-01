var validator;
var $advertisingmessageAddForm = $("#advertisingmessage-add-form");
$(function () {
    validateRule();

    $("#advertisingmessage-add .btn-save").click(function () {
        $("#advertisingmessage-add-button").attr("disabled", true);
        var name = $(this).attr("name");
        validator = $advertisingmessageAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "advertisingmessage/add", $advertisingmessageAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "advertisingmessage/update", $advertisingmessageAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
        $("#advertisingmessage-add-button").attr("disabled", false);
    });

    $("#advertisingmessage-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#advertisingmessage-add-button").attr("name", "save");
    $("#advertisingmessage-add-button").attr("disabled", false);
    $("#advertisingmessage-add-modal-title").html('新增留言');
    $advertisingmessageAddForm.find("input[name='advmessageContent']").removeAttr("readonly");
    validator.resetForm();
    $MB.closeAndRestModal("advertisingmessage-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $advertisingmessageAddForm.validate({
        rules: {
            advertisingmessage_name: {
                required: true,
                minlength: 2,
                maxlength: 10,
                remote: {
                    url: "advertisingmessage/checkadvertisingmessage_name\"",
                    type: "get",
                    dataType: "json",
                    data: {
                        advertisingmessage_name: function () {
                            return $("input[name='advertisingmessage_name']").val().trim();
                        },
                        oldadvertisingmessage_name: function () {
                            return $("input[name='oldadvertisingmessage_name']").val().trim();
                        }
                    }
                }
            },
            advertisingmessage_num: {
                required: true
            },
            advertisingmessage_imageurl: {
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
            advertisingmessage_name: {
                required: icon + "请输入广告类型名",
                minlength: icon + "广告类型名长度2到10个字符",
                remote: icon + "广告类型名已经存在"
            },
            advertisingmessage_num: icon + "请输入广告类型优先级码",
            advertisingmessage_imageurl: icon + "请提交图片",
        }
    });
}


