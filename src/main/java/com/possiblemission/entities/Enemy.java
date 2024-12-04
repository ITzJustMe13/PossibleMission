package com.possiblemission.entities;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.game.Division;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Enemy extends Human {

    public Enemy(String name, int power, Division division) {
        super(name, power,100);
        setCurrentDivision(division);
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final IOException caught) {
            throw new RuntimeException(caught);
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        JsonObject json = new JsonObject();
        json.put("name", this.getName());
        json.put("power", this.getPower());
        json.toJson(writer);
    }
}
