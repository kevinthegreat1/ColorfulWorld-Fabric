package com.kevinthegreat.colorfulworld;

import com.kevinthegreat.colorfulworld.block.ColorfulConcreteBlock;
import com.kevinthegreat.colorfulworld.block.ColorfulConcretePowderBlock;
import com.kevinthegreat.colorfulworld.block.ColorfulGlassBlock;
import com.kevinthegreat.colorfulworld.block.ColorfulGlassPaneBlock;
import com.kevinthegreat.colorfulworld.block.entity.ColorfulBlockEntity;
import com.kevinthegreat.colorfulworld.item.ColorfulItem;
import com.kevinthegreat.colorfulworld.util.DebugCommands;
import com.kevinthegreat.colorfulworld.util.MapColorGetter;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.component.DataComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulWorld implements ModInitializer {
    public static final String MOD_ID = "colorfulworld";
    public static final String MOD_NAME = "Colorful World";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String COLOR_KEY = "Color";
    public static final DataComponentType<Integer> COLOR = Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD_ID, "color"), DataComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER).build());
    public static final Block COLORFUL_CONCRETE = Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "colorful_concrete"), new ColorfulConcreteBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(Instrument.BASEDRUM).requiresTool().strength(1.8F)));
    public static final Block COLORFUL_CONCRETE_POWDER = Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "colorful_concrete_powder"), new ColorfulConcretePowderBlock(COLORFUL_CONCRETE, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(Instrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block COLORFUL_GLASS = Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "colorful_glass"), new ColorfulGlassBlock(AbstractBlock.Settings.create().mapColor(MapColor.BLACK).instrument(Instrument.HAT).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never)));
    public static final Block COLORFUL_GLASS_PANE = Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "colorful_glass_pane"), new ColorfulGlassPaneBlock(AbstractBlock.Settings.create().instrument(Instrument.HAT).strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque()));
    public static final BlockEntityType<ColorfulBlockEntity> COLORFUL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "colorful_block_entity"), BlockEntityType.Builder.create(ColorfulBlockEntity::new, COLORFUL_CONCRETE, COLORFUL_CONCRETE_POWDER, COLORFUL_GLASS, COLORFUL_GLASS_PANE).build());
    public static final Item COLORFUL_CONCRETE_ITEM = Registry.register(Registries.ITEM, Registries.BLOCK.getId(COLORFUL_CONCRETE), new ColorfulItem(COLORFUL_CONCRETE));
    public static final Item COLORFUL_CONCRETE_POWDER_ITEM = Registry.register(Registries.ITEM, Registries.BLOCK.getId(COLORFUL_CONCRETE_POWDER), new ColorfulItem(COLORFUL_CONCRETE_POWDER));
    public static final Item COLORFUL_GLASS_ITEM = Registry.register(Registries.ITEM, Registries.BLOCK.getId(COLORFUL_GLASS), new ColorfulItem(COLORFUL_GLASS));
    public static final Item COLORFUL_GLASS_PANE_ITEM = Registry.register(Registries.ITEM, Registries.BLOCK.getId(COLORFUL_GLASS_PANE), new ColorfulItem(COLORFUL_GLASS_PANE));
    public static final MapColorGetter MAP_COLOR_GETTER = new MapColorGetter();

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
            entries.addAfter(Items.PINK_CONCRETE, COLORFUL_CONCRETE_ITEM);
            entries.addAfter(Items.PINK_CONCRETE_POWDER, COLORFUL_CONCRETE_POWDER_ITEM);
            entries.addAfter(Items.PINK_STAINED_GLASS, COLORFUL_GLASS_ITEM);
            entries.addAfter(Items.PINK_STAINED_GLASS_PANE, COLORFUL_GLASS_PANE_ITEM);
        });
        CommandRegistrationCallback.EVENT.register(DebugCommands::register);
        LOGGER.info(MOD_NAME + " initialized.");
    }
}
