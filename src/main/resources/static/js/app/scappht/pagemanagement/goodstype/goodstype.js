$(function () {
    var $goodstypeTableForm = $(".goodstype-table-form");
    var settings = {
        url: ctx + "goodstype/list",
        queryParams: function (params) { //得到查询的参数
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                goodstype_name: $goodstypeTableForm.find("input[name='goodstype_name_like']").val().trim(),
                goodstype_updatetime: $goodstypeTableForm.find("input[name='goodstype_updatetime_like']").val().trim(),
                sort: params.sort,      //排序列名
                sortOrder: params.order //排位命令（desc，asc）
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'goodstype_name',
            title: '商品类型名称',
            align: 'center'
        }, {
            field: 'goodstype_num',
            title: '商品类型码',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'goodstype_htmlurl',
            title: '商品类型页面url',
            align: 'center',
        }, {
            field: 'goodstype_addtime',
            title: '添加时间',
            align: 'center'
        }, {
            field: 'goodstype_updatetime',
            title: '修改时间',
            align: 'center',
            sortable: true//列排序
        }, {
            field: 'goodstype_updateuser',
            title: '修改用户',
            align: 'center'
        }, {
                field: 'goodstype_remarks',
                title: '备注',
                align: 'center'
            }

        ]
    };

    $MB.initTable('goodstypeTable', settings);
    $MB.calenders('input[name="goodstype_updatetime"]', true, false);//日期
});

function search() {
    $MB.refreshTable('goodstypeTable');
}

function refresh() {
    $(".goodstype-table-form")[0].reset();
    search();
}

function deletegoodstype() {
    var selected = $("#goodstypeTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的商品类型！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].goodstype_id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的商品类型？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'goodstype/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportgoodstypeExcel() {
    $.post(ctx + "goodstype/excel", $(".goodstype-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportgoodstypeCsv() {
    $.post(ctx + "goodstype/csv", $(".goodstype-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}