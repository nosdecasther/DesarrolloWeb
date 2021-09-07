package com.desarrolloweb.spring.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.desarrolloweb.spring.app.repositories.ClienteRepository;
import com.desarrolloweb.spring.app.util.PageRender;
import com.desarrolloweb.spring.app.entities.Audit;
import com.desarrolloweb.spring.app.entities.Cliente;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping(value = "/detalle-cliente/{id}", method = RequestMethod.GET)
	public String detalleCliente(@PathVariable(value = "id") Long id, Model model) {

		Cliente cliente = clienteRepository.findById(id).get();
		if (cliente == null) {
			return "redirect:/listar-clientes";
		}

		model.addAttribute("titulo", "Detalle Cliente: " + cliente.getNombre());
		model.addAttribute("cliente", cliente);
		return "detalle-cliente-form";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/listar-clientes", method = RequestMethod.GET)
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Cliente> clientes = clienteRepository.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar-clientes", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "clientes";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/nuevo-cliente", method = RequestMethod.GET)
	public String nuevoCliente(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Nuevo Cliente");
		model.addAttribute("cliente", cliente);
		return "form-cliente";
	}

	@RequestMapping(value = "/editar-cliente/{id}", method = RequestMethod.GET)
	public String editarCliente(@PathVariable(value = "id") Long id, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteRepository.findById(id).get();
		} else {
			return "redirect:/listar-clientes";
		}
		model.addAttribute("titulo", "Editar Cliente");
		model.addAttribute("cliente", cliente);
		return "form-cliente";
	}

	@RequestMapping(value = "/eliminar-cliente/{id}", method = RequestMethod.GET)
	public String eliminarCliente(@PathVariable(value = "id") Long id, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteRepository.findById(id).get();
			clienteRepository.delete(cliente);
		} else {
			return "redirect:/listar-clientes";
		}

		return "redirect:/listar-clientes";
	}

	@RequestMapping(value = "/nuevo-cliente", method = RequestMethod.POST)
	public String guardarCliente(@RequestParam("file") MultipartFile foto, Cliente cliente) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();



		if (!foto.isEmpty()) {

			try {
				
				String path = "/Users/kc/Desktop/tmp/".concat(foto.getOriginalFilename());

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(path);
				Files.write(rutaCompleta, bytes);
				cliente.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (cliente.getId() != null && cliente.getId() > 0) {
			Cliente cliente2 = clienteRepository.findById(cliente.getId()).get();
			audit = new Audit(auth.getName());
			cliente.setAudit(audit);
			cliente.setId(cliente2.getId());
			cliente.getAudit().setTsCreated(cliente2.getAudit().getTsCreated());
			cliente.getAudit().setUsuCreated(cliente2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			cliente.setAudit(audit);
		}

		clienteRepository.save(cliente);
		return "redirect:/listar-clientes";
	}

}
