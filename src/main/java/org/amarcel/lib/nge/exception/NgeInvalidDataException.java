package org.amarcel.lib.nge.exception;

import org.amarcel.lib.nge.NgeErrorInfo;
import org.amarcel.lib.nge.NgeErrorType;

public class NgeInvalidDataException extends NgeException {

  public NgeInvalidDataException(NgeErrorInfo error, Throwable cause) {
    super(NgeErrorType.InvalidData, error, cause);
  }

  public NgeInvalidDataException(NgeErrorInfo error) {
    super(NgeErrorType.InvalidData, error, null);
  }
 
}
