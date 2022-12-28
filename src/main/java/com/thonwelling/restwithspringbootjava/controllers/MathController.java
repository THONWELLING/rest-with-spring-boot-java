package com.thonwelling.restwithspringbootjava.controllers;
import com.thonwelling.restwithspringbootjava.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

  @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
      method = RequestMethod.GET)
  public Double sum(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
      throw new UnsupportedMathOperationException("Please Set A Numeric Value");
    }
    return converToDouble(numberOne) + converToDouble(numberTwo);
  }

  private Double converToDouble(String strNumber) {
    if (strNumber == null) {
    throw new RuntimeException("2 Numbers Are Required To It Works!!!");
    }
    String number  = strNumber.replaceAll(",", ".");
    if (isNumeric(number)) return Double.parseDouble(number);
    return 0D;
  }

  private boolean isNumeric(String strNumber) {
    if (strNumber == null) return false;
    String number = strNumber.replaceAll(",", ".");
  return number.matches("[-+]?[0-9]*\\.?[0-9]+");
  }
}
