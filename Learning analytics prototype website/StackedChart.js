// this files is for creating stacked attendance charts with two values
function createStackedAttendance(canvasId, attended, missed){
  var ctx = document.getElementById(canvasId);
  var chart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: ["CSC1021", "CSC1022", "CSC1023", "CSC1024"],
    datasets: [{
      type: 'bar',
      label: 'Attended',
      backgroundColor: "green",
      data: attended
    },
    {
      type: 'bar',
      label: "Missed",
      backgroundColor: "grey",
      data: missed,
    }]
  },
  options: {
    legend: {display: false},
    scales: {
      xAxes: [{
        stacked: true
      }],
      yAxes: [{
        stacked: true
      }]
    }
  }
  });
}



