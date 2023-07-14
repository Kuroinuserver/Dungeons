package github.io.dungeons;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.drops.droppables.ExperienceDrop;
import io.lumine.mythic.core.drops.droppables.ItemDrop;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;

import static github.io.dungeons.Dungeons.plugin;

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
        // mythicmobsのmobかどうかを判定する
        if (mobname == null) {
            return;
        } else {
            player.sendMessage("§a" + player.getName() + "§fは§a" + mobDisplayName + "§fを倒した");
        }
    }
    // mobが倒された時に、経験値を取得する
    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent e){
        Entity entity = e.getEntity();
        Player player = (Player) e.getEntity().getKiller();
        int exp = e.getDroppedExp();
        player.sendMessage("§a" + player.getName() + "§fは§a" + entity.getName() + "§fを倒して§a" + exp + "§fの経験値を獲得した");
    }
}
