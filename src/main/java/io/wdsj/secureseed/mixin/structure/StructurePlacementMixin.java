package io.wdsj.secureseed.mixin.structure;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructurePlacement.class)
public abstract class StructurePlacementMixin {
    @Redirect(
            method = "probabilityReducer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;setLargeFeatureWithSalt(JIII)V")
    )
    private static void replaceRandom(WorldgenRandom instance, long l, int i, int j, int k) {
        // no-ops
    }

    @ModifyVariable(
            method = "probabilityReducer",
            at = @At(value = "STORE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;<init>(Lnet/minecraft/util/RandomSource;)V")
    )
    private static WorldgenRandom replaceRandomSource(WorldgenRandom instance, long l, int i, int j, int k) {
        return new WorldgenCryptoRandom(j, k, Globals.Salt.UNDEFINED, i);
    }

}
