package com.example.debugsticklock.mixin;

import com.example.debugsticklock.DebugStickLockState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DebugStickItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugStickItem.class)
public class DebugStickItemMixin {
    @Inject(method = "useOn", at = @At("TAIL"))
    private void debugsticklock$markLocked(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!(context.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        InteractionResult result = cir.getReturnValue();
        if (result != null && result.consumesAction()) {
            DebugStickLockState.get(serverLevel).lock(context.getClickedPos());
        }
    }
}
