package com.Sistema.DeporPlaza.dto;

public class EventoCalendarDTO {
    private String title;
    private String start;
    private String end;
    private String color;

    public EventoCalendarDTO() {

    }

    public EventoCalendarDTO(String title, String start, String end, String color) {
        this.color = color;
        this.end = end;
        this.start = start;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
