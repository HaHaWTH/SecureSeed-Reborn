package io.wdsj.secureseed.mixin.invokers;

import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePlacement.class)
public interface StructurePlacementInvoker {
    @Invoker("salt")
    int invokeSalt();
}
