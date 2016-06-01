package com.joincic.joincicapp.controllers;

/**
 * This class represents the object Ponencia to be parsed by GSON
 * <p/>
 * Created by Carla Urrea Stabile on 02/04/15.
 */
public class Schedule {
    Presentation ponencia;
    WorkTable mesa_de_trabajo;

    public WorkTable getMesa_de_trabajo() {
        return mesa_de_trabajo;
    }

    public Presentation getPonencia() {
        return ponencia;
    }

    public class WorkTable {
        int id;
        int patrocinante_id;
        String tema;
        boolean sorteada;
        int ponente_id;
        String titulo;
        String descripcion;
        String lugar;
        int capacidad;
        String hora_ini;
        String hora_fin;
        String dia;
        String requerimientos;
        Ponente ponente;

        public Ponente getPonente() {

            return ponente;
        }

        public void setPonente(Ponente ponente) {
            this.ponente = ponente;
        }

        public int getId() {
            return id;
        }

        public int getPatrocinante_id() {
            return patrocinante_id;
        }

        public String getTema() {
            return tema;
        }

        public boolean isSorteada() {
            return sorteada;
        }

        public int getPonente_id() {
            return ponente_id;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getLugar() {
            return lugar;
        }

        public int getCapacidad() {
            return capacidad;
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

        public class Ponente {
            String nombre;
            String apellido;

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public String getApellido() {
                return apellido;
            }

            public void setApellido(String apellido) {
                this.apellido = apellido;
            }
        }
    }

    public class Presentation {
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
        Ponente ponente;

        public void setId(int id) {
            this.id = id;
        }

        public Ponente getPonente() {
            return ponente;
        }

        public void setPonente(Ponente ponente) {
            this.ponente = ponente;
        }

        public Presentation(int id, int patrocinante_id, int ponente_id, String tema, String titulo,
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


        public class Ponente {
            String nombre;
            String apellido;

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public String getApellido() {
                return apellido;
            }

            public void setApellido(String apellido) {
                this.apellido = apellido;
            }
        }
    }
}
