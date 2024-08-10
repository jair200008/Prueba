package service;


import jakarta.transaction.Transactional;
import model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import repository.ClienteRepository;
import service.interfaces.interfazCliente;

import java.util.List;

@Service("Cliente")
@Transactional
public class ClienteService  implements interfazCliente {

    @Autowired
    @Qualifier("clienteRepository")
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> allClientes() {
        return List.of();
    }
}