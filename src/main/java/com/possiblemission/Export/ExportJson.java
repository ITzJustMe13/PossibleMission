package com.possiblemission.Export;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.possiblemission.game.Division;
import com.possiblemission.game.GameManager;

import java.io.FileWriter;
import java.io.IOException;

public class ExportJson {


    public static void exportJson(GameManager gamemanager) {
        // JSON String
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("mission",gamemanager.getGame().getCodName());
        jsonObject.put("version", gamemanager.getGame().getCodId());
        jsonObject.put("name",gamemanager.getGame().getPlayer().getName());
        jsonObject.put("health",gamemanager.getGame().getPlayer().getHealth());

        // JSON Array
        JsonArray list = new JsonArray();
        for(Division div : gamemanager.getMoves()){
            list.add(div.getName());
        }

        jsonObject.put("moves", list);

        try (
                FileWriter fileWriter = new FileWriter("Json/Export/gameData.json")) {

            Jsoner.serialize(jsonObject, fileWriter);

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}
