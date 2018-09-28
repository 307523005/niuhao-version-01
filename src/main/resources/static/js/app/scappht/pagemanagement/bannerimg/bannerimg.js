$(function () {
    var $bannerimgTableForm = $(".bannerimg-table-form");
    var settings = {
        url: ctx + "bannerimg/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                bannerimg_name: $bannerimgTableForm.find("input[name='bannerimg_name']").val().trim(),
                bannerimg_updatetime: $bannerimgTableForm.find("input[name='bannerimg_updatetime']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'bannerimg_name',
            title: '轮播图名称',
            align: 'center'
        }, {
            field: 'bannerimg_num',
            title: '轮播图码',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'bannerimg_addtime',
            title: '添加时间',
            align: 'center'
        }, {
            field: 'bannerimg_updatetime',
            title: '修改时间',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'bannerimg_updateuser',
            title: '修改用户',
            align: 'center'
        }
            , {
                field: 'bannerimg_remarks',
                title: '备注',
                align: 'center'
            }

        ]
    };

    $MB.initTable('bannerimgTable', settings);
    $MB.calenders('input[name="bannerimg_updatetime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('bannerimgTable');
}

function refresh() {
    $(".bannerimg-table-form")[0].reset();
    search();
}

function deletebannerimg() {
    var selected = $("#bannerimgTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的轮播图！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].bannerimg_id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的轮播图？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'bannerimg/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportbannerimgExcel() {
    $.post(ctx + "bannerimg/excel", $(".bannerimg-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportbannerimgCsv() {
    $.post(ctx + "bannerimg/csv", $(".bannerimg-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}