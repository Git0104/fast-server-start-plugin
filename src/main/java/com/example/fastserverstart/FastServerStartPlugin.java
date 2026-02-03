package com.example.fastserverstart;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class FastServerStartPlugin extends JavaPlugin {
    private static final String CONFIG_PATH = "startup";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigurationSection config = getConfig().getConfigurationSection(CONFIG_PATH);
        if (config == null) {
            getLogger().warning("Missing '" + CONFIG_PATH + "' section in config.yml.");
            return;
        }

        boolean disableSpawnKeepLoaded = config.getBoolean("disable-spawn-keep-loaded", true);
        int deferredDelayTicks = config.getInt("deferred-keep-spawn-delay-ticks", 20 * 5);
        boolean reenableSpawnAfterDelay = config.getBoolean("reenable-keep-spawn-after-delay", false);

        if (disableSpawnKeepLoaded) {
            List<World> worlds = Bukkit.getWorlds();
            for (World world : worlds) {
                world.setKeepSpawnInMemory(false);
                getLogger().info("Disabled keep-spawn-in-memory for world: " + world.getName());
            }
        }

        if (reenableSpawnAfterDelay) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                for (World world : Bukkit.getWorlds()) {
                    world.setKeepSpawnInMemory(true);
                    getLogger().info("Re-enabled keep-spawn-in-memory for world: " + world.getName());
                }
            }, Math.max(1, deferredDelayTicks));
        }
    }
}
