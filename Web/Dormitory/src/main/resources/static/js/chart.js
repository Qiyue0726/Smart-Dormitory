var canvasChart;
var temps;
var times;
var hums;
var room;
var line = "line";
var area = "area";

//获取URL参数
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);

room = $.getUrlParam("room");
// console.log(room);
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
            canvasChar(line);
        }
    })

$("#hum").click(function () {
    canvasChar(area);
})
$("#temp").click(function () {
    canvasChar(line);
})



//清除画布
function clearCanvas() {
    $("#canvasChart").remove();
    $("#chart").append('<canvas id="canvasChart" style="height:300px"></canvas>');
    canvasChart = $('#canvasChart').get(0).getContext('2d');
}

function canvasChar(type) {
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


    if(type == line){
        var lineChart = new Chart(canvasChart);
        var lineChartOptions = ChartOptions;
        $("#type").text("温度：");
        lineChartOptions.datasetFill = false;
        lineChartOptions.scaleLabel = "<%=value%>℃";
        lineChart.Line(lineChartData, lineChartOptions);
    }else{
        var areaChart = new Chart(canvasChart);
        var areaChartOptions = ChartOptions;
        $("#type").text("湿度：");
        areaChartOptions.datasetFill = true;
        areaChartOptions.scaleLabel = "<%=value%>%";
        areaChart.Line(areaChartData, areaChartOptions)
    }



}