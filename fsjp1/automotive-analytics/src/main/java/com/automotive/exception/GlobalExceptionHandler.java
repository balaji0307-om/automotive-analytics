package com.automotive.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler – catches exceptions from all controllers.
 * Returns:
 *   - JSON responses for REST API endpoints
 *   - HTML error pages for MVC (browser) requests
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle ResourceNotFoundException – show 404 page or JSON.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFound(ResourceNotFoundException ex,
                                          jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, Object> body = buildErrorBody(HttpStatus.NOT_FOUND, ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    /**
     * Handle DuplicateResourceException – 409 Conflict.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public Object handleDuplicate(DuplicateResourceException ex,
                                   jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, Object> body = buildErrorBody(HttpStatus.CONFLICT, ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
        }
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", ex.getMessage());
        mav.addObject("status", 409);
        return mav;
    }

    /**
     * Handle Bean Validation errors from @Valid on request bodies (REST).
     * Returns a map of field -> error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("errors", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Catch-all handler for unexpected exceptions – show generic 500 page.
     */
    @ExceptionHandler(Exception.class)
    public Object handleGeneral(Exception ex,
                                 jakarta.servlet.http.HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, Object> body = buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", "An unexpected error occurred. Please try again.");
        return mav;
    }

    // ---- Helpers ----

    // Determine if request came from API (Accept: application/json or /api/ path)
    private boolean isApiRequest(jakarta.servlet.http.HttpServletRequest request) {
        String uri = request.getRequestURI();
        String accept = request.getHeader("Accept");
        return (uri != null && uri.startsWith("/api/")) ||
               (accept != null && accept.contains("application/json"));
    }

    private Map<String, Object> buildErrorBody(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }
}
