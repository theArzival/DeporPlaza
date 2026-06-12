package com.Sistema.DeporPlaza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Horario;
import com.Sistema.DeporPlaza.repository.HorarioRespository;

@Service
public class HorarioService {
    @Autowired
    private HorarioRespository horarioRespository;

    public List<Horario> listar() {
        return horarioRespository.findAll();
    }

    public void guardar(Horario horario) {
        horarioRespository.save(horario);
    }

    public void eliminar(Integer idHorario) {
        horarioRespository.deleteById(idHorario);
    }

    public Horario buscarById(Integer idHorario) {
        return horarioRespository.findById(idHorario).orElse(null);
    }

    // public Integer ultimoId() {
    // Integer ultimoId = horarioRespository.findMaxId();
    // if (ultimoId == null) {
    // return 1;
    // }
    // return ultimoId + 1;
    // }
}
