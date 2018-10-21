package gregtech.loaders.postload;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TC_Aspects;
import gregtech.api.materials.Materials;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_DummyWorld;
import mods.railcraft.common.blocks.aesthetics.cube.EnumCube;
import mods.railcraft.common.items.RailcraftToolItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

import static gregtech.api.enums.GT_Values.DUMMY_WORLD;
import static gregtech.api.enums.GT_Values.MATERIAL_UNIT;
import static gregtech.api.enums.GT_Values.MOD_ID_AE;
import static gregtech.api.enums.GT_Values.MOD_ID_BC_SILICON;
import static gregtech.api.enums.GT_Values.MOD_ID_BC_TRANSPORT;
import static gregtech.api.enums.GT_Values.MOD_ID_EBXL;
import static gregtech.api.enums.GT_Values.MOD_ID_FM;
import static gregtech.api.enums.GT_Values.MOD_ID_FR;
import static gregtech.api.enums.GT_Values.MOD_ID_RC;
import static gregtech.api.enums.GT_Values.MOD_ID_TC;
import static gregtech.api.enums.GT_Values.MOD_ID_TF;
import static gregtech.api.enums.GT_Values.NULL_FLUID_STACK;
import static gregtech.api.enums.GT_Values.NULL_ITEM_STACK;
import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

