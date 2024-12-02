package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.interfaces.IChunkAccessSlimeChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin implements IChunkAccessSlimeChunk {
    @Inject(
            method = "checkSlimeSpawnRules",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;seedSlimeChunk(IIJJ)Lnet/minecraft/util/RandomSource;"),
            cancellable = true)
    private static void check(EntityType<Slime> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(((IChunkAccessSlimeChunk)(Object)levelAccessor.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4)).secureSeed$isSlimeChunk());
    }
}
