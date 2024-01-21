package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class TpToCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (!(source instanceof Player)){
            source.sendMessage(Component.text("Only players can execute this command"));
            return;
        }

        if (!source.hasPermission("shadedvelocity.command.tpto")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        if (args.length < 1) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        if (ShadedVelocity.getProxy().getPlayer(args[0]) == null){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_PLAYER));
            return;
        }

        Player target = ShadedVelocity.getProxy().getPlayer(args[0]).get();
        Player player = (Player) source;

        if (player.getCurrentServer() == target.getCurrentServer()){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.SAME_SERVER));
            return;
        }

        player.createConnectionRequest(target.getCurrentServer().get().getServer());
        player.sendMessage(MiniMessage.miniMessage().deserialize(Cache.TPTO_SENDED_SUCCESSFULLY.replaceAll("%target%", target.getUsername()).replaceAll("%target_sever%", target.getCurrentServer().get().getServerInfo().getName())));
    }
}
