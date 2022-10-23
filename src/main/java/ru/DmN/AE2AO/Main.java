package ru.DmN.AE2AO;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.DmN._i.AE2AO.Toml;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Mod(modid = Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "ae2ao";
    /**
     * Default Config
     */
    public static Config DC = new Config();
    /**
     * Last Config
     */
    public static Config LC = DC.clone();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        try {
            File conf = new File(event.getModConfigurationDirectory().toString() + File.separator + "ae2ao.toml");

            if (conf.createNewFile()) {
                try (FileOutputStream stream = new FileOutputStream(conf)) {
                    stream.write("DisableChannels = false\nControllerLimits = false\nMax_X = 7\nMax_Y = 7\nMax_Z = 7\nSCFD = false\nStorageCellLimits = true\nChatInfo = true".getBytes(StandardCharsets.UTF_8));
                }
                DC.useConfig();
            } else {
                DC = new Toml().read(conf).to(Config.class);
                LC = DC.clone();
                LC.useConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int usedChannels = 0;
    private int getUsedChannels()
    {
        return Main.LC.DisableChannels ? 1 : this.usedChannels;
    }
}
