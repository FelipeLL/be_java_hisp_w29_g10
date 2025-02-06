package com.project.be_java_hisp_w29_g10.exception;

import com.project.be_java_hisp_w29_g10.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> invalidPromoException(BadRequestException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflictException(ConflictException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> enumValidationException(MethodArgumentTypeMismatchException e) {
        String validValues = "";

        // Get the required type (enum class)
        Class<?> enumType = e.getRequiredType();

        // Check if it's an enum and get its values
        if (enumType != null && enumType.isEnum()) {
            validValues = Arrays.stream(enumType.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }

        String errorMessage = String.format(
                "El valor '%s' no es válido para el parámetro '%s'. Los valores permitidos son: [%s]",
                e.getValue(),
                e.getName(),
                validValues
        );

        ExceptionDto exceptionDto = new ExceptionDto(errorMessage);

        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
