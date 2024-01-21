package it.redgabri.velocitycore;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.redgabri.velocitycore.cmd.*;
import it.redgabri.velocitycore.config.ConfigLoader;
import it.redgabri.velocitycore.metrics.Metrics;
import it.redgabri.velocitycore.utils.Cache;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "shadedvelocity",
        name = "ShadedVelocity",
        version = "1.4-SNAPSHOT",
        description = "A simple open source velocity core plugin",
        authors = "redgabri"
)
public class ShadedVelocity {
    @Getter
    private static ProxyServer proxy;
    @Getter
    private static Logger logger;
    @Getter
    private static Path dataFolder;
    @Getter
    private static final ConfigLoader config = new ConfigLoader();
    private static Metrics.Factory metricsFactory;

    @Inject
    public ShadedVelocity(Logger Logger, ProxyServer Proxy, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory){
        proxy = Proxy;
        logger = Logger;
        dataFolder = dataDirectory;
        ShadedVelocity.metricsFactory = metricsFactory;
    }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        ConfigLoader.load();
        Cache.init();
        proxy.getCommandManager().register("shadedvelocity", new MainCommand(), "sv");
        proxy.getCommandManager().register("find", new FindCmd());
        proxy.getCommandManager().register("send", new SendCmd());
        proxy.getCommandManager().register("global", new GlobalCmd(), "alert", "announce");
        proxy.getCommandManager().register("kickall", new GlobalKickCmd(), "globalkick");
        proxy.getCommandManager().register("checkbrand", new ClientCmd(), "checkclient");
        proxy.getCommandManager().register("tpto", new TpToCmd());
        proxy.getCommandManager().register("goto", new GotoCmd());

        int pluginId = 20797;
        metricsFactory.make(this, pluginId);
    }
}
