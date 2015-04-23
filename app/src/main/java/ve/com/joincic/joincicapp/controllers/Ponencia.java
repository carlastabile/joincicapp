package ve.com.joincic.joincicapp.controllers;

/**
 * Created by nuevo on 4/23/15.
 */

/**
 * This class represents the object Ponencia to be parsed by GSON
 * <p/>
 * Created by Carla Urrea Stabile on 02/04/15.
 */
public class Ponencia {
    int id;
    int patrocinante_id;
    int ponente_id;
    String tema;
    String titulo;
    String descripcion;
    String hora_ini;
    String hora_fin;
    String dia;
    String requerimientos;

    public Ponencia(int id, int patrocinante_id, int ponente_id, String tema, String titulo,
                        String descripcion, String hora_ini, String hora_fin, String dia, String requerimientos) {
        this.id = id;
        this.patrocinante_id = patrocinante_id;
        this.ponente_id = ponente_id;
        this.tema = tema;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;
        this.dia = dia;
        this.requerimientos = requerimientos;
    }

    public int getId() {
        return id;
    }

    public int getPatrocinante_id() {
        return patrocinante_id;
    }

    public int getPonente_id() {
        return ponente_id;
    }

    public String getTema() {
        return tema;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public String getDia() {
        return dia;
    }

    public String getRequerimientos() {
        return requerimientos;
    }
}

