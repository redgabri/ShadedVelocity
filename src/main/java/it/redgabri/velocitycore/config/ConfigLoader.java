package it.redgabri.velocitycore.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import it.redgabri.velocitycore.ShadedVelocity;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigLoader {

    private YamlDocument messages;
    public void loadMessages(){
        try{
            messages = YamlDocument.create(new File(ShadedVelocity.getDataFolder().toFile() + "/messages.yml"),
                    getClass().getResourceAsStream("/messages.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
            messages.update();
            messages.save();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public static void load(){
        ShadedVelocity.getConfig().loadMessages();
    }
}