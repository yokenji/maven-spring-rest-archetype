package ${package}.dto.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotated element validEmail will check for a valid email structure.
 * The email cannot be null by default.
 * A MX record check can be done the ensure the domain exists.
 * 
 * @author Bossuyt Fabrice <fabrice.bossuyt@gmail.com>, Original Author
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

  String message() default "ValidEmail.email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default{};

  /**
   * @return email is required.
   */
  boolean required() default true;

  /**
   * @return domain will be validated by the MX record.
   */
  boolean mxLookup() default false;

}
