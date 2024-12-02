package io.wdsj.secureseed.mixin.structure;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Structure.GenerationContext.class)
public abstract class StructureGenerationContextMixin {
    @Inject(
            method = "makeRandom"
            , at = @At("HEAD"),
            cancellable = true)
    private static void replaceRandom(long l, ChunkPos chunkPos, CallbackInfoReturnable<WorldgenRandom> cir) {
        cir.setReturnValue(new WorldgenCryptoRandom(chunkPos.x, chunkPos.z, Globals.Salt.GENERATE_FEATURE, l));
    }
}
