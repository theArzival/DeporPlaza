package com.Sistema.DeporPlaza.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.Sistema.DeporPlaza.model.Reserva;

@Service
public class ApachePoiService {
        public byte[] generarExcel(List<Reserva> reservas) throws IOException {
                XSSFWorkbook libro = new XSSFWorkbook();
                XSSFSheet hoja = libro.createSheet("ReporteReservas");
                // CREAMOS EL ESTILO DDEL HEAD
                CellStyle estiloCabecera = libro.createCellStyle();

                // ESTABLEZCO QUE COLOR Y QUE TODO EL FONDO SEA DE ESE COLOR Y ALINEACION X
                estiloCabecera.setFillForegroundColor(
                                IndexedColors.DARK_GREEN.getIndex());

                estiloCabecera.setFillPattern(
                                FillPatternType.SOLID_FOREGROUND);

                estiloCabecera.setAlignment(
                                HorizontalAlignment.CENTER);
                // ESTABLECEMOS LOS BORDES PARA EL HEAD
                estiloCabecera.setBorderTop(BorderStyle.THIN);
                estiloCabecera.setBorderBottom(BorderStyle.THIN);
                estiloCabecera.setBorderLeft(BorderStyle.THIN);
                estiloCabecera.setBorderRight(BorderStyle.THIN);
                // ESTABLECES LOS ESTILOS DE LA FUENTE DE LETRA NEGRITA Y COLOR BLANCO DEL HEAD
                Font fuenteCabecera = libro.createFont();
                fuenteCabecera.setBold(true);
                fuenteCabecera.setColor(
                                IndexedColors.WHITE.getIndex());

                estiloCabecera.setFont(fuenteCabecera);
                CellStyle estiloDatos = libro.createCellStyle();

                estiloDatos.setBorderTop(BorderStyle.THIN);
                estiloDatos.setBorderBottom(BorderStyle.THIN);
                estiloDatos.setBorderLeft(BorderStyle.THIN);
                estiloDatos.setBorderRight(BorderStyle.THIN);
                Row header = hoja.createRow(0);

                String[] columnas = {
                                "ID",
                                "Cliente",
                                "Campo",
                                "Fecha",
                                "Horario",
                                "Estado"
                };

                for (int i = 0; i < columnas.length; i++) {

                        Cell celda = header.createCell(i);

                        celda.setCellValue(columnas[i]);

                        celda.setCellStyle(estiloCabecera);

                }
                int fila = 1;

                for (Reserva r : reservas) {

                        Row row = hoja.createRow(fila++);

                        Cell celda0 = row.createCell(0);
                        celda0.setCellValue(r.getIdReserva());
                        celda0.setCellStyle(estiloDatos);

                        Cell celda1 = row.createCell(1);
                        celda1.setCellValue(r.getUsuario().getApellidos() + " " + r.getUsuario().getNombres());
                        celda1.setCellStyle(estiloDatos);

                        Cell celda2 = row.createCell(2);
                        celda2.setCellValue(r.getCampo().getNombreCampo());
                        celda2.setCellStyle(estiloDatos);

                        Cell celda3 = row.createCell(3);
                        celda3.setCellValue(r.getFechaReserva().toString());
                        celda3.setCellStyle(estiloDatos);

                        Cell celda4 = row.createCell(4);
                        celda4.setCellValue(
                                        r.getHorario().getHoraInicio()
                                                        + " - "
                                                        + r.getHorario().getHoraFin());
                        celda4.setCellStyle(estiloDatos);

                        Cell celda5 = row.createCell(5);
                        celda5.setCellValue(r.getEstado());
                        celda5.setCellStyle(estiloDatos);
                }
                for (int i = 0; i < columnas.length; i++) {
                        hoja.autoSizeColumn(i);
                }

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                libro.write(out);

                libro.close();

                return out.toByteArray();
        }
}
