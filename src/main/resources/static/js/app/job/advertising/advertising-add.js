/*
$(function () {

});
*/
var editor1;
$(function () {
    KindEditor.ready(function (K) {
        editor1 = K.create('#paperTitle', {
            items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "code", "cut", "copy", "paste", "plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "multiimage", "flash", "media", "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak", "anchor", "link", "unlink", "|", "about"],
            cssPath: 'js/kindeditor/plugins/code/prettify.css',
            uploadJson: 'fileUpload',
            fileManagerJson: 'fileManager',
            allowFileManager: true,
            autoHeightMode: true,
            //关键所在，当失去焦点时执行this.sync()，同步输入的值到textarea中;
            /* afterBlur: function () {
                 this.sync();
             },*/
            /*afterCreate: function () {
                var self = this;//Ctrl+Enter提交表单
                K.ctrl(document, 13, function () {
                    self.sync();
                    K('form[name=example]')[0].submit();
                    //document.forms['example'].submit();
                });
                K.ctrl(self.edit.doc, 13, function () {
                    self.sync();
                    //document.forms['example'].submit();
                    K('form[name=example]')[0].submit();
                });
            }*/
        });
        prettyPrint();
       /* K('#example').bind('submit', function () {
            editor.sync();
        });*/
    });
});
/*function advertisingaa() {
    editor1.sync();//将KindEditor的数据同步到textarea标签。
    var html = document.getElementById('advertising_content').value;
    $.ajax({
        type: 'post',
        data: {"advertising_content": html.replace(/\/images22/g, "http://localhost:8081/images22")},
        url: 'advertising/add',
        cache: false,
        //dataType:'text',
        success: function () {
            KindEditor.html("#advertising_content", "");//清空
            closeModal();
        }
    })

}*/

/*$(function () {


    $("#advertising-add .btn-save").click(function () {
        advertisingaa();
        closeModal();
    });
    $("#advertising-add .btn-close").click(function () {
        closeModal();
    });
});*/
var validator;
var $advertisingAddForm = $("#advertising-add-form");
$(function () {
    // validateRule();

    $("#advertising-add .btn-save").click(function () {
        editor1.sync();//将KindEditor的数据同步到textarea标签。

        // var html = $("#paperTitle").value;
        var html = $('#paperTitle').val();
        html = html.replace(/http:\/\/www.niuxinghao.top:9080\/images22/g, "/images22").replace(/\/images22/g, "http://www.niuxinghao.top:9080/images22");
       // html = html.replace(/http:\/\/10.20.11.78:9080\/images22/g, "/images22").replace(/\/images22/g, "http://10.20.11.78:9080/images22");
        html= html.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
        $('#advertising_content').val(html);
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
    });

    $("#advertising-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    KindEditor.html("#paperTitle","");//给富文本赋值
    $("#advertising-add-button").attr("name", "save");
    //$MB.closeModal("advertising-add");
    $MB.closeAndRestModal("advertising-add");
    validator.resetForm();
    $("#advertising-add-modal-title").html('新增广告');

}