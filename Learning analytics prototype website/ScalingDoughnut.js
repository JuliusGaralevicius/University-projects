// Same as Ratio dougnut, instead scales with parent element
function createDoughnut(result, maximum, CanvasId){
    var multiplier = result/maximum;
    var G = Math.round(220 * multiplier);
    var R = Math.round(220 * (1-multiplier));
    color = "rgba(" +R +", " + G + ", 0, 1)"
    
    var ctx = document.getElementById(CanvasId).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ["Attended", "Missed"],
            datasets: [{
            backgroundColor: [color, "rgba(192,192,192,1)"],
            data: [result, maximum-result]
            }]
        },
        options:{
            responsive: true,
            maintainAspectRatio: false,
            legend: {display:false}
        }
    });
    return myChart;
}

