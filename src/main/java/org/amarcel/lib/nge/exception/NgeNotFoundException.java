package org.amarcel.lib.nge.exception;

import org.amarcel.lib.nge.NgeErrorInfo;
import org.amarcel.lib.nge.NgeErrorType;

public class NgeNotFoundException extends NgeException {

  public NgeNotFoundException(NgeErrorInfo error, Throwable cause) {
    super(NgeErrorType.NotFound, error, cause);
  }

  public NgeNotFoundException(NgeErrorInfo error) {
    super(NgeErrorType.InvalidData, error, null);
  }
}
