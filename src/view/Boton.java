package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import config.ColorPaleta;

public class Boton extends JButton {

    private Color originalBackgroundColor;
    private Color hoverBackgroundColor;

    public Boton(String text) {
        super(text);
        // Establecer colores por defecto (puedes ajustarlos)
        setBackground(ColorPaleta.BOTON_SECUNDARIO_FONDO);
        setForeground(ColorPaleta.BOTON_SECUNDARIO_TEXTO);

        // Guardar el color original y definir el color de hover
        originalBackgroundColor = getBackground();
        hoverBackgroundColor = ColorPaleta.HOVER_SECUNDARIO; // Color de hover por defecto

        // Añadir el MouseListener para el efecto hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackgroundColor);
            }
        });
    }

    // Método para cambiar el color de hover si es necesario
    public void setHoverBackgroundColor(Color hoverColor) {
        this.hoverBackgroundColor = hoverColor;
    }

    // Método para cambiar el color original si es necesario
    public void setOriginalBackgroundColor(Color originalColor) {
        this.originalBackgroundColor = originalColor;
        setBackground(originalColor);
    }
}