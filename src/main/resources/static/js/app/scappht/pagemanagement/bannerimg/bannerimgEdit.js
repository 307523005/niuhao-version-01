function updatebannerimg() {
    var selected = $("#bannerimgTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的轮播图！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个轮播图！');
        return;
    }
    var ids = selected[0].bannerimg_id;
    $.post(ctx + "bannerimg/getbannerimg", {"bannerimg_id": ids}, function (r) {
        if (r.code === 0) {
            var $form = $('#bannerimg-add');
            $form.modal();
            var bannerimg = r.msg;
            $("#bannerimg-add-modal-title").html('修改轮播图');
            $form.find("input[name='bannerimg_name']").val(bannerimg.bannerimg_name).attr("readonly", true);
            $form.find("input[name='oldbannerimg_name']").val(bannerimg.bannerimg_name);
            $form.find("input[name='bannerimg_id']").val(bannerimg.bannerimg_id);
            $form.find("input[name='bannerimg_num']").val(bannerimg.bannerimg_num);
            $form.find("input[name='bannerimg_htmlurl']").val(bannerimg.bannerimg_htmlurl);
            $form.find("input[name='bannerimg_remarks']").val(bannerimg.bannerimg_remarks);
            $form.find("input[name='bannerimg_imgurl']").val(bannerimg.bannerimg_imgurl);
            $("#bannerimg-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}