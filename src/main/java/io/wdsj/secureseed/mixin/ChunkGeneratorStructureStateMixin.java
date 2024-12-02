package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGeneratorStructureState.class)
public abstract class ChunkGeneratorStructureStateMixin {

    @Redirect(
            method = "generateRingPositions",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;create()Lnet/minecraft/util/RandomSource;")
    )
    public RandomSource replaceRandomRingPositions() {
        return new WorldgenCryptoRandom(0, 0, Globals.Salt.STRONGHOLDS, 0);
    }

    @Redirect(
            method = "generateRingPositions",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;setSeed(J)V"
            )
    )
    public void replaceRandomRingPositions(RandomSource instance, long seed) {
        // no-op
    }


}
