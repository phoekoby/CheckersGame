package ru.vsu.css.nechenko_d_a;


import ru.vsu.css.nechenko_d_a.model.Game;
import ru.vsu.css.nechenko_d_a.model.Player;
import ru.vsu.css.nechenko_d_a.service.GameService;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        GameService gameService = new GameService();
        Game game = Game.createGame();
        List<Player> pl = new ArrayList<>();
        pl.add(new Player("Player white"));
        pl.add(new Player("Player black"));
        gameService.startGame(game, pl);
    }
}
