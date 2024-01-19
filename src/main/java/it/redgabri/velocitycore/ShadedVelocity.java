package it.redgabri.velocitycore;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.redgabri.velocitycore.cmd.*;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "shadedvelocity",
        name = "ShadedVelocity",
        version = "1.4-SNAPSHOT",
        authors = {"redgabri"}
)
public class ShadedVelocity {
    private static ProxyServer proxy;
    private static Logger logger;
    private static Path dataFolder;

    @Inject
    public ShadedVelocity(Logger Logger, ProxyServer Proxy, @DataDirectory Path dataDirectory){
        proxy = Proxy;
        logger = Logger;
        dataFolder = dataDirectory;
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxy.getCommandManager().register("find", new FindCmd());
        proxy.getCommandManager().register("send", new SendCmd());
        proxy.getCommandManager().register("global", new GlobalCmd(), "alert", "announce");
        proxy.getCommandManager().register("kickall", new GlobalKickCmd(), "globalkick");
        proxy.getCommandManager().register("checkbrand", new ClientCmd(), "checkclient");
    }

    public static ProxyServer getProxy() {
        return proxy;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Path getDataFolder() {
        return dataFolder;
    }
}
