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
    var advertisingId = selected[0].advertisingId;
    $.post(ctx + "advertising/getadvertising", {"advertisingId": advertisingId}, function (r) {
        if (r.code === 0) {
            var $form = $('#advertising-add');
            $form.modal();
            var advertising = r.msg;
            var advertisingTypeIdArr = [];
            advertisingTypeIdArr.push(advertising.advertisingTypeId);
            $("#advertising-add-modal-title").html('修改广告');
            $form.find("input[name='advertisingName']").val(advertising.advertisingName);
            $form.find("input[name='merchantId']").val(advertising.merchantId);
            $form.find("input[name='advertisingContent']").val(advertising.advertisingContent);
            $form.find("input[name='advertisingRemarks']").val(advertising.advertisingRemarks);
            $form.find("input[name='advertisingTitle']").val(advertising.advertisingTitle);
            $form.find("input[name='advertisingAddtime']").val(advertising.advertisingAddtime);
            $form.find("input[name='advertisingId']").val(advertising.advertisingId);
            $form.find("select[name='advertisingTypeIdSelect']").multipleSelect('setSelects', advertisingTypeIdArr);
            $form.find("input[name='advertisingTypeId']").val($form.find("select[name='advertisingTypeIdSelect']").val());
            var  advertisingContent=advertising.advertisingContent;
            var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
            advertisingContent=advertisingContent.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
            KindEditor.html("#paperTitle",advertisingContent);//给富文本赋值
            $("#advertising-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}