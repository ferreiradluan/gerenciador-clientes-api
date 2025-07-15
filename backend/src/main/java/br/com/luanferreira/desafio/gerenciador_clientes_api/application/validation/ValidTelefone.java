package br.com.luanferreira.desafio.gerenciador_clientes_api.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelefone {
    String message() default "Telefone inválido: número deve ter 8 dígitos para residencial/comercial ou 9 para celular";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
