package net.torchednova.CreateShopDisplay.shops;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.UUID;

public class shops {
    public shops(int id, UUID player)
    {
        this.id = id;
        this.player = player;
        this.items = new ArrayList<>();;
    }

    public void addItem(String sale, int saleCount, String cost, int costCount, BlockPos pos)
    {
        if (itemExist(pos)) {
            updateItem(sale, saleCount, cost, costCount, pos);
            return;
        }
        items.add(new Items(pos, sale, saleCount, cost, costCount));
    }

    public void updateItem(String sale, int saleCount, String cost, int costCount, BlockPos pos)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).pos == pos)
            {
                items.get(i).sale = sale;
                items.get(i).saleCount = saleCount;
                items.get(i).cost = cost;
                items.get(i).costCount = costCount;
            }
        }
    }


    public boolean itemExist(BlockPos pos)
    {
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).pos == pos)
            {
                return true;
            }
        }

        return false;
    }



    public int id;
    public UUID player;
    public ArrayList<Items> items;

}
