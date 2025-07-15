package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s]+$", message = "O nome deve conter apenas letras, números e espaços")
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
