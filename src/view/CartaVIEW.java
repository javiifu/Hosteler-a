package view;

import java.util.Scanner;

public class CartaVIEW {
    Scanner sc = new Scanner(System.in);
    CategoriaVIEW categoriaVIEW = new CategoriaVIEW();
    ProductoVIEW productoVIEW = new ProductoVIEW();
    CartaDAO cartaDAO = new CartaDAO();

    //Menu principal de la clase Carta
    public void menu() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con la carta?");
            System.out.println("1. Mostrar carta");
            System.out.println("2. Modificar carta");
            System.out.println("3. Eliminar carta");
            System.out.println("4. Crear carta");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> cartaDAO.mostrarCarta();
                case 2 -> this.modificarCarta();
                case 3 -> cartaDAO.eliminarCarta();
                case 4 -> this.crearCarta();
                case 0 -> System.out.println("Volviendo al menu anterior. ");
                default -> System.out.println("ERR0R: No se reconoció esa opción");
            }
        } while (opcion != 0);
    }

    public void modificarCarta() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con la carta?");
            System.out.println("1. Modificar categoria");
            System.out.println("2. Modificar plato");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { categoriaVIEW.modificarCategoria(); }
                case 2 -> { productoVIEW.modificarProducto(); }
                case 0 -> System.out.println("Volviendo al menu anterior. ");
                default -> System.out.println("ERR0R: No se reconoció esa opción");
            }
        } while (opcion != 0);
    }

    public void crearCarta() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con la carta?");
            System.out.println("1. Crear categoria");
            System.out.println("2. Crear producto");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { categoriaVIEW.crearCategoria(); }
                case 2 -> { productoVIEW.crearProducto(); }
                case 0 -> System.out.println("Volviendo al menu anterior. ");
                default -> System.out.println("ERR0R: No se reconoció esa opción");
            }
        } while (opcion != 0);
    }
}
