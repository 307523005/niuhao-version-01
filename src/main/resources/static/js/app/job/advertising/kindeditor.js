$(function () {
    KindEditor.ready(function (K) {
        K.create('#paperTitle', {
            items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "code", "cut", "copy", "paste", "plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", /*"multiimage", "flash", "media",*/ "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak", "anchor", "link", "unlink", "|", "about"],
            cssPath: 'js/kindeditor/plugins/code/prettify.css',
            uploadJson: 'fileUpload',
            fileManagerJson: 'fileManager',
            //allowFileManager: true,
            //autoHeightMode: true,
            minWidth: 200,
            minHeight: 200,
            cssData:'body img{max-width:500px}'
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