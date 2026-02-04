package com.example.debugsticklock.mixin;

import com.example.debugsticklock.DebugStickLockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockState.class)
public class BlockStateMixin {
    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    private void debugsticklock$keepLockedShape(
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos,
            CallbackInfoReturnable<BlockState> cir
    ) {
        if (level instanceof ServerLevel serverLevel) {
            DebugStickLockState state = DebugStickLockState.get(serverLevel);
            if (state.isLocked(pos)) {
                cir.setReturnValue((BlockState) (Object) this);
            }
        }
    }
}
