package ru.vsu.css.nechenko_d_a.service;

import ru.vsu.css.nechenko_d_a.model.Game;
import ru.vsu.css.nechenko_d_a.model.Player;
import ru.vsu.css.nechenko_d_a.model.Step;

import java.util.List;


public class PlayerService {
    private final FigureService figureService = new FigureService();

    /*
    Совершение хода:
    получаем список ходов, которые мы обязаны сделать и делаем один из этих ходов,
    если таковых нет, берем все возможные и ходим один из способов
     */
    public boolean doMove(Player player, Game game) {
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
            if (availableSteps.size() < 1) {
                System.out.println("У Игрока " + player.getName() + " не осталось ходов");
                return false;
            }
            Step step = availableSteps.get((int) (Math.random() * (availableSteps.size() - 1)));
            doStep(step, game, beat);
        }
        return true;
    }

    /*
    Перестановка фигур на поле
     */
    private void doStep(Step step, Game game, boolean beat) {
        game.getCellFigure().remove(step.getFrom());
        game.getCellFigure().put(step.getTo(), step.getFigure());
        game.getFigureCell().put(step.getFigure(), step.getTo());
        if (figureService.isItEndForMakingQueen(step.getPlayer(), step.getTo(), game)) {
            figureService.makingQueenFromChecker(step.getFigure());
        }
        if (beat) {
            beat(step, game);
        }
    }

    /*
    "Бьем"
    Если в ходе мы бьем какую-то шашку, ее нужно убрать с поля
     */
    private void beat(Step step, Game game) {
        game.getCellFigure().remove(game.getFigureCell().get(step.getBeatenFigure()));
        game.getFigureCell().remove(step.getBeatenFigure());
        game.getPlayerFigures().get(game.getFigurePlayerMap().get(step.getBeatenFigure())).remove(step.getBeatenFigure());
        game.getFigurePlayerMap().remove(step.getBeatenFigure());
    }

}
