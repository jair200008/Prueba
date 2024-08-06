package domi.back.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Repuesto {

    private String nombre_repuesto;
    private Long precio_unitario;
    private Long unidades_inventario;
    private String proveedor;
    private String codigo;
}
