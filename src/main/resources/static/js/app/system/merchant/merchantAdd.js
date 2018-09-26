
var validator;
var $merchantAddForm = $("#merchant-add-form");
$(function () {
    // validateRule();

    $("#merchant-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $merchantAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "merchant/add", $merchantAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "merchant/update", $merchantAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#merchant-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#merchant-add-button").attr("name", "save");
    $("#merchant-add-modal-title").html('新增商户');
    validator.resetForm();
    $MB.closeAndRestModal("merchant-add");
}