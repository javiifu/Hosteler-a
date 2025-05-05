package view;

import java.util.Scanner;

import model.Mesa;
import dao.MesaDAO;

public class MesaVIEW {
    Scanner sc = new Scanner(System.in);
    MesaDAO mesaDAO = new MesaDAO();

    //Menu principal de la clase Mesa
    public void menu() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con las mesas?");
            System.out.println("1. Crear mesa");
            System.out.println("2. Modificar mesa");
            System.out.println("3. Eliminar mesa");
            System.out.println("4. Mostrar mesas");
            System.out.println("5. Juntar mesas");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.crearMesa(); }
                case 2 -> { this.modificarMesa(); }
                case 3 -> { this.eliminarMesa(); }
                case 4 -> { this.mostrarMesas(); }
                case 5 -> { this.juntarMesas(); }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} 
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }
        } while (opcion != 0);
    }

    public Mesa buscarMesa() {
        System.out.print("Código de la mesa: ");
        String codigo = sc.next();
        Mesa mesa = mesaDAO.buscarMesa(codigo);
        return mesa;
    }

    public void crearMesa() {
        System.out.println("Registrar mesa");
        System.out.print("Capacidad de la mesa: ");
        int capacidad = sc.nextInt();
        Mesa mesa = new Mesa(capacidad);
        mesaDAO.registrarMesa(mesa);
    }

    public void modificarMesa() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con las mesas?");
            System.out.println("1. Cambiar capacidad de la mesa");
            System.out.println("2. Cambiar estado de la mesa");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.cambiarCapacidadMesa(); }
                case 2 -> { this.cambiarEstadoMesa(); }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} 
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }
        } while (opcion != 0);
    }

    public void cambiarCapacidadMesa() {
        Mesa mesa = this.buscarMesa();
        System.out.print("Nueva capacidad de la mesa: ");
        int capacidad = sc.nextInt();
        mesa.setCapacidad(capacidad);
        mesaDAO.modificarMesa(mesa);
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
        Mesa mesa = this.buscarMesa();
        mesaDAO.eliminarMesa(mesa.getCodigo());
    }

    public void mostrarMesas() {
        mesaDAO.mostrarMesas();
    }

    public void juntarMesas() {
        System.out.println("Juntar mesas");
        System.out.print("Código de la primera mesa: ");
        String codigo1 = sc.next();
        System.out.print("Código de la segunda mesa: ");
        String codigo2 = sc.next();

        mesaDAO.juntarMesas(codigo1, codigo2);

        
    }

}
