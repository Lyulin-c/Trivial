package io.keepcoding.keeptrivial;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
	
	private String nombre;
    public List<String> quesitos;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.quesitos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public boolean tieneQuesito(String categoria) {
        return quesitos.contains(categoria);
    }

    public void ganarQuesito(String categoria) {
    	if (!tieneQuesito(categoria)) { 
            quesitos.add(categoria);
        }
    }

    public boolean haGanado() {
        return quesitos.size() == 5;
    }

    public void mostrarQuesitos() {
        System.out.println(nombre + " ha conseguido: " + quesitos);
    }
}
