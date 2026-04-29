package model;

public class Space {
    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Space(int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed) {
            this.actual = expected;
        } else {
            this.actual = null;
        }
    }

    public Integer getActual() {
        return actual;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void clearSpace() {
        setActual(null);
    }

    public void setActual(final Integer actual) {
        if (fixed) {
            return;
        }
        this.actual = actual;
    }
}
