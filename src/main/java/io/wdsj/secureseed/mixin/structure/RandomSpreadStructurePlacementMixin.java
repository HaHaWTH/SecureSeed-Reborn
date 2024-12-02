package io.wdsj.secureseed.mixin.structure;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import io.wdsj.secureseed.mixin.invokers.StructurePlacementInvoker;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RandomSpreadStructurePlacement.class)
public abstract class RandomSpreadStructurePlacementMixin {
    @Shadow @Final private int spacing;

    @Redirect(
            method = "getPotentialStructureChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;setLargeFeatureWithSalt(JIII)V")
    )
    public void swallowMethod(WorldgenRandom instance, long l, int i, int j, int k) {
        // no-ops
    }

    @ModifyVariable(
            method = "getPotentialStructureChunk",
            at = @At(value = "STORE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;<init>(Lnet/minecraft/util/RandomSource;)V")
    )
    private WorldgenRandom replaceRandom(WorldgenRandom instance, long l, int i, int j) {
        int k = Math.floorDiv(i, this.spacing);
        int m = Math.floorDiv(j, this.spacing);
        return new WorldgenCryptoRandom(k, m, Globals.Salt.POTENTIONAL_FEATURE, ((StructurePlacementInvoker)this).invokeSalt());
    }
}
