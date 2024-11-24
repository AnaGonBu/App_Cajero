package cajeroweb.restontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cajeroweb.entities.Cuenta;
import cajeroweb.entities.Movimiento;
import cajeroweb.modelo.dao.CuentaDao;
import cajeroweb.modelo.dao.MovimientoDao;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/")
public class cajeroRestcontroller {
	
	@Autowired
	private MovimientoDao mdao;
	
	@Autowired
	private CuentaDao cdao;
	
	@GetMapping("/acceso/movimientos/{id}")
	public List<Movimiento> porId(@PathVariable int id){
		
		return mdao.findAllCuenta(id);
		
	}
	@GetMapping("/acceso/procesarLog/{id}")
	public Cuenta procesarLog(@PathVariable int id){
		
		return cdao.findById(id);
		
	}
    @GetMapping("/acceso/ingresar/{id}/{cantidad}")
    public String operarIngreso(@PathVariable double cantidad,@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
     
        if (cdao.findById(id) != null) {
        	Cuenta cuenta= cdao.findById(id);
            cuenta.setSaldo(cuenta.getSaldo() + cantidad);
            mdao.insertUno(new Movimiento(0, cuenta, new Date(), cantidad, "Ingreso"));
            cdao.insertUno(cuenta); // Actualiza la cuenta
            return "operation"; // Redirige a la página de operación
        }
        return "redirect:/";
    }
    @GetMapping("/acceso/extraccion/{id}/{cantidad}")
    public String operarExtraccion(@PathVariable double cantidad, HttpSession session, RedirectAttributes redirectAttributes,@PathVariable int id) {
       // Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        if (cdao.findById(id) != null) {
        	Cuenta cuenta= cdao.findById(id);
            if (cuenta.getSaldo() >= cantidad) {
                cuenta.setSaldo(cuenta.getSaldo() - cantidad);
                mdao.insertUno(new Movimiento(0, cuenta, new Date(), cantidad, "Extracción"));
                cdao.insertUno(cuenta); // Actualiza la cuenta
                redirectAttributes.addFlashAttribute("mensaje", "Operación realizada");
               // return "redirect:/";
                
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "Saldo insuficiente");
                return "operation"; // Redirige a la página de operación
            }
            
           
        }
        return "redirect:/";
    }
	

}
