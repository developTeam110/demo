/**
 * Author:       wangjingkun
 * Description:  项目自定义全局常量
 */

var $document = $(document);

$(function () {

    /**
     * 全局变量 （为bootstrap-table定制）
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

            var selectedItems = self.$table.bootstrapTable('getSelections');
            self.deleteItems(selectedItems);
        },

        //批量删除or单条删除表中项方法
        deleteItems: function (selectedItems) {
        	console.log(this.$table);
        	var url = this.$table.attr("data-delete-url");

            if ($.isEmptyObject(selectedItems)) {
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
                $(".cancel").click();

                var usernameList = [];
                $.each(selectedItems, function(index, value) {
                    usernameList[index] = value.username;
                });

                $.ajax({
                    url: url,
                    data: {"usernameList":usernameList},
                    type: "POST",
                    traditional: true,
                    dataType: "json",
                    async: false,
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
