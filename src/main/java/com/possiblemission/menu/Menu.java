package com.possiblemission.menu;

import com.possiblemission.Enums.Difficulty;
import com.possiblemission.Export.ExportJson;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.FinishedGame;
import com.possiblemission.game.Game;
import com.possiblemission.game.GameManager;


import java.util.Scanner;

import static com.possiblemission.Import.ImportJson.importGames;
import static com.possiblemission.Import.ImportJson.importJson;

/**
 * Class that represents the menu of the game.
 */
public class Menu {

    /**
     * Menu of the game.
     */
    public static void menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Importing csv: Json/Import/game.json");

        UnorderedArrayList<FinishedGame> gamesFinnish = importGames("Json/Export/gameData.json");
        Game game = importJson("Json/Import/game.json");

        System.out.println("Previous Games with that cod-mission: \n");


        for (FinishedGame finishedGame : gamesFinnish) {
            if (finishedGame.getCodName().equals(game.getCodName())) {
                System.out.println(finishedGame + "\n");
            }
        }

        System.out.println("Player name : ");
        String playerName = in.nextLine();
        System.out.println("You want to play manually(1) or automatic (2): ");
        int choice = in.nextInt();
        boolean manually = choice != 2;

        GameManager gameManager = new GameManager(game, manually, playerName);

        Difficulty difficulty = null;
        System.out.println("Difficulty : \n1- Easy\n2- Medium\n3- Hard");
        int difficultyC = in.nextInt();

        if (difficultyC == 1) {difficulty = Difficulty.EASY;}
        else if (difficultyC == 2) {difficulty = Difficulty.MEDIUM;}
        else if (difficultyC == 3) {difficulty = Difficulty.HARD;}
        else {
            System.out.println("Invalid difficulty");
            return;
        }

        boolean result = gameManager.startGame(difficulty);

        if(result) {
            System.out.println("Player " + playerName + " won the game with " + game.getPlayer().getHealth() + " health");
        }else {
            System.out.println("Player " + playerName + " lost the game");
        }

        ExportJson.exportJson(gameManager);

    }
}
