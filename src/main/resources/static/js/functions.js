// ---------------------------------------------------
$('document').ready(function(){
	
	$('#modalSendMoney').on('show.bs.modal', function (event) {
		if (!$('#idSendCredit').val()) {
			alert("Please select a connection.");
			return event.preventDefault();		
		}
		if ($('#idSendAmount').val() <= 0) {
			alert("Transaction amount must be greater than 0.");
			return event.preventDefault();		
		}
		
		// Transaction amount
		$('#idPaymentAmount').val($('#idSendAmount').val());
		// Connection ID
		$('#idPaymentCredit').val($('#idSendCredit').val());
		
		// Connection name
		var selectElement = document.getElementById("idSendCredit");
		var selectedValue = selectElement.options[selectElement.selectedIndex].text;
		$('#idPaymentConnection').val(selectedValue);
	})

});
