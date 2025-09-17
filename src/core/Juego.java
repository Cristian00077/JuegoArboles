package core;

import java.util.*;

public class Juego {

    private ArbolBST arbolGemmas;
    private Random random;

    public Juego() {
        this.arbolGemmas = new ArbolBST();
        this.random = new Random();
    }

    // =======================
    //  MODO 1: Simulación automática (fases fijas)
    // =======================
    public void simularJuego() {
        System.out.println("INICIO DE LA AVENTURA EN EL BOSQUE\n");

        // Evento 1-5: Jugador recoge gemas iniciales
        System.out.println("FASE 1: Recoleccion de Gemas ");
        recolectarGema(50, "Gema del Rio", 10, 15);
        recolectarGema(30, "Gema del Viento", 5, 8);
        recolectarGema(70, "Gema del Fuego", 15, 20);
        recolectarGema(20, "Gema de la Tierra", 3, 12);
        recolectarGema(40, "Gema del Relampago", 8, 18);

        arbolGemmas.inorden();

        // Evento 6-10: Encuentros con jefes
        System.out.println("\nFASE 2: Encuentros con Jefes");
        jefePideGema(25);  // No existe, debe dar sucesor
        jefePideGema(40);  // Existe exactamente
        jefePideGema(60);  // No existe, debe dar sucesor

        // Evento 11-15: Cofres y portales especiales
        System.out.println("\nFASE 3: Cofres y Portales Magicos ");
        abrirCofreMenorPoder();
        abrirPortalMayorPoder();
        recolectarGema(35, "Gema de la Luna", 12, 7);
        recolectarGema(55, "Gema del Sol", 18, 22);

        // Evento 16-20: Trampas y pérdidas
        System.out.println("\nFASE 4: Trampas y Desafios ");
        caerEnTrampa();
        caerEnTrampa();
        jefePideGema(35);  // Existe exactamente

        System.out.println("\nFIN DE LA AVENTURA ");
        System.out.println("Estado final del inventario: ");
        arbolGemmas.inorden();
    }

