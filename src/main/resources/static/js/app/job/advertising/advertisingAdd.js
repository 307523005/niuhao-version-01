/*
$(function () {

});
*/

var validator;
var $advertisingAddForm = $("#advertising-add-form");
var advertisingTypeId = $advertisingAddForm.find("input[name='advertisingTypeId']");
var $advertisingTypeIdSelect = $advertisingAddForm.find("select[name='advertisingTypeIdSelect']");

$(function () {
    validateRule();
    initadvertisingType()
    $("#advertising-add .btn-save").click(function () {
        $("#advertising-add-button").attr("disabled", true);
        //editor1.sync();//将KindEditor的数据同步到textarea标签。
        var html = CKEDITOR.instances.paperTitle.getData();
        html = html.replace(ctx + "/images22/g", "/images22").replace("/images22/g", ctx + "/images22");
        // html = html.replace(/http:\/\/10.20.11.78:80\/images22/g, "/images22").replace(/\/images22/g, "http://10.20.11.78:80/images22");
        html = html.replace(/[<>&"]/g, function (c) {
            return {'<': '&lt;', '>': '&gt;', '&': '&amp;', '"': '&quot;'}[c];
        });
        $('#advertisingContent').val(html);
        var name = $(this).attr("name");
        validator = $advertisingAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "advertising/add", $advertisingAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "advertising/update", $advertisingAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
        $("#advertising-add-button").attr("disabled", false);
    });

    $("#advertising-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    CKEDITOR.instances.paperTitle.setData('');
    $("#advertising-add-button").attr("name", "save");
    $("#advertising-add-button").attr("disabled", false);
    $MB.closeAndRestModal("advertising-add");
    $advertisingTypeIdSelect.multipleSelect('setSelects', []);
    $advertisingTypeIdSelect.multipleSelect("refresh");
    validator.resetForm();
    $("#advertising-add-modal-title").html('新增广告');

}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $advertisingAddForm.validate({
        rules: {
            advertisingName: {
                required: true,
                minlength: 2,
                maxlength: 100,
                remote: {
                    url: "advertising/checkadvertisingName",
                    type: "get",
                    dataType: "json",
                    data: {
                        advertisingName: function () {
                            return $("input[name='advertisingName']").val().trim();
                        },
                        oldadvertisingName: function () {
                            return $("input[name='oldadvertisingName']").val().trim();
                        }
                    }
                }
            },
            advertisingNum: {
                required: true
            },
            advertisingTypeIdSelect: {
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
            advertisingName: {
                required: icon + "请输入广告名",
                minlength: icon + "广告名长度2到100个字符",
                remote: icon + "广告名已经存在"
            },
            advertisingNum: icon + "请输入广告优先级码",
            advertisingTypeIdSelect: icon + "请选择广告类型",
        }
    });
}


function initadvertisingType() {
    $.post(ctx + "advertisingtype/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].advertisingtype_id + "'>" + data[i].advertisingtype_name + "</option>"
        }
        $advertisingTypeIdSelect.html("").append(option);
        var options = {
            selectAllText: '所有广告类型',
            allSelected: '所有广告类型',
            width: '70%',
            single: true,//是否单选
            filter: true,//搜索
            onClose: function () {
                advertisingTypeId.val($advertisingTypeIdSelect.val());
                validator.element("input[name='advertisingTypeId']");
            }, onOpen: function () {//打开当下拉列表被打开时触发。
                $advertisingTypeIdSelect.multipleSelect('uncheckAll');
            },
        };

        $advertisingTypeIdSelect.multipleSelect(options);
    });
}