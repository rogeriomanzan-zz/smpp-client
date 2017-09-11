package br.com.zup.telecom.smpp.client.ws.request;

import javax.validation.ValidationException;

public class SmsRequestValidator {
	
    private SmsRequestValidator() {
    }
    
    public static void check(SmsRequest request) {
    	
    	if(request == null) return;
    	
    	if(request.getMessage() == null || "".equals(request.getMessage())) {
    		throw new ValidationException("Field 'message' can not be null or empty."); 
    	}
    	
    	if(request.getPhoneNumber() == null || "".equals(request.getPhoneNumber())) {
    		throw new ValidationException("Field 'phoneNumber' can not be null or empty."); 
    	}
    	
    	//TODO: validate Phone Number format
    }

}
