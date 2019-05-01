package org.amarcel.lib.nge;

import org.amarcel.lib.nge.exception.NgeException;
import org.amarcel.lib.nge.exception.NgeInvalidDataException;
import org.amarcel.lib.nge.exception.NgeNotFoundException;
import org.amarcel.lib.nge.exception.NgeTechnicalException;

import java.util.ArrayList;

import static org.amarcel.lib.nge.NgeCommonErrorMessageKey.*;

/**
 * Each application should defined:
 * <ul>
 * <li>Its own `ErrorMessageKey`,</li>
 * <li>Its own `ErrorInfoFactory`.</li>
 * </ul>
 * And should use CommonErrorInfoFactory and CommonErrorMessageKey
 * for common errors.
 */
public class NgeCommonExceptionFactory {

  public static NgeException ngeException(Throwable ex) {
    if (ex instanceof NgeException)
      return (NgeException)ex;
    else
      return unexpectedError(ex);
  }

  public static NgeTechnicalException unexpectedError(Throwable ex) {
    ArrayList<Object> args = new ArrayList<>();
    ArrayList<NgeInvalidField> invalidFields = new ArrayList<>();

    return new NgeTechnicalException(
      new NgeErrorInfo(
        CommonUnexpectedError.toString(),
        args,
        String.format("Oops! An unexpected error occurred: %s", ex),
        invalidFields
      ),
      ex
    );
  }

  public static NgeInvalidDataException invalidJsonFormat(Throwable ex) {

    ArrayList<Object> args = new ArrayList<>();
    args.add(ex.getMessage());

    ArrayList<NgeInvalidField> invalidFields = new ArrayList<>();

    return new NgeInvalidDataException(
      new NgeErrorInfo(
        CommonInvalidJsonFormat.toString(),
        args,
        String.format("Invalid JSON: %s", ex.getMessage()),
        invalidFields
      ),
      ex
    );
  }

  public static NgeInvalidDataException invalidGraphQl(Throwable ex) {
    ArrayList<Object> args = new ArrayList<>();
    args.add(ex.getMessage());

    ArrayList<NgeInvalidField> invalidFields = new ArrayList<>();

    return new NgeInvalidDataException(
      new NgeErrorInfo(
        CommonInvalidGraphQl.toString(),
        args,
        String.format("Invalid GraphQl: %s", ex.getMessage()) ,
        invalidFields
      ),
      ex
    );
  }

  public static NgeNotFoundException notFound(Object id) {
    ArrayList<Object> args = new ArrayList<>();
    args.add((Object)id);

    ArrayList<NgeInvalidField> invalidFields = new ArrayList<>();

    return new NgeNotFoundException(
      new NgeErrorInfo(
        CommonNotFound.toString(),
        args,
        String.format("entity not found: %s", id.toString()),
        invalidFields
      )
    );
  }
}
