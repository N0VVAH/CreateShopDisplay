package net.torchednova.CreateShopDisplay.SaveData;

import com.google.common.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;
import net.torchednova.CreateShopDisplay.CreateShopDisplay;
import net.torchednova.CreateShopDisplay.shops.ShopManager;
import net.torchednova.CreateShopDisplay.shops.shops;


import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class TargetDataStorage {

    private static final Type LIST_TYPE = new TypeToken<List<shops>>() {}.getType();

    public static void save(MinecraftServer server)
    {
        try{
            Path file = ModDataPath.getDataFile(server);

            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            CreateShopDisplay.LOGGER.info(String.valueOf(ShopManager.stores.size()));
            String json = ModJson.GSON.toJson(ShopManager.stores);
            Files.writeString(file, json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<shops> load(MinecraftServer server)
    {
        try{
            Path file = ModDataPath.getDataFile(server);

            if (Files.exists(file) == false)
            {
                return new ArrayList<shops>();
            }

            String json = Files.readString(file);

            ArrayList<shops> data = ModJson.GSON.fromJson(json, LIST_TYPE);

            return data != null ? data : new ArrayList<>();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
