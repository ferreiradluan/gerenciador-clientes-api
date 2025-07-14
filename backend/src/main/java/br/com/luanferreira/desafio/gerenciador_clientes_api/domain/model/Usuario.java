package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O login não pode ser nulo ou vazio")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "A senha não pode ser nula ou vazia")
    private String senha;

    @NotBlank(message = "O role não pode ser nulo ou vazio")
    private String role;
}
