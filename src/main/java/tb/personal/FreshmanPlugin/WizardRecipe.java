package tb.personal.FreshmanPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.NamespacedKey;


import java.util.ArrayList;
import java.util.Map;

public class WizardRecipe{

    static String WIZARD_PLUGIN_NAME;
    static String WIZARDWAND_DISPLAYNAME;
    static String WIZARDWAND_NAMESPACEKEY;
    public Map<String, Recipe> recipeMap;
    Plugin plugin;
    InformationResource informationResource;
    public ShapedRecipe wizardWandRecipe;

    WizardRecipe(Plugin plugin, InformationResource informationResource){
        this.plugin = plugin;
        this.informationResource = informationResource;
        WIZARD_PLUGIN_NAME=informationResource.pluginName;
        WIZARDWAND_DISPLAYNAME =informationResource.infoText.infoName.wizardWandRecipe;
        WIZARDWAND_NAMESPACEKEY =formatKey(WIZARDWAND_DISPLAYNAME);
        generateRecipe();
    }

    private String formatKey(String rawString){
        return rawString.replace(' ', '_');
    }

    private void generateRecipe() {
        /*make wizardWand*/
        ItemStack wand = new ItemStack(Material.STICK, 1);

        //get stick meta
        ItemMeta wandMeta = wand.getItemMeta();

        //set wand meta
        wandMeta.setDisplayName(ChatColor.LIGHT_PURPLE + WIZARDWAND_DISPLAYNAME);  //display wizardWandRecipe
        ArrayList<String> lore = new ArrayList<>();
        lore.add(informationResource.infoText.infoDescription.wizardWandRecipe);  //wand description
        lore.add(WIZARDWAND_DISPLAYNAME);
        wandMeta.setLore(lore);
        wandMeta.addEnchant(Enchantment.LUCK, 3, true);
        wandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wand.setItemMeta(wandMeta);

        //make recipe
        wizardWandRecipe = new ShapedRecipe(new NamespacedKey(plugin, WIZARDWAND_NAMESPACEKEY), wand);
        wizardWandRecipe.shape("*%*","*%*","*%*");
        wizardWandRecipe.setIngredient('*', Material.GLOWSTONE_DUST);
        wizardWandRecipe.setIngredient('%', Material.STICK);

        //add to map
        recipeMap.put(WIZARDWAND_NAMESPACEKEY, wizardWandRecipe);
    }
}
