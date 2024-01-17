package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class GlobalCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();

        if (!source.hasPermission("shadedvelocity.command.global")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length == 0) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i] + " ");
        }

        String sender = "Console";
        if (source instanceof Player){
            sender = ((Player) source).getUsername();
        }

        for (Player all : ShadedVelocity.getProxy().getAllPlayers()){
            all.sendMessage(MiniMessage.miniMessage().deserialize(Cache.GLOBAL_PREFIX
                    .replaceAll("%player%", sender)
            + message));
        }
    }
}
