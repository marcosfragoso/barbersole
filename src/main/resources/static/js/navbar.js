var url = '';
const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');
const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl));

$('button[id*="btn_"]').click(function() {
	url = document.location.origin + "/" + $(this).attr('id').split("_")[1];
});

$('#ok_confirm').click(function() {
	document.location.href = url;
});

$(document).ready(function(){
  	$('[data-toggle="popover"]').popover();
});

$(document).ready(function() {
	$(".navbar-toggle ").click(function() {
		$(".sidebar ").toggleClass("sidebar-open ");
	});
});