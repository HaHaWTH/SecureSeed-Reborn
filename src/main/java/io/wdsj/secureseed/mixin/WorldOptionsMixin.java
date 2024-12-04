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
    private static Gson gson = new Gson();

    @Shadow
    public static final MapCodec<WorldOptions> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.LONG.fieldOf("seed").stable().forGetter(WorldOptions::seed),
                            Codec.STRING.fieldOf("feature_seed").orElse(gson.toJson(Globals.createRandomWorldSeed())).stable().forGetter(worldOptions -> {
                                return ((IWorldOptionsFeatureSeed)(Object)worldOptions).featureSeedSerialize();
                            }),
                            Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(WorldOptions::generateStructures),
                            Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(WorldOptions::generateBonusChest),
                            Codec.STRING.lenientOptionalFieldOf("legacy_custom_options").stable().forGetter(worldOptions -> worldOptions.legacyCustomOptions)
                    )
                    .apply(instance, instance.stable((seed, featureSeed, generateStructures, generateBonusChest, legacyCustomOptions) -> {
                        var worldOptions = new WorldOptions(seed, generateStructures, generateBonusChest, legacyCustomOptions);
                        ((IWorldOptionsFeatureSeed)(Object)worldOptions).setFeatureSeed(gson.fromJson(featureSeed, long[].class));
                        return worldOptions;
                    }))
    );


    @Inject(
            method = "defaultWithRandomSeed"
            , at = @At("RETURN")
    )
    private static void setup(CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed)(Object)worldOptions).setFeatureSeed(Globals.createRandomWorldSeed());
    }

    @Inject(
            method = "withBonusChest"
            , at = @At("RETURN")
    )
    private void setupBonusChest(boolean bl, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed)(Object)worldOptions2).setFeatureSeed(this.featureSeed);
    }

    @Inject(
            method = "withStructures"
            , at = @At("RETURN")
    )
    private void setupStructures(boolean bl, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions2).setFeatureSeed(this.featureSeed);
    }

    @Inject(
            method = "withSeed"
            , at = @At("RETURN")
    )
    private void setupSeed(OptionalLong optionalLong, CallbackInfoReturnable<WorldOptions> cir) {
        var worldOptions2 = cir.getReturnValue();
        ((IWorldOptionsFeatureSeed) worldOptions2).setFeatureSeed(Globals.createRandomWorldSeed());
    }

    @Unique
    private long[] featureSeed = Globals.createRandomWorldSeed();
    @Unique
    @Override
    public long[] featureSeed() {
        return this.featureSeed;
    }

    @Unique
    @Override
    public void setFeatureSeed(long[] seed) {
        this.featureSeed = seed;
    }

    @Unique
    @Override
    public String featureSeedSerialize() {
        return gson.toJson(this.featureSeed);
    }
}
