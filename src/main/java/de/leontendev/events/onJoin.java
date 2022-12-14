package de.leontendev.events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import de.leontendev.utils.Databases.API;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        API.createPlayer(event.getPlayer());
    }
}
