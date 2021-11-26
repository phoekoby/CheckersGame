package ru.vsu.css.nechenko_d_a;

import ru.vsu.css.nechenko_d_a.model.Figure;
import ru.vsu.css.nechenko_d_a.model.Game;
import ru.vsu.css.nechenko_d_a.model.Player;
import ru.vsu.css.nechenko_d_a.service.GameService;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args){
        GameService gameService = new GameService();
        Game game = Game.createGame();
        List<Player> pl = new ArrayList<>();
        pl.add(new Player("Player 1"));
        pl.add(new Player("Player 2"));
        gameService.startGame(game, pl);
    }
    private static Player getFigurePlayer(Game game, Figure f){
        for(var playerFigures : game.getPlayerFigures().entrySet()){
            if(playerFigures.getValue().contains(f)){
                return playerFigures.getKey();
            }
        }
        return null;
    }

//
}
