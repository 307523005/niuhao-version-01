function updateadvertising() {
    var selected = $("#advertisingTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的广告！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个广告！');
        return;
    }
    var advertising_id = selected[0].advertising_id;
    $.post(ctx + "advertising/getadvertising", {"advertising_id": advertising_id}, function (r) {
        if (r.code === 0) {
            var $form = $('#advertising-add');
            $form.modal();
            var advertising = r.msg;
            $("#advertising-add-modal-title").html('修改广告');
            $form.find("input[name='advertising_name']").val(advertising.advertising_name);
            $form.find("input[name='merchant_id']").val(advertising.merchant_id);
            $form.find("input[name='advertising_content']").val(advertising.advertising_content);
            $form.find("input[name='advertising_remarks']").val(advertising.advertising_remarks);
            $form.find("input[name='advertising_title']").val(advertising.advertising_title);
            $form.find("input[name='advertising_addtime']").val(advertising.advertising_addtime);
            $form.find("input[name='advertising_id']").val(advertising.advertising_id);
            var  advertising_content=advertising.advertising_content;
            var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
            advertising_content=advertising_content.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
            KindEditor.html("#paperTitle",advertising_content);//给富文本赋值
            $("#advertising-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}