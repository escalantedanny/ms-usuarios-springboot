package com.escalantedanny.usuario.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandler {

	private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class.getSimpleName());
	
	public static final String ERROR_DETECTADO = "Finaliza consulta con codigo de error";
	public static final String MENSAJE_404 = "No se encuentra usuario con el identificador";
	public static final String MENSAJE_400 = "Error de solicitud";
	public static final String MENSAJE_500 = "Error de servidor";
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
    	log.info("Se detecta exception MethodArgumentNotValidException");
		log.info(MENSAJE_400);
		log.error(e.getLocalizedMessage());
    	// return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleAllExceptions(HttpServletRequest request, Exception e) {
    	log.info("Se detecta exception handleAllExceptions");
		log.info(ERROR_DETECTADO);
		log.error(e.getLocalizedMessage());
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorInfo> handleSQLExceptions(HttpServletRequest request, SQLException e) {
    	log.info("Se detecta exception handleSQLExceptions");
		log.info(ERROR_DETECTADO);
		log.error(e.getLocalizedMessage());
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorInfo> handleUserNotFoundException(HttpServletRequest request, NoSuchElementException e) {
    	log.info("Se detecta exception handleUserNotFoundException");
		log.info(MENSAJE_404);
		log.error(e.getLocalizedMessage());
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorInfo> handleUserNulldException(HttpServletRequest request, NullPointerException e) {
    	log.info("Se detecta exception handleUserNulldException");
		log.info(MENSAJE_404);
		log.error(e.getLocalizedMessage());
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
    
	
}