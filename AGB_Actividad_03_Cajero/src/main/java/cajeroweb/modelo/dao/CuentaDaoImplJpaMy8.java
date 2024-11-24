package cajeroweb.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajeroweb.entities.Cuenta;
import cajeroweb.repository.CuentaRepository;


@Repository
public class CuentaDaoImplJpaMy8 implements CuentaDao{

	    @Autowired
	    private CuentaRepository crepo;

	    @Override
	    public Cuenta findById(Integer id) {

			try {
		        return crepo.findById(id).orElse(null);
				
			}catch (Exception e) {
				e.printStackTrace();
				return null;	
			}			
	    }


		@Override
		public Cuenta insertUno(Cuenta cuenta) {
			try {
				crepo.save(cuenta);
				return cuenta;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
	}






