package net.torchednova.CreateShopDisplay.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.neoforge.common.NeoForgeConfig;
import net.torchednova.CreateShopDisplay.CreateShopDisplay;
import net.torchednova.CreateShopDisplay.CustomComponent;
import net.torchednova.CreateShopDisplay.shops.ShopManager;
import net.torchednova.CreateShopDisplay.shops.shops;

import java.util.Arrays;
import java.util.List;

public class CreateShopMenu extends ChestMenu {

    private final SimpleContainer container;
    private int pagenum = 1;

    private boolean inShop = false;
    private int shopPageNum = 1;
    private shops curShop = null;

    public CreateShopMenu (int id, Inventory playerInventory, SimpleContainer cont)
    {
        super(MenuType.GENERIC_9x6, id, playerInventory, cont, 6);
        pagenum = 1;
        this.container = cont;

        if (!(ShopManager.stores.size() < 1)) displayPage(playerInventory.player.level().getServer());



    }

    private void displayPage(MinecraftServer s)
    {
        this.container.clearContent();

        ItemStack itemStack;
        for (int i = 0 + ((pagenum - 1) * 35); i < ShopManager.stores.size(); i++)
        {
            if (i >= 36 + ((pagenum - 1) * 35)) break;

            itemStack = new ItemStack(Items.PLAYER_HEAD);
            itemStack.set(DataComponents.ITEM_NAME, s.getPlayerList().getPlayer(ShopManager.stores.get(i).player).getName());
            itemStack.set(CustomComponent.STRING_ID, s.getPlayerList().getPlayer(ShopManager.stores.get(i).player).getName().toString());
            this.container.addItem(itemStack);
        }

        Item i = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "blue_wool")));
        ItemStack is = new ItemStack(i, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fExit"));
        is.set(CustomComponent.STRING_ID, "Exit");
        this.container.setItem(49, is);

        i = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "red_wool")));
        is = new ItemStack(i, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fPrevious"));
        is.set(CustomComponent.STRING_ID, "Previous");
        this.container.setItem(45, is);

        i = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "green_wool")));
        is = new ItemStack(i, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fNext"));
        is.set(CustomComponent.STRING_ID, "Next");
        this.container.setItem(53, is);
    }

    private void displayShopPage(shops shop)
    {
        this.container.clearContent();

        Item it = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "blue_wool")));
        ItemStack is = new ItemStack(it, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fExit"));
        is.set(CustomComponent.STRING_ID, "Exit");
        this.container.setItem(49, is);

        it = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "red_wool")));
        is = new ItemStack(it, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fPrevious"));
        is.set(CustomComponent.STRING_ID, "Previous");
        this.container.setItem(45, is);

        it = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("minecraft", "green_wool")));
        is = new ItemStack(it, 1);
        is.set(DataComponents.ITEM_NAME, Utils.Chat("&fNext"));
        is.set(CustomComponent.STRING_ID, "Next");
        this.container.setItem(53, is);

        String[] id = null;
        String pos = "";

        for (int i = 0 + ((shopPageNum - 1) * 36); i < shop.items.size(); i++)
        {
            if (i >= 36 + ((shopPageNum - 1) * 36)) break;
            id = shop.items.get(i).sale.split(":");
            it = (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(id[0], id[1])));
            is = new ItemStack(it, 1);
            is.set(DataComponents.ITEM_NAME, Utils.Chat("&f" + id[1]));
            if (shop.items.get(i).pos.getX() > 8320 && shop.items.get(i).pos.getX() < 8400 && shop.items.get(i).pos.getY() > -7450 && shop.items.get(i).pos.getY() < -7550)
            {
                pos = "Spawn Shops";
            }
            else {
                pos = shop.items.get(i).pos.getCenter().toString();
            }

            ItemLore im = new ItemLore(List.of(Utils.Chat("&fPos: " + pos)));
            is.set(DataComponents.LORE, im);
            is.set(CustomComponent.STRING_ID, id[1]);
            this.container.setItem(i - ((shopPageNum - 1) * 36), is);
        }
    }

    @Override
    public boolean clickMenuButton(Player player, int button)
    {
        //CreateShopDisplay.LOGGER.info("Clicked");
        return super.clickMenuButton(player, button);
    }

    @Override
    public void clicked(int slotIndex, int dragType, ClickType clickType, Player player)
    {

        ItemStack is = container.getItem(slotIndex);
        if (is.isEmpty()) return;
        String name = is.get(CustomComponent.STRING_ID);

        if (name.isEmpty()) return;
        if (player.getServer() == null) return;
        //CreateShopDisplay.LOGGER.info();
        if (name.equalsIgnoreCase("Next"))
        {
            if (inShop && (curShop.items.size() / 35) + 1  > shopPageNum)
            {
                shopPageNum++;
                displayShopPage(curShop);
            }
            else if (inShop == false && (ShopManager.stores.size() / 35) + 1  > pagenum) {
                pagenum++;
                displayPage(player.getServer());
            }
        }
        else if (name.equalsIgnoreCase("Previous"))
        {
            if (inShop && shopPageNum > 1)
            {
                shopPageNum--;
                displayShopPage(curShop);
            }
            else if (pagenum > 1) {
                pagenum--;
                displayPage(player.getServer());
            }
        }
        else if(name.equals("Exit"))
        {
            if (inShop == true)
            {
                inShop = false;
                curShop = null;
                displayPage(player.getServer());
            }
            else
            {
                this.container.clearContent();
                this.curShop = null;
                player.closeContainer();
            }
        }
        else {
            if (inShop == false)
            {
                shops shop = ShopManager.getShop(player.getServer().getPlayerList().getPlayerByName(this.container.getItem(slotIndex).get(DataComponents.ITEM_NAME).getString()).getUUID());
                if (shop == null)
                {
                    CreateShopDisplay.LOGGER.error("Error couldn't find shop");
                    return;
                }
                inShop = true;
                curShop = shop;
                displayShopPage(shop);
            }
        }
        return;
    }


}
