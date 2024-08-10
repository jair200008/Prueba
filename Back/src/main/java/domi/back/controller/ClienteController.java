package domi.back.controller;
import domi.back.model.Cliente;
import domi.back.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @PostMapping
    public String createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public String updateCliente(@RequestBody Cliente cliente) {
        return clienteService.update(cliente);
    }
    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable String id) {
        return clienteService.delete(id);
    }

}
