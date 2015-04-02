package ve.com.joincic.joincicapp.controllers;

/**
 * Created by carla on 02/04/15.
 */
public class Schedule {
    int id;
    int patrocinante_id;
    int ponente_id;
    String tema;
    String titulo;
    String descripcion;
    String hora_ini;
    String hora_fin;
    String dia;

    public Schedule(int id, int patrocinante_id, int ponente_id, String tema, String titulo,
                    String descripcion, String hora_ini, String hora_fin, String dia) {
        this.id = id;
        this.patrocinante_id = patrocinante_id;
        this.ponente_id = ponente_id;
        this.tema = tema;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;
        this.dia = dia;
    }
}
