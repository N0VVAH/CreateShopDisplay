package net.torchednova.CreateShopDisplay;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.mojang.serialization.Codec;

public class CustomComponent {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, CreateShopDisplay.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STRING_ID =
            DATA_COMPONENTS.register("string_id",
                    () -> DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .build()
            );
}
