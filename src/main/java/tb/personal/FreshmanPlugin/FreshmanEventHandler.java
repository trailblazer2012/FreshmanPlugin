package tb.personal.FreshmanPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class FreshmanEventHandler implements Listener{
    JavaPlugin plugin;
    InformationResource informationResource;
    Map<Player, Location> playerLocationMap;    //for saving player's position  e.g. respawn compass

    public FreshmanEventHandler(JavaPlugin plugin, InformationResource informationResource) {
        this.plugin = plugin;
        this.informationResource = informationResource;
        playerLocationMap = new HashMap<>();
    }

    @EventHandler
    public void join(@NotNull PlayerJoinEvent event){
        getServer().broadcastMessage( ChatColor.AQUA + event.getPlayer().getName() + "님 환영합니다.");
        playerLocationMap.put(event.getPlayer(), event.getPlayer().getLocation());  //add to playerLocationMap
    }

    @EventHandler
    public void onDeath(@NotNull EntityDeathEvent event){
        if (event.getEntity().getType() == EntityType.PLAYER){
            Player player = (Player) event.getEntity();

            //prepare player's head
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(player.getName());
            item.setItemMeta(meta);

            //summon armorstand
            ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
            armorStand.setHelmet(item);

            //set the death position
            playerLocationMap.replace(player, player.getLocation());
        }
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event){
        Player player = event.getPlayer();


        //make item - death indicating compass
        ItemStack compass = new ItemStack(Material.COMPASS, 1);

        CompassMeta cm = (CompassMeta) compass.getItemMeta();
        cm.setLodestone(playerLocationMap.get(player));
        compass.setItemMeta(cm);

        //add it to inventory
        player.sendMessage(ChatColor.AQUA + playerLocationMap.get(player).toString());
    }


    /* -------------this part is for Wizard Plugin------------- */
    /* -------------------------------------------------------- */
    /* -------------------------------------------------------- */

    @EventHandler
    public void onPlayerUse(@NotNull PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        String wandName = "";

        if (itemInMainHand.hasItemMeta()) {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if (lore != null){
                wandName = lore.get(lore.size() - 1);
            }
        }
        else {
            return;
        }

        //if player used wand
        if (event.getAction()== Action.RIGHT_CLICK_AIR && WizardRecipe.recipeMap.containsKey(wandName)){
            Bukkit.getConsoleSender().sendMessage( ChatColor.AQUA + WizardRecipe.recipeMap.toString());
            Bukkit.getConsoleSender().sendMessage( ChatColor.AQUA + wandName);
            WizardRecipe.wands.get(wandName).castSpell(player);
        }
    }
}
