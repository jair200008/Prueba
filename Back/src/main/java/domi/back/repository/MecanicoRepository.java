package domi.back.repository;

import domi.back.model.Cliente;
import domi.back.model.Mecanico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class MecanicoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Mecanico> findAll() {
        String sql = "select * from mecanicos";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mecanico.class));
    } // Consultar todos los mecanicos

    public Mecanico findById(String id) {
        String sql = "select * from mecanico where codigo = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Mecanico.class), id);
    } // Consultar un mecanico por su id

    public String save(Mecanico mecanico) {
        String sql = "{call registrar_persona_procedimiento(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Nota el uso de `{}` y el parámetro OUT al final.

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setString(1, "MECANICO");
                cs.setString(2, mecanico.getTipo_documento());
                cs.setLong(3, mecanico.getDocumento());
                cs.setString(4, mecanico.getPrimer_nombre());
                cs.setString(5, mecanico.getSegundo_nombre());
                cs.setString(6, mecanico.getPrimer_apellido());
                cs.setString(7, mecanico.getSegundo_apellido());
                cs.setString(8, mecanico.getCelular());
                cs.setString(9, mecanico.getDireccion());
                cs.setString(10, mecanico.getEmail());

                cs.registerOutParameter(11, Types.VARCHAR);

                cs.execute();

                return cs.getString(11);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    } // Registrar un nuevo mecanico

    public String update(Mecanico mecanico) {
        String sql = "{call actualizar_persona_procedimiento(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Nota el uso de `{}` y el parámetro OUT al final.

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setString(1, "MECANICO");
                cs.setString(2, mecanico.getCodigo());
                cs.setString(3, mecanico.getTipo_documento());
                cs.setLong(4, mecanico.getDocumento());
                cs.setString(5, mecanico.getPrimer_nombre());
                cs.setString(6, mecanico.getSegundo_nombre());
                cs.setString(7, mecanico.getPrimer_apellido());
                cs.setString(8, mecanico.getSegundo_apellido());
                cs.setString(9, mecanico.getCelular());
                cs.setString(10, mecanico.getDireccion());
                cs.setString(11, mecanico.getEmail());
                cs.registerOutParameter(12, Types.VARCHAR);

                cs.execute();
                String result = cs.getString(12);
                System.out.println(result);
                return result;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    } // Actualizar un mecanico

    public String delete(String id) {
        String sql = "{call eliminar_persona_procedimiento(?,?,?)}";
        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {

                cs.setString(1, "MECANICO");
                cs.setString(2, id);

                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();
                String result = cs.getString(3);
                System.out.println(result);

                return cs.getString(3);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }

    } // Eliminar un mecanico
}
