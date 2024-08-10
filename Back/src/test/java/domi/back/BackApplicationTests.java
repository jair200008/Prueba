package domi.back;

import domi.back.model.Cliente;
import domi.back.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackApplicationTests {
    @Autowired
    private ClienteService p1;
    @Test
    void contextLoads() {
        Cliente cliente = new Cliente();
        cliente.setCodigo("123456789");
        cliente.setTipo_documento("DNI");
        cliente.setDocumento(123456789L);
        cliente.setPrimer_nombre("Jair");
        cliente.setSegundo_nombre("Garcia");
        cliente.setPrimer_apellido("Jair");
        cliente.setSegundo_apellido("Garcia");
        cliente.setCelular("123456789");
        cliente.setDireccion("Calle 123");
        cliente.setEmail("jair@gmail.com");
        p1.save(cliente);
    }

}