    // =======================
    //  MODO 2: Juego interactivo (usuario elige)
    // =======================
    public void iniciarJuegoInteractivo() {
        Scanner sc = new Scanner(System.in);
        boolean jugando = true;

        System.out.println("=== INICIO DE LA AVENTURA EN EL BOSQUE ===");

        while (jugando) {
            mostrarMenu();
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1: // Recolectar gema aleatoria
                    int poder = random.nextInt(100) + 1;
                    String nombre = "Gema " + poder;
                    int x = random.nextInt(20);
                    int y = random.nextInt(20);
                    recolectarGema(poder, nombre, x, y);
                    break;

                case 2: // Jefe pide gema
                    System.out.print("Ingrese el poder que pide el jefe: ");
                    int poderJefe = sc.nextInt();
                    jefePideGema(poderJefe);
                    break;

                case 3: // Abrir cofre
                    abrirCofreMenorPoder();
                    break;

                case 4: // Abrir portal
                    abrirPortalMayorPoder();
                    break;

                case 5: // Caer en trampa
                    caerEnTrampa();
                    break;

                case 6: // Ver inventario
                    System.out.println("\nInventario (Inorden): ");
                    arbolGemmas.inorden();
                    break;

                case 0: // Salir
                    jugando = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        System.out.println("\n=== FIN DE LA AVENTURA ===");
        System.out.println("Estado final del inventario: ");
        arbolGemmas.inorden();
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENU DE AVENTURA ---");
        System.out.println("1. Recolectar gema");
        System.out.println("2. Enfrentar a un jefe");
        System.out.println("3. Abrir cofre (minima gema)");
        System.out.println("4. Abrir portal (maxima gema)");
        System.out.println("5. Caer en trampa");
        System.out.println("6. Ver inventario");
        System.out.println("0. Salir del juego");
        System.out.print("Elige una opcion: ");
    }

    // =======================
    //  Métodos de eventos (reusados en ambos modos)
    // =======================

    private void recolectarGema(int poder, String nombre, int x, int y) {
        System.out.println("Encontraste la " + nombre + " (Poder: " + poder + ")");
        arbolGemmas.insertarGema(poder, nombre, x, y);
    }

    private void jefePideGema(int poderRequerido) {
        System.out.println("\nUn jefe aparece, Pide una gema de poder " + poderRequerido);

        Nodo gema = arbolGemmas.buscar(poderRequerido);
        if (gema != null) {
            System.out.println("Tienes la gema exacta, Entregas: " + gema.getNombre());
            arbolGemmas.eliminar(poderRequerido);
        } else {
            System.out.println("No tienes la gema exacta. Buscando alternativa");
            Nodo sucesor = arbolGemmas.sucesor(poderRequerido);
            Nodo predecesor = arbolGemmas.predecesor(poderRequerido);

            if (sucesor != null && predecesor != null) {
                int diffSucesor = sucesor.getPoder() - poderRequerido;
                int diffPredecesor = poderRequerido - predecesor.getPoder();

                if (diffSucesor < diffPredecesor) {
                    System.out.println("Entregas la gema más cercana: " + sucesor.getNombre());
                    arbolGemmas.eliminar(sucesor.getPoder());
                } else {
                    System.out.println("Entregas la gema más cercana: " + predecesor.getNombre());
                    arbolGemmas.eliminar(predecesor.getPoder());
                }
            } else if (sucesor != null) {
                System.out.println("Entregas la gema más cercana: " + sucesor.getNombre());
                arbolGemmas.eliminar(sucesor.getPoder());
            } else if (predecesor != null) {
                System.out.println("Entregas la gema más cercana: " + predecesor.getNombre());
                arbolGemmas.eliminar(predecesor.getPoder());
            } else {
                System.out.println("No tienes ninguna gema que sirva");
            }
        }
    }

    private void abrirCofreMenorPoder() {
        System.out.println("\nEncontraste un cofre antiguo, Requiere la gema de menor poder");
        Nodo minimaGema = arbolGemmas.minimo();
        if (minimaGema != null) {
            System.out.println("Usas: " + minimaGema.getNombre() + " para abrir el cofre");
            arbolGemmas.eliminar(minimaGema.getPoder());
        } else {
            System.out.println("No tienes gemas para abrir el cofre");
        }
    }

    private void abrirPortalMayorPoder() {
        System.out.println("\nUn portal mágico aparece, Requiere la gema de mayor poder");
        Nodo maximaGema = arbolGemmas.maximo();
        if (maximaGema != null) {
            System.out.println("Usas: " + maximaGema.getNombre() + " para activar el portal");
            arbolGemmas.eliminar(maximaGema.getPoder());
        } else {
            System.out.println("No tienes gemas para activar el portal");
        }
    }

    private void caerEnTrampa() {
        System.out.println("\nCaíste en una trampa, Pierdes una gema aleatoria");

        List<Nodo> gemas = new ArrayList<>();
        colectarGemas(arbolGemmas, gemas);

        if (!gemas.isEmpty()) {
            Nodo gemaPerdida = gemas.get(random.nextInt(gemas.size()));
            System.out.println("Perdiste: " + gemaPerdida.getNombre());
            arbolGemmas.eliminar(gemaPerdida.getPoder());
        } else {
            System.out.println("No tienes gemas que perder");
        }
    }

    private void colectarGemas(ArbolBST arbol, List<Nodo> lista) {
        colectarGemasRec(arbol.getRaiz(), lista);
    }

    private void colectarGemasRec(Nodo actual, List<Nodo> lista) {
        if (actual == null) return;
        colectarGemasRec(actual.getIzquierdo(), lista);
        lista.add(actual);
        colectarGemasRec(actual.getDerecho(), lista);
    }
}