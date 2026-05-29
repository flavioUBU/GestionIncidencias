package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.model.Incidencia;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.service.IncidenciaService;
import com.sistemasdistr.basico.service.RabbitMQProducer;
import com.sistemasdistr.basico.service.mapper.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class IncidenciaController {

    private final IncidenciaService incidenciaService;
    private final UserService userService;
    private final RabbitMQProducer rabbitMQProducer;

    public IncidenciaController(IncidenciaService incidenciaService,
                                UserService userService,
                                RabbitMQProducer rabbitMQProducer) {
        this.incidenciaService = incidenciaService;
        this.userService = userService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @GetMapping("/incidencias")
    public String listarIncidencias(Model model) {
        model.addAttribute("incidencias", incidenciaService.listarTodas());
        return "incidencias/lista";
    }

    @GetMapping("/incidencias/nueva")
    public String mostrarFormularioNuevaIncidencia(Model model) {
        model.addAttribute("incidencia", new Incidencia());
        return "incidencias/formulario";
    }

    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(@ModelAttribute Incidencia incidencia, Principal principal) {
        incidencia.setFechaCreacion(LocalDateTime.now());

        String username = principal.getName();
        User usuario = userService.findByUsername(username);
        incidencia.setUsuarioAsignado(usuario);

        incidenciaService.guardar(incidencia);

        String mensaje = "Nueva incidencia creada por " + username + ": " + incidencia.getTitulo();
        rabbitMQProducer.enviarMensaje(mensaje);

        return "redirect:/incidencias";
    }

    @GetMapping("/incidencias/borrar/{id}")
    public String borrarIncidencia(@PathVariable Integer id) {
        incidenciaService.borrarPorId(id);
        return "redirect:/incidencias";
    }

    @GetMapping("/incidencias/editar/{id}")
    public String mostrarFormularioEditarIncidencia(@PathVariable Integer id, Model model) {
        Incidencia incidencia = incidenciaService.buscarPorId(id).orElseThrow();
        model.addAttribute("incidencia", incidencia);
        return "incidencias/formulario";
    }
}