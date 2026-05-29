package com.sistemasdistr.basico.service;

import com.sistemasdistr.basico.model.Incidencia;
import com.sistemasdistr.basico.repository.IncidenciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;

    public IncidenciaService(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    public List<Incidencia> listarTodas() {
        return incidenciaRepository.findAll();
    }

    public Incidencia guardar(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public Optional<Incidencia> buscarPorId(Integer id) {
        return incidenciaRepository.findById(id);
    }

    public void borrarPorId(Integer id) {
        incidenciaRepository.deleteById(id);
    }
}