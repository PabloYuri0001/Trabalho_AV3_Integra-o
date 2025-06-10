package com.hostpet.hostpet.services;

import com.hostpet.hostpet.entity.Cliente;
import com.hostpet.hostpet.entity.User;
import com.hostpet.hostpet.forms.ClienteForm;
import com.hostpet.hostpet.repository.IClienteRepository;
import com.hostpet.hostpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;

    public Cliente cadastrarCliente(ClienteForm form) {
        User user = userRepository.findById(form.userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Cliente cliente = new Cliente();
        cliente.setNome(form.nome);
        cliente.setTelefone(form.telefone);
        cliente.setUser(user);
        return clienteRepository.save(cliente);
    }


    public List<Cliente> listClientesByUser(Long userId) {
        // Verifica se o usuário existe
        boolean usuarioExiste = userRepository.existsById(userId);
        if (!usuarioExiste) {
            throw new IllegalArgumentException("Usuário não encontrado ou não cadastrado.");
        }

        // Busca os clientes vinculados ao usuário
        return clienteRepository.findByUserId(userId);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void excluirCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}