package domi.back.service;

import domi.back.model.Cliente;
import domi.back.model.Vehiculo;
import domi.back.repository.ClienteRepository;
import domi.back.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(String id) {
        return vehiculoRepository.findById(id);
    }

    public String save(Vehiculo vehiculo) {
        String resultado = vehiculoRepository.save(vehiculo );
        return resultado;

    }

    public String update(Vehiculo detalle) {
        String resultado = vehiculoRepository.update(detalle);
        return resultado;
    }
    public String delete(String id) {
        String resultado = vehiculoRepository.delete(id);
        return resultado;
    }


}
