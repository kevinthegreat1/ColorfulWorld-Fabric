package com.kevinthegreat.colorfulconcrete.util;

import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FillDebugWorldCommand {
    public static int execute(CommandContext<ServerCommandSource> context) {
        ServerWorld world = context.getSource().getWorld();
        ServerCommandSource source = context.getSource();
        source.sendFeedback(Text.of("Filling debug world, this may take a while!"), true);
        fillCircle(world, new BlockPos(0, 0, 0), 1);
        fillCircle(world, new BlockPos(512, 0, 0), 0.5F);
        source.sendFeedback(Text.of("Filled debug world"), true);
        return 1;
    }

    private static void fillCircle(ServerWorld world, BlockPos center, float value) {
        BlockPos.Mutable blockPos = new BlockPos.Mutable(-256, 0, -256);
        for (int i = 0; i < 512; i++) {
            for (int j = 0; j < 512; j++) {
                float hypotSquared = ((float) MathHelper.squaredHypot(blockPos.getX(), blockPos.getZ()));
                if (hypotSquared > 65536) {
                    blockPos.move(0, 0, 1);
                    continue;
                }
                float hue = (float) (MathHelper.atan2(blockPos.getZ(), blockPos.getX()) / (2 * Math.PI));
                int color = MathHelper.hsvToRgb(hue < 0 ? hue + 1 : hue, MathHelper.sqrt(hypotSquared) / 256, value);
                world.setBlockState(blockPos.add(center), ColorfulConcrete.COLORFUL_CONCRETE.getDefaultState());
                NbtCompound nbt = new NbtCompound();
                nbt.putInt(ColorfulConcrete.COLOR_KEY, color);
                world.getBlockEntity(blockPos.add(center), ColorfulConcrete.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readNbt(nbt));
                blockPos.move(0, 0, 1);
            }
            blockPos.move(1, 0, -512);
        }
    }
}
