function updateadvertisingtype() {
    var selected = $("#advertisingtypeTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的广告类型！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个广告类型！');
        return;
    }
    var ids = selected[0].advertisingtype_id;
    $.post(ctx + "advertisingtype/getadvertisingtype", {"advertisingtype_id": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#advertisingtype-add');
            $form.modal();
            var advertisingtype = r.msg;
            $("#advertisingtype-add-modal-title").html('修改广告类型');
            $form.find("input[name='advertisingtype_name']").val(advertisingtype.advertisingtype_name).attr("readonly", true);
            $form.find("input[name='oldadvertisingtype_name']").val(advertisingtype.advertisingtype_name);
            $form.find("input[name='advertisingtype_id']").val(advertisingtype.advertisingtype_id);
            $form.find("input[name='advertisingtype_num']").val(advertisingtype.advertisingtype_num);
            $form.find("input[name='advertisingtype_remarks']").val(advertisingtype.advertisingtype_remarks);
            $form.find("input[name='advertisingtype_imageurl']").val(advertisingtype.advertisingtype_imageurl);
            $("#advertisingtype-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}