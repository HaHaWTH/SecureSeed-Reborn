package io.wdsj.secureseed.mixin;

import com.google.gson.Gson;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.interfaces.IWorldOptionsFeatureSeed;
import net.minecraft.world.level.levelgen.WorldOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalLong;

@Mixin(WorldOptions.class)
public abstract class WorldOptionsMixin implements IWorldOptionsFeatureSeed {
    @Unique
    private static Gson secureSeed$gson = new Gson();

    @Shadow
    public static final MapCodec<WorldOptions> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.LONG.fieldOf("seed").stable().forGetter(WorldOptions::seed),
                            Codec.STRING.fieldOf("feature_seed").orElse(secureSeed$gson.toJson(Globals.createRandomWorldSeed())).stable().forGetter(worldOptions -> {
                                return ((IWorldOptionsFeatureSeed) worldOptions).secureSeed$featureSeedSerialize();
                            }),
                            Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(WorldOptions::generateStructures),
                            Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(WorldOptions::generateBonusChest),
                            Codec.STRING.lenientOptionalFieldOf("legacy_custom_options").stable().forGetter(worldOptions -> worldOptions.legacyCustomOptions)
                    )
                    .apply(instance, instance.stable((seed, featureSeed, generateStructures, generateBonusChest, legacyCustomOptions) -> {
                        var worldOptions = new WorldOptions(seed, generateStructures, generateBonusChest, legacyCustomOptions);
                        ((IWorldOptionsFeatureSeed) worldOptions).secureSeed$setFeatureSeed(secureSeed$gson.fromJson(featureSeed, long[].class));
                        return worldOptions;
                    }))
    );


    @Inject(
            method = "defaultWithRandomSeed"
            , at = @At("RETURN")
    )
    private static void setup(CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions).secureSeed$setFeatureSeed(Globals.createRandomWorldSeed());
    }

    @Inject(
            method = "withBonusChest"
            , at = @At("RETURN")
    )
    private void setupBonusChest(boolean bl, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions2).secureSeed$setFeatureSeed(this.secureSeed$featureSeed);
    }

    @Inject(
            method = "withStructures"
            , at = @At("RETURN")
    )
    private void setupStructures(boolean bl, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions2).secureSeed$setFeatureSeed(this.secureSeed$featureSeed);
    }

    @Inject(
            method = "withSeed"
            , at = @At("RETURN")
    )
    private void setupSeed(OptionalLong optionalLong, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions2).secureSeed$setFeatureSeed(Globals.createRandomWorldSeed());
    }

    @Unique
    private long[] secureSeed$featureSeed = Globals.createRandomWorldSeed();
    @Unique
    @Override
    public long[] secureSeed$featureSeed() {
        return this.secureSeed$featureSeed;
    }

    @Unique
    @Override
    public void secureSeed$setFeatureSeed(long[] seed) {
        this.secureSeed$featureSeed = seed;
    }

    @Unique
    @Override
    public String secureSeed$featureSeedSerialize() {
        return secureSeed$gson.toJson(this.secureSeed$featureSeed);
    }
}
