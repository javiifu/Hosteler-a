# Gestión Hostelería

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

### Opción 1: Usando el script `run.bat` (solo en Windows)

1. Asegúrate de tener el JDK instalado y agregado al `PATH`.
2. Haz doble clic en `run.bat` o ejecútalo desde la terminal:

'cmd'
run.bat



