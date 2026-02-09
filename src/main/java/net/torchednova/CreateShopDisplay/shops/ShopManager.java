package net.torchednova.CreateShopDisplay.shops;


import net.minecraft.core.BlockPos;
import net.torchednova.CreateShopDisplay.CreateShopDisplay;

import java.util.ArrayList;
import java.util.UUID;

public class ShopManager {
    public static ArrayList<shops> stores;

    public static void init()
    {
        stores = new ArrayList<>();
    }

    public static void addItem(String cost, int costCount, String sale, int saleCount, BlockPos pos, UUID player)
    {
        shops s = getShop(player);
        if (s == null)
        {
            addShop(player);
            addItem(cost, costCount, sale, saleCount, pos, player);
            return;
        }

        s.addItem(sale, saleCount, cost, costCount, pos);
    }

    public static void addShop(UUID player)
    {
        stores.add(new shops(stores.size(), player));
    }

    public static shops getShop(UUID player)
    {
        for (int i = 0; i < stores.size(); i++)
        {
            if (stores.get(i).player.equals(player))
            {
                return stores.get(i);
            }
        }

        return null;
    }

    public static shops getShop(int id)
    {
        for (int i = 0; i < stores.size(); i++)
        {
            if (stores.get(i).id == id)
            {
                return stores.get(i);
            }
        }

        return null;
    }

    public static boolean shopExists(BlockPos pos)
    {
        for (int i = 0; i < stores.size(); i++)
        {
            for (int ii = 0; ii < stores.size(); ii++) {
                if (stores.get(i).items.get(ii).pos.equals(pos)) return true;
            }
        }

        return false;
    }

    public static void removeShop(BlockPos pos)
    {
        for (int i = 0; i < stores.size(); i++)
        {
            for (int ii = 0; ii < stores.get(i).items.size(); ii++) {
                if (stores.get(i).items.get(ii).pos.equals(pos)) {
                    stores.get(i).items.remove(ii);
                    return;
                }
            }
        }
    }





}
