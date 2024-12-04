package com.possiblemission.Export;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileWriter;
import java.io.IOException;

public class ExportJson {


    public void exportJson() {
        // JSON String
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("name", "mkyong");
        jsonObject.put("age", 42);

        // JSON Array
        JsonArray list = new JsonArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

        jsonObject.put("messages", list);

        try (
                FileWriter fileWriter = new FileWriter("src/main/java/com/possiblemission/Json/Export/person.json")) {

            Jsoner.serialize(jsonObject, fileWriter);

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }


}
