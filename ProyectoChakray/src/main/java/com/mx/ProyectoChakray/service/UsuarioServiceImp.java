package com.mx.ProyectoChakray.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;

import com.mx.ProyectoChakray.dto.InicioSesionDTO;
import com.mx.ProyectoChakray.model.Direccion;
import com.mx.ProyectoChakray.model.Usuario;
import com.mx.ProyectoChakray.security.EncryptionUtil;

@Service
public class UsuarioServiceImp {

	private List<Usuario> usuario = new ArrayList<>();
	 
	private DateTimeFormatter formato =DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	// @formatter:off// @formatter:on
	public UsuarioServiceImp() {
        Usuario usuarioPorDefecto = new Usuario();
        usuarioPorDefecto.setId(UUID.randomUUID());
        usuarioPorDefecto.setNombre("Admin");
        usuarioPorDefecto.setCorreoE("admin@example.com");
        usuarioPorDefecto.setCelular("0000000000");
        usuarioPorDefecto.setTax_id("ADMIN001");
        usuarioPorDefecto.setContrasenna(EncryptionUtil.encrypt("admin123"));
        usuarioPorDefecto.setCrear_fecha(ZonedDateTime.now(ZoneId.of("Indian/Antananarivo")).format(formato));
        List<Direccion> listaDirecciones = new ArrayList<>();
        Direccion dir = new Direccion();
        listaDirecciones.add(dir);
        usuarioPorDefecto.setDireccion(listaDirecciones);
        usuarioPorDefecto.setContrasenna(EncryptionUtil.encrypt("admin123"));
        usuario.add(usuarioPorDefecto);
    }

	public List<Usuario> mostrarUsuarios(){
		return usuario;
	}
	
    public boolean annadirUsuario(Usuario nuevoUsuario) {
		for(Usuario u : usuario) {
			if(u.getTax_id().equals(nuevoUsuario.getTax_id())) {
				return false;
			}
		}
		if (rfcValido(nuevoUsuario.getTax_id()) == false) {
		    return false; 
		}

		if (telefonoValido(nuevoUsuario.getCelular()) == false) {
		    return false;
		}

		nuevoUsuario.setId(UUID.randomUUID());
				
		ZonedDateTime zonaHorarioaMadagascar = ZonedDateTime.now(ZoneId.of("Indian/Antananarivo"));
		nuevoUsuario.setCrear_fecha(zonaHorarioaMadagascar.format(formato));
		usuario.add(nuevoUsuario);

		String constrasennaEncriptada = EncryptionUtil.encrypt(nuevoUsuario.getContrasenna());
	    nuevoUsuario.setContrasenna(constrasennaEncriptada);
		
		return true;	
	}
	
