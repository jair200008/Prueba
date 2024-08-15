package domi.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    private String placa;
    private String color;
    private Marca marca;
    private Cliente cliente;          // Asociación con la clase Cliente
    private Mecanico mecanico;        // Asociación con la clase Mecanico (si quieres manejarlo directamente)
    private List<Foto> fotos;         // Lista de fotos asociadas al vehículo
    private String codigo;            // Código único del vehículo
    private BigDecimal presupuesto;
}
