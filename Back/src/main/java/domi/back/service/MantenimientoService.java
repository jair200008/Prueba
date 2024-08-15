package domi.back.service;

import domi.back.model.Mantenimiento;
import domi.back.repository.MantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoService {
    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    public Mantenimiento getMantenimientosByMecanico(String codMecanico) {
        return mantenimientoRepository.getMantenimientosByMecanico(codMecanico);
    }

    public String createMantenimiento( Mantenimiento mantenimiento) {
        return "";
    }

    public String updateMantenimiento( String codigo, Mantenimiento mantenimiento) {
        return "";
    }

    public String deleteMantenimiento( String codigo) {
        return "";
    }
}
