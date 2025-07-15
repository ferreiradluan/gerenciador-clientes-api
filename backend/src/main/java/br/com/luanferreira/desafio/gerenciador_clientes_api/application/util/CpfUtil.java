package br.com.luanferreira.desafio.gerenciador_clientes_api.application.util;

/**
 * Utilitário para formatação e limpeza de CPF
 */
public class CpfUtil {

    /**
     * Remove a formatação do CPF (máscara)
     * @param cpf CPF com ou sem formatação
     * @return CPF apenas com números
     */
    public static String removerMascara(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("[^0-9]", "");
    }

    /**
     * Aplica máscara no CPF
     * @param cpf CPF sem formatação (apenas números)
     * @return CPF formatado (XXX.XXX.XXX-XX)
     */
    public static String aplicarMascara(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." + 
               cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + 
               cpf.substring(9, 11);
    }

    /**
     * Verifica se o CPF está formatado
     * @param cpf CPF a ser verificado
     * @return true se contém formatação, false caso contrário
     */
    public static boolean temMascara(String cpf) {
        return cpf != null && cpf.contains(".");
    }
}
