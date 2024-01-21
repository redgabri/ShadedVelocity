package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SendCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        Optional<RegisteredServer> target;

        if (!source.hasPermission("shadedvelocity.command.send")){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length < 2) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        if (ShadedVelocity.getProxy().getServer(args[1]).isPresent()) {
            target = ShadedVelocity.getProxy().getServer(args[1]);
        } else {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SEND_ERROR_SERVER_NOT_FOUND));
            return;
        }

        if (args[0].equalsIgnoreCase("all")){
            for (Player all : ShadedVelocity.getProxy().getAllPlayers()){
                all.createConnectionRequest(target.get()).connect();
            }
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SEND_MESSAGE
                    .replaceAll("%server%", target.get().getServerInfo().getName())
                    .replaceAll("%send_size%", ShadedVelocity.getProxy().getAllPlayers().size()+"") //Abusing java stupid way of handling strings
            ));
            return;
        }

        if (args[0].equalsIgnoreCase("current")){
            if (!(source instanceof Player)){
                return;
            }
            Player player = (Player) source;
            for (Player all : player.getCurrentServer().get().getServer().getPlayersConnected()){
                all.createConnectionRequest(target.get()).connect();
            }
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SEND_MESSAGE
                    .replaceAll("%server%", target.get().getServerInfo().getName())
                    .replaceAll("%send_count%", player.getCurrentServer().get().getServer().getPlayersConnected().size()+"") //Abusing java stupid way of handling strings pt2
            ));
            return;
        }

        for (Player online : ShadedVelocity.getProxy().getAllPlayers()){
            if (args[0].equalsIgnoreCase(online.getUsername())){
                online.createConnectionRequest(target.get()).connect();
                source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SEND_MESSAGE
                        .replaceAll("%server%", target.get().getServerInfo().getName())
                        .replaceAll("%send_count%", online.getUsername())
                ));
            }
            return;
        }

        source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SEND_ERROR_ARG_NOT_FOUND));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        List<String> suggestions = new ArrayList<>();
        if (args.length == 0){
            suggestions.add("all");
            suggestions.add("current");
        }
        if (args.length == 1) {
            for (Player all : ShadedVelocity.getProxy().getAllPlayers()) {
                if (all.getUsername().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(all.getUsername());
                }
            }
        }
        if (args.length == 2){
            for (RegisteredServer server : ShadedVelocity.getProxy().getAllServers()){
                suggestions.add(server.getServerInfo().getName());
            }
        }
        return suggestions;
    }
}
