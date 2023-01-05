package com.thonwelling.restwithspringbootjava.controllers;
import com.thonwelling.restwithspringbootjava.converters.NumberConverter;
import com.thonwelling.restwithspringbootjava.exceptions.ResourceNotFoundException;
import com.thonwelling.restwithspringbootjava.math.SimpleMath;
import org.springframework.web.bind.annotation.*;


@RestController
public class MathController {

  private SimpleMath math = new SimpleMath();
  @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
      method = RequestMethod.GET)
  public Double sum(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.sum(NumberConverter.converToDouble(numberOne), NumberConverter.converToDouble(numberTwo));
  }

  @GetMapping(value = "/sub/{numberOne}/{numberTwo}")
  public Double sub(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.sub(NumberConverter.converToDouble(numberOne), NumberConverter.converToDouble(numberTwo));
  }

  @GetMapping(value = "/mult/{numberOne}/{numberTwo}")
  public Double mult(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.mult(NumberConverter.converToDouble(numberOne), NumberConverter.converToDouble(numberTwo));
  }

  @GetMapping(value = "/div/{numberOne}/{numberTwo}")
  public Double div(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.div(NumberConverter.converToDouble(numberOne), NumberConverter.converToDouble(numberTwo));
  }

  @GetMapping(value = "/mean/{numberOne}/{numberTwo}")
  public Double mean(
      @PathVariable(value = "numberOne") String numberOne,
      @PathVariable(value = "numberTwo") String numberTwo
  ) throws Exception {
    if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.mean(NumberConverter.converToDouble(numberOne), NumberConverter.converToDouble(numberTwo));
  }
  @GetMapping(value = "/squareRoot/{number}")
  public Double squareRoot(
      @PathVariable(value = "number") String number
  ) throws Exception {
    if (!NumberConverter.isNumeric(number)) {
      throw new ResourceNotFoundException("Please Set A Numeric Value");
    }
    return math.squareRoot(NumberConverter.converToDouble(number));
  }
}
