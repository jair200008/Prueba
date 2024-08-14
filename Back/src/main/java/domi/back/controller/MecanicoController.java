package domi.back.controller;

import domi.back.model.Mecanico;
import domi.back.service.MecanicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/mecanicos")
public class MecanicoController {


    @Autowired
    private MecanicoService mecanicoService;

    @GetMapping
    public List<Mecanico> getAllMecanicos() {
        return mecanicoService.findAll();
    }

    @GetMapping("/{id}")
    public Mecanico getMecanicoById(@PathVariable String id) {
        return mecanicoService.findById(id);
    }

    @PostMapping
    public String saveMecanico(@RequestBody Mecanico mecanico) {
        String result = mecanicoService.save(mecanico);
        return result;
    }

    @PutMapping("/{id}")
    public String updateMecanico(@RequestBody Mecanico mecanico) {
        return mecanicoService.update(mecanico);
    }

    @DeleteMapping("/{id}")
    public String deleteMecanico(@PathVariable String id) {
        return mecanicoService.delete(id);
    }
}