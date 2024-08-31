package com.oregame.blockblasting.datagen;

import com.oregame.blockblasting.OreBlockBlasting;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = OreBlockBlasting.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreDataGenerator {
  
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput packOutput = generator.getPackOutput();
    
    generator.addProvider(event.includeServer(), new OreRecipeProvider(packOutput));
  }
  
}
