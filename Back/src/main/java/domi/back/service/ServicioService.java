package domi.back.service;

import domi.back.model.Servicio;
import domi.back.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;
    public List<Servicio> findAll(String id) {
        return servicioRepository.findAll(id);
    }

    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
}
