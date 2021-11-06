package tb.personal.FreshmanPlugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;

public class WizardWand extends Wand{
    WizardWand(Plugin plugin, InformationResource informationResource){
        super(plugin, informationResource);
    }

    private String removeFormat(String rawString){
        return rawString.replace('_', ' ');
    }

    @Override
    public Recipe generateRecipe(){
        /*make wizardWand*/
        ItemStack wand = new ItemStack(Material.STICK, 1);

        //get stick meta
        ItemMeta wandMeta = wand.getItemMeta();

        //set wand meta
        wandMeta.setDisplayName(ChatColor.LIGHT_PURPLE + WAND_DISPLAYNAME);  //display wandRecipe

        // split recipe desciption so that it makes multiline lore.
        ArrayList<String> lore = new ArrayList<>(List.of(informationResource.infoText.infoDescription.wizardWandRecipe.split("\n")));  //wand description
        lore.add(WAND_NAMESPACEKEY);
        wandMeta.setLore(lore);
        wandMeta.addEnchant(Enchantment.LUCK, 3, true);
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wand.setItemMeta(wandMeta);

        //make recipe
        wandRecipe = new ShapedRecipe(new NamespacedKey(plugin, WAND_NAMESPACEKEY), wand);
        wandRecipe.shape("*%*","*%*","*%*");
        wandRecipe.setIngredient('*', Material.GLOWSTONE_DUST);
        wandRecipe.setIngredient('%', Material.STICK);

        return wandRecipe;
    }



    public void castSpell(Player player) {
        int range = 30;

        List<Entity> entities= player.getNearbyEntities(range,range,range);

        List<LivingEntity> livingEntities = new ArrayList<>();

        for (Entity e : entities) {
            if (e instanceof LivingEntity) {
                livingEntities.add((LivingEntity) e);
            }
        }

        LivingEntity target = null;
        BlockIterator bItr = new BlockIterator(player, range);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
// loop through player's line of sight
        while (bItr.hasNext()) {
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            if (block.getType()!=Material.AIR){
                break;
            }
// check for entities near this block in the line of sight
            for (LivingEntity e : livingEntities) {
                loc = e.getLocation();
                ex = loc.getX();
                ey = loc.getY();
                ez = loc.getZ();
                if ((bx - .75 <= ex && ex <= bx + 1.75)
                        && (bz - .75 <= ez && ez <= bz + 1.75)
                        && (by - 1 <= ey && ey <= by + 2.5)) {
// entity is close enough, set target and stop
                    target = e;
                    break;
                }
            }
        }

        if (target==null){
            player.sendMessage(ChatColor.GOLD + "[WIZARD] 잘못된 대상입니다.");
            return;
        }

        target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, ticksPerSecond*1, 1));
        player.getWorld().spawnParticle(Particle.CLOUD,target.getLocation(),  20);
    }
}
