package io.wdsj.secureseed.mixin;

import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import io.wdsj.secureseed.interfaces.IChunkAccessSlimeChunk;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkAccess.class)
public abstract class ChunkAccessMixin implements IChunkAccessSlimeChunk {
    @Shadow @Final protected ChunkPos chunkPos;
    @Unique
    private boolean slimeChunk, hasComputedSlimeChunk;

    @Override
    @Unique
    public boolean secureSeed$isSlimeChunk() {
        if (!hasComputedSlimeChunk) {
            hasComputedSlimeChunk = true;
            slimeChunk = WorldgenCryptoRandom.seedSlimeChunk(chunkPos.x, chunkPos.z).nextInt(10) == 0;
        }

        return slimeChunk;
    }
}
