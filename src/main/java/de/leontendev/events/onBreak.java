package de.leontendev.events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import de.leontendev.utils.Databases.API;
import de.leontendev.utils.JobConfig;
import de.leontendev.utils.helpers.Jobs;

import java.util.ArrayList;

public class onBreak implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (event.getBlock().getId() == Block.LOG || event.getBlock().getId() == Block.LOG2){
            if (API.getJob(player).equalsIgnoreCase(Jobs.LUMBERJACK)){
                API.addXP(player, JobConfig.lumberjackXP(), Jobs.LUMBERJACK);
            }
        }else {
            for (int i = 0; i < ores().size(); i++){
                if (ores().get(i).getId() == event.getBlock().getId()){
                    if (API.getJob(player).equalsIgnoreCase(Jobs.MINER)){
                        API.addXP(player, JobConfig.minerXP(), Jobs.MINER);
                    }
                }
            }
        }
    }

    public static ArrayList<Block> ores(){
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(Block.get(BlockID.COAL_ORE));
        blocks.add(Block.get(BlockID.IRON_ORE));
        blocks.add(Block.get(BlockID.GOLD_ORE));
        blocks.add(Block.get(BlockID.LAPIS_ORE));
        blocks.add(Block.get(BlockID.REDSTONE_ORE));
        blocks.add(Block.get(BlockID.DIAMOND_ORE));
        blocks.add(Block.get(BlockID.COPPER_ORE));
        blocks.add(Block.get(BlockID.QUARTZ_ORE));
        blocks.add(Block.get(BlockID.EMERALD_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_COAL_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_COPPER_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_IRON_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_GOLD_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_LAPIS_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_DIAMOND_ORE));
        blocks.add(Block.get(BlockID.DEEPSLATE_EMERALD_ORE));
        return blocks;
    }
}
