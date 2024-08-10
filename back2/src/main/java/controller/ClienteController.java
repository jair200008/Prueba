package controller;


import model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable String id) {
        Cliente cliente = clienteService.findById(id);
        if(cliente != null) {
            return ResponseEntity.ok(cliente);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente cliente) {
        Cliente existingCliente = clienteService.findById(id);
        if(existingCliente != null) {
            cliente.setCodigo(id);
            Cliente updatedCliente = clienteService.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable String id) {

        if(clienteService.findById(id) != null) {
            Cliente cliente = clienteService.findById(id);
            clienteService.delete(cliente);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }


}