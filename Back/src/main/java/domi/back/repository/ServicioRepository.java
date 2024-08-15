package domi.back.repository;

import domi.back.model.Servicio;
import domi.back.utils.ServicioUtils; // Asegúrate de importar la clase de utilidades
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServicioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Servicio> findAll(String id) {
        String sql = "SELECT * FROM SERVICIOS WHERE CODIGO LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Servicio.class), id);
    }

    public Servicio save(Servicio servicio) {
        // Generar el código del servicio antes de guardarlo
        String codigo = ServicioUtils.generarCodigo();
        servicio.setCodigo(codigo);
        System.out.println(servicio.getNombre_servicio());
        jdbcTemplate.update("INSERT INTO SERVICIOS (CODIGO, NOMBRE_SERVICIO, PRECIO) VALUES (?, ?, ?)",
                servicio.getCodigo(), servicio.getNombre_servicio(), servicio.getPrecio());
        return servicio;
    }
}
