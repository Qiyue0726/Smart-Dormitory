var serviceId = 0;
$("#tree").on("click","li",function () {
    $(this).addClass("active").siblings().removeClass('active');
    var id = $(this).attr("id");
    if(id != undefined && id != "home"){
        $("#titleTwo").text(id);
        if(id == "未解决") {
            $("#table").empty();
            var table = "<table id='info' class='table table-bordered table-hover'><thead>"+
                "<tr id='table_head'>"+
                "<th>id</th>"+
                "<th>类型</th>"+
                "<th>宿舍</th>"+
                "<th>标题</th>"+
                "<th>详细信息</th>"+
                "<th>报修时间</th>"+
                "<th>操作</th>"+
                "</tr>"+
                "</thead>"+
                "<tbody id='table_body'>"+
                "</tbody></table>"
            $("#table").append(table);
            initOne();
        }

        if(id == "已解决") {
            $("#table").empty();
            var table = "<table id='info' class='table table-bordered table-hover'><thead>"+
                "<tr id='table_head'>"+
                "<th>id</th>"+
                "<th>类型</th>"+
                "<th>宿舍</th>"+
                "<th>标题</th>"+
                "<th>详细信息</th>"+
                "<th>报修时间</th>"+
                "<th>处理时间</th>"+
                "</tr>"+
                "</thead>"+
                "<tbody id='table_body'>"+
                "</tbody></table>"
            $("#table").append(table);
                initTwo();
        }

        if(id == "忽略") {
            $("#table").empty();
            var table = "<table id='info' class='table table-bordered table-hover'><thead>"+
                "<tr id='table_head'>"+
                "<th>id</th>"+
                "<th>类型</th>"+
                "<th>宿舍</th>"+
                "<th>标题</th>"+
                "<th>详细信息</th>"+
                "<th>报修时间</th>"+
                "<th>处理时间</th>"+
                "<th>操作</th>"+
                "</tr>"+
                "</thead>"+
                "<tbody id='table_body'>"+
                "</tbody></table>"
            $("#table").append(table);
                initThree();
        }

    }else{
        $("#titleTwo").text("Here");
    }
})

$("#logout").click(function () {
    // alert("33")
    if (confirm("是否退出系统")) {
        window.location.href="/loginView"
    }
})

$("#management").click(function () {
    var name = $("#userName").text();
    // window.location.href="http://localhost:8080/managementView?userName="+name;
    document.write("<form action='managementView' method=post name=form1 style='display:none'>");
    document.write("<input type=hidden name=userName value='"+name+"'/>");
    document.write("</form>");
    document.form1.submit();
})

function initOne() {
    option();
    $.ajax({
        type: "GET",
        url: "/service/getFinishing",
        dataType: "json",
        success: function (data) {
            var table = "";
            if (data.statusCode == 0) {

                $("#info").dataTable().fnClearTable();
                $.each(data.data,function(i,item){
                    var type = "";
                    switch(item.servicetype){
                        case 1: type = "网络"; break;
                        case 2: type = "阳台"; break;
                        case 3: type = "空调"; break;
                        case 4: type = "其他"; break;
                    };
                    console.log(item.systemtime);

                    var btn = "<div><button id='solve' onclick='solve()' class='btn btn-block btn-success '>已处理</button>"
                        +"<button onclick='getId()'  class='btn btn-block btn-warning' data-toggle='modal' data-target='#myModal'>回复</button></div>";
                    table = $("#info").DataTable().row.add([
                        item.id,
                        type,
                        item.room,
                        item.servicetitle,
                        item.servicedata,
                        item.systemtime,
                        btn
                    ]).draw();
                });

            } else {
                alert(data.message);
            }

        }
    })
}

function initTwo() {
    option();
    $.ajax({
        type: "GET",
        url: "/service/getFinish",
        dataType: "json",
        success: function (data) {
            var table = "";
            if (data.statusCode == 0) {

                $("#info").dataTable().fnClearTable();
                $.each(data.data,function(i,item){
                    var type = "";
                    switch(item.servicetype){
                        case 1: type = "网络"; break;
                        case 2: type = "阳台"; break;
                        case 3: type = "空调"; break;
                        case 4: type = "其他"; break;
                    };
                    table = $("#info").DataTable().row.add([
                        item.id,
                        type,
                        item.room,
                        item.servicetitle,
                        item.servicedata,
                        item.systemtime,
                        item.chulitime
                    ]).draw();
                });

            } else {
                alert(data.message);
            }

        }
    })
}

