
var lineChartCanvas;
var areaChartCanvas;
var temps;
var times;
var hums;
var all_status = 0;
var light_status = 0;
var window_status = 0;
var title;
var datas;
var serviceId;
var room;
var count = 0;


$(function() {
    setInterval("ajaxData(room)",300000);
});


$("#service").click(function () {
    var name = $("#userName").text();
    // window.location.href="http://localhost:8080/serviceView?userName="+name;
    document.write("<form action='serviceView' method=post name=form1 style='display:none'>");
    document.write("<input type=hidden name=userName value='"+name+"'/>");
    document.write("</form>");
    document.form1.submit();
})


$('.lcs_check').lc_switch();
$('#lightDiv').delegate('.lcs_check', 'lcs-statuschange', function() {
    var status = ($(this).is(':checked')) ? 'checked' : 'unchecked';
    if(status == "checked"){

        light_status = 1;
    }else {
        light_status = 0;
    }
    count++;
    control();
});


$('#windowDiv').delegate('.lcs_check', 'lcs-statuschange', function() {
    var status = ($(this).is(':checked')) ? 'checked' : 'unchecked';
    // console.log('field changed status: '+ status );
    if(status == "checked"){
        window_status = 1;
    }else{
        window_status = 0;
    }
    count++;
    control();
});
$('#allDiv').delegate('.lcs_check', 'lcs-statuschange', function() {
    var status = ($(this).is(':checked')) ? 'checked' : 'unchecked';
    if(status == "checked"){
        all_status = 1;
    }else{
        all_status = 0;
    }
    count++;
    control();
});

$("#solve").click(function () {
    $.ajax({
        type:"POST",
        url:"/management/ajax/solve",
        dataType:"json",
        data:{id:serviceId},
        success:function (data) {
            // alert(data);
            if(data.statusCode == 0){
                alert(data.message);
                ajaxData(room);
            }else {
                alert(data.message);
                ajaxData(room)
            }

        },
        error:function (data) {
            alert(data.message);
            ajaxData(room);
        }
    })
})

$("#ignore").click(function () {
    $.ajax({
        type:"POST",
        url:"/management/ajax/ignore",
        dataType:"json",
        data:{id:serviceId},
        success:function (data) {
            // alert(data.message);
            if(data.statusCode == 0){
                alert(data.message);
                ajaxData(room);
            }else {
                alert(data.message);
                ajaxData(room)
            }

        },
        error:function (data) {
            alert(data.message);
            ajaxData(room);
        }
    })
})

$("#person").click(function () {
    $.ajax({
        type:"POST",
        url:"/management/ajax/isPerson",
        dataType:"json",
        data:{room:room},
        success:function (data) {
            if(data.statusCode == 0){
                alert(data.message);
            }else {
                alert(data.message);
            }

        },
        error:function (data) {
            alert(data.message);
        }
    })
})

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
                ajaxData(room);
            }else {
                alert(data.message);
                ajaxData(room)
            }

        },
        error:function (data) {
            alert(data.message);
            ajaxData(room);
        }
    })
    setTimeout("$('#myModal').modal('hide')", 1000)
})




$("#tree").on("click","li",function () {
    $(this).addClass("active").siblings().removeClass('active');
    // alert($(this).parent().find("i").attr("id"));
    var id = $(this).attr("id");

    if(id == "红棉楼"||id == "青枫阁"||id == "橙萱居"){
        // alert(id);
        $("#titleOne").text(id);

    }
    if(id != undefined && id != "红棉楼" && id != "青枫阁" && id != "橙萱居"){
        // alert($(this).attr("id"))
        $("#titleTwo").text(id);
        room = id;
        ajaxData(room);
    }
})


$("#logout").click(function () {
    // alert("33")
        if (confirm("是否退出系统")) {
            window.location.href="/loginView"
        }
})

function control() {
    if(count > 3){
    $.ajax({
        type:"POST",
        url:"/management/ajax/control",
        dataType:"json",
        data:{room:room,light:light_status,window:window_status,all:all_status},
        success:function (data) {
             // alert(data.message);
            if(data.statusCode == 0){
                alert(data.message);
            }else {
                alert(data.message);
                ajaxData(room)
            }

        },
        error:function (data) {
            alert(data.message);
            ajaxData(room);
        }
    })}
}


function ajaxData(room) {

    $.ajax({
        type: "POST",
        url: "/management/ajax/data",
        dataType: "json",
        data: {room: room},
        success: function (data) {
            console.log(data);
            times = data.data.times;
            temps = data.data.temps;
            hums = data.data.hums;
            all_status = data.data.all;
            light_status = data.data.light;
            window_status = data.data.window;
            title = data.data.serviceTitle;
            datas = data.data.serviceData;
            serviceId = data.data.serviceId;
            // alert(data.data.all);
            count = 0;
            canvasChar();
            setService();
            setStatus();
        }
    })
}

