package com.possiblemission.Import;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.*;
import com.possiblemission.game.Division;
import com.possiblemission.game.Game;


import java.io.FileReader;
import java.io.IOException;

/**
 * Class that imports the jsons into the game
 */
public class ImportJson {

    /**
     * Imports the game's json
     * @param path
     * @return
     */
    public static Game importJson(String path) {

        try (FileReader reader = new FileReader(path)) {

            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);

            //GAME
            Game game = new Game((String)jsonObject.get("cod-missao"),((Number)jsonObject.get("versao")).intValue());

            //MAPA
            JsonArray edificio = (JsonArray)jsonObject.get("edificio");

            for(Object division : edificio){
                game.addDivision((String)division);
            }

            //LIGACOES
            JsonArray ligacoes = (JsonArray)jsonObject.get("ligacoes");

            for (Object ligacao : ligacoes) {
                JsonArray link = (JsonArray) ligacao;
                String div = (String) link.get(0);
                String div2 = (String) link.get(1);
                game.addLink(div, div2);
            }

            //CHECK IF MAP IS VALID
            if(!game.checkIfMapIsValid()){
                throw new IllegalArgumentException("Invalid map!");
            }

            //INIMIGOS
            JsonArray inimigos = (JsonArray)jsonObject.get("inimigos");

            for (Object o : inimigos) {
                JsonObject inimigo = (JsonObject) o;
                String divisao = inimigo.get("divisao").toString();
                String nome = inimigo.get("nome").toString();
                int poder = Integer.parseInt(inimigo.get("poder").toString());
                Division division = game.getDivisionByName(divisao);
                Enemy enemy = new Enemy(nome, poder,division);
                division.addEnemy(enemy);
                game.addEnemy(enemy);
            }

            //ENTRADAS SAIDAS
            JsonArray entradasSaidas = (JsonArray)jsonObject.get("entradas-saidas");

            for (Object entradaSaida : entradasSaidas) {
                String divisao = (String) entradaSaida;
                Division division = game.getDivisionByName(divisao);
                division.setExitOrEntry(true);
                game.addEntryOrExit(division);
            }

            //ALVO
            JsonObject target = (JsonObject)jsonObject.get("alvo");

            Division targetDivision = game.getDivisionByName((String) target.get("divisao"));

            Target alvo = new Target ((String) target.get("tipo"), targetDivision);

            targetDivision.setTarget(alvo);

            game.addTarget(alvo);

            //items
            JsonArray itens = (JsonArray)jsonObject.get("itens");
            for (Object item : itens) {
                JsonObject itemJson = (JsonObject) item;
                Division division = game.getDivisionByName(itemJson.get("divisao").toString());
                if(itemJson.get("tipo").toString().equals("kit de vida")){
                    HealthKit healthKit = new HealthKit(Integer.parseInt(itemJson.get("pontos-recuperados").toString()), division);
                    division.setItem(healthKit);
                    game.addItem(healthKit);
                } else if (itemJson.get("tipo").toString().equals("colete")) {
                    Armour colete = new Armour(Integer.parseInt(itemJson.get("pontos-extra").toString()), division);
                    division.setItem(colete);
                    game.addItem(colete);
                }else{
                    throw new IllegalArgumentException("Invalid Item type! : " + itemJson.get("tipo").toString());
                }

            }

            return game;

        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Imports the previous game's json
     * @param path
     * @return
     */
    public static UnorderedArrayList<FinishedGame> importGames(String path) {
        UnorderedArrayList<FinishedGame> gamesList = new UnorderedArrayList<>();

        try (FileReader reader = new FileReader(path)) {
            Object data = Jsoner.deserialize(reader);

            if (data instanceof JsonObject) {
                JsonObject rootObject = (JsonObject) data;
                JsonArray gamesArray = (JsonArray) rootObject.get("games");

                for (Object obj : gamesArray) {
                    if (obj instanceof JsonObject) {
                        JsonObject gameObj = (JsonObject) obj;


                        JsonArray movesArray = (JsonArray) gameObj.get("moves");
                        UnorderedArrayList<String> movesList = new UnorderedArrayList<>();
                        for (Object moveObj : movesArray) {
                            if (moveObj instanceof String) {
                                movesList.addToRear((String) moveObj);
                            }
                        }


                        FinishedGame game = new FinishedGame(
                                (String) gameObj.get("mission"),
                                ((Number) gameObj.get("version")).intValue(),
                                (String) gameObj.get("name"),
                                ((Number) gameObj.get("health")).intValue(),
                                movesList
                        );

                        gamesList.addToRear(game);
                    }
                }
            }
        } catch (IOException | JsonException e) {
            e.printStackTrace();
        }

        return gamesList;

    }
}
