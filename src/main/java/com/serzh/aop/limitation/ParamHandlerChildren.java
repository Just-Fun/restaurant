package com.serzh.aop.limitation;

import org.springframework.stereotype.Component;

@Component
class ParamHandlerChildren extends ParamHandler {

    @Override
    String handle(String param) {
        return super.handle(param);
    }
}
