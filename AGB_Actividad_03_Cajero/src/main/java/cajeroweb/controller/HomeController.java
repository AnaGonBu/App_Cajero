package cajeroweb.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import cajeroweb.entities.Cuenta;
import cajeroweb.entities.Movimiento;
import cajeroweb.modelo.dao.CuentaDao;
import cajeroweb.modelo.dao.MovimientoDao;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private MovimientoDao movimientoDao;

    @GetMapping({"", "/", "/home"})
    public String inicio() {
    	
        return "home";
    }
	@GetMapping("/acceso")
	public String log(HttpSession session) {
		session.removeAttribute("mensaje");
		return "options";
	}
    @PostMapping("/acceso")
    public String procesarLog(@RequestParam int idCuenta, HttpSession session, RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaDao.findById(idCuenta);
        if (cuenta != null) {
            session.setAttribute("cuenta", cuenta);
            return "options"; // Redirige al menú de opciones
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Número de cuenta no encontrado");
            
        }return "redirect:/";
    }
	@GetMapping("/acceso/ingresar")
	public String ingresar(HttpSession session) {
		session.removeAttribute("mensaje");
		return "operation";
	}
    @PostMapping("/acceso/ingresar")
    public String operarIngreso(@RequestParam double cantidad, HttpSession session) {
        Cuenta cuenta =(Cuenta) session.getAttribute("cuenta");
        if (cuenta != null & cantidad>0) {
            cuenta.setSaldo(cuenta.getSaldo() + cantidad);
            movimientoDao.insertUno(new Movimiento(0, cuenta, new Date(), cantidad, "Ingreso"));
            cuentaDao.insertUno(cuenta); // Actualiza la cuenta
            session.setAttribute("mensaje", "Operación realizada");
            return "options"; // Redirige a la página de operación sin cerrar sesion
        }
        session.setAttribute("mensaje", "Operación no realizada");
        return "options";// Redirige a la página de operación sin cerrar sesion
    }
    
	@GetMapping("/acceso/extraer")
	public String extraer(HttpSession session) {
		session.removeAttribute("mensaje");
		return "operation2";
	}   

    @PostMapping("/acceso/extraer")
    public String operarExtraccion(@RequestParam double cantidad, HttpSession session) {
        Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        if (cuenta != null & cantidad>0) {
            if (cuenta.getSaldo() >= cantidad) {
                cuenta.setSaldo(cuenta.getSaldo() - cantidad);
                movimientoDao.insertUno(new Movimiento(0, cuenta, new Date(), cantidad, "Extracción"));//da de alta movimiento
                cuentaDao.insertUno(cuenta); // Actualiza la cuenta
                session.setAttribute("mensaje", "Operación realizada");
                return "options";// Redirige a la página de operaciónes sin cerrar sesion
            } else {
            	session.setAttribute("mensaje", "Saldo insuficiente");
                
                return "operation2"; // Redirige a la página de operación sin cerrar sesion por si quiere sacar menos
            }
        }
        return "options";// Redirige a la página de operación sin cerrar sesion
    }
    
	@GetMapping("/acceso/resume")
	public String apuntes(HttpSession session, Model model) {
        Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        if (cuenta != null) {
            List<Movimiento> movimientos = movimientoDao.findAllCuenta(cuenta.getIdCuenta()).reversed();//ordenamos los movimientos mas recientes
            model.addAttribute("movimientos", movimientos);
            if (movimientos.isEmpty()) {
            	 model.addAttribute("mensaje", "No hay movimientos para mostrar");
            	return "options";
            }else
            	
            return "movimientos"; // Redirige a la página de movimientos
        }
        return "options";
	}	
	
	@GetMapping("/acceso/transferencia")//recogemos peticion y devolvemos formulario de operacion
	public String transferir(HttpSession session) {
		session.removeAttribute("mensaje");
		return "operation3";
	}
    

	@PostMapping("/acceso/transferencia")//recogemos el formulario, procesamos, y devolvemos informacion de la operacion    
	public String operarTransfer(@RequestParam double cantidad, HttpSession session,@RequestParam int idCuenta) {
        Cuenta cuenta =(Cuenta) session.getAttribute("cuenta");
        Cuenta cuenta2 = cuentaDao.findById(idCuenta);//confirmamos que la cuenta existe
        if (cuenta2 == null) {
        	session.setAttribute("mensaje", "La cuenta de destino no existe");
        	return "operation3";
        }else if (cuenta.getIdCuenta() == cuenta2.getIdCuenta()) { //confirmamos que son cuentas diferentes
        	session.setAttribute("mensaje", "Introduzca una cuenta de destino diferente");
        	return "operation3";
        }else if (cuenta !=null && cantidad>0) { //confirmamos que no ha habido problemas recuperando sesión y que tiene sentido la operación
        		if ( cuenta.getSaldo() >= cantidad) {//confirmamos saldo suficiente
        			cuenta2.setSaldo(cuenta2.getSaldo() + cantidad);
        			movimientoDao.insertUno(new Movimiento(0, cuenta2, new Date(), cantidad, "Ingreso por transferencia"));
        			cuentaDao.insertUno(cuenta2); // Actualiza la cuenta
        			cuenta.setSaldo(cuenta.getSaldo() - cantidad);
        			movimientoDao.insertUno(new Movimiento(0, cuenta, new Date(), cantidad, "Extracción por transferencia"));
        			cuentaDao.insertUno(cuenta);
        			session.setAttribute("mensaje", "Operación realizada");
        				return "options"; // Redirige a la página de operación sin cerrar sesion
        	}session.setAttribute("mensaje", "Saldo insuficiente, operación no realizada");
        		return "operation3";// Redirige a la página de operación sin cerrar sesion
        }session.setAttribute("mensaje", "Introduzca un importe correcto");
		return "operation3";// Redirige a la página de operación sin cerrar sesion
        
        }
        
    


    @GetMapping("/acceso/logout")
    public String cerrarSesion(HttpSession session) {//recogemos objeto de sesión
    
    	session.removeAttribute("cuenta");//quitamos atributos de sesión
        session.invalidate();//invalidamos el obketo de sesión
        
        return "home";
    }
}
	
	
	


