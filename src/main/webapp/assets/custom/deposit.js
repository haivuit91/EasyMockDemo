$(document).ready(function() {
	console.log('Document is ready!');
	var $destIdHidden = $('#destination-id'),
		$symbolIdHidden = $('#symbol-id'),
		$destNameHidden = $('#destination-name'),
		$symbolCdHidden = $('#symbol-cd');
	console.log('Get the assets!');
	var $assetsEls = $('.assets');
	var destinationId = $destIdHidden.val();
	console.log($destIdHidden);
	$('#deposit-amount').prop("disabled", true);
	console.log('After disabled amount!');
	if (destinationId == undefined || !destinationId.length) {
		console.log("Set for the first time come!");
		var $firstAssets = $('.assets:first'),
			symbolCd = $firstAssets.attr('data-symbol-cd'),
			minDep = $firstAssets.attr('data-min-dep');
		console.log($firstAssets);
		$firstAssets.addClass('selected');
		$destIdHidden.val($firstAssets.attr('data-assets-id'));
		$symbolIdHidden.val($firstAssets.attr('data-symbol-id'));
		$destNameHidden.val($firstAssets.attr('data-destination-name'));
		$symbolCdHidden.val(symbolCd);
		$('#min-deposit').html(minDep + " " + symbolCd);
	} else {
		$assetsEls.removeClass('selected');
		$assetsEls.filter('[data-assets-id="' + destinationId + '"]').addClass('selected');
	}
	// START: binding events
	$assetsEls.on('click', function() {
		var $this = $(this);
		var assetsId = $this.attr('data-assets-id'),
			symbolId = $this.attr('data-symbol-id'),
			destName = $this.attr('data-destination-name'),
			symbolCd = $this.attr('data-symbol-cd'),
			minDeposit = $this.attr('data-min-dep');
		$destIdHidden.val(assetsId);
		$symbolIdHidden.val(symbolId);
		$destNameHidden.val(destName);
		$symbolCdHidden.val(symbolCd);
		$('#min-deposit').html(minDeposit + ' ' + symbolCd);
		$assetsEls.removeClass('selected');
		$this.addClass('selected');
	});
	$('#deposit-method').on('click', function() {
		var $this = $(this),
			selectedMethod = $this.val();
		if (selectedMethod == -1) {
			$('#deposit-amount').prop("disabled", true);
		} else {
			$('#deposit-amount').prop("disabled", false);
		}
	});
	
	$('.cancel-deposit').on('click', function() {
		var $this = $(this),
			depositId = $this.attr('data-deposit-id');
		$.ajax({
			url: 'cancel?depositId=' + depositId,
			type: 'GET',
			dataType: 'json',
			complete: function (xhr, status) {
			    if (status === 'error' || !xhr.responseText) {
			    	 console.log("Error while canceling deposit.");
			    	 alert('Error!');
			    } else {
			    	console.log("Cancel deposit successfully!");
			    	$this.closest('td').siblings('td.stt').empty().html('Cancelled');
			    	$this.hide();
			    }
			}
		});
	});
	// END: binding events
});
