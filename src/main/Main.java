package main;

import core.ArbolBST;
import core.Juego;
import core.Nodo;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Juego juego = new Juego();
        Scanner leer = new Scanner(System.in);
        int n = 0;
        while (n != 3) {
            System.out.println("");
            System.out.println("Seleccione la opcion que desee");
            System.out.println("1. Juego Simulado");
            System.out.println("2. Juego Interactivo");
            System.out.println("3. Salir");
            n = leer.nextInt();

            if (n == 1) {
                juego.simularJuego();
            } else if (n == 2) {
                juego.iniciarJuegoInteractivo();
            } else {
                break;
            }
        }
    }

}
