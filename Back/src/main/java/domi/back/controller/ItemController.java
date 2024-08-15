package domi.back.controller;

import domi.back.model.Repuesto;
import domi.back.model.Servicio;
import domi.back.service.RepuestoService;
import domi.back.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private RepuestoService repuestoService;

    @Autowired
    private ServicioService servicioService;

    @PostMapping("/repuestos")
    public ResponseEntity<Repuesto> createRepuesto(@RequestBody Repuesto repuesto) {
        Repuesto nuevoRepuesto = repuestoService.saveRepuesto(repuesto);
        return new ResponseEntity<>(nuevoRepuesto, HttpStatus.CREATED);
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.saveServicio(servicio);
        return new ResponseEntity<>(nuevoServicio, HttpStatus.CREATED);
    }
}
