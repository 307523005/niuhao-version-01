var validator;
var $jobAddForm = $("#job-add-form");
var $cronExpressionSelect = $jobAddForm.find("select[name='cronExpressionSelect']");
var cron = $jobAddForm.find("input[name='cronExpression']");
var $methodNameSelect = $jobAddForm.find("select[name='methodNameSelect']");
var methodName = $jobAddForm.find("input[name='methodName']");
$(function () {
    validateRule();
    initmethodName();
    initCron();
    $("#job-add .btn-save").click(function () {
        $("#job-add-button").attr("disabled", true);
        var name = $(this).attr("name");
        validator = $jobAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "job/add", $jobAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "job/update", $jobAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
        $("#job-add-button").attr("disabled", false);
    });

    $("#job-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $cronExpressionSelect.multipleSelect('setSelects', []);
    $cronExpressionSelect.multipleSelect("refresh");

    $methodNameSelect.multipleSelect('setSelects', []);
    $methodNameSelect.multipleSelect("refresh");


    $("#job-add-button").attr("name", "save");
    $("#job-add-button").attr("disabled", false);

    $MB.closeAndRestModal("job-add");
    validator.resetForm();
    $("#job-add-modal-title").html('新增任务');
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $jobAddForm.validate({
        rules: {
            job_name: {
                required: true,
                maxlength: 100
            },
            methodName: {
                required: true,
            },
            cronExpression: {
                required: true,
                remote: {
                    url: "job/checkCron",
                    type: "get",
                    dataType: "json",
                    data: {
                        cron: function () {
                            return $("input[name='cronExpression']").val().trim();
                        }
                    }
                }
            }
        },
        messages: {
            job_name: {
                required: icon + "请输任务名称",
                maxlength: icon + "长度不能超过100个字符"
            },
            methodName: {
                required: icon + "请选择方法名称",
            },
            cronExpression: {
                required: icon + "请输入cron表达式",
                remote: icon + "cron表达式不合法"
            }
        }
    });
}

/*cron表达式列表*/
function initCron() {
    $.post(ctx + "cron/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].cron_content + "'>" + data[i].cron_remarks + "</option>"
        }
        $cronExpressionSelect.html("").append(option);
        var options = {
            selectAllText: '所有表达式',
            allSelected: '所有表达式',
            width: '100%',
            single: true,//是否单选
            filter: true,//搜索
            onClose: function () {
                cron.val($cronExpressionSelect.val());
                validator.element("input[name='cronExpression']");
            }, onOpen: function () {//打开当下拉列表被打开时触发。
                $cronExpressionSelect.multipleSelect('uncheckAll');
            },
        };

        $cronExpressionSelect.multipleSelect(options);
    });
}

/*定时执行方法列表*/
function initmethodName() {
    $.post(ctx + "job/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].methodName + "'>" + data[i].methodName + "</option>"

            /*option += "<option value='sendMail'>发送提醒邮件</option>";
            option += "<option value='test'>test</option>";*/
        }
        $methodNameSelect.html("").append(option);
        var options = {
            selectAllText: '所有方法',
            allSelected: '所有方法',
            width: '100%',
            single: true,//是否单选
            filter: true,//搜索
            onClose: function () {
                methodName.val($methodNameSelect.val());
                validator.element("input[name='methodName']");
            }, onOpen: function () {//打开当下拉列表被打开时触发。
                $methodNameSelect.multipleSelect('uncheckAll');
            },
        };

        $methodNameSelect.multipleSelect(options);
    });
}