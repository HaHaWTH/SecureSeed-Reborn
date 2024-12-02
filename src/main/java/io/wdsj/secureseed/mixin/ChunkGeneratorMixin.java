package io.wdsj.secureseed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @ModifyVariable(
            method = "applyBiomeDecoration",
            at = @At(value = "STORE", ordinal = 0, target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;<init>(Lnet/minecraft/util/RandomSource;)V")
    )
    private WorldgenRandom replaceRandomDecoration(WorldgenRandom value, @Local BlockPos blockPos) {
        return new WorldgenCryptoRandom(blockPos.getX(), blockPos.getZ(), Globals.Salt.UNDEFINED, 0);
    }

    @ModifyVariable(
            method = "method_41041",
            at = @At(value = "STORE", ordinal = 0, target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;<init>(Lnet/minecraft/util/RandomSource;)V")
    )
    private WorldgenRandom replaceRandomStructures(WorldgenRandom value, @Local ChunkPos chunkPos) {
        return new WorldgenCryptoRandom(chunkPos.x, chunkPos.z, Globals.Salt.GENERATE_FEATURE, 0);
    }

    @Redirect(method = "method_41041",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;setLargeFeatureSeed(JII)V")
    )
    public void swallowMethod(WorldgenRandom instance, long l, int i, int j) {
        // no-op
    }
}
