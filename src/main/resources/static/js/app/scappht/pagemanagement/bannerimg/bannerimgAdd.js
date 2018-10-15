var validator;
var $bannerimgAddForm = $("#bannerimg-add-form");
$(function () {
    validateRule();

    $("#bannerimg-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $bannerimgAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "bannerimg/add", $bannerimgAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "bannerimg/update", $bannerimgAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#bannerimg-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#bannerimg-add-button").attr("name", "save");
    $("#bannerimg-add-modal-title").html('新增轮播图');
    $bannerimgAddForm.find("input[name='bannerimg_name']").removeAttr("readonly");
    validator.resetForm();
    $MB.closeAndRestModal("bannerimg-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $bannerimgAddForm.validate({
        rules: {
            bannerimg_name: {
                required: true,
                minlength: 2,
                maxlength: 25,
                remote: {
                    url: "bannerimg/checkbannerimg_name",
                    type: "get",
                    dataType: "json",
                    data: {
                        bannerimg_name: function () {
                            return $("input[name='bannerimg_name']").val().trim();
                        },
                        oldbannerimg_name: function () {
                            return $("input[name='oldbannerimg_name']").val().trim();
                        }
                    }
                }
            },
            bannerimg_num: {
                required: true
            },
            bannerimg_htmlurl: {
                required: true,
            },
            bannerimg_imgurl: {
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
            bannerimg_name: {
                required: icon + "请输入轮播图名",
                minlength: icon + "轮播图名长度2到25个字符",
                remote: icon + "轮播图名已经存在"
            },
            bannerimg_num: icon + "请输入轮播图码",
            bannerimg_htmlurl: icon + "请输入轮播图页面url",
            bannerimg_imgurl: icon + "请提交图片",
        }
    });
}


