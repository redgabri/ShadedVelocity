package it.redgabri.velocitycore.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import it.redgabri.velocitycore.ShadedVelocity;
import it.redgabri.velocitycore.utils.Cache;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientCmd implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 1){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_ARGS));
            return;
        }

        if (ShadedVelocity.getProxy().getPlayer(args[0]) == null){
            source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.INVALID_PLAYER));
            return;
        }

        Optional<Player> target = ShadedVelocity.getProxy().getPlayer(args[0]);

        source.sendMessage(MiniMessage.miniMessage().deserialize(Cache.CLIENTMESSAGE
                .replaceAll("%target%", target.get().getUsername())
                .replaceAll("%client%", target.get().getClientBrand()+"")
        ));
    }

    @Override
    public List<String> suggest(Invocation invocation){
        String[] args = invocation.arguments();
        List<String> suggestion = new ArrayList<>();

        if (args.length == 0){
            for (Player all : ShadedVelocity.getProxy().getAllPlayers()){
                suggestion.add(all.getUsername());
            }
        }

        return suggestion;
    }
}
