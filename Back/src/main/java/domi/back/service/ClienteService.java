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
        return clienteRepository.findById(id).orElse(null);
    }
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
    public Cliente update(String id, Cliente detalle) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setPrimer_nombre(detalle.getPrimer_nombre());
        cliente.setSegundo_nombre(detalle.getSegundo_nombre());
        cliente.setPrimer_apellido(detalle.getPrimer_apellido());
        cliente.setSegundo_apellido(detalle.getSegundo_apellido());
        cliente.setDireccion(detalle.getDireccion());
        cliente.setCelular(detalle.getCelular());
        cliente.setEmail(detalle.getEmail());
        return clienteRepository.save(cliente);
    }


}
