package com.vn.ld.btc.fe.business;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.vn.ld.api.context.AccountContext;
import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.btc.fe.info.ProfileInfo;
import com.vn.ld.common.email.MailObject;
import com.vn.ld.common.email.MailSenderInfo;

public interface IAccountManager {

	public boolean checkExistCustomerByEmail(String email);
	
	public boolean checkExistUserName(String username);
	
	public void registerNewCustomer(AccountContext context);
	
	public void sendMailActiveAccountToCustomer(String urlLink, AccountContext context);
		
	public void activeAccountByActiveCode(String activeCode);
	
	public CustomerDTO getCustomerDTObyActiveCode(String activeCode);
	
	public Boolean comfirmActiveCustomerAccount(Integer customerId);
	
	public ProfileInfo getProfileByCustomerId(Integer customerId);
	
	public Boolean updateProfile(ProfileInfo profileInfo);
	
	public void sendMailActiveRegisterCustomer(MailObject mailObject, MailSenderInfo mailSender) throws AddressException, MessagingException;
	
	public Boolean enableGoogleAuth(Integer customerId, String secretCode);
	
	public Boolean disableGoogleAuth(Integer customerId);
}
