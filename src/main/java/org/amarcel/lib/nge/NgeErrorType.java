package org.amarcel.lib.nge;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum NgeErrorType {
  InvalidData(400), NotFound(404), TechnicalError(500);

  private final int errorTypeNum;

  private final static Map<Integer, NgeErrorType> map =
    stream(NgeErrorType.values()).collect(toMap(x -> x.errorTypeNum, leg -> leg));

  NgeErrorType(final int errorTypeNum) {
    this.errorTypeNum = errorTypeNum;
  }

  public static NgeErrorType valueOf(int legNo) {
    return map.get(legNo);
  }
}
