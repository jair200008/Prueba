package domi.back.repository;

import domi.back.model.Repuesto;
import domi.back.utils.ServicioUtils; // Asegúrate de importar la clase de utilidades
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepuestoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Repuesto> findAll(String id) {
        String sql = "SELECT * FROM REPUESTOS WHERE CODIGO LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Repuesto.class), id);
    }

    public Repuesto save(Repuesto repuesto) {
        // Generar el código del repuesto antes de guardarlo
        String codigo = ServicioUtils.generarCodigo();
        repuesto.setCodigo(codigo);

        jdbcTemplate.update("INSERT INTO REPUESTOS (CODIGO, NOMBRE_REPUESTO, PRECIO_UNITARIO, UNIDADES_INVENTARIO, PROVEEDOR) VALUES (?, ?, ?, ?, ?)",
                repuesto.getCodigo(), repuesto.getNombre_repuesto(), repuesto.getPrecio_unitario(), repuesto.getUnidades_inventario(), repuesto.getProveedor());
        return repuesto;
    }
}
