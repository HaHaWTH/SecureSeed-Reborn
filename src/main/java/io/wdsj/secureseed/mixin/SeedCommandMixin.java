package io.wdsj.secureseed.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.wdsj.secureseed.crypto.Globals;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.commands.SeedCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = SeedCommand.class, priority = 999)
public abstract class SeedCommandMixin {
    /**
     * @author HaHaWTH
     * @reason Secure Seed Command
     */
    @Overwrite
    @SuppressWarnings("unchecked")
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher, boolean bl) {
        //noinspection rawtypes
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder) Commands.literal("seed").requires((commandSourceStack) -> !bl || commandSourceStack.hasPermission(2))).executes((commandContext) -> {
            long l = ((CommandSourceStack)commandContext.getSource()).getLevel().getSeed();
            Component component = ComponentUtils.copyOnClickText(String.valueOf(l));
            ((CommandSourceStack)commandContext.getSource()).sendSuccess(() -> Component.translatable("commands.seed.success", component), false);
            Globals.setupGlobals(((CommandSourceStack)commandContext.getSource()).getLevel());
            String seedStr = Globals.seedToString(Globals.worldSeed);
            Component featureSeedComponent = ComponentUtils.copyOnClickText(seedStr);

            ((CommandSourceStack)commandContext.getSource()).sendSuccess(() -> Component.translatable(("Feature seed: %s"), featureSeedComponent), false);
            return (int)l;
        }));
    }

}
