package model;

import java.util.Collection;
import java.util.List;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.GameStatusEnum.*;

public class Board {
    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual()))? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if (getStatus()==NON_STARTED) {
            return false;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));

    }

    public boolean changeValue(final int row, final int col,final Integer value){
        Space space = spaces.get(row).get(col);
        if (value == null || value < 1 || value > 9) {
            return false;
        }
        if (space.isFixed()) {
            return false;
        }
        space.setActual(value);
        return true;

    }

    public boolean clearValue(final int row, final int col){
        Space space = spaces.get(row).get(col);
        if (space.isFixed() || isNull(space.getActual())) {
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(r -> r.forEach(space -> {
            if (!space.isFixed()) {
                space.clearSpace();
            }
        }));
    }

    public boolean gameIsFinished() {
        return COMPLETE.equals(getStatus()) && !hasErrors();
    }
}
