package ru.vsu.css.nechenko_d_a.service;

import ru.vsu.css.nechenko_d_a.model.*;


import java.util.*;

public class GameService {
    private final static BoardService boardService = new BoardService();
    private final static PlayerService playerService = new PlayerService();

    public void startGame(Game game, List<Player> players) {
        Cell rightUp = new Cell();
        Cell leftDown = boardService.createBoard(rightUp);
        game.setRightUpCell(rightUp);
        game.setLeftDownCell(leftDown);
        setCheckers(players, game);
        game.setBoardForPainting(boardService.getBoardForPainting(game));
        BoardService.drawBoard2(game);
        processGame(game);
    }

    private void processGame(Game game) {
        Player player = null;
        while (game.getPlayers().size() == 2) {
             player = doMove(game);
            BoardService.drawBoard2(game);
        }
        if(player!=null){
            System.out.println(player.getName() + " WINNER");
        }
    }

    /*
    Ход игрока
     */
    private Player doMove(Game game) {
        Player player = game.getPlayers().poll();
        System.out.println(player != null ? player.getName() : null + "do movement");
        if (!playerService.doMove(player, game) ||
                game.getPlayerFigures().get(game.getPlayers().peek()).size() < 1) {
            return player;
        }
        game.getPlayers().offer(player);
        return null;
    }

    /*
    Устанавливаем шашки на поле
     */
    private void setCheckers(List<Player> players, Game game) {
        List<Player> pl = new ArrayList<>(players);
        Player one = pl.get(0);
        Player two = pl.get(1);
        Queue<Player> playerQueue = new ArrayDeque<>();
        playerQueue.offer(one);
        playerQueue.offer(two);
        game.setPlayers(playerQueue);
        List<Figure> figureForPlayerOne = new ArrayList<>();
        List<Figure> figureForPlayerTwo = new ArrayList<>();
        Map<Cell, Figure> cellFigureMap = new HashMap<>();
        Map<Figure, Cell> figureCellMap = new HashMap<>();
        Map<Player, List<Figure>> playerSetMap = new HashMap<>();
        Map<Figure, Player> figurePlayerMap = new HashMap<>();
        Map<Player, List<Direction>> availableDirection = new HashMap<>();

        setFiguresForPlayer(game.getRightUpCell(), one, figureForPlayerOne, cellFigureMap, figureCellMap, figurePlayerMap,
                Direction.SOUTH_WEST, Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST);
        availableDirection.put(one, List.of(Direction.SOUTH_EAST, Direction.SOUTH_WEST));
        setFiguresForPlayer(game.getLeftDownCell(), two, figureForPlayerTwo, cellFigureMap, figureCellMap, figurePlayerMap,
                Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST, Direction.NORTH_WEST);
        availableDirection.put(two, List.of(Direction.NORTH_EAST, Direction.NORTH_WEST));

        Map<TypeOfFigure, String> forOne = new HashMap<>();
        forOne.put(TypeOfFigure.CHECKER, " w ");
        forOne.put(TypeOfFigure.QUEEN, " W ");
        Map<TypeOfFigure, String> forTwo = new HashMap<>();
        forTwo.put(TypeOfFigure.CHECKER, " b ");
        forTwo.put(TypeOfFigure.QUEEN, " B ");
        game.getVisualFigure().put(one, forOne);
        game.getVisualFigure().put(two, forTwo);
        game.setCellFigure(cellFigureMap);
        game.setFigureCell(figureCellMap);
        playerSetMap.put(one, figureForPlayerOne);
        playerSetMap.put(two, figureForPlayerTwo);
        game.setPlayerFigures(playerSetMap);
        game.setFigurePlayerMap(figurePlayerMap);
        game.setAvailableDirections(availableDirection);
    }

    /*
    Устанавливаем на поле фигуры для одного игрока (змейкой)
     */
    private void setFiguresForPlayer(Cell curr,
                                     Player player,
                                     List<Figure> figureForPlayer,
                                     Map<Cell, Figure> cellFigureMap,
                                     Map<Figure, Cell> figureCellMap,
                                     Map<Figure, Player> figurePlayerMap,
                                     Direction firstDirectionForFirstIteration,
                                     Direction secondDirectionForFirstIteration,
                                     Direction firstDirectionForSecondIteration,
                                     Direction secondDirectionForSecondIteration) {
        while (curr.getNeighbours().containsKey(firstDirectionForFirstIteration) ||
                curr.getNeighbours().containsKey(secondDirectionForFirstIteration)) {
            Figure figure = new Figure(TypeOfFigure.CHECKER);
            figureForPlayer.add(figure);
            cellFigureMap.put(curr, figure);
            figureCellMap.put(figure, curr);
            figurePlayerMap.put(figure, player);
            if (curr.getNeighbours().containsKey(secondDirectionForFirstIteration)) {
                curr = curr.getNeighbours().get(secondDirectionForFirstIteration);
            } else if (curr.getNeighbours().containsKey(firstDirectionForFirstIteration)) {
                curr = curr.getNeighbours().get(firstDirectionForFirstIteration);
            } else {
                break;
            }
        }
        Figure figure = new Figure(TypeOfFigure.CHECKER);
        figureForPlayer.add(figure);
        cellFigureMap.put(curr, figure);
        figureCellMap.put(figure, curr);
        figurePlayerMap.put(figure, player);
        boolean isCellAvailable = true;
        while (curr.getNeighbours().containsKey(firstDirectionForSecondIteration)
                || curr.getNeighbours().containsKey(secondDirectionForSecondIteration)) {
            if (!isCellAvailable) {
                curr = curr.getNeighbours().get(firstDirectionForSecondIteration);
                isCellAvailable = true;
            } else {
                if (curr.getNeighbours().containsKey(secondDirectionForSecondIteration)) {
                    curr = curr.getNeighbours().get(secondDirectionForSecondIteration);
                    Figure figure1 = new Figure(TypeOfFigure.CHECKER);
                    figureForPlayer.add(figure1);
                    cellFigureMap.put(curr, figure1);
                    figureCellMap.put(figure1, curr);
                    figurePlayerMap.put(figure1, player);
                    isCellAvailable = false;
                } else {
                    break;
                }
            }
        }
    }
}
