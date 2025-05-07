# Gstion de Restaurante

**Gestión Hostelería** es una aplicación de escritorio desarrollada en Java para la gestión integral de un restaurante o bar. Te Permite controlar usuarios, mesas, pedidos, productos, generación de facturas y resúmenes de ventas diarios, todo a través de una **interfaz gráfica** organizada por secciones.


# Interfaz gráfica (TPV):

La interfaz gráfica está desarrollada con **Java Swing** y estructurada en múltiples vistas que se alternan mediante `CardLayout`:

-VistaMesas  Vista principal con las mesas del local.
-VistaMenu  Gestión del menú, productos y pedidos.
-VistaConfiguracion  Ajustes del sistema.

Estas vistas se controlan desde la clase `TPVMain.java`, este es el núcleo visual de nuestra aplicación, que se activará tras el inicio de sesión del usuario.


# Características principales de la aplicación:

- Interfaz gráfica intuitiva para el punto de venta (TPV).
- Inicio de sesión y gestión de usuarios.
- Creación automática de un usuario administrador si no existen usuarios registrados.
- Gestión de productos, categorías, pedidos y sesiones.
- Generación de facturas en HTML por pedido.
- Resumen diario de ventas por categoría y forma de pago.
- Cálculo automático de IVA.
- Configuración personalizada (impresora, impuestos, horarios).
- Conexión a base de datos MySQL.
- Uso de librerías externas como Jackson para manejo de JSON.


# Requisitos:

- Java Development Kit (JDK) 8 o superior.
- Sistema operativo: Windows, macOS o Linux.
- Dependencias externas (incluidas en la carpeta `lib/`):
  - `jackson-annotations-3.0-rc3.jar`.
  - `jackson-core-2.19.0-rc2.jar`.
  - `jackson-databind-2.19.0-rc2.jar`.
  - `mysql-connector-j-9.2.0.jar`.


## Estructura del proyecto:

- `config/` – Configuración general (colores, impuestos, impresora, etc.)
- `dao/` – Acceso a datos (conexiones, consultas a base de datos)
- `model/` – Clases de modelo de datos
- `view/` – Componentes gráficos del sistema (`TPVMain.java`)
- `main/` – Clase principal `App.java`
- `lib/` – Librerías externas necesarias para compilar y ejecutar
- `run.bat` – Script de compilación y ejecución para Windows


#Ejecución:

# Opción 1: Usando el script `run.bat` (solo en Windows)

1. Asegúrate de tener el JDK instalado y agregado al `PATH`.
2. Haz doble clic en `run.bat` o ejecútalo desde la terminal:

'cmd'
run.bat
# Opción 2: Usando el script `run.sh`(para linux o macOS)
1.  Asegúrate de tener el JDK instalado 
2. tienes que otorgar permisos al script `chmod +x run.sh`

./run.sh

ENG

# Restaurant management

**Restaurant management** is a desktop application developed in Java for the comprehensive management of a restaurant or bar. It allows you to control users, tables, orders, products, generate invoices, and daily sales summaries, all through a **graphical interface** organized by sections.

# Graphical interface (POS):

The graphical interface is developed with **Java Swing** and structured into multiple views that alternate using `CardLayout`:

-TableView: Main view with the establishment's tables.
-MenuView: Management of the menu, products, and orders.
-ConfigurationView: System settings.

These views are controlled from the `TPVMain.java` class, which is the visual core of our application and will be activated after the user logs in.

# Main features of the application:

- Intuitive graphical interface for the point of sale (POS).
- Login and user management.
- Automatic creation of an administrator user if no users are registered.
- Management of products, categories, orders, and sessions.
- Generation of HTML invoices per order.
- Daily sales summary by category and payment method.
- Automatic VAT calculation.
- Custom configuration (printer, taxes, schedules).
- Connection to a MySQL database.
- Use of external libraries such as Jackson for JSON processing.

# Requirements:

- Java Development Kit (JDK) 8 or higher.
- Operating system: Windows, macOS, or Linux.
- External dependencies (included in the `lib/` folder):
- `jackson-annotations-3.0-rc3.jar`.
- `jackson-core-2.19.0-rc2.jar`.
- `jackson-databind-2.19.0-rc2.jar`. - `mysql-connector-j-9.2.0.jar`.

## Project structure:

- `config/` – General settings (colors, taxes, printer, etc.)
- `dao/` – Data access (connections, database queries)
- `model/` – Data model classes
- `view/` – Graphical system components (`TPVMain.java`)
- `main/` – Main class `App.java`
- `lib/` – External libraries needed to compile and run
- `run.bat` – Compile and run script for Windows

#Execution:

# Option 1: Using the `run.bat` script (Windows only)

1. Make sure you have the JDK installed and added to your `PATH`.

2. Double-click `run.bat` or run it from the terminal:

'cmd'
run.bat
# Option 2: Using the `run.sh` script (for Linux or macOS)
1. Make sure you have the JDK installed
2. You need to grant permissions to the script `chmod +x run.sh`

./run.sh

