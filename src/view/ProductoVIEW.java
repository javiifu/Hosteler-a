package view;

import java.util.Scanner;

import model.Producto;

public class ProductoVIEW {
    Scanner sc = new Scanner(System.in);

    //Menu principal de la clase Producto
    public void menu() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con los productos?");
            System.out.println("1. Crear Producto");
            System.out.println("2. Modificar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Mostrar Productos");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.crearProducto(); }
                case 2 -> { this.modificarProducto(); }
                case 3 -> { this.eliminarProducto(); }
                case 4 -> { this.mostrarProductos(); }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");} 
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }
        } while (opcion != 0);
    }

    public void crearProducto() {
        System.out.println("Registrar Producto");
        System.out.print("Nombre del producto: ");
        String nombre = sc.next();
        System.out.print("Descripción del producto: ");
        String descripcion = sc.next();
        System.out.print("Precio del producto: ");
        double precio = sc.nextDouble();
        boolean disponible = false;


        Producto producto = new Producto(nombre, descripcion, precio, disponible);
        // productoDAO.registrarProducto(producto);
    }

    public Producto buscarProducto() {
        System.out.print("Código del producto: ");
        int codigo = sc.nextInt();
        Producto producto = productoDAO.buscarProducto(codigo);
        return producto;
    }
    public void modificarProducto() {
        int opcion;

        do {
            System.out.println("Qué desea hacer con los productos?");
            System.out.println("1. Modificar nombre");
            System.out.println("2. Modificar descripción");
            System.out.println("3. Modificar precio");
            System.out.println("4. Modificar disponibilidad");
            System.out.println("5. Modificar categoría");
            System.out.println("0. Atrás");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> { this.modificarNombreProducto(); }
                case 2 -> { this.modificarDescripcionProducto(); }
                case 3 -> { this.modificarPrecioProducto(); }
                case 4 -> { this.modificarDisponibilidadProducto(); }
                case 5 -> { this.modificarCategoriaProducto(); }
                case 0 -> { System.out.println("Volviendo al menu anterior. ");}
                default -> { System.out.println("ERR0R: No se reconoció esa opción"); }
            }
        } while (opcion != 0);
    }

    public void modificarNombreProducto() {
        Producto producto = this.buscarProducto();
        System.out.print("Nuevo nombre del producto: ");
        String nombre = sc.nextLine();
        sc.nextLine(); 

        producto.setNombre(nombre);
        // productoDAO.modificarNombreProducto(codigo, nombre);
    }

    public void modificarDescripcionProducto() {
        Producto producto = this.buscarProducto();
        System.out.print("Nueva descripción del producto: ");
        String descripcion = sc.nextLine();
        sc.nextLine();

        producto.setDescripcion(descripcion);
        // productoDAO.modificarDescripcionProducto(codigo, descripcion);
    }

    public void modificarPrecioProducto() {
        double precio;
        Producto producto = this.buscarProducto();


        do {
            System.out.print("Nuevo precio del producto: ");
            precio = sc.nextDouble();
            if (precio < 0) {
                System.out.println("El precio no puede ser negativo. Intente nuevamente.");
            }
        } while (precio < 0);
        producto.setPrecio(precio);
        // productoDAO.modificarPrecioProducto(codigo, precio);
    }

    public void modificarDisponibilidadProducto() {
        Producto producto = this.buscarProducto();
        System.out.println("Disponibilidad actual del producto: " + producto.isDisponible());

        int opcion;
        boolean cambiarDisponibilidad = false;

        do {
            System.out.println("¿Quieres cambiar la disponibilidad?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            System.out.print(">>> ");
            opcion = sc.nextInt();

            if (opcion == 1) {
            cambiarDisponibilidad = true;
            } else if (opcion == 2) {
            cambiarDisponibilidad = false;
            } else {
            System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 1 && opcion != 2);

        if (cambiarDisponibilidad) {
            System.out.println("Cambiando la disponibilidad del producto...");
            producto.cambiarEstadoProducto();
            System.out.println("Disponibilidad cambiada a: " + producto.isDisponible());

            productoDAO.modificarDisponibilidadProducto(producto.getCodigo(), producto.isDisponible());
        } else {
            System.out.println("No se cambió la disponibilidad del producto.");
        }
    }

    public void modificarCategoriaProducto() {
        Producto producto = this.buscarProducto();
        int codigo = producto.getCategoriaCodigo();

        System.out.print("Nuevo código de categoría del producto: ");
        int categoriaCodigo = sc.nextInt();

        
        // productoDAO.modificarCategoriaProducto(codigo, categoriaCodigo);
    }
}
