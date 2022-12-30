package com.thonwelling.restwithspringbootjava.converters;

public class NumberConverter {
  public static Double converToDouble(String strNumber) {
    if (strNumber == null) {
      throw new RuntimeException("2 Numbers Are Required To It Works!!!");
    }
    String number = strNumber.replaceAll(",", ".");
    if (isNumeric(number)) return Double.parseDouble(number);
    return 0D;
  }

  public static boolean isNumeric(String strNumber) {
    if (strNumber == null) return false;
    String number = strNumber.replaceAll(",", ".");
    return number.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
