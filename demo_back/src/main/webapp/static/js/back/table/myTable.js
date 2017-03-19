function initTable(requestUrl){
    var $table = $('#table');
    $table.bootstrapTable({
        url: requestUrl,
        method: 'get',      //请求方式（*）  
        height:600,
        toolbar: '#toolbar',    //工具按钮用哪个容器  
        striped: true,      //是否显示行间隔色  
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）  
        pagination: true,     //是否显示分页（*）  
        sortable: false,      //是否启用排序  
        sortOrder: "asc",     //排序方式  
        //queryParams: queryParams,//传递参数（*）  
        pageNumber:1,      //初始化加载第一页，默认第一页  
        pageSize: 20,      //每页的记录行数（*）  
        pageList: [10, 20, 50, 100],  //可供选择的每页的行数（*）  
        //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端  
        //strictSearch: true,
        //showColumns: true,     //是否显示所有的列  
        showRefresh: true,     //是否显示刷新按钮  
        minimumCountColumns: 2,    //最少允许的列数  
        uniqueId: "id",      //每一行的唯一标识，一般为主键列  
        showToggle:false,     //是否显示详细视图和列表视图的切换按钮  
        sidePagination: "server" //服务端处理分页 
      });

    return $table;
}  