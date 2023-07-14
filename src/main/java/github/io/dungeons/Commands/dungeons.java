package github.io.dungeons.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class dungeons implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // コマンドの種類を判別する
        if (strings.length == 0) {
            // 引数がない場合
            commandSender.sendMessage("引数がありません");
            return true;
        } else if (strings[0].equalsIgnoreCase("start")) {
            // 引数がstartの場合
            commandSender.sendMessage("startコマンドが実行されました");
            return true;
        } else if (strings[0].equalsIgnoreCase("stop")) {
            // 引数がstopの場合
            commandSender.sendMessage("stopコマンドが実行されました");
            return true;
        } else {
            // 引数がstartでもstopでもない場合
            commandSender.sendMessage("引数が不正です");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
