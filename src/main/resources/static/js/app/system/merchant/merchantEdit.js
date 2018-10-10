function updatemerchant() {
    var selected = $("#merchantTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的商户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个商户！');
        return;
    }
    var ids = selected[0].merchant_id;
    $.post(ctx + "merchant/getmerchant", {"merchant_id": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#merchant-add');
            $form.modal();
            var merchant = r.msg;
            $("#merchant-add-modal-title").html('修改商户');
            $form.find("input[name='merchant_name']").val(merchant.merchant_name);
            $form.find("input[name='merchant_id']").val(merchant.merchant_id);
            $form.find("input[name='merchant_addtime']").val(merchant.merchant_addtime);
            $form.find("input[name='merchant_phone']").val(merchant.merchant_phone);
            $form.find("input[name='merchant_mail']").val(merchant.merchant_mail);
            $form.find("input[name='merchant_corp']").val(merchant.merchant_corp);
            $form.find("input[name='merchant_addr']").val(merchant.merchant_addr);
            $form.find("input[name='merchant_region']").val(merchant.merchant_region);
            $form.find("input[name='merchant_information']").val(merchant.merchant_information);
            $form.find("input[name='merchant_wximge']").val(merchant.merchant_wximge);
            $("#merchant-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}