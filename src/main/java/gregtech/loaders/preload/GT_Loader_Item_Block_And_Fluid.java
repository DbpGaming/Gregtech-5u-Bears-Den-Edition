package gregtech.loaders.preload;

import codechicken.nei.api.API;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.materials.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.items.GT_Generic_Item;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Casings1;
import gregtech.common.blocks.GT_Block_Casings2;
import gregtech.common.blocks.GT_Block_Casings3;
import gregtech.common.blocks.GT_Block_Casings4;
import gregtech.common.blocks.GT_Block_Concretes;
import gregtech.common.blocks.GT_Block_Granites;
import gregtech.common.blocks.GT_Block_Machines;
import gregtech.common.blocks.GT_Block_Ores;
import gregtech.common.blocks.GT_Block_Stones;
import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.common.items.GT_DepletetCell_Item;
import gregtech.common.items.GT_FluidDisplayItem;
import gregtech.common.items.GT_IntegratedCircuit_Item;
import gregtech.common.items.GT_MetaGenerated_Item_01;
import gregtech.common.items.GT_MetaGenerated_Item_02;
import gregtech.common.items.GT_MetaGenerated_Item_03;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.items.GT_NeutronReflector_Item;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import static gregtech.api.enums.GT_Values.DEBUG_LEVEL_1;
import static gregtech.api.enums.GT_Values.EMPTY_STRING;
import static gregtech.api.enums.GT_Values.MOD_ID_AE;
import static gregtech.api.enums.GT_Values.MOD_ID_HaC;
import static gregtech.api.enums.GT_Values.MOD_ID_RC;
import static gregtech.api.enums.GT_Values.MOD_ID_TC;
import static gregtech.api.enums.GT_Values.MOD_ID_TF;

