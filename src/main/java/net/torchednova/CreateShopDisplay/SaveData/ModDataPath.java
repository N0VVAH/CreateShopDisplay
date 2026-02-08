package net.torchednova.CreateShopDisplay.SaveData;

import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

public class ModDataPath {
    public static Path getDataFile(MinecraftServer server) {
        return server.getWorldPath(net.minecraft.world.level.storage.LevelResource.ROOT).resolve("data").resolve("CreateShopDisplay").resolve("shops.json");
    }


}