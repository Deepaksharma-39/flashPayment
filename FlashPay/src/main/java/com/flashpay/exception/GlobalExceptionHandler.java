package com.flashpay.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

//	Login/SignUp Exception
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MyErrorDetails> loginExceptionHandler(LoginException le,WebRequest req){
		MyErrorDetails med=new MyErrorDetails();
		med.setTimestamp(LocalDateTime.now());
		med.setMessage(le.getMessage());
		med.setDescription(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(med,HttpStatus.BAD_REQUEST);
	}
	
//	validation Exception
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> illegalArgumentExceptionHandler(MethodArgumentNotValidException me){
		MyErrorDetails med= new MyErrorDetails();
		med.setTimestamp(LocalDateTime.now());
		med.setMessage(me.getMessage());
		med.setDescription(me.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<MyErrorDetails>(med,HttpStatus.FORBIDDEN);
	}
	
//	other kind of Exception
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(Exception e, WebRequest req){
		MyErrorDetails med= new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<MyErrorDetails>(med,HttpStatus.BAD_REQUEST);
	}
}
