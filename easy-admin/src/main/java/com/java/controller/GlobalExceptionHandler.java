package com.java.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Description:	   <br/>
 * Date:     2020/11/24 21:39 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "/pages/error/exception.jsp";
    }
}
