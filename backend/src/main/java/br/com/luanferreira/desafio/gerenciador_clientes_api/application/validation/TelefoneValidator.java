package br.com.luanferreira.desafio.gerenciador_clientes_api.application.validation;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.TelefoneUtil;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, Telefone> {

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
        // Inicialização se necessário
    }

    @Override
    public boolean isValid(Telefone telefone, ConstraintValidatorContext context) {
        if (telefone == null) {
            return true; // Deixa outras validações (como @NotNull) lidar com null
        }

        return TelefoneUtil.isValido(telefone.getDdd(), telefone.getNumero(), telefone.getTipo());
    }
}
