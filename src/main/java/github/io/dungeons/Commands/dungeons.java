package github.io.dungeons.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static github.io.dungeons.Dungeons.plugin;
import static github.io.dungeons.Dungeons.prefix;

public class dungeons implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        // コマンドの種類を判別する
        if (args.length == 0) {
            // 引数がない場合
            sender.sendMessage("引数がありません");
            return true;
        } else if (args[0].equals("arena")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + "引数がありません");
                sender.sendMessage(prefix + "使い方: /dungeons arena <create|delete|list>");
                return true;
            } else if (args[1].equals("pos1")) {
                if (args.length == 3) {
                    // arenaディレクトリにあるYamlファイルを読み込む処理
                    FileConfiguration arena = YamlConfiguration.loadConfiguration(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                    // ファイルが存在しない場合
                    if (!arena.contains("pos1")) {
                        sender.sendMessage(prefix + "arenaが存在しません");
                        return true;
                    } else {
                        // プレイヤーの視点のブロックをpos1に設定する処理
                        Location loc = ((Player) sender).getLocation();
                        // pos1を数値のリストに変換する処理
                        List<Double> pos1 = new ArrayList<>();
                        pos1.add(loc.getX());
                        pos1.add(loc.getY());
                        pos1.add(loc.getZ());
                        // Yamlファイルにpos1を書き込む処理
                        arena.set("pos1", pos1);
                        try {
                            arena.save(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(prefix + "§a§lpos1を設定しました");
                    }
                } else {
                    sender.sendMessage(prefix + "引数がありません");
                    sender.sendMessage(prefix + "使い方: /dungeons arena pos1 <arena>");
                    return true;
                }
            } else if (args[1].equals("pos2")) {
                if (args.length == 3) {
                    // arenaディレクトリにあるYamlファイルを読み込む処理
                    FileConfiguration arena = YamlConfiguration.loadConfiguration(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                    // ファイルが存在しない場合
                    if (!arena.contains("pos2")) {
                        sender.sendMessage(prefix + "arenaが存在しません");
                        return true;
                    } else {
                        // プレイヤーの視点のブロックをpos1に設定する処理
                        Location loc = ((Player) sender).getLocation();
                        // pos2を数値のリストに変換する処理
                        List<Double> pos2 = new ArrayList<>();
                        pos2.add(loc.getX());
                        pos2.add(loc.getY());
                        pos2.add(loc.getZ());
                        // Yamlファイルにpos2を書き込む処理
                        arena.set("pos2", pos2);
                        try {
                            arena.save(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(prefix + "§a§lpos2を設定しました");
                    }
                } else {
                    sender.sendMessage(prefix + "引数がありません");
                    sender.sendMessage(prefix + "使い方: /dungeons arena pos2 <arena>");
                    return true;
                }
            } else if (args[1].equals("spawn")) {
                if (args.length == 3) {
                    // arenaディレクトリにあるYamlファイルを読み込む処理
                    FileConfiguration arena = YamlConfiguration.loadConfiguration(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                    // ファイルが存在しない場合
                    if (!arena.contains("spawn")) {
                        sender.sendMessage(prefix + "arenaが存在しません");
                        return true;
                    } else {
                        // プレイヤーの視点のブロックをpos1に設定する処理
                        Location loc = ((Player) sender).getLocation();
                        // spawnを数値のリストに変換する処理
                        List<Double> spawn = new ArrayList<>();
                        spawn.add(loc.getX());
                        spawn.add(loc.getY());
                        spawn.add(loc.getZ());
                        // Yamlファイルにspawnを書き込む処理
                        arena.set("spawn", spawn);
                        try {
                            arena.save(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sender.sendMessage(prefix + "§a§lspawnを設定しました");
                    }
                } else {
                    sender.sendMessage(prefix + "引数がありません");
                    sender.sendMessage(prefix + "使い方: /dungeons arena spawn <arena>");
                    return true;
                }
            } else if (args[1].equals("create")) {
                if (args.length == 2) {
                    sender.sendMessage(prefix + "引数がありません");
                    sender.sendMessage(prefix + "使い方: /dungeons arena create <arena>");
                    return true;
                } else {
                    // Yamlファイルをarenaディレクトリに作成する処理
                    File arenaFile = new File("plugins/Dungeons/arena/" + args[2] + ".yml");
                    YamlConfiguration arena = new YamlConfiguration();
                    try {
                        arena.save(arenaFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    arena.set("pos1", "0,0,0");
                    arena.set("pos2", "0,0,0");
                    arena.set("spawn", "0,0,0");
                    // worldをプレイヤーがいるワールドに設定する処理
                    arena.set("world", ((Player) sender).getWorld().getName());
                    try {
                        arena.save(arenaFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sender.sendMessage(prefix + "§a§l" + args[2] + "を作成しました");
                }
            } else if (args[1].equals("list")) {
                if (args.length == 2) {
                    sender.sendMessage(prefix + "§a§l=== arena list ===");
                    // arenaディレクトリにあるYamlファイルを読み込む処理
                    for (File file : new File("plugins/Dungeons/arena").listFiles()) {
                        // .DS_Storeを除外する処理
                        if (file.getName().equals(".DS_Store")) {
                            continue;
                        }
                        sender.sendMessage(prefix + "§a§l - " + file.getName().replace(".yml", ""));
                    }
                }
            } else if (args[1].equals("delete")) {
                if (args.length == 3) {
                    // arenaディレクトリにあるYamlファイルを読み込む処理
                    FileConfiguration arena = YamlConfiguration.loadConfiguration(new File("plugins/Dungeons/arena/" + args[2] + ".yml"));
                    // ファイルが存在しない場合
                    if (!arena.contains("pos1")) {
                        sender.sendMessage(prefix + "arenaが存在しません");
                        return true;
                    } else {
                        // Yamlファイルを削除する処理
                        File arenaFile = new File("plugins/Dungeons/arena/" + args[2] + ".yml");
                        arenaFile.delete();
                        sender.sendMessage(prefix + "§a§l" + args[2] + "を削除しました");
                    }
                } else {
                    sender.sendMessage(prefix + "引数がありません");
                    sender.sendMessage(prefix + "使い方: /dungeons arena delete <arena>");
                    return true;
                }
            } else {
                sender.sendMessage(prefix + "引数が間違っています");
                sender.sendMessage(prefix + "使い方: /dungeons arena <create/delete/list/pos1/pos2/spawn>");
                return true;
            }
            return false;
        } else if (args[0].equals("help")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + "§a§l=== Dungeons help ===");
                sender.sendMessage(prefix + "§a§l - /dungeons help: ヘルプを表示します");
                sender.sendMessage(prefix + "§a§l - /dungeons arena: arenaコマンドを表示します");
                sender.sendMessage(prefix + "§a§l - /dungeons join: ダンジョンに参加します");
                sender.sendMessage(prefix + "§a§l - /dungeons leave: ダンジョンから退出します");
                sender.sendMessage(prefix + "§a§l - /dungeons reload: プラグインをリロードします");
                sender.sendMessage(prefix + "§a§l - /dungeons version: プラグインのバージョンを表示します");
            } else {
                sender.sendMessage(prefix + "引数が間違っています");
                sender.sendMessage(prefix + "使い方: /dungeons help");
                return true;
            }
            return false;
        } else if (args[0].equals("reload")) {
            if (args.length == 1) {
                // config.ymlをリロードする処理
                plugin.reloadConfig();
                sender.sendMessage(prefix + "§a§lconfig.ymlをリロードしました");
            } else {
                sender.sendMessage(prefix + "引数が間違っています");
                sender.sendMessage(prefix + "使い方: /dungeons reload");
                return true;
            }
            return false;
        } else if (args[0].equals("join")) {
            if (args.length == 2) {
                // arenaディレクトリにあるYamlファイルを読み込む処理
                FileConfiguration arena = YamlConfiguration.loadConfiguration(new File("plugins/Dungeons/arena/" + args[1] + ".yml"));
                // ファイルが存在しない場合
                if (!arena.contains("pos1")) {
                    sender.sendMessage(prefix + "arenaが存在しません");
                    return true;
                } else {
                    // プレイヤーをダンジョンに参加させる処理
                    Player player = (Player) sender;
                    // プレイヤーをymlのspawnにテレポートさせる処理
                    // spawnリストからx,y,zを取得する処理
                    String[] spawn = arena.getString("spawn").replace("[", "").replace("]", "").split(",");
                    player.teleport(new Location(Bukkit.getWorld(arena.getString("world")), Double.parseDouble(spawn[0]), Double.parseDouble(spawn[1]), Double.parseDouble(spawn[2])));
                    player.sendMessage(prefix + "§a§l" + args[1] + "に参加しました");
                }
            } else {
                sender.sendMessage(prefix + "引数が間違っています");
                sender.sendMessage(prefix + "使い方: /dungeons join <arena>");
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command
            command, @NotNull String alias, @NotNull String[] args) {
        // コマンドの種類を判別する
        if (command.getName().equals("dungeons") || command.getName().equals("d")) {
            if (args.length == 1) {
                List<String> args1 = new ArrayList<>();
                args1.add("arena");
                args1.add("help");
                args1.add("join");
                return args1;
            }else if (args.length == 2) {
                if (args[0].equals("arena")) {
                    List<String> args2 = new ArrayList<>();
                    args2.add("create");
                    args2.add("delete");
                    args2.add("list");
                    args2.add("pos1");
                    args2.add("pos2");
                    args2.add("spawn");
                    return args2;
                }
            }else if (args.length == 3) {
                if (args[1].equals("create")) {
                    List<String> args3 = new ArrayList<>();
                    args3.add("<name>");
                    return args3;
                } else if (args[1].equals("delete")) {
                    List<String> args3 = new ArrayList<>();
                    args3.add("<name>");
                    return args3;
                } else if (args[1].equals("pos1")) {
                    List<String> args3 = new ArrayList<>();
                    args3.add("<name>");
                    return args3;
                } else if (args[1].equals("pos2")) {
                    List<String> args3 = new ArrayList<>();
                    args3.add("<name>");
                    return args3;
                } else if (args[1].equals("spawn")) {
                    List<String> args3 = new ArrayList<>();
                    args3.add("<name>");
                    return args3;
                }
            }
        }
        return null;
    }
}
