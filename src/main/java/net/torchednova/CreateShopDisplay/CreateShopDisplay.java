package net.torchednova.CreateShopDisplay;


import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.torchednova.CreateShopDisplay.SaveData.TargetDataStorage;
import net.torchednova.CreateShopDisplay.commands.ViewShops;
import net.torchednova.CreateShopDisplay.shops.ShopManager;
import org.slf4j.Logger;


import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateShopDisplay.MODID)
public class CreateShopDisplay {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createshopdisplay";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateShopDisplay(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        CustomComponent.DATA_COMPONENTS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");


    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event)
    {
        ViewShops.register(event.getDispatcher());
    }


    @SubscribeEvent
    public void onPlaceBlock(BlockEvent.EntityPlaceEvent event)
    {

         TagKey<Block> TABLE_CLOTHS =
                TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("create", "table_cloths"));
        if (event.getPlacedBlock().is(TABLE_CLOTHS))
        {


            if ( ((TableClothBlockEntity)event.getLevel().getBlockEntity(event.getPos())) == null) return;
            if ( ((TableClothBlockEntity)event.getLevel().getBlockEntity(event.getPos())).getItemsForRender() == null) return;


            TableClothBlockEntity tbce = ((TableClothBlockEntity) event.getLevel().getBlockEntity(event.getPos()));


            assert tbce != null;
            String item = tbce.getItemsForRender().getFirst().getItemHolder().getKey().location().toString();
            int itemCount = tbce.getItemsForRender().getFirst().getCount();


            ShopManager.addItem(null, 0, item, itemCount, event.getPos(), event.getEntity().getUUID());
            TargetDataStorage.save(event.getLevel().getServer());

        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        TagKey<Block> TABLE_CLOTHS =
                TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("create", "table_cloths"));


        if (!event.getState().is(TABLE_CLOTHS))
        {
            return;
        }

        LOGGER.info(event.getPos().getX() + " | " + event.getPos().getY() + " | " + event.getPos().getZ());

        ShopManager.removeShop(event.getPos());

        TargetDataStorage.save(event.getLevel().getServer());

    }

    @SubscribeEvent
    public void explosionEvent(ExplosionEvent.Detonate event)
    {
        List<BlockPos> poss = event.getExplosion().getToBlow();
        for (int i = 0; i < poss.size(); i++) {
            ShopManager.removeShop(poss.get(i));
        }
        TargetDataStorage.save(event.getLevel().getServer());
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
        ShopManager.init();
        ShopManager.stores = TargetDataStorage.load(event.getServer());
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
        TargetDataStorage.save(event.getServer());
    }
}
