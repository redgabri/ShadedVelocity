package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public class GotoCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (!(source instanceof Player)){
            source.sendMessage(Component.text("Only players can execute this command"));
            return;
        }

        if (!source.hasPermission("shadedvelocity.command.goto")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length < 1) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        if (ShadedVelocity.getProxy().getServer(args[0]) == null){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_PLAYER));
            return;
        }

        RegisteredServer target = ShadedVelocity.getProxy().getServer(args[0]).get();
        Player player = (Player) source;

        if (player.getCurrentServer().get() == target){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SAME_SERVER_GOTO.replaceAll("%server%", target.getServerInfo().getName())));
            return;
        }

        player.createConnectionRequest(target);
        player.sendMessage(MiniMessage.miniMessage().deserialize(Cache.GOTO_SENDED_SUCCESSFULLY.replaceAll("%server%", target.getServerInfo().getName())));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        List<String> sugegstions = new ArrayList<>();
        for (RegisteredServer all : ShadedVelocity.getProxy().getAllServers()) {
            if (args.length == 0){
                sugegstions.add(all.getServerInfo().getName());
            }

            if (args.length == 1) {
                if (all.getServerInfo().getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    sugegstions.add(all.getServerInfo().getName());
                }
            }
        }
        return sugegstions;
    }
}
