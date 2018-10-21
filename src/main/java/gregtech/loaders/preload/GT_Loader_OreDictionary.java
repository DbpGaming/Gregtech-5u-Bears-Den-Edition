package gregtech.loaders.preload;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.materials.Materials;
import gregtech.api.enums.OreDictNames;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static gregtech.api.enums.GT_Values.MOD_ID_AE;
import static gregtech.api.enums.GT_Values.MOD_ID_RC;
import static gregtech.api.enums.GT_Values.MOD_ID_TC;
import static gregtech.api.enums.GT_Values.MOD_ID_TE;
import static gregtech.api.enums.GT_Values.MOD_ID_TF;

public class GT_Loader_OreDictionary
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Register OreDict Entries of Non-GT-Items.");
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Empty"), ItemList.Cell_Empty.get(1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Lava"), ItemList.Cell_Lava.get(1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Lava"), GT_ModHandler.getIC2Item("lavaCell", 1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Water"), ItemList.Cell_Water.get(1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Water"), GT_ModHandler.getIC2Item("waterCell", 1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("Creosote"), GT_ModHandler.getModItem(MOD_ID_RC, "fluid.creosote.cell", 1L));


        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("UUMatter"), GT_ModHandler.getIC2Item("uuMatterCell", 1L));
        GT_OreDictUnificator.set(OrePrefixes.cell, Materials.get("ConstructionFoam"), GT_ModHandler.getIC2Item("CFCell", 1L));

        GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.get("Empty"), new ItemStack(Items.bucket, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.get("Water"), new ItemStack(Items.water_bucket, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.get("Lava"), new ItemStack(Items.lava_bucket, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.get("Milk"), new ItemStack(Items.milk_bucket, 1, 0));

        GT_OreDictUnificator.set(OrePrefixes.bottle, Materials.get("Empty"), new ItemStack(Items.glass_bottle, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.bottle, Materials.get("Water"), new ItemStack(Items.potionitem, 1, 0));

        GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.get("Iridium"), GT_ModHandler.getIC2Item("iridiumPlate", 1L));
        GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.get("Advanced"), GT_ModHandler.getIC2Item("advancedAlloy", 1L));
        GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.get("Carbon"), GT_ModHandler.getIC2Item("carbonPlate", 1L));

        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Coal"), new ItemStack(Blocks.coal_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Iron"), new ItemStack(Blocks.iron_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Lapis"), new ItemStack(Blocks.lapis_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Redstone"), new ItemStack(Blocks.redstone_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Redstone"), new ItemStack(Blocks.lit_redstone_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Gold"), new ItemStack(Blocks.gold_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Diamond"), new ItemStack(Blocks.diamond_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("Emerald"), new ItemStack(Blocks.emerald_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ore, Materials.get("NetherQuartz"), new ItemStack(Blocks.quartz_ore, 1));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Copper"), GT_ModHandler.getIC2Item("copperIngot", 1L));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Tin"), GT_ModHandler.getIC2Item("tinIngot", 1L));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Lead"), GT_ModHandler.getIC2Item("leadIngot", 1L));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Bronze"), GT_ModHandler.getIC2Item("bronzeIngot", 1L));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Silver"), GT_ModHandler.getIC2Item("silverIngot", 1L));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Iridium"), GT_ModHandler.getIC2Item("iridiumOre", 1L));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Lapis"), new ItemStack(Items.dye, 1, 4));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("EnderEye"), new ItemStack(Items.ender_eye, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("EnderPearl"), new ItemStack(Items.ender_pearl, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Diamond"), new ItemStack(Items.diamond, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Emerald"), new ItemStack(Items.emerald, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Coal"), new ItemStack(Items.coal, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Charcoal"), new ItemStack(Items.coal, 1, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("NetherQuartz"), new ItemStack(Items.quartz, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("NetherStar"), new ItemStack(Items.nether_star, 1));
        GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.get("Gold"), new ItemStack(Items.gold_nugget, 1));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Gold"), new ItemStack(Items.gold_ingot, 1));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Iron"), new ItemStack(Items.iron_ingot, 1));
        GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Paper"), new ItemStack(Items.paper, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Sugar"), new ItemStack(Items.sugar, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Bone"), ItemList.Dye_Bonemeal.get(1L));
        GT_OreDictUnificator.set(OrePrefixes.stick, Materials.get("Wood"), new ItemStack(Items.stick, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Redstone"), new ItemStack(Items.redstone, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Gunpowder"), new ItemStack(Items.gunpowder, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Glowstone"), new ItemStack(Items.glowstone_dust, 1));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Blaze"), new ItemStack(Items.blaze_powder, 1));
        GT_OreDictUnificator.set(OrePrefixes.stick, Materials.get("Blaze"), new ItemStack(Items.blaze_rod, 1));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Iron"), new ItemStack(Blocks.iron_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Gold"), new ItemStack(Blocks.gold_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Diamond"), new ItemStack(Blocks.diamond_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Emerald"), new ItemStack(Blocks.emerald_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Lapis"), new ItemStack(Blocks.lapis_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Coal"), new ItemStack(Blocks.coal_block, 1, 0));
        GT_OreDictUnificator.set(OrePrefixes.block, Materials.get("Redstone"), new ItemStack(Blocks.redstone_block, 1, 0));
        if (Blocks.ender_chest != null) {
            GT_OreDictUnificator.registerOre(OreDictNames.enderChest, new ItemStack(Blocks.ender_chest, 1));
        }
        GT_OreDictUnificator.registerOre(OreDictNames.craftingAnvil, new ItemStack(Blocks.anvil, 1));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingAnvil, GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.anvil", 1L, 0));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingIndustrialDiamond, ItemList.IC2_Industrial_Diamond.get(1L));
        GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.get("Wood"), GT_ModHandler.getModItem(MOD_ID_TE, "sawdust", 1L));
        GT_OreDictUnificator.registerOre(OrePrefixes.glass, Materials.get("Reinforced"), GT_ModHandler.getIC2Item("reinforcedGlass", 1L));
        GT_OreDictUnificator.registerOre(OrePrefixes.glass, Materials.get("Reinforced"), GT_ModHandler.getModItem(MOD_ID_TE, "glassHardened", 1L));

        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Basalt"), GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.cube", 1L, 6));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Marble"), GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.cube", 1L, 7));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Basalt"), GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.brick.abyssal", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Marble"), GT_ModHandler.getModItem(MOD_ID_RC, "tile.railcraft.brick.quarried", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Obsidian"), new ItemStack(Blocks.obsidian, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.mossy_cobblestone, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.mossy_cobblestone, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.cobblestone, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneSmooth, new ItemStack(Blocks.stone, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneBricks, new ItemStack(Blocks.stonebrick, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.stonebrick, 1, 1));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneCracked, new ItemStack(Blocks.stonebrick, 1, 2));
        GT_OreDictUnificator.registerOre(OrePrefixes.stoneChiseled, new ItemStack(Blocks.stonebrick, 1, 3));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Sand"), new ItemStack(Blocks.sandstone, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Netherrack"), new ItemStack(Blocks.netherrack, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("NetherBrick"), new ItemStack(Blocks.nether_brick, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.get("Endstone"), new ItemStack(Blocks.end_stone, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre("paperResearchFragment", GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1L, 9));
        GT_OreDictUnificator.registerOre("itemCertusQuartz", GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 1));
        GT_OreDictUnificator.registerOre("itemNetherQuartz", GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 10));
        GT_OreDictUnificator.registerOre("itemCertusQuartz", GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 11));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 1));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 10));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem(MOD_ID_AE, "item.ItemMultiMaterial", 1L, 11));
        GT_OreDictUnificator.registerOre("cropLemon", ItemList.FR_Lemon.get(1L));
        GT_OreDictUnificator.registerOre("cropCoffee", ItemList.IC2_CoffeeBeans.get(1L));
        GT_OreDictUnificator.registerOre("cropPotato", ItemList.Food_Raw_Potato.get(1L));
        GT_OreDictUnificator.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item("reBattery", 1L));
        GT_OreDictUnificator.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item("chargedReBattery", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.get("Basic"), GT_ModHandler.getIC2Item("reBattery", 1L));
        GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.get("Basic"), GT_ModHandler.getIC2Item("chargedReBattery", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.get("Advanced"), GT_ModHandler.getIC2Item("advBattery", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.get("Elite"), GT_ModHandler.getIC2Item("energyCrystal", 1L, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.get("Master"), GT_ModHandler.getIC2Item("lapotronCrystal", 1L, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingWireGold, GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingWireIron, GT_ModHandler.getIC2Item("insulatedIronCableItem", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingWireTin, GT_ModHandler.getIC2Item("insulatedTinCableItem", 1L, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L)));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.redstone_torch, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.unlit_redstone_torch, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(Blocks.crafting_table, 1));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(GregTech_API.sBlockMachines, 1, 16));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.piston, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.sticky_piston, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingSafe, new ItemStack(GregTech_API.sBlockMachines, 1, 45));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingSafe, GT_ModHandler.getIC2Item("personalSafe", 1L));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.chest, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.trapped_chest, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.furnace, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.lit_furnace, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingPump, GT_ModHandler.getIC2Item("pump", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingElectromagnet, GT_ModHandler.getIC2Item("magnetizer", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingTeleporter, GT_ModHandler.getIC2Item("teleporter", 1L));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingMacerator, GT_ModHandler.getIC2Item("macerator", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingMacerator, new ItemStack(GregTech_API.sBlockMachines, 1, 50));


        GT_OreDictUnificator.registerOre(OreDictNames.craftingExtractor, GT_ModHandler.getIC2Item("extractor", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingExtractor, new ItemStack(GregTech_API.sBlockMachines, 1, 51));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingCompressor, GT_ModHandler.getIC2Item("compressor", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingCompressor, new ItemStack(GregTech_API.sBlockMachines, 1, 52));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingRecycler, GT_ModHandler.getIC2Item("recycler", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingRecycler, new ItemStack(GregTech_API.sBlockMachines, 1, 53));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingIronFurnace, GT_ModHandler.getIC2Item("ironFurnace", 1L));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingCentrifuge, new ItemStack(GregTech_API.sBlockMachines, 1, 62));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingInductionFurnace, GT_ModHandler.getIC2Item("inductionFurnace", 1L));


        GT_OreDictUnificator.registerOre(OreDictNames.craftingElectricFurnace, GT_ModHandler.getIC2Item("electroFurnace", 1L));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingElectricFurnace, new ItemStack(GregTech_API.sBlockMachines, 1, 54));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingGenerator, GT_ModHandler.getIC2Item("generator", 1L));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingGeothermalGenerator, GT_ModHandler.getIC2Item("geothermalGenerator", 1L));

        GT_OreDictUnificator.registerOre(OreDictNames.craftingFeather, new ItemStack(Items.feather, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingFeather, GT_ModHandler.getModItem(MOD_ID_TF, "item.tfFeather", 1L, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre("itemWheat", new ItemStack(Items.wheat, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("paperEmpty", new ItemStack(Items.paper, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("paperMap", new ItemStack(Items.map, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("paperMap", new ItemStack(Items.filled_map, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("bookEmpty", new ItemStack(Items.book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("bookWritable", new ItemStack(Items.writable_book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(Items.written_book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre("bookEnchanted", new ItemStack(Items.enchanted_book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.writable_book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.written_book, 1, OreDictionary.WILDCARD_VALUE));
        GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.enchanted_book, 1, OreDictionary.WILDCARD_VALUE));

        GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.get("Basic"), GT_ModHandler.getIC2Item("electronicCircuit", 1L));
        GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.get("Advanced"), GT_ModHandler.getIC2Item("advancedCircuit", 1L));
    }
}
