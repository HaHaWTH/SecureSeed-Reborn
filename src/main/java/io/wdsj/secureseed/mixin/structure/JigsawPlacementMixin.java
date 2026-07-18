package io.wdsj.secureseed.mixin.structure;

import com.llamalad7.mixinextras.sugar.Local;
import io.wdsj.secureseed.crypto.Globals;
import io.wdsj.secureseed.crypto.random.WorldgenCryptoRandom;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(JigsawPlacement.class)
public abstract class JigsawPlacementMixin {
    @ModifyVariable(
            method = "addPieces(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Lnet/minecraft/core/Holder;Ljava/util/Optional;ILnet/minecraft/core/BlockPos;ZLjava/util/Optional;Lnet/minecraft/world/level/levelgen/structure/structures/JigsawStructure$MaxDistance;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)Ljava/util/Optional;",
            at = @At(value = "STORE", ordinal = 0, target = "Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;random()Lnet/minecraft/world/level/levelgen/WorldgenRandom;"),
            name = "random")
    private static WorldgenRandom replaceRandom(WorldgenRandom random, @Local(argsOnly = true, name = "context") Structure.GenerationContext context) {
        return new WorldgenCryptoRandom(context.chunkPos().x(), context.chunkPos().z(), Globals.Salt.JIGSAW_PLACEMENT, 0);
    }
}
