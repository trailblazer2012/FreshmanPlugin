package tb.personal.FreshmanPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;


import java.util.ArrayList;

public class WizardRecipe extends JavaPlugin{

    static final String WIZARD_PLUGIN_NAME=InformationResource.PluginName;
    static final String WIZARD_NAMESPACEKEY=InformationResource.infoText.infoName.wizardWandRecipe;
    Plugin plugin;
    public ShapedRecipe wizardWandRecipe;

    WizardRecipe(Plugin plugin){

        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        generateRecipe();
    }

    private void generateRecipe() {
        ItemStack wand = new ItemStack(Material.STICK, 1);

        //get stick meta
        ItemMeta wandMeta = wand.getItemMeta();

        //set wand meta
        wandMeta.setDisplayName(ChatColor.LIGHT_PURPLE + WIZARD_NAMESPACEKEY);  //display wizardWandRecipe
        ArrayList<String> lore = new ArrayList<>();
        lore.add(InformationResource.infoText.infoDescription.wizardWandRecipe);  //wand description
        lore.add(WIZARD_NAMESPACEKEY);
        wandMeta.setLore(lore);
        wandMeta.addEnchant(Enchantment.LUCK, 3, true);
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wand.setItemMeta(wandMeta);

        //make recipe
        wizardWandRecipe = new ShapedRecipe(new NamespacedKey(plugin,WIZARD_NAMESPACEKEY), wand);
        wizardWandRecipe.shape("*%*","*%*","*%*");
        wizardWandRecipe.setIngredient('*', Material.GLOWSTONE_DUST);
        wizardWandRecipe.setIngredient('%', Material.STICK);
    }
}
