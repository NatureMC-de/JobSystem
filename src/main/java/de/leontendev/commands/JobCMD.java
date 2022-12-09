package de.leontendev.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class JobCMD extends Command {

    public JobCMD(String name, String description, String usageMessage, String[] aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        return true;
    }
}
