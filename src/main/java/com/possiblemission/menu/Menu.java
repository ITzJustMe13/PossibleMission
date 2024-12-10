package com.possiblemission.menu;

import com.possiblemission.Enums.Difficulty;
import com.possiblemission.Export.ExportJson;
import com.possiblemission.Import.ImportJson;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.FinishedGame;
import com.possiblemission.game.Division;
import com.possiblemission.game.Game;
import com.possiblemission.game.GameManager;


import java.util.Scanner;

import static com.possiblemission.Import.ImportJson.importGames;
import static com.possiblemission.Import.ImportJson.importJson;

public class Menu {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Importing csv: Json/Import/game.json");

        UnorderedArrayList<FinishedGame> gamesFinnish = importGames("Json/Export/gameData.json");
        Game game = importJson("Json/Import/game.json");

        System.out.println("Previous Games with that cod-mission: \n");

        // Iterate through the finished games and filter by codName
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

        if(manually) {
            int start = 0;
            UnorderedArrayList<Division> entries = game.getEntriesAndExits();
            while(start == 0 && start < entries.size() ) {
                System.out.println("Choose where to start\n");
                for(int i = 0; i < entries.size(); i++) {
                    Division division = entries.get(i);
                    System.out.println(i+1+"-"+division.getName());
                }
                start = in.nextInt();
                game.getPlayer().setCurrentDivision(entries.get(start-1));
            }
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
