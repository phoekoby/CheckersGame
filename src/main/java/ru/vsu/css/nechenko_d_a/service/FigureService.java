package ru.vsu.css.nechenko_d_a.service;

import ru.vsu.css.nechenko_d_a.model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FigureService {
    private final CheckerService checkerService = new CheckerService(this);
    private final QueenService queenService = new QueenService(this);

    public boolean isItEndForMakingQueen(Player player, Cell cell, Game game) {
        List<Direction> directions = game.getAvailableDirections().get(player);
        for (Direction d : directions) {
            Cell next = cell.getNeighbours().get(d);
            if (next != null) {
                return false;
            }
        }
        return true;
    }

    public void makingQueenFromChecker(Figure figure) {
        figure.setType(TypeOfFigure.QUEEN);
    }

    /*
    Проверка на наличие у фигуры ходов, в зависимости от типа фигуры, разные проверки
     */
    protected boolean haveAvailableMoves(Figure figure, Game game, Player player) {
        if (figure.getType() == TypeOfFigure.QUEEN) {
            return queenService.haveAvailableMoves(figure, game);
        } else {
            return checkerService.haveAvailableMoves(figure, game, player);
        }
    }

    /*
    Получение абсолютно всех обязательных ходов
     */
    public List<Step> getNecessarySteps(Game game, Player player) {
        List<Step> necessarySteps = new ArrayList<>();
        necessarySteps.addAll(queenService.getNecessarySteps(game, player));
        necessarySteps.addAll(checkerService.getNecessarySteps(game, player));
        return necessarySteps;
    }

    /*
        Получение абсолютно всех возможных ходов
     */
    public List<Step> getAvailableSteps(Game game, Player player) {
        List<Step> availableStep = new ArrayList<>();
        availableStep.addAll(checkerService.getAvailableSteps(game, player));
        availableStep.addAll(queenService.getAvailableSteps(game, player));
        return availableStep;
    }

    /*
    доступные для хода фигуры
     */
    public List<Figure> availableFigures(Game game, Player player, TypeOfFigure typeOfFigure) {
        List<Figure> allFiguresOfOnePLayer = game.getPlayerFigures().get(player);
        return allFiguresOfOnePLayer.stream()
                .filter(figure -> haveAvailableMoves(figure, game, player) && figure.getType() == typeOfFigure)
                .collect(Collectors.toList());
    }

    /*
    получение списка фигур, у которых есть обязательные ходы
     */
    protected List<Figure> listOfFigureWithNecessaryMoves(Game game, Player player, TypeOfFigure typeOfFigure) {
        List<Figure> allFigures = game.getPlayerFigures().get(player);
        return allFigures.stream()
                .filter(figure -> figure.getType() == typeOfFigure && (typeOfFigure == TypeOfFigure.QUEEN ?
                        queenService.canIBeat(figure, player, game) != null : checkerService.canIBeat(figure, player, game) != null))
                .collect(Collectors.toList());
    }

    /*
    Проверка наличия соседней клетки по заданному направлению и отсутствия на ней фигуры
     */
    protected boolean checkNeighbourCellAndIsFigureOnThisCell(Game game, Direction direction, Cell cell) {
        return cell.getNeighbours().containsKey(direction) &&
                !game.getCellFigure().containsKey(cell.getNeighbours().get(direction));
    }
}
