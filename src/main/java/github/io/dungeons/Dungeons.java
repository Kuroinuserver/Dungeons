package github.io.dungeons;

import github.io.dungeons.Commands.dungeons;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dungeons extends JavaPlugin {
    public static JavaPlugin plugin;
    private Listeners listeners;
    public static String prefix = "§6§l[§e§lDungeons§6§l]§r";

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        String message = getConfig().getString("language");
        // languageフォルダからlanguageで指定された言語のファイルを読み込む
        FileConfiguration language = getConfig();
        // languageフォルダが存在しない場合は作成する
        if (!getDataFolder().exists()) {
            // languageフォルダを作成する
            getDataFolder().mkdir();
            // languageフォルダにja_JP.ymlを作成する
            saveResource("ja_JP.yml", false);
            // languageフォルダにen_US.ymlを作成する
            saveResource("en_US.yml", false);
        }
        if (message == null) {
            // languageが指定されていない場合は英語を読み込む
            language = getConfig();
        }
        String start_message = language.getString("start_message");
        String commandclass = language.getString("commandclass");
        getLogger().info(start_message);
        try {
            getCommand("dungeons").setExecutor(new dungeons());
            getLogger().info(commandclass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            this.listeners = new Listeners();
        } catch (Exception e) {
            getLogger().severe("Listenersのインスタンス化に失敗しました。");
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(this.listeners, this);
        plugin.saveDefaultConfig();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
