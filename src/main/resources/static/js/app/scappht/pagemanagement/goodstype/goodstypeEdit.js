function updategoodstype() {
    var selected = $("#goodstypeTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的商品类型！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个商品类型！');
        return;
    }
    var ids = selected[0].goodstype_id;
    $.post(ctx + "goodstype/getgoodstype", {"goodstype_id": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#goodstype-add');
            $form.modal();
            var goodstype = r.msg;
            $("#goodstype-add-modal-title").html('修改商品类型');
            $form.find("input[name='goodstype_name']").val(goodstype.goodstype_name).attr("readonly", true);
            $form.find("input[name='oldgoodstype_name']").val(goodstype.goodstype_name);
            $form.find("input[name='goodstype_id']").val(goodstype.goodstype_id);
            $form.find("input[name='goodstype_num']").val(goodstype.goodstype_num);
            $form.find("input[name='goodstype_htmlurl']").val(goodstype.goodstype_htmlurl);
            $form.find("input[name='goodstype_remarks']").val(goodstype.goodstype_remarks);
            $form.find("input[name='goodstype_imageurl']").val(goodstype.goodstype_imageurl);
            $("#goodstype-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}