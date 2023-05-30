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
	
	$('#modalEditUser').on('show.bs.modal', function (event) {
/*		
		// User ID
		$('#idUser').val($('#idPageUser').val());
		// Email used to authenticate the user
		$('#idUsername').val($('#idPageUsername').val());
		// User account expired
		$('#idExpired').val($('#idPageExpired').val());
		// User locked
		$('#idLocked').val($('#idPageLocked').val());
		// User credentials (password) expired
		$('#idCredentiaExpired').val($('#idPageCredentiaExpired').val());
		// Activated user
		$('#idEnabled').val($('#enabled').val());
		// Valid email end date for customers
		$('#idValidEmailEndDate').val($('#idPageValidEmailEndDate').val());
		// Customer first name
		$('#idFirstName').val($('#idPageFirstName').val());
		// Customer last name
		$('#idLastName').val($('#idPageLastName').val());
*/
/*
var itemId=$(this).attr("data-id");
		
			var index = this.getAttribute('data-id');
			alert(index);
*/
	})
	
	$('.btn-info').click(function () {
//		var itemId=$(this).attr("data-id");
//			alert(itemId);
			
 //$('#idFirstName').val(itemId);
 

 
//  var message = /*[[${customerUserPage.content[1]}]]*/ {};
//			alert(message);

		// User ID
		$('#idUser').val(5);
		// Email used to authenticate the user
		$('#idUsername').val('alex@gmail.com');
		// User account expired
		$('#idExpired').checked = false;
		// User locked
		$('#idLocked').checked = false;
		// User credentials (password) expired
		$('#idCredentiaExpired').checked = false;
		// Activated user
		$('#idEnabled').checked = true;
		// Customer first name
		$('#idFirstName').val('alex');
		// Customer last name
		$('#idLastName').val('ALEX');
	});
	

});
