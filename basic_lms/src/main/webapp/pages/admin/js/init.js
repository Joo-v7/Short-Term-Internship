function ini_set(container) {
	if(container === undefined){
		container = 'body';
	}
  //form 관련 css 재선언
	M.updateTextFields(); //텍스트필드 업데이트 라벨 class active

	$('.tooltipped').tooltip({
		container:container,
	});
	$('select').formSelect();
	$('.materialboxed').materialbox();
	$('.datepicker').datepicker({
		autoClose : true,
		selectMonths: true, // Creates a dropdown to control monthz
		selectYears: 50, // Creates a dropdown of 15 years to control year,
		today: 'Today',
		setDefaultDate: true,
		showClearBtn: true,
		closeOnSelect: true, // Close upon selecting a date,
		container: container // ex. 'body' will append picker to body,
	});	

	$('.timepicker').timepicker({
		default: 'now', // Set default time: 'now', '1:30AM', '16:30'
		autoClose : true,
		fromnow: 0,       // set default time to * milliseconds from now (using with default = 'now')
		twelveHour: false, // Use AM/PM or 24-hour format
		donetext: 'OK', // text for done-button
		cleartext: 'Clear', // text for clear-button
		canceltext: 'Cancel', // Text for cancel-button,
		container:container, // ex. 'body' will append picker to body,
		autoclose: true, // automatic close timepicker
		ampmclickable: true, // make AM PM clickable
		aftershow: function(){} //Function for after opening timepicker
	});
}