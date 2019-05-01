package org.amarcel.lib.nge.exception;

import org.amarcel.lib.nge.NgeErrorInfo;
import org.amarcel.lib.nge.NgeErrorType;

public class NgeTechnicalException extends NgeException {
  
  public NgeTechnicalException(NgeErrorInfo error, Throwable cause) {
    super(NgeErrorType.TechnicalError, error, cause);
  }

  public NgeTechnicalException(NgeErrorInfo error) {
    super(NgeErrorType.TechnicalError, error, null);
  }
}
