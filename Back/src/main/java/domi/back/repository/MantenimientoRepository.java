package domi.back.repository;

import domi.back.model.Cliente;
import domi.back.model.Mantenimiento;
import domi.back.model.Mecanico;
import domi.back.model.Vehiculo;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class MantenimientoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Mantenimiento getMantenimientosByMecanico(String codMecanico) {
        String sql = "{call get_mantenimiento_by_mecanico(?, ?)}";

        try {
            return jdbcTemplate.execute(sql, (CallableStatementCallback<Mantenimiento>) cs -> {
                cs.setString(1, codMecanico);
                cs.registerOutParameter(2, OracleTypes.CURSOR);

                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(2);
                if (rs.next()) {
                    Mantenimiento mantenimiento = new Mantenimiento();
                    mantenimiento.setCodigo(rs.getString("codigo"));
                    mantenimiento.setEstado(rs.getString("estado"));

                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setPlaca(rs.getString("placa_vehiculo"));
                    mantenimiento.setVehiculo(vehiculo);

                    // La fecha ahora se obtiene como String
                    mantenimiento.setFecha(rs.getString("fecha"));

                    Mecanico mecanico = new Mecanico();
                    mecanico.setCodigo(codMecanico);
                    String nombreCompletoMecanico = rs.getString("nombre_mecanico");
                    String[] nombrePartes = nombreCompletoMecanico.split(" ");
                    if (nombrePartes.length >= 2) {
                        mecanico.setPrimer_nombre(nombrePartes[0]);
                        mecanico.setPrimer_apellido(nombrePartes[1]);
                    }
                    mantenimiento.setMecanico(mecanico);

                    mantenimiento.setPresupuesto(rs.getLong("presupuesto"));

                    return mantenimiento;
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener mantenimiento: " + e.getMessage());
        }
    }


    public String createMantenimiento(Mantenimiento mantenimiento) {
        jdbcTemplate.update("INSERT INTO Mantenimientos (codigo, estado, cod_vehiculo, fecha, cod_mecanico, presupuesto) VALUES (?, ?, ?, ?, ?, ?)",
                mantenimiento.getCodigo(), mantenimiento.getEstado(), mantenimiento.getMecanico().getCodigo(),
                mantenimiento.getFecha(), mantenimiento.getMecanico().getCodigo(), mantenimiento.getPresupuesto());
        return "Mantenimiento creado exitosamente";
    }

    public String updateMantenimiento(String codigo, Mantenimiento mantenimiento) {
        jdbcTemplate.update("UPDATE Mantenimientos SET estado = ?, cod_vehiculo = ?, fecha = ?, cod_mecanico = ?, presupuesto = ? WHERE codigo = ?",
                mantenimiento.getEstado(), mantenimiento.getVehiculo().getCodigo(), mantenimiento.getFecha(),
                mantenimiento.getMecanico().getCodigo(), mantenimiento.getPresupuesto(), codigo);
        return "Mantenimiento actualizado exitosamente";
    }

    public String deleteMantenimiento(String codigo) {
        jdbcTemplate.update("DELETE FROM Mantenimientos WHERE codigo = ?", codigo);
        return "Mantenimiento eliminado exitosamente";
    }
}