public class GT_Loader_Item_Block_And_Fluid
        implements Runnable {
    public void run() {
        Materials.get("Ice").setFluid(GT_ModHandler.getWater(1000L).getFluid());
        Materials.get("Water").setFluid(Materials.get("Ice").getFluid());
        Materials.get("Lava").setFluid(GT_ModHandler.getLava(1000L).getFluid());

        GT_Log.out.println("GT_Mod: Register Books.");

        GT_Utility.getWrittenBook("Manual_Printer", "Printer Manual V2.0", "Gregorius Techneticies", "This Manual explains the different Functionalities the GregTech Printing Factory has built in, which are not in NEI. Most got NEI Support now, but there is still some left without it.",
                "1. Coloring Items and Blocks: You know those Crafting Recipes, which have a dye surrounded by 8 Item to dye them? Or the ones which have just one Item and one Dye in the Grid? Those two Recipe Types can be cheaply automated using the Printer.",
                "The Colorization Functionality even optimizes the Recipes, which normally require 8 Items + 1 Dye to 1 Item and an 8th of the normally used Dye in Fluid Form, isn't that awesome?",
                "2. Copying Books: This Task got slightly harder. The first Step is putting the written and signed Book inside the Scanner with a Data Stick ready to receive the Data.",
                "Now insert the Stick into the Data Slot of the Printer and add 3 pieces of Paper together with 144 Liters of actual Ink Fluid. Water mixed and chemical Dyes won't work on Paper without messing things up!",
                "You got a stack of Pages for your new Book, just put them into the Assembler with some Glue and a piece of Leather for the Binding, and you receive an identical copy of the Book, which would stack together with the original.",
                "3. Renaming Items: This Functionality is no longer Part of the Printer. There is now a Name Mold for the Forming Press to imprint a Name into an Item, just rename the Mold in an Anvil and use it in the Forming Press on any Item.",
                "4. Crafting of Books, Maps, Nametags etc etc etc: Those Recipes moved to other Machines, just look them up in NEI.");

        GT_Utility.getWrittenBook("Manual_Punch_Cards", "Punch Card Manual V0.0", "Gregorius Techneticies", "This Manual will explain the Functionality of the Punch Cards, once they are fully implemented. And no, they won't be like the IRL Punch Cards. This is just a current Idea Collection.",
                "(i1&&i2)?o1=15:o1=0;=10",
                "ignore all Whitespace Characters, use Long for saving the Numbers",
                "&& || ^^ & | ^ ! ++ -- + - % / // * ** << >> >>> < > <= >= == !=  ~ ( ) ?: , ; ;= ;=X; = i0 i1 i2 i3 i4 i5 o0 o1 o2 o3 o4 o5 v0 v1 v2 v3 v4 v5 v6 v7 v8 v9 m0 m1 m2 m3 m4 m5 m6 m7 m8 m9 A B C D E F",
                "'0' = false, 'everything but 0' = true, '!' turns '0' into '1' and everything else into '0'", "',' is just a separator for multiple executed Codes in a row.",
                "';' means that the Program waits until the next tick before continuing. ';=10' and ';=10;' both mean that it will wait 10 Ticks instead of 1. And ';=0' or anything < 0 will default to 0.",
                "If the '=' Operator is used within Brackets, it returns the value the variable has been set to.",
                "The Program saves the Char Index of the current Task, the 10 Variables (which reset to 0 as soon as the Program Loop stops), the 10 Member Variables and the remaining waiting Time in its NBT.",
                "A = 10, B = 11, C = 12, D = 13, E = 14, F = 15, just for Hexadecimal Space saving, since Redstone has only 4 Bits.",
                "For implementing Loops you just need 1 Punch Card per Loop, these Cards can restart once they are finished, depending on how many other Cards there are in the Program Loop you inserted your Card into, since it will process them procedurally.",
                "A Punch Card Processor can run up to four Loops, each with the length of seven Punch Cards, parallel.",
                "Why does the Punch Card need Ink to be made, you ask? Because the empty one needs to have some lines on, and the for the punched one it prints the Code to execute in a human readable format on the Card.");

        GT_Utility.getWrittenBook("Manual_Microwave", "Microwave Oven Manual", "Kitchen Industries", "Congratulations, you inserted a random seemingly empty Book into the Microwave and these Letters appeared out of nowhere.",
                "You just got a Microwave Oven and asked yourself 'why do I even need it?'. It's simple, the Microwave can cook for just 128 EU and at an insane speed. Not even a normal E-furnace can do it that fast and cheap!",
                "This is the cheapest and fastest way to cook for you. That is why the Microwave Oven can be found in almost every Kitchen (see www.youwannabuyakitchen.ly).",
                "Long time exposure to Microwaves can cause Cancer, but we doubt Steve lives long enough to die because of that.",
                "Do not insert any Metals. It might result in an Explosion.",
                "Do not dry Animals with it. It will result in a Hot Dog, no matter which Animal you put into it.",
                "Do not insert inflammable Objects. The Oven will catch on Fire.", "Do not insert Explosives such as Eggs. Just don't.");

        GT_Log.out.println("GT_Mod: Register Items.");

        new GT_IntegratedCircuit_Item();
        new GT_MetaGenerated_Item_01();
        new GT_MetaGenerated_Item_02();
        new GT_MetaGenerated_Item_03();
        new GT_MetaGenerated_Tool_01();

        new GT_FluidDisplayItem();
        
        ItemList.Rotor_LV.set(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Tin"), 1L));
        ItemList.Rotor_MV.set(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Bronze"), 1L));
        ItemList.Rotor_HV.set(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("Steel"), 1L));
        ItemList.Rotor_EV.set(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("StainlessSteel"), 1L));
        ItemList.Rotor_IV.set(GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.get("TungstenSteel"), 1L));
        

        Item tItem = (Item) GT_Utility.callConstructor("gregtech.common.items.GT_SensorCard_Item", 0, null, false, new Object[]{"sensorcard", "GregTech Sensor Card"});
        ItemList.NC_SensorCard.set(tItem == null ? new GT_Generic_Item("sensorcard", "GregTech Sensor Card", "Nuclear Control not installed", false) : tItem);

        ItemList.Neutron_Reflector.set(new GT_NeutronReflector_Item("neutronreflector", "Iridium Neutron Reflector", 0));
        GT_ModHandler.addCraftingRecipe(ItemList.Neutron_Reflector.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RRR", "RPR", "RRR", 'R', GT_ModHandler.getIC2Item("reactorReflectorThick", 1L, 1), 'P', OrePrefixes.plateAlloy.get(Materials.get("Iridium"))});

        ItemList.Reactor_Coolant_He_1.set(GregTech_API.constructCoolantCellItem("60k_Helium_Coolantcell", "60k He Coolant Cell", 60000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_1.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" P ", "PCP", " P ", 'C', OrePrefixes.cell.get(Materials.get("Helium")), 'P', OrePrefixes.plate.get(Materials.get("Tin"))});

        ItemList.Reactor_Coolant_He_3.set(GregTech_API.constructCoolantCellItem("180k_Helium_Coolantcell", "180k He Coolant Cell", 180000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_3.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PCP", "PCP", 'C', ItemList.Reactor_Coolant_He_1, 'P', OrePrefixes.plate.get(Materials.get("Tin"))});

        ItemList.Reactor_Coolant_He_6.set(GregTech_API.constructCoolantCellItem("360k_Helium_Coolantcell", "360k He Coolant Cell", 360000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_6.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PDP", "PCP", 'C', ItemList.Reactor_Coolant_He_3, 'P', OrePrefixes.plate.get(Materials.get("Tin")), 'D', OrePrefixes.plateDense.get(Materials.get("Copper"))});

        ItemList.Reactor_Coolant_NaK_1.set(GregTech_API.constructCoolantCellItem("60k_NaK_Coolantcell", "60k NaK Coolantcell", 60000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_1.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TST", "PCP", "TST", 'C', GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L, 1), 'T', OrePrefixes.plate.get(Materials.get("Tin")), 'S', OrePrefixes.dust.get(Materials.get("Sodium")), 'P', OrePrefixes.dust.get(Materials.get("Potassium"))});

        ItemList.Reactor_Coolant_NaK_3.set(GregTech_API.constructCoolantCellItem("180k_NaK_Coolantcell", "180k NaK Coolantcell", 180000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_3.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PCP", "PCP", 'C', ItemList.Reactor_Coolant_NaK_1, 'P', OrePrefixes.plate.get(Materials.get("Tin"))});

        ItemList.Reactor_Coolant_NaK_6.set(GregTech_API.constructCoolantCellItem("360k_NaK_Coolantcell", "360k NaK Coolantcell", 360000));
        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_6.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PDP", "PCP", 'C', ItemList.Reactor_Coolant_NaK_3, 'P', OrePrefixes.plate.get(Materials.get("Tin")), 'D', OrePrefixes.plateDense.get(Materials.get("Copper"))});

        ItemList.ThoriumCell_1.set(new GT_RadioactiveCellIC_Item("Thoriumcell", "Fuel Rod (Thorium)", 1, 50000, 0.2F, 0, 0.25F));

        ItemList.ThoriumCell_2.set(new GT_RadioactiveCellIC_Item("Double_Thoriumcell", "Double Fuel Rod (Thorium)", 2, 50000, 0.2F, 0, 0.25F));
        GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_2.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.ThoriumCell_1, 'P', OrePrefixes.plate.get(Materials.get("Iron"))});

        ItemList.ThoriumCell_4.set(new GT_RadioactiveCellIC_Item("Quad_Thoriumcell", "Quad Fuel Rod (Thorium)", 4, 50000, 0.2F, 0, 0.25F));
        GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_4.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.ThoriumCell_1, 'P', OrePrefixes.plate.get(Materials.get("Iron")), 'C', OrePrefixes.plate.get(Materials.get("Copper"))});

        ItemList.Depleted_Thorium_1.set(new GT_DepletetCell_Item("ThoriumcellDep", "Fuel Rod (Depleted Thorium)", 1));
        ItemList.Depleted_Thorium_2.set(new GT_DepletetCell_Item("Double_ThoriumcellDep", "Dual Fuel Rod (Depleted Thorium)", 2));
        ItemList.Depleted_Thorium_4.set(new GT_DepletetCell_Item("Quad_ThoriumcellDep", "Quad Fuel Rod (Depleted Thorium)", 4));

        GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_1.get(1), 5000, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Lutetium"), 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Thorium"), 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 1L));
        GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_2.get(1), 5000, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Lutetium"), 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Thorium"), 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 3L));
        GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_4.get(1), 5000, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Lutetium"), 4L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Thorium"), 8L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.get("Iron"), 6L));


        GT_Log.out.println("GT_Mod: Adding Blocks.");
        GregTech_API.sBlockMachines = new GT_Block_Machines();
        GregTech_API.sBlockCasings1 = new GT_Block_Casings1();
        GregTech_API.sBlockCasings2 = new GT_Block_Casings2();
        GregTech_API.sBlockCasings3 = new GT_Block_Casings3();
        GregTech_API.sBlockCasings4 = new GT_Block_Casings4();
        GregTech_API.sBlockGranites = new GT_Block_Granites();
        GregTech_API.sBlockConcretes = new GT_Block_Concretes();
        GregTech_API.sBlockStones = new GT_Block_Stones();
        GregTech_API.sBlockOres1 = new GT_Block_Ores();

        GT_Log.out.println("GT_Mod: Register TileEntities.");


        BaseMetaTileEntity tBaseMetaTileEntity = GregTech_API.constructBaseMetaTileEntity();

        GT_Log.out.println("GT_Mod: Testing BaseMetaTileEntity.");
        if (tBaseMetaTileEntity == null) {
            GT_Log.out.println("GT_Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
            throw new RuntimeException(EMPTY_STRING);
        }
        GT_Log.out.println("GT_Mod: Registering the BaseMetaTileEntity.");
        GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
        FMLInterModComms.sendMessage(MOD_ID_AE, "whitelist-spatial", tBaseMetaTileEntity.getClass().getName());

        GT_Log.out.println("GT_Mod: Registering the BaseMetaPipeEntity.");
        GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
        FMLInterModComms.sendMessage(MOD_ID_AE, "whitelist-spatial", BaseMetaPipeEntity.class.getName());

        GT_Log.out.println("GT_Mod: Registering the Ore TileEntity.");
        GameRegistry.registerTileEntity(GT_TileEntity_Ores.class, "GT_TileEntity_Ores");
        FMLInterModComms.sendMessage(MOD_ID_AE, "whitelist-spatial", GT_TileEntity_Ores.class.getName());

        GT_Log.out.println("GT_Mod: Registering Fluids.");
        Materials.get("ConstructionFoam").setFluid(GT_Utility.getFluidForFilledItem(GT_ModHandler.getIC2Item("CFCell", 1L), true).getFluid());
        Materials.get("UUMatter").setFluid(GT_Utility.getFluidForFilledItem(GT_ModHandler.getIC2Item("uuMatterCell", 1L), true).getFluid());

//    GT_Mod.gregtechproxy.addFluid("HeliumPlasma", "Helium Plasma", Materials.get("Helium"), 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, Materials.get("Helium"), 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
//    GT_Mod.gregtechproxy.addFluid("NitrogenPlasma", "Nitrogen Plasma", Materials.get("Nitrogen"), 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, Materials.get("Nitrogen"), 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);


        GT_Mod.gregtechproxy.addFluid("Air", "Air", Materials.get("Air"), 2, 295, ItemList.Cell_Air.get(1L), ItemList.Cell_Empty.get(1L), 2000);
        GT_Mod.gregtechproxy.addFluid("Oxygen", "Oxygen", Materials.get("Oxygen"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oxygen"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Hydrogen", "Hydrogen", Materials.get("Hydrogen"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Hydrogen"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Deuterium", "Deuterium", Materials.get("Deuterium"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Deuterium"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Tritium", "Tritium", Materials.get("Tritium"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Tritium"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Helium", "Helium", Materials.get("Helium"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Helium"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Fluorine", "Fluorine", Materials.get("Fluorine"), 2, 53, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Fluorine"), 1L), ItemList.Cell_Empty.get(1L), 1000);
//    Materials.get("Lithium").mStandardMoltenFluid = new Fluid("lithium");
        GT_Mod.gregtechproxy.addFluid("Helium-3", "Helium-3", Materials.get("Helium_3"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Helium_3"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Methane", "Methane", Materials.get("Methane"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Methane"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Nitrogen", "Nitrogen", Materials.get("Nitrogen"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Nitrogen"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("NitrogenDioxide", "Nitrogen Dioxide", Materials.get("NitrogenDioxide"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("NitrogenDioxide"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("SulfurDioxide", "Sulfur Dioxide", Materials.get("SulfurDioxide"), 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("SulfurDioxide"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Steam", "Steam", Materials.get("Water"), 2, 375);
        Materials.get("Ice").setGas(Materials.get("Water").getGas());
        Materials.get("Water").getGas().setTemperature(375).setGaseous(true);

        ItemList.sOilExtraHeavy = GT_Mod.gregtechproxy.addFluid("liquid_extra_heavy_oil", "Very Heavy Oil", null, 1, 295);
        ItemList.sOilHeavy = GT_Mod.gregtechproxy.addFluid("liquid_heavy_oil", "Heavy Oil", null, 1, 295);
        ItemList.sOilMedium = GT_Mod.gregtechproxy.addFluid("liquid_medium_oil", "Raw Oil", null, 1, 295);
        ItemList.sOilLight = GT_Mod.gregtechproxy.addFluid("liquid_light_oil", "Light Oil", null, 1, 295);
        ItemList.sNaturalGas = GT_Mod.gregtechproxy.addFluid("gas_natural_gas", "Natural Gas", null, 2, 295);

        ItemList.sNitricAcid = GT_Mod.gregtechproxy.addFluid("nitricacid", "Nitric acid ", null, 1, 295);
        ItemList.sBlueVitriol = GT_Mod.gregtechproxy.addFluid("solution.bluevitriol", "Blue Vitriol water solution", null, 1, 295);
        ItemList.sGreenVitriol = GT_Mod.gregtechproxy.addFluid("solution.greenvitriol", "Green Vitriol water solution", null, 1, 295);
        ItemList.sNickelSulfate = GT_Mod.gregtechproxy.addFluid("solution.nickelsulfate", "Nickel Sulfate water solution", null, 1, 295);

        GT_Mod.gregtechproxy.addFluid("UUAmplifier", "UU Amplifier", Materials.get("UUAmplifier"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("UUAmplifier"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Chlorine", "Chlorine", Materials.get("Chlorine"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Chlorine"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Mercury", "Mercury", Materials.get("Mercury"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Mercury"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("NitroFuel", "Nitro Diesel", Materials.get("NitroFuel"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("NitroFuel"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("SodiumPersulfate", "Sodium Persulfate", Materials.get("SodiumPersulfate"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("SodiumPersulfate"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("Glyceryl", "Glyceryl Trinitrate", Materials.get("Glyceryl"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Glyceryl"), 1L), ItemList.Cell_Empty.get(1L), 1000);

        GT_Mod.gregtechproxy.addFluid("lubricant", "Lubricant", Materials.get("Lubricant"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Lubricant"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("creosote", "Creosote Oil", Materials.get("Creosote"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Creosote"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("seedoil", "Seed Oil", Materials.get("SeedOil"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("SeedOil"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("fishoil", "Fish Oil", Materials.get("FishOil"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("FishOil"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("oil", "Oil", Materials.get("Oil"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Oil"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("fuel", "Diesel", Materials.get("Fuel"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Fuel"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("for.honey", "Honey", Materials.get("Honey"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Honey"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("biomass", "Biomass", Materials.get("Biomass"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Biomass"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("bioethanol", "Bio Ethanol", Materials.get("Ethanol"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Ethanol"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("sulfuricacid", "Sulfuric Acid", Materials.get("SulfuricAcid"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("SulfuricAcid"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("milk", "Milk", Materials.get("Milk"), 1, 290, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Milk"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("mcguffium", "Mc Guffium 239", Materials.get("McGuffium239"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("McGuffium239"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("glue", "Glue", Materials.get("Glue"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Glue"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("hotfryingoil", "Hot Frying Oil", Materials.get("FryingOilHot"), 1, 400, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("FryingOilHot"), 1L), ItemList.Cell_Empty.get(1L), 1000);

        GT_Mod.gregtechproxy.addFluid("fieryblood", "Fiery Blood", Materials.get("FierySteel"), 1, 6400, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("FierySteel"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        GT_Mod.gregtechproxy.addFluid("holywater", "Holy Water", Materials.get("HolyWater"), 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("HolyWater"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        if (ItemList.TF_Vial_FieryBlood.get(1L) != null) {
            FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("FierySteel").getFluid(250L), ItemList.TF_Vial_FieryBlood.get(1L), ItemList.Bottle_Empty.get(1L)));
        }
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("Milk").getFluid(1000L), GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.get("Milk"), 1L), GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.get("Empty"), 1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("Milk").getFluid(250L), ItemList.Bottle_Milk.get(1L), ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("HolyWater").getFluid(250L), ItemList.Bottle_Holy_Water.get(1L), ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("McGuffium239").getFluid(250L), ItemList.McGuffium_239.get(1L), ItemList.Bottle_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("Fuel").getFluid(100L), ItemList.Tool_Lighter_Invar_Full.get(1L), ItemList.Tool_Lighter_Invar_Empty.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.get("Fuel").getFluid(1000L), ItemList.Tool_Lighter_Platinum_Full.get(1L), ItemList.Tool_Lighter_Platinum_Empty.get(1L)));

        Dyes.dyeBlack.addFluidDye(GT_Mod.gregtechproxy.addFluid("squidink", "Squid Ink", null, 1, 295));
        Dyes.dyeBlue.addFluidDye(GT_Mod.gregtechproxy.addFluid("indigo", "Indigo Dye", null, 1, 295));
        Fluid tFluid;
        for (byte i = 0; i < Dyes.getValidColors().length; i = (byte) (i + 1)) {
            Dyes tDye = Dyes.getValidColors()[i];
            tFluid = GT_Mod.gregtechproxy.addFluid("dye.watermixed." + tDye.name().toLowerCase(), "dyes", "Water Mixed " + tDye.mName + " Dye", null, tDye.getRGBa(), 1, 295, null, null, 0);
            tDye.addFluidDye(tFluid);
            tFluid = GT_Mod.gregtechproxy.addFluid("dye.chemical." + tDye.name().toLowerCase(), "dyes", "Chemical " + tDye.mName + " Dye", null, tDye.getRGBa(), 1, 295, null, null, 0);
            tDye.addFluidDye(tFluid);
            FluidContainerRegistry.registerFluidContainer(new FluidStack(tFluid, 2304), ItemList.SPRAY_CAN_DYES[i].get(1L), ItemList.Spray_Empty.get(1L));
        }
        GT_Mod.gregtechproxy.addFluid("ice", "Crushed Ice", Materials.get("Ice"), 0, 270, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.get("Ice"), 1L), ItemList.Cell_Empty.get(1L), 1000);
        Materials.get("Water").setSolid(Materials.get("Ice").getSolid());


        GT_Mod.gregtechproxy.addFluid("molten.glass", "Molten Glass", Materials.get("Glass"), 4, 1500);
        GT_Mod.gregtechproxy.addFluid("molten.redstone", "Molten Redstone", Materials.get("Redstone"), 4, 500);
        GT_Mod.gregtechproxy.addFluid("molten.blaze", "Molten Blaze", Materials.get("Blaze"), 4, 6400);
        GT_Mod.gregtechproxy.addFluid("molten.concrete", "Wet Concrete", Materials.get("Concrete"), 4, 300);
        for (Materials tMaterial : Materials.values()) {
            if ((tMaterial.getStandardMoltenFluid() == null) && (tMaterial.contains(SubTag.SMELTING_TO_FLUID)) && (!tMaterial.contains(SubTag.NO_SMELTING))) {
                GT_Mod.gregtechproxy.addAutogeneratedMoltenFluid(tMaterial);
                if ((tMaterial.getSmeltingInto() != tMaterial) && (tMaterial.getSmeltingInto().getStandardMoltenFluid() == null)) {
                    GT_Mod.gregtechproxy.addAutogeneratedMoltenFluid(tMaterial.getSmeltingInto());
                }
            }
            if (tMaterial.getElement() != null) {
                GT_Mod.gregtechproxy.addAutogeneratedPlasmaFluid(tMaterial);
            }
        }
        GT_Mod.gregtechproxy.addFluid("potion.awkward", "Awkward Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.thick", "Thick Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 32), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.mundane", "Mundane Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 64), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.damage", "Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8204), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.damage.strong", "Strong Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8236), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.damage.splash", "Splash Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16396), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.damage.strong.splash", "Strong Splash Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16428), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.health", "Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8197), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.health.strong", "Strong Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8229), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.health.splash", "Splash Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16389), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.health.strong.splash", "Strong Splash Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16421), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed", "Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8194), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed.strong", "Strong Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8226), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed.long", "Stretched Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8258), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed.splash", "Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16386), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed.strong.splash", "Strong Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16418), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.speed.long.splash", "Stretched Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16450), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength", "Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8201), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength.strong", "Strong Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8233), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength.long", "Stretched Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8265), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength.splash", "Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16393), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength.strong.splash", "Strong Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16425), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.strength.long.splash", "Stretched Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16457), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen", "Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8193), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen.strong", "Strong Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8225), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen.long", "Stretched Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8257), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen.splash", "Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16385), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen.strong.splash", "Strong Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16417), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.regen.long.splash", "Stretched Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16449), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison", "Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8196), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison.strong", "Strong Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8228), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison.long", "Stretched Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8260), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison.splash", "Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16388), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison.strong.splash", "Strong Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16420), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.poison.long.splash", "Stretched Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16452), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.fireresistance", "Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 8195), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.fireresistance.long", "Stretched Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 8259), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.fireresistance.splash", "Splash Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 16387), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.fireresistance.long.splash", "Stretched Splash Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 16451), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.nightvision", "Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8198), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.nightvision.long", "Stretched Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8262), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.nightvision.splash", "Splash Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16390), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.nightvision.long.splash", "Stretched Splash Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16454), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.weakness", "Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8200), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.weakness.long", "Stretched Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8264), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.weakness.splash", "Splash Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16392), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.weakness.long.splash", "Stretched Splash Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16456), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.slowness", "Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8202), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.slowness.long", "Stretched Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8266), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.slowness.splash", "Splash Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16394), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.slowness.long.splash", "Stretched Splash Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16458), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.waterbreathing", "Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8205), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.long", "Stretched Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8269), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.splash", "Splash Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16397), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.long.splash", "Stretched Splash Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16461), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.invisibility", "Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8206), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.invisibility.long", "Stretched Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8270), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.invisibility.splash", "Splash Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16398), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.invisibility.long.splash", "Stretched Splash Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16462), ItemList.Bottle_Empty.get(1L), 250);

        GT_Mod.gregtechproxy.addFluid("potion.purpledrink", "Purple Drink", null, 1, 275, ItemList.Bottle_Purple_Drink.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.grapejuice", "Grape Juice", null, 1, 295, ItemList.Bottle_Grape_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.wine", "Wine", null, 1, 295, ItemList.Bottle_Wine.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.vinegar", "Vinegar", null, 1, 295, ItemList.Bottle_Vinegar.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.potatojuice", "Potato Juice", null, 1, 295, ItemList.Bottle_Potato_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.vodka", "Vodka", null, 1, 275, ItemList.Bottle_Vodka.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.leninade", "Leninade", null, 1, 275, ItemList.Bottle_Leninade.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.mineralwater", "Mineral Water", null, 1, 275, ItemList.Bottle_Mineral_Water.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.saltywater", "Salty Water", null, 1, 275, ItemList.Bottle_Salty_Water.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.reedwater", "Reed Water", null, 1, 295, ItemList.Bottle_Reed_Water.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.rum", "Rum", null, 1, 295, ItemList.Bottle_Rum.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.piratebrew", "Pirate Brew", null, 1, 295, ItemList.Bottle_Pirate_Brew.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.hopsjuice", "Hops Juice", null, 1, 295, ItemList.Bottle_Hops_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.darkbeer", "Dark Beer", null, 1, 275, ItemList.Bottle_Dark_Beer.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.dragonblood", "Dragon Blood", null, 1, 375, ItemList.Bottle_Dragon_Blood.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.wheatyjuice", "Wheaty Juice", null, 1, 295, ItemList.Bottle_Wheaty_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.scotch", "Scotch", null, 1, 275, ItemList.Bottle_Scotch.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.glenmckenner", "Glen McKenner", null, 1, 275, ItemList.Bottle_Glen_McKenner.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.wheatyhopsjuice", "Wheaty Hops Juice", null, 1, 295, ItemList.Bottle_Wheaty_Hops_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.beer", "Beer", null, 1, 275, ItemList.Bottle_Beer.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.chillysauce", "Chilly Sauce", null, 1, 375, ItemList.Bottle_Chilly_Sauce.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.hotsauce", "Hot Sauce", null, 1, 380, ItemList.Bottle_Hot_Sauce.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.diabolosauce", "Diabolo Sauce", null, 1, 385, ItemList.Bottle_Diabolo_Sauce.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.diablosauce", "Diablo Sauce", null, 1, 390, ItemList.Bottle_Diablo_Sauce.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.diablosauce.strong", "Old Man Snitches glitched Diablo Sauce", null, 1, 999, ItemList.Bottle_Snitches_Glitch_Sauce.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.applejuice", "Apple Juice", null, 1, 295, ItemList.Bottle_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.cider", "Cider", null, 1, 295, ItemList.Bottle_Cider.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.goldenapplejuice", "Golden Apple Juice", null, 1, 295, ItemList.Bottle_Golden_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.goldencider", "Golden Cider", null, 1, 295, ItemList.Bottle_Golden_Cider.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.idunsapplejuice", "Idun's Apple Juice", null, 1, 295, ItemList.Bottle_Iduns_Apple_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.notchesbrew", "Notches Brew", null, 1, 295, ItemList.Bottle_Notches_Brew.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.lemonjuice", "Lemon Juice", null, 1, 295, ItemList.Bottle_Lemon_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.limoncello", "Limoncello", null, 1, 295, ItemList.Bottle_Limoncello.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.lemonade", "Lemonade", null, 1, 275, ItemList.Bottle_Lemonade.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.alcopops", "Alcopops", null, 1, 275, ItemList.Bottle_Alcopops.get(1L), ItemList.Bottle_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.cavejohnsonsgrenadejuice", "Cave Johnsons Grenade Juice", null, 1, 295, ItemList.Bottle_Cave_Johnsons_Grenade_Juice.get(1L), ItemList.Bottle_Empty.get(1L), 250);

        GT_Mod.gregtechproxy.addFluid("potion.darkcoffee", "Dark Coffee", null, 1, 295, ItemList.ThermosCan_Dark_Coffee.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.darkcafeaulait", "Dark Cafe au lait", null, 1, 295, ItemList.ThermosCan_Dark_Cafe_au_lait.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.coffee", "Coffee", null, 1, 295, ItemList.ThermosCan_Coffee.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.cafeaulait", "Cafe au lait", null, 1, 295, ItemList.ThermosCan_Cafe_au_lait.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.laitaucafe", "Lait au cafe", null, 1, 295, ItemList.ThermosCan_Lait_au_cafe.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.darkchocolatemilk", "Bitter Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Dark_Chocolate_Milk.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.chocolatemilk", "Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Chocolate_Milk.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.tea", "Tea", null, 1, 295, ItemList.ThermosCan_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.sweettea", "Sweet Tea", null, 1, 295, ItemList.ThermosCan_Sweet_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);
        GT_Mod.gregtechproxy.addFluid("potion.icetea", "Ice Tea", null, 1, 255, ItemList.ThermosCan_Ice_Tea.get(1L), ItemList.ThermosCan_Empty.get(1L), 250);

        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 750), ItemList.IC2_Spray_WeedEx.get(1L), ItemList.Spray_Empty.get(1L)));


        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Head_Glass_Poison.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Head_Glass_Poison_Long.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Head_Glass_Poison_Strong.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Head_Glass_Slowness.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Head_Glass_Slowness_Long.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Head_Glass_Weakness.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Head_Glass_Weakness_Long.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Head_Glass_Holy_Water.get(1L), ItemList.Arrow_Head_Glass_Emtpy.get(1L)));

        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Wooden_Glass_Poison.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Wooden_Glass_Poison_Long.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Wooden_Glass_Poison_Strong.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Wooden_Glass_Slowness.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Wooden_Glass_Slowness_Long.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Wooden_Glass_Weakness.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Wooden_Glass_Weakness_Long.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Wooden_Glass_Holy_Water.get(1L), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L)));

        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Plastic_Glass_Poison.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Plastic_Glass_Poison_Long.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Plastic_Glass_Poison_Strong.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Plastic_Glass_Slowness.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Plastic_Glass_Slowness_Long.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Plastic_Glass_Weakness.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Plastic_Glass_Weakness_Long.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Plastic_Glass_Holy_Water.get(1L), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L)));
        if (!DEBUG_LEVEL_1) {
            try {
                Class.forName("codechicken.nei.api.API");
                GT_Log.out.println("GT_Mod: Hiding certain Items from NEI.");
                API.hideItem(ItemList.Display_Fluid.getWildcard(1L));
            } catch (Throwable e) {
                if (DEBUG_LEVEL_1) {
                    e.printStackTrace(GT_Log.err);
                }
            }
        }
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.cobblestone, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sand, 1), null, 0, false);
        //GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.stone, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.cobblestone, 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.gravel, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.flint, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.get("Flint"), 1L), 10, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.furnace, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sand, 6), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.lit_furnace, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sand, 6), null, 0, false);

        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("FierySteel"), GT_ModHandler.getModItem(MOD_ID_TF, "item.fieryIngot", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Knightmetal"), GT_ModHandler.getModItem(MOD_ID_TF, "item.knightMetal", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Steeleaf"), GT_ModHandler.getModItem(MOD_ID_TF, "item.steeleafIngot", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("IronWood"), GT_ModHandler.getModItem(MOD_ID_TF, "item.ironwoodIngot", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedAir"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedFire"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 1));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedWater"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 2));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedEarth"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 3));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedOrder"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 4));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("InfusedEntropy"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemShard", 1L, 5));
        GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.get("Mercury"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemNugget", 1L, 5));
        GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.get("Thaumium"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemNugget", 1L, 6));
        GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.get("Thaumium"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1L, 2));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Mercury"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1L, 3));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Amber"), GT_ModHandler.getModItem(MOD_ID_TC, "ItemResource", 1L, 6));
        GT_OreDictUnificator.set(OrePrefixes.gem, Materials.get("Firestone"), GT_ModHandler.getModItem(MOD_ID_RC, "firestone.raw", 1L));

        if (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateIron", true)) {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Iron"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 0));
        } else {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Iron"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 0), false, false);
        }

        if (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateSteel", true)) {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Steel"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 1));
        } else {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Steel"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 1), false, false);
        }

        if (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateTinAlloy", true)) {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("TinAlloy"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 2));
        } else {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("TinAlloy"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 2), false, false);
        }


        if (GregTech_API.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateCopper", true)) {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Copper"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 3));
        } else {
            GT_OreDictUnificator.set(OrePrefixes.plate, Materials.get("Copper"), GT_ModHandler.getModItem(MOD_ID_RC, "part.plate", 1L, 3), false, false);
        }


        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Cocoa"), GT_ModHandler.getModItem(MOD_ID_HaC, "cocoapowderItem", 1L, 0));
        GT_OreDictUnificator.set(OrePrefixes.dust, Materials.get("Coffee"), ItemList.IC2_CoffeePowder.get(1L));
    }
}
