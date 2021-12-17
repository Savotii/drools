package com.spirent.drools.dto.rules.global;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ysavi2
 * @since 08.12.2021
 */
@Slf4j
public class GlobalBoolean {
    private boolean passed = true;

    public GlobalBoolean () {}

    public boolean isPassed() {
        log.info("isPassed {} ", passed);
        return this.passed;
    }

    public void setIsPassed(boolean flag) {
        log.info("setIsPassed {} ", flag);
        this.passed = flag;
    }
}
