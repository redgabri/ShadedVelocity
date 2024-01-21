package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class GlobalKickCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (!source.hasPermission("shadedvelocity.command.kickall")) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.NO_PERMISSION));
            return;
        }

        for (Player all : ShadedVelocity.getProxy().getAllPlayers()) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.KICK_MESSAGE
                    .replaceAll("%kick_size%", ShadedVelocity.getProxy().getAllPlayers().size()+"")
            ));
            if (all == source || all.hasPermission("shadedvelocity.bypass.kickall")) return;
            if (args.length > 0) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    builder.append(args[i]).append("");
                }
                all.disconnect(MiniMessage.miniMessage().deserialize(builder.toString().trim()));
            }
            else {
                all.disconnect(MiniMessage.miniMessage().deserialize(Cache.DEFAULT_KICK_REASON));
            }
        }
    }
}
