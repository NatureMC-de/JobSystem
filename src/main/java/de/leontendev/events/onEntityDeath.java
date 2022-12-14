package de.leontendev.events;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDeathEvent;
import de.leontendev.utils.Databases.API;
import de.leontendev.utils.JobConfig;
import de.leontendev.utils.helpers.Jobs;


public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
            Entity killer = ((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager();
            if (killer instanceof Player){
                Player player = ((Player) killer).asPlayer();
                if (API.getJob(player).equalsIgnoreCase(Jobs.HUNTER)){
                    API.addXP(player, JobConfig.hunterXP(), Jobs.HUNTER);
                }
            }
        }
    }
}
