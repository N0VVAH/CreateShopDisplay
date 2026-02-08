package net.torchednova.CreateShopDisplay.shops;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Items {
    public Items(BlockPos pos, String sale, int saleCount, String cost, int costCount) {
        this.pos = pos;
        this.sale = sale;
        this.saleCount = saleCount;
        this.cost = cost;
        this.costCount = costCount;
    }
    public BlockPos pos;
    public String sale;
    public int saleCount;
    public String cost;
    public int costCount;
}
