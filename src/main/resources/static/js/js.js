/**
 * Created by Boris on 18-Jun-17.
 */

$(document).ready(function() {
	$('#create-prod-btn, #bind-products-button').click(function () {
		var $form = $(this).closest('form');

		$.ajax({
			type: "POST",
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(message) {
				$('#message').text(message);
			}
		})
	});
});