function initThree() {
    option();
    $.ajax({
        type: "GET",
        url: "/service/getIgnore",
        dataType: "json",
        success: function (data) {
            var table = "";
            if (data.statusCode == 0) {

                $("#info").dataTable().fnClearTable();
                $.each(data.data,function(i,item){
                    var type = "";
                    switch(item.servicetype){
                        case 1: type = "网络"; break;
                        case 2: type = "阳台"; break;
                        case 3: type = "空调"; break;
                        case 4: type = "其他"; break;
                    };
                    var btn = "<div><button id='recovery' onclick='recovery()' class='btn btn-block btn-primary '>恢复</button></div>";
                    table = $("#info").DataTable().row.add([
                        item.id,
                        type,
                        item.room,
                        item.servicetitle,
                        item.servicedata,
                        item.systemtime,
                        item.chulitime,
                        btn
                    ]).draw();
                });

            } else {
                alert(data.message);
            }

        }
    })
}

function getId() {
    var table = $('#info').DataTable();
    var datas = "";
    var count = 0;

    $('#info tbody').on( 'click', 'tr', function () {
        count++;
        if(count == 1) {
            datas = table.row(this).data();
            serviceId = datas[0];
            console.log(serviceId);
        }
    });
    count = 0;
}

function solve(){
    var table = $('#info').DataTable();
    var datas = "";
    var id = 0;
    var count = 0;

    $('#info tbody').on( 'click', 'tr', function () {
        count++;
        if(count == 1){
            datas = table.row( this ).data();
            id = datas[0];
            console.log(id);
            $.ajax({
                type:"POST",
                url:"/management/ajax/solve",
                dataType:"json",
                data:{id:id},
                success:function (data) {
                    // alert(data);
                    if(data.statusCode == 0){
                        alert(data.message);
                        initOne();
                    }else {
                        alert(data.message);
                        initOne();
                    }

                },
                error:function (data) {
                    alert(data.message);
                    initOne();
                }
            })
        }

    } );
    count = 0;
}

function ignore(){
    var table = $('#info').DataTable();
    var datas = "";
    var id = 0;
    var count = 0;

    $('#info tbody').on( 'click', 'tr', function () {
        count++;
        if(count == 1){
            datas = table.row( this ).data();
            id = datas[0];
            // console.log(id);
            $.ajax({
                type:"POST",
                url:"/management/ajax/ignore",
                dataType:"json",
                data:{id:id},
                success:function (data) {
                    if(data.statusCode == 0){
                        alert(data.message);
                        initOne();
                    }else {
                        alert(data.message);
                        initOne();
                    }

                },
                error:function (data) {
                    alert(data.message);
                    initOne();
                }
            })
        }

    } );
    count = 0;
}

function recovery() {
    var table = $('#info').DataTable();
    var datas = "";
    var id = 0;
    var count = 0;

    $('#info tbody').on( 'click', 'tr', function () {
        count++;
        if(count == 1){
            datas = table.row( this ).data();
            id = datas[0];
            // console.log(id);
            $.ajax({
                type:"POST",
                url:"/service/recovery",
                dataType:"json",
                data:{id:id},
                success:function (data) {
                    if(data.statusCode == 0){
                        alert(data.message);
                        initThree();
                    }else {
                        alert(data.message);
                        initThree();
                    }

                },
                error:function (data) {
                    alert(data.message);
                    initThree();
                }
            })
        }

    } );
    count = 0;
}

$("#reply").click(function () {
    var result = $("#result").val();
    $.ajax({
        type:"POST",
        url:"/service/reply",
        dataType:"json",
        data:{id:serviceId,reply:result},
        success:function (data) {
            if(data.statusCode == 0){
                alert(data.message);
                initOne();
            }else {
                alert(data.message);
                initOne();
            }

        },
        error:function (data) {
            alert(data.message);
            initOne();
        }
    })
    setTimeout("$('#myModal').modal('hide')", 1000)
})

function option(){
    $('#info').DataTable({
        'paging'      : true,
        'lengthChange': false,
        'searching'   : false,
        'ordering'    : true,
        'info'        : true,
        'autoWidth'   : true,
        'destroy'     :true,
        'iDisplayLength': 5, //每页初始显示5条记录
        // 'columnDefs': [{
        //     orderable:false,//禁用排序
        //     targets:[0,5]   //指定的列
        // }],

        "columnDefs": [{
            "targets": [0], //隐藏第0列，从第0列开始
            "visible": false
        }],
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }

    })
}
$(function () {
    option();
})