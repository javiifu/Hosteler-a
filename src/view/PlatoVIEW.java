package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.Plato;

public class PlatoVIEW {
    public ArrayList<Plato> array_platos = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    //Menu principal de la clase Plato
    public void menu() {

        int opcion;
        
        do { 
            System.out.println("Qué desea hacer con los platos?");
            System.out.println("1. Registrar Plato");
            System.out.println("2. Modificar Plato");
            System.out.println("3. Eliminar Plato");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();
            
    
            switch (opcion) {
                case 1 -> {  }
                case 2 -> {  }
                case 3 -> {  }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} //Vuelve al menu anterior
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }

        } while (opcion != 0);
    }
}
