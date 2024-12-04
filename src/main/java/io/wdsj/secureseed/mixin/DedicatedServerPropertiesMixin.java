package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.interfaces.IWorldOptionsFeatureSeed;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.world.level.levelgen.WorldOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Properties;

@Mixin(DedicatedServerProperties.class)
public abstract class DedicatedServerPropertiesMixin {

    @Shadow @Final public WorldOptions worldOptions;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL", target = "Lnet/minecraft/world/level/levelgen/WorldOptions;<init>(JZZ)V")
    )
    public void replaceWorldOptions(Properties properties, CallbackInfo ci) {
        String featureSeedStr = ((DedicatedServerProperties)(Object)this).get("feature-level-seed", "");
        long[] featureSeed = Globals.parseSeed(featureSeedStr)
                .orElse(Globals.createRandomWorldSeed());
        ((IWorldOptionsFeatureSeed) worldOptions).secureSeed$setFeatureSeed(featureSeed);
    }
}
