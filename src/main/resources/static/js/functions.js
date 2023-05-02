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


// ---------------------------------------------------
/*
	<div class="mx-3">
		<input type="number" class="form-control"
            onBlur="formatCurrency(this, '$ ', 'blur');"
            onkeyup="formatCurrency(this, '$ ');"
			placeholder="0€"
			th:field="${newTransaction.amount}"
			min="0" />
	</div>

function formatCurrency(input, currency, blur) {
	// Adds € to the value and returns the cursor to the correct position.
	
	// Input value
	var inputValue = input.value;
	
	// Invalid blank entry
	if (inputValue === "") {
		return;
	}
	
	// Cursor initial position
	var cursorPosition = input.selectionStart;
	
	input.value = inputValue.replace(/\D/g, "") + "€";
	
	input.setSelectionRange(cursorPosition, cursorPosition);
};
*/
