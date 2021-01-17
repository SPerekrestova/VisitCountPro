package com.sperekrestova.visitCount.model;

/**
 * Created by Svetlana
 * Date: 17.01.2021
 */
public enum Mark {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int code;

    Mark(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
