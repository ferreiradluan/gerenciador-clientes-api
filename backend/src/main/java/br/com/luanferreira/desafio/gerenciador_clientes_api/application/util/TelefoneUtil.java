package br.com.luanferreira.desafio.gerenciador_clientes_api.application.util;

/**
 * Utilitário para formatação e limpeza de Telefone
 */
public class TelefoneUtil {

    /**
     * Remove a formatação do telefone (máscara)
     * @param telefone Telefone com ou sem formatação
     * @return Telefone apenas com números
     */
    public static String removerMascara(String telefone) {
        if (telefone == null) {
            return null;
        }
        return telefone.replaceAll("[^0-9]", "");
    }

    /**
     * Aplica máscara no telefone baseado no tipo
     * @param ddd DDD do telefone
     * @param numero Número sem formatação
     * @param tipo Tipo do telefone (CELULAR, RESIDENCIAL, COMERCIAL)
     * @return Telefone formatado
     */
    public static String aplicarMascara(String ddd, String numero, String tipo) {
        if (ddd == null || numero == null || ddd.length() != 2) {
            return (ddd != null ? ddd : "") + (numero != null ? numero : "");
        }

        // Celular tem 9 dígitos, outros tipos têm 8
        if ("CELULAR".equalsIgnoreCase(tipo) && numero.length() == 9) {
            return "(" + ddd + ") " + numero.substring(0, 5) + "-" + numero.substring(5);
        } else if (numero.length() == 8) {
            return "(" + ddd + ") " + numero.substring(0, 4) + "-" + numero.substring(4);
        }
        
        // Se não conseguir formatar, retorna sem máscara
        return ddd + numero;
    }

    /**
     * Aplica máscara no telefone
     * @param telefoneCompleto Telefone completo (DDD + número)
     * @param tipo Tipo do telefone
     * @return Telefone formatado
     */
    public static String aplicarMascara(String telefoneCompleto, String tipo) {
        if (telefoneCompleto == null || telefoneCompleto.length() < 10) {
            return telefoneCompleto;
        }
        
        String limpo = removerMascara(telefoneCompleto);
        String ddd = limpo.substring(0, 2);
        String numero = limpo.substring(2);
        
        return aplicarMascara(ddd, numero, tipo);
    }

    /**
     * Verifica se o telefone está formatado
     * @param telefone Telefone a ser verificado
     * @return true se contém formatação, false caso contrário
     */
    public static boolean temMascara(String telefone) {
        return telefone != null && (telefone.contains("(") || telefone.contains("-"));
    }

    /**
     * Valida se o telefone tem formato correto baseado no tipo
     * @param ddd DDD do telefone
     * @param numero Número do telefone
     * @param tipo Tipo do telefone
     * @return true se válido
     */
    public static boolean isValido(String ddd, String numero, String tipo) {
        if (ddd == null || numero == null || tipo == null) {
            return false;
        }
        
        String dddLimpo = removerMascara(ddd);
        String numeroLimpo = removerMascara(numero);
        
        // Valida DDD (2 dígitos)
        if (dddLimpo.length() != 2 || !dddLimpo.matches("\\d{2}")) {
            return false;
        }
        
        // Valida número baseado no tipo
        if ("CELULAR".equalsIgnoreCase(tipo)) {
            return numeroLimpo.length() == 9 && numeroLimpo.matches("\\d{9}");
        } else {
            return numeroLimpo.length() == 8 && numeroLimpo.matches("\\d{8}");
        }
    }
}
