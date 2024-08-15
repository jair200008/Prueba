package domi.back.controller;

import domi.back.model.Servicio;
import domi.back.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServiciosController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> buscarCoincidencias(@RequestParam String search) {
        return servicioService.findAll(search);
    }
}
