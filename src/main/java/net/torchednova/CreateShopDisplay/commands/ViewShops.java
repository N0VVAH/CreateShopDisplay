package net.torchednova.CreateShopDisplay.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.torchednova.CreateShopDisplay.uis.ChestUIController;

public class ViewShops {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(Commands.literal("ViewShops")
                .executes(ViewShops::OpenChestUI)
        );
    }

    public static int OpenChestUI(CommandContext<CommandSourceStack> context)
    {
        Player p = context.getSource().getPlayer();
        if (p == null) return 0;

        ChestUIController.viewShops(p, 54, context.getSource().getLevel());


        return 1;
    }
}