//清除画布
function clearCanvas() {
    $("#lineChart").remove();
    $("#areaChart").remove();
    $("#one").append('<canvas id="lineChart" style="height:250px"></canvas>');
    $("#two").append('<canvas id="areaChart" style="height:250px"></canvas>');
    lineChartCanvas = $('#lineChart').get(0).getContext('2d');
    areaChartCanvas = $('#areaChart').get(0).getContext('2d');
}

function setService() {
    // console.log(title);
    $("#serviceTitle").html(title);
    $("#serviceData").html(datas);
    $("#serviceId").val(serviceId);
}


function canvasChar() {
    clearCanvas();
    /* ChartJS
     * -------
     * Here we will create a few charts using ChartJS
     */

    //--------------
    //- AREA CHART -
    //--------------
    // Get context with jQuery - using jQuery's .get() method.
    // var areaChartCanvas = $('#areaChart').get(0).getContext('2d')
    // This will get the first returned node in the jQuery collection.


    var lineChartData = {
        labels:times,
        datasets: [{
            label: 'Digital Goods',
            fillColor: 'rgba(60,141,188,0.9)',
            strokeColor: 'rgba(36,147,110,0.8)',
            pointColor: '#3b8bba',
            pointStrokeColor: 'rgba(60,141,188,1)',
            pointHighlightFill: '#fff',
            pointHighlightStroke: 'rgba(60,141,188,1)',
            // data: [28, 48, 40, 19, 86, 27, 90]
            data:temps
        }]
    }
    var areaChartData = {
        labels:times,
        datasets: [{
            label: 'Digital Goods',
            fillColor: 'rgba(60,141,188,0.9)',
            strokeColor: 'rgba(60,141,188,0.8)',
            pointColor: '#3b8bba',
            pointStrokeColor: 'rgba(60,141,188,1)',
            pointHighlightFill: '#fff',
            pointHighlightStroke: 'rgba(60,141,188,1)',
            // data: [28, 48, 40, 19, 86, 27, 90]
            data:hums
        }]
    }
    var ChartOptions = {
        // scaleLabel : "<%=value%>℃",// 标签显示值，这里添加的单位是百分比
        //Boolean - If we should show the scale at all
        showScale: true,
        //Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines: false,
        //String - Colour of the grid lines
        scaleGridLineColor: 'rgba(0,0,0,.05)',
        //Number - Width of the grid lines
        scaleGridLineWidth: 1,
        //Boolean - Whether to show horizontal lines (except X axis)
        scaleShowHorizontalLines: true,
        //Boolean - Whether to show vertical lines (except Y axis)
        scaleShowVerticalLines: true,
        //Boolean - Whether the line is curved between points
        bezierCurve: true,
        //Number - Tension of the bezier curve between points
        bezierCurveTension: 0.3,
        //Boolean - Whether to show a dot for each point
        pointDot: false,
        //Number - Radius of each point dot in pixels
        pointDotRadius: 4,
        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth: 1,
        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius: 20,
        //Boolean - Whether to show a stroke for datasets
        datasetStroke: true,
        //Number - Pixel width of dataset stroke
        datasetStrokeWidth: 2,
        //Boolean - Whether to fill the dataset with a color
        // datasetFill: true,
        //String - A legend template
        legendTemplate: '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].lineColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
        //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
        maintainAspectRatio: true,
        //Boolean - whether to make the chart responsive to window resizing
        responsive: true
    }

    //Create the line chart


    //-------------
    //- LINE CHART -
    //--------------
    // var lineChartCanvas = $('#lineChart').get(0).getContext('2d')
    var lineChart = new Chart(lineChartCanvas);
    var areaChart = new Chart(areaChartCanvas);
    var lineChartOptions = ChartOptions;
    var areaChartOptions = ChartOptions;
    lineChartOptions.datasetFill = false;
    lineChartOptions.scaleLabel = "<%=value%>℃";
    lineChart.Line(lineChartData, lineChartOptions);

    areaChartOptions.datasetFill = true;
    areaChartOptions.scaleLabel = "<%=value%>%";
    areaChart.Line(areaChartData, areaChartOptions)

}

function setStatus() {
    if (light_status === 1) {
        $("#light_status").lcs_on();
    } else {
        $("#light_status").lcs_off();
    }
    if (window_status === 1) {
        $("#window_status").lcs_on();
    } else {
        $("#window_status").lcs_off();
    }
    if (all_status === 1) {
        $("#all_status").lcs_on();
    } else {
        $("#all_status").lcs_off();
    }
    console.log(light_status,window_status,all_status);
}