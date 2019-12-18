package com.serzh.aop.limitation;

import com.serzh.aop.AddParam;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
class ParamHandler {

    @AddParam
    String handle(String param) {
        return param;
    }

    String handle2(String param) {
        return handle(param);
    }

    @AddParam
    String handle3(String param) {
        return handle(param);
    }
}
