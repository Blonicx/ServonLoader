package blonicx.servonloader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    private ServonLoader plugin;
    public static List<File> loadedPluginFiles = new ArrayList<>();

    public PluginManager(ServonLoader plugin) {
        this.plugin = plugin;
        this.loadedPluginFiles = new ArrayList<>();
    }

    public static void loadPlugins(File pluginFolder) {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs(); // Create the plugin folder if it doesn't exist
        }

        File[] files = pluginFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    try {
                        Plugin plugin = Bukkit.getServer().getPluginManager().loadPlugin(file);
                        if (plugin != null) {
                            Bukkit.getServer().getPluginManager().enablePlugin(plugin);
                            loadedPluginFiles.add(file);
                            Bukkit.getLogger().info("Loaded plugin: " + plugin.getName());
                        }
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("Failed to load plugin from file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void unloadPlugins() {
        for (File file : loadedPluginFiles) {
            try {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(file.getName());
                if (plugin != null) {
                    Bukkit.getServer().getPluginManager().disablePlugin(plugin);
                    Bukkit.getLogger().info("Unloaded plugin: " + plugin.getName());
                }
            } catch (Exception e) {
                Bukkit.getLogger().warning("Failed to unload plugin from file: " + file.getName());
                e.printStackTrace();
            }
        }
    }
}