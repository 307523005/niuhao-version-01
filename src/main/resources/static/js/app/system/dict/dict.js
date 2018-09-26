$(function () {
    var $dictTableForm = $(".dict-table-form");
    var settings = {
        url: ctx + "dict/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                keyy: $dictTableForm.find("input[name='key']").val().trim(),
                valuee: $dictTableForm.find("input[name='value']").val().trim(),
                tableName: $dictTableForm.find("input[name='tableName']").val().trim(),
                fieldName: $dictTableForm.find("input[name='fieldName']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'dictId',
                title: '字典ID',
                width: 150,
                sortable: true,//列排序
                align: 'center'
            }, {
                field: 'keyy',
                title: '键',
                align: 'center'
            }, {
                field: 'valuee',
                title: '值',
                align: 'center'
            }, {
                field: 'tableName',
                title: '表名',
                align: 'center'
            }, {
                field: 'fieldName',
                title: '字段名',
                align: 'center'
            }
        ]
    };

    $MB.initTable('dictTable', settings);
});

function search() {
    $MB.refreshTable('dictTable');
}

function refresh() {
    $(".dict-table-form")[0].reset();
    search();
}

function deleteDicts() {
    var selected = $("#dictTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的字典！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].dictId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的字典？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'dict/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportDictExcel() {
    $.post(ctx + "dict/excel", $(".dict-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportDictCsv() {
    $.post(ctx + "dict/csv", $(".dict-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}