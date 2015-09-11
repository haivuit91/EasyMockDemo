package com.vn.ld.btc.fe.info;

import java.io.Serializable;

import com.vn.ld.api.user.dto.CustomerDTO;
import com.vn.ld.common.Constants;
import com.vn.ld.common.util.DateUtils;

public class ProfileInfo implements Serializable {
	private static final long serialVersionUID = -2674474980733623233L;
	private Integer customerId;
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String city;
	private String country;
	private String zipCode;
	private String birthday;
	
	public ProfileInfo() {
		
	}
	public ProfileInfo(CustomerDTO customer) {
		this.customerId = customer.getCustomerId();
		this.email = customer.getEmail();
		this.username = customer.getUsername();
		this.firstName = customer.getFirstName();
		this.lastName = customer.getLastName();
		this.phoneNumber = customer.getPhoneNumber();
		this.address = customer.getAddress();
		this.city = customer.getCity();
		this.country = customer.getCountry();
		this.zipCode = customer.getZipCode();
		this.birthday = customer.getBirthday() == null ? "" : DateUtils.dateToString(customer.getBirthday(), Constants.DateTimeFormat.PATTERN_MMMMMDDYYYY);
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getEmail() {
		return email;
	}
	public String getUsername() {
		return username;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
