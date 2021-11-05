package tb.personal.FreshmanPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class WizardCommandExecutor implements CommandExecutor {

    JavaPlugin plugin;
    WizardRecipe wizardRecipe;

    WizardCommandExecutor(JavaPlugin plugin, WizardRecipe wizardRecipe){
        this.plugin = plugin;
        this.wizardRecipe = wizardRecipe;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()){
            sender.sendMessage(ChatColor.RED +"[WIZARD] 명령어를 사용할 권한이 없습니다.");
        }
        if (args[0].equals("remove")){
            plugin.getServer().removeRecipe(NamespacedKey.minecraft(WizardRecipe.WIZARD_PLUGIN_NAME));
        }
        else if(args[0].equals("add")){
            plugin.getServer().addRecipe(wizardRecipe.wizardWandRecipe);
        }
        return true;
    }
}
