package domi.back.controller;

import domi.back.model.Mantenimiento;
import domi.back.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimientos")
@CrossOrigin(origins = "http://localhost:5173")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping("/{codMecanico}")
    public Mantenimiento getMantenimientoByMecanico(@PathVariable String codMecanico) {
        return mantenimientoService.getMantenimientosByMecanico(codMecanico);
    }

    @PostMapping
    public String createMantenimiento(@RequestBody Mantenimiento mantenimiento) {
        return mantenimientoService.createMantenimiento(mantenimiento);
    }

    @PutMapping("/{codigo}")
    public String updateMantenimiento(@PathVariable String codigo, @RequestBody Mantenimiento mantenimiento) {
        return mantenimientoService.updateMantenimiento(codigo, mantenimiento);
    }

    @DeleteMapping("/{codigo}")
    public String deleteMantenimiento(@PathVariable String codigo) {
        return mantenimientoService.deleteMantenimiento(codigo);
    }
}