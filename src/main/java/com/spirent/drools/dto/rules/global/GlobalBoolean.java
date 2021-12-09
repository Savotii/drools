package com.spirent.drools.dto.rules.global;

/**
 * @author ysavi2
 * @since 08.12.2021
 */
public class GlobalBoolean {
    private boolean passed = true;

    public GlobalBoolean () {}

    public boolean isPassed() {
        return this.passed;
    }

    public void setIsPassed(boolean flag) {
        this.passed = flag;
    }
}
