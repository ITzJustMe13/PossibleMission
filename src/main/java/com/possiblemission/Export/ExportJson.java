package com.possiblemission.Export;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.possiblemission.game.Division;
import com.possiblemission.game.GameManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class that exports the game's json
 */
public class ExportJson {


    /**
     * Method that exports to the json
     * @param gameManager the game that was played.
     */
    public static void exportJson(GameManager gameManager) {
        File file = new File("Json/Export/gameData.json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("mission", gameManager.getGame().getCodName());
        jsonObject.put("version", gameManager.getGame().getCodId());
        jsonObject.put("name", gameManager.getGame().getPlayer().getName());
        jsonObject.put("health", gameManager.getGame().getPlayer().getHealth());

        JsonArray list = new JsonArray();
        for (Division div : gameManager.getMoves()) {
            list.add(div.getName());
        }
        jsonObject.put("moves", list);

        try {
            JsonObject rootObject;
            JsonArray dataArray;

            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    Object existingData = Jsoner.deserialize(reader);
                    if (existingData instanceof JsonObject) {
                        rootObject = (JsonObject) existingData;
                    } else {
                        rootObject = new JsonObject();
                    }
                } catch (JsonException e) {
                    throw new RuntimeException(e);
                }
            } else {
                rootObject = new JsonObject();
            }

            if (rootObject.containsKey("games")) {
                dataArray = (JsonArray) rootObject.get("games");
            } else {
                dataArray = new JsonArray();
                rootObject.put("games", dataArray);
            }

            dataArray.add(jsonObject);

            try (FileWriter fileWriter = new FileWriter(file)) {
                Jsoner.serialize(rootObject, fileWriter);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
