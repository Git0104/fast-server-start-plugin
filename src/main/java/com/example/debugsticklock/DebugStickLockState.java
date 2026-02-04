package com.example.debugsticklock;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class DebugStickLockState extends SavedData {
    private static final String DATA_NAME = "debugsticklock";
    private static final String LOCKED_KEY = "locked";

    private final LongSet lockedPositions = new LongOpenHashSet();

    public boolean isLocked(BlockPos pos) {
        return lockedPositions.contains(pos.asLong());
    }

    public void lock(BlockPos pos) {
        if (lockedPositions.add(pos.asLong())) {
            setDirty();
        }
    }

    public void unlock(BlockPos pos) {
        if (lockedPositions.remove(pos.asLong())) {
            setDirty();
        }
    }

    public static DebugStickLockState get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(DebugStickLockState::load, DebugStickLockState::new, DATA_NAME);
    }

    public static DebugStickLockState load(CompoundTag tag) {
        DebugStickLockState state = new DebugStickLockState();
        long[] data = tag.getLongArray(LOCKED_KEY);
        for (long value : data) {
            state.lockedPositions.add(value);
        }
        return state;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putLongArray(LOCKED_KEY, lockedPositions.toLongArray());
        return tag;
    }
}
