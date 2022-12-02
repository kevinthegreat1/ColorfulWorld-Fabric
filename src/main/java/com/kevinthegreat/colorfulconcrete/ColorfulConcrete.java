package com.kevinthegreat.colorfulconcrete;

import com.kevinthegreat.colorfulconcrete.block.ColorfulConcreteBlock;
import com.kevinthegreat.colorfulconcrete.block.ColorfulConcretePowderBlock;
import com.kevinthegreat.colorfulconcrete.block.entity.ColorfulBlockEntity;
import com.kevinthegreat.colorfulconcrete.item.ColorfulItem;
import com.kevinthegreat.colorfulconcrete.util.DebugCommands;
import com.kevinthegreat.colorfulconcrete.util.MapColorGetter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulConcrete implements ModInitializer {
    public static final String MOD_ID = "colorfulconcrete";
    public static final String MOD_NAME = "Colorful Concrete";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String COLOR_KEY = "Color";
    public static final Block COLORFUL_CONCRETE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colorful_concrete"), new ColorfulConcreteBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.8F)));
    public static final Block COLORFUL_CONCRETE_POWDER = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "colorful_concrete_powder"), new ColorfulConcretePowderBlock(COLORFUL_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, MapColor.BLACK).strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final BlockEntityType<ColorfulBlockEntity> COLORFUL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "colorful_block_entity"), FabricBlockEntityTypeBuilder.create(ColorfulBlockEntity::new, COLORFUL_CONCRETE, COLORFUL_CONCRETE_POWDER).build());
    public static final Item COLORFUL_CONCRETE_ITEM = Registry.register(Registry.ITEM, Registry.BLOCK.getId(COLORFUL_CONCRETE), new ColorfulItem(COLORFUL_CONCRETE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item COLORFUL_CONCRETE_POWDER_ITEM = Registry.register(Registry.ITEM, Registry.BLOCK.getId(COLORFUL_CONCRETE_POWDER), new ColorfulItem(COLORFUL_CONCRETE_POWDER, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final MapColorGetter mapColorGetter = new MapColorGetter();

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_NAME + " initialized.");
        CommandRegistrationCallback.EVENT.register(DebugCommands::register);
    }
}
