package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GeodeFeature.class)
public abstract class GeodeFeatureMixin {
    @ModifyVariable(
            method = "place",
            at = @At(value = "STORE", target = "Lnet/minecraft/world/level/levelgen/synth/NormalNoise;create(Lnet/minecraft/util/RandomSource;I[D)Lnet/minecraft/world/level/levelgen/synth/NormalNoise;")
    )
    public WorldgenRandom replace(WorldgenRandom instance) {
        return new WorldgenCryptoRandom(0, 0, Globals.Salt.GEODE_FEATURE, 0);
    }

}
