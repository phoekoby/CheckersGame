package ru.vsu.css.nechenko_d_a.service;

import ru.vsu.css.nechenko_d_a.model.Game;
import ru.vsu.css.nechenko_d_a.model.Player;
import ru.vsu.css.nechenko_d_a.model.Step;

import java.util.List;


public class PlayerService {
    private final FigureService figureService = new FigureService();
    private final CheckerService checkerService = new CheckerService(figureService);

    public void doMove(Player player, Game game) {
        boolean beat = false;
        List<Step> steps = figureService.getNecessarySteps(game, player);
        if (!steps.isEmpty()) {
            Step step = steps.get((int) (Math.random() * (steps.size() - 1)));
            beat = true;
            doStep(step, game, beat);
            steps = figureService.getNecessarySteps(game, player);
            while (!steps.isEmpty()) {
                step = steps.get((int) (Math.random() * (steps.size() - 1)));
                doStep(step, game, beat);
                steps = figureService.getNecessarySteps(game, player);
            }
        } else {
            List<Step> availableSteps = figureService.getAvailableSteps(game, player);
            Step step = availableSteps.get((int) (Math.random() * (availableSteps.size() - 1)));
            doStep(step, game, beat);
        }

    }

    private void doStep(Step step, Game game, boolean beat) {
        game.getCellFigure().remove(step.getFrom());
        game.getCellFigure().put(step.getTo(), step.getFigure());
        game.getFigureCell().put(step.getFigure(), step.getTo());
        if (figureService.isItEndForMakingQueen(step.getPlayer(), step.getTo(), game)) {
            figureService.makingQueenFromChecker(step.getPlayer(), step.getFigure(), game);
        }
        if (beat) {
            beat(step, game);
        }
    }

    private void beat(Step step, Game game) {
        game.getCellFigure().remove(game.getFigureCell().get(step.getBeatenFigure()));
        game.getFigureCell().remove(step.getBeatenFigure());
        game.getPlayerFigures().get(game.getFigurePlayerMap().get(step.getBeatenFigure())).remove(step.getBeatenFigure());
        game.getFigurePlayerMap().remove(step.getBeatenFigure());
    }

}
