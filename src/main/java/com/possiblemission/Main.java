package com.possiblemission;

import com.possiblemission.Import.ImportJson;
import com.possiblemission.game.Game;

public class Main {
    public static void main(String[] args) {
        String path = "Json/Export/gameData.json";
        Game game = ImportJson.importJson(path);


    }
}