package domi.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    private String codigo;
    private String nombre_servicio;
    private Long precio;
    private String comentario;

}
