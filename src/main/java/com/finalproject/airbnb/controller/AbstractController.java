package com.finalproject.airbnb.controller;


        import com.finalproject.airbnb.model.DTOs.ErrorDTO;
        import com.finalproject.airbnb.model.exceptions.BadRequestException;
        import com.finalproject.airbnb.model.exceptions.NotFoundException;
        import com.finalproject.airbnb.model.exceptions.UnauthorizedException;
        import jakarta.servlet.http.HttpSession;
        import org.springframework.http.HttpStatus;
        import org.springframework.validation.FieldError;
        import org.springframework.web.bind.MethodArgumentNotValidException;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.ResponseStatus;
        import com.finalproject.airbnb.Utility;
        import java.time.LocalDateTime;
        import java.util.HashMap;
        import java.util.Map;

public abstract class AbstractController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e){
        e.printStackTrace();
        return generateErrorDTO(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e){
        e.printStackTrace();
        return generateErrorDTO(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e){
        e.printStackTrace();
        return generateErrorDTO(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e){
        e.printStackTrace();
        return generateErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return generateValidationErrorDTO(errors, HttpStatus.BAD_REQUEST);
    }

    private ErrorDTO generateErrorDTO(Exception e, HttpStatus s){
        return ErrorDTO.builder()
                .msg(e.getMessage())
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }

    private ErrorDTO generateValidationErrorDTO(Object e, HttpStatus s){
        return ErrorDTO.builder()
                .msg(e)
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }


    protected int getLoggedId(HttpSession s){
        if(s.getAttribute(Utility.LOGGED) == null){
            throw new UnauthorizedException("You have to login first");
        }
        return (int) s.getAttribute(Utility.LOGGED_ID);
    }
}




