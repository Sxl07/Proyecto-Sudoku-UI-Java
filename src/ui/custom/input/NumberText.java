package ui.custom.input;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

public class NumberText extends JTextField {

    public NumberText(final int minValue, final int maxValue, final int maxLength) {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Segoe UI", Font.BOLD, 20));
        setForeground(new Color(35, 35, 35));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(198, 206, 221)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        ((AbstractDocument) getDocument()).setDocumentFilter(new NumberTextLimit(minValue, maxValue, maxLength));
    }
}
