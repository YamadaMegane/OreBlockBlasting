package com.oregame.blockblasting.datagen;

import com.oregame.blockblasting.OreBlockBlasting;
import com.oregame.blockblasting.init.CsvData;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Consumer;



public class OreRecipeProvider extends RecipeProvider {

  public OreRecipeProvider(PackOutput output) {
    super(output);
  }
  
  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
    oreSmeltingBlasting(consumer, Items.RAW_IRON_BLOCK, Items.IRON_BLOCK, 0.7F*9, 200*9, null);
    oreSmeltingBlasting(consumer, Items.RAW_COPPER_BLOCK, Items.COPPER_BLOCK, 0.7F*9, 200*9, null);
    oreSmeltingBlasting(consumer, Items.RAW_GOLD_BLOCK, Items.GOLD_BLOCK, 1.0F*9, 200*9, null);
    
    for (Map<String, String> data : CsvData.RAWS) {
      String modid = data.get("modid");
      String from = data.get("from");
      String to = data.get("to");
      float exp = Float.parseFloat(data.get("exp"));
      int time = Integer.parseInt(data.get("time"));
      
      if (ModList.get().isLoaded(modid)) {
        ItemLike fromItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, from));
        ItemLike toItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, to));
        
        oreSmeltingBlasting(consumer, fromItem, toItem, exp*9, time*9, modid);
      }
    }
  }
  
  private static void oreSmeltingBlasting(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, float exp, int time, String modid) {
    String inputName = input.asItem().toString();
    String outputName = output.asItem().toString();
    String unlockName = "has_" + inputName;
    float roundedExp = Math.round(exp * 10) / 10.0F;
    
    String smeltingKey = outputName;
    String blastingKey = outputName;
    
    if (modid != null && !modid.isEmpty()) {
      smeltingKey = modid + "/" + outputName;
      blastingKey = modid + "/" + outputName;
    }
    
    ResourceLocation smeltingId = new ResourceLocation(OreBlockBlasting.MODID, smeltingKey + "_from_smelting");
    ResourceLocation blastingId = new ResourceLocation(OreBlockBlasting.MODID, blastingKey + "_from_blasting");
    
    SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, roundedExp, time)
      .unlockedBy(unlockName, has(input))
      .save(consumer, smeltingId);
    
    SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), RecipeCategory.MISC, output, roundedExp, time/2)
      .unlockedBy(unlockName, has(input))
      .save(consumer, blastingId);
  }
}
