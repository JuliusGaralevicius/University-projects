// This file was used to generate HTML and Javascript for the "Attendance" part of the website;

months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
function generateBody(elementId){
    var returnString = "";
    // loop through the array of months
    for (var i=0; i<months.length; i++){
        // generate needed arrays and calculate their sums
        var attended = randomArray(4, 50);
        var totalAttended = arraySum(attended);
        var missed = randomArray(4, 50);
        var totalMissed = arraySum(missed);
        
        
        // Create an elements with data depending on the values of the loop
        returnString+="<div class=\"col-md-12\">"+"\n";
        returnString+="<div class = \"well\">"+"\n";
        returnString+="<h3>" +months[i] +"</h3>"+"\n"+"<br/>"+"\n";
        returnString+="<div class =\"row\">"+"\n";
        returnString+="<div class = \"col-md-6\">"+"\n";
        returnString+="<h4>Attendance by module</h4>"+"\n" + "<br/>" + "\n";
        returnString+="<canvas id =\"stackedCanvas" +i+"\">" + "\n";
        returnString+="<br/>" + "\n";
        returnString+="<script type = \"text/javascript\">" + "\n";
        
        returnString+="createStackedAttendance(\"stackedCanvas" +i +"\"," +"["+attended.toString()+"]" +"," +"[" +missed.toString() +"]);" +"\n" +
                    "</script>" +"\n" +
                    "</div>" +"\n" +
                    "<h4>Total attendance</h4>"+"\n" +
                    "<div class = \"col-md-6\">"+"\n" +
                    
                    "<canvas id =\"totalCanvas" +i +"\">"+ "\n"+
                    "<script type =\"text/javascript\">"+ "\n"+
                        "createDoughnut(" +totalAttended +", " +(totalAttended+totalMissed) +", \"totalCanvas" +i + "\" );" + "\n" +
                    "</script>" + "\n" +
                    "</canvas>" + "\n" +
                    "</div>" + "\n" +
                "</div>" + "\n" +
        "</div>" + "\n" +
    "</div>" + "\n";
    }
    // Put generated string on the document
    document.getElementById(elementId).innerHTML=returnString;
}
function arraySum(array){
    var sum=0;
    for (var i=0; i<array.length; i++){
        sum+=array[i];
    }
    return sum;
}
function randomArray(size, range){
    var returnArray = new Array();
    for (var i = 0; i<size; i++){
        returnArray.push(Math.round(Math.random()*range));
    }
    return returnArray;
}

