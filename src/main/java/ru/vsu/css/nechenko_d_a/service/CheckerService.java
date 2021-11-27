package ru.vsu.css.nechenko_d_a.service;


import ru.vsu.css.nechenko_d_a.model.*;

import java.util.ArrayList;
import java.util.List;

public class CheckerService {
    private final FigureService figureService;

    public CheckerService(FigureService figureService) {
        this.figureService = figureService;
    }

    //Получение возможных шагов для всех шашек игрока
    public List<Step> getAvailableSteps(Game game, Player player) {
        List<Figure> availableFigures = figureService.availableFigures(game, player, TypeOfFigure.CHECKER);
        List<Step> steps = new ArrayList<>();
        availableFigures.forEach(figure -> {
            if (figure.getType() == TypeOfFigure.CHECKER) {
                Cell cell = game.getFigureCell().get(figure);
                for (Direction direction : game.getAvailableDirections().get(player)) {
                    if (!game.getCellFigure().containsKey(game.getFigureCell().get(figure).getNeighbours().get(direction))
                            && cell.getNeighbours().containsKey(direction)) {
                        steps.add(new Step(cell, game.getFigureCell().get(figure).getNeighbours().get(direction), figure, player));
                    }
                }
            }
        });
        return steps;
    }

    /*
   Проверка на наличие у шашки возможных ходов
    */
    protected boolean haveAvailableMoves(Figure figure, Game game, Player player) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : game.getAvailableDirections().get(player)) {
            if (figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, cell)) {
                return true;
            }
        }
        return false;
    }

    /*
    Может ли шашка побить фигуру противника, если да, то возвращаем направление
     */
    protected Direction canIBeat(Figure figure, Player player, Game game) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : cell.getNeighbours().keySet()) {
            Cell nextCell = cell.getNeighbours().get(direction);
            if (game.getCellFigure().containsKey(nextCell) &&
                    game.getFigurePlayerMap().get(game.getCellFigure().get(nextCell)) != player &&
                    figureService.checkNeighbourCellAndIsFigureOnThisCell(game, direction, nextCell)) {
                return direction;
            }

        }
        return null;
    }

    protected Step stepWhereCanDoBeat(Figure figure, Player player, Game game, Direction direction) {
        Cell cell = game.getFigureCell().get(figure);
        return new Step(cell, cell.getNeighbours().get(direction).getNeighbours().get(direction), figure, player,
                game.getCellFigure().get(cell.getNeighbours().get(direction)));
    }

    /*
    Получение обязательных ходов для шашки
     */
    public List<Step> getNecessarySteps(Game game, Player player) {
        List<Figure> figures = figureService.listOfFigureWithNecessaryMoves(game, player, TypeOfFigure.CHECKER);
        List<Step> necessarySteps = new ArrayList<>();
        for (Figure figure : figures) {
            necessarySteps.add(stepWhereCanDoBeat(figure, player, game, canIBeat(figure, player, game)));
        }
        return necessarySteps;
    }
}
