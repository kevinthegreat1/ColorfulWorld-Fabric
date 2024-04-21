package com.kevinthegreat.colorfulworld.datagen;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyComponentsLootFunction;
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
        pack.addProvider(ColorfulWorldEnglishLanguageGenerator::new);
        pack.addProvider(ColorfulWorldTagGenerator::new);
    }

    private static class ColorfulWorldBlockStateModelGenerator extends FabricModelProvider {
        private ColorfulWorldBlockStateModelGenerator(FabricDataOutput output) {
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
        private ColorfulWorldBlockLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ColorfulWorld.COLORFUL_CONCRETE, this::colorfulDrops);
            addDrop(ColorfulWorld.COLORFUL_CONCRETE_POWDER, this::colorfulDrops);
            addDrop(ColorfulWorld.COLORFUL_GLASS, this::colorfulDropsWithSilkTouch);
            addDrop(ColorfulWorld.COLORFUL_GLASS_PANE, this::colorfulDropsWithSilkTouch);
        }

        private LootTable.Builder colorfulDrops(Block drop) {
            return LootTable.builder().pool(addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(drop).apply(CopyComponentsLootFunction.builder(CopyComponentsLootFunction.Source.BLOCK_ENTITY).include(ColorfulWorld.COLOR).include(DataComponentTypes.ITEM_NAME).include(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)))));
        }

        private LootTable.Builder colorfulDropsWithSilkTouch(Block drop) {
            return LootTable.builder().pool(addSurvivesExplosionCondition(drop, LootPool.builder().conditionally(WITH_SILK_TOUCH).rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(drop).apply(CopyComponentsLootFunction.builder(CopyComponentsLootFunction.Source.BLOCK_ENTITY).include(ColorfulWorld.COLOR).include(DataComponentTypes.ITEM_NAME).include(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)))));
        }
    }

    private static class ColorfulWorldEnglishLanguageGenerator extends FabricLanguageProvider {
        private ColorfulWorldEnglishLanguageGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
            translationBuilder.add(ColorfulWorld.COLORFUL_CONCRETE, "Colorful Concrete");
            translationBuilder.add(ColorfulWorld.COLORFUL_CONCRETE_POWDER, "Colorful Concrete Powder");
            translationBuilder.add(ColorfulWorld.COLORFUL_GLASS, "Colorful Glass");
            translationBuilder.add(ColorfulWorld.COLORFUL_GLASS_PANE, "Colorful Glass Pane");
        }
    }

    private static class ColorfulWorldTagGenerator extends FabricTagProvider<Block> {
        private ColorfulWorldTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BLOCK, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ColorfulWorld.COLORFUL_CONCRETE);
            getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(ColorfulWorld.COLORFUL_CONCRETE_POWDER);
            getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(ColorfulWorld.COLORFUL_GLASS);
        }
    }
}
