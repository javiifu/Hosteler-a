package view;

import java.util.Scanner;

import model.Categoria;

public class CategoriaVIEW {
    Scanner sc = new Scanner(System.in);

    //Menu principal de la clase Categoria
    public void menu() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con las categorias?");
            System.out.println("1. Crear Categoria");
            System.out.println("2. Modificar Categoria");
            System.out.println("3. Eliminar Categoria");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.crearCategoria(); }
                case 2 -> { this.modificarCategoria(); }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} 
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }

        } while (opcion != 0);
}

    public void crearCategoria() {
        System.out.println("Registrar Categoria");
        System.out.print("Nombre de la categoria: ");
        String nombre = sc.next();

        Categoria categoria = new Categoria(nombre);
        // categoriaDAO.registrarCategoria(categoria);
    }

    public void modificarCategoria() {
        System.out.println("Modificar Categoria");
        System.out.println("Introduce el nombre de la categoria a modificar: ");
        String nombre = sc.next();
        System.out.println("Introduce el nuevo nombre de la categoria: ");
        String nuevoNombre = sc.next();
        // categoriaDAO.modificarCategoria(nombre, nuevoNombre);
    }

    public void eliminarCategoria() {
        System.out.println("Eliminar Categoria");
        System.out.println("Introduce el nombre de la categoria a eliminar: ");
        String nombre = sc.next();

        // categoriaDAO.eliminarCategoria(nombre);
    }
}

