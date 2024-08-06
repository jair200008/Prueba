package domi.back.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Marca implements Serializable {
    private String codigo;
    private String nombre_marca;


}
