package github.io.dungeons;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.utils.bossbar.BossBarColor;
import io.lumine.mythic.bukkit.utils.bossbar.BossBarStyle;
import io.lumine.mythic.core.drops.droppables.ExperienceDrop;
import io.lumine.mythic.core.drops.droppables.ItemDrop;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static github.io.dungeons.Dungeons.plugin;
import static github.io.dungeons.Dungeons.prefix;
import static org.bukkit.Bukkit.getBossBar;
import static org.bukkit.Bukkit.getServer;

public class Listeners implements Listener {
    // mythicmobs death eventでmobの名前を取得して、そのmobのドロップアイテムを取得して、そのアイテムをプレイヤーに与える
    @EventHandler
    public void MythicMobDeathEvent(MythicMobDeathEvent e) {
        Entity entity = e.getEntity();
        Player player = (Player) e.getKiller();
        ActiveMob mob = e.getMob();
        String mobname = mob.getType().getInternalName();
        MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(mobname).get();
        String mobDisplayName = String.valueOf(mythicMob.getDisplayName());
    }

    // mobが倒された時に、経験値を取得する
    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent e) {
        Location location = e.getEntity().getLocation();
        Entity entity = e.getEntity();
        Player player = e.getEntity().getKiller();
        plugin.getLogger().info("mobの位置を取得しました" + location);
        // mobが倒された時、mobがどれかのアリーナにいるかを判定する
        // arenaディレクトリの中にあるアリーナの数だけ繰り返す
        for (File file : new File(plugin.getDataFolder() + "/arena").listFiles()) {
            // .DS_Storeを除外する
            if (file.getName().equals(".DS_Store")) {
                continue;
            }
            FileConfiguration arena = YamlConfiguration.loadConfiguration(file);
            String arenaName = file.getName().replace(".yml", "");
            String arenapos1 = arena.getString("pos1").replace("[", "").replace("]", "");
            String arenapos2 = arena.getString("pos2").replace("[", "").replace("]", "");
            plugin.getLogger().info("アリーナの名前を取得しました" + arenaName);
            plugin.getLogger().info("アリーナのpos1を取得しました" + arenapos1);
            plugin.getLogger().info("アリーナのpos2を取得しました" + arenapos2);
            // arenapos1とarenapos2をそれぞれLocationに変換する
            Location pos1Location = new Location(player.getWorld(), 0, 0, 0);
            Location pos2Location = new Location(player.getWorld(), 0, 0, 0);
            String[] pos1 = arenapos1.split(",");
            String[] pos2 = arenapos2.split(",");
            pos1Location.setX(Double.parseDouble(pos1[0]));
            pos1Location.setY(Double.parseDouble(pos1[1]));
            pos1Location.setZ(Double.parseDouble(pos1[2]));
            pos2Location.setX(Double.parseDouble(pos2[0]));
            pos2Location.setY(Double.parseDouble(pos2[1]));
            pos2Location.setZ(Double.parseDouble(pos2[2]));
            plugin.getLogger().info("pos1をLocationに変換しました" + pos1Location);
            plugin.getLogger().info("pos2をLocationに変換しました" + pos2Location);
            // pos1とpos2を結ぶ長方形の中にmobがいるかを判定する
            plugin.getLogger().info("mobのX座標:" + entity.getLocation().getBlockX() + "がpos1のX座標:" + pos1Location.getBlockX() + "とpos2のX座標:" + pos2Location.getBlockX() + "の間にあるかを判定します");
            if (pos1Location.getBlockX() <= entity.getLocation().getBlockX() && entity.getLocation().getBlockX() <= pos2Location.getBlockX() || pos1Location.getBlockX() >= entity.getLocation().getBlockX() && entity.getLocation().getBlockX() >= pos2Location.getBlockX()) {
                plugin.getLogger().info("mobがアリーナの中にいるかを判定しました(X)");
                plugin.getLogger().info("mobのY座標:" + entity.getLocation().getBlockY() + "がpos1のY座標:" + pos1Location.getBlockY() + "とpos2のY座標:" + pos2Location.getBlockY() + "の間にあるかを判定します");
                if (pos1Location.getBlockY() >= entity.getLocation().getBlockY() && entity.getLocation().getBlockY() >= pos2Location.getBlockY() || pos1Location.getBlockY() <= entity.getLocation().getBlockY() && entity.getLocation().getBlockY() <= pos2Location.getBlockY()) {
                    plugin.getLogger().info("mobがアリーナの中にいるかを判定しました(Y)");
                    plugin.getLogger().info("mobのZ座標:" + entity.getLocation().getBlockZ() + "がpos1のZ座標:" + pos1Location.getBlockZ() + "とpos2のZ座標:" + pos2Location.getBlockZ() + "の間にあるかを判定します");
                    if (pos1Location.getBlockZ() >= entity.getLocation().getBlockZ() && entity.getLocation().getBlockZ() >= pos2Location.getBlockZ() || pos1Location.getBlockZ() <= entity.getLocation().getBlockZ() && entity.getLocation().getBlockZ() <= pos2Location.getBlockZ()) {
                        plugin.getLogger().info("mobがアリーナの中にいるかを判定しました(Z)");
                        // mobがアリーナの中にいる場合、mobの名前を取得する
                        String mobName = entity.getName();
                        // mobの名前を取得したら、mobの名前をアリーナの名前と一致するかを判定する
                        int exp = e.getDroppedExp();
                        player.sendMessage(prefix + "§a" + player.getName() + "§fは" + arenaName + "§r内で§a" + entity.getName() + "§fを倒して§a" + exp + "§fの経験値を獲得した");
                        // 経験値ボスバーを表示する
                        // プレイヤーのdataディレクトリにあるプレイヤーのデータファイルを取得する
                        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/" + player.getUniqueId() + ".yml"));
                        // levelとexpを取得する
                        int playerLevel = data.getInt("level");
                        int playerExp = data.getInt("point");
                        plugin.getLogger().info("プレイヤーのレベルを取得しました" + playerLevel);
                        plugin.getLogger().info("プレイヤーの経験値を取得しました" + playerExp);
                        // expを加算する
                        playerExp = playerExp + exp;
                        plugin.getLogger().info("プレイヤーの経験値を加算しました" + playerExp);
                        // config.ymlのlevel.<プレイヤーのレベル>を取得する
                        String playernextLevel = String.valueOf(playerLevel + 1);
                        int level = plugin.getConfig().getInt("level." + playernextLevel);
                        plugin.getLogger().info(String.valueOf(level));
                        // playerExpがconfigのlevel.<レベルに必要な経験値>より大きい場合、レベルを上げる
                        if (playerExp >= level) {
                            playerLevel = playerLevel + 1;
                            player.sendMessage(prefix + "§aレベルが上がりました。現在のレベルは§e" + playerLevel + "§aです。");
                        }
                        // levelとexpを保存する
                        data.set("level", playerLevel);
                        data.set("point", playerExp);
                        try {
                            data.save(new File(plugin.getDataFolder() + "/data/" + player.getUniqueId() + ".yml"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // bossbarを更新する
                        // bossbarが表示されていない場合
                        plugin.getLogger().info("bossbarが表示されていないかを判定します");
                        int finalPlayerLevel = playerLevel;
                        int finalPlayerExp = playerExp;
                        player.getServer().getScheduler().runTask(plugin, () -> {
                            if (!player.getScoreboardTags().contains("bossbar")) {
                                plugin.getLogger().info("bossbarが表示されていないことを確認しました");
                                // bossbarを表示する
                                BossBar bossBar = Bukkit.createBossBar("§a§lレベル: §e§l" + finalPlayerLevel + "§a§l経験値: §e§l" + finalPlayerExp + "§f§l/§a§l" + plugin.getConfig().getInt("level." + playernextLevel), BarColor.GREEN, BarStyle.SEGMENTED_10);
                                bossBar.addPlayer(player);
                                player.addScoreboardTag("bossbar");
                                // 1秒ごとにbossbarを更新する
                                Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        // config.ymlのlevel.<プレイヤーのレベル>を取得する
                                        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/data/" + player.getUniqueId() + ".yml"));
                                        int playerLevel = data.getInt("level");
                                        int playerExp = data.getInt("point");
                                        String playernextLevel = String.valueOf(playerLevel + 1);
                                        int level = plugin.getConfig().getInt("level." + playernextLevel);
                                        // bossbarを更新する
                                        bossBar.setTitle("§a§lレベル: §e§l" + finalPlayerLevel + "§a§l経験値: §e§l" + finalPlayerExp + "§f§l/§a§l" + plugin.getConfig().getInt("level." + playernextLevel));
                                        bossBar.setProgress((double) finalPlayerExp / plugin.getConfig().getInt("level." + playernextLevel));
                                    }
                                }, 0, 20);
                            }
                        });
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.sendMessage(prefix + "§a§lDungeonsをご利用いただきありがとうございます。");
        player.sendMessage(prefix + "§a§lDungeonsのコマンドは§e§l/dungeons help§a§lで確認できます。");
        File dataFile = new File("plugins/Dungeons/data/" + player.getUniqueId() + ".yml");
        YamlConfiguration data = new YamlConfiguration();
        // ファイルが存在しない場合は作成する
        // 先にdataディレクトリを作成する
        File dataDir = new File("plugins/Dungeons/data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                data.set("level", 1);
                data.set("point", 0);
                data.set("kill", 0);
                data.save(dataFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
