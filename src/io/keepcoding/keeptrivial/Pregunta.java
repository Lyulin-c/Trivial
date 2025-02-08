package io.keepcoding.keeptrivial;

import java.util.List;

public class Pregunta {
	
	private String enunciado;
    private List<String> opciones;
    private int respuestaCorrecta;
    private String categoria;

    public Pregunta(String enunciado, List<String> opciones, int respuestaCorrecta, String categoria) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void mostrarPregunta() {
        System.out.println("\nCategoría: " + categoria);
        System.out.println(enunciado);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + opciones.get(i));
        }
    }

    public boolean esCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }
	
}
