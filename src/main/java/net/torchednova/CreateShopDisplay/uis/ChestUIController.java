package net.torchednova.CreateShopDisplay.uis;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.torchednova.CreateShopDisplay.shops.ShopManager;
import net.torchednova.CreateShopDisplay.utils.CreateShopMenu;
import net.torchednova.CreateShopDisplay.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChestUIController {

    public static SimpleContainer createContainer(int size) {
        SimpleContainer container = new SimpleContainer(size) {

            @Override
            public boolean canPlaceItem(int slot, @NotNull ItemStack stack)
            {
                return false;
            }

            @Override
            public boolean canTakeItem(@NotNull Container target, int slot, @NotNull ItemStack stack)
            {
                return false;
            }

        };

        return container;
    }

    public static void openMenu(Player player, SimpleContainer cont) {
        player.openMenu(new SimpleMenuProvider((id, inv, p) -> new CreateShopMenu(id, inv, cont), Component.literal("Stores")));
    }

    public static void viewShops(Player player, int size, ServerLevel sl)
    {
        SimpleContainer cont = createContainer(size);

        if (ShopManager.stores.size() < 1)
        {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "barrier"));
            ItemStack itemStack = new ItemStack(item);
            ItemLore itemLore = new ItemLore(List.of(Utils.Chat("&fCurrently no stores are active")));

            itemStack.set(DataComponents.LORE, itemLore);
            itemStack.set(DataComponents.ITEM_NAME, Utils.Chat("&fYou can create you store to be added to this list"));
            cont.setItem(22, itemStack);
        }


        openMenu(player, cont);

    }



}
