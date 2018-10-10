function updategoods() {
    var selected = $("#goodsTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的商品！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个商品！');
        return;
    }
    var ids = selected[0].goods_id;
    $.post(ctx + "goods/getgoods", {"goods_id": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#goods-add');
            $form.modal();
            var goods = r.msg;
            var goodstype_idArr = [];
            goodstype_idArr.push(goods.goodstype_id);
            $("#goods-add-modal-title").html('修改商品');
            $form.find("input[name='goods_name']").val(goods.goods_name).attr("readonly", true);
            $form.find("input[name='oldgoods_name']").val(goods.goods_name);
            $form.find("input[name='goods_id']").val(goods.goods_id);
            $form.find("input[name='goods_num']").val(goods.goods_num);
            $form.find("input[name='goods_remarks']").val(goods.goods_remarks);
            $form.find("input[name='goods_littleimge']").val(goods.goods_littleimge);
            $form.find("input[name='goods_bigimge']").val(goods.goods_bigimge);

            $form.find("input[name='goods_activity']").val(goods.goods_activity);
            $form.find("input[name='goods_price']").val(goods.goods_price);
            $form.find("input[name='goods_units']").val(goods.goods_units);
            $form.find("input[name='goods_intro']").val(goods.goods_intro);
            $form.find("select[name='goodstype_idSelect']").multipleSelect('setSelects', goodstype_idArr);
            $form.find("input[name='goodstype_id']").val($form.find("select[name='goodstype_idSelect']").val());
            $("#goods-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}