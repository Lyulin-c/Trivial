package io.keepcoding.keeptrivial;

public class Main {
	
	 public static void main(String[] args) {
	        Juego juego = new Juego();
	        juego.configurarEquipos();
	        juego.jugar();
	    }

	    public static void title(String text) {
	        int length = text.length();
	        printHashtagLine(length + 4);
	        System.out.println("# " + text + " #");
	        printHashtagLine(length + 4);
	    }

	    public static void printHashtagLine(int length) {
	    	for (int i = 0; i < length; i++) {
	            System.out.print("#");
	        }
	        System.out.println();
	    }
}
