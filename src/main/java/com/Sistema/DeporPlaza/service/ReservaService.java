package com.Sistema.DeporPlaza.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.dto.EventoCalendarDTO;
import com.Sistema.DeporPlaza.model.Reserva;
import com.Sistema.DeporPlaza.repository.ReservaRepository;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    public Long contar() {
        return reservaRepository.count();
    }

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public List<Reserva> ultimas5Reservas() {
        return reservaRepository.findTop5ByOrderByIdReservaDesc();

    }

    public List<Object[]> obtenerReservasPorMes() {
        return reservaRepository.reservasPorMes();
    }

    public List<Object[]> obtenerReservasPorTipo() {
        return reservaRepository.reservasPorTipo();
    }

    public void guardar(Reserva reserva) {

        reservaRepository.save(reserva);
        log.info(
                "Reserva registrada correctamente. Usuario: {}, Campo: {}, Fecha: {}",
                reserva.getUsuario().getIdUsuario(),
                reserva.getCampo().getIdCampo(),
                reserva.getFechaReserva());
    }

    public void validar(Reserva reserva) {
        if (ObjectUtils.isEmpty(reserva)) {
            log.error("Reserva nula");
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        if (ObjectUtils.isEmpty(reserva.getCampo())) {
            log.warn("Reserva sin campo deportivo");
            throw new IllegalArgumentException("Debe seleccionar un campo deportivo");
        }

        if (ObjectUtils.isEmpty(reserva.getHorario())) {
            log.warn("Reserva sin horario");
            throw new IllegalArgumentException("Debe seleccionar un horario");
        }

        if (ObjectUtils.isEmpty(reserva.getUsuario())) {
            log.warn("Reserva sin usuario autenticado");
            throw new IllegalArgumentException("Debe iniciar sesión");
        }

        if (ObjectUtils.isEmpty(reserva.getFechaReserva())) {
            log.warn("Reserva sin fecha");
            throw new IllegalArgumentException("Debe seleccionar una fecha");
        }

        if (reserva.getFechaReserva().isBefore(LocalDate.now())) {
            log.warn("Intento de reservar fecha pasada: {}", reserva.getFechaReserva());
            throw new IllegalArgumentException("No se puede reservar fechas pasadas");
        }

        if (StringUtils.isBlank(reserva.getObservaciones())) {
            log.warn("Reserva sin observaciones");
            throw new IllegalArgumentException("Debe ingresar una observación");
        }
    }

    public void eliminar(Integer idUsuario) {
        reservaRepository.deleteById(idUsuario);
    }

    public Reserva buscarById(Integer idReserva) {
        return reservaRepository.findById(idReserva).orElse(null);
    }

    public List<EventoCalendarDTO> obtenerEventosCalendario() {

        List<Reserva> reservas = reservaRepository.findAll();

        List<EventoCalendarDTO> eventos = new ArrayList<>();

        for (Reserva reserva : reservas) {

            String fecha = reserva.getFechaReserva().toString();

            String inicio = fecha + "T" +
                    reserva.getHorario().getHoraInicio();

            String fin = fecha + "T" +
                    reserva.getHorario().getHoraFin();

            EventoCalendarDTO evento = new EventoCalendarDTO(
                    reserva.getCampo().getNombreCampo(),
                    inicio,
                    fin,
                    "#dc3545");

            eventos.add(evento);
        }

        return eventos;
    }

    public List<EventoCalendarDTO> obtenerEventosPorCampo(Integer idCampo) {

        List<Reserva> reservas = reservaRepository.findByCampoIdCampo(idCampo);

        List<EventoCalendarDTO> eventos = new ArrayList<>();

        for (Reserva reserva : reservas) {

            String fecha = reserva.getFechaReserva().toString();

            String inicio = fecha + "T" +
                    reserva.getHorario().getHoraInicio();

            String fin = fecha + "T" +
                    reserva.getHorario().getHoraFin();

            EventoCalendarDTO evento = new EventoCalendarDTO(
                    reserva.getCampo().getNombreCampo(),
                    inicio,
                    fin,
                    "#dc3545");

            eventos.add(evento);
        }

        return eventos;
    }
}
