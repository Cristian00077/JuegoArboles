
package core;

public class ArbolBST {
    Nodo raiz;
    public ArbolBST() {
        raiz = null;
    }
    // Insertar gema
    public void insertarGema(int poder, String nombre, int x, int y) {
        raiz = insertarGemaRec(raiz, new Nodo(poder, nombre, x, y));
    }
    private Nodo insertarGemaRec(Nodo raiz, Nodo nuevo) {
        if (raiz == null) {
            return nuevo;
        }
        if (nuevo.getPoder() < raiz.getPoder()) {
            raiz.setIzquierdo(insertarGemaRec(raiz.getIzquierdo(), nuevo));
        } else if (nuevo.getPoder() > raiz.getPoder()) {
            raiz.setDerecho(insertarGemaRec(raiz.getDerecho(), nuevo));
        }
        return raiz;
    }
    
    // Buscar gema
    public Nodo buscar(int poder) {
        return buscarRec(raiz, poder);
    }
    private Nodo buscarRec(Nodo raiz, int poder) {
        if (raiz == null || raiz.getPoder() == poder) {
            return raiz;
        }
        if (poder < raiz.getPoder()) {
            return buscarRec(raiz.getIzquierdo(), poder);
        } else {
            return buscarRec(raiz.getDerecho(), poder);
        }
    }
    // Eliminar gema
    public void eliminar(int poder) {
        raiz = eliminarRec(raiz, poder);
    }
    private Nodo eliminarRec(Nodo raiz, int poder) {
       if (raiz == null) return raiz;
        if (poder < raiz.getPoder()) {
            raiz.setIzquierdo(eliminarRec(raiz.getIzquierdo(), poder));
        } else if (poder > raiz.getPoder()) {
            raiz.setDerecho(eliminarRec(raiz.getDerecho(), poder));
        } else {
            // Nodo con un hijo o sin hijos
            if (raiz.getIzquierdo() == null) return raiz.getDerecho();
            else if (raiz.getDerecho() == null) return raiz.getIzquierdo();
            // Nodo con dos hijos: obtener sucesor (mínimo en subárbol derecho)
            Nodo sucesor = minimoNodo(raiz.getDerecho());
            raiz.setPoder(sucesor.getPoder());
            raiz.setNombre(sucesor.getNombre());
            raiz.setCoordenadaX(sucesor.getCoordenadaX());
            raiz.setCoordenadaY(sucesor.getCoordenadaY());
            raiz.setDerecho(eliminarRec(raiz.getDerecho(), sucesor.getPoder()));
        }
        return raiz;
    }
    
    public void inorden() {
        inordenRec(raiz);
    }
    private void inordenRec(Nodo raiz) {
        if (raiz != null) {
            inordenRec(raiz.getIzquierdo());
            System.out.println("Poder: " + raiz.getPoder() + ", Nombre: " + raiz.getNombre());
            inordenRec(raiz.getDerecho());
        }
    }
    
    public void preorden() {
        preordenRec(raiz);
    }
    private void preordenRec(Nodo raiz) {
        if (raiz != null) {
            System.out.println("Poder: " + raiz.getPoder() + ", Nombre: " + raiz.getNombre());
            inordenRec(raiz.getIzquierdo());
            inordenRec(raiz.getDerecho());
        }
    }
    
    public void posorden() {
        posordenRec(raiz);
    }
    private void posordenRec(Nodo raiz) {
        if (raiz != null) {
            inordenRec(raiz.getIzquierdo());
            inordenRec(raiz.getDerecho());
            System.out.println("Poder: " + raiz.getPoder() + ", Nombre: " + raiz.getNombre());
        }
    }
    
    // Encontrar mínimo
    public Nodo minimoNodo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;
    }
    public Nodo minimo() {
        return minimoNodo(raiz);
    }
    
    // Encontrar máximo
    public Nodo maximo() {
        Nodo actual = raiz;
        while (actual.getDerecho() != null) {
            actual = actual.getDerecho();
        }
        return actual;
    }
    
    // Sucesor
    public Nodo sucesor(int poder) {
        Nodo current = buscar(poder);
        if (current == null) return null;
        if (current.getDerecho() != null) {
            return minimoNodo(current.getDerecho());
        } else {
            Nodo sucesor = null;
            Nodo ancestro = raiz;
            while (ancestro != current) {
                if (current.getPoder() < ancestro.getPoder()) {
                    sucesor = ancestro;
                    ancestro = ancestro.getIzquierdo();
                } else {
                    ancestro = ancestro.getDerecho();
                }
            }
            return sucesor;
        }
    }
    
    
    // Predecesor (similar a sucesor, pero hacia la izquierda)
    public Nodo predecesor(int poder) {
        Nodo actual = buscar(poder);
        if (actual == null) return null;
        if (actual.getIzquierdo() != null) {
            Nodo temporal = actual.getIzquierdo();
            while (temporal.getDerecho() != null) {
                temporal = temporal.getDerecho();
            }
            return temporal;
        } else {
            Nodo predecesor = null;
            Nodo ancestro = raiz;
            while (ancestro != actual) {
                if (actual.getPoder() > ancestro.getPoder()) {
                    predecesor = ancestro;
                    ancestro = ancestro.getDerecho();
                } else {
                    ancestro = ancestro.getIzquierdo();
                }
            }
            return predecesor;
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }
    
    
}
