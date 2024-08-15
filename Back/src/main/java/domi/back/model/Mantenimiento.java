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
    private String codigo;
    private String estado;
    private Vehiculo vehiculo;
    private String fecha;
    private Mecanico mecanico;
    private Long presupuesto;
}