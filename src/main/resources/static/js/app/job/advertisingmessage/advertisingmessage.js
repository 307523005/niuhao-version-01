$(function () {
    var $advertisingmessageTableForm = $(".advertisingmessage-table-form");
    var pageSizes;
    var pageNumbers;
    var settings = {
        url: ctx + "advertisingmessage/list",
        queryParams: function (params) { //得到查询的参数
            pageSizes=params.limit;
            pageNumbers=params.offset / params.limit + 1;
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                advertisingTitle: $advertisingmessageTableForm.find("input[name='advertisingTitle']").val().trim(),
                advmessageAddtime: $advertisingmessageTableForm.find("input[name='advmessageAddtime']").val().trim(),
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
            field: 'advertisingTitle',
            title: '广告名称',
            align: 'center'
        },{
            field: 'advmessageContent',
            title: '留言内容',
            align: 'center'
        }, {
            field: 'advmessageAddtime',
            title: '留言时间',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'advmessageAuthorcontent',
            title: '回复内容',
            align: 'center'
        }, {
            field: 'advmessageUpdateuser',
            title: '修改用户',
            align: 'center'
        }

        ]
    };

    $MB.initTable('advertisingmessageTable', settings);
    $MB.calenders('input[name="advmessageAddtime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('advertisingmessageTable');
}

function refresh() {
    $(".advertisingmessage-table-form")[0].reset();
    search();
}

function deleteadvertisingmessage() {
    var selected = $("#advertisingmessageTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的留言！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].advmessageId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的留言？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'advertisingmessage/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportadvertisingmessageExcel() {
    $.post(ctx + "advertisingmessage/excel", $(".advertisingmessage-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportadvertisingmessageCsv() {
    $.post(ctx + "advertisingmessage/csv", $(".advertisingmessage-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}