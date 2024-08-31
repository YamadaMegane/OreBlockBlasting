package com.oregame.blockblasting;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;



@Mod(OreBlockBlasting.MODID)
public class OreBlockBlasting {
  public static final String MODID = "oreblockblasting";

  public OreBlockBlasting() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
  }
  
}
