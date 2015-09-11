$(document).ready(function() {
	$('#btn-edit-profile').on('click', editProfile);
	$('#btn-cancel-profile').on('click', cancelProfile);
	$('#btn-cancel-confirm').on('click', cancelConfirm);
});

function editProfile(e) {
	var $target = $(e.target);
	var $profileContainer = $target.closest('#profile-container');
	if (!$profileContainer.length) {
		return;
	}
	$profileContainer.find('.profile-input').each(function() {
		var $this = $(this);
		$this.removeClass('hid');
		$this.siblings('.profile-info').addClass('hid');
	});
	$target.addClass('hid');
	$('#btn-save-profile').removeClass('hid');
	$('#btn-cancel-profile').removeClass('hid');
}

function cancelProfile(e) {
	var $target = $(e.target);
	var $profileContainer = $target.closest('#profile-container');
	if (!$profileContainer.length) {
		return;
	}
	$profileContainer.find('.profile-info').each(function() {
		var $this = $(this);
		$this.removeClass('hid');
		$this.siblings('.profile-input').addClass('hid');
	});
	
	$target.addClass('hid');
	$('#btn-save-profile').addClass('hid');
	$('#btn-edit-profile').removeClass('hid');
}

function cancelConfirm() {
	window.location = "../profile/index";
}