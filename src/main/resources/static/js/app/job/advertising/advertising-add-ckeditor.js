/*
$(function () {

});
*/

$(function () {
    /* var config = {
         extraPlugins: 'codesnippet,image2',
         codeSnippet_theme: 'monokai_sublime',
         filebrowserImageUploadUrl: 'ckeditor/uploadImage',
         /!*      removeButtons  :'Source,Save,NewPage,Scayt',*!/
         height: 560,
     };*/
    CKEDITOR.replace('paperTitle');
});


var validator;
var $advertisingAddForm = $("#advertising-add-form");
var advertisingTypeId = $advertisingAddForm.find("input[name='advertisingTypeId']");
var $advertisingTypeIdSelect = $advertisingAddForm.find("select[name='advertisingTypeIdSelect']");

$(function () {
    validateRule();
    initadvertisingType()
    $("#advertising-add .btn-save").click(function () {
        $("#advertising-add-button").attr("disabled", true);
        var html = CKEDITOR.instances.paperTitle.getData();//给富文本赋值
        html = html.replace(ctx + "/images22/g", "/images22").replace("/images22/g", ctx + "/images22");
        $('#advertisingContent').val(html);
        var name = $(this).attr("name");
        validator = $advertisingAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                alert($advertisingAddForm.serialize());
                //富文本内容不能够序列化
                $.post(ctx + "advertising/add", $advertisingAddForm.serialize()+'&advertisingContent='+html, function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                //富文本内容不能够序列化
                $.post(ctx + "advertising/update", {advertisingId:$('input[name="advertisingId"]').val(),advertisingContent:html,advertisingName:$('input[name="advertisingName"]').val(),advertisingTypeId:$('input[name="advertisingTypeId"]').val(),advertisingTitle:$('input[name="advertisingTitle"]').val(),advertisingRemarks:$('input[name="advertisingRemarks"]').val()}, function (r) {
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
    CKEDITOR.instances.paperTitle.setData('');//给富文本赋值
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