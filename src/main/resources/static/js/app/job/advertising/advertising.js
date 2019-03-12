$(function () {
    var $advertisingTableForm = $(".advertising-table-form");
    var pageSizes;
    var pageNumbers;
    var settings = {
        url: ctx + "advertising/list",
        queryParams: function (params) { //得到查询的参数
            pageSizes = params.limit;
            pageNumbers = params.offset / params.limit + 1;
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                advertisingTitle: $advertisingTableForm.find("input[name='advertisingTitle']").val().trim(),
                advertisingUpdatetime: $advertisingTableForm.find("input[name='advertisingUpdatetime']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        },
            {
                title: '序号',
                field: '',
                align: 'center',
                formatter: function (value, row, index) {
                    return pageSizes * (pageNumbers - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                }
            }, {
                field: 'advertisingContent',
                title: '查看',
                align: 'center',
                formatter: function (value, row, index) {
                    return ' <a target="_blank" href="https://www.niuxinghao.top/apphtml/scapp/advertising.html?merchant_id=' + row.merchantId + '&advertising_id=' + row.advertisingId + '" class="btn">查看</a>';
                    /*<button type="button" onclick="chakan(value)" class="btn">查看</button>*/
                }
            }/*, {
                field: 'advertisingName',
                title: '广告名称',
                align: 'center'
            }*/, {
                field: 'advertisingTitle',
                title: '标题',
                align: 'center'
            }, {
                field: 'advertisingHits',
                title: '阅读量',
                sortable: true,//列排序
                align: 'center'
            }, {
                field: 'advertisingTypeName',
                title: '广告类型',
                align: 'center'
            }, {
                field: 'merchantId',
                title: '商户码',
                align: 'center'
            }, {
                field: 'advertisingRemarks',
                title: '备注',
                align: 'center'
            }, {
                field: 'advertisingAddtime',
                title: '添加时间',
                align: 'center'
            }, {
                field: 'advertisingUpdatetime',
                sortable: true,//列排序
                title: '修改时间',
                align: 'center'
            }, {
                field: 'advertisingUpdateuser',
                title: '操作用户',
                align: 'center'
            }
        ]
    };

    $MB.initTable('advertisingTable', settings);
    $MB.calenders('input[name="advertisingUpdatetime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('advertisingTable');
}

function refresh() {
    $(".advertising-table-form")[0].reset();
    search();
}

function deleteadvertising() {
    var selected = $("#advertisingTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的广告！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].advertisingId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的广告？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'advertising/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportadvertisingExcel() {
    $.post(ctx + "advertising/excel", $(".advertising-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportadvertisingCsv() {
    $.post(ctx + "advertising/csv", $(".advertising-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}