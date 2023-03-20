package com.xkq.gmall.product.exception;

import com.xkq.common.exception.BizCodeEnume;
import com.xkq.common.utils.R;
import com.xkq.gmall.product.entity.BrandEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author: xkq
 * @date: 2023/3/18 0:02
 * @description:
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.xkq.gmall.product.controller")
public class GamllExceptionControllerAdvice {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            String message = item.getDefaultMessage();
            String field = item.getField();
            map.put(field, message);
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", map);
    }
}
