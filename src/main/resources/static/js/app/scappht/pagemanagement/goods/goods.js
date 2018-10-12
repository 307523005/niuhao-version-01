$(function () {
    var $goodsTableForm = $(".goods-table-form");
    var settings = {
        url: ctx + "goods/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                goods_name: $goodsTableForm.find("input[name='goods_name_like']").val().trim(),
                goods_updatetime: $goodsTableForm.find("input[name='goods_updatetime_like']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'goods_name',
            title: '商品名称',
            align: 'center'
        }, {
            field: 'goods_num',
            title: '商品优先级',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'goods_price',
            title: '商品价格',
            align: 'center',
        }, {
            field: 'goods_units',
            title: '商品单位',
            align: 'center',
        }, {
            field: 'goods_activity',
            title: '商品活动',
            align: 'center',
        }, {
            field: 'goods_intro',
            title: '商品简介',
            align: 'center',
        }







        , {
            field: 'goods_addtime',
            title: '添加时间',
            align: 'center'
        }, {
            field: 'goods_updatetime',
            title: '修改时间',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'goods_updateuser',
            title: '修改用户',
            align: 'center'
        }, {
                field: 'goods_remarks',
                title: '备注',
                align: 'center'
            }

        ]
    };

    $MB.initTable('goodsTable', settings);
    $MB.calenders('input[name="goods_updatetime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('goodsTable');
}

function refresh() {
    $(".goods-table-form")[0].reset();
    search();
}

function deletegoods() {
    var selected = $("#goodsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的商品！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].goods_id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的商品？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'goods/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportgoodsExcel() {
    $.post(ctx + "goods/excel", $(".goods-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportgoodsCsv() {
    $.post(ctx + "goods/csv", $(".goods-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}