package ru.vsu.css.nechenko_d_a.model;

public class Step {

    private Cell from;

    private Cell to;

    private Figure figure;

    private Player player;

    private Figure beatenFigure = null;

    public Step(Cell from, Cell to, Figure figure, Player player) {
        this.from = from;
        this.to = to;
        this.figure = figure;
        this.player = player;
    }
    public Step(Cell from, Cell to, Figure figure, Player player, Figure beatenFigure) {
        this.from = from;
        this.to = to;
        this.figure = figure;
        this.player = player;
        this.beatenFigure=beatenFigure;
    }

    public Cell getFrom() {
        return from;
    }

    public void setFrom(Cell from) {
        this.from = from;
    }

    public Cell getTo() {
        return to;
    }

    public void setTo(Cell to) {
        this.to = to;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Figure getBeatenFigure() {
        return beatenFigure;
    }

    public void setBeatenFigure(Figure beatenFigure) {
        this.beatenFigure = beatenFigure;
    }
}
