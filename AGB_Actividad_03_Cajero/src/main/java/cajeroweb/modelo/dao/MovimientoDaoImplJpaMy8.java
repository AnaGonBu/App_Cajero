package cajeroweb.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.entities.Movimiento;
import cajeroweb.repository.MovimientoRepository;


import java.util.List;

@Repository
public class MovimientoDaoImplJpaMy8 implements MovimientoDao {
    @Autowired
    private MovimientoRepository mrepo;

	@Override
	public Movimiento findById(Integer ID) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Movimiento insertUno(Movimiento movimiento) {
		try {
			mrepo.save(movimiento);
			//return movimiento;
		}catch (Exception e) {
			e.printStackTrace();
			
		}return null;
		
	}

	@Override
	public List<Movimiento> findAllCuenta(Integer ID) {
		try {
			
			return mrepo.findMovimientosByCuenta(ID);

		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
