package net.bytem0use.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerFlightConfig {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "aspects.json");
    public float maxFlightSpeed = 9.0F;
    public boolean isHeatEnabled = true;
    public boolean breakBlocksOnTakeoff = true;
    public static PlayerFlightConfig INSTANCE = new PlayerFlightConfig();

    public static void load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                INSTANCE = (PlayerFlightConfig)GSON.fromJson(reader, PlayerFlightConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }

    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
