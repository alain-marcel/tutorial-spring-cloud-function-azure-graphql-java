package org.amarcel.lib.nge;

import java.util.List;

public class NgeInvalidField {

  public List<String> path;
  public String field;
  public Object rejectedValue;
  public String reason;

  NgeInvalidField() {
  }

  NgeInvalidField(List<String> path, String field, Object rejectedValue, String reason) {
    this.path = path;
    this.field = field;
    this.rejectedValue = rejectedValue;
    this.reason = reason;
  }

}
