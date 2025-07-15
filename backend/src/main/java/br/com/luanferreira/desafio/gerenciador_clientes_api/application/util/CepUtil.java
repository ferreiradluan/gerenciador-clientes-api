package br.com.luanferreira.desafio.gerenciador_clientes_api.application.util;

/**
 * Utilitário para formatação e limpeza de CEP
 */
public class CepUtil {

    /**
     * Remove a formatação do CEP (máscara)
     * @param cep CEP com ou sem formatação
     * @return CEP apenas com números
     */
    public static String removerMascara(String cep) {
        if (cep == null) {
            return null;
        }
        return cep.replaceAll("[^0-9]", "");
    }

    /**
     * Aplica máscara no CEP
     * @param cep CEP sem formatação (apenas números)
     * @return CEP formatado (XXXXX-XXX)
     */
    public static String aplicarMascara(String cep) {
        if (cep == null || cep.length() != 8) {
            return cep;
        }
        return cep.substring(0, 5) + "-" + cep.substring(5, 8);
    }

    /**
     * Verifica se o CEP está formatado
     * @param cep CEP a ser verificado
     * @return true se contém formatação, false caso contrário
     */
    public static boolean temMascara(String cep) {
        return cep != null && cep.contains("-");
    }

    /**
     * Valida se o CEP tem formato correto
     * @param cep CEP a ser validado
     * @return true se válido
     */
    public static boolean isValido(String cep) {
        if (cep == null) {
            return false;
        }
        String cepLimpo = removerMascara(cep);
        return cepLimpo.length() == 8 && cepLimpo.matches("\\d{8}");
    }
}
