package tb.personal.FreshmanPlugin;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class WizardCommandExecutor implements CommandExecutor {

    JavaPlugin plugin;

    WizardCommandExecutor(JavaPlugin plugin, WizardRecipe wizardRecipe){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()){
            sender.sendMessage(ChatColor.RED +"[WIZARD] 명령어를 사용할 권한이 없습니다.");
        }
        if (args[0].equals("remove")){
            if (args[1].equals("all")){
                WizardRecipe.recipeMap.forEach(((k, v) -> removeRecipe(k)));
                sender.sendMessage(ChatColor.YELLOW +"[WIZARD] 모든 조합법이 비활성화 되었습니다.");
            }
            else {
                plugin.getServer().removeRecipe(NamespacedKey.minecraft(args[1]));
                sender.sendMessage(ChatColor.YELLOW +"[WIZARD] 일부 조합법이 비활성화 되었습니다.");
            }
        }
        else if(args[0].equals("add")){
            if (args[1].equals("all")){
                WizardRecipe.recipeMap.forEach(((k, v) -> addRecipe(v)));
                sender.sendMessage(ChatColor.YELLOW +"[WIZARD] 모든 조합법이 활성화 되었습니다.");
            }
            else {
                addRecipe(WizardRecipe.wands.get(args[1]).wandRecipe);
                sender.sendMessage(ChatColor.YELLOW +"[WIZARD] 일부 조합법이 활성화 되었습니다.");
            }
        }
        return true;
    }

    /**add recipe with name**/
    private boolean addRecipe(String name){
        return plugin.getServer().addRecipe(WizardRecipe.recipeMap.get(name));
    }

    /**add recipe with recipe**/
    private void addRecipe(Recipe recipe){
        plugin.getServer().addRecipe(recipe);
    }

    /**remove recipe with name**/
    private void removeRecipe(String name){
        plugin.getServer().removeRecipe(NamespacedKey.minecraft(name));
    }
}
