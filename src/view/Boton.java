package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Boton extends JButton {

    private Color originalBackgroundColor;
    private Color hoverBackgroundColor;

    // Constructor que recibe el texto, el color de fondo y el color de hover
    public Boton(String text, Color backgroundColor, Color hoverColor) {
        super(text);

        // Establecer colores personalizados
        setBackground(backgroundColor);
        setForeground(Color.WHITE); // Puedes ajustar el color del texto según sea necesario

        // Guardar los colores originales y de hover
        originalBackgroundColor = backgroundColor;
        hoverBackgroundColor = hoverColor;

        // Añadir el MouseListener para el efecto hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor); // Cambiar al color de hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackgroundColor); // Restaurar el color original
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