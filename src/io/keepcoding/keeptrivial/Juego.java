package io.keepcoding.keeptrivial;

import java.io.*;
import java.util.*;

public class Juego {
	
	private List<Equipo> equipos;
    private Map<String, List<Pregunta>> preguntasPorCategoria;
    private Scanner scanner;

    public Juego() {
        equipos = new ArrayList<>();
        preguntasPorCategoria = new HashMap<>();
        scanner = new Scanner(System.in);
        cargarPreguntas();
    }

    private void cargarPreguntas() {
        File folder = new File("questions");
        if (!folder.exists()) {
            Main.title("Error: Carpeta 'questions' no encontrada.");
            return;
        }

        File[] archivos = folder.listFiles();
        if (archivos == null) return;

        for (File file : archivos) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String categoria = file.getName().replace(".txt", ""); // Nombre del archivo como categoría

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String linea;
                    List<String> bloque = new ArrayList<>();

                    while ((linea = br.readLine()) != null) {
                        bloque.add(linea);
                        if (bloque.size() == 6) { 
                            Pregunta pregunta = new Pregunta(
                                bloque.get(0),
                                Arrays.asList(bloque.get(1), bloque.get(2), bloque.get(3), bloque.get(4)),
                                Integer.parseInt(bloque.get(5)),
                                categoria
                            );

                            if (!preguntasPorCategoria.containsKey(categoria)) {
                                preguntasPorCategoria.put(categoria, new ArrayList<>());
                            }
                            preguntasPorCategoria.get(categoria).add(pregunta);

                            bloque.clear();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void configurarEquipos() {
    	int i = 1;
    	while(true) {
    		System.out.print("Ingrese el nombre del equipo " + i + " ('q' para terminar): ");
            String nombre = scanner.nextLine();
            
            if(nombre.equals("q")) break;
            
            equipos.add(new Equipo(nombre));
            i ++;
    	}
    }

    public static boolean esEntero(String cadena) {
    	boolean result = false;
        try {
            Integer.parseInt(cadena);
            result = true; 
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
    
    public void jugar() {
        boolean hayGanador = false;
        Random random = new Random();

        while (!hayGanador) {
            for (Equipo equipo : equipos) {
                System.out.println("\nTurno de: " + equipo.getNombre());

                // solo se muestra las preguntas que el equipo NO ha obtenido quesitos
                List<String> quesitosDisponibles = new ArrayList<>(preguntasPorCategoria.keySet());
                quesitosDisponibles.removeAll(equipo.quesitos);

                if (quesitosDisponibles.isEmpty()) continue;

                String categoriaElegida = quesitosDisponibles.get(random.nextInt(quesitosDisponibles.size()));

                List<Pregunta> preguntasCategoria = preguntasPorCategoria.get(categoriaElegida);
                if (preguntasCategoria.isEmpty()) continue;

                Pregunta pregunta = preguntasCategoria.get(random.nextInt(preguntasCategoria.size()));

                pregunta.mostrarPregunta();
                
                int intentos = 3;
                int respuesta = -1;

                while (intentos > 0 && !(respuesta >= 1 && respuesta <= 4)) {
                    System.out.print("Ingrese su respuesta (1-4): ");
                    String input = scanner.nextLine();
                    
                    if (esEntero(input)) {
                        respuesta = Integer.parseInt(input);
                    } else {
                    	intentos--;
                        System.out.println("Entrada inválida. Tienes " + intentos + " intentos restantes.");
                    }

                    
                }

                if (intentos == 0) {
                    System.out.println("Se agotaron los intentos. Pasamos al siguiente jugador.");
                }

                if (pregunta.esCorrecta(respuesta)) {
                    System.out.println("¡Correcto! " + equipo.getNombre() + " ganado el quesito " + categoriaElegida);
                    equipo.ganarQuesito(categoriaElegida);
                } else {
                	System.out.println("Incorrecto. No obtiene el quesito.");
                }

                equipo.mostrarQuesitos();

                if (equipo.haGanado()) {
                    Main.title("\n¡FELICIDADES! " + equipo.getNombre() + " ha ganado el juego.");
                    hayGanador = true;
                    break;
                }
            }
        }
    }
}
