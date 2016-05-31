package com.joincic.joincicapp.controllers;

/**
 * Created by carla on 25/05/15.
 */
public class Assistant {
    Participante participante;

    public class Participante{
        int id;
        int cedula;
        String correo;
        String nombre;
        String apellido;

        public int getId() {
            return id;
        }

        public int getCedula() {
            return cedula;
        }

        public String getCorreo() {
            return correo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }
    }

    public Participante getParticipante() {
        return participante;
    }
}
