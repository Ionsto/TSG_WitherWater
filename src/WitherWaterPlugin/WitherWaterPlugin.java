package WitherWaterPlugin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.server.v1_6_R3.EntityBoat;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.yaml.snakeyaml.Yaml;

public class WitherWaterPlugin extends JavaPlugin implements Listener{
	int Power = 2;//Withering effect
	boolean BoatEffect = true;//Wheter you get effected in boat
    @Override
    public void onEnable() {
    	this.getServer().getPluginManager().registerEvents(this, this);
    	LoadYAML();
        // TODO Insert logic to be performed when the plugin is enabled
    	
    }
 
    @Override
    public void onDisable() {
    	SaveYAML();
        // TODO Insert logic to be performed when the plugin is disabled
    } 
    public float ZDiv(float x,float y)
    {
    	if(x == 0 || y == 0)
    	{
    		return 0;
    	}
    	return x/y;
    }
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent evt) {
        Player player = evt.getPlayer(); // The player who joined
        Material mat = player.getLocation().getBlock().getType();
        if(mat == Material.WATER || mat == Material.STATIONARY_WATER)
        {
        	if(!BoatEffect)
        	{
        		player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 999 ,(int) Power));
        	}
        	else
        	{
        		if(!(player.getVehicle() instanceof EntityBoat))
        		{
            		player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 999 ,(int) Power));
        		}
        	}
        }
        else
        {
    		player.removePotionEffect(PotionEffectType.WITHER);
        }
    }
    void SaveYAML(){
    	System.out.println("Saving Configs");
        if(!getDataFolder().exists())
    	{
    		this.getDataFolder().mkdirs();
    	}
        File weightfile = new File(getDataFolder(), "witherwaterconfig.yml");
        if(weightfile.exists())
        {
        	weightfile.delete();
		}
		try {
			weightfile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
            FileWriter fw = new FileWriter(weightfile,false);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(Power);
            pw.println(BoatEffect);
             
            pw.flush();
             
            pw.close();
    	} catch (IOException e) {
    	e.printStackTrace();
    	}
    }
    @SuppressWarnings("unchecked")
	void LoadYAML()
    {
    	System.out.println("Loading Configs");
    	if(!getDataFolder().exists())
    	{
    		this.getDataFolder().mkdirs();
    	}
		try {
			File witherwaterfile = new File(getDataFolder(), "witherwaterconfig.yml");
			BufferedReader br = new BufferedReader(new FileReader(witherwaterfile));
	    	if(witherwaterfile.exists())
	    	{
		    	try {
		            witherwaterfile.createNewFile();
		            FileReader fw = new FileReader(witherwaterfile);
			    	Power = (int) Integer.parseInt(br.readLine());
			    	BoatEffect = (boolean) Boolean.parseBoolean(br.readLine());
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
	    	}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }
}
