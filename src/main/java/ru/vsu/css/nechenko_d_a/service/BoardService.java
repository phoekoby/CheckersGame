package ru.vsu.css.nechenko_d_a.service;



import ru.vsu.css.nechenko_d_a.model.*;

import java.util.*;

public class BoardService {
    protected Cell createBoard(Cell rightUp) {
        List<Cell> centerCells = new ArrayList<>();
        centerCells.add(rightUp);
        Cell prev = rightUp;
        for (int i = 1; i < 8; i++) {
            Cell curr = new Cell();
            prev.getNeighbours().put(Direction.SOUTH_WEST, curr);
            curr.getNeighbours().put(Direction.NORTH_EAST, prev);
            prev = curr;
            centerCells.add(curr);
        }
        for (int i = 1; i < 7; i++) {
            Deque<Cell> deq = new ArrayDeque<>();
            Cell center = centerCells.get(i);
            deq.addFirst(center);
            for (int j = 0; j < i && j < 7 - i; j++) {
                Cell northWest = new Cell();
                northWest.getNeighbours().put(Direction.SOUTH_EAST, deq.getLast());
                deq.getLast().getNeighbours().put(Direction.NORTH_WEST, northWest);
                deq.addLast(northWest);
                Cell southEast = new Cell();
                southEast.getNeighbours().put(Direction.NORTH_WEST, deq.getFirst());
                deq.getFirst().getNeighbours().put(Direction.SOUTH_EAST, southEast);
                deq.addFirst(southEast);
            }
        }
        for(int i = 1; i < 6; i++){
            Cell currentFirst = centerCells.get(i);
            Cell currentSecond = centerCells.get(i+1);
            createDiag(currentFirst,currentSecond,Direction.SOUTH_EAST);
            currentFirst = centerCells.get(i);
            currentSecond = centerCells.get(i+1);
            createDiag(currentFirst,currentSecond,Direction.NORTH_WEST);
        }


        return centerCells.get(7);
    }
    private void createDiag(Cell currentFirst, Cell currentSecond, Direction direction){
        while (currentSecond.getNeighbours().containsKey(direction) && currentFirst.getNeighbours().containsKey(direction)){
            currentFirst=currentFirst.getNeighbours().get(direction);
            currentSecond=currentSecond.getNeighbours().get(direction);
            currentFirst.getNeighbours().put(Direction.SOUTH_WEST,currentSecond);
            currentSecond.getNeighbours().put(Direction.NORTH_EAST,currentFirst);
        }
    }

    public static void drawBoard(Game game){
        Cell up = game.getRightUpCell();
        List<List<Cell>> board = game.getBoardForPainting();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.print("╒═══╕ ");
            }
            System.out.println();
            if(i%2==0) {
                for (int j = 0; j < 4; j++) {
                    System.out.print("|   | |");
                    Cell currentPaintingCell = board.get(i).get(j);
                    Figure figure = game.getCellFigure().get(currentPaintingCell);
                    if(figure!=null){
                        Player owner = game.getFigurePlayerMap().get(figure);
                        System.out.print(game.getVisualFigure().get(owner).get(figure.getType()) + "| ");
                    }else{
                        System.out.print("   | ");
                    }
                }
            }else {
                for (int j = 0; j < 4; j++) {
                    Cell currentPaintingCell = board.get(i).get(j);
                    Figure figure = game.getCellFigure().get(currentPaintingCell);
                    if(figure!=null){
                        Player owner = game.getFigurePlayerMap().get(figure);
                        System.out.print("|" + game.getVisualFigure().get(owner).get(figure.getType()) + "| |   | ");
                    }else{
                        System.out.print("|   | |   | ");
                    }
                }
            }

            System.out.println();
            for(int j = 0; j < 8; j++){
                System.out.print("╘═══╛ ");
            }
            System.out.println();
        }

    }
    public static void drawBoard2(Game game){
        Cell up = game.getRightUpCell();
        List<List<Cell>> board = game.getBoardForPainting();
        for(int i = 0; i < 8; i++){

            if(i%2==0) {
                for(int j = 0; j < 4; j++){
                    System.out.print("      ╔═══╗ ");
                }
                System.out.println();
                for (int j = 0; j < 4; j++) {
                    System.out.print("      ∥");
                    Cell currentPaintingCell = board.get(i).get(j);
                    Figure figure = game.getCellFigure().get(currentPaintingCell);
                    if(figure!=null){
                        Player owner = game.getFigurePlayerMap().get(figure);
                        System.out.print(game.getVisualFigure().get(owner).get(figure.getType()) + "∥ ");
                    }else{
                        System.out.print("   ∥ ");
                    }
                }
                System.out.println();
                for(int j = 0; j <4; j++){
                    System.out.print("      ╚═══╝ ");
                }
                System.out.println();
            }else {
                for(int j = 0; j < 4; j++){
                    System.out.print("╔═══╗       ");
                }
                System.out.println();
                for (int j = 0; j < 4; j++) {
                    Cell currentPaintingCell = board.get(i).get(j);
                    Figure figure = game.getCellFigure().get(currentPaintingCell);
                    if(figure!=null){
                        Player owner = game.getFigurePlayerMap().get(figure);
                        System.out.print("∥" + game.getVisualFigure().get(owner).get(figure.getType()) + "∥       ");
                    }else{
                        System.out.print("∥   ∥       ");
                    }
                }
                System.out.println();
                for(int j = 0; j < 4; j++){
                    System.out.print("╚═══╝       ");
                }
                System.out.println();
            }


        }
        System.out.println("----------------------------------------------------------------");
    }
    protected List<List<Cell>> getBoardForPainting(Game game){
        Cell curr = game.getRightUpCell();
        List<List<Cell>> board = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            List<Cell> lineListOfCells = new ArrayList<>();
            if(i%2==0){
                for(int c = 0 ; c < 4; c++){
                    lineListOfCells.add(curr);
                    curr = curr.getNeighbours().get(Direction.SOUTH_WEST);
                    if(curr.getNeighbours().containsKey(Direction.NORTH_WEST)){
                        curr = curr.getNeighbours().get(Direction.NORTH_WEST);
                    }else {
                        break;
                    }
                }
                Collections.reverse(lineListOfCells);
                board.add(lineListOfCells);
            }else {
                if(i!=7) {
                    for (int c = 0; c < 4; c++) {
                        lineListOfCells.add(curr);
                        if (!curr.getNeighbours().containsKey(Direction.SOUTH_EAST)) {
                            break;
                        }
                        curr = curr.getNeighbours().get(Direction.SOUTH_EAST);
                        if (curr.getNeighbours().containsKey(Direction.NORTH_EAST)) {
                            curr = curr.getNeighbours().get(Direction.NORTH_EAST);
                        }
                    }
                }else{
                    lineListOfCells.add(curr);
                    for(int l = 0; l < 3; l++){
                        curr = curr.getNeighbours().get(Direction.NORTH_EAST);
                        curr = curr.getNeighbours().get(Direction.SOUTH_EAST);
                        lineListOfCells.add(curr);
                    }
                }
                board.add(lineListOfCells);
            }

        }
        return board;
    }
}
