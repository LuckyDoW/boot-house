package com.etoak.utils;

import com.etoak.exception.ParamException;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 后端验证另一种方法  /boot/house/add2方法
 * 参考文档地址：
 */
public class ValidationUtil {
    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static void Validate(Object object){
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if(CollectionUtils.isNotEmpty(violations)) {
            Iterator<ConstraintViolation<Object>> iterator = violations.iterator();

            // 保存错误信息
            StringBuffer messageBuf = new StringBuffer();
            while(iterator.hasNext()) {
                ConstraintViolation<Object> violation = iterator.next();
                String message = violation.getMessage();
                messageBuf.append(message).append(";");
            }
            throw new ParamException("参数错误：" + messageBuf.toString());
        }
    }

}
