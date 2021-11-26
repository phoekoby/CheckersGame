package ru.vsu.css.nechenko_d_a.service;


import ru.vsu.css.nechenko_d_a.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QueenService {
    private final FigureService figureService;

    public QueenService(FigureService figureService) {
        this.figureService = figureService;
    }

    public List<Step> getAvailableSteps(Game game, Player player) {
        List<Figure> availableFigures = figureService.availableFigures(game, player, TypeOfFigure.QUEEN);
        if(availableFigures.isEmpty()){
            return Collections.emptyList();
        }
        List<Step> steps = new ArrayList<>();
        availableFigures.forEach(figure -> {
            System.out.println(figure.getType());
                Cell cell = game.getFigureCell().get(figure);
                Cell to = game.getFigureCell().get(figure);
                for (Direction direction : Direction.values()) {
                    while (to.getNeighbours().containsKey(direction) && !game.getCellFigure().containsKey(to.getNeighbours().get(direction))) {
                        steps.add(new Step(cell, to, figure, player));
                        to = to.getNeighbours().get(direction);
                    }
                }
        });
        System.out.println(steps.size() + "                steps");
        return steps;
    }


    protected Direction canIBeat(Figure figure, Player player, Game game) {
        Cell cell = game.getFigureCell().get(figure);
        for (Direction direction : cell.getNeighbours().keySet()) {
            int i = 0;
            while (cell.getNeighbours().containsKey(direction)
                    && !game.getCellFigure().containsKey(cell.getNeighbours().get(direction))) {
                cell = cell.getNeighbours().get(direction);
            }
                Cell nextCell = cell.getNeighbours().get(direction);
                if (game.getCellFigure().containsKey(cell.getNeighbours().get(direction)) &&
                        game.getFigurePlayerMap().get(game.getCellFigure().get(nextCell)) != player &&
                        nextCell.getNeighbours().containsKey(direction) &&
                        !game.getCellFigure().containsKey(nextCell.getNeighbours().get(direction))) {
                    System.out.println("CAn");
                    return direction;
                }
            }
        return null;
    }

    protected List<Step> stepWhereCanDoBeat(Figure figure, Player player, Game game, Direction direction) {
        List<Step> steps = new ArrayList<>();
        Cell from = game.getFigureCell().get(figure);
        Cell cell = from;
        while (cell.getNeighbours().containsKey(direction) && !game.getCellFigure().containsKey(cell.getNeighbours().get(direction))) {
            cell = cell.getNeighbours().get(direction);
        }
        Cell to = cell.getNeighbours().get(direction);
        while (to.getNeighbours().containsKey(direction)) {
            steps.add(new Step(from, to, figure, player, game.getCellFigure().get(cell.getNeighbours().get(direction))));
            to=to.getNeighbours().get(direction);
        }
        return steps;
    }

        public List<Step> getNecessarySteps(Game game, Player player){
        List<Figure> figures = figureService.listOfFigureWithNecessaryMoves(game,player, TypeOfFigure.QUEEN);
        List<Step> necessarySteps = new ArrayList<>();
        for(Figure figure: figures){
            necessarySteps.addAll(stepWhereCanDoBeat(figure,player,game,canIBeat(figure,player,game)));
        }
        return necessarySteps;
    }
}
