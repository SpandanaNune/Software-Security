package sbs.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {
	private static final Logger logger = Logger.getLogger(DatabaseExceptionHandler.class);
	
	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException(DataAccessException exception) {
		exception.printStackTrace();
		logger.error("Failure :" + exception.getMessage());
		return "customerrorpage";

	}

}
 