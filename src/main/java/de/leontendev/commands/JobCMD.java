package de.leontendev.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import com.nukkitx.fakeinventories.inventory.ChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent;
import de.leontendev.utils.Databases.API;
import de.leontendev.utils.JobConfig;
import de.leontendev.utils.helpers.Jobs;

import java.util.HashMap;
import java.util.Map;

public class JobCMD extends Command {

    public JobCMD(String name, String description, String usageMessage, String[] aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player){
            if (args.length == 0){
                Player player = (Player) sender;
                if (JobConfig.useUI()){
                    //TODO: ADD CONFIG OPTIONS FOR UI AND GUI
                    FormWindowSimple fw = new FormWindowSimple("", "");
                    fw.addButton(new ElementButton("LumberJack"));
                    fw.addButton(new ElementButton("Hunter"));
                    fw.addButton(new ElementButton("Miner"));
                    player.showFormWindow(fw);
                }else {
                    ChestFakeInventory cfi = new ChestFakeInventory();
                    cfi.setName("");
                    cfi.setContents(slots());
                    player.addWindow(cfi);
                    cfi.addListener(this::onSlotChange);
                }
            }else {
                sender.sendMessage(JobConfig.prefix() + JobConfig.commandUsageMessage());
            }
        }
        return true;
    }

    public static Map<Integer, Item> slots(){
        Map<Integer, Item> items = new HashMap<>();
        items.put(0, Item.get(ItemID.IRON_AXE));
        items.put(1, Item.get(ItemID.IRON_SWORD));
        items.put(2, Item.get(ItemID.IRON_PICKAXE));
        return items;
    }

    private void onSlotChange(final FakeSlotChangeEvent event) {
        if (event.getInventory() instanceof ChestFakeInventory && event.getInventory().getName().equalsIgnoreCase("")) {
            if (event.getAction().getSlot() == 0){
                API.addJob(event.getPlayer(), Jobs.LUMBERJACK);
            }else if (event.getAction().getSlot() == 1){
                API.addJob(event.getPlayer(), Jobs.HUNTER);
            }else {
                API.addJob(event.getPlayer(), Jobs.MINER);
            }
            event.getInventory().close(event.getPlayer());
           event.setCancelled(true);
        }
    }
}
