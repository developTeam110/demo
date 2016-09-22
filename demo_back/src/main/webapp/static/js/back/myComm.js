/**
 * Author:       wangjingkun
 * Description:  项目自定义全局常量
 */

var $document = $(document);

$(function () {

	/**
	 * 自定义公共方法
	 */
    window.my = {

        //判断对象为空方法
        isEmpty: function (obj) {
            if (obj instanceof jQuery) { 
                return obj.length == 0;
            } else { 
                var name;
                for ( name in obj ) {
                    return false;
                }
                return true;
            }
        },

    };

    /**
     * 全局table变量 （为bootstrap-table定制）
     */
    window.myTable = {

        //每页显示数量
        PAGE_SIZE: 20,

        //标签宽度
        TAB_WIDTH: 35,
        TAB_ACTIVE_WIDTH: 100,

        //滚动最快最慢速度 毫秒
        SLOWEST: 1000,

        DELETE_URL: $("table").attr("data-delete-url"),

        //表对象
        $table: $("table"),

        $toolbar: $("#toolbar"),

        //初始化
        init: function () {
            this.bindEvent();
        },

        //绑定事件
        bindEvent: function () {
            $document.on("click", "#toolbar .JS_delete_btn", {self: this}, this.deleteItemsHandler);
        },

        //批量删除or单条删除表中项方法
        deleteItemsHandler: function (event) {
            var self = event.data.self;
            var $this = $(this);

            var checkedBoxs = self.$table.find(".bs-checkbox>input[name='btSelectItem']:checked");
            var uniqueids = [];
            $.each(checkedBoxs, function(index, checkedBox){
                uniqueids[index] = $(checkedBox).val();
            });

            self.deleteItems(uniqueids);
        },

        //批量删除or单条删除表中项方法
        deleteItems: function (uniqueids) {
            var url = this.DELETE_URL;
            if (my.isEmpty(uniqueids)) {
                swal({title: "没有任何项被选中",text: "至少选择一项进行删除操作"})
                return false;
            }

            swal({
                title: "您确定要删除这条信息吗",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "删除",
                cancelButtonText: "取消",
                closeOnConfirm: false
            }, function() {
                $(".sweet-alert .cancel").click();

                $.ajax({
                    url: url,
                    data: {"uniqueIds":uniqueids},
                    type: "POST",
                    traditional: true,
                    dataType: "json",
                    success: function(result) {
                        if (result.code == 1) {
                            $('button[name="refresh"]').click();
                            toastr.success("你已经删除了选中的数据!", "恭喜删除成功")
                        }else {
                            toastr.error(result.message)
                        }
                    },
                    error: function() {
                        toastr.error("网络连接异常，请稍后重试！")
                    }
                });

            })

        },

    };

});
