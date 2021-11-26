package ru.vsu.css.nechenko_d_a.service;



import ru.vsu.css.nechenko_d_a.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckerService {
    private final FigureService figureService;

    public CheckerService(FigureService figureService) {
        this.figureService = figureService;
    }

    //Получение возможных шагов
    public List<Step> getAvailableSteps(Game game, Player player) {
        List<Figure> availableFigures = figureService.availableFigures(game, player, TypeOfFigure.CHECKER);
        List<Step> steps = new ArrayList<>();
        availableFigures.forEach(figure -> {
            if(figure.getType()== TypeOfFigure.CHECKER) {
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

    protected Direction canIBeat(Figure figure, Player player, Game game) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : cell.getNeighbours().keySet()) {
            Cell nextCell = cell.getNeighbours().get(direction);
            if (game.getCellFigure().containsKey(nextCell) &&
                    game.getFigurePlayerMap().get(game.getCellFigure().get(nextCell)) != player &&
                    nextCell.getNeighbours().containsKey(direction) &&
                    !game.getCellFigure().containsKey(nextCell.getNeighbours().get(direction))) {
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
//Получение необходимых шагов
    public List<Step> getNecessarySteps(Game game, Player player) {
        List<Figure> figures = figureService.listOfFigureWithNecessaryMoves(game, player, TypeOfFigure.CHECKER);
        List<Step> necessarySteps = new ArrayList<>();
        for (Figure figure : figures) {
            necessarySteps.add(stepWhereCanDoBeat(figure, player, game, canIBeat(figure, player, game)));
        }
        return necessarySteps;
    }
//Получение фигур, для которых есть шаги, которые необходимо совершить
    protected List<Figure> listOfFigureWithNecessaryMoves(Game game, Player player) {
        List<Figure> allFigures = game.getPlayerFigures().get(player);
        return allFigures.stream()
                .filter(figure -> canIBeat(figure, player, game) != null && figure.getType() == TypeOfFigure.CHECKER)
                .collect(Collectors.toList());
    }
}
// как ходить? куда ходить и тд
