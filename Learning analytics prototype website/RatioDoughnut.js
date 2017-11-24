// this file generate a simple doughnut based on given value and maximum value
function createDoughnut(result, maximum, CanvasId){

    var multiplier = result/maximum;
    // Increase value of green when result/maximum ratio approaches 1
    var G = Math.round(220 * multiplier);
    
    // Increase value of red when result/maximum ratio approaches 0
    var R = Math.round(220 * (1-multiplier));
    color = "rgba(" +R +", " + G + ", 0, 1)"
    var ctx = document.getElementById(CanvasId).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            datasets: [{
            backgroundColor: [color, "rgba(192,192,192,1)"],
            data: [result, maximum-result]
            }]
        },
        options:{
            responsive: true,
            // The chart does not scale with parent element
            maintainAspectRatio: true,
            // No need to display tooltips since it's a percentage doughnut
            tooltips: {enabled: false},
        }
    });
    return myChart;
}

