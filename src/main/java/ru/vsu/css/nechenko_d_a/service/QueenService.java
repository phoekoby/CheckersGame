package ru.vsu.css.nechenko_d_a.service;


import ru.vsu.css.nechenko_d_a.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QueenService {
    private final FigureService figureService;

    public QueenService(FigureService figureService) {
        this.figureService = figureService;
    }

    /*
    Возможные ходы для дамок
     */
    public List<Step> getAvailableSteps(Game game, Player player) {
        List<Figure> availableFigures = figureService.availableFigures(game, player, TypeOfFigure.QUEEN);
        if (availableFigures.isEmpty()) {
            return Collections.emptyList();
        }
        List<Step> steps = new ArrayList<>();
        availableFigures.forEach(figure -> {
            Cell cell = game.getFigureCell().get(figure);
            Cell to = game.getFigureCell().get(figure);
            for (Direction direction : Direction.values()) {
                while (figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, to)) {
                    to = to.getNeighbours().get(direction);
                    steps.add(new Step(cell, to, figure, player));
                }
            }
        });
        return steps;
    }

    /*
    проверка на наличие у дамки ходов, по которым она может сходить
     */
    protected boolean haveAvailableMoves(Figure figure, Game game) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : Direction.values()) {
            if (figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, cell)) {
                return true;
            }
        }
        return false;
    }

    /*
    может ли дамка побить какую-то фигуру
     */
    protected Direction canIBeat(Figure figure, Player player, Game game) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : cell.getNeighbours().keySet()) {
            while (cell.getNeighbours().containsKey(direction)
                    && !game.getCellFigure().containsKey(cell.getNeighbours().get(direction))) {
                cell = cell.getNeighbours().get(direction);
            }
            Cell nextCell = cell.getNeighbours().get(direction);
            if (game.getCellFigure().containsKey(nextCell) &&
                    game.getFigurePlayerMap().get(game.getCellFigure().get(nextCell)) != player &&
                    figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, nextCell)) {
                return direction;
            }
        }
        return null;
    }

    /*
    ходы, которые можно сделать, побив фигуру противника
     */
    private List<Step> stepWhereCanDoBeat(Figure figure, Player player, Game game, Direction direction) {
        List<Step> steps = new ArrayList<>();
        Cell from = game.getFigureCell().get(figure);
        Cell cell = from;
        while (figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, cell)) {
            cell = cell.getNeighbours().get(direction);
        }
        if (cell.getNeighbours().containsKey(direction)) {
            Cell to = cell.getNeighbours().get(direction);
            while (figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, to)) {
                to = to.getNeighbours().get(direction);
                steps.add(new Step(from, to, figure, player, game.getCellFigure().get(cell.getNeighbours().get(direction))));
            }
        }
        return steps;
    }

    /*
    Обязательные ходы для дамки
     */
    public List<Step> getNecessarySteps(Game game, Player player) {
        List<Figure> figures = figureService.listOfFigureWithNecessaryMoves(game, player, TypeOfFigure.QUEEN);
        List<Step> necessarySteps = new ArrayList<>();
        for (Figure figure : figures) {
            necessarySteps.addAll(stepWhereCanDoBeat(figure, player, game, canIBeat(figure, player, game)));
        }
        return necessarySteps;
    }

}
