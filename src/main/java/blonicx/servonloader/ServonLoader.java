package blonicx.servonloader;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ServonLoader extends JavaPlugin {

    public File dataFolder = getDataFolder();

    @Override
    public void onEnable() {
        PluginManager.loadPlugins(dataFolder);
    }

    @Override
    public void onDisable() {
        PluginManager.unloadPlugins();
    }
}
