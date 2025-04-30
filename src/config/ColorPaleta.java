package config;

import java.awt.Color;

public class ColorPaleta {
    public static final Color PRIMARIO = new Color(0xFFD700);      // Amarillo brillante
    public static final Color SECUNDARIO = new Color(0x007BFF);   // Azul brillante
    public static final Color ACENTO = new Color(0xFF4500);       // Naranja rojizo
    public static final Color FONDO_PRINCIPAL = new Color(0x222222); // Gris oscuro (fondo principal)
    public static final Color FONDO_SECUNDARIO = new Color(0xF8F9FA); // Gris muy claro (para áreas de contenido)
    public static final Color TEXTO_PRINCIPAL_OSCURO = new Color(0xFFFFFF); // Blanco (sobre fondos oscuros)
    public static final Color TEXTO_SECUNDARIO_OSCURO = new Color(0xAAAAAA); // Gris claro (sobre fondos oscuros)
    public static final Color TEXTO_PRINCIPAL_CLARO = new Color(0x333333);  // Gris oscuro (sobre fondos claros)
    public static final Color TEXTO_SECUNDARIO_CLARO = new Color(0x777777);  // Gris más claro (sobre fondos claros)
    public static final Color ENFASIS_ACCION = new Color(0x00FF7F);    // Verde lima brillante
    public static final Color HOVER_PRIMARIO = new Color(0x0056b3);    // Azul ligeramente más oscuro (hover)
    public static final Color HOVER_SECUNDARIO = new Color(0x5a6268);  // Gris ligeramente más claro (hover)
    public static final Color HOVER_ACENTO = new Color(0xcc3700);      // Rojo ligeramente más oscuro (hover)
    public static final Color SELECCION = new Color(0xADD8E6);       // Azul claro (para selección en listas)

    // Colores específicos para componentes (siguiendo las sugerencias)
    public static final Color BOTON_PRIMARIO_FONDO = SECUNDARIO;
    public static final Color BOTON_PRIMARIO_TEXTO = TEXTO_PRINCIPAL_OSCURO;
    public static final Color BOTON_SECUNDARIO_FONDO = new Color(0x6C757D);
    public static final Color BOTON_SECUNDARIO_TEXTO = TEXTO_PRINCIPAL_OSCURO;
    public static final Color BOTON_ACENTO_FONDO = ACENTO;
    public static final Color BOTON_ACENTO_TEXTO = TEXTO_PRINCIPAL_OSCURO;

    public static final Color TEXTAREA_FONDO = FONDO_SECUNDARIO;
    public static final Color TEXTAREA_TEXTO = TEXTO_PRINCIPAL_CLARO;
    public static final Color TEXTAREA_BORDE = new Color(0xCED4DA);

    public static final Color LISTA_FONDO = new Color(0xFFFFFF);
    public static final Color LISTA_TEXTO = TEXTO_PRINCIPAL_CLARO;
    public static final Color LISTA_SELECCION_FONDO = SELECCION;
    public static final Color LISTA_SELECCION_TEXTO = TEXTO_PRINCIPAL_CLARO; // Asumiendo texto oscuro para selección clara
}
