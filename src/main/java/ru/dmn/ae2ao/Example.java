package ru.dmn.ae2ao;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Example.MOD_ID)
public class Example {
    public static final String MOD_ID = "ae2ao";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        System.out.println("Hello AE2AO!");
    }
}
