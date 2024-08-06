package domi.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Mantenimiento {
    private Long Estado;
    private String cod_vehiculo;
    private Date fecha;
    private String cod_mecanico;
    private Long presupuesto;
    private String codigo;

}
