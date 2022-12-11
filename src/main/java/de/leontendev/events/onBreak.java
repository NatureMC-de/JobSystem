package de.leontendev.events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;

public class onBreak implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (event.getBlock().getId() == Block.LOG || event.getBlock().getId() == Block.LOG2){
            if ()
        }
    }

}
