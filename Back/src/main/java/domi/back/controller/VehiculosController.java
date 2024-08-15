package domi.back.controller;

import domi.back.model.Vehiculo;
import domi.back.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoService.findAll();
    }

    @GetMapping("/{id}")
    public Vehiculo getMecanicoById(@PathVariable String id) {
        return vehiculoService.findById(id);
    }

    @PostMapping
    public String saveMecanico(@RequestBody Vehiculo vehiculo) {
        String result = vehiculoService.save(vehiculo);
        return result;
    }

    @PutMapping("/{id}")
    public String updateMecanico(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.update(vehiculo);
    }

    @DeleteMapping("/{id}")
    public String deleteMecanico(@PathVariable String id) {
        return vehiculoService.delete(id);
    }


}
