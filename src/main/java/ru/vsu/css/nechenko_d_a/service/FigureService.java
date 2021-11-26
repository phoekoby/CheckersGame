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

    public void makingQueenFromChecker(Player player, Figure figure, Game game) {
        figure.setType(TypeOfFigure.QUEEN);
    }

    protected boolean haveAvailableMoves(Figure figure, Game game, Player player) {
        Cell cell = game.getFigureCell().get(figure);
        if(figure.getType()==TypeOfFigure.QUEEN){
            for (Direction direction : Direction.values()) {
                if (cell.getNeighbours().containsKey(direction) && !game.getCellFigure().containsKey(cell.getNeighbours().get(direction))) {
                    return true;
                }
            }
        }else {
            for (Direction direction : game.getAvailableDirections().get(player)) {
                if (cell.getNeighbours().containsKey(direction) && !game.getCellFigure().containsKey(cell.getNeighbours().get(direction))) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Step> getNecessarySteps(Game game, Player player) {
        List<Step> necessarySteps = new ArrayList<>();
        necessarySteps.addAll(queenService.getNecessarySteps(game, player));
        necessarySteps.addAll(checkerService.getNecessarySteps(game, player));
        return necessarySteps;
    }
    public List<Step> getAvailableSteps(Game game, Player player){
        List<Step> availableStep= new ArrayList<>();
        availableStep.addAll(checkerService.getAvailableSteps(game,player));
        availableStep.addAll(queenService.getAvailableSteps(game,player));
        return availableStep;
    }

    public List<Figure> availableFigures(Game game, Player player, TypeOfFigure typeOfFigure) {
        List<Figure> allFiguresOfOnePLayer = game.getPlayerFigures().get(player);
        return allFiguresOfOnePLayer.stream()
                .filter(figure -> haveAvailableMoves(figure, game, player) && figure.getType()==typeOfFigure)
                .collect(Collectors.toList());
    }
    protected List<Figure> listOfFigureWithNecessaryMoves(Game game, Player player, TypeOfFigure typeOfFigure) {
        List<Figure> allFigures = game.getPlayerFigures().get(player);
        return allFigures.stream()
                .filter(figure -> figure.getType() == typeOfFigure && (typeOfFigure==TypeOfFigure.QUEEN ?
                        queenService.canIBeat(figure, player, game) != null : checkerService.canIBeat(figure, player, game) != null))
                .collect(Collectors.toList());
    }
}
