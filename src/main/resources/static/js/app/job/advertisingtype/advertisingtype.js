$(function () {
    var $advertisingtypeTableForm = $(".advertisingtype-table-form");
    var pageSizes;
    var pageNumbers;
    var settings = {
        url: ctx + "advertisingtype/list",
        queryParams: function (params) { //得到查询的参数
            pageSizes=params.limit;
            pageNumbers=params.offset / params.limit + 1;
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                advertisingtype_name: $advertisingtypeTableForm.find("input[name='advertisingtype_name']").val().trim(),
                advertisingtype_updatetime: $advertisingtypeTableForm.find("input[name='advertisingtype_updatetime']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        }, {
            title: '序号',
            field: '',
            align: 'center',
            formatter: function (value, row, index) {
                return pageSizes * (pageNumbers - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            }
        },{
            field: 'advertisingtype_name',
            title: '广告类型名称',
            align: 'center'
        }, {
            field: 'advertisingtype_num',
            title: '广告类型优先级',
            align: 'center',
            sortable: true//列排序
        },{
            field: 'advertisingtype_addtime',
            title: '添加时间',
            align: 'center'
        }, {
            field: 'advertisingtype_updatetime',
            title: '修改时间',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'advertisingtype_updateuser',
            title: '修改用户',
            align: 'center'
        }, {
            field: 'advertisingtype_remarks',
            title: '备注',
            align: 'center'
        }

        ]
    };

    $MB.initTable('advertisingtypeTable', settings);
    $MB.calenders('input[name="advertisingtype_updatetime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('advertisingtypeTable');
}

function refresh() {
    $(".advertisingtype-table-form")[0].reset();
    search();
}

function deleteadvertisingtype() {
    var selected = $("#advertisingtypeTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的广告类型！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].advertisingtype_id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的广告类型？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'advertisingtype/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportadvertisingtypeExcel() {
    $.post(ctx + "advertisingtype/excel", $(".advertisingtype-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportadvertisingtypeCsv() {
    $.post(ctx + "advertisingtype/csv", $(".advertisingtype-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}