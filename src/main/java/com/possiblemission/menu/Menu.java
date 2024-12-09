package com.possiblemission.menu;

import com.possiblemission.Enums.Difficulty;
import com.possiblemission.Export.ExportJson;
import com.possiblemission.Import.ImportJson;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.FinishedGame;
import com.possiblemission.game.Division;
import com.possiblemission.game.Game;
import com.possiblemission.game.GameManager;
import pt.ipp.estg.ed.UnorderedListADT;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Importing csv: Json/Import/game.json");

        UnorderedArrayList<FinishedGame> gamesFinnish = ImportJson.importGames("Json/Export/gameData.json");
        Game game = ImportJson.importJson("Json/Import/game.json");

        System.out.println("Previous Games with that cod-mission: \n");
        for(FinishedGame gameFinnish : gamesFinnish) {
            if(gameFinnish.getCodName().equals(game.getCodName())) {
                System.out.println(gameFinnish+ "\n");
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

//        UnorderedArrayList<Division> entries = (UnorderedArrayList<Division>) game.getEntriesAndExits();
//        System.out.println("Choose where to start\n");
//        for(int i = 0; i < entries.size(); i++) {
//            Division division = entries.get(i);
//            System.out.println(i+"-"+division.getName());
//        }
//        int start = in.nextInt();
//        game.getPlayer().setCurrentDivision(entries.get(start));

        boolean result = gameManager.StartGame(difficulty);

        if(result) {
            System.out.println("Player " + playerName + " won the game with " + game.getPlayer().getHealth() + " health");
        }else {
            System.out.println("Player " + playerName + " lost the game");
        }

        ExportJson.exportJson(gameManager);

    }
}
