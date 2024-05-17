package me.kermx.condenseplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CondensePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("condense")).setExecutor(new CondenseCommand());
        Objects.requireNonNull(getCommand("condense")).setTabCompleter(new CondenseCommand());

        Objects.requireNonNull(getCommand("uncondense")).setExecutor(new UncondenseCommand());
        Objects.requireNonNull(getCommand("uncondense")).setTabCompleter(new UncondenseCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
