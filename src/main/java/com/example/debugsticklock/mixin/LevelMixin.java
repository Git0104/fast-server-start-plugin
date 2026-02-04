package com.example.debugsticklock.mixin;

import com.example.debugsticklock.DebugStickLockState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {
    @Inject(method = "setBlock", at = @At("HEAD"))
    private void debugsticklock$clearLockOnReplace(BlockPos pos, BlockState newState, int flags, CallbackInfoReturnable<Boolean> cir) {
        Level level = (Level) (Object) this;
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockState oldState = level.getBlockState(pos);
        if (oldState.getBlock() != newState.getBlock() || newState.isAir()) {
            DebugStickLockState.get(serverLevel).unlock(pos);
        }
    }
}
