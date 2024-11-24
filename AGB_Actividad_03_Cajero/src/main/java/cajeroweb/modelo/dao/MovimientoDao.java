package cajeroweb.modelo.dao;

import java.util.List;

import cajeroweb.entities.Movimiento;

public interface MovimientoDao extends IGenericoCrud<Movimiento, Integer> {

	
	List<Movimiento> findAllCuenta(Integer ID);
	
	

}
