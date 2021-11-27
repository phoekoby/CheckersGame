package ru.vsu.css.nechenko_d_a.model;

import java.util.*;

public class Game {
    private Cell rightUpCell;
    private Cell leftDownCell;
    private List<List<Cell>> boardForPainting;
    //all players settings
    private Queue<Player> players;

    private final Map<Player, Map<TypeOfFigure, String>> visualFigure;
    private Map<Player, List<Figure>> playerFigures;
    private Map<Figure, Player> figurePlayerMap;
    private Map<Player, List<Direction>> availableDirections;

    //actually for figures
    private List<Cell> cells;
    private Map<Cell, Figure> cellFigure;
    private Map<Figure, Cell> figureCell;
    private Map<TypeOfFigure, List<Direction>> figuresDirections;


    private Game() {
        players = new LinkedList<>();
        visualFigure = new HashMap<>();
    }

    public Cell getRightUpCell() {
        return rightUpCell;
    }

    public void setRightUpCell(Cell leftUpCell) {
        this.rightUpCell = leftUpCell;
    }

    public Cell getLeftDownCell() {
        return leftDownCell;
    }

    public void setLeftDownCell(Cell leftDownCell) {
        this.leftDownCell = leftDownCell;
    }

    public List<List<Cell>> getBoardForPainting() {
        return boardForPainting;
    }

    public void setBoardForPainting(List<List<Cell>> boardForPainting) {
        this.boardForPainting = boardForPainting;
    }

    public Map<Player, Map<TypeOfFigure, String>> getVisualFigure() {
        return visualFigure;
    }

    public Map<Player, List<Figure>> getPlayerFigures() {
        return playerFigures;
    }

    public Map<Figure, Player> getFigurePlayerMap() {
        return figurePlayerMap;
    }

    public void setFigurePlayerMap(Map<Figure, Player> figurePlayerMap) {
        this.figurePlayerMap = figurePlayerMap;
    }

    public void setPlayerFigures(Map<Player, List<Figure>> playerFigures) {
        this.playerFigures = playerFigures;
    }

    public Map<Player, List<Direction>> getAvailableDirections() {
        return availableDirections;
    }

    public void setAvailableDirections(Map<Player, List<Direction>> availableDirections) {
        this.availableDirections = availableDirections;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Map<Cell, Figure> getCellFigure() {
        return cellFigure;
    }

    public void setCellFigure(Map<Cell, Figure> cellFigure) {
        this.cellFigure = cellFigure;
    }

    public Map<Figure, Cell> getFigureCell() {
        return figureCell;
    }

    public void setFigureCell(Map<Figure, Cell> figureCell) {
        this.figureCell = figureCell;
    }

//    public Map<Figure, Cell> getBasicFigureCellPosition() {
//        return basicFigureCellPosition;
//    }
//
//    public void setBasicFigureCellPosition(Map<Figure, Cell> basicFigureCellPosition) {
//        this.basicFigureCellPosition = basicFigureCellPosition;
//    }
//
//    public Map<Cell, Figure> getBasicCellFigurePosition() {
//        return basicCellFigurePosition;
//    }
//
//    public void setBasicCellFigurePosition(Map<Cell, Figure> basicCellFigurePosition) {
//        this.basicCellFigurePosition = basicCellFigurePosition;
//    }

    public Map<TypeOfFigure, List<Direction>> getFiguresDirections() {
        return figuresDirections;
    }

    public void setFiguresDirections(Map<TypeOfFigure, List<Direction>> figuresDirections) {
        this.figuresDirections = figuresDirections;
    }


    public Map<Cell, Figure> getMapCellFigure() {
        return mapCellFigure;
    }

    public void setMapCellFigure(Map<Cell, Figure> mapCellFigure) {
        this.mapCellFigure = mapCellFigure;
    }

    private Map<Player, List<Figure>> playerCheckersMap;

    private Map<Player, List<Direction>> playerDirectionsMap;

    private Map<Figure, Cell> mapFigureCell;

    private Map<Cell, Figure> mapCellFigure;

    public Queue<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    public Map<Player, List<Figure>> getPlayerCheckersMap() {
        return playerCheckersMap;
    }

    public void setPlayerCheckersMap(Map<Player, List<Figure>> playerCheckersMap) {
        this.playerCheckersMap = playerCheckersMap;
    }

    public Map<Player, List<Direction>> getPlayerDirectionsMap() {
        return playerDirectionsMap;
    }

    public void setPlayerDirectionsMap(Map<Player, List<Direction>> playerDirectionsMap) {
        this.playerDirectionsMap = playerDirectionsMap;
    }

    public Map<Figure, Cell> getMapFigureCell() {
        return mapFigureCell;
    }

    public void setMapFigureCell(Map<Figure, Cell> mapFigureCell) {
        this.mapFigureCell = mapFigureCell;
    }


    public static Game createGame() {
        return new Game();
    }
}
