package org.amarcel.lib.nge;

import java.util.List;

public class NgeErrorInfo {
  
  public String messageKey;
  public List<Object> args;
  public String message;
  public List<NgeInvalidField> invalidFields;

  public NgeErrorInfo() {
  }
  
  public NgeErrorInfo(
    String messageKey,
    List<Object> args,
    String message,
    List<NgeInvalidField> invalidFields
  ) {
    this.messageKey = messageKey;
    this.args = args;
    this.message = message;
    this.invalidFields = invalidFields;
  }
}
