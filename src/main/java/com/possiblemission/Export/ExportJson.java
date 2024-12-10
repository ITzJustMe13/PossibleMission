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

public class ExportJson {


    public static void exportJson(GameManager gamemanager) {
        // Path to the JSON file
        File file = new File("Json/Export/gameData.json");

        // Create the new game data to add
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("mission", gamemanager.getGame().getCodName());
        jsonObject.put("version", gamemanager.getGame().getCodId());
        jsonObject.put("name", gamemanager.getGame().getPlayer().getName());
        jsonObject.put("health", gamemanager.getGame().getPlayer().getHealth());

        JsonArray list = new JsonArray();
        for (Division div : gamemanager.getMoves()) {
            list.add(div.getName());
        }
        jsonObject.put("moves", list);

        try {
            JsonObject rootObject;
            JsonArray dataArray;

            // If the file exists, read the existing content
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    Object existingData = Jsoner.deserialize(reader);
                    if (existingData instanceof JsonObject) {
                        rootObject = (JsonObject) existingData;
                    } else {
                        rootObject = new JsonObject();  // In case it's not a valid JSON object
                    }
                } catch (JsonException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // If the file doesn't exist, create a new root object
                rootObject = new JsonObject();
            }

            // If the "games" array exists, append to it. Otherwise, create it.
            if (rootObject.containsKey("games")) {
                dataArray = (JsonArray) rootObject.get("games");
            } else {
                dataArray = new JsonArray();
                rootObject.put("games", dataArray);
            }

            // Add the new game data to the array
            dataArray.add(jsonObject);

            // Write the updated JSON data back to the file
            try (FileWriter fileWriter = new FileWriter(file)) {
                Jsoner.serialize(rootObject, fileWriter);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
