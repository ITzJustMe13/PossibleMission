package com.possiblemission;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.cliftonlabs.json_simple.*;
import com.possiblemission.Import.ImportJson;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.game.Game;

public class Main {
    public static void main(String[] args) {

        Game game = ImportJson.importJson("Json/Import/game.json");

        System.out.println(game.getMap().toString());
    }
}