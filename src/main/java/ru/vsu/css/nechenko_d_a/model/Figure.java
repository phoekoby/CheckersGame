package ru.vsu.css.nechenko_d_a.model;

public class Figure {
    private TypeOfFigure type;

    public Figure(TypeOfFigure type) {
        this.type = type;
    }

    public TypeOfFigure getType() {
        return type;
    }

    public void setType(TypeOfFigure type) {
        this.type = type;
    }
}
