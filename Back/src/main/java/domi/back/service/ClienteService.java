package domi.back.service;

import domi.back.model.Cliente;
import domi.back.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(String id) {
        return clienteRepository.findById(id);
    }

    public String save(Cliente cliente) {
        String resultado = clienteRepository.save(cliente);
        return resultado;

    }

    public String update(Cliente detalle) {
        String resultado = clienteRepository.update(detalle);
        return resultado;
    }

    public String delete(String id) {
        String resultado = clienteRepository.delete(id);
        return resultado;
    }
}
