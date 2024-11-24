package cajeroweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cajeroweb.entities.Movimiento;


public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
	
	@Query("select m from Movimiento m where m.cuenta.idCuenta = ?1")//No puedo ordenar por fecha, asi que lo hago directamente al mostrar los datos
	public List<Movimiento> findMovimientosByCuenta(int idCuenta);



}
