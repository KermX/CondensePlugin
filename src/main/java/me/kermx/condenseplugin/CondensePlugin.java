package me.kermx.condenseplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class CondensePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("condense").setExecutor(new CondenseCommand());
        getCommand("condense").setTabCompleter(new CondenseCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
