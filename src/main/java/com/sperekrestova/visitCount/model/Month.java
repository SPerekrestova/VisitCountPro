package com.sperekrestova.visitCount.model;

/**
 * Created by Svetlana
 * Date: 31.01.2021
 */
public enum Month {

    ЯНВАРЬ,
    ФЕВРАЛЬ,
    МАРТ,
    АПРЕЛЬ,
    МАЙ,
    ИЮНЬ,
    ИЮЛЬ,
    АВГУСТ,
    СЕНТЯБРЬ,
    ОКТЯБРЬ,
    НОЯБРЬ,
    ДЕКАБРЬ;

    private static final Month[] ENUMS = Month.values();

    public int getValue() {
        return ordinal() + 1;
    }
}
