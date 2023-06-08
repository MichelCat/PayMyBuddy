$('document').ready(function(){
	
	// ---------------------------------------------------------------------------------------
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
		let selectElement = document.getElementById("idSendCredit");
		let selectedValue = selectElement.options[selectElement.selectedIndex].text;
		$('#idPaymentConnection').val(selectedValue);
	})
	
	
	// ---------------------------------------------------------------------------------------
	$('.btnModalEditUser').click(function () {
		let itemId=$(this).attr("data-id");
		
		$('#idUser').val( document.getElementById("idRowIdUser"+itemId).innerHTML );
		$('#idLastName').val( document.getElementById("idRowLastName"+itemId).innerHTML );
		$('#idFirstName').val( document.getElementById("idRowFirstName"+itemId).innerHTML );
		$('#idUsername').val( document.getElementById("idRowUsername"+itemId).innerHTML );
		
		let sourceCheckbox;
		let targetCheckbox;
		
		sourceCheckbox = document.getElementById("idRowExpired"+itemId);
		targetCheckbox = document.getElementById("idExpired");
		targetCheckbox.checked = sourceCheckbox.checked;
		
		sourceCheckbox = document.getElementById("idRowLocked"+itemId);
		targetCheckbox = document.getElementById("idLocked");
		targetCheckbox.checked = sourceCheckbox.checked;
		
		sourceCheckbox = document.getElementById("idRowCredentialsExpired"+itemId);
		targetCheckbox = document.getElementById("idCredentialsExpired");
		targetCheckbox.checked = sourceCheckbox.checked;
		
		sourceCheckbox = document.getElementById("idRowEnabled"+itemId);
		targetCheckbox = document.getElementById("idEnabled");
		targetCheckbox.checked = sourceCheckbox.checked;
	})

});
