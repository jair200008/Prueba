package domi.back.service;

import domi.back.model.Mecanico;
import domi.back.repository.MecanicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoService {

 @Autowired
 private MecanicoRepository mecanicos;

    public List<Mecanico> findAll() {
        return mecanicos.findAll();
    }

    public Mecanico findById(String id) {
        return mecanicos.findById(id);
    }

    public String save(Mecanico mecanico) {
        String resultado = mecanicos.save(mecanico);
        return resultado;
    }

    public String update(Mecanico mecanico) {
        String resultado = mecanicos.update(mecanico);
        return resultado;
    }

    public String delete(String id) {
        String resultado = mecanicos.delete(id);
        return resultado;
    }

}
