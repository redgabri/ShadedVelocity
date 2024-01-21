package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MainCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();

        if (!source.hasPermission("shadedvelocity.command.maincommand") || !source.hasPermission("shadedvelocity.subcommand.help")){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length < 1){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize("<aqua>ShadedVelocity<white>'s help page (<aqua>1<white>/1)"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/send <white><all/current/player> <server>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/find <white><player>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/checkbrand <white><player>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/goto <white><server>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/tpto <white><player>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/global <white><message>"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white> - <aqua>/kickall <white>[reason]"));
            source.sendMessage(MiniMessage.miniMessage().deserialize("<white><> = <aqua>required<white>, [] = <aqua>optional"));
        }
    }
}
