$(function () {
    KindEditor.ready(function (K) {
        K.create('#paperTitle', {
            items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "code", "cut", "copy", "paste", "plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", /*"multiimage", "flash", "media",*/ "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak", "anchor", "link", "unlink", "|", "about"],
            cssPath: 'js/kindeditor/plugins/code/prettify.css',
            uploadJson: 'fileUpload',
            fileManagerJson: 'fileManager',
            resizeType  :2,  // 2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
            pasteType : 2,//设置粘贴类型，0:禁止粘贴, 1:纯文本粘贴, 2:HTML粘贴
            //allowFileManager: true,
            //autoHeightMode: true, //开启自动高度模式
            minWidth: 200,
            minHeight: 200,
            cssData:'body img{max-width:500px}',
            previewEmoticons: true, // 是否可以预览表情 的 gif动态图形
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