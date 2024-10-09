package com.example.demo.Exception;



	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.ControllerAdvice;
	import org.springframework.web.bind.annotation.ExceptionHandler;
	

	@ControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(RoomNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleRoomNotFoundException(RoomNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(HotelNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleHotelNotFoundException(HotelNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(UserAlreadyExistsException.class)
	    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	    }

	    @ExceptionHandler(BookingIdNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleBookingIdNotFoundException(BookingIdNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(NoContentFoundException.class)
	    public ResponseEntity<ErrorResponse> handleNoContentFoundException(NoContentFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NO_CONTENT);
	    }

	    @ExceptionHandler(HotelOwnerNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleHotelOwnerNotFoundException(HotelOwnerNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(ReviewNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException ex) {
	        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
	        return buildErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
	        ErrorResponse errorResponse = new ErrorResponse(message);
	        return new ResponseEntity<>(errorResponse, status);
	    }

}
