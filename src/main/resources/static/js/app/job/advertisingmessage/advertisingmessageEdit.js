function updateadvertisingmessage() {
    var selected = $("#advertisingmessageTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要回复的留言！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能回复一个留言！');
        return;
    }
    var ids = selected[0].advmessageId;
    $.post(ctx + "advertisingmessage/getadvertisingmessage", {"advmessageId": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#advertisingmessage-add');
            $form.modal();
            var advertisingmessage = r.msg;
            $("#advertisingmessage-add-modal-title").html('回复留言');
           // $form.find("input[name='advmessageContent']").val(advertisingmessage.advmessageContent).attr("readonly", true);
            $form.find("input[name='advmessageAuthorcontent']").val(advertisingmessage.advmessageAuthorcontent);
            $form.find("input[name='advmessageId']").val(advertisingmessage.advmessageId);
            $("#advertisingmessage-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}