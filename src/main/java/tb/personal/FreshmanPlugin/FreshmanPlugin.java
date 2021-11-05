package tb.personal.FreshmanPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.InputStream;
import java.util.Objects;

public class FreshmanPlugin extends JavaPlugin{

    ConsoleCommandSender consol = Bukkit.getConsoleSender();
    WizardRecipe wizardRecipe;

    @Override
    public void onEnable() {
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]");

        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(InformationResource.class.getClassLoader()));
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 1step");
        InputStream inputStream = getClass().getResourceAsStream("/wizardStrings.yaml");
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 2step");
        //consol.sendMessage(ChatColor.MAGIC + yaml.load(inputStream).toString());
        InformationResource informationResource = yaml.load(inputStream);
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 3step");
        consol.sendMessage( informationResource.toString());
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 4step");

        getServer().getPluginManager().registerEvents(new FreshmanEventHandler(this), this);
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 5step");
        this.wizardRecipe = new WizardRecipe(this, informationResource);
        Objects.requireNonNull(this.getCommand("Wizard")).setExecutor(new WizardCommandExecutor(this, wizardRecipe));
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]- 성공!");
    }

    @Override
    public void onDisable() {
        consol.sendMessage( ChatColor.AQUA + "[플러그인 비활성화 중 입니다.]");
    }
}
