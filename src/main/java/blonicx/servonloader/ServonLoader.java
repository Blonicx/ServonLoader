package blonicx.servonloader;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ServonLoade extends JavaPlugin {

    private List<File> loadedPluginFiles;

    @Override
    public void onEnable() {
        this.loadedPluginFiles = new ArrayList<>();
        loadPlugins();
    }

    @Override
    public void onDisable() {
        unloadPlugins();
    }

    private void loadPlugins() {
        File pluginFolder = getDataFolder(); // Get the plugin folder
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs(); // Create the plugin folder if it doesn't exist
        }

        File[] files = pluginFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    try {
                        Plugin plugin = getServer().getPluginManager().loadPlugin(file);
                        if (plugin != null) {
                            getServer().getPluginManager().enablePlugin(plugin);
                            loadedPluginFiles.add(file);
                            getLogger().info("Loaded plugin: " + plugin.getName());
                        }
                    } catch (Exception e) {
                        getLogger().warning("Failed to load plugin from file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void unloadPlugins() {
        for (File file : loadedPluginFiles) {
            try {
                Plugin plugin = getServer().getPluginManager().getPlugin(file.getName());
                if (plugin != null) {
                    getServer().getPluginManager().disablePlugin(plugin);
                    getLogger().info("Unloaded plugin: " + plugin.getName());
                }
            } catch (Exception e) {
                getLogger().warning("Failed to unload plugin from file: " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
