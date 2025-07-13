package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo ou vazio")
    private String nome;

    @NotBlank(message = "O CPF não pode ser nulo ou vazio")
    @CPF(message = "CPF inválido")
    @Column(unique = true)
    private String cpf;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cliente_telefones", joinColumns = @JoinColumn(name = "cliente_id"))
    private Set<Telefone> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cliente_emails", joinColumns = @JoinColumn(name = "cliente_id"))
    private Set<Email> emails = new HashSet<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Endereco> enderecos = new ArrayList<>();
}
