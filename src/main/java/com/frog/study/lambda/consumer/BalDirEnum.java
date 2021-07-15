package com.frog.study.lambda.consumer;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/07/15
 */
public enum BalDirEnum {
    ADD("+");
    private String value;

    BalDirEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
