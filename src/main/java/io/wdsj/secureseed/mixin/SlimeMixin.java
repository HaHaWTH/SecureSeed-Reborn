package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.interfaces.IChunkAccessSlimeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Slime.class)
public abstract class SlimeMixin implements IChunkAccessSlimeChunk {
    @ModifyVariable(
            method = "checkSlimeSpawnRules",
            at = @At(value = "STORE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;seedSlimeChunk(IIJJ)Lnet/minecraft/util/RandomSource;")
    )
    private static boolean replace(boolean value, EntityType<Slime> entityType, LevelAccessor levelAccessor, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource randomSource) {
        return ((IChunkAccessSlimeChunk)(Object)levelAccessor.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4)).secureSeed$isSlimeChunk();
    }
}