public class GT_MachineRecipeLoader
        implements Runnable {
    private static final String AE_ITEM = "item.ItemMultiMaterial";

    //currently have only refactored values referenced >8 into strings - e59
    private static final String AE_SEED = "item.ItemCrystalSeed";
    private static final String RCHIP = "redstoneChipset";
    private static final String CHIPS = "chipsets";
    private static final String LUBE = "lubricant";
    private static final String GC_OIL = "oilgc";
    private static final String P_TH = "potion.thick";
    private static final String P_MU = "potion.mundane";
    private static final String P_AW = "potion.awkward";
    private static final String P_WE = "potion.weakness";
    private static final String F1 = "flower1";
    private static final String F2 = "flower2";
    private static final String F3 = "flower3";
    private static final String EBDYE = "extrabiomes.dye";
    private static final String EBS1 = "saplings_1";
    private static final String EBS2 = "saplings_2";
    private static final String TTUBE = "thermionicTubes";
    private static final String SL1 = "slabs1";
    private static final String SL2 = "slabs2";
    private static final String SL3 = "slabs3";
    private static final String RESEARCH_P1 = "gt.research.page.1.";
    private static final String ALC = "ALCHEMY";
    private final MaterialStack[][] mAlloySmelterList = {{new MaterialStack(Materials.get("Copper"), 3L), new MaterialStack(Materials.get("Tin"), 1L), new MaterialStack(Materials.get("Bronze"), 4L)}, {new MaterialStack(Materials.get("Copper"), 3L), new MaterialStack(Materials.get("Zinc"), 1L), new MaterialStack(Materials.get("Brass"), 4L)}, {new MaterialStack(Materials.get("Copper"), 1L), new MaterialStack(Materials.get("Nickel"), 1L), new MaterialStack(Materials.get("Cupronickel"), 2L)}, {new MaterialStack(Materials.get("Copper"), 1L), new MaterialStack(Materials.get("Redstone"), 4L), new MaterialStack(Materials.get("RedAlloy"), 1L)}, {new MaterialStack(Materials.get("AnnealedCopper"), 3L), new MaterialStack(Materials.get("Tin"), 1L), new MaterialStack(Materials.get("Bronze"), 4L)}, {new MaterialStack(Materials.get("AnnealedCopper"), 3L), new MaterialStack(Materials.get("Zinc"), 1L), new MaterialStack(Materials.get("Brass"), 4L)}, {new MaterialStack(Materials.get("AnnealedCopper"), 1L), new MaterialStack(Materials.get("Nickel"), 1L), new MaterialStack(Materials.get("Cupronickel"), 2L)}, {new MaterialStack(Materials.get("AnnealedCopper"), 1L), new MaterialStack(Materials.get("Redstone"), 4L), new MaterialStack(Materials.get("RedAlloy"), 1L)}, {new MaterialStack(Materials.get("Iron"), 1L), new MaterialStack(Materials.get("Tin"), 1L), new MaterialStack(Materials.get("TinAlloy"), 2L)}, {new MaterialStack(Materials.get("WroughtIron"), 1L), new MaterialStack(Materials.get("Tin"), 1L), new MaterialStack(Materials.get("TinAlloy"), 2L)}, {new MaterialStack(Materials.get("Iron"), 2L), new MaterialStack(Materials.get("Nickel"), 1L), new MaterialStack(Materials.get("Invar"), 3L)}, {new MaterialStack(Materials.get("WroughtIron"), 2L), new MaterialStack(Materials.get("Nickel"), 1L), new MaterialStack(Materials.get("Invar"), 3L)}, {new MaterialStack(Materials.get("Tin"), 9L), new MaterialStack(Materials.get("Antimony"), 1L), new MaterialStack(Materials.get("SolderingAlloy"), 10L)}, {new MaterialStack(Materials.get("Lead"), 4L), new MaterialStack(Materials.get("Antimony"), 1L), new MaterialStack(Materials.get("BatteryAlloy"), 5L)}, {new MaterialStack(Materials.get("Gold"), 1L), new MaterialStack(Materials.get("Silver"), 1L), new MaterialStack(Materials.get("Electrum"), 2L)}, {new MaterialStack(Materials.get("Magnesium"), 1L), new MaterialStack(Materials.get("Aluminium"), 2L), new MaterialStack(Materials.get("Magnalium"), 3L)}, {new MaterialStack(Materials.get("Silver"), 1L), new MaterialStack(Materials.get("Teslatite"), 4L), new MaterialStack(Materials.get("BlueAlloy"), 1L)}, {new MaterialStack(Materials.get("RedAlloy"), 1L), new MaterialStack(Materials.get("BlueAlloy"), 1L), new MaterialStack(Materials.get("PurpleAlloy"), 2L)}};

    public void run() {
        GT_Log.out.println("GT_Mod: Adding non-OreDict Machine Recipes.");
        try {
            GT_Utility.removeSimpleIC2MachineRecipe(NULL_ITEM_STACK, ic2.api.recipe.Recipes.metalformerExtruding.getRecipes(), ItemList.Cell_Empty.get(3L));
            GT_Utility.removeSimpleIC2MachineRecipe(ItemList.IC2_Energium_Dust.get(1L), ic2.api.recipe.Recipes.compressor.getRecipes(), NULL_ITEM_STACK);
            GT_Utility.removeSimpleIC2MachineRecipe(new ItemStack(Items.gunpowder), ic2.api.recipe.Recipes.extractor.getRecipes(), NULL_ITEM_STACK);
            GT_Utility.removeSimpleIC2MachineRecipe(new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), ic2.api.recipe.Recipes.extractor.getRecipes(), NULL_ITEM_STACK);
        } catch (RuntimeException e) {
            GT_Log.out.println("GT_Mod: Problem adding non-OreDict Machine Recipes.");
            e.printStackTrace(GT_Log.err);
        }
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.wheat_seeds, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, Materials.get("SeedOil").getFluid(5L), 10000, 32, 2);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.melon_seeds, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, Materials.get("SeedOil").getFluid(3L), 10000, 32, 2);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.pumpkin_seeds, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, Materials.get("SeedOil").getFluid(6L), 10000, 32, 2);
        try {
            GT_DummyWorld tWorld = (GT_DummyWorld) DUMMY_WORLD;
            while (tWorld.mRandom.mIterationStep > 0) {
                RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_Utility.copyAmount(1L, ForgeHooks.getGrassSeed(tWorld)), NULL_ITEM_STACK, Materials.get("SeedOil").getFluid(5L), 10000, 64, 2);
            }
        } catch (RuntimeException e) {
            GT_Log.out.println("GT_Mod: failed to iterate somehow, maybe it's your Forge Version causing it. But it's not that important\n");
            e.printStackTrace(GT_Log.err);
        }
        RECIPE_ADDER_INSTANCE.addPrinterRecipe(GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.get("Paper"), 1L), FluidRegistry.getFluidStack("squidink", 36), NULL_ITEM_STACK, ItemList.Paper_Punch_Card_Empty.get(1L), 100, 2);
        RECIPE_ADDER_INSTANCE.addPrinterRecipe(ItemList.Paper_Punch_Card_Empty.get(1L), FluidRegistry.getFluidStack("squidink", 36), ItemList.Tool_DataStick.getWithName(0L, "With Punch Card Data"), ItemList.Paper_Punch_Card_Encoded.get(1L), 100, 2);
        RECIPE_ADDER_INSTANCE.addPrinterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Paper"), 3L), FluidRegistry.getFluidStack("squidink", 144), ItemList.Tool_DataStick.getWithName(0L, "With Scanned Book Data"), ItemList.Paper_Printed_Pages.get(1L), 400, 2);
        RECIPE_ADDER_INSTANCE.addPrinterRecipe(new ItemStack(Items.map, 1, OreDictionary.WILDCARD_VALUE), FluidRegistry.getFluidStack("squidink", 144), ItemList.Tool_DataStick.getWithName(0L, "With Scanned Map Data"), new ItemStack(Items.filled_map, 1, 0), 400, 2);
        RECIPE_ADDER_INSTANCE.addPrinterRecipe(new ItemStack(Items.book, 1, OreDictionary.WILDCARD_VALUE), FluidRegistry.getFluidStack("squidink", 144), NULL_ITEM_STACK, GT_Utility.getWrittenBook("Manual_Printer", ItemList.Book_Written_01.get(1L)), 400, 2);
        for (OrePrefixes tPrefix : Arrays.asList(OrePrefixes.dust, OrePrefixes.dustSmall, OrePrefixes.dustTiny)) {
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("EnderPearl"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Blaze"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("EnderEye"), 1L * tPrefix.mMaterialAmount), (int) (100L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Silver"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Electrum"), 2L * tPrefix.mMaterialAmount), (int) (200L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Iron"), 2L), GT_OreDictUnificator.get(tPrefix, Materials.get("Nickel"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Invar"), 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Iron"), 4L), GT_OreDictUnificator.get(tPrefix, Materials.get("Invar"), 3L), GT_OreDictUnificator.get(tPrefix, Materials.get("Manganese"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Chrome"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("StainlessSteel"), 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Aluminium"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Chrome"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Kanthal"), 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 3L), GT_OreDictUnificator.get(tPrefix, Materials.get("Barium"), 2L), GT_OreDictUnificator.get(tPrefix, Materials.get("Yttrium"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("YttriumBariumCuprate"), 6L * tPrefix.mMaterialAmount), (int) (600L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 3L), GT_OreDictUnificator.get(tPrefix, Materials.get("Zinc"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Brass"), 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 3L), GT_OreDictUnificator.get(tPrefix, Materials.get("Tin"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Bronze"), 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Nickel"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Cupronickel"), 2L * tPrefix.mMaterialAmount), (int) (200L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Gold"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("RoseGold"), 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Silver"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("SterlingSilver"), 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Copper"), 3L), GT_OreDictUnificator.get(tPrefix, Materials.get("Electrum"), 2L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("BlackBronze"), 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Bismuth"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Brass"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("BismuthBronze"), 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("BlackBronze"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Nickel"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Steel"), 3L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("BlackSteel"), 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("SterlingSilver"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("BismuthBronze"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("BlackSteel"), 4L), GT_OreDictUnificator.get(tPrefix, Materials.get("Steel"), 2L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("RedSteel"), 8L * tPrefix.mMaterialAmount), (int) (800L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("RoseGold"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Brass"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("BlackSteel"), 4L), GT_OreDictUnificator.get(tPrefix, Materials.get("Steel"), 2L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("BlueSteel"), 8L * tPrefix.mMaterialAmount), (int) (800L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Cobalt"), 5L), GT_OreDictUnificator.get(tPrefix, Materials.get("Chrome"), 2L), GT_OreDictUnificator.get(tPrefix, Materials.get("Nickel"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Molybdenum"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Ultimet"), 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Brass"), 7L), GT_OreDictUnificator.get(tPrefix, Materials.get("Aluminium"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Cobalt"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("CobaltBrass"), 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Saltpeter"), 2L), GT_OreDictUnificator.get(tPrefix, Materials.get("Sulfur"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Coal"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Gunpowder"), 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
            RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(tPrefix, Materials.get("Saltpeter"), 2L), GT_OreDictUnificator.get(tPrefix, Materials.get("Sulfur"), 1L), GT_OreDictUnificator.get(tPrefix, Materials.get("Charcoal"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.getDust(Materials.get("Gunpowder"), 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / MATERIAL_UNIT), 8);
        }
        RECIPE_ADDER_INSTANCE.addMixerRecipe(new ItemStack(Items.rotten_flesh, 1, 0), new ItemStack(Items.fermented_spider_eye, 1, 0), ItemList.IC2_Scrap.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("MeatRaw"), 1L), FluidRegistry.getFluidStack("potion.purpledrink", 750), FluidRegistry.getFluidStack("sludge", 1000), ItemList.Food_Chum.get(4L), 128, 24);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wheat"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, ItemList.Food_Dough.get(2L), 32, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chili"), 1L), ItemList.Food_PotatoChips.get(1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, ItemList.Food_ChiliChips.get(1L), 32, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Clay"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Stone"), 3L), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(500L), Materials.get("Concrete").getMolten(576L), NULL_ITEM_STACK, 20, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Redstone"), 5L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Ruby"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, ItemList.IC2_Energium_Dust.get(1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 5L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ruby"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, ItemList.IC2_Energium_Dust.get(9L), 900, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), new ItemStack(Blocks.brown_mushroom, 1), new ItemStack(Items.spider_eye, 1), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, new ItemStack(Items.fermented_spider_eye, 1), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("LiveRoot"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("IronWood"), 2L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 9L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("LiveRoot"), 9L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("IronWood"), 18L), 900, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("NetherQuartz"), 1L), NULL_ITEM_STACK, Materials.get("Water").getFluid(500L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Fluix"), 2L), 20, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("NetherQuartz"), 1L), NULL_ITEM_STACK, GT_ModHandler.getDistilledWater(500L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Fluix"), 2L), 20, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(ItemList.IC2_Fertilizer.get(1L), new ItemStack(Blocks.dirt, 8, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "soil", 8L, 0), 64, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(ItemList.FR_Fertilizer.get(1L), new ItemStack(Blocks.dirt, 8, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "soil", 8L, 0), 64, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(ItemList.FR_Compost.get(1L), new ItemStack(Blocks.dirt, 8, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "soil", 8L, 0), 64, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(ItemList.FR_Mulch.get(1L), new ItemStack(Blocks.dirt, 8, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "soil", 9L, 0), 64, 16);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(new ItemStack(Blocks.sand, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "soil", 2L, 1), 16, 16);

        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Lapis"), 1L), null, null, null, Materials.get("Water").getFluid(125), FluidRegistry.getFluidStack("ic2coolant", 125), null, 256, 48);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Lapis"), 1L), null, null, null, GT_ModHandler.getDistilledWater(1000), FluidRegistry.getFluidStack("ic2coolant", 1000), null, 256, 48);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Silicon"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RedstoneAlloy"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RedstoneAlloy"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Titanium"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ConductiveIron"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ConductiveIron"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("TungstenSteel"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnergeticAlloy"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnergeticAlloy"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderEye"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chrome"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("VibrantAlloy"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Silicon"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ElectricalSteel"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderPearl"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RedstoneAlloy"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("PulsatingIron"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(new ItemStack(Blocks.soul_sand, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Soularium"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ElectricalSteel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Obsidian"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkSteel"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Tin"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Silver"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Platinum"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderiumBase"), 4L), 400, 8);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderiumBase"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Thaumium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderPearl"), 1L), null, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Enderium"), 2L), 200, 8);

        RECIPE_ADDER_INSTANCE.addExtruderRecipe(ItemList.FR_Wax.get(1L), ItemList.Shape_Extruder_Cell.get(0L), ItemList.FR_WaxCapsule.get(1L), 64, 16);
        RECIPE_ADDER_INSTANCE.addExtruderRecipe(ItemList.FR_RefractoryWax.get(1L), ItemList.Shape_Extruder_Cell.get(0L), ItemList.FR_RefractoryCapsule.get(1L), 128, 16);

        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1L), ItemList.IC2_ReBattery.get(1L), Materials.get("Redstone").getMolten(288L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1L), ItemList.Battery_SU_LV_Mercury.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("Mercury").getFluid(1000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_MV.get(1L), ItemList.Battery_SU_MV_Mercury.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("Mercury").getFluid(4000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_HV.get(1L), ItemList.Battery_SU_HV_Mercury.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("Mercury").getFluid(16000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1L), ItemList.Battery_SU_LV_SulfuricAcid.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("SulfuricAcid").getFluid(1000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_MV.get(1L), ItemList.Battery_SU_MV_SulfuricAcid.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("SulfuricAcid").getFluid(4000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.Battery_Hull_HV.get(1L), ItemList.Battery_SU_HV_SulfuricAcid.getWithCharge(1L, Integer.MAX_VALUE), Materials.get("SulfuricAcid").getFluid(16000L), NULL_FLUID_STACK);
        RECIPE_ADDER_INSTANCE.addFluidCannerRecipe(ItemList.TF_Vial_FieryTears.get(1L), ItemList.Bottle_Empty.get(1L), NULL_FLUID_STACK, Materials.get("FierySteel").getFluid(250L));

        Materials tMaterial = Materials.get("Iron");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Iron.get(1L), 16, 8);
        }
        tMaterial = Materials.get("WroughtIron");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Iron.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Gold");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Gold.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Bronze");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Bronze.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Copper");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Copper.get(1L), 16, 8);
        }
        tMaterial = Materials.get("AnnealedCopper");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Copper.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Tin");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Tin.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Lead");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Lead.get(1L), 16, 8);
        }
        tMaterial = Materials.get("Steel");
        if (tMaterial.getStandardMoltenFluid() != null) {
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Steel.get(1L), 16, 8);
        }
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.get("Mercury").getFluid(1000L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Mercury"), 1L), 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.get("Water").getFluid(250L), new ItemStack(Items.snowball, 1, 0), 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), GT_ModHandler.getDistilledWater(250L), new ItemStack(Items.snowball, 1, 0), 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.get("Water").getFluid(1000L), new ItemStack(Blocks.snow, 1, 0), 512, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), GT_ModHandler.getDistilledWater(1000L), new ItemStack(Blocks.snow, 1, 0), 512, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.get("Lava").getFluid(1000L), new ItemStack(Blocks.obsidian, 1, 0), 1024, 16);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.get("Concrete").getMolten(144L), new ItemStack(GregTech_API.sBlockConcretes, 1, 8), 12, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.get("Glowstone").getMolten(576L), new ItemStack(Blocks.glowstone, 1, 0), 12, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.get("Glass").getMolten(144L), new ItemStack(Blocks.glass, 1, 0), 12, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L), Materials.get("Glass").getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Glass"), 1L), 12, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Bottle.get(0L), Materials.get("Glass").getMolten(144L), ItemList.Bottle_Empty.get(1L), 12, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Cylinder.get(0L), Materials.get("Milk").getFluid(250L), ItemList.Food_Cheese.get(1L), 1024, 4);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Cylinder.get(0L), Materials.get("Cheese").getMolten(144L), ItemList.Food_Cheese.get(1L), 64, 8);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.get("Iron").getMolten(4464L), new ItemStack(Blocks.anvil, 1, 0), 128, 16);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.get("WroughtIron").getMolten(4464L), new ItemStack(Blocks.anvil, 1, 0), 128, 16);
        RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.get("Steel").getMolten(4464L), GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.anvil", 1L, 0), 128, 16);

        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(ItemList.Food_Raw_Fries.get(1L), Materials.get("FryingOilHot").getFluid(10L), NULL_FLUID_STACK, ItemList.Food_Fries.get(1L), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 16, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_ModHandler.getIC2Item("dynamite", 1L), Materials.get("Glue").getFluid(10L), NULL_FLUID_STACK, GT_ModHandler.getIC2Item("stickyDynamite", 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 16, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Steel"), 1L), Materials.get("Concrete").getMolten(144L), NULL_FLUID_STACK, GT_ModHandler.getIC2Item("reinforcedStone", 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), Materials.get("Water").getFluid(125L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("HydratedCoal"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 12, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wood"), 1L), Materials.get("Water").getFluid(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Paper"), 1L), Materials.get("Water").getFluid(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 100, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Items.reeds, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Water").getFluid(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 100, 8);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), GT_ModHandler.getDistilledWater(125L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("HydratedCoal"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 12, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wood"), 1L), GT_ModHandler.getDistilledWater(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Paper"), 1L), GT_ModHandler.getDistilledWater(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 100, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Items.reeds, 1, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getDistilledWater(100L), NULL_FLUID_STACK, new ItemStack(Items.paper, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 100, 8);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 1), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 2), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 3), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 4), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 5), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 6), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 7), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 8), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 9), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 10), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 11), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 12), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 13), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 14), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 15), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 1), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 2), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 3), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 4), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 5), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 6), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 7), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 8), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 9), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 10), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 11), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 12), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 13), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 14), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.carpet, 1, 15), Materials.get("Chlorine").getFluid(25L), NULL_FLUID_STACK, new ItemStack(Blocks.carpet, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.hardened_clay, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Chlorine").getFluid(50L), NULL_FLUID_STACK, new ItemStack(Blocks.glass, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.stained_glass_pane, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Chlorine").getFluid(20L), NULL_FLUID_STACK, new ItemStack(Blocks.glass_pane, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 400, 2);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 8), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 9), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 10), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 2), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 11), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 3), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 12), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 4), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 13), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 5), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 14), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 6), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 15), Materials.get("Water").getFluid(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 7), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 8), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 9), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 10), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 2), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 11), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 3), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 12), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 4), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 13), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 5), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 14), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 6), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(GregTech_API.sBlockConcretes, 1, 15), GT_ModHandler.getDistilledWater(250L), NULL_FLUID_STACK, new ItemStack(GregTech_API.sBlockConcretes, 1, 7), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 200, 4);
        for (int j = 0; j < Dyes.dyeRed.getSizeOfFluidList(); j++) {
            RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("RedAlloy"), 1L), Dyes.dyeRed.getFluidDye(j, 72L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "pipeWire", 4L, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeBlue.getSizeOfFluidList(); j++) {
            RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("RedAlloy"), 1L), Dyes.dyeBlue.getFluidDye(j, 72L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "pipeWire", 4L, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeGreen.getSizeOfFluidList(); j++) {
            RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("RedAlloy"), 1L), Dyes.dyeGreen.getFluidDye(j, 72L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "pipeWire", 4L, 2), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeYellow.getSizeOfFluidList(); j++) {
            RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("RedAlloy"), 1L), Dyes.dyeYellow.getFluidDye(j, 72L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "pipeWire", 4L, 3), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 32, 16);
        }
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            for (int j = 0; j < Dyes.getValidColors()[i].getSizeOfFluidList(); j++) {
                if (i != 15) {
                    RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.wool, 1, 0), Dyes.getValidColors()[i].getFluidDye(j, 144L), NULL_FLUID_STACK, new ItemStack(Blocks.wool, 1, 15 - i), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 64, 2);
                }
                RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.glass, 1, 0), Dyes.getValidColors()[i].getFluidDye(j, 18L), NULL_FLUID_STACK, new ItemStack(Blocks.stained_glass, 1, 15 - i), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 64, 2);
                RECIPE_ADDER_INSTANCE.addChemicalBathRecipe(new ItemStack(Blocks.hardened_clay, 1, 0), Dyes.getValidColors()[i].getFluidDye(j, 18L), NULL_FLUID_STACK, new ItemStack(Blocks.stained_hardened_clay, 1, 15 - i), NULL_ITEM_STACK, NULL_ITEM_STACK, null, 64, 2);
            }
        }
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(ItemList.Dye_SquidInk.get(1L), NULL_ITEM_STACK, FluidRegistry.getFluidStack("squidink", 144), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(ItemList.Dye_Indigo.get(1L), NULL_ITEM_STACK, FluidRegistry.getFluidStack("indigo", 144), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(ItemList.Crop_Drop_Indigo.get(1L), NULL_ITEM_STACK, FluidRegistry.getFluidStack("indigo", 144), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(ItemList.Crop_Drop_MilkWart.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Milk"), 1L), GT_ModHandler.getMilk(150L), 1000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(ItemList.Crop_Drop_OilBerry.get(1L), NULL_ITEM_STACK, Materials.get("Oil").getFluid(100L), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.fish, 1, 0), NULL_ITEM_STACK, Materials.get("FishOil").getFluid(4L), 10000, 16, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.fish, 1, 1), NULL_ITEM_STACK, Materials.get("FishOil").getFluid(6L), 10000, 16, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.fish, 1, 2), NULL_ITEM_STACK, Materials.get("FishOil").getFluid(7L), 10000, 16, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.fish, 1, 3), NULL_ITEM_STACK, Materials.get("FishOil").getFluid(3L), 10000, 16, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(new ItemStack(Items.coal, 1, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 1L), Materials.get("Creosote").getFluid(100L), 1000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wood"), 1L), ItemList.IC2_Plantball.get(1L), Materials.get("Creosote").getFluid(5L), 100, 16, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("HydratedCoal"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 1L), Materials.get("Water").getFluid(100L), 10000, 32, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Mercury"), 1L), NULL_ITEM_STACK, Materials.get("Mercury").getFluid(1000L), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Monazite"), 1L), NULL_ITEM_STACK, Materials.get("Helium").getFluid(200L), 10000, 64, 64);

        RECIPE_ADDER_INSTANCE.addFluidSmelterRecipe(new ItemStack(Items.snowball, 1, 0), NULL_ITEM_STACK, Materials.get("Water").getFluid(250L), 10000, 32, 4);
        RECIPE_ADDER_INSTANCE.addFluidSmelterRecipe(new ItemStack(Blocks.snow, 1, 0), NULL_ITEM_STACK, Materials.get("Water").getFluid(1000L), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidSmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ice"), 1L), NULL_ITEM_STACK, Materials.get("Ice").getSolid(1000L), 10000, 128, 4);
        RECIPE_ADDER_INSTANCE.addFluidSmelterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, "phosphor", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphor"), 1L), Materials.get("Lava").getFluid(800L), 1000, 256, 128);

        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(ItemList.IC2_Energium_Dust.get(9L), Materials.get("Water").getFluid(1000L), ItemList.IC2_EnergyCrystal.get(1L), 10000, 500, 256);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 0), Materials.get("Water").getFluid(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 10), 10000, 2000, 24);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 600), Materials.get("Water").getFluid(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 11), 10000, 2000, 24);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 1200), Materials.get("Water").getFluid(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 12), 10000, 2000, 24);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(ItemList.IC2_Energium_Dust.get(9L), GT_ModHandler.getDistilledWater(1000L), ItemList.IC2_EnergyCrystal.get(1L), 10000, 250, 256);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 0), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 10), 10000, 1000, 24);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 600), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 11), 10000, 1000, 24);
        RECIPE_ADDER_INSTANCE.addAutoclaveRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 1L, 1200), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 12), 10000, 1000, 24);

        RECIPE_ADDER_INSTANCE.addSlicerRecipe(ItemList.Food_Dough_Chocolate.get(1L), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Raw_Cookie.get(4L), 128, 4);
        RECIPE_ADDER_INSTANCE.addSlicerRecipe(ItemList.Food_Baked_Bun.get(1L), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Bun.get(2L), 128, 4);
        RECIPE_ADDER_INSTANCE.addSlicerRecipe(ItemList.Food_Baked_Bread.get(1L), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Bread.get(2L), 128, 4);
        RECIPE_ADDER_INSTANCE.addSlicerRecipe(ItemList.Food_Baked_Baguette.get(1L), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Baguette.get(2L), 128, 4);

        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.Cell_Empty.get(1), null, Materials.get("Air").getGas(10000), Materials.get("Nitrogen").getGas(3900), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1), null, null, null, null, null, null, 1600, 8);

        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("NitrogenDioxide"), 4), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1), Materials.get("Water").getFluid(2000), new FluidStack(ItemList.sNitricAcid, 4000), ItemList.Cell_Empty.get(5), 30);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.get("Pentlandite"), 1), null, new FluidStack(ItemList.sNitricAcid, 8000), new FluidStack(ItemList.sNickelSulfate, 9000), ItemList.PlatinumGroupSludgeTiny.get(1), 30);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.get("Chalcopyrite"), 1), null, new FluidStack(ItemList.sNitricAcid, 8000), new FluidStack(ItemList.sBlueVitriol, 9000), ItemList.PlatinumGroupSludgeTiny.get(1), 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(ItemList.Cell_Empty.get(1), null, new FluidStack(ItemList.sBlueVitriol, 9000), Materials.get("SulfuricAcid").getFluid(8000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Copper"), 1), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1), null, null, null, null, null, 900, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(ItemList.Cell_Empty.get(1), null, new FluidStack(ItemList.sGreenVitriol, 9000), Materials.get("SulfuricAcid").getFluid(8000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1), null, null, null, null, null, 900, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(ItemList.Cell_Empty.get(1), null, new FluidStack(ItemList.sNickelSulfate, 9000), Materials.get("SulfuricAcid").getFluid(8000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Nickel"), 1), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1), null, null, null, null, null, 900, 30);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.PlatinumGroupSludge.get(1), null, null, null, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("SiliconDioxide"), 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Gold"), 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Platinum"), 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Palladium"), 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Iridium"), 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Osmium"), 1), new int[]{10000, 10000, 10000, 8000, 6000, 6000}, 900, 30);

        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 1), 100, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 1), 100, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 2), 200, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Diamond"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 3), 100, 480);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderPearl"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 2L, 4), 200, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("NetherQuartz"), 1L), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 5), 300, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(new ItemStack(Items.comparator, 1, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_SILICON, RCHIP, 1L, 6), 300, 120);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 10), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 0L, 13), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 16), 200, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("CertusQuartz"), 1L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 0L, 13), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 16), 200, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Diamond"), 1L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 0L, 14), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 17), 200, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 1L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 0L, 15), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 18), 200, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Silicon"), 1L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 0L, 19), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 20), 200, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Silicon"), 1L), ItemList.Circuit_Parts_Wiring_Basic.get(4L), ItemList.Circuit_Board_Basic.get(1L), 32, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Silicon"), 1L), ItemList.Circuit_Parts_Wiring_Advanced.get(4L), ItemList.Circuit_Board_Advanced.get(1L), 32, 64);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Silicon"), 2L), ItemList.Circuit_Parts_Wiring_Elite.get(4L), ItemList.Circuit_Board_Elite.get(1L), 32, 256);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lapis"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 1L), ItemList.Circuit_Parts_Advanced.get(2L), 32, 64);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lazurite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 1L), ItemList.Circuit_Parts_Advanced.get(2L), 32, 64);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(ItemList.Food_Dough_Sugar.get(4L), ItemList.Shape_Mold_Cylinder.get(0L), ItemList.Food_Raw_Cake.get(1L), 384, 4);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(new ItemStack(Blocks.glass, 1, OreDictionary.WILDCARD_VALUE), ItemList.Shape_Mold_Arrow.get(0L), ItemList.Arrow_Head_Glass_Emtpy.get(1L), 64, 4);
        for (Materials tMat : Materials.values()) {
            if ((tMat.getStandardMoltenFluid() != null) && (tMat.contains(SubTag.SOLDERING_MATERIAL))) {
                int tMultiplier;
                tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1 : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.IC2_Item_Casing_Steel.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("RedAlloy"), 1L), tMat.getMolten(144L * tMultiplier / 8L), ItemList.Circuit_Primitive.get(1L), 16, 8);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Board_Basic.get(1L), ItemList.Circuit_Primitive.get(2L), tMat.getMolten(144L * tMultiplier / 4L), ItemList.Circuit_Basic.get(1L), 32, 16);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L), ItemList.Circuit_Primitive.get(2L), tMat.getMolten(144L * tMultiplier / 4L), ItemList.Circuit_Good.get(1L), 32, 16);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Board_Advanced.get(1L), ItemList.Circuit_Parts_Advanced.get(2L), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Advanced.get(1L), 32, 64);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Board_Advanced.get(1L), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Data.get(1L), 32, 64);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Board_Elite.get(1L), ItemList.Circuit_Data.get(3L), tMat.getMolten(144L * tMultiplier / 1L), ItemList.Circuit_Elite.get(1L), 32, 256);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Board_Elite.get(1L), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L), tMat.getMolten(144L * tMultiplier / 1L), ItemList.Circuit_Master.get(1L), 32, 256);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Data.get(1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Plastic"), 2L), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Tool_DataStick.get(1L), 128, 64);
                for (ItemStack tPlate : new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 1L)}) {
                    RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.lever, 1, OreDictionary.WILDCARD_VALUE), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_Controller.get(1L), 800, 16);
                    RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.redstone_torch, 1, OreDictionary.WILDCARD_VALUE), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_ActivityDetector.get(1L), 800, 16);
                    RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1, OreDictionary.WILDCARD_VALUE), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_FluidDetector.get(1L), 800, 16);
                    RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1, OreDictionary.WILDCARD_VALUE), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_ItemDetector.get(1L), 800, 16);
                    RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("ecMeter", 1L), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_EnergyDetector.get(1L), 800, 16);
                }
            }
        }
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 8), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.get("Silicon"), 1), Materials.get("Glue").getFluid(250L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Graphene"), 1), 480, 240);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.redstone_torch, 2, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), Materials.get("Concrete").getMolten(144L), new ItemStack(Items.repeater, 1, 0), 800, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.leather, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.lead, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Glue").getFluid(50L), new ItemStack(Items.name_tag, 1, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Paper"), 8L), new ItemStack(Items.compass, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, new ItemStack(Items.map, 1, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Tantalum"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Manganese"), 1L), Materials.get("Plastic").getMolten(144L), ItemList.Battery_RE_ULV_Tantalum.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Elite.get(2L), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(18L), NULL_FLUID_STACK, ItemList.Tool_DataOrb.get(1L), 512, 256);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Circuit_Master.get(2L), ItemList.Circuit_Parts_Crystal_Chip_Master.get(18L), NULL_FLUID_STACK, ItemList.Energy_LapotronicOrb.get(1L), 512, 1024);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Europium"), 4L), ItemList.Energy_LapotronicOrb.get(8L), NULL_FLUID_STACK, ItemList.Energy_LapotronicOrb2.get(1L), 2048, 4096);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Neutronium"), 16L), ItemList.Energy_LapotronicOrb2.get(8L), NULL_FLUID_STACK, ItemList.ZPM2.get(1L), 32768, 4096);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfLife1", 4L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfLife2", 1L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping1", 4L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping2", 1L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping2", 4L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping3", 1L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfLife2", 1L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfLife1", 4L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping2", 1L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping1", 4L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping3", 1L, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_TF, "item.charmOfKeeping2", 4L, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 16), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 20), Materials.get("Redstone").getMolten(144L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 23), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 17), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 20), Materials.get("Redstone").getMolten(144L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 24), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 18), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 20), Materials.get("Redstone").getMolten(144L), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 22), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("CertusQuartz"), 1L), new ItemStack(Blocks.sand, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 2L, 0), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NetherQuartz"), 1L), new ItemStack(Blocks.sand, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 2L, 600), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Fluix"), 1L), new ItemStack(Blocks.sand, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_AE, AE_SEED, 2L, 1200), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.FR_Wax.get(6L), new ItemStack(Items.string, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Water").getFluid(600L), GT_ModHandler.getModItem(MOD_ID_FR, "candle", 24L, 0), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.FR_Wax.get(2L), ItemList.FR_Silk.get(1L), Materials.get("Water").getFluid(200L), GT_ModHandler.getModItem(MOD_ID_FR, "candle", 8L, 0), 16, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.FR_Silk.get(9L), ItemList.Circuit_Integrated.getWithDamage(0L, 9L), Materials.get("Water").getFluid(500L), GT_ModHandler.getModItem(MOD_ID_FR, "craftingMaterial", 1L, 3), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_FR, "propolis", 5L, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_FR, "craftingMaterial", 1L, 1), 16, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_FR, "sturdyMachine", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Diamond"), 4L), Materials.get("Water").getFluid(5000L), ItemList.FR_Casing_Hardened.get(1L), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Bronze"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), NULL_FLUID_STACK, ItemList.FR_Casing_Sturdy.get(1L), 32, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Tin"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 6L), Materials.get("Water").getFluid(1000L), GT_ModHandler.getModItem(MOD_ID_FR, CHIPS, 1L, 0), 16, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Bronze"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 6L), Materials.get("Water").getFluid(1000L), GT_ModHandler.getModItem(MOD_ID_FR, CHIPS, 1L, 1), 32, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Iron"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 6L), Materials.get("Water").getFluid(1000L), GT_ModHandler.getModItem(MOD_ID_FR, CHIPS, 1L, 2), 48, 24);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("WroughtIron"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 6L), Materials.get("Water").getFluid(1000L), GT_ModHandler.getModItem(MOD_ID_FR, CHIPS, 1L, 2), 48, 24);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Gold"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 6L), Materials.get("Water").getFluid(1000L), GT_ModHandler.getModItem(MOD_ID_FR, CHIPS, 1L, 3), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Creosote").getFluid(1000L), new ItemStack(Blocks.torch, 6, 0), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getModItem(MOD_ID_FR, "craftingMaterial", 5L, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderPearl"), 1L), 64, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.piston, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.slime_ball, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, new ItemStack(Blocks.sticky_piston, 1, 0), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.piston, 1, OreDictionary.WILDCARD_VALUE), ItemList.IC2_Resin.get(1L), NULL_FLUID_STACK, new ItemStack(Blocks.sticky_piston, 1, 0), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.piston, 1, OreDictionary.WILDCARD_VALUE), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("Glue").getFluid(100L), new ItemStack(Blocks.sticky_piston, 1, 0), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 3L), GT_ModHandler.getIC2Item("carbonMesh", 3L), Materials.get("Glue").getFluid(300L), ItemList.Duct_Tape.get(1L), 100, 64);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Paper"), 3L), new ItemStack(Items.leather, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Glue").getFluid(20L), new ItemStack(Items.book, 1, 0), 32, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Paper_Printed_Pages.get(1L), new ItemStack(Items.leather, 1, OreDictionary.WILDCARD_VALUE), Materials.get("Glue").getFluid(20L), new ItemStack(Items.written_book, 1, 0), 32, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.IC2_Item_Casing_Tin.get(4L), new ItemStack(Blocks.glass_pane, 1, OreDictionary.WILDCARD_VALUE), NULL_FLUID_STACK, ItemList.Cell_Universal_Fluid.get(1L), 128, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Baked_Cake.get(1L), new ItemStack(Items.egg, 1, 0), Materials.get("Milk").getFluid(3000L), new ItemStack(Items.cake, 1, 0), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), NULL_FLUID_STACK, ItemList.Food_Sliced_Buns.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Bread.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), NULL_FLUID_STACK, ItemList.Food_Sliced_Breads.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Baguette.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), NULL_FLUID_STACK, ItemList.Food_Sliced_Baguettes.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, ItemList.Food_Sliced_Bun.get(2L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Breads.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, ItemList.Food_Sliced_Bread.get(2L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Baguettes.get(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), NULL_FLUID_STACK, ItemList.Food_Sliced_Baguette.get(2L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("MeatCooked"), 1L), NULL_FLUID_STACK, ItemList.Food_Burger_Meat.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("MeatCooked"), 1L), NULL_FLUID_STACK, ItemList.Food_Burger_Meat.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Food_Chum.get(1L), NULL_FLUID_STACK, ItemList.Food_Burger_Chum.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1L), ItemList.Food_Chum.get(1L), NULL_FLUID_STACK, ItemList.Food_Burger_Chum.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Food_Sliced_Cheese.get(3L), NULL_FLUID_STACK, ItemList.Food_Burger_Cheese.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1L), ItemList.Food_Sliced_Cheese.get(3L), NULL_FLUID_STACK, ItemList.Food_Burger_Cheese.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Flat_Dough.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("MeatCooked"), 1L), NULL_FLUID_STACK, ItemList.Food_Raw_Pizza_Meat.get(1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.Food_Flat_Dough.get(1L), ItemList.Food_Sliced_Cheese.get(3L), NULL_FLUID_STACK, ItemList.Food_Raw_Pizza_Cheese.get(1L), 100, 4);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Copper"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 0), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("AnnealedCopper"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 0), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Tin"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 1), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Bronze"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 2), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Iron"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 3), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("WroughtIron"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 3), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Gold"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 4), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Diamond"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 5), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 7), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Rubber"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 8), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Emerald"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 9), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Apatite"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 10), 64, 32);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Lapis"), 5L), Materials.get("Glass").getMolten(72L), GT_ModHandler.getModItem(MOD_ID_FR, TTUBE, 4L, 11), 64, 32);

        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("Oil").getFluid(16L), Materials.get("Fuel").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), Materials.get("Oil").getFluid(16L), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), Materials.get("Oil").getFluid(16L), Materials.get("Methane").getGas(15L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.get("Oil").getFluid(16L), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), Materials.get("Oil").getFluid(16L), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
        if (FluidRegistry.getFluid(GC_OIL) != null) {
            RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(FluidRegistry.getFluid(GC_OIL), 16), Materials.get("Fuel").getFluid(16L), 32, 16, false);
            RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(FluidRegistry.getFluid(GC_OIL), 16), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
            RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(FluidRegistry.getFluid(GC_OIL), 16), Materials.get("Methane").getGas(15L), 32, 16, false);
            RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new FluidStack(FluidRegistry.getFluid(GC_OIL), 16), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
            RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new FluidStack(FluidRegistry.getFluid(GC_OIL), 16), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
            RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(FluidRegistry.getFluid(GC_OIL), 64), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, null, 16, 64);
        }

        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(ItemList.sOilLight, 32), Materials.get("Fuel").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(ItemList.sOilLight, 32), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(ItemList.sOilLight, 32), Materials.get("Methane").getGas(15L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new FluidStack(ItemList.sOilLight, 32), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new FluidStack(ItemList.sOilLight, 32), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(ItemList.sOilMedium, 16), Materials.get("Fuel").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(ItemList.sOilMedium, 16), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(ItemList.sOilMedium, 16), Materials.get("Methane").getGas(15L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new FluidStack(ItemList.sOilMedium, 16), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new FluidStack(ItemList.sOilMedium, 16), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(ItemList.sOilHeavy, 8), Materials.get("Fuel").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(ItemList.sOilHeavy, 8), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(ItemList.sOilHeavy, 8), Materials.get("Methane").getGas(15L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new FluidStack(ItemList.sOilHeavy, 8), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new FluidStack(ItemList.sOilHeavy, 8), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(ItemList.sOilExtraHeavy, 4), Materials.get("Fuel").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(ItemList.sOilExtraHeavy, 4), Materials.get("Glyceryl").getFluid(1L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(ItemList.sOilExtraHeavy, 4), Materials.get("Methane").getGas(15L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new FluidStack(ItemList.sOilExtraHeavy, 4), Materials.get("Lubricant").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new FluidStack(ItemList.sOilExtraHeavy, 4), Materials.get("SulfuricAcid").getFluid(16L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new FluidStack(ItemList.sNaturalGas, 16), Materials.get("Methane").getGas(30L), 32, 16, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.get("Creosote").getFluid(3L), Materials.get("Lubricant").getFluid(1L), 16, 24, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.get("SeedOil").getFluid(4L), Materials.get("Lubricant").getFluid(1L), 16, 24, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.get("FishOil").getFluid(3L), Materials.get("Lubricant").getFluid(1L), 16, 24, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("Biomass").getFluid(40L), Materials.get("Ethanol").getFluid(12L), 16, 24, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.get("Biomass").getFluid(40L), Materials.get("Water").getFluid(12L), 16, 24, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.get("Water").getFluid(5L), GT_ModHandler.getDistilledWater(4L), 16, 8, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), FluidRegistry.getFluidStack("potion.potatojuice", 2), FluidRegistry.getFluidStack("potion.vodka", 1), 16, 16, true);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), FluidRegistry.getFluidStack("potion.lemonade", 2), FluidRegistry.getFluidStack("potion.alcopops", 1), 16, 16, true);

        RECIPE_ADDER_INSTANCE.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("Water").getFluid(6L), Materials.get("Water").getGas(960L), 30, 32);
        RECIPE_ADDER_INSTANCE.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), GT_ModHandler.getDistilledWater(6L), Materials.get("Water").getGas(960L), 30, 32);
        RECIPE_ADDER_INSTANCE.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("SeedOil").getFluid(16L), Materials.get("FryingOilHot").getFluid(16L), 16, 32);
        RECIPE_ADDER_INSTANCE.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("FishOil").getFluid(16L), Materials.get("FryingOilHot").getFluid(16L), 16, 32);

        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Talc"), 1L), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Soapstone"), 1L), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Talc"), 1L), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Soapstone"), 1L), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Talc"), 1L), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Soapstone"), 1L), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid(LUBE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid(LUBE), false);
        for (Fluid tFluid : new Fluid[]{FluidRegistry.WATER, GT_ModHandler.getDistilledWater(1L).getFluid()}) {
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Milk"), 1L), tFluid, FluidRegistry.getFluid("milk"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wheat"), 1L), tFluid, FluidRegistry.getFluid("potion.wheatyjuice"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Potassium"), 1L), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Magnesium"), 1L), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Salt"), 1L), tFluid, FluidRegistry.getFluid("potion.saltywater"), true);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RockSalt"), 1L), tFluid, FluidRegistry.getFluid("potion.saltywater"), true);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 1L), tFluid, FluidRegistry.getFluid(P_TH), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 1L), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.magma_cream, 1, 0), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fermented_spider_eye, 1, 0), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.spider_eye, 1, 0), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.speckled_melon, 1, 0), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.ghast_tear, 1, 0), tFluid, FluidRegistry.getFluid(P_MU), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.nether_wart, 1, 0), tFluid, FluidRegistry.getFluid(P_AW), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Blocks.red_mushroom, 1, 0), tFluid, FluidRegistry.getFluid("potion.poison"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fish, 1, 3), tFluid, FluidRegistry.getFluid("potion.poison.strong"), true);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(ItemList.IC2_Grin_Powder.get(1L), tFluid, FluidRegistry.getFluid("potion.poison.strong"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.reeds, 1, 0), tFluid, FluidRegistry.getFluid("potion.reedwater"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.apple, 1, 0), tFluid, FluidRegistry.getFluid("potion.applejuice"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.golden_apple, 1, 0), tFluid, FluidRegistry.getFluid("potion.goldenapplejuice"), true);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.golden_apple, 1, 1), tFluid, FluidRegistry.getFluid("potion.idunsapplejuice"), true);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(ItemList.IC2_Hops.get(1L), tFluid, FluidRegistry.getFluid("potion.hopsjuice"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coffee"), 1L), tFluid, FluidRegistry.getFluid("potion.darkcoffee"), false);
            RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chili"), 1L), tFluid, FluidRegistry.getFluid("potion.chillysauce"), false);

            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphate"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 3L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(1L), 100);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(1L), 100);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(4L), 400);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphate"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 3L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Apatite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Apatite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(4L), 400);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Apatite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphate"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Apatite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 3L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Apatite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glauconite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glauconite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(4L), 400);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glauconite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphate"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glauconite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 3L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glauconite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GlauconiteSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GlauconiteSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(4L), 400);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GlauconiteSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphate"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(3L), 300);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GlauconiteSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 3L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
            RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GlauconiteSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 1L), new FluidStack(tFluid, 1000), NULL_FLUID_STACK, ItemList.IC2_Fertilizer.get(2L), 200);
        }
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chili"), 1L), FluidRegistry.getFluid("potion.chillysauce"), FluidRegistry.getFluid("potion.hotsauce"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chili"), 1L), FluidRegistry.getFluid("potion.hotsauce"), FluidRegistry.getFluid("potion.diabolosauce"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chili"), 1L), FluidRegistry.getFluid("potion.diabolosauce"), FluidRegistry.getFluid("potion.diablosauce"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coffee"), 1L), FluidRegistry.getFluid("milk"), FluidRegistry.getFluid("potion.coffee"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Cocoa"), 1L), FluidRegistry.getFluid("milk"), FluidRegistry.getFluid("potion.darkchocolatemilk"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(ItemList.IC2_Hops.get(1L), FluidRegistry.getFluid("potion.wheatyjuice"), FluidRegistry.getFluid("potion.wheatyhopsjuice"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Wheat"), 1L), FluidRegistry.getFluid("potion.hopsjuice"), FluidRegistry.getFluid("potion.wheatyhopsjuice"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.tea"), FluidRegistry.getFluid("potion.sweettea"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.coffee"), FluidRegistry.getFluid("potion.cafeaulait"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.cafeaulait"), FluidRegistry.getFluid("potion.laitaucafe"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.lemonjuice"), FluidRegistry.getFluid("potion.lemonade"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.darkcoffee"), FluidRegistry.getFluid("potion.darkcafeaulait"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid("potion.darkchocolatemilk"), FluidRegistry.getFluid("potion.chocolatemilk"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ice"), 1L), FluidRegistry.getFluid("potion.tea"), FluidRegistry.getFluid("potion.icetea"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Gunpowder"), 1L), FluidRegistry.getFluid("potion.lemonade"), FluidRegistry.getFluid("potion.cavejohnsonsgrenadejuice"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fish, 1, 3), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.waterbreathing"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.magma_cream, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.fireresistance"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.golden_carrot, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.nightvision"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fermented_spider_eye, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid(P_WE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.spider_eye, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.poison"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.speckled_melon, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.health"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.ghast_tear, 1, 0), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.regen"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.speed"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 1L), FluidRegistry.getFluid(P_AW), FluidRegistry.getFluid("potion.strength"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid(P_MU), FluidRegistry.getFluid("potion.purpledrink"), true);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fermented_spider_eye, 1, 0), FluidRegistry.getFluid(P_MU), FluidRegistry.getFluid(P_WE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.fermented_spider_eye, 1, 0), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid(P_WE), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.spider_eye, 1, 0), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid("potion.poison.strong"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.speckled_melon, 1, 0), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid("potion.health.strong"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(new ItemStack(Items.ghast_tear, 1, 0), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid("potion.regen.strong"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sugar"), 1L), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid("potion.speed.strong"), false);
        RECIPE_ADDER_INSTANCE.addBrewingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 1L), FluidRegistry.getFluid(P_TH), FluidRegistry.getFluid("potion.strength.strong"), false);

        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("milk", 50), FluidRegistry.getFluidStack(P_MU, 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.lemonjuice", 50), FluidRegistry.getFluidStack("potion.limoncello", 25), 1024, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.applejuice", 50), FluidRegistry.getFluidStack("potion.cider", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.goldenapplejuice", 50), FluidRegistry.getFluidStack("potion.goldencider", 25), 1024, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.idunsapplejuice", 50), FluidRegistry.getFluidStack("potion.notchesbrew", 25), 1024, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.reedwater", 50), FluidRegistry.getFluidStack("potion.rum", 25), 1024, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.rum", 50), FluidRegistry.getFluidStack("potion.piratebrew", 10), 2048, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.grapejuice", 50), FluidRegistry.getFluidStack("potion.wine", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wine", 50), FluidRegistry.getFluidStack("potion.vinegar", 10), 2048, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wheatyjuice", 50), FluidRegistry.getFluidStack("potion.scotch", 25), 1024, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.scotch", 50), FluidRegistry.getFluidStack("potion.glenmckenner", 10), 2048, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wheatyhopsjuice", 50), FluidRegistry.getFluidStack("potion.beer", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.hopsjuice", 50), FluidRegistry.getFluidStack("potion.darkbeer", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.darkbeer", 50), FluidRegistry.getFluidStack("potion.dragonblood", 10), 2048, true);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack(P_AW, 50), FluidRegistry.getFluidStack(P_WE, 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack(P_MU, 50), FluidRegistry.getFluidStack(P_WE, 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack(P_TH, 50), FluidRegistry.getFluidStack(P_WE, 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.health", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.waterbreathing", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.nightvision", 50), FluidRegistry.getFluidStack("potion.invisibility", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.fireresistance", 50), FluidRegistry.getFluidStack("potion.slowness", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed", 50), FluidRegistry.getFluidStack("potion.slowness", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength", 50), FluidRegistry.getFluidStack(P_WE, 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen", 50), FluidRegistry.getFluidStack("potion.poison", 25), 1024, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison.strong", 50), FluidRegistry.getFluidStack("potion.damage.strong", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.health.strong", 50), FluidRegistry.getFluidStack("potion.damage.strong", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed.strong", 50), FluidRegistry.getFluidStack("potion.slowness.strong", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength.strong", 50), FluidRegistry.getFluidStack("potion.weakness.strong", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen.strong", 50), FluidRegistry.getFluidStack("potion.poison.strong", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison.long", 50), FluidRegistry.getFluidStack("potion.damage.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.waterbreathing.long", 50), FluidRegistry.getFluidStack("potion.damage.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.nightvision.long", 50), FluidRegistry.getFluidStack("potion.invisibility.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.fireresistance.long", 50), FluidRegistry.getFluidStack("potion.slowness.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed.long", 50), FluidRegistry.getFluidStack("potion.slowness.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength.long", 50), FluidRegistry.getFluidStack("potion.weakness.long", 10), 2048, false);
        RECIPE_ADDER_INSTANCE.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen.long", 50), FluidRegistry.getFluidStack("potion.poison.long", 10), 2048, false);

        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_PotatoChips.get(1L), ItemList.Food_PotatoChips.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Potato_On_Stick.get(1L), ItemList.Food_Potato_On_Stick_Roasted.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Bun.get(1L), ItemList.Food_Baked_Bun.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Bread.get(1L), ItemList.Food_Baked_Bread.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Baguette.get(1L), ItemList.Food_Baked_Baguette.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Veggie.get(1L), ItemList.Food_Baked_Pizza_Veggie.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Cheese.get(1L), ItemList.Food_Baked_Pizza_Cheese.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Meat.get(1L), ItemList.Food_Baked_Pizza_Meat.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Baguette.get(1L), ItemList.Food_Baked_Baguette.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Cake.get(1L), ItemList.Food_Baked_Cake.get(1L));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Cookie.get(1L), new ItemStack(Items.cookie, 1));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Items.slime_ball, 1), ItemList.IC2_Resin.get(1L));

        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.bookshelf, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.book, 3, 0));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Items.slime_ball, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Rubber"), 2L));
        GT_ModHandler.addExtractionRecipe(ItemList.IC2_Resin.get(1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Rubber"), 3L));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("rubberSapling", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Rubber"), 1L));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("rubberLeaves", 16L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Rubber"), 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Cell_Air.get(1L), ItemList.Cell_Empty.get(1L));
        if (Loader.isModLoaded(MOD_ID_EBXL)) {
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, "waterplant1", 1, 0), new ItemStack(Items.dye, 4, 2));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, "vines", 1, 0), new ItemStack(Items.dye, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 11), new ItemStack(Items.dye, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 10), new ItemStack(Items.dye, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 9), new ItemStack(Items.dye, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 8), new ItemStack(Items.dye, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 7), new ItemStack(Items.dye, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 6), new ItemStack(Items.dye, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 5), new ItemStack(Items.dye, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 0), new ItemStack(Items.dye, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 4), new ItemStack(Items.dye, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 3), new ItemStack(Items.dye, 4, 13));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F1, 1, 3), new ItemStack(Items.dye, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 2), new ItemStack(Items.dye, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F1, 1, 1), new ItemStack(Items.dye, 4, 12));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 15), new ItemStack(Items.dye, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 14), new ItemStack(Items.dye, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 13), new ItemStack(Items.dye, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 12), new ItemStack(Items.dye, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 11), new ItemStack(Items.dye, 4, 7));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F1, 1, 7), new ItemStack(Items.dye, 4, 7));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F1, 1, 2), new ItemStack(Items.dye, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 13), new ItemStack(Items.dye, 4, 6));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 6), new ItemStack(Items.dye, 4, 12));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 5), new ItemStack(Items.dye, 4, 10));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 2), new ItemStack(Items.dye, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 1), new ItemStack(Items.dye, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 0), new ItemStack(Items.dye, 4, 13));


            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 7), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 0));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 1), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F3, 1, 12), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 4), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F1, 1, 6), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 2));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 8), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 3));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, F2, 1, 3), GT_ModHandler.getModItem(MOD_ID_EBXL, EBDYE, 1, 3));

            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 0), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 1), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 2), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 3), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 4), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 5), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 6), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS1, 4, 7), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS2, 4, 0), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS2, 4, 1), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS2, 4, 2), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS2, 4, 3), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_EBXL, EBS2, 4, 4), ItemList.IC2_Plantball.get(1));

        }

        GT_ModHandler.addCompressionRecipe(ItemList.IC2_Compressed_Coal_Chunk.get(1L), ItemList.IC2_Industrial_Diamond.get(1L));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uranium"), 1L), GT_ModHandler.getIC2Item("Uran238", 1L));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uranium235"), 1L), GT_ModHandler.getIC2Item("Uran235", 1L));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Plutonium"), 1L), GT_ModHandler.getIC2Item("Plutonium", 1L));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Uranium235"), 1L), GT_ModHandler.getIC2Item("smallUran235", 1L));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Plutonium"), 1L), GT_ModHandler.getIC2Item("smallPlutonium", 1L));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.ice, 2, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.packed_ice, 1, 0));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ice"), 1L), new ItemStack(Blocks.ice, 1, 0));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("CertusQuartz"), 4L), GT_ModHandler.getModItem(MOD_ID_AE, "tile.BlockQuartz", 1L));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 8L, 10), GT_ModHandler.getModItem(MOD_ID_AE, "tile.BlockQuartz", 1L));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 8L, 11), new ItemStack(Blocks.quartz_block, 1, 0));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 8L, 12), GT_ModHandler.getModItem(MOD_ID_AE, "tile.BlockFluix", 1L));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Items.quartz, 4, 0), new ItemStack(Blocks.quartz_block, 1, 0));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Items.wheat, 9, 0), new ItemStack(Blocks.hay_block, 1, 0));
        GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 4L), new ItemStack(Blocks.glowstone, 1));
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Graphite"), 9L), NULL_ITEM_STACK, 500, 48);
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.ore, Materials.get("Graphite"), 1L));
        GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.ore, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.get("Graphite"), 1L));
        GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.oreBlackgranite, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreEndstone, Materials.get("Graphite"), 1L));
        GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.oreEndstone, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.get("Graphite"), 1L));
        GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.oreNetherrack, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 1L));
        GT_ModHandler.removeFurnaceSmelting(GT_OreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.get("Graphite"), 1L));
        GT_ModHandler.addSmeltingRecipe(GT_OreDictUnificator.get(OrePrefixes.oreRedgranite, Materials.get("Graphite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphite"), 1L));

        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem(MOD_ID_AE, "tile.BlockSkyStone", 1L, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 45), NULL_ITEM_STACK, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem(MOD_ID_AE, "tile.BlockSkyChest", 1L, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 8L, 45), NULL_ITEM_STACK, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.blaze_rod, 1), new ItemStack(Items.blaze_powder, 3), new ItemStack(Items.blaze_powder, 1), 50, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem(MOD_ID_RC, "cube.crushed.obsidian", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Obsidian"), 1L), NULL_ITEM_STACK, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.flint, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Flint"), 4L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Flint"), 1L), 40, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.red_mushroom, 1, OreDictionary.WILDCARD_VALUE), ItemList.IC2_Grin_Powder.get(1L));
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.item_frame, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.leather, 1), GT_OreDictUnificator.getDust(Materials.get("Wood"), OrePrefixes.stick.mMaterialAmount * 4L), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.bow, 1, 0), new ItemStack(Items.string, 3), GT_OreDictUnificator.getDust(Materials.get("Wood"), OrePrefixes.stick.mMaterialAmount * 3L), 95, false);

        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.stone, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.cobblestone, 1, 0), new ItemStack(Blocks.gravel, 1, 0), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.sandstone, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sand, 1, 0), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.ice, 1, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ice"), 1L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.packed_ice, 1, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ice"), 2L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.hardened_clay, 1, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Clay"), 1L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Clay"), 1L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(Items.brick, 3, 0), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.nether_brick, 1, 0), new ItemStack(Items.netherbrick, 3, 0), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glass"), 1L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.glass, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glass"), 1L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.stained_glass_pane, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Glass"), 3L), 16, 10);
        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(new ItemStack(Blocks.glass_pane, 1, OreDictionary.WILDCARD_VALUE), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Glass"), 3L), 16, 10);

        RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ore", 1), GT_OreDictUnificator.get(OrePrefixes.crushed, Materials.get("Endium"), 1), 16, 10);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ore", 1), GT_OreDictUnificator.get(OrePrefixes.crushed, Materials.get("Endium"), 2), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Endstone"), 1), 50, NULL_ITEM_STACK, 0, true);
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Endium"), GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ingot", 1), true, true);

        RECIPE_ADDER_INSTANCE.addAmplifier(ItemList.IC2_Scrap.get(9L), 180, 1);
        RECIPE_ADDER_INSTANCE.addAmplifier(ItemList.IC2_Scrapbox.get(1L), 180, 1);

        RECIPE_ADDER_INSTANCE.addBoxingRecipe(ItemList.IC2_Scrap.get(9L), ItemList.Schematic_3by3.get(0L), ItemList.IC2_Scrapbox.get(1L), 16, 1);
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(ItemList.Food_Fries.get(1L), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.get("Paper"), 1L), ItemList.Food_Packaged_Fries.get(1L), 64, 16);
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(ItemList.Food_PotatoChips.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.get("Aluminium"), 1L), ItemList.Food_Packaged_PotatoChips.get(1L), 64, 16);
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(ItemList.Food_ChiliChips.get(1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.get("Aluminium"), 1L), ItemList.Food_Packaged_ChiliChips.get(1L), 64, 16);

        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Tungsten"), 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Steel"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("TungstenSteel"), 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), (int) Math.max(Materials.get("TungstenSteel").getMass() / 80L, 1L) * Materials.get("TungstenSteel").getBlastFurnaceTemp(), 480, Materials.get("TungstenSteel").getBlastFurnaceTemp());
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Tungsten"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Carbon"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("TungstenCarbide"), 2L), NULL_ITEM_STACK, (int) Math.max(Materials.get("TungstenCarbide").getMass() / 40L, 1L) * Materials.get("TungstenCarbide").getBlastFurnaceTemp(), 480, Materials.get("TungstenCarbide").getBlastFurnaceTemp());
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Vanadium"), 3L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Gallium"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("VanadiumGallium"), 4L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 2L), (int) Math.max(Materials.get("VanadiumGallium").getMass() / 40L, 1L) * Materials.get("VanadiumGallium").getBlastFurnaceTemp(), 480, Materials.get("VanadiumGallium").getBlastFurnaceTemp());
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Niobium"), 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Titanium"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("NiobiumTitanium"), 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), (int) Math.max(Materials.get("NiobiumTitanium").getMass() / 80L, 1L) * Materials.get("NiobiumTitanium").getBlastFurnaceTemp(), 480, Materials.get("NiobiumTitanium").getBlastFurnaceTemp());
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Nickel"), 4L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Chrome"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("Nichrome"), 5L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 2L), (int) Math.max(Materials.get("Nichrome").getMass() / 32L, 1L) * Materials.get("Nichrome").getBlastFurnaceTemp(), 480, Materials.get("Nichrome").getBlastFurnaceTemp());
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ruby"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkAsh"), 1L), 400, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Ruby"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkAsh"), 1L), 320, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("GreenSapphire"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkAsh"), 1L), 400, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("GreenSapphire"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkAsh"), 1L), 320, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sapphire"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), NULL_ITEM_STACK, 400, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Sapphire"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), NULL_ITEM_STACK, 320, 100, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ilmenite"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("WroughtIron"), 4L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Titanium"), 4L), 800, 480, 1700);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Ilmenite"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("WroughtIron"), 5L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Titanium"), 5L), 640, 480, 1700);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Galena"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Silver"), 4L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Lead"), 4L), 400, 480, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Galena"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Silver"), 5L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Lead"), 5L), 320, 480, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Magnetite"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("WroughtIron"), 4L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 400, 480, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Magnetite"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("WroughtIron"), 5L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Ash"), 1L), 320, 480, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Iron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 480, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("PigIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 100, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("WroughtIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 100, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ShadowIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ShadowSteel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 480, 120, 1100);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("MeteoricIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("MeteoricSteel"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkAsh"), 1L), 480, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Copper"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("AnnealedCopper"), 1L), NULL_ITEM_STACK, 480, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Copper"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("AnnealedCopper"), 1L), NULL_ITEM_STACK, 480, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("BandedIron"), 5L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("WroughtIron"), 3L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Silicon"), 1L), 100, 120, 1200);

        //EnderIO Compat.
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RedstoneAlloy"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("RedstoneAlloy"), 1L), NULL_ITEM_STACK, 800, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("RedstoneAlloy"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("RedstoneAlloy"), 1L), NULL_ITEM_STACK, 800, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("RedstoneAlloy"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("RedstoneAlloy"), 1L), NULL_ITEM_STACK, 800, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ConductiveIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ConductiveIron"), 1L), NULL_ITEM_STACK, 1200, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("ConductiveIron"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ConductiveIron"), 1L), NULL_ITEM_STACK, 1200, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("ConductiveIron"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ConductiveIron"), 1L), NULL_ITEM_STACK, 1200, 120, 1200);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnergeticAlloy"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("EnergeticAlloy"), 1L), NULL_ITEM_STACK, 1600, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("EnergeticAlloy"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("EnergeticAlloy"), 1L), NULL_ITEM_STACK, 1600, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("EnergeticAlloy"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("EnergeticAlloy"), 1L), NULL_ITEM_STACK, 1600, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("VibrantAlloy"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("VibrantAlloy"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("VibrantAlloy"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("VibrantAlloy"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("VibrantAlloy"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("VibrantAlloy"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("ElectricalSteel"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ElectricalSteel"), 1L), NULL_ITEM_STACK, 1200, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("ElectricalSteel"), 9L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ElectricalSteel"), 1L), NULL_ITEM_STACK, 1200, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("ElectricalSteel"), 4L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("ElectricalSteel"), 1L), NULL_ITEM_STACK, 1200, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("PulsatingIron"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("PulsatingIron"), 1L), NULL_ITEM_STACK, 1600, 120, 1600);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("PulsatingIron"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("PulsatingIron"), 1L), NULL_ITEM_STACK, 1600, 120, 1600);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("PulsatingIron"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("PulsatingIron"), 1L), NULL_ITEM_STACK, 1600, 120, 1600);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Soularium"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Soularium"), 1L), NULL_ITEM_STACK, 1000, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Soularium"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Soularium"), 1L), NULL_ITEM_STACK, 1000, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Soularium"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Soularium"), 1L), NULL_ITEM_STACK, 1000, 120, 1000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkSteel"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("DarkSteel"), 1L), NULL_ITEM_STACK, 1000, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkSteel"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("DarkSteel"), 1L), NULL_ITEM_STACK, 1000, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("DarkSteel"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("DarkSteel"), 1L), NULL_ITEM_STACK, 1000, 120, 1500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("EnderiumBase"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("EnderiumBase"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("EnderiumBase"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("EnderiumBase"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("EnderiumBase"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("EnderiumBase"), 1L), NULL_ITEM_STACK, 3000, 120, 3000);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Enderium"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("Enderium"), 1L), NULL_ITEM_STACK, 3500, 120, 3500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Enderium"), 9L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("Enderium"), 1L), NULL_ITEM_STACK, 3500, 120, 3500);
        RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Enderium"), 4L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.get("Enderium"), 1L), NULL_ITEM_STACK, 3500, 120, 3500);

        RECIPE_ADDER_INSTANCE.addCannerRecipe(GT_ModHandler.getIC2Item("fuelRod", 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Lithium"), 1L), GT_ModHandler.getIC2Item("reactorLithiumCell", 1, 1), null, 16, 64);
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_ModHandler.getIC2Item("TritiumCell", 1), GT_ModHandler.getIC2Item("fuelRod", 1), Materials.get("Tritium").getGas(32), 10000, 16, 64);
        RECIPE_ADDER_INSTANCE.addCannerRecipe(GT_ModHandler.getIC2Item("fuelRod", 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Thorium"), 3), ItemList.ThoriumCell_1.get(1L), null, 30, 16);
        RECIPE_ADDER_INSTANCE.addCannerRecipe(GT_ModHandler.getIC2Item("fuelRod", 1), GT_ModHandler.getIC2Item("UranFuel", 1), GT_ModHandler.getIC2Item("reactorUraniumSimple", 1, 1), null, 30, 16);
        RECIPE_ADDER_INSTANCE.addCannerRecipe(GT_ModHandler.getIC2Item("fuelRod", 1), GT_ModHandler.getIC2Item("MOXFuel", 1), GT_ModHandler.getIC2Item("reactorMOXSimple", 1, 1), null, 30, 16);

        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Lithium").getMolten(16), Materials.get("Tungsten").getMolten(16), Materials.get("Iridium").getMolten(16), 32, 32768, 300000000);
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Deuterium").getGas(125), Materials.get("Tritium").getGas(125), Materials.get("Helium").getPlasma(125), 16, 4096, 40000000);  //Mark 1 Cheap //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Deuterium").getGas(125), Materials.get("Helium_3").getGas(125), Materials.get("Helium").getPlasma(125), 16, 2048, 60000000); //Mark 1 Expensive //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Carbon").getMolten(125), Materials.get("Helium_3").getGas(125), Materials.get("Oxygen").getPlasma(125), 32, 4096, 80000000); //Mark 1 Expensive //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Aluminium").getMolten(16), Materials.get("Lithium").getMolten(16), Materials.get("Sulfur").getPlasma(125), 32, 10240, 240000000); //Mark 2 Cheap
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Beryllium").getMolten(16), Materials.get("Deuterium").getGas(375), Materials.get("Nitrogen").getPlasma(175), 16, 16384, 180000000); //Mark 2 Expensive //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Silicon").getMolten(16), Materials.get("Magnesium").getMolten(16), Materials.get("Iron").getPlasma(125), 32, 8192, 360000000); //Mark 3 Cheap //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Potassium").getMolten(16), Materials.get("Fluorine").getGas(125), Materials.get("Nickel").getPlasma(125), 16, 32768, 480000000); //Mark 3 Expensive //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Beryllium").getMolten(16), Materials.get("Tungsten").getMolten(16), Materials.get("Platinum").getMolten(16), 32, 32768, 150000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Neodymium").getMolten(16), Materials.get("Hydrogen").getGas(48), Materials.get("Europium").getMolten(16), 64, 24576, 150000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Lutetium").getMolten(16), Materials.get("Chrome").getMolten(16), Materials.get("Americium").getMolten(16), 96, 49152, 200000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Plutonium").getMolten(16), Materials.get("Thorium").getMolten(16), Materials.get("Naquadah").getMolten(16), 64, 32768, 300000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Americium").getMolten(16), Materials.get("Naquadria").getMolten(16), Materials.get("Neutronium").getMolten(1), 1200, 98304, 600000000); //

        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Tungsten").getMolten(16), Materials.get("Helium").getGas(16), Materials.get("Osmium").getMolten(16), 64, 24578, 150000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Manganese").getMolten(16), Materials.get("Hydrogen").getGas(16), Materials.get("Iron").getMolten(16), 64, 8192, 120000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Mercury").getFluid(16), Materials.get("Magnesium").getMolten(16), Materials.get("Uranium").getMolten(16), 64, 49152, 240000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Gold").getMolten(16), Materials.get("Aluminium").getMolten(16), Materials.get("Uranium").getMolten(16), 64, 49152, 240000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Uranium").getMolten(16), Materials.get("Helium").getGas(16), Materials.get("Plutonium").getMolten(16), 128, 49152, 480000000); //
        RECIPE_ADDER_INSTANCE.addFusionReactorRecipe(Materials.get("Vanadium").getMolten(16), Materials.get("Hydrogen").getGas(125), Materials.get("Chrome").getMolten(16), 64, 24576, 140000000); //

        GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Fertilizer.get(1L));
        RECIPE_ADDER_INSTANCE.addImplosionRecipe(ItemList.IC2_Compressed_Coal_Chunk.get(1L), 8, ItemList.IC2_Industrial_Diamond.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("DarkAsh"), 4L));
        RECIPE_ADDER_INSTANCE.addFluidExtractionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Quartzite"), 1L), null, Materials.get("Glass").getMolten(250), 10000, 600, 28);

        RECIPE_ADDER_INSTANCE.addPyrolyseRecipe(GT_ModHandler.getIC2Item("biochaff", 1), Materials.get("Water").getFluid(1000), 1, null, new FluidStack(FluidRegistry.getFluid("ic2biomass"), 1500), 100, 10);
        RECIPE_ADDER_INSTANCE.addPyrolyseRecipe(new ItemStack(Items.reeds, 24, 0), null, 1, (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Charcoal"), 12)), Materials.get("Water").getFluid(11000), 320, 32);

        if (Loader.isModLoaded(MOD_ID_RC)) {
            RECIPE_ADDER_INSTANCE.addPyrolyseRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Coal"), 16), null, 1, RailcraftToolItems.getCoalCoke(16), Materials.get("Creosote").getFluid(8000), 640, 32);
            RECIPE_ADDER_INSTANCE.addPyrolyseRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.get("Coal"), 8), null, 1, EnumCube.COKE_BLOCK.getItem(8), Materials.get("Creosote").getFluid(32000), 2560, 32);
        }

        //: TODO NOT WORKING HYDRPFARM RECIPES - 2nd output slot does not work correctly yet
        RECIPE_ADDER_INSTANCE.addHydroFarmRecipe(new ItemStack(Items.melon_seeds, 9, 0), ItemList.IC2_Fertilizer.get(1L), Materials.get("Water").getFluid(1000), new ItemStack(Blocks.melon_block, 18, 0), new ItemStack(Items.melon_seeds, 9, 0), Materials.get("Oxygen").getGas(100L), 1000, 40);
        RECIPE_ADDER_INSTANCE.addHydroFarmRecipe(new ItemStack(Items.pumpkin_seeds, 9, 0), ItemList.IC2_Fertilizer.get(1L), Materials.get("Water").getFluid(1000), new ItemStack(Blocks.pumpkin, 18, 0), new ItemStack(Items.pumpkin_seeds, 9, 0), Materials.get("Oxygen").getGas(100L), 1000, 40);
        RECIPE_ADDER_INSTANCE.addHydroFarmRecipe(new ItemStack(Items.carrot, 9, 0), ItemList.IC2_Fertilizer.get(1L), Materials.get("Water").getFluid(1000), new ItemStack(Items.carrot, 27, 0), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(100L), 1000, 40);
        RECIPE_ADDER_INSTANCE.addHydroFarmRecipe(new ItemStack(Blocks.sapling, 1, 0), ItemList.IC2_Fertilizer.get(1L), Materials.get("Water").getFluid(1000), new ItemStack(Blocks.log, 3, 0), new ItemStack(Blocks.sapling, 1, 0), Materials.get("Oxygen").getGas(100L), 1000, 40);

        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("Oil").getFluid(64L), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, null, 16, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(ItemList.sOilLight, 128), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, null, 16, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(ItemList.sOilMedium, 64), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, null, 16, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(ItemList.sOilHeavy, 32), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("HydratedCoal"), 1L), 24, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(ItemList.sOilExtraHeavy, 16), new FluidStack[]{Materials.get("Lubricant").getFluid(16L), Materials.get("Fuel").getFluid(64L), Materials.get("SulfuricAcid").getFluid(64L), Materials.get("Glyceryl").getFluid(4L), Materials.get("Methane").getGas(60L)}, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("HydratedCoal"), 1L), 24, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(ItemList.sNaturalGas, 64), new FluidStack[]{Materials.get("Methane").getGas(120L)}, null, 16, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("Creosote").getFluid(24L), new FluidStack[]{Materials.get("Lubricant").getFluid(12L)}, null, 16, 96);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("SeedOil").getFluid(32L), new FluidStack[]{Materials.get("Lubricant").getFluid(12L)}, null, 16, 96);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("FishOil").getFluid(24L), new FluidStack[]{Materials.get("Lubricant").getFluid(12L)}, null, 16, 96);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("Biomass").getFluid(150L), new FluidStack[]{Materials.get("Ethanol").getFluid(60L), Materials.get("Water").getFluid(60L)}, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Wood"), 1L), 25, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(Materials.get("Water").getFluid(288L), new FluidStack[]{GT_ModHandler.getDistilledWater(260L)}, null, 16, 64);
        RECIPE_ADDER_INSTANCE.addDistillationTowerRecipe(new FluidStack(FluidRegistry.getFluid("ic2biomass"), 250), new FluidStack[]{new FluidStack(FluidRegistry.getFluid("ic2biogas"), 8000), Materials.get("Water").getFluid(125L)}, ItemList.IC2_Fertilizer.get(1), 150, 512);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getIC2Item("biogasCell", 1L), null, 32, 1);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new FluidStack(FluidRegistry.getFluid("ic2biomass"), 1), new FluidStack(FluidRegistry.getFluid("ic2biogas"), 32), 20, 30, false);
        RECIPE_ADDER_INSTANCE.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(FluidRegistry.getFluid("ic2biomass"), 4), Materials.get("Water").getFluid(2), 80, 30, false);


        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 6), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "GluttonyShard", 1L), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "FMResource", 1L, 3), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 1), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 2), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 3), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 4), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 5), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem(MOD_ID_FM, "NetherShard", 1L, 6), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem("TaintedMagic", "WarpedShard", 1L), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem("TaintedMagic", "FluxShard", 1L), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem("TaintedMagic", "EldritchShard", 1L), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem("ThaumicTinkerer", "kamiResource", 1L, 6), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(GT_ModHandler.getModItem("ThaumicTinkerer", "kamiResource", 1L, 7), null, 720, 5);
        RECIPE_ADDER_INSTANCE.addFuel(new ItemStack(Items.golden_apple, 1, 1), new ItemStack(Items.apple, 1), 640000, 5);

        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(NULL_ITEM_STACK, ItemList.Cell_Empty.get(1L), Materials.get("Water").getFluid(3000L), Materials.get("Hydrogen").getGas(2000L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 1500, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(NULL_ITEM_STACK, ItemList.Cell_Empty.get(1L), GT_ModHandler.getDistilledWater(3000L), Materials.get("Hydrogen").getGas(2000L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 1500, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(GT_ModHandler.getIC2Item("electrolyzedWaterCell", 3L), 0, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Hydrogen"), 2L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 30, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Water"), 1L), 0, GT_ModHandler.getIC2Item("electrolyzedWaterCell", 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, 490, 30);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(ItemList.Dye_Bonemeal.get(3L), 0, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, 98, 26);
        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(new ItemStack(Blocks.sand, 8), 0, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("SiliconDioxide"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, 500, 25);

        RECIPE_ADDER_INSTANCE.addElectrolyzerRecipe(NULL_ITEM_STACK, ItemList.Cell_Empty.get(6L), Materials.get("SulfuricAcid").getFluid(7000L), null, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Hydrogen"), 2L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 4L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 380, 90);

        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Chalcopyrite"), 1L), Materials.get("Oxygen").getGas(4000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Copper"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Iron"), 1L)), NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Molybdenite"), 1L), Materials.get("Oxygen").getGas(6000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Molybdenum"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(200L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Cobaltite"), 1L), Materials.get("Oxygen").getGas(3000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Cobalt"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Arsenic"), 1L)), NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Pentlandite"), 1L), Materials.get("Oxygen").getGas(8000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Nickel"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Stibnite"), 1L), Materials.get("Oxygen").getGas(5000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Antimony"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sphalerite"), 1L), Materials.get("Oxygen").getGas(4000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Zinc"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Galena"), 1L), Materials.get("Oxygen").getGas(2000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Silver"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Lead"), 1L)), NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(500L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Pyrite"), 1L), Materials.get("Oxygen").getGas(3000L), (GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Iron"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), null, 512, 16);
        RECIPE_ADDER_INSTANCE.addRoasterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), Materials.get("Oxygen").getGas(8000L), (GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Sulfur"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(3000L), new int[]{1000}, 512, 16);

        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("SulfurDioxide"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Water").getFluid(2000L), Materials.get("SulfuricAcid").getFluid(3000L), ItemList.Cell_Empty.get(1L), 1150, 30);
        RECIPE_ADDER_INSTANCE.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Water"), 2L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("SulfurDioxide").getGas(1000L), Materials.get("SulfuricAcid").getFluid(3000L), ItemList.Cell_Empty.get(2L), 1150, 30);

        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NetherQuartz"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("NetherQuartz"), 3L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("CertusQuartz"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("CertusQuartz"), 3L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Quartzite"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), Materials.get("Water").getFluid(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Quartzite"), 3L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NetherQuartz"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), GT_ModHandler.getDistilledWater(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("NetherQuartz"), 3L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("CertusQuartz"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), GT_ModHandler.getDistilledWater(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("CertusQuartz"), 3L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Quartzite"), 3L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), GT_ModHandler.getDistilledWater(1000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Quartzite"), 3L), 500);

        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uraninite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Aluminium"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uranium"), 1L), 1000);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uraninite"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Magnesium"), 1L), NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uranium"), 1L), 1000);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Carbon"), 1L), Materials.get("Oxygen").getGas(3000L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Calcite"), 5L), 500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Carbon"), 1L), NULL_ITEM_STACK, Materials.get("Hydrogen").getGas(4000L), Materials.get("Methane").getGas(5000L), NULL_ITEM_STACK, 3500);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), Materials.get("Water").getFluid(2000L), Materials.get("SulfuricAcid").getFluid(3000L), NULL_ITEM_STACK, 1150);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sodium"), 1L), Materials.get("Oxygen").getGas(4000L), Materials.get("SodiumPersulfate").getFluid(6000L), NULL_ITEM_STACK, 8000);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Nitrogen"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Carbon"), 1L), Materials.get("Water").getFluid(2000L), Materials.get("Glyceryl").getFluid(4000L), ItemList.Cell_Empty.get(1L), 2700);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Fuel"), 1L), NULL_ITEM_STACK, Materials.get("Glyceryl").getFluid(250L), Materials.get("NitroFuel").getFluid(1250L), ItemList.Cell_Empty.get(1L), 250);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Glyceryl"), 1L), NULL_ITEM_STACK, Materials.get("Fuel").getFluid(4000L), Materials.get("NitroFuel").getFluid(5000L), ItemList.Cell_Empty.get(1L), 1000);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Nitrogen"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(2000L), Materials.get("NitrogenDioxide").getGas(3000L), ItemList.Cell_Empty.get(1L), 1250);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 2L), NULL_ITEM_STACK, Materials.get("Nitrogen").getGas(1000L), Materials.get("NitrogenDioxide").getGas(3000L), ItemList.Cell_Empty.get(2L), 1250);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1L), NULL_ITEM_STACK, Materials.get("Hydrogen").getGas(2000L), GT_ModHandler.getDistilledWater(3000L), ItemList.Cell_Empty.get(1L), 10);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Hydrogen"), 1L), NULL_ITEM_STACK, Materials.get("Oxygen").getGas(500L), GT_ModHandler.getDistilledWater(1500L), ItemList.Cell_Empty.get(1L), 5);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Tin"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Saltpeter"), 1L), Materials.get("Glass").getMolten(864L), NULL_FLUID_STACK, GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.glass", 6L), 50);

        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Gold"), 8L), new ItemStack(Items.melon, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.speckled_melon, 1, 0), 50);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Gold"), 8L), new ItemStack(Items.carrot, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.golden_carrot, 1, 0), 50);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Gold"), 8L), new ItemStack(Items.apple, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.golden_apple, 1, 0), 50);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.get("Gold"), 8L), new ItemStack(Items.apple, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.golden_apple, 1, 1), 50);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 1L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderPearl"), 1L), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderEye"), 1L), 50);
        RECIPE_ADDER_INSTANCE.addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Blaze"), 1L), new ItemStack(Items.slime_ball, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.magma_cream, 1, 0), 50);

        RECIPE_ADDER_INSTANCE.addBenderRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1L), GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.get("Advanced"), 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Aluminium"), 6L), ItemList.RC_Rail_Standard.get(2L), 200, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 6L), ItemList.RC_Rail_Standard.get(4L), 400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("WroughtIron"), 6L), ItemList.RC_Rail_Standard.get(5L), 400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Bronze"), 6L), ItemList.RC_Rail_Standard.get(3L), 300, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 6L), ItemList.RC_Rail_Standard.get(8L), 800, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("StainlessSteel"), 6L), ItemList.RC_Rail_Standard.get(12L), 1200, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Titanium"), 6L), ItemList.RC_Rail_Standard.get(16L), 1600, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("TungstenSteel"), 6L), ItemList.RC_Rail_Reinforced.get(24L), 2400, 30);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Aluminium"), 12L), ItemList.RC_Rebar.get(4L), 200, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 12L), ItemList.RC_Rebar.get(8L), 400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("WroughtIron"), 12L), ItemList.RC_Rebar.get(10L), 400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Bronze"), 12L), ItemList.RC_Rebar.get(8L), 400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 12L), ItemList.RC_Rebar.get(16L), 800, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("StainlessSteel"), 12L), ItemList.RC_Rebar.get(24L), 1200, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Titanium"), 12L), ItemList.RC_Rebar.get(32L), 1600, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("TungstenSteel"), 12L), ItemList.RC_Rebar.get(48L), 2400, 15);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Tin"), 12L), ItemList.Cell_Empty.get(6L), 1200, 8);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 12L), new ItemStack(Items.bucket, 4, 0), 800, 4);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 12L), new ItemStack(Items.bucket, 4, 0), 800, 4);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(ItemList.IC2_Item_Casing_Iron.get(2L), GT_ModHandler.getIC2Item("fuelRod", 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addBenderRecipe(ItemList.IC2_Item_Casing_Tin.get(1L), ItemList.IC2_Food_Can_Empty.get(1L), 100, 8);

        RECIPE_ADDER_INSTANCE.addPulveriserRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.get("Marble"), 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Marble"), 1L)}, null, 160, 4);

        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L, 1), 100);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantTriple", 1L, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getIC2Item("reactorCoolantTriple", 1L, 1), 300);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantSix", 1L, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getIC2Item("reactorCoolantSix", 1L, 1), 600);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_1.getWildcard(1L), ItemList.Reactor_Coolant_He_1.get(1L), 600);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_3.getWildcard(1L), ItemList.Reactor_Coolant_He_3.get(1L), 1800);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_6.getWildcard(1L), ItemList.Reactor_Coolant_He_6.get(1L), 3600);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_1.getWildcard(1L), ItemList.Reactor_Coolant_NaK_1.get(1L), 600);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_3.getWildcard(1L), ItemList.Reactor_Coolant_NaK_3.get(1L), 1800);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_6.getWildcard(1L), ItemList.Reactor_Coolant_NaK_6.get(1L), 3600);
        RECIPE_ADDER_INSTANCE.addVacuumFreezerRecipe(GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Water"), 1L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Ice"), 1L), 50);

        RECIPE_ADDER_INSTANCE.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Lead"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Obsidian"), 2L), ItemList.TE_Hardened_Glass.get(2L), 200, 16);
        RECIPE_ADDER_INSTANCE.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Lead"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Obsidian"), 2L), ItemList.TE_Hardened_Glass.get(2L), 200, 16);

        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "item.buildcraftPipe.pipestructurecobblestone", 1L, 0), GT_ModHandler.getModItem(MOD_ID_BC_TRANSPORT, "pipePlug", 8L, 0), NULL_ITEM_STACK, 32, 16);
        for (int i = 0; i < 16; i++) {
            RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.stained_glass, 3, i), new ItemStack(Blocks.stained_glass_pane, 8, i), NULL_ITEM_STACK, 50, 8);
        }
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.glass, 3, 0), new ItemStack(Blocks.glass_pane, 8, 0), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.stone, 1, 0), new ItemStack(Blocks.stone_slab, 2, 0), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.stone_slab, 2, 1), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.cobblestone, 1, 0), new ItemStack(Blocks.stone_slab, 2, 3), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(Blocks.stone_slab, 2, 4), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stone_slab, 2, 5), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.nether_brick, 1, 0), new ItemStack(Blocks.stone_slab, 2, 6), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.quartz_block, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.stone_slab, 2, 7), NULL_ITEM_STACK, 25, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.glowstone, 1, 0), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Glowstone"), 4L), NULL_ITEM_STACK, 100, 16);

        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), ItemList.IC2_Item_Casing_Iron.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), ItemList.IC2_Item_Casing_Iron.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 1L), ItemList.IC2_Item_Casing_Gold.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Bronze"), 1L), ItemList.IC2_Item_Casing_Bronze.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Copper"), 1L), ItemList.IC2_Item_Casing_Copper.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("AnnealedCopper"), 1L), ItemList.IC2_Item_Casing_Copper.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Tin"), 1L), ItemList.IC2_Item_Casing_Tin.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 1L), ItemList.IC2_Item_Casing_Lead.get(2L), NULL_ITEM_STACK, 50, 16);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 1L), ItemList.IC2_Item_Casing_Steel.get(2L), NULL_ITEM_STACK, 50, 16);
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wool, 2, i), new ItemStack(Blocks.carpet, 3, i), NULL_ITEM_STACK, 50, 8);
        }
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 0), ItemList.Plank_Oak.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 1), ItemList.Plank_Spruce.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 2), ItemList.Plank_Birch.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 3), ItemList.Plank_Jungle.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 4), ItemList.Plank_Acacia.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(new ItemStack(Blocks.wooden_slab, 1, 5), ItemList.Plank_DarkOak.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 0), ItemList.Plank_Larch.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 1), ItemList.Plank_Teak.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 2), ItemList.Plank_Acacia_Green.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 3), ItemList.Plank_Lime.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 4), ItemList.Plank_Chestnut.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 5), ItemList.Plank_Wenge.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 6), ItemList.Plank_Baobab.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL1, 1L, 7), ItemList.Plank_Sequoia.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 0), ItemList.Plank_Kapok.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 1), ItemList.Plank_Ebony.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 2), ItemList.Plank_Mahagony.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 3), ItemList.Plank_Balsa.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 4), ItemList.Plank_Willow.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 5), ItemList.Plank_Walnut.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 6), ItemList.Plank_Greenheart.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL2, 1L, 7), ItemList.Plank_Cherry.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 0), ItemList.Plank_Mahoe.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 1), ItemList.Plank_Poplar.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 2), ItemList.Plank_Palm.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 3), ItemList.Plank_Papaya.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 4), ItemList.Plank_Pine.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 5), ItemList.Plank_Plum.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 6), ItemList.Plank_Maple.get(2L), NULL_ITEM_STACK, 50, 8);
        RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_ModHandler.getModItem(MOD_ID_FR, SL3, 1L, 7), ItemList.Plank_Citrus.get(2L), NULL_ITEM_STACK, 50, 8);

        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Cupronickel"), 1L), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Greg_Cupronickel.get(4L), 100, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Brass"), 1L), ItemList.Shape_Mold_Credit.get(0L), ItemList.Coin_Doge.get(4L), 100, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Iron.get(4L), 100, 16);
        RECIPE_ADDER_INSTANCE.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Iron.get(4L), 100, 16);

        if (!GT_Mod.gregtechproxy.mDisableIC2Cables) {
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Copper"), 1L), GT_ModHandler.getIC2Item("copperCableItem", 3L), 100, 2);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("AnnealedCopper"), 1L), GT_ModHandler.getIC2Item("copperCableItem", 3L), 100, 2);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Tin"), 1L), GT_ModHandler.getIC2Item("tinCableItem", 4L), 150, 1);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), GT_ModHandler.getIC2Item("ironCableItem", 6L), 200, 2);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), GT_ModHandler.getIC2Item("ironCableItem", 6L), 200, 2);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 1L), GT_ModHandler.getIC2Item("goldCableItem", 6L), 200, 1);
        }
        RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Carbon"), 8L), GT_ModHandler.getIC2Item("carbonFiber", 1L), 400, 2);
        RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Graphene"), 1L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Graphene"), 1L), 400, 2);
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
            RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.torch, 4), 400, 1);
        }
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.light_weighted_pressure_plate, 1), 200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), 200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 6L), ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new ItemStack(Items.iron_door, 1), 600, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 7L), ItemList.Circuit_Integrated.getWithDamage(0L, 7L), new ItemStack(Items.cauldron, 1), 700, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), GT_ModHandler.getIC2Item("ironFence", 1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.iron_bars, 4), 300, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), 200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 6L), ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new ItemStack(Items.iron_door, 1), 600, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 7L), ItemList.Circuit_Integrated.getWithDamage(0L, 7L), new ItemStack(Items.cauldron, 1), 700, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("WroughtIron"), 1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), GT_ModHandler.getIC2Item("ironFence", 1L), 100, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("WroughtIron"), 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.iron_bars, 4), 300, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.fence, 1), 300, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Iron"), 2L), new ItemStack(Blocks.tripwire_hook, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("WroughtIron"), 2L), new ItemStack(Blocks.tripwire_hook, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 3L), new ItemStack(Items.string, 3, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.bow, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 3L), ItemList.Component_Minecart_Wheels_Iron.get(2L), new ItemStack(Items.minecart, 1), 500, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 3L), ItemList.Component_Minecart_Wheels_Iron.get(2L), new ItemStack(Items.minecart, 1), 400, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 3L), ItemList.Component_Minecart_Wheels_Steel.get(2L), new ItemStack(Items.minecart, 1), 300, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Iron"), 2L), ItemList.Component_Minecart_Wheels_Iron.get(1L), 500, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("WroughtIron"), 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("WroughtIron"), 2L), ItemList.Component_Minecart_Wheels_Iron.get(1L), 400, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Steel"), 2L), ItemList.Component_Minecart_Wheels_Steel.get(1L), 300, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.hopper, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.hopper_minecart, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.tnt, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.tnt_minecart, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.chest_minecart, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.trapped_chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.chest_minecart, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.furnace, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.furnace_minecart, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.tripwire_hook, 1), new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.trapped_chest, 1), 200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.stone, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(Blocks.stonebrick, 1, 0), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.sandstone, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.sandstone, 1, 2), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.sandstone, 1, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.sandstone, 1, 0), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.sandstone, 1, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.sandstone, 1, 0), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_ULV.get(1L), 25, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_LV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_MV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("StainlessSteel"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_HV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Titanium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_EV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("TungstenSteel"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_IV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Chrome"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_LuV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iridium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_ZPM.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Osmium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_UV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Neutronium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_MAX.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 1L), Materials.get("Plastic").getMolten(144), ItemList.Battery_Hull_LV.get(1L), 800, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Copper"), 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 3L), Materials.get("Plastic").getMolten(432), ItemList.Battery_Hull_MV.get(1L), 1600, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnnealedCopper"), 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 3L), Materials.get("Plastic").getMolten(432), ItemList.Battery_Hull_MV.get(1L), 1600, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 9L), Materials.get("Plastic").getMolten(1296), ItemList.Battery_Hull_HV.get(1L), 3200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Lead"), 2L), ItemList.Casing_ULV.get(1L), ItemList.Hull_ULV.get(1L), 25, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L), ItemList.Casing_LV.get(1L), ItemList.Hull_LV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Copper"), 2L), ItemList.Casing_MV.get(1L), ItemList.Hull_MV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnnealedCopper"), 2L), ItemList.Casing_MV.get(1L), ItemList.Hull_MV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 2L), ItemList.Casing_HV.get(1L), ItemList.Hull_HV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 2L), ItemList.Casing_EV.get(1L), ItemList.Hull_EV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 2L), ItemList.Casing_IV.get(1L), ItemList.Hull_IV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.get("Tungsten"), 2L), ItemList.Casing_LuV.get(1L), ItemList.Hull_LuV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt04, Materials.get("Osmium"), 2L), ItemList.Casing_ZPM.get(1L), ItemList.Hull_ZPM.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.get("Osmium"), 2L), ItemList.Casing_UV.get(1L), ItemList.Hull_UV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Superconductor"), 2L), ItemList.Casing_MAX.get(1L), ItemList.Hull_MAX.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 2L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 1L), ItemList.Battery_Hull_LV.get(1L), 1600, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 6L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Copper"), 2L), ItemList.Battery_Hull_MV.get(1L), 2400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 6L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnnealedCopper"), 2L), ItemList.Battery_Hull_MV.get(1L), 2400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("BatteryAlloy"), 18L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 4L), ItemList.Battery_Hull_HV.get(1L), 3200, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Items.string, 4, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.slime_ball, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.lead, 2), 200, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L), new ItemStack(Blocks.brick_block, 1), ItemList.IC2_Compressed_Coal_Chunk.get(1L), 400, 4);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("waterMill", 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GT_ModHandler.getIC2Item("generator", 1L), 6400, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("batPack", 1L, OreDictionary.WILDCARD_VALUE), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), ItemList.IC2_ReBattery.get(6L), 800, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.stone_slab, 3, 0), ItemList.RC_Rebar.get(1L), ItemList.RC_Tie_Stone.get(1L), 128, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(new ItemStack(Blocks.stone_slab, 3, 7), ItemList.RC_Rebar.get(1L), ItemList.RC_Tie_Stone.get(1L), 128, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Copper"), 9L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 2L), NULL_FLUID_STACK, ItemList.RC_ShuntingWire.get(4L), 1600, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnnealedCopper"), 9L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 2L), NULL_FLUID_STACK, ItemList.RC_ShuntingWire.get(4L), 1600, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Steel"), 3L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 3L), Materials.get("Blaze").getMolten(432L), ItemList.RC_Rail_HS.get(8L), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(3L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 3L), Materials.get("Redstone").getMolten(432L), ItemList.RC_Rail_Adv.get(8L), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Copper"), 1L), ItemList.RC_Rail_Electric.get(1L), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(1L), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnnealedCopper"), 1L), ItemList.RC_Rail_Electric.get(1L), 50, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(6L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 1L), ItemList.RC_Rail_Wooden.get(6L), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(6L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 1L), ItemList.RC_Rail_Wooden.get(6L), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), ItemList.RC_Bed_Wood.get(1L), 200, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.RC_Tie_Stone.get(4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), ItemList.RC_Bed_Stone.get(1L), 200, 4);
        for (ItemStack tRail : new ItemStack[]{ItemList.RC_Rail_Standard.get(6L), ItemList.RC_Rail_Adv.get(6L), ItemList.RC_Rail_Reinforced.get(6L), ItemList.RC_Rail_Electric.get(6L), ItemList.RC_Rail_HS.get(6L), ItemList.RC_Rail_Wooden.get(6L)}) {
            for (ItemStack tBed : new ItemStack[]{ItemList.RC_Bed_Wood.get(1L), ItemList.RC_Bed_Stone.get(1L)}) {
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(tBed, tRail, GT_ModHandler.getRecipeOutput(tRail, NULL_ITEM_STACK, tRail, tRail, tBed, tRail, tRail, NULL_ITEM_STACK, tRail), 400, 4);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(tBed, tRail, Materials.get("Redstone").getMolten(144L), GT_ModHandler.getRecipeOutput(tRail, NULL_ITEM_STACK, tRail, tRail, tBed, tRail, tRail, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), tRail), 400, 4);
                RECIPE_ADDER_INSTANCE.addAssemblerRecipe(tBed, tRail, Materials.get("Redstone").getMolten(288L), GT_ModHandler.getRecipeOutput(tRail, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), tRail, tRail, tBed, tRail, tRail, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), tRail), 400, 4);
            }
        }
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("carbonFiber", 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GT_ModHandler.getIC2Item("carbonMesh", 1L), 800, 2);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(ItemList.NC_SensorCard.getWildcard(1L), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), ItemList.Circuit_Basic.get(3L), 1600, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 4L), GT_ModHandler.getIC2Item("generator", 1L), GT_ModHandler.getIC2Item("waterMill", 2L), 6400, 8);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), GT_ModHandler.getIC2Item("machine", 1L), 25, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Osmium"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_UV.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Invar"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Invar"), 1L), ItemList.Casing_HeatProof.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.get("Cupronickel"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Cupronickel.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.get("Kanthal"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Kanthal.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.get("Nichrome"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Nichrome.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.get("Superconductor"), 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Superconductor.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Steel"), 1L), ItemList.Casing_SolidSteel.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Aluminium"), 1L), ItemList.Casing_FrostProof.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("TungstenSteel"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("TungstenSteel"), 1L), ItemList.Casing_RobustTungstenSteel.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("StainlessSteel"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("StainlessSteel"), 1L), ItemList.Casing_CleanStainlessSteel.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Titanium"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Titanium"), 1L), ItemList.Casing_StableTitanium.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("TungstenSteel"), 6L), ItemList.Casing_LuV.get(1L), ItemList.Casing_Fusion.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Magnalium"), 6L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("BlueSteel"), 1L), ItemList.Casing_Turbine.get(1L), 50, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("StainlessSteel"), 6L), ItemList.Casing_Turbine.get(1L), ItemList.Casing_Turbine.get(1L), 50, 16);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 5L), new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.hopper), 800, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 5L), new ItemStack(Blocks.trapped_chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.hopper), 800, 2);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 5L), new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.hopper), 800, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 5L), new ItemStack(Blocks.trapped_chest, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.hopper), 800, 2);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Magnalium"), 2L), GT_ModHandler.getIC2Item("generator", 1L), GT_ModHandler.getIC2Item("windMill", 1L), 6400, 8);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderPearl"), 1L), new ItemStack(Items.blaze_powder, 1, 0), new ItemStack(Items.ender_eye, 1, 0), 400, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("EnderPearl"), 6L), new ItemStack(Items.blaze_rod, 1, 0), new ItemStack(Items.ender_eye, 6, 0), 2500, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.gear, Materials.get("CobaltBrass"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Diamond"), 1L), ItemList.Component_Sawblade_Diamond.get(1L), 1600, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Flint"), 5L), new ItemStack(Blocks.tnt, 3, OreDictionary.WILDCARD_VALUE), GT_ModHandler.getIC2Item("industrialTnt", 5L), 800, 2);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Gunpowder"), 4L), new ItemStack(Blocks.sand, 4, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.tnt, 1), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 4L), new ItemStack(Blocks.redstone_lamp, 1), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Blocks.redstone_torch, 1), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iron"), 4L), new ItemStack(Items.compass, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("WroughtIron"), 4L), new ItemStack(Items.compass, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Gold"), 4L), new ItemStack(Items.clock, 1), 400, 4);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Sulfur"), 1L), new ItemStack(Blocks.torch, 2), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Phosphorus"), 1L), new ItemStack(Blocks.torch, 6), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), ItemList.IC2_Resin.get(1L), new ItemStack(Blocks.torch, 6), 400, 1);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Coal"), 8L), new ItemStack(Items.flint, 1), ItemList.IC2_Compressed_Coal_Ball.get(1L), 400, 4);
        if (!GT_Mod.gregtechproxy.mDisableIC2Cables) {
            RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("tinCableItem", 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Rubber"), 1L), GT_ModHandler.getIC2Item("insulatedTinCableItem", 1L), 100, 2);
            RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("copperCableItem", 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Rubber"), 1L), GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L), 100, 2);
            RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("goldCableItem", 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Rubber"), 2L), GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L), 200, 2);
            RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_ModHandler.getIC2Item("ironCableItem", 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Rubber"), 3L), GT_ModHandler.getIC2Item("insulatedIronCableItem", 1L), 300, 2);
        }
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.wooden_sword, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Stone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.stone_sword, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.iron_sword, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.golden_sword, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Diamond"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), new ItemStack(Items.diamond_sword, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Bronze"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), ItemList.Tool_Sword_Bronze.getUndamaged(1L), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 1L), ItemList.Tool_Sword_Steel.getUndamaged(1L), 100, 16);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.wooden_pickaxe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Stone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.stone_pickaxe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.iron_pickaxe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.golden_pickaxe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Diamond"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.diamond_pickaxe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Bronze"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Pickaxe_Bronze.getUndamaged(1L), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Pickaxe_Steel.getUndamaged(1L), 100, 16);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.wooden_shovel, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Stone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.stone_shovel, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.iron_shovel, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.golden_shovel, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Diamond"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.diamond_shovel, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Bronze"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Shovel_Bronze.getUndamaged(1L), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Shovel_Steel.getUndamaged(1L), 100, 16);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.wooden_axe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Stone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.stone_axe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.iron_axe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.golden_axe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Diamond"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.diamond_axe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Bronze"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Axe_Bronze.getUndamaged(1L), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Axe_Steel.getUndamaged(1L), 100, 16);

        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Wood"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.wooden_hoe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Stone"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.stone_hoe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.iron_hoe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.golden_hoe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Diamond"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), new ItemStack(Items.diamond_hoe, 1), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Bronze"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Hoe_Bronze.getUndamaged(1L), 100, 16);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.get("Steel"), 1L), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Wood"), 2L), ItemList.Tool_Hoe_Steel.getUndamaged(1L), 100, 16);

        /* Part Factory */

        /* Electric Motors*/
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("IronMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Iron"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnyCopper"), 3L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_LV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("SteelMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnyCopper"), 3L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_LV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("SteelMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Aluminium"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnyCopper"), 6L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnyCopper"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_MV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("SteelMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("StainlessSteel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnyCopper"), 12L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_HV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("NeodymiumMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Titanium"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnnealedCopper"), 24L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_EV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("NeodymiumMagnetic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("TungstenSteel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("AnnealedCopper"), 48L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Motor_IV.get(1L)), 100, 16);

        /* Electric Pumps*/
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Tin"), 1L), (GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Rubber"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.get("Bronze"), 1L)), (ItemList.Electric_Motor_LV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 1L)), NULL_ITEM_STACK, (ItemList.Electric_Pump_LV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Bronze"), 1L), (GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Rubber"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.get("Steel"), 1L)), (ItemList.Electric_Motor_MV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnyCopper"), 1L)), NULL_ITEM_STACK, (ItemList.Electric_Pump_MV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Steel"), 1L), (GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Rubber"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.get("StainlessSteel"), 1L)), (ItemList.Electric_Motor_HV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 1L)), NULL_ITEM_STACK, (ItemList.Electric_Pump_HV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("StainlessSteel"), 1L), (GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Rubber"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.get("Titanium"), 1L)), (ItemList.Electric_Motor_EV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 1L)), NULL_ITEM_STACK, (ItemList.Electric_Pump_EV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("TungstenSteel"), 1L), (GT_OreDictUnificator.get(OrePrefixes.ring, Materials.get("Rubber"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.get("TungstenSteel"), 1L)), (ItemList.Electric_Motor_IV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 1L)), NULL_ITEM_STACK, (ItemList.Electric_Pump_IV.get(1L)), 200, 16);

        /* Conveyors */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 4L), (ItemList.Electric_Motor_LV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Conveyor_Module_LV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 4L), (ItemList.Electric_Motor_MV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Copper"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Conveyor_Module_MV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 4L), (ItemList.Electric_Motor_HV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Conveyor_Module_HV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 4L), (ItemList.Electric_Motor_EV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Conveyor_Module_EV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Rubber"), 4L), (ItemList.Electric_Motor_IV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Conveyor_Module_IV.get(1L)), 100, 16);

        /* Electric Pistons */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 4L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 2L)), (ItemList.Electric_Motor_LV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Piston_LV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 4L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Aluminium"), 2L)), (ItemList.Electric_Motor_MV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnyCopper"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Piston_MV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("StainlessSteel"), 4L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("StainlessSteel"), 2L)), (ItemList.Electric_Motor_HV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Piston_HV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Titanium"), 4L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Titanium"), 2L)), (ItemList.Electric_Motor_EV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Piston_EV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("TungstenSteel"), 4L), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("TungstenSteel"), 2L)), (ItemList.Electric_Motor_IV.get(1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 2L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Electric_Piston_IV.get(1L)), 200, 16);

        /* Robot Arms */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Basic"), 1L), (ItemList.Electric_Piston_LV.get(1L)), (ItemList.Electric_Motor_LV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Steel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L)), NULL_ITEM_STACK, (ItemList.Robot_Arm_LV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Good"), 1L), (ItemList.Electric_Piston_MV.get(1L)), (ItemList.Electric_Motor_MV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Aluminium"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnyCopper"), 2L)), NULL_ITEM_STACK, (ItemList.Robot_Arm_MV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L), (ItemList.Electric_Piston_HV.get(1L)), (ItemList.Electric_Motor_HV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("StainlessSteel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 2L)), NULL_ITEM_STACK, (ItemList.Robot_Arm_HV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L), (ItemList.Electric_Piston_EV.get(1L)), (ItemList.Electric_Motor_EV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Titanium"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 2L)), NULL_ITEM_STACK, (ItemList.Robot_Arm_EV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 1L), (ItemList.Electric_Piston_IV.get(1L)), (ItemList.Electric_Motor_IV.get(2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("TungstenSteel"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 2L)), NULL_ITEM_STACK, (ItemList.Robot_Arm_IV.get(1L)), 200, 16);

        /* Feild Generators */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Basic"), 4L), (new ItemStack(Items.ender_pearl, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Osmium"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Field_Generator_LV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Good"), 4L), (new ItemStack(Items.ender_eye, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Osmium"), 8L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Field_Generator_MV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 4L), (new ItemStack(Items.nether_star, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Osmium"), 16L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Field_Generator_HV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 4L), (new ItemStack(Items.nether_star, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Osmium"), 32L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Field_Generator_EV.get(1L)), 100, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 4L), (new ItemStack(Items.nether_star, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.get("Osmium"), 64L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Field_Generator_IV.get(1L)), 100, 16);

        /* Emitters */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Basic"), 2L), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Quartzite"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tin"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Brass"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Emitter_LV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Good"), 2L), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NetherQuartz"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("AnyCopper"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Electrum"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Emitter_MV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 2L), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Emerald"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Gold"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Chrome"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Emitter_HV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 2L), (new ItemStack(Items.ender_pearl, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Aluminium"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Platinum"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Emitter_EV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 2L), (new ItemStack(Items.ender_eye, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.get("Tungsten"), 2L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Osmium"), 4L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Emitter_IV.get(1L)), 200, 16);

        /* Sensors */
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Basic"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Steel"), 4L)), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Quartzite"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Brass"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Sensor_LV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Good"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Aluminium"), 4L)), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NetherQuartz"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Electrum"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Sensor_MV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("StainlessSteel"), 4L)), (GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Emerald"), 1L)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Chrome"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Sensor_HV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Titanium"), 4L)), (new ItemStack(Items.ender_pearl, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Platinum"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Sensor_EV.get(1L)), 200, 16);
        RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("TungstenSteel"), 4L)), (new ItemStack(Items.ender_eye, 1, 1)), (GT_OreDictUnificator.get(OrePrefixes.stick, Materials.get("Osmium"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, (ItemList.Sensor_IV.get(1L)), 200, 16);

        /* Better Jetpack recipe using Dragon Egg */
        if (GT_Mod.gregtechproxy.ElectricJetpackNeedsDragonEgg) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("electricJetpack", 1L));
            RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Basic"), 1L), GT_ModHandler.getIC2Item("casingiron", 4L), (GT_ModHandler.getIC2Item("batBox", 1L)), (new ItemStack(Items.glowstone_dust, 2, 0)), (new ItemStack(Blocks.dragon_egg, 0, 0)), NULL_ITEM_STACK, (GT_ModHandler.getIC2Item("electricJetpack", 1L)), 200, 16);
        }

        /* makes ic2 reactor stuff take some gt parts if ic2 cables are disabled */

        if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorvessel", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorFluidPort", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorAccessHatch", 1L));
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item("reactorRedstonePort", 1L));
            RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.get("Invar"), 1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 6L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (GT_ModHandler.getIC2Item("reactorvessel", 1L)), 200, 16);
            RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(ItemList.Hatch_Input_LV.get(1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 6L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (GT_ModHandler.getIC2Item("reactorFluidPort", 1L)), 200, 16);
            RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(ItemList.Hull_LV.get(1L), (GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Lead"), 6L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (GT_ModHandler.getIC2Item("reactorAccessHatch", 1L)), 200, 16);
            RECIPE_ADDER_INSTANCE.addPartFactoryRecipe(GT_ModHandler.getIC2Item("reactorvessel", 1L), (GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, (GT_ModHandler.getIC2Item("reactorRedstonePort", 1L)), 200, 16);
        }

        GT_ModHandler.removeRecipe(new ItemStack(Items.lava_bucket), ItemList.Cell_Empty.get(1L));
        GT_ModHandler.removeRecipe(new ItemStack(Items.water_bucket), ItemList.Cell_Empty.get(1L));

        GT_ModHandler.removeFurnaceSmelting(ItemList.IC2_Resin.get(1L));

        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.golden_apple, 1, 1), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(9216L), new ItemStack(Items.gold_ingot, 64), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 9216, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.golden_apple, 1, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), new ItemStack(Items.gold_ingot, 7), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 9216, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.golden_carrot, 1, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), new ItemStack(Items.gold_nugget, 6), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 9216, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.speckled_melon, 1, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), new ItemStack(Items.gold_nugget, 6), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 9216, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.mushroom_stew, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), new ItemStack(Items.bowl, 16, 0), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.apple, 32, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.bread, 64, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.porkchop, 12, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cooked_porkchop, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.beef, 12, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cooked_beef, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.fish, 12, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cooked_fished, 16, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.chicken, 12, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cooked_chicken, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.melon, 64, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.pumpkin, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.rotten_flesh, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.spider_eye, 32, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.carrot, 16, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.Food_Raw_Potato.get(16L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.Food_Poisonous_Potato.get(12L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.Food_Baked_Potato.get(24L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cookie, 64, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.cake, 8, 0), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.brown_mushroom_block, 12, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.red_mushroom_block, 12, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.brown_mushroom, 32, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.red_mushroom, 32, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.nether_wart, 32, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getIC2Item("terraWart", 16L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.meefRaw", 12L, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.meefSteak", 16L, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.venisonRaw", 12L, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getModItem(MOD_ID_TF, "item.venisonCooked", 16L, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Methane").getGas(1152L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 4608, 5);

        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.sand, 1, 1), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Diamond"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{5000, 100, 5000}, 50, 30);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, ItemList.IC2_Plantball.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Clay"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{1250, 5000, 5000}, 250, 30);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.grass, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, ItemList.IC2_Plantball.get(1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Clay"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{2500, 5000, 5000}, 250, 30);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.mycelium, 1, OreDictionary.WILDCARD_VALUE), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, new ItemStack(Blocks.brown_mushroom, 1), new ItemStack(Blocks.red_mushroom, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Clay"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{2500, 2500, 5000, 5000}, 650, 30);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(ItemList.IC2_Resin.get(1L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Glue").getFluid(100L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Rubber"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Plastic"), 1L), ItemList.IC2_Plantball.get(1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{10000, 5000, 5000}, 300, 5);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("DarkAsh"), 2L), 0, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 1L), ItemList.TE_Slag.get(1L, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Ash"), 1L)), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, 250);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Items.magma_cream, 1), 0, new ItemStack(Items.blaze_powder, 1), new ItemStack(Items.slime_ball, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, 500);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Uranium"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Uranium235"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Plutonium"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{2000, 200}, 800, 320);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Plutonium"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Plutonium241"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Uranium"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{2000, 3000, 1000}, 1600, 320);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Naquadah"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("NaquadahEnriched"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Naquadria"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{5000, 1000}, 3200, 320);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("NaquadahEnriched"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Naquadria"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Naquadah"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{2000, 3000}, 6400, 640);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Hydrogen").getGas(160L), Materials.get("Deuterium").getGas(40L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 160, 20);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Deuterium").getGas(160L), Materials.get("Tritium").getGas(40L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 160, 80);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Helium").getGas(80L), Materials.get("Helium_3").getGas(5L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 160, 80);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Redstone"), 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Gold"), 2L), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, null, 488, 80);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Endstone"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Helium").getGas(120L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Tungsten"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Platinum"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{625, 625, 9000, 0, 0, 0}, 320, 20);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Netherrack"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Redstone"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Sulfur"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Coal"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Gold"), 1L), NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{5625, 9900, 5625, 625, 0, 0}, 160, 20);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(new ItemStack(Blocks.soul_sand, 1), NULL_ITEM_STACK, NULL_FLUID_STACK, Materials.get("Oil").getFluid(80L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Saltpeter"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Coal"), 1L), new ItemStack(Blocks.sand, 1), NULL_ITEM_STACK, NULL_ITEM_STACK, NULL_ITEM_STACK, new int[]{8000, 2000, 9000, 0, 0, 0}, 200, 80);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(NULL_ITEM_STACK, NULL_ITEM_STACK, Materials.get("Lava").getFluid(100L), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Copper"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Tin"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Silver"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Tantalum"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Tungsten"), 1L), new int[]{2000, 1000, 250, 250, 250, 125}, 80, 80);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(NULL_ITEM_STACK, NULL_ITEM_STACK, FluidRegistry.getFluidStack("ic2pahoehoelava", 100), NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Copper"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Tin"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Gold"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Silver"), 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Tantalum"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.get("Tungsten"), 1L), new int[]{2000, 1000, 250, 250, 250, 125}, 40, 80);

        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("RareEarth"), 1L), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Neodymium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Yttrium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Lanthanum"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Cerium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Cadmium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Caesium"), 1L), new int[]{2500, 2500, 2500, 2500, 2500, 2500}, 64, 20);
        RECIPE_ADDER_INSTANCE.addCentrifugeRecipe(GT_ModHandler.getModItem(MOD_ID_AE, AE_ITEM, 1L, 45), NULL_ITEM_STACK, NULL_FLUID_STACK, NULL_FLUID_STACK, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("BasalticMineralSand"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Olivine"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Obsidian"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Basalt"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Flint"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("RareEarth"), 1L), new int[]{2000, 2000, 2000, 2000, 2000, 2000}, 64, 20);

        GT_Utility.removeSimpleIC2MachineRecipe(new ItemStack(Blocks.cobblestone), GT_ModHandler.getMaceratorRecipeList(), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Stone"), 1L));
        GT_Utility.removeSimpleIC2MachineRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Lapis"), 1L), GT_ModHandler.getMaceratorRecipeList(), ItemList.IC2_Plantball.get(1L));
        GT_Utility.removeSimpleIC2MachineRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Redstone"), 1L), GT_ModHandler.getMaceratorRecipeList(), ItemList.IC2_Plantball.get(1L));
        GT_Utility.removeSimpleIC2MachineRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Glowstone"), 1L), GT_ModHandler.getMaceratorRecipeList(), ItemList.IC2_Plantball.get(1L));

        if (GregTech_API.sThaumcraftCompat != null) {
            String tKey = "GT_WOOD_TO_CHARCOAL";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way of making charcoal magically instead of using regular ovens for this purpose.<BR><BR>To create charcoal from wood you first need an air-free environment, some vacuus essentia is needed for that, then you need to incinerate the wood using ignis essentia and wait until all the water inside the wood is burned away.<BR><BR>This method however doesn't create creosote oil as byproduct.");

            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Charcoal Transmutation", "Turning wood into charcoal", new String[]{"ALUMENTUM"}, ALC, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Charcoal"), 1L), 2, 0, 13, 5, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 10L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 8L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.log.get(Materials.get("Wood")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Charcoal"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)))});

            tKey = "GT_FILL_WATER_BUCKET";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way of filling a bucket with aqua essentia in order to simply get water.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Water Transmutation", "Filling buckets with water", null, ALC, GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.get("Water"), 1L), 2, 0, 16, 5, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.get("Empty"), 1L), GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.get("Water"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, GT_OreDictUnificator.get(OrePrefixes.capsule, Materials.get("Empty"), 1L), GT_OreDictUnificator.get(OrePrefixes.capsule, Materials.get("Water"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Empty"), 1L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Water"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L)))});

            tKey = "GT_TRANSZINC";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply zinc by steeping zinc nuggets in metallum harvested from other metals.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Zinc Transmutation", "Transformation of metals into zinc", new String[]{"TRANSTIN"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Zinc"), 1L), 2, 1, 9, 13, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Zinc")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Zinc"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)))});

            tKey = "GT_TRANSANTIMONY";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply antimony by steeping antimony nuggets in metallum harvested from other metals.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Antimony Transmutation", "Transformation of metals into antimony", new String[]{"GT_TRANSZINC", "TRANSLEAD"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Antimony"), 1L), 2, 1, 9, 14, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Antimony")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Antimony"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L)))});

            tKey = "GT_TRANSNICKEL";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply nickel by steeping nickel nuggets in metallum harvested from other metals.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Nickel Transmutation", "Transformation of metals into nickel", new String[]{"TRANSLEAD"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Nickel"), 1L), 2, 1, 9, 15, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Nickel")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Nickel"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)))});

            tKey = "GT_TRANSCOBALT";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply cobalt by steeping cobalt nuggets in metallum harvested from other metals.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Cobalt Transmutation", "Transformation of metals into cobalt", new String[]{"GT_TRANSNICKEL"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Cobalt"), 1L), 2, 1, 9, 16, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Cobalt")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Cobalt"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1L)))});

            tKey = "GT_TRANSBISMUTH";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply bismuth by steeping bismuth nuggets in metallum harvested from other metals.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Bismuth Transmutation", "Transformation of metals into bismuth", new String[]{"GT_TRANSCOBALT"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Bismuth"), 1L), 2, 1, 11, 17, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Bismuth")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Bismuth"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1L)))});

            tKey = "GT_IRON_TO_STEEL";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way of making Iron harder by just re-ordering its components.<BR><BR>This Method can be used to create a Material called Steel, which is used in many non-Thaumaturgic applications.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Steel Transmutation", "Transforming iron to steel", new String[]{"TRANSIRON", "GT_WOOD_TO_CHARCOAL"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Steel"), 1L), 3, 0, 13, 8, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Iron")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Steel"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 1L)))});

            tKey = "GT_TRANSBRONZE";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way of creating Alloys using the already known transmutations of Copper and Tin.<BR><BR>This Method can be used to create a Bronze directly without having to go through an alloying process.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Bronze Transmutation", "Transformation of metals into bronze", new String[]{"TRANSTIN", "TRANSCOPPER"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Bronze"), 1L), 2, 0, 13, 11, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Bronze")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Bronze"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1L)))});

            tKey = "GT_TRANSELECTRUM";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Electrum as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Electrum Transmutation", "Transformation of metals into electrum", new String[]{"GT_TRANSBRONZE", "TRANSGOLD", "TRANSSILVER"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Electrum"), 1L), 2, 1, 11, 11, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Electrum")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Electrum"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)))});

            tKey = "GT_TRANSBRASS";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Brass as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Brass Transmutation", "Transformation of metals into brass", new String[]{"GT_TRANSBRONZE", "GT_TRANSZINC"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Brass"), 1L), 2, 1, 11, 12, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Brass")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Brass"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1L)))});

            tKey = "GT_TRANSINVAR";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Invar as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Invar Transmutation", "Transformation of metals into invar", new String[]{"GT_TRANSBRONZE", "GT_TRANSNICKEL"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Invar"), 1L), 2, 1, 11, 15, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Invar")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Invar"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L)))});

            tKey = "GT_TRANSCUPRONICKEL";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Cupronickel as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Cupronickel Transmutation", "Transformation of metals into cupronickel", new String[]{"GT_TRANSBRONZE", "GT_TRANSNICKEL"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Cupronickel"), 1L), 2, 1, 11, 16, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Cupronickel")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Cupronickel"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)))});

            tKey = "GT_TRANSBATTERYALLOY";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Battery Alloy as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Battery Alloy Transmutation", "Transformation of metals into battery alloy", new String[]{"GT_TRANSBRONZE", "GT_TRANSANTIMONY"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("BatteryAlloy"), 1L), 2, 1, 11, 13, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("BatteryAlloy")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("BatteryAlloy"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 1L)))});

            tKey = "GT_TRANSSOLDERINGALLOY";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Your discovery of Bronze Transmutation has lead you to the conclusion it works with other Alloys such as Soldering Alloy as well.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Soldering Alloy Transmutation", "Transformation of metals into soldering alloy", new String[]{"GT_TRANSBRONZE", "GT_TRANSANTIMONY"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("SolderingAlloy"), 1L), 2, 1, 11, 14, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("SolderingAlloy")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("SolderingAlloy"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L)))});

            tKey = "GT_ADVANCEDMETALLURGY";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Now that you have discovered all the basic metals, you can finally move on to the next Level of magic metallurgy and create more advanced metals");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Advanced Metallurgic Transmutation", "Mastering the basic metals", new String[]{"GT_TRANSBISMUTH", "GT_IRON_TO_STEEL", "GT_TRANSSOLDERINGALLOY", "GT_TRANSBATTERYALLOY", "GT_TRANSBRASS", "GT_TRANSELECTRUM", "GT_TRANSCUPRONICKEL", "GT_TRANSINVAR"}, ALC, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.get("Iron"), 1L), 3, 0, 16, 14, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 50L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 20L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 20L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 20L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 20L), new TC_Aspects.TC_AspectStack(TC_Aspects.MAGNETO, 20L)), null, new Object[]{RESEARCH_P1 + tKey});

            tKey = "GT_TRANSALUMINIUM";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "You have discovered a way to multiply aluminium by steeping aluminium nuggets in metallum harvested from other metals.<BR><BR>This transmutation is slightly harder to achieve, because aluminium has special properties, which require more order to achieve the desired result.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Aluminium Transmutation", "Transformation of metals into aluminium", new String[]{"GT_ADVANCEDMETALLURGY"}, ALC, GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 1L), 4, 0, 19, 14, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.VOLATUS, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.nugget.get(Materials.get("Aluminium")), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.get("Aluminium"), 3L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VOLATUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)))});

            tKey = "GT_CRYSTALLISATION";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey, "Sometimes when processing your Crystal Shards they become a pile of Dust instead of the mostly required Shard.<BR><BR>You have finally found a way to reverse this Process by using Vitreus Essentia for recrystallising the Shards.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey, "Shard Recrystallisation", "Fixing your precious crystals", new String[]{"ALCHEMICALMANUFACTURE"}, ALC, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedOrder"), 1L), 3, 0, -11, -3, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 5L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 3L)), null, new Object[]{RESEARCH_P1 + tKey, GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("Amber")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("Amber"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedOrder")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedOrder"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedEntropy")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedEntropy"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedAir")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedAir"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedEarth")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedEarth"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedFire")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedFire"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L))), GregTech_API.sThaumcraftCompat.addCrucibleRecipe(tKey, OrePrefixes.dust.get(Materials.get("InfusedWater")), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.get("InfusedWater"), 1L), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L)))});

            tKey = "GT_MAGICENERGY";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey,
                    "While trying to find new ways to integrate magic into your industrial factories, you have discovered a way to convert magical energy into electrical power.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey,
                    "Magic Energy Conversion",
                    "Magic to Power",
                    new String[]{"ARCANEBORE"},
                    "ARTIFICE",
                    ItemList.MagicEnergyConverter_LV.get(1L),
                    3, 0, -3, 10,
                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 10L)),
                    null, new Object[]{RESEARCH_P1 + tKey,
                            GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                                    ItemList.Hull_LV.get(1L),
                                    new ItemStack[]{
                                            new ItemStack(Blocks.beacon),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L),
                                            ItemList.Sensor_MV.get(2L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L),
                                            ItemList.Sensor_MV.get(2L)
                                    },
                                    ItemList.MagicEnergyConverter_LV.get(1L),
                                    5,
                                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L)))});

            tKey = "GT_MAGICENERGY2";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey,
                    "Attempts to increase the output of your Magic Energy generators have resulted in significant improvements.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey,
                    "Adept Magic Energy Conversion",
                    "Magic to Power",
                    new String[]{"GT_MAGICENERGY"},
                    "ARTIFICE",
                    ItemList.MagicEnergyConverter_MV.get(1L),
                    1, 1, -4, 12,
                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 10L)),
                    null, new Object[]{RESEARCH_P1 + tKey,
                            GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                                    ItemList.Hull_MV.get(1L),
                                    new ItemStack[]{
                                            new ItemStack(Blocks.beacon),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Data"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L),
                                            ItemList.Sensor_HV.get(2L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Data"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Iridium"), 1L),
                                            ItemList.Sensor_HV.get(2L)
                                    },
                                    ItemList.MagicEnergyConverter_MV.get(1L),
                                    6,
                                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 64L)))});

            tKey = "GT_MAGICENERGY3";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey,
                    "Attempts to further increase the output of your Magic Energy generators have resulted in great improvements.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey,
                    "Master Magic Energy Conversion",
                    "Magic to Power",
                    new String[]{"GT_MAGICENERGY2"},
                    "ARTIFICE",
                    ItemList.MagicEnergyConverter_HV.get(1L),
                    1, 1, -4, 14,
                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 40L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 20L)),
                    null, new Object[]{RESEARCH_P1 + tKey,
                            GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                                    ItemList.Hull_HV.get(1L),
                                    new ItemStack[]{
                                            new ItemStack(Blocks.beacon),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.get("Thaumium"), 1L),
                                            ItemList.Field_Generator_MV.get(1L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.get("Europium"), 1L),
                                            ItemList.Field_Generator_MV.get(1L)
                                    },
                                    ItemList.MagicEnergyConverter_HV.get(1L),
                                    8,
                                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 128L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 128L)))});


            tKey = "GT_MAGICABSORB";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey,
                    "Research into magical energy conversion methods has identified a way to convert surrounding energies into electrical power.");
            GregTech_API.sThaumcraftCompat.addResearch(tKey,
                    "Magic Energy Absorption",
                    "Harvesting Magic",
                    new String[]{"GT_MAGICENERGY"},
                    "ARTIFICE",
                    ItemList.MagicEnergyAbsorber_LV.get(1L),
                    3, 0, -2, 12,
                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 10L)),
                    null, new Object[]{RESEARCH_P1 + tKey,
                            GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                                    ItemList.Hull_LV.get(1L),
                                    new ItemStack[]{
                                            ItemList.MagicEnergyConverter_LV.get(1L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L),
                                            ItemList.Sensor_MV.get(2L)
                                    },
                                    ItemList.MagicEnergyAbsorber_LV.get(1L),
                                    6,
                                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 16L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 4L)))});

            tKey = "GT_MAGICABSORB2";
            GT_LanguageManager.addStringLocalization(RESEARCH_P1 + tKey,
                    "Moar output! Drain all the Magic!");
            GregTech_API.sThaumcraftCompat.addResearch(tKey,
                    "Improved Magic Energy Absorption",
                    "Harvesting Magic",
                    new String[]{"GT_MAGICABSORB"},
                    "ARTIFICE",
                    ItemList.MagicEnergyAbsorber_EV.get(1L),
                    3, 1, -2, 14,
                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 10L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 20L),
                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 10L)),
                    null, new Object[]{RESEARCH_P1 + tKey,
                            GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                                    ItemList.Hull_MV.get(1L),
                                    new ItemStack[]{
                                            ItemList.MagicEnergyConverter_MV.get(1L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L),
                                            ItemList.Sensor_HV.get(2L),
                                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Advanced"), 1L),
                                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.get("Thaumium"), 1L)
                                    },
                                    ItemList.MagicEnergyAbsorber_MV.get(1L),
                                    6,
                                    Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 64L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 32L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 64L),
                                            new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 8L)))


                            , GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                            ItemList.Hull_HV.get(1L),
                            new ItemStack[]{
                                    ItemList.MagicEnergyConverter_MV.get(1L),
                                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L),
                                    GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1, 16),
                                    ItemList.Field_Generator_MV.get(1L),
                                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Elite"), 1L),
                                    GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1, 16)
                            },
                            ItemList.MagicEnergyAbsorber_HV.get(1L),
                            8,
                            Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 128L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 128L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 64L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 128L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 16L)))


                            , GregTech_API.sThaumcraftCompat.addInfusionRecipe(tKey,
                            ItemList.Hull_EV.get(1L),
                            new ItemStack[]{
                                    ItemList.MagicEnergyConverter_HV.get(1L),
                                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 1L),
                                    GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1, 16),
                                    ItemList.Field_Generator_HV.get(1L),
                                    GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.get("Master"), 1L),
                                    GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1, 16)
                            },
                            ItemList.MagicEnergyAbsorber_EV.get(1L),
                            10,
                            Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 256L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 128L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 256L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 128L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 256L),
                                    new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 64L)))
                    });

        }
        for (MaterialStack[] tMats : this.mAlloySmelterList) {
            ItemStack tDust1 = GT_OreDictUnificator.get(OrePrefixes.dust, tMats[0].mMaterial, tMats[0].mAmount);
            ItemStack tDust2 = GT_OreDictUnificator.get(OrePrefixes.dust, tMats[1].mMaterial, tMats[1].mAmount);
            ItemStack tIngot1 = GT_OreDictUnificator.get(OrePrefixes.ingot, tMats[0].mMaterial, tMats[0].mAmount);
            ItemStack tIngot2 = GT_OreDictUnificator.get(OrePrefixes.ingot, tMats[1].mMaterial, tMats[1].mAmount);
            ItemStack tOutputIngot = GT_OreDictUnificator.get(OrePrefixes.ingot, tMats[2].mMaterial, tMats[2].mAmount);
            if (tOutputIngot != NULL_ITEM_STACK) {
                GT_ModHandler.addAlloySmelterRecipe(tIngot1, tDust2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tIngot1, tIngot2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tDust1, tIngot2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tDust1, tDust2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
            }
        }

    }
}
