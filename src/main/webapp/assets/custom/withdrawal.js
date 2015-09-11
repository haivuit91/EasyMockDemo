$(document).ready(function() {
	var $sourceIdHidden = $('#source-id'),
		$symbolIdHidden = $('#symbol-id'),
		$sourceNameHidden = $('#source-name'),
		$symbolCdHidden = $('#symbol-cd');
	var $assetsEls = $('.assets');
	var sourceId = $sourceIdHidden.val();
	
	if (sourceId == undefined || !sourceId.length) {
		var $firstAssets = $('.assets:first'),
			symbolCd = $firstAssets.attr('data-symbol-cd'),
			maxWithdraw = $firstAssets.attr('data-max-withdraw');
		$firstAssets.addClass('selected');
		$sourceIdHidden.val($firstAssets.attr('data-assets-id'));
		$symbolIdHidden.val($firstAssets.attr('data-symbol-id'));
		$sourceNameHidden.val($firstAssets.attr('data-assets-name'));
		$symbolCdHidden.val(symbolCd);
		$('#max-withdraw').html(maxWithdraw + ' ' + symbolCd);
	} else {
		$assetsEls.removeClass('selected');
		$assetsEls.filter('[data-assets-id="' + sourceId + '"]').addClass('selected');
	}
	
	// START: binding events
	$assetsEls.on('click', function() {
		var $this = $(this);
		var assetsId = $this.attr('data-assets-id'),
			symbolId = $this.attr('data-symbol-id'),
			assetsName = $this.attr('data-assets-name'),
			symbolCd = $this.attr('data-symbol-cd'),
			maxWithdraw = $this.attr('data-max-withdraw');
		$sourceIdHidden.val(assetsId);
		$symbolIdHidden.val(symbolId);
		$sourceNameHidden.val(assetsName);
		$symbolCdHidden.val(symbolCd);
		$('#max-withdraw').html(maxWithdraw + ' ' + symbolCd);
		$assetsEls.removeClass('selected');
		$this.addClass('selected');
	});
	$('.cancel-withdrawal').on('click', function() {
		var $this = $(this),
			withdrawalId = $this.attr('data-withdrawal-id');
		$.ajax({
			url: 'cancel?withdrawalId=' + withdrawalId,
			type: 'GET',
			dataType: 'json',
			complete: function (xhr, status) {
			    if (status === 'error' || !xhr.responseText) {
			    	 console.log("Error while canceling withdrawal.");
			    	 alert('Error!');
			    } else {
			    	console.log("Cancel withdrawal successfully!");
			    	$this.closest('td').siblings('td.stt').empty().html('Cancelled');
			    	$this.hide();
			    }
			}
		});
	});
	// END: binding events
});