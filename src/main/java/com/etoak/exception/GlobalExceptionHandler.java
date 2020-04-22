package com.etoak.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
/**
 *  参数异常处理
 * @author DouDou、
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理ParamException
     * @param e
     * @return
     */
    @ExceptionHandler(ParamException.class)
    public ModelAndView handleParamException(ParamException e) {
        log.error(e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView();
        // 将错误信息添加到request域中
        modelAndView.addObject("error", e.getMessage());
        modelAndView.setViewName("/house/error");
        return modelAndView;
    }

}
