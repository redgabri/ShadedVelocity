package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class FindCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();

        if (!source.hasPermission("shadedvelocity.command.find")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length < 1) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        for (Player all : ShadedVelocity.getProxy().getAllPlayers()) {
            if (args[0].equalsIgnoreCase(all.getUsername())) {
                source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.FIND_MESSAGE));
            } else {
                source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.FIND_ERROR_NOT_FOUND));
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        List<String> proxyPlayers = new ArrayList<>();
        for (Player all : ShadedVelocity.getProxy().getAllPlayers()) {
            if (args.length == 0){
                proxyPlayers.add(all.getUsername());
            }

            if (args.length == 1) {
                if (all.getUsername().toLowerCase().startsWith(args[0].toLowerCase())) {
                    proxyPlayers.add(all.getUsername());
                }
            }
        }
        return proxyPlayers;
    }
}
