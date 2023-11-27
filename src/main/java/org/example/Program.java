package org.example;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';

    private static final int WIN_COUNT = 4;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static  char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;


    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    static void initialize(){
        fieldSizeY = 5;
        fieldSizeX = 5;

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++){
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int y = 0; y < fieldSizeY; y++){
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++){
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static void humanTurn(){
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода X и Y (от 1 до " + fieldSizeX + ")\nчерез пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }

    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn(){
        int x;
        int y;

        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));

        field[y][x] = DOT_AI;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка доступности ячейки игрового поля
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x< fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Метод проверки состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return результат проверки состояния игры
     */
    static boolean checkGameState(char dot, String s){
        if (checkWin(dot)){
            System.out.println(s);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

/**
 * Проверка победы игрока
 * @param dot фишка игрока
 * @return признак победы
 */static boolean checkWin(char dot) {
    return checkRows(dot) || checkColumns(dot) || checkDiagonals(dot);
}

    static boolean checkRows(char dot) {
        // Проверка по горизонтали
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x <= fieldSizeX - WIN_COUNT; x++) {
                boolean win = true;
                for (int i = 0; i < WIN_COUNT; i++) {
                    if (field[y][x + i] != dot) {
                        win = false;
                        break;
                    }
                }
                if (win)
                    return true;
            }
        }
        return false;
    }

    static boolean checkColumns(char dot) {
        // Проверка по вертикали
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y <= fieldSizeY - WIN_COUNT; y++) {
                boolean win = true;
                for (int i = 0; i < WIN_COUNT; i++) {
                    if (field[y + i][x] != dot) {
                        win = false;
                        break;
                    }
                }
                if (win)
                    return true;
            }
        }
        return false;
    }

    static boolean checkDiagonals(char dot) {
        // Проверка по диагонали
        for (int y = 0; y <= fieldSizeY - WIN_COUNT; y++) {
            for (int x = 0; x <= fieldSizeX - WIN_COUNT; x++) {
                boolean win = true;
                for (int i = 0; i < WIN_COUNT; i++) {
                    if (field[y + i][x + i] != dot) {
                        win = false;
                        break;
                    }
                }
                if (win)
                    return true;
            }
        }
        for (int y = 0; y <= fieldSizeY - WIN_COUNT; y++) {
            for (int x = WIN_COUNT - 1; x < fieldSizeX; x++) {
                boolean win = true;
                for (int i = 0; i < WIN_COUNT; i++) {
                    if (field[y + i][x - i] != dot) {
                        win = false;
                        break;
                    }
                }
                if (win)
                    return true;
            }
        }
        return false;
    }
}