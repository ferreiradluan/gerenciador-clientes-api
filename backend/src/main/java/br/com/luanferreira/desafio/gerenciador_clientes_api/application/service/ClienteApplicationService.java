package br.com.luanferreira.desafio.gerenciador_clientes_api.application.service;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.mapper.ClienteMapper;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ClienteNaoEncontradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.CpfJaCadastradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Endereco;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.ClienteRepository;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.ClienteSpecification;
import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteApplicationService {

    private final ClienteRepository clienteRepository;
    private final ViaCepClient viaCepClient;
    private final ClienteMapper clienteMapper;

    @Transactional
    public ClienteDTO criarCliente(ClienteRequestBody clienteRequestBody) {
        clienteRepository.findByCpf(clienteRequestBody.getCpf()).ifPresent(cliente -> {
            throw new CpfJaCadastradoException(clienteRequestBody.getCpf());
        });

        Cliente cliente = clienteMapper.toEntity(clienteRequestBody);

        List<Endereco> enderecos = clienteRequestBody.getEnderecos().stream()
                .map(this::buscarEnderecoPorCep)
                .collect(Collectors.toList());

        enderecos.forEach(endereco -> endereco.setCliente(cliente));
        cliente.setEnderecos(enderecos);

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> listarTodos(String nome, String cpf, Pageable pageable) {
        Specification<Cliente> spec = Specification.where(ClienteSpecification.comNome(nome))
                .and(ClienteSpecification.comCpf(cpf));
        return clienteMapper.toDTO(clienteRepository.findAll(spec, pageable));
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return clienteMapper.toDTO(cliente);
    }

    @Transactional
    public ClienteDTO atualizarCliente(Long id, ClienteRequestBody clienteRequestBody) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        clienteRepository.findByCpf(clienteRequestBody.getCpf()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new CpfJaCadastradoException(clienteRequestBody.getCpf());
            }
        });

        clienteMapper.updateClienteFromDto(clienteRequestBody, cliente);

        List<Endereco> enderecos = clienteRequestBody.getEnderecos().stream()
                .map(this::buscarEnderecoPorCep)
                .collect(Collectors.toList());

        enderecos.forEach(endereco -> endereco.setCliente(cliente));
        cliente.getEnderecos().clear();
        cliente.getEnderecos().addAll(enderecos);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return clienteMapper.toDTO(clienteAtualizado);
    }

    @Transactional
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException(id);
        }
        clienteRepository.deleteById(id);
    }

    private Endereco buscarEnderecoPorCep(EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoViaCep = viaCepClient.consultarCep(enderecoDTO.getCep());
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoViaCep.getCep());
        endereco.setLogradouro(enderecoViaCep.getLogradouro());
        endereco.setBairro(enderecoViaCep.getBairro());
        endereco.setCidade(enderecoViaCep.getCidade());
        endereco.setUf(enderecoViaCep.getUf());
        endereco.setComplemento(enderecoDTO.getComplemento());
        return endereco;
    }
}
