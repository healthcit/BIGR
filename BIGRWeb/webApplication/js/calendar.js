
// Entry Point
// Opens popup calendar screen, that allows selecting Variable Precision
// Date (i.e. yyyy or mm/yyyy or mm/dd/yyyy). Updates fullDateFldName 
// with date selected. 
//
// yearBtn: setting this value to 'true' will display a button on the 
//          popup with year as the caption. Pressing this button, will
//          return only year selected on the popup.
//
// monthBtn: setting this value to 'true' will display a button on the 
//          popup with year and month as the caption. Pressing this 
//          button, will return only year and month selected on the 
//          popup.
//
// fullDateFldName: the name of the control that accepts date 
//
function openCalendar (fullDateFldName, yearBtn, monthBtn, index)
{
   var showYearBtn = "";
   var showMonthBtn = "";
      
   if (openCalendar.arguments.length == 1) {
   		showYearBtn = false;
   		showMonthBtn = false;
   }
   else if (openCalendar.arguments.length == 3) {
   		showYearBtn = yearBtn;
   		showMonthBtn = monthBtn;
   }
   
   calFullDateFld = fullDateFldName;

   var URL = "/BIGR/calendar.html";

   var dateValue = "";
   
   if (openCalendar.arguments.length == 4) {
   	  dateValue = document.all[calFullDateFld][index].value;
   }
   else {
   	  dateValue = document.all(calFullDateFld).value;
   }
   var args = new Array(4);

   args[0] = "";
   args[1] = "";
   args[2] = "";
   
   if ((dateValue == null) || (dateValue == "")) {
   		var today = new Date();
   		args[0] = today.getFullYear();
   		args[1] = today.getMonth() + 1;
   		args[2] = today.getDate();
   }
   
   
   if ((dateValue != null)&& (dateValue.length > 0)) {
		var dateString = new String(dateValue.toString());
		var dateValues = new Array();
		dateValues = dateString.split("/");
		if (dateValues.length == 3 
			&& (dateValues[2] >= 1900 && dateValues[2]<=2099)
			&& (dateValues[1] > 0 && dateValues[1]<=31)
			&& (dateValues[0] > 0 && dateValues[0]<=12)) {
	   		args[0] = dateValues[2];
	   		if ((dateValues[0].length == 2) && (dateValues[0].substring(0,1) == "0")) {
	   			args[1] = dateValues[0].substring(2,1);
	   		}
	   		else {
	   			args[1] = dateValues[0];
	   		}
	   		if ((dateValues[1].length == 2) && (dateValues[1].substring(0,1) == "0")) {
	   			args[2] = dateValues[1].substring(2,1);
	   		}
	   		else {
	   			args[2] = dateValues[1];
	   		}
	   	}
		else if (dateValues.length == 2 
			&& (dateValues[1] >= 1900 && dateValues[1]<=2099)
			&& (dateValues[0] > 0 && dateValues[0]<=12)) {
	   		args[0] = dateValues[1];
	   		if ((dateValues[0].length == 2) && (dateValues[0].substring(0,1) == "0")) {
	   			args[1] = dateValues[0].substring(2,1);
	   		}
	   		else {
	   			args[1] = dateValues[0];
	   		}
	   	}
		else if (dateValues.length == 1 
			&& (dateValues[0] >= 1900 && dateValues[0]<=2099)) {
	   		args[0] = dateValues[0];
	   	}
	   	else { // garbage entered in date field
	   		var today = new Date();
	   		args[0] = today.getFullYear();
	   		args[1] = today.getMonth() + 1;
	   		args[2] = today.getDate();
	   	}
   }

   args[3] = showMonthBtn;
   args[4] = showYearBtn;

   var retVal = window.showModalDialog(URL, args, "scroll:no;status:no;resizable:no;help:no;dialogWidth:325px;dialogHeight:300px;dialogTop:400px;dialogLeft:500px");
   if (openCalendar.arguments.length == 4) {
     fillDate(retVal, index);
   }
   else {
     fillDate(retVal);
   }
}


// fill the calling forms date, month and year, using value from the
// calendar dialog's returnValue property.  If the returnValue is false
// it means the dialog was closed without selecting a date -- we do
// nothing in this case.
//
function fillDate(returnedValue, index)
{
   if (returnedValue == null) return;
   if (returnedValue[0] == 0) return;

   var year = "";
   var monthNum = "";
   var day = "";
   var dStr = "";
	
   if (returnedValue.length == 1) {
     year = returnedValue[0];   
     dStr = year;
   }
   else if (returnedValue.length == 2) {
     year = returnedValue[0];   
     monthNum = returnedValue[1] + ""; 
     if (monthNum < 10 && monthNum.length < 2)
       dStr = "0"+ monthNum + "/";
     else
       dStr = monthNum + "/"; 
       dStr = dStr + year;
   }
   else if (returnedValue.length == 3) {
     year = returnedValue[0];   
     monthNum = returnedValue[1] + ""; 
     day = returnedValue[2] + "";
     if (monthNum < 10 && monthNum.length < 2)
       dStr = "0"+ monthNum + "/";
     else
       dStr = monthNum + "/"; 
     if (day < 10 && day.length < 2)
       dStr = dStr + "0" + day + "/" + year;
     else
       dStr = dStr + day + "/" + year;
   }

   if (fillDate.arguments.length == 1) {
     document.all(calFullDateFld).value = dStr;
     document.all(calFullDateFld).fireEvent('onchange');
   }
   else if (fillDate.arguments.length == 2) {
     document.all[calFullDateFld][index].value = dStr;
     document.all[calFullDateFld][index].fireEvent('onchange');
   }

}

/*
  Returns zero if the dates are equal
  Returns 1 if the end date is later then the start date
  Returns -1 if the start date is later then the end date
*/

function compareDate( startYear, startMonth, startDay, endYear, endMonth, endDay )
{
	var result = 0;
             	
	startDate = new Date(startYear,startMonth,startDay);
	endDate = new Date(endYear,endMonth,endDay);
	
	if (endDate > startDate)
		result = 1;

	if (endDate < startDate)
		result = -1;
	
	return result;
}

function getFullYear(aDate)
{
   return aDate.getFullYear();
}

function clearDateField(dateField){
	document.all(dateField).value = '';
}
