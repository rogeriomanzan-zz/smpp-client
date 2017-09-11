package br.com.zup.telecom.smpp.client.ws.interceptor;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.zup.telecom.smpp.client.session.SmppClientSessionException;
import br.com.zup.telecom.smpp.client.session.SmppClientSessionResponse;

@ControllerAdvice
@ResponseBody
public class SmppClientExceptioinHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmppClientExceptioinHandler.class);

	@ExceptionHandler({ValidationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SmppClientSessionResponse badRequestException(ValidationException ex) {
		LOGGER.error("Request validation failed. {}", ex.getMessage());
		return new SmppClientSessionResponse("smpp.bad.request", ex.getMessage());
	}
	
	@ExceptionHandler({SmppClientSessionException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public SmppClientSessionResponse internalServerErrorException(SmppClientSessionException ex) {
		LOGGER.error("Failed to execute SmppClient", ex);
		
		String cause = " ...failed in execution SmppClient. {" + (ex.getCause() != null ? ex.getCause().getClass().getName() 
				+ " " + ex.getCause().getMessage() : "") + "}";
		return new SmppClientSessionResponse(ex.getCode(), (ex.getMessage() + cause));
	}
}