	public boolean borrarUsuarioXid(UUID id) {
		for(int i=0;i<usuario.size();i++) {
			if(usuario.get(i).getId().equals(id)) {
				usuario.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean editarUsuario(UUID id, Usuario editarUsuario) {
	    for (Usuario i : usuario) {
	        if (i.getId().equals(id)) {
	            if (editarUsuario.getTax_id() != null) {
	                for (Usuario u : usuario) {
	                    if (!u.getId().equals(id) && u.getTax_id().equals(editarUsuario.getTax_id())) {
	                        return false;
	                    }
	                }
	                if (!rfcValido(editarUsuario.getTax_id())) {
	                    return false;
	                }
	                i.setTax_id(editarUsuario.getTax_id());
	            }
	            if (editarUsuario.getCelular() != null) {
	                if (!telefonoValido(editarUsuario.getCelular())) {
	                    return false;
	                }
	                i.setCelular(editarUsuario.getCelular());
	            }
	            if (editarUsuario.getContrasenna() != null) {
	                String encriptada = EncryptionUtil.encrypt(editarUsuario.getContrasenna());
	                i.setContrasenna(encriptada);
	            }
	            if (editarUsuario.getNombre() != null) i.setNombre(editarUsuario.getNombre());
	            if (editarUsuario.getCorreoE() != null) i.setCorreoE(editarUsuario.getCorreoE());
	            if (editarUsuario.getDireccion() != null) i.setDireccion(editarUsuario.getDireccion());

	            return true;
	        }
	    }
	    return false;
	}

	public InicioSesionDTO inicioSesion(String taxid,String contrasenna) {
	    System.out.println("Buscando usuario con tax_id: " + taxid);
	    for(Usuario u : usuario) {
	        if(u.getTax_id().equals(taxid)) {
	            String desencriptar = EncryptionUtil.decrypt(u.getContrasenna());
	            System.out.println("Contraseña guardada desencriptada: " + desencriptar);
	            System.out.println("tax_id: " + u.getTax_id());
	            System.out.println("desencriptar: " + desencriptar);
	            System.out.println("contrasenna recibida: " + contrasenna);

	            if(desencriptar != null && desencriptar.equals(contrasenna)) {
	                InicioSesionDTO sesion = new InicioSesionDTO();
	                sesion.setCorreoE(u.getCorreoE());
	                sesion.setTex_id(u.getTax_id());
	                sesion.setNombre(u.getNombre());
	                return sesion;
	            }
	        }
	    }
	    return null;
	}

	private boolean telefonoValido(String celular) {
	    return celular != null && celular.matches("^\\+?\\d{10,15}$");
	}

	private boolean rfcValido(String rfc) {
	    return rfc != null && rfc.matches("^[A-Z0-9]{13}$");
	}
	public List<Usuario> obtenerUsuariosFiltradosYOrdenados(String ordenarPor, String filtro) {
	    List<Usuario> copia = new ArrayList<>(usuario);

	    if (filtro != null && !filtro.isEmpty()) {
	        String[] partes = filtro.split("[ +]");
	        if (partes.length == 3) {
	            String campo = partes[0];
	            String operacion = partes[1];
	            String valor = partes[2];

	            for (int i = copia.size() - 1; i >= 0; i--) {
	                Usuario usuario = copia.get(i);
	                boolean cumple = cumpleFiltro(usuario, campo, operacion, valor);
	                if (!cumple) {
	                    copia.remove(i);
	                }
	            }
	        }
	    }

	    if (ordenarPor != null && !ordenarPor.isEmpty()) {
	        int n = copia.size();
	        for (int i = 0; i < n - 1; i++) {
	            for (int j = 0; j < n - i - 1; j++) {
	                if (compararCampo(copia.get(j), copia.get(j + 1), ordenarPor) > 0) {
	                    Usuario temp = copia.get(j);
	                    copia.set(j, copia.get(j + 1));
	                    copia.set(j + 1, temp);
	                }
	            }
	        }
	    }

	    return copia;
	}


private int compararCampo(Usuario u1, Usuario u2, String campo) {
    switch (campo) {
        case "id": return u1.getId().toString().compareTo(u2.getId().toString());
        case "correoE": return u1.getCorreoE().compareTo(u2.getCorreoE());
        case "nombre": return u1.getNombre().compareTo(u2.getNombre());
        case "celular": return u1.getCelular().compareTo(u2.getCelular());
        case "tax_id": return u1.getTax_id().compareTo(u2.getTax_id());
        case "crear_fecha": return u1.getCrear_fecha().compareTo(u2.getCrear_fecha());
        default: return 0;
    }
}
private boolean cumpleFiltro(Usuario usuario, String campo, String operacion, String valor) {
    String dato = "";
    switch (campo) {
        case "id": dato = usuario.getId().toString(); break;
        case "correoE": dato = usuario.getCorreoE(); break;
        case "nombre": dato = usuario.getNombre(); break;
        case "celular": dato = usuario.getCelular(); break;
        case "tax_id": dato = usuario.getTax_id(); break;
        case "crear_fecha": dato = usuario.getCrear_fecha(); break;
        default: return false;
    }
    dato = dato.toLowerCase();
    valor = valor.toLowerCase();

    switch (operacion) {
        case "co": return dato.contains(valor);
        case "eq": return dato.equals(valor);
        case "sw": return dato.startsWith(valor);
        case "ew": return dato.endsWith(valor);
        default: return false;
    }
}



}
