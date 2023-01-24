package br.com.vr.host.cartoes.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CartaoValidator.class })
@Documented
public @interface CartaoConstraint {
    String message() default "Cartão Inválido.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
