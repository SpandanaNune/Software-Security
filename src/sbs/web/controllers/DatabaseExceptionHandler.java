package sbs.web.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {
	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException(DataAccessException exception) {
		exception.printStackTrace();
		return "error";
	}

}
 