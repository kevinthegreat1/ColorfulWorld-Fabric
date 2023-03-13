package com.kevinthegreat.colorfulworld.datagen;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ColorfulWorldDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();
        pack.addProvider(ColorfulWorldBlockStateModelGenerator::new);
        pack.addProvider(ColorfulWorldBlockLootTableGenerator::new);
        pack.addProvider(ColorfulWorldTagGenerator::new);
    }

    private static class ColorfulWorldBlockStateModelGenerator extends FabricModelProvider {
        public ColorfulWorldBlockStateModelGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(ColorfulWorld.COLORFUL_CONCRETE);
            blockStateModelGenerator.registerSimpleCubeAll(ColorfulWorld.COLORFUL_CONCRETE_POWDER);
            blockStateModelGenerator.registerGlassPane(ColorfulWorld.COLORFUL_GLASS, ColorfulWorld.COLORFUL_GLASS_PANE);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        }
    }

    private static class ColorfulWorldBlockLootTableGenerator extends FabricBlockLootTableProvider {
        public ColorfulWorldBlockLootTableGenerator(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ColorfulWorld.COLORFUL_CONCRETE, this::colorfulDrops);
            addDrop(ColorfulWorld.COLORFUL_CONCRETE_POWDER, this::colorfulDrops);
            addDrop(ColorfulWorld.COLORFUL_GLASS, this::colorfulDrops);
            addDrop(ColorfulWorld.COLORFUL_GLASS_PANE, this::colorfulDrops);
        }

        private LootTable.Builder colorfulDrops(Block drop) {
            return LootTable.builder().pool(addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(drop).apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY).withOperation("id", "BlockEntityTag.id").withOperation("Color", "BlockEntityTag.Color")))));
        }
    }

    private static class ColorfulWorldTagGenerator extends FabricTagProvider<Block> {
        public ColorfulWorldTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BLOCK, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(ColorfulWorld.COLORFUL_GLASS);
        }
    }
}
