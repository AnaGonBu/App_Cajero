package cajeroweb.modelo.dao;



public interface IGenericoCrud <E,ID> {

    E findById(ID id);
    E insertUno (E entidad);

  

	

}
