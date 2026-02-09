package net.torchednova.CreateShopDisplay.mixin;

import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.torchednova.CreateShopDisplay.CreateShopDisplay;
import net.torchednova.CreateShopDisplay.SaveData.TargetDataStorage;
import net.torchednova.CreateShopDisplay.shops.ShopManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlacementOffset.class)
public class CreatePlacementMixin {

    @Shadow
    private Vec3i pos;

    @Inject(method = "placeInWorld", at = @At("TAIL"), cancellable = true)
    private void onCreateSmartPlace(Level world, BlockItem blockItem, Player player, InteractionHand hand, BlockHitResult ray, CallbackInfoReturnable<ItemInteractionResult> cir)
    {

        BlockState bs = blockItem.getBlock().getStateDefinition().getPossibleStates().getFirst();
        TagKey<Block> TABLE_CLOTHS =
                TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("create", "table_cloths"));
        if (bs.is(TABLE_CLOTHS))
        {
            CreateShopDisplay.LOGGER.info("here");

            if ( (world.getBlockEntity((BlockPos) this.pos)) == null) return;
            if ( ((TableClothBlockEntity)world.getBlockEntity((BlockPos) this.pos)).getItemsForRender() == null) return;

            CreateShopDisplay.LOGGER.info("here2");


            TableClothBlockEntity tbce = ((TableClothBlockEntity) world.getBlockEntity((BlockPos) this.pos));

            CreateShopDisplay.LOGGER.info("here3");

            assert tbce != null;
            String item = tbce.getItemsForRender().getFirst().getItemHolder().getKey().location().toString();
            int itemCount = tbce.getItemsForRender().getFirst().getCount();
            CreateShopDisplay.LOGGER.info("here4");


            ShopManager.addItem(null, 0, item, itemCount, (BlockPos) this.pos, player.getUUID());
            TargetDataStorage.save(world.getServer());
        }
    }

}
