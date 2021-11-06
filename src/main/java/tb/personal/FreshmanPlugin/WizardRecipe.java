package tb.personal.FreshmanPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class WizardRecipe{

    static String WIZARD_PLUGIN_NAME;
    public static Map<String, Recipe> recipeMap;
    Plugin plugin;
    InformationResource informationResource;
    public static Map<String, Wand> wands;

    WizardRecipe(Plugin plugin, InformationResource informationResource){
        this.plugin = plugin;
        this.informationResource = informationResource;
        recipeMap = new HashMap<>();
        wands = new HashMap<>();
        WIZARD_PLUGIN_NAME=informationResource.pluginName;

        // make wands
        List<Wand> wandList = new ArrayList<>();
        wandList.add(new WizardWand(plugin, informationResource));

        wandList.forEach((wand)->wands.put(WizardWand.WAND_NAMESPACEKEY, wand));
        generateRecipe();
        Bukkit.getConsoleSender().sendMessage( ChatColor.AQUA + WizardRecipe.recipeMap.toString());
    }

    private String unformatKey(String rawString){
        return rawString.replace('_', ' ');
    }

    private void generateRecipe() {
        wands.forEach((s, wand) -> recipeMap.put(s, wand.wandRecipe));
    }
}
