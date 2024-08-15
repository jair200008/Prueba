package domi.back.service;
import domi.back.model.Repuesto;
import domi.back.repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    public Repuesto saveRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }
}