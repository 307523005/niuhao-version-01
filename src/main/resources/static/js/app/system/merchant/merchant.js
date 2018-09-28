$(function () {
    var $merchantTableForm = $(".merchant-table-form");
    var settings = {
        url: ctx + "merchant/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                merchant_name: $merchantTableForm.find("input[name='merchant_name']").val().trim(),
                merchant_addtime: $merchantTableForm.find("input[name='merchant_addtime']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'merchant_name',
            title: '商户名称',
            align: 'center'
        }, {
            field: 'merchant_id',
            title: '商户码',
            align: 'center'
        }, {
            field: 'merchant_phone',
            title: '电话',
            align: 'center'
        }/*,{
                field: 'merchant_content',
                title: '内容',
                align: 'center'
            }*/, {
            field: 'merchant_mail',
            title: '邮箱',
            align: 'center'
        }, {
            field: 'merchant_corp',
            title: '法人',
            align: 'center'
        }
        , {
            field: 'merchant_addr',
            title: '地址',
            align: 'center'
        }
        , {
            field: 'merchant_region',
            title: '地区（简写）',
            align: 'center'
        }
        , {
            field: 'merchant_information',
            title: '工商信息',
            align: 'center'
        }, {
            field: 'merchant_addtime',
            title: '添加时间',
            align: 'center',
            sortable: true//列排序
        }
        ]
    };

    $MB.initTable('merchantTable', settings);
    $MB.calenders('input[name="merchant_addtime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('merchantTable');
}

function refresh() {
    $(".merchant-table-form")[0].reset();
    search();
}

function deletemerchant() {
    var selected = $("#merchantTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的商户！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].merchant_id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的商户？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'merchant/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportmerchantExcel() {
    $.post(ctx + "merchant/excel", $(".merchant-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportmerchantCsv() {
    $.post(ctx + "merchant/csv", $(".merchant-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}