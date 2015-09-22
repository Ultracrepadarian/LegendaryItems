package com.krazytar.plugins.legendaryitems;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LegendaryItems extends JavaPlugin implements Listener{
    // All the items :)
    ItemStack speedSword;
    ItemStack invisibleStick;
    
    @Override
    public void onEnable() {
        List<String> lore = new ArrayList<>();
        // Initialize all items :)
        lore.add("Speed yourself up by right clicking");
        speedSword = createItem("Sword of Speed", lore, Material.STONE_SWORD);
        invisibleStick = createItem("Stick of Invisibility", lore, Material.STICK);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
    {
        Player player = (Player) sender;
        if(label.equalsIgnoreCase("li") || label.equalsIgnoreCase("LegendaryItems"))
        {
            if(sender instanceof Player)
            {
                if(args[1] != null)
                {
                    if(args[1].equalsIgnoreCase("give"))
                    {
                        String give = ChatColor.BLUE + "Here's your item!";
                        if(args[2] != null)
                        {
                            if(args[2].equalsIgnoreCase("invisiblestick"))//Invisible stick give
                            {
                                player.getInventory().addItem(invisibleStick);
                                player.sendMessage(give);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    
    // Make it easier to automatically create items without having to code the lore and such every time
    ItemStack createItem(String name, List<String> lore, Material type) {
        ItemStack item = new ItemStack(type, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        List<String> lore = new ArrayList<>();
        Player player = e.getPlayer();
        ItemStack item = new ItemStack(player.getItemInHand().getType(), 1);
        
        // For the speed sword
        if(item.getType().equals(Material.STONE_SWORD)) {
            ItemMeta im = item.getItemMeta();
            lore.clear();
            lore.add("Speed yourself up by right clicking");
            if(im.getLore() == lore) {
                PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 2, 1);
                player.addPotionEffect(pe);
                short durability = item.getDurability();
                short newDurability = durability -= 1;
                item.setDurability(newDurability);
            }
        }
        
        // For the fire boots
        int repeating10Ticks = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(Player player : getServer().getOnlinePlayers()) {
                    
                    Material boots = player.getInventory().getBoots().getType();
                    PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1);
                    if(boots != null && boots == Material.LEATHER_BOOTS) {
                        //TODO fire stoofs
                    }
                    if(player.getInventory().getItemInHand().getItemMeta() == invisibleStick.getItemMeta())
                    {
                        player.addPotionEffect(invisibility);
                    }
                    
                }
            }
        }, 0L, 10L);
        
    }
    //This Is ALL A TESTTTTTTTT
}
