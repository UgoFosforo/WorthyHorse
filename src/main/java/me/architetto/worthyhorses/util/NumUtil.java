package me.architetto.worthyhorses.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumUtil {

    public static String fixedValueToString(double value) {
        return String.valueOf(new BigDecimal(value).setScale(2, RoundingMode.HALF_DOWN).doubleValue()) ;
    }

    public static double fixedValue(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_DOWN).doubleValue() ;
    }

}
