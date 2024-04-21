package com.kevinthegreat.colorfulworld.util;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.Block;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DebugCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, @SuppressWarnings("unused") CommandRegistryAccess registryAccess, @SuppressWarnings("unused") CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal(ColorfulWorld.MOD_ID).requires(source -> source.hasPermissionLevel(2))
                .then(literal("debugworld").executes(DebugCommands::execute))
                .then(literal("setcolorfulconcretepowder").then(argument("pos", BlockPosArgumentType.blockPos()).executes(context -> {
                    context.getSource().getWorld().setBlockState(BlockPosArgumentType.getLoadedBlockPos(context, "pos"), ColorfulWorld.COLORFUL_CONCRETE_POWDER.getDefaultState(), Block.NOTIFY_ALL, 0);
                    return Command.SINGLE_SUCCESS;
                })))
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerWorld world = context.getSource().getWorld();
        ServerCommandSource source = context.getSource();
        source.sendFeedback(() -> Text.of("Filling debug world, this may take a while!"), true);
        fillCircle(world, new BlockPos(0, 0, 0), 1);
        fillCircle(world, new BlockPos(512, 0, 0), 0.5F);
        source.sendFeedback(() -> Text.of("Filled debug world"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static void fillCircle(ServerWorld world, BlockPos center, float value) {
        BlockPos.Mutable blockPos = new BlockPos.Mutable(-256, 0, -256);
        BlockPos.Mutable actualPos = new BlockPos.Mutable();
        for (int i = 0; i < 512; i++) {
            for (int j = 0; j < 512; blockPos.move(0, 0, 1), j++) {
                float hypotSquared = (float) MathHelper.squaredHypot(blockPos.getX(), blockPos.getZ());
                if (hypotSquared > 65536) {
                    continue;
                }
                float hue = (float) MathHelper.atan2(blockPos.getZ(), blockPos.getX()) / MathHelper.TAU;
                int color = MathHelper.hsvToRgb(hue < 0 ? hue + 1 : hue, MathHelper.sqrt(hypotSquared) / 256, value);
                actualPos.set(blockPos).move(center);

                NbtCompound nbt = new NbtCompound();
                nbt.putInt(ColorfulWorld.COLOR_KEY, color);

                // Set colorful concrete
                world.setBlockState(actualPos, ColorfulWorld.COLORFUL_CONCRETE.getDefaultState(), Block.NOTIFY_ALL, 0);
                world.getBlockEntity(actualPos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readNbt(nbt, world.getRegistryManager()));

                // Set colorful glass
                actualPos.move(Direction.UP);
                world.setBlockState(actualPos, ColorfulWorld.COLORFUL_GLASS.getDefaultState(), Block.NOTIFY_ALL, 0);
                world.getBlockEntity(actualPos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readNbt(nbt, world.getRegistryManager()));
            }
            blockPos.move(1, 0, -512);
        }
    }
}
