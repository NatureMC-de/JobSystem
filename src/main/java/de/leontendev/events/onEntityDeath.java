package de.leontendev.events;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDeathEvent;
import de.leontendev.utils.Databases.API;
import de.leontendev.utils.JobConfig;
import de.leontendev.utils.helpers.Jobs;


public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity entityKiller = event.getEntity().getLastDamageCause().getEntity();
        if (entityKiller instanceof Player){
            Player killer = ((Player) entityKiller).asPlayer();
            if (API.getJob(killer).equalsIgnoreCase(Jobs.HUNTER)){
                API.addXP(killer, JobConfig.hunterXP(), Jobs.HUNTER);
            }
        }
    }
}
