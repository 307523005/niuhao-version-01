$(function () {
    var $advertisingTableForm = $(".advertising-table-form");
    var settings = {
        url: ctx + "advertising/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                advertising_title: $advertisingTableForm.find("input[name='advertising_title']").val().trim(),
                advertising_updatetime: $advertisingTableForm.find("input[name='advertising_updatetime']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'advertising_id',
                title: '广告ID',
                width: 150,
                sortable: true,//列排序
                align: 'center'
            }, {
                field: 'advertising_name',
                title: '广告名称',
                align: 'center'
            }, {
                field: 'merchant_id',
                title: '商户码',
                align: 'center'
            },  {
                field: 'advertising_title',
                title: '标题',
                align: 'center'
            }/*,{
                field: 'advertising_content',
                title: '内容',
                align: 'center'
            }*/,{
                field: 'advertising_remarks',
                title: '备注',
                align: 'center'
            },{
                field: 'advertising_addtime',
                title: '添加时间',
                align: 'center'
            },{
                field: 'advertising_updatetime',
                title: '修改时间',
                align: 'center'
            },{
                field: 'advertising_updateuser',
                title: '操作用户',
                align: 'center'
            }, {
                field: 'advertising_content',
                title: '查看',
                formatter: function (value, row, index) {
                    return ' <button type="button" onclick="chakan(value)" class="btn">查看</button>';

                }
            }
        ]
    };

    $MB.initTable('advertisingTable', settings);
    $MB.calenders('input[name="advertising_updatetime"]', true, false);//日期
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
        ids += selected[i].advertising_id;
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