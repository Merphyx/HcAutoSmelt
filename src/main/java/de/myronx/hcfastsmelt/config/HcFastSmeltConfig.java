package de.myronx.hcfastsmelt.config;

import net.fabricmc.loader.api.FabricLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HcFastSmeltConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().resolve("hcfastsmelt.json").toString());
    public static boolean hotbartoggle = false;

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            hotbartoggle = json.get("hotbartoggle").getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("hotbartoggle", hotbartoggle);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE))) {
            writer.write(GSON.toJson(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}