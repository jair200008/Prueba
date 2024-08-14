package domi.back.repository;

import domi.back.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM Clientes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
    } // Consultar todos los clientes

    public Cliente findById(String id) {
        String sql = "SELECT * FROM Clientes WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), id);
    } // Consultar un cliente por su id

    public String save(Cliente cliente) {
        String sql = "{call registrar_persona_procedimiento(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Nota el uso de `{}` y el parámetro OUT al final.

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {

                cs.setString(1, "CLIENTE");
                cs.setString(2, cliente.getTipo_documento());
                cs.setLong(3, cliente.getDocumento());
                cs.setString(4, cliente.getPrimer_nombre());
                cs.setString(5, cliente.getSegundo_nombre());
                cs.setString(6, cliente.getPrimer_apellido());
                cs.setString(7, cliente.getSegundo_apellido());
                cs.setString(8, cliente.getCelular());
                cs.setString(9, cliente.getDireccion());
                cs.setString(10, cliente.getEmail());

                cs.registerOutParameter(11, Types.VARCHAR);

                cs.execute();

                return cs.getString(11);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }  // Registrar un nuevo cliente

    public String update(Cliente cliente) {
        String sql = "{call actualizar_persona_procedimiento(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Nota el uso de `{}` y el parámetro OUT al final.

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                // Configurar los parámetros de entrada
                cs.setString(1, "CLIENTE");
                cs.setString(2, cliente.getCodigo());
                cs.setString(3, cliente.getTipo_documento());
                cs.setLong(4, cliente.getDocumento());
                cs.setString(5, cliente.getPrimer_nombre());
                cs.setString(6, cliente.getSegundo_nombre());
                cs.setString(7, cliente.getPrimer_apellido());
                cs.setString(8, cliente.getSegundo_apellido());
                cs.setString(9, cliente.getCelular());
                cs.setString(10, cliente.getDireccion());
                cs.setString(11, cliente.getEmail());
                cs.registerOutParameter(12, Types.VARCHAR);


                // Ejecutar el procedimiento
                cs.execute();

                // Obtener el resultado del parámetro OUT
                return cs.getString(12);
            });
        } catch (Exception e) {

            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    } // Actualizar un cliente

    public String delete(String id) {
        String sql = "{call eliminar_persona_procedimiento(?,?,?)}";
        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setString(1, "CLIENTE");
                cs.setString(2, id);

                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();

                return cs.getString(3);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    } // Eliminar un cliente
}
