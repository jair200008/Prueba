package domi.back.repository;

import domi.back.model.Cliente;
import domi.back.model.Marca;
import domi.back.model.Vehiculo;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehiculoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Vehiculo> findAll() {
        String sql = "{? = call get_vehiculos_detalle()}";

        return jdbcTemplate.execute(sql, (CallableStatementCallback<List<Vehiculo>>) cs -> {
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);
            List<Vehiculo> vehiculos = new ArrayList<>();

            while (rs.next()) {
                Vehiculo vd = new Vehiculo();
                vd.setPlaca(rs.getString("placa"));
                vd.setColor(rs.getString("color"));
                vd.setCodigo(rs.getString("vehiculo_codigo"));

                Marca marca = new Marca();
                marca.setNombre_marca(rs.getString("marca_nombre"));
                marca.setCodigo(rs.getString("marca_codigo"));
                vd.setMarca(marca);

                Cliente cliente = new Cliente();
                cliente.setDocumento(rs.getLong("cliente_documento"));
                cliente.setPrimer_nombre(rs.getString("cliente_nombre_completo"));
                cliente.setCodigo(rs.getString("cliente_codigo"));
                vd.setCliente(cliente);


                vehiculos.add(vd);
            }

            return vehiculos;
        });
    }

    public Vehiculo findById(String id) {
        String sql = "SELECT * FROM vehiculos WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vehiculo.class), id);
    } // Consultar un vehículo por su id

    public String save(Vehiculo vehiculo) {
        String sql = "{call registrar_vehiculo(?, ?, ?, ?, ?,?)}";

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setLong(1, vehiculo.getCliente().getDocumento());
                cs.setString(2, vehiculo.getPlaca());
                cs.setString(3, vehiculo.getColor());
                cs.setString(4, vehiculo.getMarca().getNombre_marca());
                cs.setString(5, vehiculo.getMarca().getCodigo());
                cs.registerOutParameter(6, Types.VARCHAR);

                cs.execute();

                return cs.getString(6);
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLException) {
                SQLException sqlEx = (SQLException) e.getCause();
                if (sqlEx.getErrorCode() == 20001) {
                    return "Error: Cliente no encontrado."+ vehiculo.getCliente().getDocumento();
                }
            }
            return "Error: " + e.getMessage();
        }
    }

    public String update(Vehiculo vehiculo) {
        String sql = "{call actualizar_vehiculo(?, ?, ?, ?, ?)}";
        System.out.println(vehiculo.getPresupuesto());
        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setString(1, vehiculo.getCodigo());
                cs.setString(2, vehiculo.getMarca().getNombre_marca());
                cs.setString(3, vehiculo.getColor());
                cs.setBigDecimal(4, vehiculo.getPresupuesto());
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();
                String resultado = cs.getString(5);
                return resultado;
            });
        } catch (DataAccessException e) {
            String error = "Error en la base de datos: " + e.getMessage();
            System.out.println(error);
            return error;
        }
    }

    public String delete(String id) {
        String sql = "{call eliminar_vehiculo(?, ?)}";
        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
                cs.setString(1, id);
                cs.registerOutParameter(2, Types.VARCHAR);

                cs.execute();

                return cs.getString(2);
            });
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLException) {
                SQLException sqlEx = (SQLException) e.getCause();
                if (sqlEx.getErrorCode() == 20001) {
                    return "Error: Vehículo no encontrado.";
                }
            }
            return "Error: " + e.getMessage();
        }
    }
}
