package view;

import dao.MesaDAO;

import java.util.ArrayList;
import java.util.Scanner;
import model.Mesa;

public class MesaVIEW {
    Scanner sc = new Scanner(System.in);
    MesaDAO mesaDAO = new MesaDAO();

    //Menu principal de la clase Mesa
    public void menu() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con las mesas?");
            System.out.println("1. Crear mesa");
            System.out.println("2. Modificar estado mesa");
            System.out.println("3. Eliminar mesa");
            System.out.println("4. Mostrar mesas");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.crearMesa(); }
                case 2 -> { this.cambiarEstadoMesa(); }
                case 3 -> { this.eliminarMesa(); }
                case 4 -> { this.mostrarMesas();}
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} 
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }
        } while (opcion != 0);
    }

    public Mesa buscarMesa() {
        System.out.print("Código de la mesa: ");
        int numero = sc.nextInt();
        Mesa mesa = mesaDAO.buscarMesa(numero);
        return mesa;
    }

    public void crearMesa() {
        System.out.println("Registrar mesa");
        System.out.println("Intoduce numero de mesa");
        
        int numero = sc.nextInt();
        Mesa mesa = new Mesa(numero);
        mesaDAO.newMesa(mesa);
    }

    public void cambiarEstadoMesa() {
        Mesa mesa = this.buscarMesa();
        System.out.println("Estado actual de la mesa: " + (mesa.getEstado() ? "Disponible" : "Ocupada"));

        int opcion;
        boolean cambiarEstado = false;

        do {
            System.out.println("¿Desea cambiar el estado de la mesa?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            if (opcion == 1) {
                cambiarEstado = true;
            } else if (opcion == 2) {
                cambiarEstado = false;
            } else {
                System.out.println("ERR0R: No se reconoció esa opción");
            }
        } while (opcion != 1 && opcion != 2);

        if (cambiarEstado) {
            System.out.println("Cambiado el estado de la mesa...");
            mesa.cambiarEstadoMesa();
            System.out.println("Estado cambiado a: " + (mesa.getEstado() ? "Disponible" : "Ocupada"));
            
            mesaDAO.modificarEstadoMesa(mesa.getCodigo(), mesa.getEstado());
        } else {
            System.out.println("No se ha cambiado el estado de la mesa.");
        }
    }

    public void eliminarMesa() {
        System.out.println("Introduce el numero de mesa que quieres eliminar");
        Mesa mesa = this.buscarMesa();
        mesaDAO.deleteMesa(mesa.getCodigo());
    }

    //pendiente de haer
    public void mostrarMesas() {
        ArrayList<Mesa> mesas = new ArrayList<>();
        mesaDAO.getMesas();
        System.out.println("Las mesas son las siguientes: ");
        for (int i = 0; i < mesas.size(); i++) {
            System.out.println(mesas.get(i));
        }
    }


}
