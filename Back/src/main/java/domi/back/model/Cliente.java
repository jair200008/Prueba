package domi.back.model;

import lombok.*;



@Data
public class Cliente{
    private String codigo;
    private String tipo_documento;
    private Long documento;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String celular;
    private String direccion;
    private String email;





}