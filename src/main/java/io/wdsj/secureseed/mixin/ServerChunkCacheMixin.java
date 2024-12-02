package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.crypto.Globals;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin {

    @Shadow @Final
    ServerLevel level;

    @Inject(method = "getGenerator", at = @At("HEAD"))
    private void secureSeed_setupGlobals(CallbackInfoReturnable<ChunkGenerator> cir) {
        Globals.setupGlobals(level);
    }
}
