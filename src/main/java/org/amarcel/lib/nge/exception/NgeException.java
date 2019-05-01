package org.amarcel.lib.nge.exception;

import org.amarcel.lib.nge.NgeErrorInfo;
import org.amarcel.lib.nge.NgeErrorType;

/**
 * Design: you must use `CommonNgeExceptionFactory` to create `NgeException`
 */
public abstract class NgeException extends RuntimeException {

  public NgeErrorType errorType;
  public NgeErrorInfo error;

  public NgeException(NgeErrorType errorType, NgeErrorInfo error, Throwable cause) {
    super(error.message, cause);
    this.errorType = errorType;
    this.error = error;
  }

  public NgeException(NgeErrorType errorType, NgeErrorInfo error) {
    super(error.message, null);
    this.errorType = errorType;
    this.error = error;
  }
}
