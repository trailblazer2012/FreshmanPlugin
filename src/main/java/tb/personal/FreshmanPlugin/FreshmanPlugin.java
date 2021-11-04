package tb.personal.FreshmanPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class FreshmanPlugin extends JavaPlugin{

    ConsoleCommandSender consol = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        consol.sendMessage( ChatColor.AQUA + "[플러그인 활성화 중 입니다.]");

        Yaml yaml = new Yaml(new Constructor(InformationResource.class));
        InputStream inputStream = getClass().getResourceAsStream("/wizardStrings.yml");
        InformationResource informationResource = yaml.load(inputStream);
        Yaml y = new Yaml(new CustomClassLoaderConstructor(FreshmanPlugin.class.getClassLoader()));
        InformationResource test =y.loadAs(inputStream, InformationResource.class);

        getServer().getPluginManager().registerEvents(new FreshmanEventHandler(this), this);
        Objects.requireNonNull(this.getCommand("Wizard")).setExecutor(new WizardCommandExecutor(this, new WizardRecipe(this)));
    }

    @Override
    public void onDisable() {
        consol.sendMessage( ChatColor.AQUA + "[플러그인 비활성화 중 입니다.]");
    }
}
