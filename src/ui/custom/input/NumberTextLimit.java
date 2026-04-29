package ui.custom.input;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberTextLimit extends DocumentFilter {
    private final int minValue;
    private final int maxValue;
    private final int maxLength;

    public NumberTextLimit(final int minValue, final int maxValue, final int maxLength) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) {
            return;
        }

        final String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        final StringBuilder builder = new StringBuilder(currentText);
        builder.replace(offset, offset + length, text);

        final String candidate = builder.toString().trim();
        if (candidate.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        if (candidate.length() > maxLength || !candidate.matches("\\d+")) {
            return;
        }

        final int value = Integer.parseInt(candidate);
        if (value < minValue || value > maxValue) {
            return;
        }

        super.replace(fb, offset, length, text, attrs);
    }
}
