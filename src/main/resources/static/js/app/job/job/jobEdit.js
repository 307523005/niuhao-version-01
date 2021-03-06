function updateJob() {
    var selected = $("#jobTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的任务！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个任务！');
        return;
    }
    var jobId = selected[0].jobId;
    $.post(ctx + "job/getJob", {"jobId": jobId}, function (r) {
        if (r.code === 0) {
            var $form = $('#job-add');
            $form.modal();
            var job = r.msg;
            $("#job-add-modal-title").html('修改任务');
            $form.find("input[name='jobId']").val(job.jobId);
            $form.find("input[name='status']").val(job.status);
            $form.find("input[name='job_name']").val(job.job_name);
            $form.find("input[name='remark']").val(job.remark);
            $form.find("input[name='beanName']").val(job.beanName);
            $form.find("input[name='params']").val(job.params);
            var cronExpressionArr = [];
            cronExpressionArr.push(job.cronExpression);
            $form.find("select[name='cronExpressionSelect']").multipleSelect('setSelects', cronExpressionArr);
            $form.find("input[name='cronExpression']").val(job.cronExpression);
            var methodNameArr = [];
            methodNameArr.push(job.methodName);
            $form.find("select[name='methodNameSelect']").multipleSelect('setSelects', methodNameArr);
            $form.find("input[name='methodName']").val(job.methodName);
            $("#job-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}