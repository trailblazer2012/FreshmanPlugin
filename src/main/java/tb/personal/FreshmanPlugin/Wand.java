package tb.personal.FreshmanPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Wand {
    InformationResource informationResource;
    static String WAND_DISPLAYNAME;
    static String WAND_NAMESPACEKEY;
    public ShapedRecipe wandRecipe;
    public int ticksPerSecond;
    Plugin plugin;

    Wand(Plugin plugin, InformationResource informationResource){
        ticksPerSecond=20;
        this.informationResource = informationResource;
        this.plugin = plugin;
        WAND_NAMESPACEKEY = informationResource.infoText.infoName.wizardWandRecipe;
        WAND_DISPLAYNAME = removeFormat(WAND_NAMESPACEKEY);
        generateRecipe();
    }

    private String removeFormat(String rawString){
        return rawString.replace('_', ' ');
    }

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

        return wandRecipe;
    }

    public void castSpell() {

    }

    public void castSpell(Player player) {

    }
}
