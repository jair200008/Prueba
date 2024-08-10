package repository;


import model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("clienteRepository")
public interface ClienteRepository extends CrudRepository<Cliente, String> {

}