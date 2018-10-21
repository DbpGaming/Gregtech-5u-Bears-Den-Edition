package gregtech.api.materials;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TC_Aspects;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.GT_Color;
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static gregtech.GT_Mod.getConfigdir;
import static gregtech.api.enums.Dyes.dyeBlack;
import static gregtech.api.enums.Dyes.dyeBlue;
import static gregtech.api.enums.Dyes.dyeBrown;
import static gregtech.api.enums.Dyes.dyeCyan;
import static gregtech.api.enums.Dyes.dyeGray;
import static gregtech.api.enums.Dyes.dyeGreen;
import static gregtech.api.enums.Dyes.dyeLightBlue;
import static gregtech.api.enums.Dyes.dyeLightGray;
import static gregtech.api.enums.Dyes.dyeLime;
import static gregtech.api.enums.Dyes.dyeMagenta;
import static gregtech.api.enums.Dyes.dyeOrange;
import static gregtech.api.enums.Dyes.dyePink;
import static gregtech.api.enums.Dyes.dyePurple;
import static gregtech.api.enums.Dyes.dyeRed;
import static gregtech.api.enums.Dyes.dyeWhite;
import static gregtech.api.enums.Dyes.dyeYellow;
import static gregtech.api.enums.GT_Values.EMPTY_STRING;
import static gregtech.api.enums.GT_Values.MATERIAL_UNIT;
import static gregtech.api.enums.GT_Values.MOD_ID_TC;
import static gregtech.api.enums.MaterialFlags.BGEM;
import static gregtech.api.enums.MaterialFlags.BOLT;
import static gregtech.api.enums.MaterialFlags.CELL;
import static gregtech.api.enums.MaterialFlags.CENT;
import static gregtech.api.enums.MaterialFlags.CFLUID;
import static gregtech.api.enums.MaterialFlags.DPLATE;
import static gregtech.api.enums.MaterialFlags.DUST;
import static gregtech.api.enums.MaterialFlags.ELEC;
import static gregtech.api.enums.MaterialFlags.FOIL;
import static gregtech.api.enums.MaterialFlags.FRAME;
import static gregtech.api.enums.MaterialFlags.FWIRE;
import static gregtech.api.enums.MaterialFlags.GEAR;
import static gregtech.api.enums.MaterialFlags.GEM;
import static gregtech.api.enums.MaterialFlags.LIQUID;
import static gregtech.api.enums.MaterialFlags.ORE;
import static gregtech.api.enums.MaterialFlags.PLASMA;
import static gregtech.api.enums.MaterialFlags.PLATE;
import static gregtech.api.enums.MaterialFlags.RING;
import static gregtech.api.enums.MaterialFlags.ROTOR;
import static gregtech.api.enums.MaterialFlags.SCREW;
import static gregtech.api.enums.MaterialFlags.SGEAR;
import static gregtech.api.enums.MaterialFlags.SOLID;
import static gregtech.api.enums.MaterialFlags.STICK;
import static gregtech.api.enums.MaterialFlags.TOOL;
import static gregtech.api.enums.SubTag.HAS_COLOR;
import static gregtech.api.enums.SubTag.SMELTING_TO_FLUID;
import static gregtech.api.enums.SubTag.TRANSPARENT;
import static gregtech.api.enums.TextureSet.SET_DIAMOND;
import static gregtech.api.enums.TextureSet.SET_DULL;
import static gregtech.api.enums.TextureSet.SET_EMERALD;
import static gregtech.api.enums.TextureSet.SET_FIERY;
import static gregtech.api.enums.TextureSet.SET_FINE;
import static gregtech.api.enums.TextureSet.SET_FLINT;
import static gregtech.api.enums.TextureSet.SET_FLUID;
import static gregtech.api.enums.TextureSet.SET_GEM_HORIZONTAL;
import static gregtech.api.enums.TextureSet.SET_GEM_VERTICAL;
import static gregtech.api.enums.TextureSet.SET_GLASS;
import static gregtech.api.enums.TextureSet.SET_LAPIS;
import static gregtech.api.enums.TextureSet.SET_LEAF;
import static gregtech.api.enums.TextureSet.SET_LIGNITE;
import static gregtech.api.enums.TextureSet.SET_MAGNETIC;
import static gregtech.api.enums.TextureSet.SET_METALLIC;
import static gregtech.api.enums.TextureSet.SET_NETHERSTAR;
import static gregtech.api.enums.TextureSet.SET_NONE;
import static gregtech.api.enums.TextureSet.SET_OPAL;
import static gregtech.api.enums.TextureSet.SET_PAPER;
import static gregtech.api.enums.TextureSet.SET_POWDER;
import static gregtech.api.enums.TextureSet.SET_QUARTZ;
import static gregtech.api.enums.TextureSet.SET_ROUGH;
import static gregtech.api.enums.TextureSet.SET_RUBY;
import static gregtech.api.enums.TextureSet.SET_SAND;
import static gregtech.api.enums.TextureSet.SET_SHARDS;
import static gregtech.api.enums.TextureSet.SET_SHINY;
import static gregtech.api.enums.TextureSet.SET_WOOD;

@SuppressWarnings({"squid:S1845", // Some methods and field names differ only by capitalization
})

public class Materials implements ISubTagContainer {
    private static final Configuration materialsConfig = new Configuration(new File(new File(getConfigdir(), "GregTech"), "Materials.cfg"), (String.format("v%d", GT_Mod.VERSION)), true);
    private static final ConcurrentHashMap<String, Materials> MATERIALS_HASH_MAP = new ConcurrentHashMap<>();
    public static volatile int VERSION = 508;
    /**
     * This is the Default Material returned in case no Material has been found or a NullPointer has been inserted at a location where it shouldn't happen.
     */
    public static Materials _NULL = new MaterialsBuilder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(0x00FFFFFF).locName("NULL").fuelType(0).fuelPow(0).meltAt(0).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 1)).build();

    /*
     * Direct Elements
     */
    private static void initDirectElements() {
        MATERIALS_HASH_MAP.put("Aluminium", new MaterialsBuilder().subID(19).name("Aluminium").textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(128).toolQual(2).type(DUST | SOLID | ORE | PLATE | STICK | RING | BOLT | FOIL | SCREW | GEAR | SGEAR | FWIRE | ROTOR | DPLATE | LIQUID | FRAME).solidColor(0x0080C8F0).locName("Aluminium").fuelType(0).fuelPow(0).meltAt(933).blastAt(1700).needBlast(true).oreVal(3).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Al).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VOLATUS, 1)).build());
        MATERIALS_HASH_MAP.put("Americium", new MaterialsBuilder().subID(103).name("Americium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | SOLID | LIQUID).solidColor(0x00C8C8C8).locName("Americium").fuelType(0).fuelPow(0).meltAt(1449).blastAt(0).needBlast(false).oreVal(3).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Am).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Antimony", new MaterialsBuilder().subID(58).name("Antimony").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00DCDCF0).locName("Antimony").fuelType(0).fuelPow(0).meltAt(903).blastAt(0).needBlast(false).oreVal(2).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Sb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.AQUA, 1)).build());
        MATERIALS_HASH_MAP.put("Argon", new MaterialsBuilder().subID(24).name("Argon").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(CELL | CFLUID).solidColor(0x00FF00F00).locName("Argon").fuelType(0).fuelPow(0).meltAt(83).blastAt(0).needBlast(false).subTags(TRANSPARENT).oreVal(5).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.Ar).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build());
        MATERIALS_HASH_MAP.put("Arsenic", new MaterialsBuilder().subID(39).name("Arsenic").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00FFFFFF).locName("Arsenic").fuelType(0).fuelPow(0).meltAt(1090).blastAt(0).needBlast(false).oreVal(3).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.As).aspects(new TC_AspectStack(TC_Aspects.VENENUM, 3)).build());
        MATERIALS_HASH_MAP.put("Barium", new MaterialsBuilder().subID(63).name("Barium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00777777).locName("Barium").fuelType(0).fuelPow(0).meltAt(1000).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ba).aspects(new TC_AspectStack(TC_Aspects.VINCULUM, 3)).build());
        MATERIALS_HASH_MAP.put("Beryllium", new MaterialsBuilder().subID(8).name("Beryllium").textureSet(SET_METALLIC).toolSpeed(14.0F).toolDura(64).toolQual(2).type(DUST | SOLID | ORE | PLATE | DPLATE | LIQUID).solidColor(0x0064B464).locName("Beryllium").fuelType(0).fuelPow(0).meltAt(1560).blastAt(0).needBlast(false).oreVal(6).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.Be).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Bismuth", new MaterialsBuilder().subID(90).name("Bismuth").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | SOLID | ORE | LIQUID).solidColor(0x0064A0A0).locName("Bismuth").fuelType(0).fuelPow(0).meltAt(544).blastAt(0).needBlast(false).oreVal(2).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.Bi).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Boron", new MaterialsBuilder().subID(9).name("Boron").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00AAAAAA).locName("Boron").fuelType(0).fuelPow(0).meltAt(2349).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.B).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Caesium", new MaterialsBuilder().subID(62).name("Caesium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(0x00FFC83C).locName("Caesium").fuelType(0).fuelPow(0).meltAt(301).blastAt(0).needBlast(false).oreVal(4).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Cs).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Calcium", new MaterialsBuilder().subID(26).name("Calcium").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(0x00FFF5F5).locName("Calcium").fuelType(0).fuelPow(0).meltAt(1115).blastAt(0).needBlast(false).oreVal(4).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Ca).aspects(new TC_AspectStack(TC_Aspects.SANO, 1), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build());

        //TODO: set density of liquid for correct materials
        MATERIALS_HASH_MAP.put("Carbon", new MaterialsBuilder().subID(10).name("Carbon").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | PLASMA | TOOL | GEAR | SGEAR).solidColor(20, 20, 20, 0).locName("Carbon").fuelType(0).fuelPow(0).meltAt(3800).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.C).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("Cadmium", new MaterialsBuilder().subID(55).name("Cadmium").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(50, 50, 60, 0).locName("Cadmium").fuelType(0).fuelPow(0).meltAt(594).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Cd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)).build());
        MATERIALS_HASH_MAP.put("Cerium", new MaterialsBuilder().subID(65).name("Cerium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Cerium").fuelType(0).fuelPow(0).meltAt(1068).blastAt(1068).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ce).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Chlorine", new MaterialsBuilder().subID(23).name("Chlorine").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 255, 0).locName("Chlorine").fuelType(0).fuelPow(0).meltAt(171).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.Cl).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.PANNUS, 1)).build());
        MATERIALS_HASH_MAP.put("Chrome", new MaterialsBuilder().subID(30).name("Chrome").textureSet(SET_SHINY).toolSpeed(11.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 230, 230, 0).locName("Chrome").fuelType(0).fuelPow(0).meltAt(2180).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Cr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build());
        MATERIALS_HASH_MAP.put("Cobalt", new MaterialsBuilder().subID(33).name("Cobalt").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(80, 80, 250, 0).locName("Cobalt").fuelType(0).fuelPow(0).meltAt(1768).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Co).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Copper", new MaterialsBuilder().subID(35).name("Copper").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | GEAR | SGEAR).solidColor(255, 100, 0, 0).locName("Copper").fuelType(0).fuelPow(0).meltAt(1357).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.Cu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PERMUTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("Deuterium", new MaterialsBuilder().subID(2).name("Deuterium").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Deuterium").fuelType(0).fuelPow(0).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.D).aspects(new TC_AspectStack(TC_Aspects.AQUA, 3)).build());
        MATERIALS_HASH_MAP.put("Dysprosium", new MaterialsBuilder().subID(-1).name("Dysprosium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Dysprosium").fuelType(0).fuelPow(0).meltAt(1680).blastAt(1680).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Dy).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build());
        MATERIALS_HASH_MAP.put("Empty", new MaterialsBuilder().subID(0).name("Empty").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 255).locName("Empty").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 2)).build());
        MATERIALS_HASH_MAP.put("Erbium", new MaterialsBuilder().subID(-1).name("Erbium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Erbium").fuelType(0).fuelPow(0).meltAt(1802).blastAt(1802).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Er).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Europium", new MaterialsBuilder().subID(70).name("Europium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Europium").fuelType(0).fuelPow(0).meltAt(1099).blastAt(1099).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Eu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Fluorine", new MaterialsBuilder().subID(14).name("Fluorine").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 255, 127).locName("Fluorine").fuelType(0).fuelPow(0).meltAt(53).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.F).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 2)).build());
        MATERIALS_HASH_MAP.put("Gadolinium", new MaterialsBuilder().subID(-1).name("Gadolinium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Gadolinium").fuelType(0).fuelPow(0).meltAt(1585).blastAt(1585).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Gd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Gallium", new MaterialsBuilder().subID(37).name("Gallium").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(220, 220, 255, 0).locName("Gallium").fuelType(0).fuelPow(0).meltAt(302).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Ga).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Gold", new MaterialsBuilder().subID(86).name("Gold").textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 255, 30, 0).locName("Gold").fuelType(0).fuelPow(0).meltAt(1337).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.Au).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 2)).build());
        MATERIALS_HASH_MAP.put("Holmium", new MaterialsBuilder().subID(-1).name("Holmium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Holmium").fuelType(0).fuelPow(0).meltAt(1734).blastAt(1734).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ho).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Hydrogen", new MaterialsBuilder().subID(1).name("Hydrogen").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 0, 255, 240).locName("Hydrogen").fuelType(1).fuelPow(15).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.H).aspects(new TC_AspectStack(TC_Aspects.AQUA, 1)).build());
        MATERIALS_HASH_MAP.put("Helium", new MaterialsBuilder().subID(4).name("Helium").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Helium").fuelType(0).fuelPow(0).meltAt(1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.He).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build());
        MATERIALS_HASH_MAP.put("Helium_3", new MaterialsBuilder().subID(5).name("Helium_3").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Helium-3").fuelType(0).fuelPow(0).meltAt(1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.He_3).aspects(new TC_AspectStack(TC_Aspects.AER, 3)).build());
        MATERIALS_HASH_MAP.put("Indium", new MaterialsBuilder().subID(56).name("Indium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(64, 0, 128, 0).locName("Indium").fuelType(0).fuelPow(0).meltAt(429).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.In).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Iridium", new MaterialsBuilder().subID(84).name("Iridium").textureSet(SET_DULL).toolSpeed(6.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(240, 240, 245, 0).locName("Iridium").fuelType(0).fuelPow(0).meltAt(2719).blastAt(2719).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Ir).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build());
        MATERIALS_HASH_MAP.put("Iron", new MaterialsBuilder().subID(32).name("Iron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Fe).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build());
        MATERIALS_HASH_MAP.put("Lanthanum", new MaterialsBuilder().subID(64).name("Lanthanum").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(216, 224, 255, 0).locName("Lanthanum").fuelType(0).fuelPow(0).meltAt(1193).blastAt(1193).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.La).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Lead", new MaterialsBuilder().subID(89).name("Lead").textureSet(SET_DULL).toolSpeed(8.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(140, 100, 140, 0).locName("Lead").fuelType(0).fuelPow(0).meltAt(600).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Pb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)).build());
        MATERIALS_HASH_MAP.put("Lithium", new MaterialsBuilder().subID(6).name("Lithium").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(225, 220, 255, 0).locName("Lithium").fuelType(0).fuelPow(0).meltAt(454).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Li).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build());
        MATERIALS_HASH_MAP.put("Lutetium", new MaterialsBuilder().subID(78).name("Lutetium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Lutetium").fuelType(0).fuelPow(0).meltAt(1925).blastAt(1925).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Lu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Magic", new MaterialsBuilder().subID(-128).name("Magic").textureSet(SET_SHINY).toolSpeed(8.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | GEM | BGEM | ORE | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(100, 0, 200, 0).locName("Magic").fuelType(5).fuelPow(32).meltAt(5000).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Ma).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 4)).build());
        MATERIALS_HASH_MAP.put("Magnesium", new MaterialsBuilder().subID(18).name("Magnesium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 200, 200, 0).locName("Magnesium").fuelType(0).fuelPow(0).meltAt(923).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Mg).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)).build());
        MATERIALS_HASH_MAP.put("Manganese", new MaterialsBuilder().subID(31).name("Manganese").textureSet(SET_DULL).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(250, 250, 250, 0).locName("Manganese").fuelType(0).fuelPow(0).meltAt(1519).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Mn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build());
        MATERIALS_HASH_MAP.put("Mercury", new MaterialsBuilder().subID(87).name("Mercury").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(0).type(LIQUID | CELL | PLASMA).solidColor(255, 220, 220, 0).locName("Mercury").fuelType(5).fuelPow(32).meltAt(234).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Hg).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)).build());
        MATERIALS_HASH_MAP.put("Molybdenum", new MaterialsBuilder().subID(48).name("Molybdenum").textureSet(SET_SHINY).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(180, 180, 220, 0).locName("Molybdenum").fuelType(0).fuelPow(0).meltAt(2896).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Mo).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Neodymium", new MaterialsBuilder().subID(67).name("Neodymium").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Neodymium").fuelType(0).fuelPow(0).meltAt(1297).blastAt(1297).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Nd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 2)).build());
        MATERIALS_HASH_MAP.put("Neutronium", new MaterialsBuilder().subID(129).name("Neutronium").textureSet(SET_DULL).toolSpeed(24.0F).toolDura(655360).toolQual(6).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(250, 250, 250, 0).locName("Neutronium").fuelType(0).fuelPow(0).meltAt(10000).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Nt).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.ALIENIS, 2)).build());
        MATERIALS_HASH_MAP.put("Nickel", new MaterialsBuilder().subID(34).name("Nickel").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(200, 200, 250, 0).locName("Nickel").fuelType(0).fuelPow(0).meltAt(1728).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Ni).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("Niobium", new MaterialsBuilder().subID(47).name("Niobium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(190, 180, 200, 0).locName("Niobium").fuelType(0).fuelPow(0).meltAt(2750).blastAt(2750).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Nb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Nitrogen", new MaterialsBuilder().subID(12).name("Nitrogen").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 150, 200, 240).locName("Nitrogen").fuelType(0).fuelPow(0).meltAt(63).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.N).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build());
        MATERIALS_HASH_MAP.put("Osmium", new MaterialsBuilder().subID(83).name("Osmium").textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(50, 50, 255, 0).locName("Osmium").fuelType(0).fuelPow(0).meltAt(3306).blastAt(3306).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Os).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build());
        MATERIALS_HASH_MAP.put("Oxygen", new MaterialsBuilder().subID(13).name("Oxygen").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 100, 200, 240).locName("Oxygen").fuelType(0).fuelPow(0).meltAt(54).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.O).aspects(new TC_AspectStack(TC_Aspects.AER, 1)).build());
        MATERIALS_HASH_MAP.put("Palladium", new MaterialsBuilder().subID(52).name("Palladium").textureSet(SET_SHINY).toolSpeed(8.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Palladium").fuelType(0).fuelPow(0).meltAt(1828).blastAt(1828).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Pd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build());
        MATERIALS_HASH_MAP.put("Phosphor", new MaterialsBuilder().subID(21).name("Phosphor").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(255, 255, 0, 0).locName("Phosphor").fuelType(0).fuelPow(0).meltAt(317).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.P).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 2), new TC_AspectStack(TC_Aspects.POTENTIA, 1)).build());
        MATERIALS_HASH_MAP.put("Platinum", new MaterialsBuilder().subID(85).name("Platinum").textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 255, 200, 0).locName("Platinum").fuelType(0).fuelPow(0).meltAt(2041).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.Pt).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build());
        MATERIALS_HASH_MAP.put("Plutonium", new MaterialsBuilder().subID(100).name("Plutonium").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(240, 50, 50, 0).locName("Plutonium 244").fuelType(0).fuelPow(0).meltAt(912).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).elem(Element.Pu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)).build());
        MATERIALS_HASH_MAP.put("Plutonium241", new MaterialsBuilder().subID(101).name("Plutonium241").textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(250, 70, 70, 0).locName("Plutonium 241").fuelType(0).fuelPow(0).meltAt(912).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).elem(Element.Pu_241).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 3)).build());
        MATERIALS_HASH_MAP.put("Potassium", new MaterialsBuilder().subID(25).name("Potassium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | PLASMA).solidColor(250, 250, 250, 0).locName("Potassium").fuelType(0).fuelPow(0).meltAt(336).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.K).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1)).build());
        MATERIALS_HASH_MAP.put("Praseodymium", new MaterialsBuilder().subID(-1).name("Praseodymium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Praseodymium").fuelType(0).fuelPow(0).meltAt(1208).blastAt(1208).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Pr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Promethium", new MaterialsBuilder().subID(-1).name("Promethium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Promethium").fuelType(0).fuelPow(0).meltAt(1315).blastAt(1315).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Pm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Radon", new MaterialsBuilder().subID(93).name("Radon").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 0, 255, 240).locName("Radon").fuelType(0).fuelPow(0).meltAt(202).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Rn).aspects(new TC_AspectStack(TC_Aspects.AER, 1), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Rubidium", new MaterialsBuilder().subID(43).name("Rubidium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(240, 30, 30, 0).locName("Rubidium").fuelType(0).fuelPow(0).meltAt(312).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).elem(Element.Rb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build());
        MATERIALS_HASH_MAP.put("Samarium", new MaterialsBuilder().subID(-1).name("Samarium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Samarium").fuelType(0).fuelPow(0).meltAt(1345).blastAt(1345).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Sm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Scandium", new MaterialsBuilder().subID(27).name("Scandium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Scandium").fuelType(0).fuelPow(0).meltAt(1814).blastAt(1814).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.Sc).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Silicon", new MaterialsBuilder().subID(20).name("Silicon").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(60, 60, 80, 0).locName("Silicon").fuelType(0).fuelPow(0).meltAt(1687).blastAt(1687).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.Si).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TENEBRAE, 1)).build());
        MATERIALS_HASH_MAP.put("Silver", new MaterialsBuilder().subID(54).name("Silver").textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(220, 220, 255, 0).locName("Silver").fuelType(0).fuelPow(0).meltAt(1234).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Ag).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Sodium", new MaterialsBuilder().subID(17).name("Sodium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLASMA).solidColor(0, 0, 150, 0).locName("Sodium").fuelType(0).fuelPow(0).meltAt(370).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Na).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.LUX, 1)).build());
        MATERIALS_HASH_MAP.put("Strontium", new MaterialsBuilder().subID(44).name("Strontium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(200, 200, 200, 0).locName("Strontium").fuelType(0).fuelPow(0).meltAt(1050).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Sr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.STRONTIO, 1)).build());
        MATERIALS_HASH_MAP.put("Sulfur", new MaterialsBuilder().subID(22).name("Sulfur").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(200, 200, 0, 0).locName("Sulfur").fuelType(0).fuelPow(0).meltAt(388).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.S).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("Tantalum", new MaterialsBuilder().subID(80).name("Tantalum").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Tantalum").fuelType(0).fuelPow(0).meltAt(3290).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ta).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VINCULUM, 1)).build());
        MATERIALS_HASH_MAP.put("Tellurium", new MaterialsBuilder().subID(-1).name("Tellurium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Tellurium").fuelType(0).fuelPow(0).meltAt(722).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Te).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Terbium", new MaterialsBuilder().subID(-1).name("Terbium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Terbium").fuelType(0).fuelPow(0).meltAt(1629).blastAt(1629).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Tb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Thorium", new MaterialsBuilder().subID(96).name("Thorium").textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(0, 30, 0, 0).locName("Thorium").fuelType(0).fuelPow(0).meltAt(2115).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.Th).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Thulium", new MaterialsBuilder().subID(-1).name("Thulium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Thulium").fuelType(0).fuelPow(0).meltAt(1818).blastAt(1818).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Tm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Tin", new MaterialsBuilder().subID(57).name("Tin").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | GEAR | SGEAR).solidColor(220, 220, 220, 0).locName("Tin").fuelType(0).fuelPow(0).meltAt(505).blastAt(505).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Sn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build());
        MATERIALS_HASH_MAP.put("Titanium", new MaterialsBuilder().subID(28).name("Titanium").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(1600).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(220, 160, 240, 0).locName("Titanium").fuelType(0).fuelPow(0).meltAt(1941).blastAt(1940).needBlast(true).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Ti).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build());
        MATERIALS_HASH_MAP.put("Tritium", new MaterialsBuilder().subID(3).name("Tritium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 0, 0, 240).locName("Tritium").fuelType(0).fuelPow(0).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).elem(Element.T).aspects(new TC_AspectStack(TC_Aspects.AQUA, 4)).build());
        MATERIALS_HASH_MAP.put("Tungsten", new MaterialsBuilder().subID(81).name("Tungsten").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(50, 50, 50, 0).locName("Tungsten").fuelType(0).fuelPow(0).meltAt(3695).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.W).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build());
        MATERIALS_HASH_MAP.put("Uranium", new MaterialsBuilder().subID(98).name("Uranium").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(50, 240, 50, 0).locName("Uranium 238").fuelType(0).fuelPow(0).meltAt(1405).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.U).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Uranium235", new MaterialsBuilder().subID(97).name("Uranium235").textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(70, 250, 70, 0).locName("Uranium 235").fuelType(0).fuelPow(0).meltAt(1405).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.U_235).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)).build());
        MATERIALS_HASH_MAP.put("Vanadium", new MaterialsBuilder().subID(29).name("Vanadium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(50, 50, 50, 0).locName("Vanadium").fuelType(0).fuelPow(0).meltAt(2183).blastAt(2183).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.V).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Ytterbium", new MaterialsBuilder().subID(-1).name("Ytterbium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Ytterbium").fuelType(0).fuelPow(0).meltAt(1097).blastAt(1097).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Yb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Yttrium", new MaterialsBuilder().subID(45).name("Yttrium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(220, 250, 220, 0).locName("Yttrium").fuelType(0).fuelPow(0).meltAt(1799).blastAt(1799).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Y).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build());
        MATERIALS_HASH_MAP.put("Zinc", new MaterialsBuilder().subID(36).name("Zinc").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(250, 240, 240, 0).locName("Zinc").fuelType(0).fuelPow(0).meltAt(692).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Zn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)).build());
    }

    /*
     * The "Random Material" ones.
     */
    //TODO: bring back unifiable param!
    private static void initRandomMaterialOnes() {
        MATERIALS_HASH_MAP.put("Organic", new MaterialsBuilder().subID(-1).name("Organic").textureSet(SET_LEAF).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build());
        MATERIALS_HASH_MAP.put("AnyCopper", new MaterialsBuilder().subID(-1).name("AnyCopper").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build());
        MATERIALS_HASH_MAP.put("AnyBronze", new MaterialsBuilder().subID(-1).name("AnyBronze").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build());
        MATERIALS_HASH_MAP.put("AnyIron", new MaterialsBuilder().subID(-1).name("AnyIron").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build());
        MATERIALS_HASH_MAP.put("Crystal", new MaterialsBuilder().subID(-1).name("Crystal").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build());
        MATERIALS_HASH_MAP.put("Quartz", new MaterialsBuilder().subID(-1).name("Quartz").textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build());
        MATERIALS_HASH_MAP.put("Metal", new MaterialsBuilder().subID(-1).name("Metal").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build());
        MATERIALS_HASH_MAP.put("Unknown", new MaterialsBuilder().subID(-1).name("Unknown").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build());
        MATERIALS_HASH_MAP.put("Cobblestone", new MaterialsBuilder().subID(-1).name("Cobblestone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build());
        MATERIALS_HASH_MAP.put("Brick", new MaterialsBuilder().subID(-1).name("Brick").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build());
        MATERIALS_HASH_MAP.put("BrickNether", new MaterialsBuilder().subID(-1).name("BrickNether").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build());
    }

    /*
     * The "I don't care" Section, everything I don't want to do anything with right now, is right here. Just to make the Material Finder shut up about them.
     * But I do see potential uses in some of these Materials.
     */
    private static void initIdontCareSection() {
        MATERIALS_HASH_MAP.put("TarPitch", new MaterialsBuilder().subID(-1).name("TarPitch").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Tar Pitch").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Serpentine", new MaterialsBuilder().subID(-1).name("Serpentine").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Serpentine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Flux", new MaterialsBuilder().subID(-1).name("Flux").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Flux").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("RedstoneAlloy", new MaterialsBuilder().subID(-1).name("RedstoneAlloy").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Redstone Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("OsmiumTetroxide", new MaterialsBuilder().subID(-1).name("OsmiumTetroxide").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Osmium Tetroxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("NitricAcid", new MaterialsBuilder().subID(-1).name("NitricAcid").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Nitric Acid").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("RubberTreeSap", new MaterialsBuilder().subID(-1).name("RubberTreeSap").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Rubber Tree Sap").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("AquaRegia", new MaterialsBuilder().subID(-1).name("AquaRegia").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Aqua Regia").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("SolutionBlueVitriol", new MaterialsBuilder().subID(-1).name("SolutionBlueVitriol").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Blue Vitriol Solution").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("SolutionNickelSulfate", new MaterialsBuilder().subID(-1).name("SolutionNickelSulfate").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Nickel Sulfate Solution").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Signalum", new MaterialsBuilder().subID(-1).name("Signalum").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Signalum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Lumium", new MaterialsBuilder().subID(-1).name("Lumium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Lumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("PhasedIron", new MaterialsBuilder().subID(-1).name("PhasedIron").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Phased Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("PhasedGold", new MaterialsBuilder().subID(-1).name("PhasedGold").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Phased Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Soularium", new MaterialsBuilder().subID(-1).name("Soularium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Soularium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Endium", new MaterialsBuilder().subID(770).name("Endium").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(165, 220, 250, 0).locName("Endium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Prismarine", new MaterialsBuilder().subID(-1).name("Prismarine").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Prismarine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("GraveyardDirt", new MaterialsBuilder().subID(-1).name("GraveyardDirt").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Graveyard Dirt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("DarkSteel", new MaterialsBuilder().subID(364).name("DarkSteel").textureSet(SET_DULL).toolSpeed(8.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(80, 70, 80, 0).locName("Dark Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build());
        MATERIALS_HASH_MAP.put("Terrasteel", new MaterialsBuilder().subID(-1).name("Terrasteel").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Terrasteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("ConductiveIron", new MaterialsBuilder().subID(-1).name("ConductiveIron").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Conductive Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("ElectricalSteel", new MaterialsBuilder().subID(-1).name("ElectricalSteel").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Electrical Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("EnergeticAlloy", new MaterialsBuilder().subID(-1).name("EnergeticAlloy").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Energetic Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("VibrantAlloy", new MaterialsBuilder().subID(-1).name("VibrantAlloy").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Vibrant Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("PulsatingIron", new MaterialsBuilder().subID(-1).name("PulsatingIron").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Pulsating Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Graphene", new MaterialsBuilder().subID(819).name("Graphene").textureSet(SET_DULL).toolSpeed(6.0F).toolDura(32).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Graphene").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Bedrockium", new MaterialsBuilder().subID(-1).name("Bedrockium").textureSet(SET_FINE).toolSpeed(8.0F).toolDura(8196).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(39, 39, 39, 0).locName("Bedrockium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(get("Carbon"), 63), new MaterialStack(get("Carbon"), 56)).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 8), new TC_AspectStack(TC_Aspects.TUTAMEN, 3)).build());
        MATERIALS_HASH_MAP.put("Fluix", new MaterialsBuilder().subID(-1).name("Fluix").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Fluix").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Manasteel", new MaterialsBuilder().subID(-1).name("Manasteel").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Manasteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Tennantite", new MaterialsBuilder().subID(-1).name("Tennantite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Tennantite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("DarkThaumium", new MaterialsBuilder().subID(-1).name("DarkThaumium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Dark Thaumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Alfium", new MaterialsBuilder().subID(-1).name("Alfium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Alfium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Ryu", new MaterialsBuilder().subID(-1).name("Ryu").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ryu").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Mutation", new MaterialsBuilder().subID(-1).name("Mutation").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Mutation").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Aquamarine", new MaterialsBuilder().subID(-1).name("Aquamarine").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Aquamarine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Ender", new MaterialsBuilder().subID(-1).name("Ender").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ender").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("ElvenElementium", new MaterialsBuilder().subID(-1).name("ElvenElementium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Elven Elementium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("EnrichedCopper", new MaterialsBuilder().subID(-1).name("EnrichedCopper").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Enriched Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("DiamondCopper", new MaterialsBuilder().subID(-1).name("DiamondCopper").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Diamond Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("SodiumPeroxide", new MaterialsBuilder().subID(-1).name("SodiumPeroxide").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Sodium Peroxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("IridiumSodiumOxide", new MaterialsBuilder().subID(-1).name("IridiumSodiumOxide").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Iridium Sodium Oxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        //MATERIALS_HASH_MAP.put("PlatinumGroupSludge", new Materials.Builder().subID(-1).name("PlatinumGroupSludge").textureSet(SET_POWDER ).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST ,0, 30,0,0,	"Platinum Group Sludge" ,0,0, -1,0, false, false,3,1,1,.dye(Dyes._NULL .build());
        MATERIALS_HASH_MAP.put("Fairy", new MaterialsBuilder().subID(-1).name("Fairy").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Fairy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Ludicrite", new MaterialsBuilder().subID(-1).name("Ludicrite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Ludicrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Pokefennium", new MaterialsBuilder().subID(-1).name("Pokefennium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Pokefennium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Draconium", new MaterialsBuilder().subID(-1).name("Draconium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Draconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("DraconiumAwakened", new MaterialsBuilder().subID(-1).name("DraconiumAwakened").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Awakened Draconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("InfusedTeslatite", new MaterialsBuilder().subID(-1).name("InfusedTeslatite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(100, 180, 255, 0).locName("Infused Teslatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
    }

    /*
     * Unknown Material Components. Dead End Section.
     */
    private static void initUnknownMaterialComponents() {
        MATERIALS_HASH_MAP.put("Adamantium", new MaterialsBuilder().subID(319).name("Adamantium").textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 255, 0).locName("Adamantium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build());
        MATERIALS_HASH_MAP.put("Adamite", new MaterialsBuilder().subID(-1).name("Adamite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Adamite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build());
        MATERIALS_HASH_MAP.put("Adluorite", new MaterialsBuilder().subID(-1).name("Adluorite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Adluorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Agate", new MaterialsBuilder().subID(-1).name("Agate").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Agate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Alduorite", new MaterialsBuilder().subID(485).name("Alduorite").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | LIQUID).solidColor(159, 180, 180, 0).locName("Alduorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Amber", new MaterialsBuilder().subID(514).name("Amber").textureSet(SET_RUBY).toolSpeed(4.0F).toolDura(128).toolQual(2).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 128, 0, 127).locName("Amber").fuelType(5).fuelPow(3).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.VINCULUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build());
        MATERIALS_HASH_MAP.put("Ammonium", new MaterialsBuilder().subID(-1).name("Ammonium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ammonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Amordrine", new MaterialsBuilder().subID(-1).name("Amordrine").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 255, 255, 0).locName("Amordrine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Andesite", new MaterialsBuilder().subID(-1).name("Andesite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Andesite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Angmallen", new MaterialsBuilder().subID(958).name("Angmallen").textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(128).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(215, 225, 138, 0).locName("Angmallen").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Ardite", new MaterialsBuilder().subID(-1).name("Ardite").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 0, 0, 0).locName("Ardite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Aredrite", new MaterialsBuilder().subID(-1).name("Aredrite").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 0, 0, 0).locName("Aredrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Atlarus", new MaterialsBuilder().subID(965).name("Atlarus").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Atlarus").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Bitumen", new MaterialsBuilder().subID(-1).name("Bitumen").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Bitumen").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Black", new MaterialsBuilder().subID(-1).name("Black").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(0, 0, 0, 0).locName("Black").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Blizz", new MaterialsBuilder().subID(851).name("Blizz").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(220, 233, 255, 0).locName("Blizz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Blueschist", new MaterialsBuilder().subID(-1).name("Blueschist").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Blue Schist").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).build());
        MATERIALS_HASH_MAP.put("Bluestone", new MaterialsBuilder().subID(813).name("Bluestone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Bluestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build());
        MATERIALS_HASH_MAP.put("Bloodstone", new MaterialsBuilder().subID(-1).name("Bloodstone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Bloodstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Blutonium", new MaterialsBuilder().subID(-1).name("Blutonium").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(0, 0, 255, 0).locName("Blutonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build());
        MATERIALS_HASH_MAP.put("Carmot", new MaterialsBuilder().subID(962).name("Carmot").textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(128).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(217, 205, 140, 0).locName("Carmot").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Celenegil", new MaterialsBuilder().subID(964).name("Celenegil").textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(4096).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(148, 204, 72, 0).locName("Celenegil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("CertusQuartz", new MaterialsBuilder().subID(516).name("CertusQuartz").textureSet(SET_QUARTZ).toolSpeed(5.0F).toolDura(32).toolQual(1).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(210, 210, 230, 0).locName("Certus Quartz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build());
        MATERIALS_HASH_MAP.put("Ceruclase", new MaterialsBuilder().subID(952).name("Ceruclase").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(140, 189, 208, 0).locName("Ceruclase").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Citrine", new MaterialsBuilder().subID(-1).name("Citrine").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Citrine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("CobaltHexahydrate", new MaterialsBuilder().subID(-1).name("CobaltHexahydrate").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(80, 80, 250, 0).locName("Cobalt Hexahydrate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build());
        MATERIALS_HASH_MAP.put("ConstructionFoam", new MaterialsBuilder().subID(854).name("ConstructionFoam").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(128, 128, 128, 0).locName("Construction Foam").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build());
        MATERIALS_HASH_MAP.put("Chert", new MaterialsBuilder().subID(-1).name("Chert").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chert").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Chimerite", new MaterialsBuilder().subID(-1).name("Chimerite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chimerite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Coral", new MaterialsBuilder().subID(-1).name("Coral").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 128, 255, 0).locName("Coral").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("CrudeOil", new MaterialsBuilder().subID(858).name("CrudeOil").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(10, 10, 10, 0).locName("Crude Oil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Chrysocolla", new MaterialsBuilder().subID(-1).name("Chrysocolla").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chrysocolla").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("CrystalFlux", new MaterialsBuilder().subID(-1).name("CrystalFlux").textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | GEM | BGEM).solidColor(100, 50, 100, 0).locName("Flux Crystal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Cyanite", new MaterialsBuilder().subID(-1).name("Cyanite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Cyanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).build());
        MATERIALS_HASH_MAP.put("Dacite", new MaterialsBuilder().subID(-1).name("Dacite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Dacite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build());
        MATERIALS_HASH_MAP.put("DarkIron", new MaterialsBuilder().subID(342).name("DarkIron").textureSet(SET_DULL).toolSpeed(7.0F).toolDura(384).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(55, 40, 60, 0).locName("Dark Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build());
        MATERIALS_HASH_MAP.put("DarkStone", new MaterialsBuilder().subID(-1).name("DarkStone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Dark Stone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Demonite", new MaterialsBuilder().subID(-1).name("Demonite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Demonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Desh", new MaterialsBuilder().subID(884).name("Desh").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(1280).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(40, 40, 40, 0).locName("Desh").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Desichalkos", new MaterialsBuilder().subID(-1).name("Desichalkos").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(1280).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 255, 255, 0).locName("Desichalkos").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Dilithium", new MaterialsBuilder().subID(515).name("Dilithium").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(255, 250, 250, 127).locName("Dilithium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("Draconic", new MaterialsBuilder().subID(-1).name("Draconic").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Draconic").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Drulloy", new MaterialsBuilder().subID(-1).name("Drulloy").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(255, 255, 255, 0).locName("Drulloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Duranium", new MaterialsBuilder().subID(328).name("Duranium").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Duranium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build());
        MATERIALS_HASH_MAP.put("Eclogite", new MaterialsBuilder().subID(-1).name("Eclogite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Eclogite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("ElectrumFlux", new MaterialsBuilder().subID(320).name("ElectrumFlux").textureSet(SET_SHINY).toolSpeed(16.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 120, 0).locName("Fluxed Electrum").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Emery", new MaterialsBuilder().subID(-1).name("Emery").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Emery").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Enderium", new MaterialsBuilder().subID(321).name("Enderium").textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(89, 145, 135, 0).locName("Enderium").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build());
        MATERIALS_HASH_MAP.put("EnderiumBase", new MaterialsBuilder().subID(-1).name("EnderiumBase").textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(89, 145, 135, 0).locName("Enderium Base").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build());
        MATERIALS_HASH_MAP.put("Energized", new MaterialsBuilder().subID(-1).name("Energized").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Energized").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Epidote", new MaterialsBuilder().subID(-1).name("Epidote").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Epidote").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Eximite", new MaterialsBuilder().subID(959).name("Eximite").textureSet(SET_METALLIC).toolSpeed(5.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(124, 90, 150, 0).locName("Eximite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("FierySteel", new MaterialsBuilder().subID(346).name("FierySteel").textureSet(SET_FIERY).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID | TOOL | GEAR | SGEAR).solidColor(64, 0, 0, 0).locName("Fiery Steel").fuelType(5).fuelPow(2048).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 3), new TC_AspectStack(TC_Aspects.CORPUS, 3)).build());
        MATERIALS_HASH_MAP.put("Firestone", new MaterialsBuilder().subID(347).name("Firestone").textureSet(SET_QUARTZ).toolSpeed(6.0F).toolDura(1280).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 20, 0, 0).locName("Firestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Fluorite", new MaterialsBuilder().subID(-1).name("Fluorite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Fluorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build());
        MATERIALS_HASH_MAP.put("FoolsRuby", new MaterialsBuilder().subID(512).name("FoolsRuby").textureSet(SET_RUBY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM | ORE).solidColor(255, 100, 100, 127).locName("Ruby").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build());
        MATERIALS_HASH_MAP.put("Forcicium", new MaterialsBuilder().subID(518).name("Forcicium").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(50, 50, 70, 0).locName("Forcicium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build());
        MATERIALS_HASH_MAP.put("Forcillium", new MaterialsBuilder().subID(519).name("Forcillium").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(50, 50, 70, 0).locName("Forcillium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build());
        MATERIALS_HASH_MAP.put("Gabbro", new MaterialsBuilder().subID(-1).name("Gabbro").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Gabbro").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Glowstone", new MaterialsBuilder().subID(811).name("Glowstone").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID).solidColor(255, 255, 0, 0).locName("Glowstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.LUX, 2), new TC_AspectStack(TC_Aspects.SENSUS, 1)).build());
        MATERIALS_HASH_MAP.put("Gneiss", new MaterialsBuilder().subID(-1).name("Gneiss").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Gneiss").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Graphite", new MaterialsBuilder().subID(865).name("Graphite").textureSet(SET_DULL).toolSpeed(5.0F).toolDura(32).toolQual(2).type(DUST | ORE | LIQUID | TOOL).solidColor(128, 128, 128, 0).locName("Graphite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("Greenschist", new MaterialsBuilder().subID(-1).name("Greenschist").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Green Schist").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build());
        MATERIALS_HASH_MAP.put("Greenstone", new MaterialsBuilder().subID(-1).name("Greenstone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Greenstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build());
        MATERIALS_HASH_MAP.put("Greywacke", new MaterialsBuilder().subID(-1).name("Greywacke").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Greywacke").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build());
        MATERIALS_HASH_MAP.put("Haderoth", new MaterialsBuilder().subID(963).name("Haderoth").textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(3200).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(119, 52, 30, 0).locName("Haderoth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Hematite", new MaterialsBuilder().subID(-1).name("Hematite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Hematite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Hepatizon", new MaterialsBuilder().subID(957).name("Hepatizon").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(128).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(117, 94, 117, 0).locName("Hepatizon").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("HSLA", new MaterialsBuilder().subID(322).name("HSLA").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(500).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("HSLA Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)).build());
        MATERIALS_HASH_MAP.put("Ignatius", new MaterialsBuilder().subID(-1).name("Ignatius").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID).solidColor(255, 169, 83, 0).locName("Ignatius").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Infernal", new MaterialsBuilder().subID(-1).name("Infernal").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Infernal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Infuscolium", new MaterialsBuilder().subID(490).name("Infuscolium").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(146, 33, 86, 0).locName("Infuscolium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("InfusedGold", new MaterialsBuilder().subID(323).name("InfusedGold").textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 200, 60, 0).locName("Infused Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("InfusedAir", new MaterialsBuilder().subID(540).name("InfusedAir").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 0, 0).locName("Aer").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AER, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedFire", new MaterialsBuilder().subID(541).name("InfusedFire").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 0, 0, 0).locName("Ignis").fuelType(5).fuelPow(320).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedEarth", new MaterialsBuilder().subID(542).name("InfusedEarth").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(0, 255, 0, 0).locName("Terra").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.TERRA, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedWater", new MaterialsBuilder().subID(543).name("InfusedWater").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(0, 0, 255, 0).locName("Aqua").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AQUA, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedEntropy", new MaterialsBuilder().subID(544).name("InfusedEntropy").textureSet(SET_SHARDS).toolSpeed(32.0F).toolDura(64).toolQual(4).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(62, 62, 62, 0).locName("Perditio").fuelType(5).fuelPow(320).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedOrder", new MaterialsBuilder().subID(545).name("InfusedOrder").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(252, 252, 252, 0).locName("Ordo").fuelType(5).fuelPow(240).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.ORDO, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedVis", new MaterialsBuilder().subID(-1).name("InfusedVis").textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 0, 255, 0).locName("Auram").fuelType(5).fuelPow(240).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AURAM, 2)).build());
        MATERIALS_HASH_MAP.put("InfusedDull", new MaterialsBuilder().subID(-1).name("InfusedDull").textureSet(SET_SHARDS).toolSpeed(32.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Vacuus").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.VACUOS, 2)).build());
        MATERIALS_HASH_MAP.put("Inolashite", new MaterialsBuilder().subID(954).name("Inolashite").textureSet(SET_NONE).toolSpeed(8.0F).toolDura(2304).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(148, 216, 187, 0).locName("Inolashite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Invisium", new MaterialsBuilder().subID(-1).name("Invisium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Invisium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Jade", new MaterialsBuilder().subID(537).name("Jade").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(0, 100, 0, 0).locName("Jade").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Jasper", new MaterialsBuilder().subID(511).name("Jasper").textureSet(SET_EMERALD).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM | ORE).solidColor(200, 80, 80, 100).locName("Jasper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build());
        MATERIALS_HASH_MAP.put("Kalendrite", new MaterialsBuilder().subID(-1).name("Kalendrite").textureSet(SET_METALLIC).toolSpeed(5.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID).solidColor(170, 91, 189, 0).locName("Kalendrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Komatiite", new MaterialsBuilder().subID(-1).name("Komatiite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Komatiite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Lava", new MaterialsBuilder().subID(700).name("Lava").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(255, 64, 0, 0).locName("Lava").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build());
        MATERIALS_HASH_MAP.put("Lemurite", new MaterialsBuilder().subID(-1).name("Lemurite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(219, 219, 219, 0).locName("Lemurite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Limestone", new MaterialsBuilder().subID(-1).name("Limestone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Limestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Lodestone", new MaterialsBuilder().subID(-1).name("Lodestone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Lodestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Luminite", new MaterialsBuilder().subID(-1).name("Luminite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(250, 250, 250, 0).locName("Luminite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("Magma", new MaterialsBuilder().subID(-1).name("Magma").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 64, 0, 0).locName("Magma").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build());
        MATERIALS_HASH_MAP.put("Mawsitsit", new MaterialsBuilder().subID(-1).name("Mawsitsit").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Mawsitsit").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Mercassium", new MaterialsBuilder().subID(-1).name("Mercassium").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Mercassium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("MeteoricIron", new MaterialsBuilder().subID(340).name("MeteoricIron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(100, 50, 80, 0).locName("Meteoric Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("MeteoricSteel", new MaterialsBuilder().subID(341).name("MeteoricSteel").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(768).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(50, 25, 40, 0).locName("Meteoric Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)).build());
        MATERIALS_HASH_MAP.put("Meteorite", new MaterialsBuilder().subID(-1).name("Meteorite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(80, 35, 60, 0).locName("Meteorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build());
        MATERIALS_HASH_MAP.put("Meutoite", new MaterialsBuilder().subID(-1).name("Meutoite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE | LIQUID).solidColor(95, 82, 105, 0).locName("Meutoite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Migmatite", new MaterialsBuilder().subID(-1).name("Migmatite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Migmatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Mimichite", new MaterialsBuilder().subID(-1).name("Mimichite").textureSet(SET_GEM_VERTICAL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE).solidColor(255, 255, 255, 0).locName("Mimichite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Moonstone", new MaterialsBuilder().subID(-1).name("Moonstone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Moonstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build());
        MATERIALS_HASH_MAP.put("Naquadah", new MaterialsBuilder().subID(324).name("Naquadah").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(50, 50, 50, 0).locName("Naquadah").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build());
        MATERIALS_HASH_MAP.put("NaquadahAlloy", new MaterialsBuilder().subID(325).name("NaquadahAlloy").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(40, 40, 40, 0).locName("Naquadah Alloy").fuelType(0).fuelPow(0).meltAt(7200).blastAt(7200).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build());
        MATERIALS_HASH_MAP.put("NaquadahEnriched", new MaterialsBuilder().subID(326).name("NaquadahEnriched").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(50, 50, 50, 0).locName("Enriched Naquadah").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 2)).build());
        MATERIALS_HASH_MAP.put("Naquadria", new MaterialsBuilder().subID(327).name("Naquadria").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(512).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(30, 30, 30, 0).locName("Naquadria").fuelType(0).fuelPow(0).meltAt(9000).blastAt(9000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.RADIO, 3), new TC_AspectStack(TC_Aspects.NEBRISUM, 3)).build());
        MATERIALS_HASH_MAP.put("Nether", new MaterialsBuilder().subID(-1).name("Nether").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Nether").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("NetherBrick", new MaterialsBuilder().subID(814).name("NetherBrick").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(100, 0, 0, 0).locName("Nether Brick").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("NetherQuartz", new MaterialsBuilder().subID(522).name("NetherQuartz").textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(32).toolQual(1).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(230, 210, 210, 0).locName("Nether Quartz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build());
        MATERIALS_HASH_MAP.put("NetherStar", new MaterialsBuilder().subID(506).name("NetherStar").textureSet(SET_NETHERSTAR).toolSpeed(1.0F).toolDura(5120).toolQual(4).type(DUST | GEM | BGEM | TOOL).solidColor(255, 255, 255, 0).locName("Nether Star").fuelType(5).fuelPow(50000).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("Teslatite", new MaterialsBuilder().subID(812).name("Teslatite").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(60, 180, 200, 0).locName("Teslatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 2)).build());
        MATERIALS_HASH_MAP.put("ObsidianFlux", new MaterialsBuilder().subID(-1).name("ObsidianFlux").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(80, 50, 100, 0).locName("Fluxed Obsidian").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build());
        MATERIALS_HASH_MAP.put("Oilsands", new MaterialsBuilder().subID(878).name("Oilsands").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(10, 10, 10, 0).locName("Oilsands").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Onyx", new MaterialsBuilder().subID(-1).name("Onyx").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Onyx").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Orichalcum", new MaterialsBuilder().subID(-1).name("Orichalcum").textureSet(SET_METALLIC).toolSpeed(4.5F).toolDura(3456).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(84, 122, 56, 0).locName("Orichalcum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Osmonium", new MaterialsBuilder().subID(-1).name("Osmonium").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Osmonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build());
        MATERIALS_HASH_MAP.put("Oureclase", new MaterialsBuilder().subID(-1).name("Oureclase").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1920).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(183, 98, 21, 0).locName("Oureclase").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Painite", new MaterialsBuilder().subID(-1).name("Painite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Painite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Peanutwood", new MaterialsBuilder().subID(-1).name("Peanutwood").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Peanut Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Petroleum", new MaterialsBuilder().subID(-1).name("Petroleum").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Petroleum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Pewter", new MaterialsBuilder().subID(-1).name("Pewter").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Pewter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Phoenixite", new MaterialsBuilder().subID(-1).name("Phoenixite").textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Phoenixite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Potash", new MaterialsBuilder().subID(-1).name("Potash").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Potash").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Prometheum", new MaterialsBuilder().subID(-1).name("Prometheum").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(512).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(90, 129, 86, 0).locName("Prometheum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Quartzite", new MaterialsBuilder().subID(523).name("Quartzite").textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE).solidColor(210, 230, 210, 0).locName("Quartzite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("Quicklime", new MaterialsBuilder().subID(-1).name("Quicklime").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Quicklime").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Randomite", new MaterialsBuilder().subID(-1).name("Randomite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Randomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Rhyolite", new MaterialsBuilder().subID(875).name("Rhyolite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Rhyolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Rubracium", new MaterialsBuilder().subID(-1).name("Rubracium").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE | LIQUID).solidColor(151, 45, 45, 0).locName("Rubracium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Sand", new MaterialsBuilder().subID(-1).name("Sand").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Sanguinite", new MaterialsBuilder().subID(-1).name("Sanguinite").textureSet(SET_METALLIC).toolSpeed(3.0F).toolDura(4480).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(185, 0, 0, 0).locName("Sanguinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Siltstone", new MaterialsBuilder().subID(-1).name("Siltstone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Siltstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Spinel", new MaterialsBuilder().subID(-1).name("Spinel").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Spinel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Starconium", new MaterialsBuilder().subID(-1).name("Starconium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Starconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Sugilite", new MaterialsBuilder().subID(-1).name("Sugilite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Sugilite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Sunstone", new MaterialsBuilder().subID(-1).name("Sunstone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Sunstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build());
        MATERIALS_HASH_MAP.put("Tar", new MaterialsBuilder().subID(-1).name("Tar").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(10, 10, 10, 0).locName("Tar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Tartarite", new MaterialsBuilder().subID(-1).name("Tartarite").textureSet(SET_METALLIC).toolSpeed(20.0F).toolDura(7680).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID).solidColor(255, 118, 60, 0).locName("Tartarite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Tapazite", new MaterialsBuilder().subID(-1).name("Tapazite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Tapazite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build());
        MATERIALS_HASH_MAP.put("Thyrium", new MaterialsBuilder().subID(-1).name("Thyrium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Thyrium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Tourmaline", new MaterialsBuilder().subID(-1).name("Tourmaline").textureSet(SET_RUBY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Tourmaline").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Tritanium", new MaterialsBuilder().subID(329).name("Tritanium").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(2560).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Tritanium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 2)).build());
        MATERIALS_HASH_MAP.put("Turquoise", new MaterialsBuilder().subID(-1).name("Turquoise").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Turquoise").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("UUAmplifier", new MaterialsBuilder().subID(721).name("UUAmplifier").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(96, 0, 128, 0).locName("UU-Amplifier").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build());
        MATERIALS_HASH_MAP.put("UUMatter", new MaterialsBuilder().subID(703).name("UUMatter").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(128, 0, 196, 0).locName("UU-Matter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build());
        MATERIALS_HASH_MAP.put("Void", new MaterialsBuilder().subID(-1).name("Void").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 200).locName("Void").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 1)).build());
        MATERIALS_HASH_MAP.put("Voidstone", new MaterialsBuilder().subID(-1).name("Voidstone").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 200).locName("Voidstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.VACUOS, 1)).build());
        MATERIALS_HASH_MAP.put("Vulcanite", new MaterialsBuilder().subID(489).name("Vulcanite").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 132, 72, 0).locName("Vulcanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Vyroxeres", new MaterialsBuilder().subID(-1).name("Vyroxeres").textureSet(SET_METALLIC).toolSpeed(9.0F).toolDura(768).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(85, 224, 1, 0).locName("Vyroxeres").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build());
        MATERIALS_HASH_MAP.put("Wimalite", new MaterialsBuilder().subID(-1).name("Wimalite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ORE).solidColor(255, 255, 255, 0).locName("Wimalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Yellorite", new MaterialsBuilder().subID(-1).name("Yellorite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ORE).solidColor(255, 255, 255, 0).locName("Yellorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Yellorium", new MaterialsBuilder().subID(-1).name("Yellorium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Yellorium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Zectium", new MaterialsBuilder().subID(-1).name("Zectium").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Zectium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
    }

    /*
     * Circuitry, Batteries and other Technical things
     */
    private static void initCircuitryBaterisAndOtherTechnicalThins() {
        MATERIALS_HASH_MAP.put("Primitive", new MaterialsBuilder().subID(-1).name("Primitive").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Primitive").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1)).build());
        MATERIALS_HASH_MAP.put("Basic", new MaterialsBuilder().subID(-1).name("Basic").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Basic").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2)).build());
        MATERIALS_HASH_MAP.put("Good", new MaterialsBuilder().subID(-1).name("Good").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Good").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 3)).build());
        MATERIALS_HASH_MAP.put("Advanced", new MaterialsBuilder().subID(-1).name("Advanced").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Advanced").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4)).build());
        MATERIALS_HASH_MAP.put("Data", new MaterialsBuilder().subID(-1).name("Data").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Data").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 5)).build());
        MATERIALS_HASH_MAP.put("Elite", new MaterialsBuilder().subID(-1).name("Elite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Elite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 6)).build());
        MATERIALS_HASH_MAP.put("Master", new MaterialsBuilder().subID(-1).name("Master").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Master").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 7)).build());
        MATERIALS_HASH_MAP.put("Ultimate", new MaterialsBuilder().subID(-1).name("Ultimate").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Ultimate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8)).build());
        MATERIALS_HASH_MAP.put("Superconductor", new MaterialsBuilder().subID(-1).name("Superconductor").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Superconductor").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8)).build());
        MATERIALS_HASH_MAP.put("Infinite", new MaterialsBuilder().subID(-1).name("Infinite").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Infinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build());
    }

    /*
     * Not possible to determine exact Components
     */
    private static void initUndeterminedExactComponents() {
        MATERIALS_HASH_MAP.put("Antimatter", new MaterialsBuilder().subID(-1).name("Antimatter").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Antimatter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 9), new TC_AspectStack(TC_Aspects.PERFODIO, 8)).build());
        MATERIALS_HASH_MAP.put("BioFuel", new MaterialsBuilder().subID(705).name("BioFuel").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Biofuel").fuelType(0).fuelPow(6).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build());
        MATERIALS_HASH_MAP.put("Biomass", new MaterialsBuilder().subID(704).name("Biomass").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(0, 255, 0, 0).locName("Biomass").fuelType(3).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build());
        MATERIALS_HASH_MAP.put("Cheese", new MaterialsBuilder().subID(894).name("Cheese").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | ORE).solidColor(255, 255, 0, 0).locName("Cheese").fuelType(0).fuelPow(0).meltAt(320).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Chili", new MaterialsBuilder().subID(895).name("Chili").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(200, 0, 0, 0).locName("Chili").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Chocolate", new MaterialsBuilder().subID(886).name("Chocolate").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(190, 95, 0, 0).locName("Chocolate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
        MATERIALS_HASH_MAP.put("Cluster", new MaterialsBuilder().subID(-1).name("Cluster").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 127).locName("Cluster").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("CoalFuel", new MaterialsBuilder().subID(710).name("CoalFuel").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(50, 50, 70, 0).locName("Coalfuel").fuelType(0).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Cocoa", new MaterialsBuilder().subID(887).name("Cocoa").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(190, 95, 0, 0).locName("Cocoa").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
        MATERIALS_HASH_MAP.put("Coffee", new MaterialsBuilder().subID(888).name("Coffee").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 75, 0, 0).locName("Coffee").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
        MATERIALS_HASH_MAP.put("Creosote", new MaterialsBuilder().subID(712).name("Creosote").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(128, 64, 0, 0).locName("Creosote").fuelType(3).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
        MATERIALS_HASH_MAP.put("Ethanol", new MaterialsBuilder().subID(706).name("Ethanol").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Ethanol").fuelType(0).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build());
        MATERIALS_HASH_MAP.put("FishOil", new MaterialsBuilder().subID(711).name("FishOil").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 196, 0, 0).locName("Fish Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2)).build());
        MATERIALS_HASH_MAP.put("Fuel", new MaterialsBuilder().subID(708).name("Fuel").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 255, 0, 0).locName("Diesel").fuelType(0).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Glue", new MaterialsBuilder().subID(726).name("Glue").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 196, 0, 0).locName("Glue").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 2)).build());
        MATERIALS_HASH_MAP.put("Gunpowder", new MaterialsBuilder().subID(800).name("Gunpowder").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(128, 128, 128, 0).locName("Gunpowder").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build());
        MATERIALS_HASH_MAP.put("FryingOilHot", new MaterialsBuilder().subID(727).name("FryingOilHot").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 196, 0, 0).locName("Hot Frying Oil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("Honey", new MaterialsBuilder().subID(725).name("Honey").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(210, 200, 0, 0).locName("Honey").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Leather", new MaterialsBuilder().subID(-1).name("Leather").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 150, 80, 127).locName("Leather").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build());
        MATERIALS_HASH_MAP.put("LimePure", new MaterialsBuilder().subID(-1).name("LimePure").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Pure Lime").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).build());
        MATERIALS_HASH_MAP.put("Lubricant", new MaterialsBuilder().subID(724).name("Lubricant").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 196, 0, 0).locName("Lubricant").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build());
        MATERIALS_HASH_MAP.put("McGuffium239", new MaterialsBuilder().subID(999).name("McGuffium239").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 50, 150, 0).locName("Mc Guffium 239").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).aspects(new TC_AspectStack(TC_Aspects.ALIENIS, 8), new TC_AspectStack(TC_Aspects.PERMUTATIO, 8), new TC_AspectStack(TC_Aspects.SPIRITUS, 8), new TC_AspectStack(TC_Aspects.AURAM, 8), new TC_AspectStack(TC_Aspects.VITIUM, 8), new TC_AspectStack(TC_Aspects.RADIO, 8), new TC_AspectStack(TC_Aspects.MAGNETO, 8), new TC_AspectStack(TC_Aspects.ELECTRUM, 8), new TC_AspectStack(TC_Aspects.NEBRISUM, 8), new TC_AspectStack(TC_Aspects.STRONTIO, 8)).build());
        MATERIALS_HASH_MAP.put("MeatRaw", new MaterialsBuilder().subID(892).name("MeatRaw").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 100, 100, 0).locName("Raw Meat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build());
        MATERIALS_HASH_MAP.put("MeatCooked", new MaterialsBuilder().subID(893).name("MeatCooked").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 60, 20, 0).locName("Cooked Meat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build());
        MATERIALS_HASH_MAP.put("Milk", new MaterialsBuilder().subID(885).name("Milk").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID).solidColor(254, 254, 254, 0).locName("Milk").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 2)).build());
        MATERIALS_HASH_MAP.put("Mud", new MaterialsBuilder().subID(-1).name("Mud").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Mud").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
        MATERIALS_HASH_MAP.put("Oil", new MaterialsBuilder().subID(707).name("Oil").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(10, 10, 10, 0).locName("Oil").fuelType(3).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build());
        MATERIALS_HASH_MAP.put("Paper", new MaterialsBuilder().subID(879).name("Paper").textureSet(SET_PAPER).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(250, 250, 250, 0).locName("Paper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1)).build());
        MATERIALS_HASH_MAP.put("Peat", new MaterialsBuilder().subID(-1).name("Peat").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Peat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build());
        MATERIALS_HASH_MAP.put("Quantum", new MaterialsBuilder().subID(-1).name("Quantum").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Quantum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        MATERIALS_HASH_MAP.put("RareEarth", new MaterialsBuilder().subID(891).name("RareEarth").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(128, 128, 100, 0).locName("Rare Earth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build());
        MATERIALS_HASH_MAP.put("Red", new MaterialsBuilder().subID(-1).name("Red").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 0, 0, 0).locName("Red").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("Reinforced", new MaterialsBuilder().subID(-1).name("Reinforced").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Reinforced").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build());
        MATERIALS_HASH_MAP.put("SeedOil", new MaterialsBuilder().subID(713).name("SeedOil").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_AspectStack(TC_Aspects.GRANUM, 2)).build());
        MATERIALS_HASH_MAP.put("SeedOilHemp", new MaterialsBuilder().subID(722).name("SeedOilHemp").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Hemp Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)).build());
        MATERIALS_HASH_MAP.put("SeedOilLin", new MaterialsBuilder().subID(723).name("SeedOilLin").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Lin Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)).build());
        MATERIALS_HASH_MAP.put("Stone", new MaterialsBuilder().subID(299).name("Stone").textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(32).toolQual(1).type(DUST | TOOL | GEAR | SGEAR).solidColor(205, 205, 205, 0).locName("Stone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.TERRA, 1)).build());
        MATERIALS_HASH_MAP.put("TNT", new MaterialsBuilder().subID(-1).name("TNT").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("TNT").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 7), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build());
        MATERIALS_HASH_MAP.put("Unstable", new MaterialsBuilder().subID(-1).name("Unstable").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(4).type(0).solidColor(255, 255, 255, 127).locName("Unstable").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)).build());
        MATERIALS_HASH_MAP.put("Unstableingot", new MaterialsBuilder().subID(-1).name("Unstableingot").textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(4).type(0).solidColor(255, 255, 255, 127).locName("Unstable").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)).build());
        MATERIALS_HASH_MAP.put("Wheat", new MaterialsBuilder().subID(881).name("Wheat").textureSet(SET_POWDER).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 255, 196, 0).locName("Wheat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 2)).build());
    }

    /*
     * TODO: This
     */
    private static void initSomeOther() {
        MATERIALS_HASH_MAP.put("AluminiumBrass", new MaterialsBuilder().subID(-1).name("AluminiumBrass").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Aluminium Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Osmiridium", new MaterialsBuilder().subID(317).name("Osmiridium").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(1600).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 255, 0).locName("Osmiridium").fuelType(0).fuelPow(0).meltAt(3333).blastAt(2500).needBlast(true).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Iridium"), 3), new MaterialStack(get("Osmium"), 1)).build());
        MATERIALS_HASH_MAP.put("Sunnarium", new MaterialsBuilder().subID(318).name("Sunnarium").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 0, 0).locName("Sunnarium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Endstone", new MaterialsBuilder().subID(808).name("Endstone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(232, 232, 168, 0).locName("Endstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("Netherrack", new MaterialsBuilder().subID(807).name("Netherrack").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(200, 0, 0, 0).locName("Netherrack").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build());
        MATERIALS_HASH_MAP.put("SoulSand", new MaterialsBuilder().subID(-1).name("SoulSand").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 255, 255, 0).locName("Soulsand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build());
    }

    /*
     * First Degree Compounds, Begin Centrifuge and Electrolyzing recipes
     */
    private static void initFirstDegreeCompounds() {
        MATERIALS_HASH_MAP.put("Methane", new MaterialsBuilder().subID(715).name("Methane").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255, 0).locName("Methane").fuelType(1).fuelPow(45).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeMagenta).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Hydrogen"), 4)).build());
        MATERIALS_HASH_MAP.put("CarbonDioxide", new MaterialsBuilder().subID(497).name("CarbonDioxide").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Carbon Dioxide").fuelType(0).fuelPow(0).meltAt(25).blastAt(1).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("NobleGases", new MaterialsBuilder().subID(496).name("NobleGases").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Noble Gases").fuelType(0).fuelPow(0).meltAt(4).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("CarbonDioxide"), 21), new MaterialStack(get("Helium"), 9), new MaterialStack(get("Methane"), 3), new MaterialStack(get("Deuterium"), 1)).build());
        MATERIALS_HASH_MAP.put("Air", new MaterialsBuilder().subID(-1).name("Air").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Air").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Nitrogen"), 40), new MaterialStack(get("Oxygen"), 11), new MaterialStack(get("Argon"), 1), new MaterialStack(get("NobleGases"), 1)).build());
        MATERIALS_HASH_MAP.put("LIQUIDAir", new MaterialsBuilder().subID(-1).name("LIQUIDAir").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("LIQUID Air").fuelType(0).fuelPow(0).meltAt(4).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Nitrogen"), 40), new MaterialStack(get("Oxygen"), 11), new MaterialStack(get("Argon"), 1), new MaterialStack(get("NobleGases"), 1)).build());
        MATERIALS_HASH_MAP.put("Almandine", new MaterialsBuilder().subID(820).name("Almandine").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 0, 0, 0).locName("Almandine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Iron"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("Andradite", new MaterialsBuilder().subID(821).name("Andradite").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 120, 0, 0).locName("Andradite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Calcium"), 3), new MaterialStack(get("Iron"), 2), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("AnnealedCopper", new MaterialsBuilder().subID(345).name("AnnealedCopper").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | GEAR | SGEAR).solidColor(255, 120, 20, 0).locName("Annealed Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Copper"), 1)).build());
        MATERIALS_HASH_MAP.put("Asbestos", new MaterialsBuilder().subID(946).name("Asbestos").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 230, 0).locName("Asbestos").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Magnesium"), 3), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Hydrogen"), 4), new MaterialStack(get("Oxygen"), 9)).build()); // Mg3Si2O5(OH)4
        MATERIALS_HASH_MAP.put("Ash", new MaterialsBuilder().subID(815).name("Ash").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(150, 150, 150, 0).locName("Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 1)).build());
        MATERIALS_HASH_MAP.put("BandedIron", new MaterialsBuilder().subID(917).name("BandedIron").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(145, 90, 90, 0).locName("Banded Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Iron"), 2), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("BatteryAlloy", new MaterialsBuilder().subID(315).name("BatteryAlloy").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(156, 124, 160, 0).locName("Battery Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Lead"), 4), new MaterialStack(get("Antimony"), 1)).build());
        MATERIALS_HASH_MAP.put("BlueTopaz", new MaterialsBuilder().subID(513).name("BlueTopaz").textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(0, 0, 255, 127).locName("Blue Topaz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Fluorine"), 2), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 6)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build());
        MATERIALS_HASH_MAP.put("Bone", new MaterialsBuilder().subID(806).name("Bone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Bone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Calcium"), 1)).aspects(new TC_AspectStack(TC_Aspects.MORTUUS, 2), new TC_AspectStack(TC_Aspects.CORPUS, 1)).build());
        MATERIALS_HASH_MAP.put("Brass", new MaterialsBuilder().subID(301).name("Brass").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(96).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 180, 0, 0).locName("Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Zinc"), 1), new MaterialStack(get("Copper"), 3)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Bronze", new MaterialsBuilder().subID(300).name("Bronze").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(192).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 128, 0, 0).locName("Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Tin"), 1), new MaterialStack(get("Copper"), 3)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("BrownLimonite", new MaterialsBuilder().subID(930).name("BrownLimonite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Brown Limonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Oxygen"), 2)).build()); // FeO(OH)
        MATERIALS_HASH_MAP.put("Calcite", new MaterialsBuilder().subID(823).name("Calcite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 230, 220, 0).locName("Calcite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("Cassiterite", new MaterialsBuilder().subID(824).name("Cassiterite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ORE).solidColor(220, 220, 220, 0).locName("Cassiterite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Tin"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("CassiteriteSand", new MaterialsBuilder().subID(937).name("CassiteriteSand").textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ORE).solidColor(220, 220, 220, 0).locName("Cassiterite Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Tin"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Chalcopyrite", new MaterialsBuilder().subID(855).name("Chalcopyrite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(160, 120, 40, 0).locName("Chalcopyrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Copper"), 1), new MaterialStack(get("Iron"), 1), new MaterialStack(get("Sulfur"), 2)).build());
        MATERIALS_HASH_MAP.put("Chalk", new MaterialsBuilder().subID(-1).name("Chalk").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Chalk").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("Charcoal", new MaterialsBuilder().subID(536).name("Charcoal").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(100, 70, 70, 0).locName("Charcoal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build());
        MATERIALS_HASH_MAP.put("Chromite", new MaterialsBuilder().subID(825).name("Chromite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(35, 20, 15, 0).locName("Chromite").fuelType(0).fuelPow(0).meltAt(1700).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Chrome"), 2), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("ChromiumDioxide", new MaterialsBuilder().subID(361).name("ChromiumDioxide").textureSet(SET_DULL).toolSpeed(11.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(230, 200, 200, 0).locName("Chromium Dioxide").fuelType(0).fuelPow(0).meltAt(650).blastAt(650).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Chrome"), 1), new MaterialStack(get("Oxygen"), 2)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build());
        MATERIALS_HASH_MAP.put("Cinnabar", new MaterialsBuilder().subID(826).name("Cinnabar").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 0, 0, 0).locName("Cinnabar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Mercury"), 1), new MaterialStack(get("Sulfur"), 1)).build());
        MATERIALS_HASH_MAP.put("Water", new MaterialsBuilder().subID(701).name("Water").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build());
        MATERIALS_HASH_MAP.put("Clay", new MaterialsBuilder().subID(805).name("Clay").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(200, 200, 220, 0).locName("Clay").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Sodium"), 2), new MaterialStack(get("Lithium"), 1), new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Water"), 6)).build());
        MATERIALS_HASH_MAP.put("Coal", new MaterialsBuilder().subID(535).name("Coal").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(70, 70, 70, 0).locName("Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build());
        MATERIALS_HASH_MAP.put("Cobaltite", new MaterialsBuilder().subID(827).name("Cobaltite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(80, 80, 250, 0).locName("Cobaltite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Cobalt"), 1), new MaterialStack(get("Arsenic"), 1), new MaterialStack(get("Sulfur"), 1)).build());
        MATERIALS_HASH_MAP.put("Cooperite", new MaterialsBuilder().subID(828).name("Cooperite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 200, 0).locName("Sheldonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Platinum"), 3), new MaterialStack(get("Nickel"), 1), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Palladium"), 1)).build());
        MATERIALS_HASH_MAP.put("Cupronickel", new MaterialsBuilder().subID(310).name("Cupronickel").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(227, 150, 128, 0).locName("Cupronickel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Copper"), 1), new MaterialStack(get("Nickel"), 1)).build());
        MATERIALS_HASH_MAP.put("DarkAsh", new MaterialsBuilder().subID(816).name("DarkAsh").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(50, 50, 50, 0).locName("Dark Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 1)).build());
        MATERIALS_HASH_MAP.put("DeepIron", new MaterialsBuilder().subID(829).name("DeepIron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(150, 140, 140, 0).locName("Deep Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Iron"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("Diamond", new MaterialsBuilder().subID(500).name("Diamond").textureSet(SET_DIAMOND).toolSpeed(8.0F).toolDura(1280).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(200, 255, 255, 127).locName("Diamond").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 4)).build());
        MATERIALS_HASH_MAP.put("Electrum", new MaterialsBuilder().subID(303).name("Electrum").textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 100, 0).locName("Electrum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Silver"), 1), new MaterialStack(get("Gold"), 1)).build());
        MATERIALS_HASH_MAP.put("Emerald", new MaterialsBuilder().subID(501).name("Emerald").textureSet(SET_EMERALD).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(80, 255, 80, 127).locName("Emerald").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("Beryllium"), 3), new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 6), new MaterialStack(get("Oxygen"), 18)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 5)).build());
        MATERIALS_HASH_MAP.put("FreshWater", new MaterialsBuilder().subID(-1).name("FreshWater").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Fresh Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build());
        MATERIALS_HASH_MAP.put("Galena", new MaterialsBuilder().subID(830).name("Galena").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(100, 60, 100, 0).locName("Galena").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Lead"), 3), new MaterialStack(get("Silver"), 3), new MaterialStack(get("Sulfur"), 2)).build());
        MATERIALS_HASH_MAP.put("Garnierite", new MaterialsBuilder().subID(906).name("Garnierite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(50, 200, 70, 0).locName("Garnierite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Nickel"), 1), new MaterialStack(get("Oxygen"), 1)).build());
        MATERIALS_HASH_MAP.put("Glyceryl", new MaterialsBuilder().subID(714).name("Glyceryl").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 150, 150, 0).locName("Glyceryl Trinitrate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Carbon"), 3), new MaterialStack(get("Hydrogen"), 5), new MaterialStack(get("Nitrogen"), 3), new MaterialStack(get("Oxygen"), 9)).build());
        MATERIALS_HASH_MAP.put("GreenSapphire", new MaterialsBuilder().subID(504).name("GreenSapphire").textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 200, 130, 127).locName("Green Sapphire").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Oxygen"), 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Grossular", new MaterialsBuilder().subID(831).name("Grossular").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Grossular").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Calcium"), 3), new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("HolyWater", new MaterialsBuilder().subID(729).name("HolyWater").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Holy Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.AURAM, 1)).build());
        MATERIALS_HASH_MAP.put("Ice", new MaterialsBuilder().subID(702).name("Ice").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID).solidColor(200, 200, 255, 0).locName("Ice").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.GELUM, 2)).build());
        MATERIALS_HASH_MAP.put("Ilmenite", new MaterialsBuilder().subID(918).name("Ilmenite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(70, 55, 50, 0).locName("Ilmenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Titanium"), 1), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("Rutile", new MaterialsBuilder().subID(375).name("Rutile").textureSet(SET_GEM_HORIZONTAL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(212, 13, 92, 0).locName("Rutile").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Titanium"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Bauxite", new MaterialsBuilder().subID(822).name("Bauxite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Bauxite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Rutile"), 2), new MaterialStack(get("Aluminium"), 16), new MaterialStack(get("Hydrogen"), 10), new MaterialStack(get("Oxygen"), 11)).build());
        MATERIALS_HASH_MAP.put("Titaniumtetrachloride", new MaterialsBuilder().subID(-1).name("Titaniumtetrachloride").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(212, 13, 92, 0).locName("Titaniumtetrachloride").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Titanium"), 1), new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Chlorine"), 2)).build());
        MATERIALS_HASH_MAP.put("Magnesiumchloride", new MaterialsBuilder().subID(-1).name("Magnesiumchloride").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID).solidColor(212, 13, 92, 0).locName("Magnesiumchloride").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Chlorine"), 2)).build());
        MATERIALS_HASH_MAP.put("Invar", new MaterialsBuilder().subID(302).name("Invar").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 120, 0).locName("Invar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Iron"), 2), new MaterialStack(get("Nickel"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.GELUM, 1)).build());
        MATERIALS_HASH_MAP.put("IronCompressed", new MaterialsBuilder().subID(-1).name("IronCompressed").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(96).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Compressed Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Iron"), 1)).build());
        MATERIALS_HASH_MAP.put("Kanthal", new MaterialsBuilder().subID(312).name("Kanthal").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(194, 210, 223, 0).locName("Kanthal").fuelType(0).fuelPow(0).meltAt(1800).blastAt(1800).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Aluminium"), 1), new MaterialStack(get("Chrome"), 1)).build());
        MATERIALS_HASH_MAP.put("Lazurite", new MaterialsBuilder().subID(524).name("Lazurite").textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(100, 120, 255, 0).locName("Lazurite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Aluminium"), 6), new MaterialStack(get("Silicon"), 6), new MaterialStack(get("Calcium"), 8), new MaterialStack(get("Sodium"), 8)).build());
        MATERIALS_HASH_MAP.put("Magnalium", new MaterialsBuilder().subID(313).name("Magnalium").textureSet(SET_DULL).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 190, 255, 0).locName("Magnalium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Aluminium"), 2)).build());
        MATERIALS_HASH_MAP.put("Magnesite", new MaterialsBuilder().subID(908).name("Magnesite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 180, 0).locName("Magnesite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("Magnetite", new MaterialsBuilder().subID(870).name("Magnetite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(30, 30, 30, 0).locName("Magnetite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Iron"), 3), new MaterialStack(get("Oxygen"), 4)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("Molybdenite", new MaterialsBuilder().subID(942).name("Molybdenite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(25, 25, 25, 0).locName("Molybdenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Molybdenum"), 1), new MaterialStack(get("Sulfur"), 2)).build()); // MoS2 (also source of Re)
        MATERIALS_HASH_MAP.put("Nichrome", new MaterialsBuilder().subID(311).name("Nichrome").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(205, 206, 246, 0).locName("Nichrome").fuelType(0).fuelPow(0).meltAt(2700).blastAt(2700).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Nickel"), 4), new MaterialStack(get("Chrome"), 1)).build());
        MATERIALS_HASH_MAP.put("NiobiumNitride", new MaterialsBuilder().subID(359).name("NiobiumNitride").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(29, 41, 29, 0).locName("Niobium Nitride").fuelType(0).fuelPow(0).meltAt(2573).blastAt(2573).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Niobium"), 1), new MaterialStack(get("Nitrogen"), 1)).build()); // Anti-Reflective Material
        MATERIALS_HASH_MAP.put("NiobiumTitanium", new MaterialsBuilder().subID(360).name("NiobiumTitanium").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(29, 29, 41, 0).locName("Niobium-Titanium").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Niobium"), 1), new MaterialStack(get("Titanium"), 1)).build());
        MATERIALS_HASH_MAP.put("NitroCarbon", new MaterialsBuilder().subID(716).name("NitroCarbon").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 75, 100, 0).locName("Nitro-Carbon").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Nitrogen"), 1), new MaterialStack(get("Carbon"), 1)).build());
        MATERIALS_HASH_MAP.put("NitrogenDioxide", new MaterialsBuilder().subID(717).name("NitrogenDioxide").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(100, 175, 255, 0).locName("Nitrogen Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Nitrogen"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Obsidian", new MaterialsBuilder().subID(804).name("Obsidian").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST).solidColor(80, 50, 100, 0).locName("Obsidian").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Iron"), 1), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Oxygen"), 8)).build());
        MATERIALS_HASH_MAP.put("Phosphate", new MaterialsBuilder().subID(833).name("Phosphate").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE | LIQUID).solidColor(255, 255, 0, 0).locName("Phosphate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Phosphor"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("PigIron", new MaterialsBuilder().subID(307).name("PigIron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(200, 180, 180, 0).locName("Pig Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Iron"), 1)).build());
        MATERIALS_HASH_MAP.put("Plastic", new MaterialsBuilder().subID(874).name("Plastic").textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Polyethylene").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Hydrogen"), 2)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Epoxid", new MaterialsBuilder().subID(-1).name("Epoxid").textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 140, 20, 0).locName("Epoxid").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Hydrogen"), 4), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Silicone", new MaterialsBuilder().subID(-1).name("Silicone").textureSet(SET_DULL).toolSpeed(3.0F).toolDura(128).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(220, 220, 220, 0).locName("Polysiloxane").fuelType(0).fuelPow(0).meltAt(900).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Polycaprolactam", new MaterialsBuilder().subID(-1).name("Polycaprolactam").textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(50, 50, 50, 0).locName("Polycaprolactam").fuelType(0).fuelPow(0).meltAt(500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 6), new MaterialStack(get("Hydrogen"), 11), new MaterialStack(get("Nitrogen"), 1), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Polytetrafluoroethylene", new MaterialsBuilder().subID(-1).name("Polytetrafluoroethylene").textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Polytetrafluoroethylene").fuelType(0).fuelPow(0).meltAt(1400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Fluorine"), 4)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Powellite", new MaterialsBuilder().subID(883).name("Powellite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 0, 0).locName("Powellite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Molybdenum"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("Pumice", new MaterialsBuilder().subID(-1).name("Pumice").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(230, 185, 185, 0).locName("Pumice").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Stone"), 1)).build());
        MATERIALS_HASH_MAP.put("Pyrite", new MaterialsBuilder().subID(834).name("Pyrite").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 120, 40, 0).locName("Pyrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Sulfur"), 2)).build());
        MATERIALS_HASH_MAP.put("Pyrolusite", new MaterialsBuilder().subID(943).name("Pyrolusite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(150, 150, 170, 0).locName("Pyrolusite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(get("Manganese"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Pyrope", new MaterialsBuilder().subID(835).name("Pyrope").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(120, 50, 100, 0).locName("Pyrope").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Magnesium"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("RockSalt", new MaterialsBuilder().subID(944).name("RockSalt").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(240, 200, 200, 0).locName("Rock Salt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Chlorine"), 1)).build());
        MATERIALS_HASH_MAP.put("Rubber", new MaterialsBuilder().subID(880).name("Rubber").textureSet(SET_SHINY).toolSpeed(1.5F).toolDura(16).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(0, 0, 0, 0).locName("Rubber").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Carbon"), 5), new MaterialStack(get("Hydrogen"), 8)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("RawRubber", new MaterialsBuilder().subID(-1).name("RawRubber").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST).solidColor(204, 199, 137, 0).locName("Raw Rubber").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 5), new MaterialStack(get("Hydrogen"), 8)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build());
        MATERIALS_HASH_MAP.put("Ruby", new MaterialsBuilder().subID(502).name("Ruby").textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 100, 100, 127).locName("Ruby").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Chrome"), 1), new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Oxygen"), 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build());
        MATERIALS_HASH_MAP.put("Salt", new MaterialsBuilder().subID(817).name("Salt").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 250, 0).locName("Salt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Sodium"), 1), new MaterialStack(get("Chlorine"), 1)).build());
        MATERIALS_HASH_MAP.put("Saltpeter", new MaterialsBuilder().subID(836).name("Saltpeter").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 230, 0).locName("Saltpeter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Nitrogen"), 1), new MaterialStack(get("Oxygen"), 3)).build());
        MATERIALS_HASH_MAP.put("SaltWater", new MaterialsBuilder().subID(-1).name("SaltWater").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Salt Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build());
        MATERIALS_HASH_MAP.put("Sapphire", new MaterialsBuilder().subID(503).name("Sapphire").textureSet(SET_GEM_VERTICAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 100, 200, 127).locName("Sapphire").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Oxygen"), 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Scheelite", new MaterialsBuilder().subID(910).name("Scheelite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(200, 140, 20, 0).locName("Scheelite").fuelType(0).fuelPow(0).meltAt(2500).blastAt(2500).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Tungsten"), 1), new MaterialStack(get("Calcium"), 2), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("SiliconDioxide", new MaterialsBuilder().subID(837).name("SiliconDioxide").textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID).solidColor(200, 200, 200, 0).locName("Silicon Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Snow", new MaterialsBuilder().subID(728).name("Snow").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID).solidColor(250, 250, 250, 0).locName("Snow").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 1)).aspects(new TC_AspectStack(TC_Aspects.GELUM, 1)).build());
        MATERIALS_HASH_MAP.put("Sodalite", new MaterialsBuilder().subID(525).name("Sodalite").textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(20, 20, 255, 0).locName("Sodalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Aluminium"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Sodium"), 4), new MaterialStack(get("Chlorine"), 1)).build());
        MATERIALS_HASH_MAP.put("SodiumPersulfate", new MaterialsBuilder().subID(718).name("SodiumPersulfate").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST).solidColor(255, 255, 255, 0).locName("Sodium Persulfate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Sodium"), 1), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("SodiumSulfide", new MaterialsBuilder().subID(-1).name("SodiumSulfide").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255, 0).locName("Sodium Sulfide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Sodium"), 2), new MaterialStack(get("Sulfur"), 1)).build());
        //MATERIALS_HASH_MAP.put("HydricSulfide", new MaterialsBuilder().subID(-1).name("HydricSulfide").textureSet(SET_FLUID,1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL, 255, 255, 255,0,	"Hydrogen Sulfide",0,0, -1,0, false, false,1,1,1,.dye(dyeOrange ).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Sulfur"), 1)).build());
//private static Materials OilHeavy = new Materials.Builder().subID(730).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Heavy Oil" ,3, 32, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//private static Materials OilMedium = new Materials.Builder().subID(731).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Raw Oil" ,3, 24, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//private static Materials OilLight = new Materials.Builder().subID(732).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Light Oil" ,3, 16, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//private static Materials NatruralGas = new Materials.Builder().subID(733).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(1, 16, 255, 255, 255,0).locName("Natural Gas"	,1, 15, -1,0, false, false,3,1,1,.dye(dyeWhite	.build();
//private static Materials SulfuricGas = new Materials.Builder().subID(734).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(1, 16, 255, 255, 255,0).locName("Sulfuric Gas"	,1, 20, -1,0, false, false,3,1,1,.dye(dyeWhite	.build();
        MATERIALS_HASH_MAP.put("Gas", new MaterialsBuilder().subID(735).name("Gas").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255, 0).locName("Refinery Gas").fuelType(1).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build());
        //private static Materials SulfuricNaphtha= new MaterialsBuilder().subID( 736).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Naphtha",1, 32, -1,0, false, false,1,1,1,.dye(dyeYellow	.build();
//MATERIALS_HASH_MAP.put("SulfuricLightFuel", new MaterialsBuilder().subID(737).name("SulfuricLightFuel").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Light Fuel" ,0, 32, -1,0, false, false,1,1,1,.dye(dyeYellow	.build());
//MATERIALS_HASH_MAP.put("SulfuricHeavyFuel", new MaterialsBuilder().subID(738).name("SulfuricHeavyFuel").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Heavy Fuel" ,3, 32, -1,0, false, false,1,1,1,.dye(dyeBlack	.build());
//MATERIALS_HASH_MAP.put("Naphtha", new MaterialsBuilder().subID(739).name("Naphtha").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Naphtha" ,1, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build());
//MATERIALS_HASH_MAP.put("LightFuel", new MaterialsBuilder().subID(740).name("LightFuel").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Light Fuel"	,0, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build());
//MATERIALS_HASH_MAP.put("HeavyFuel", new MaterialsBuilder().subID(741).name("HeavyFuel").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Heavy Fuel"	,3, 192, -1,0, false, false,1,1,1,.dye(dyeBlack).build());
//MATERIALS_HASH_MAP.put("LPG", new MaterialsBuilder().subID(742).name("LPG").textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("LPG",1, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build());
        MATERIALS_HASH_MAP.put("SolderingAlloy", new MaterialsBuilder().subID(314).name("SolderingAlloy").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(220, 220, 230, 0).locName("Soldering Alloy").fuelType(0).fuelPow(0).meltAt(400).blastAt(400).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Tin"), 9), new MaterialStack(get("Antimony"), 1)).build());
        MATERIALS_HASH_MAP.put("Spessartine", new MaterialsBuilder().subID(838).name("Spessartine").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(255, 100, 100, 0).locName("Spessartine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Manganese"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("Sphalerite", new MaterialsBuilder().subID(839).name("Sphalerite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 255, 0).locName("Sphalerite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Zinc"), 1), new MaterialStack(get("Sulfur"), 1)).build());
        MATERIALS_HASH_MAP.put("StainlessSteel", new MaterialsBuilder().subID(306).name("StainlessSteel").textureSet(SET_SHINY).toolSpeed(7.0F).toolDura(480).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 220, 0).locName("Stainless Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Iron"), 6), new MaterialStack(get("Chrome"), 1), new MaterialStack(get("Manganese"), 1), new MaterialStack(get("Nickel"), 1)).build());
        MATERIALS_HASH_MAP.put("Steel", new MaterialsBuilder().subID(305).name("Steel").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Iron"), 50), new MaterialStack(get("Carbon"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)).build());
        MATERIALS_HASH_MAP.put("Stibnite", new MaterialsBuilder().subID(945).name("Stibnite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(70, 70, 70, 0).locName("Stibnite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Antimony"), 2), new MaterialStack(get("Sulfur"), 3)).build());
        MATERIALS_HASH_MAP.put("SulfuricAcid", new MaterialsBuilder().subID(720).name("SulfuricAcid").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Sulfuric Acid").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("SulfurDioxide", new MaterialsBuilder().subID(795).name("SulfurDioxide").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(186, 155, 46, 0).locName("Sulfur Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Tanzanite", new MaterialsBuilder().subID(508).name("Tanzanite").textureSet(SET_GEM_VERTICAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(64, 0, 200, 127).locName("Tanzanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Calcium"), 2), new MaterialStack(get("Aluminium"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Oxygen"), 13)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Tetrahedrite", new MaterialsBuilder().subID(840).name("Tetrahedrite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 32, 0, 0).locName("Tetrahedrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Copper"), 3), new MaterialStack(get("Antimony"), 1), new MaterialStack(get("Sulfur"), 3), new MaterialStack(get("Iron"), 1)).build()); //Cu3SbS3 + x(Fe,Zn)6Sb2S9
        MATERIALS_HASH_MAP.put("TinAlloy", new MaterialsBuilder().subID(363).name("TinAlloy").textureSet(SET_METALLIC).toolSpeed(6.5F).toolDura(96).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Tin Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Tin"), 1), new MaterialStack(get("Iron"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Topaz", new MaterialsBuilder().subID(507).name("Topaz").textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 128, 0, 127).locName("Topaz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Fluorine"), 2), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 6)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build());
        MATERIALS_HASH_MAP.put("Tungstate", new MaterialsBuilder().subID(841).name("Tungstate").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(55, 50, 35, 0).locName("Tungstate").fuelType(0).fuelPow(0).meltAt(2500).blastAt(2500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Tungsten"), 1), new MaterialStack(get("Lithium"), 2), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("Ultimet", new MaterialsBuilder().subID(344).name("Ultimet").textureSet(SET_SHINY).toolSpeed(9.0F).toolDura(2048).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 230, 0).locName("Ultimet").fuelType(0).fuelPow(0).meltAt(2700).blastAt(2700).needBlast(true).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Cobalt"), 5), new MaterialStack(get("Chrome"), 2), new MaterialStack(get("Nickel"), 1), new MaterialStack(get("Molybdenum"), 1)).build()); // 54% Cobalt, 26% Chromium, 9% Nickel, 5% Molybdenum, 3% Iron, 2% Tungsten, 0.8% Manganese, 0.3% Silicon, 0.08% Nitrogen and 0.06% Carbon
        MATERIALS_HASH_MAP.put("Uraninite", new MaterialsBuilder().subID(922).name("Uraninite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(35, 35, 35, 0).locName("Uraninite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(get("Uranium"), 1), new MaterialStack(get("Oxygen"), 2)).build());
        MATERIALS_HASH_MAP.put("Uvarovite", new MaterialsBuilder().subID(842).name("Uvarovite").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(180, 255, 180, 0).locName("Uvarovite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("Calcium"), 3), new MaterialStack(get("Chrome"), 2), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 12)).build());
        MATERIALS_HASH_MAP.put("VanadiumGallium", new MaterialsBuilder().subID(357).name("VanadiumGallium").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(128, 128, 140, 0).locName("Vanadium-Gallium").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Vanadium"), 3), new MaterialStack(get("Gallium"), 1)).build());
        MATERIALS_HASH_MAP.put("Wood", new MaterialsBuilder().subID(809).name("Wood").textureSet(SET_WOOD).toolSpeed(2.0F).toolDura(16).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 50, 0, 0).locName("Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Oxygen"), 1), new MaterialStack(get("Hydrogen"), 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2)).build());
        MATERIALS_HASH_MAP.put("WroughtIron", new MaterialsBuilder().subID(304).name("WroughtIron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 180, 180, 0).locName("Wrought Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(get("Iron"), 1)).build());
        MATERIALS_HASH_MAP.put("Wulfenite", new MaterialsBuilder().subID(882).name("Wulfenite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(255, 128, 0, 0).locName("Wulfenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Lead"), 1), new MaterialStack(get("Molybdenum"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("YellowLimonite", new MaterialsBuilder().subID(931).name("YellowLimonite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 200, 0, 0).locName("Yellow Limonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Oxygen"), 2)).build()); // FeO(OH) + a bit Ni and Co
        MATERIALS_HASH_MAP.put("YttriumBariumCuprate", new MaterialsBuilder().subID(358).name("YttriumBariumCuprate").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(80, 64, 70, 0).locName("Yttrium Barium Cuprate").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Yttrium"), 1), new MaterialStack(get("Barium"), 2), new MaterialStack(get("Copper"), 3), new MaterialStack(get("Oxygen"), 7)).build());
    }

    /*
     * Second Degree Compounds
     */
    private static void initSecondDegreeCompounds() {
        MATERIALS_HASH_MAP.put("WoodSealed", new MaterialsBuilder().subID(889).name("WoodSealed").textureSet(SET_WOOD).toolSpeed(3.0F).toolDura(24).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(80, 40, 0, 0).locName("Sealed Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Wood"), 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.FABRICO, 1)).build());
        MATERIALS_HASH_MAP.put("LiveRoot", new MaterialsBuilder().subID(832).name("LiveRoot").textureSet(SET_WOOD).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(220, 200, 0, 0).locName("Liveroot").fuelType(5).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Wood"), 3), new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.VICTUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("IronWood", new MaterialsBuilder().subID(338).name("IronWood").textureSet(SET_WOOD).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(150, 140, 110, 0).locName("Ironwood").fuelType(5).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(get("Iron"), 9), new MaterialStack(get("LiveRoot"), 9), new MaterialStack(get("Gold"), 1)).build());
        MATERIALS_HASH_MAP.put("Glass", new MaterialsBuilder().subID(890).name("Glass").textureSet(SET_GLASS).toolSpeed(1.0F).toolDura(4).toolQual(0).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(250, 250, 250, 220).locName("Glass").fuelType(0).fuelPow(0).meltAt(1500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("SiliconDioxide"), 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2)).build());
        MATERIALS_HASH_MAP.put("Perlite", new MaterialsBuilder().subID(-1).name("Perlite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(30, 20, 30, 0).locName("Perlite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Obsidian"), 2), new MaterialStack(get("Water"), 1)).build());
        MATERIALS_HASH_MAP.put("Borax", new MaterialsBuilder().subID(-1).name("Borax").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 250, 0).locName("Borax").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Sodium"), 2), new MaterialStack(get("Boron"), 4), new MaterialStack(get("Water"), 10), new MaterialStack(get("Oxygen"), 7)).build());
        MATERIALS_HASH_MAP.put("Lignite", new MaterialsBuilder().subID(538).name("Lignite").textureSet(SET_LIGNITE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(100, 70, 70, 0).locName("Lignite Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Water"), 4), new MaterialStack(get("DarkAsh"), 1)).build());
        MATERIALS_HASH_MAP.put("Olivine", new MaterialsBuilder().subID(505).name("Olivine").textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(150, 255, 150, 127).locName("Olivine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(get("Magnesium"), 2), new MaterialStack(get("Iron"), 1), new MaterialStack(get("SiliconDioxide"), 2)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build());
        MATERIALS_HASH_MAP.put("Opal", new MaterialsBuilder().subID(510).name("Opal").textureSet(SET_OPAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(0, 0, 255, 0).locName("Opal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("SiliconDioxide"), 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Amethyst", new MaterialsBuilder().subID(509).name("Amethyst").textureSet(SET_FLINT).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(210, 50, 210, 127).locName("Amethyst").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("SiliconDioxide"), 4), new MaterialStack(get("Iron"), 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build());
        MATERIALS_HASH_MAP.put("Redstone", new MaterialsBuilder().subID(810).name("Redstone").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 0, 0, 0).locName("Redstone").fuelType(0).fuelPow(0).meltAt(500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Pyrite"), 5), new MaterialStack(get("Ruby"), 1), new MaterialStack(get("Mercury"), 3)).aspects(new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build());
        MATERIALS_HASH_MAP.put("Lapis", new MaterialsBuilder().subID(526).name("Lapis").textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(70, 70, 220, 0).locName("Lapis").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Lazurite"), 12), new MaterialStack(get("Sodalite"), 2), new MaterialStack(get("Pyrite"), 1), new MaterialStack(get("Calcite"), 1)).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 1)).build());
        MATERIALS_HASH_MAP.put("Blaze", new MaterialsBuilder().subID(801).name("Blaze").textureSet(SET_POWDER).toolSpeed(2.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | TOOL).solidColor(255, 200, 0, 0).locName("Blaze").fuelType(0).fuelPow(0).meltAt(6400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("DarkAsh"), 1), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build());
        MATERIALS_HASH_MAP.put("EnderPearl", new MaterialsBuilder().subID(532).name("EnderPearl").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(108, 220, 200, 0).locName("Enderpearl").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("Beryllium"), 1), new MaterialStack(get("Potassium"), 4), new MaterialStack(get("Nitrogen"), 5), new MaterialStack(get("Magic"), 6)).aspects(new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2)).build());
        MATERIALS_HASH_MAP.put("EnderEye", new MaterialsBuilder().subID(533).name("EnderEye").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(160, 250, 230, 0).locName("Endereye").fuelType(5).fuelPow(10).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("EnderPearl"), 1), new MaterialStack(get("Blaze"), 1)).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 4), new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build());
        MATERIALS_HASH_MAP.put("Flint", new MaterialsBuilder().subID(802).name("Flint").textureSet(SET_FLINT).toolSpeed(2.5F).toolDura(64).toolQual(1).type(ELEC | CENT | DUST | TOOL).solidColor(0, 32, 64, 0).locName("Flint").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("SiliconDioxide"), 1)).aspects(new TC_AspectStack(TC_Aspects.TERRA, 1), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build());
        MATERIALS_HASH_MAP.put("Diatomite", new MaterialsBuilder().subID(948).name("Diatomite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(225, 225, 225, 0).locName("Diatomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Flint"), 8), new MaterialStack(get("BandedIron"), 1), new MaterialStack(get("Sapphire"), 1)).build());
        MATERIALS_HASH_MAP.put("VolcanicAsh", new MaterialsBuilder().subID(-1).name("VolcanicAsh").textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST).solidColor(60, 50, 50, 0).locName("Volcanic Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Flint"), 6), new MaterialStack(get("Iron"), 1), new MaterialStack(get("Magnesium"), 1)).build());
        MATERIALS_HASH_MAP.put("Niter", new MaterialsBuilder().subID(531).name("Niter").textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(255, 200, 200, 0).locName("Niter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Saltpeter"), 1)).build());
        MATERIALS_HASH_MAP.put("Pyrotheum", new MaterialsBuilder().subID(843).name("Pyrotheum").textureSet(SET_FIERY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(255, 128, 0, 0).locName("Pyrotheum").fuelType(2).fuelPow(62).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Coal"), 1), new MaterialStack(get("Redstone"), 1), new MaterialStack(get("Blaze"), 1)).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build());
        MATERIALS_HASH_MAP.put("HydratedCoal", new MaterialsBuilder().subID(818).name("HydratedCoal").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(70, 70, 100, 0).locName("Hydrated Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Coal"), 8), new MaterialStack(get("Water"), 1)).build());
        MATERIALS_HASH_MAP.put("Apatite", new MaterialsBuilder().subID(530).name("Apatite").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(200, 200, 255, 0).locName("Apatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Calcium"), 5), new MaterialStack(get("Phosphate"), 3), new MaterialStack(get("Chlorine"), 1)).aspects(new TC_AspectStack(TC_Aspects.MESSIS, 2)).build());
        MATERIALS_HASH_MAP.put("Alumite", new MaterialsBuilder().subID(-1).name("Alumite").textureSet(SET_METALLIC).toolSpeed(1.5F).toolDura(64).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Alumite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Aluminium"), 5), new MaterialStack(get("Iron"), 2), new MaterialStack(get("Obsidian"), 2)).aspects(new TC_AspectStack(TC_Aspects.STRONTIO, 2)).build());
        MATERIALS_HASH_MAP.put("Manyullyn", new MaterialsBuilder().subID(-1).name("Manyullyn").textureSet(SET_METALLIC).toolSpeed(1.5F).toolDura(64).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Manyullyn").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Cobalt"), 1), new MaterialStack(get("Aredrite"), 1)).aspects(new TC_AspectStack(TC_Aspects.STRONTIO, 2)).build());
        MATERIALS_HASH_MAP.put("ShadowIron", new MaterialsBuilder().subID(336).name("ShadowIron").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(120, 120, 120, 0).locName("Shadowiron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Iron"), 3), new MaterialStack(get("Magic"), 1)).build());
        MATERIALS_HASH_MAP.put("ShadowSteel", new MaterialsBuilder().subID(337).name("ShadowSteel").textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(768).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(90, 90, 90, 0).locName("Shadowsteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Steel"), 3), new MaterialStack(get("Magic"), 1)).build());
        MATERIALS_HASH_MAP.put("Steeleaf", new MaterialsBuilder().subID(339).name("Steeleaf").textureSet(SET_LEAF).toolSpeed(8.0F).toolDura(768).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(50, 127, 50, 0).locName("Steeleaf").fuelType(5).fuelPow(24).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("Steel"), 1), new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.HERBA, 2), new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("Knightmetal", new MaterialsBuilder().subID(362).name("Knightmetal").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1024).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(210, 240, 200, 0).locName("Knightmetal").fuelType(5).fuelPow(24).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(get("Steel"), 2), new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 1), new TC_AspectStack(TC_Aspects.METALLUM, 2)).build());
        MATERIALS_HASH_MAP.put("SterlingSilver", new MaterialsBuilder().subID(350).name("SterlingSilver").textureSet(SET_SHINY).toolSpeed(13.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(250, 220, 225, 0).locName("Sterling Silver").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Copper"), 1), new MaterialStack(get("Silver"), 4)).build());
        MATERIALS_HASH_MAP.put("RoseGold", new MaterialsBuilder().subID(351).name("RoseGold").textureSet(SET_SHINY).toolSpeed(14.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 230, 30, 0).locName("Rose Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1600).needBlast(true).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Copper"), 1), new MaterialStack(get("Gold"), 4)).build());
        MATERIALS_HASH_MAP.put("BlackBronze", new MaterialsBuilder().subID(352).name("BlackBronze").textureSet(SET_DULL).toolSpeed(12.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 50, 125, 0).locName("Black Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(2000).needBlast(true).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Gold"), 1), new MaterialStack(get("Silver"), 1), new MaterialStack(get("Copper"), 3)).build());
        MATERIALS_HASH_MAP.put("BismuthBronze", new MaterialsBuilder().subID(353).name("BismuthBronze").textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 125, 125, 0).locName("Bismuth Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1100).needBlast(true).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(get("Bismuth"), 1), new MaterialStack(get("Zinc"), 1), new MaterialStack(get("Copper"), 3)).build());
        MATERIALS_HASH_MAP.put("BlackSteel", new MaterialsBuilder().subID(334).name("BlackSteel").textureSet(SET_METALLIC).toolSpeed(6.5F).toolDura(768).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(100, 100, 100, 0).locName("Black Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1200).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Nickel"), 1), new MaterialStack(get("BlackBronze"), 1), new MaterialStack(get("Steel"), 3)).build());
        MATERIALS_HASH_MAP.put("RedSteel", new MaterialsBuilder().subID(348).name("RedSteel").textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(896).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(140, 100, 100, 0).locName("Red Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1300).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("SterlingSilver"), 1), new MaterialStack(get("BismuthBronze"), 1), new MaterialStack(get("Steel"), 2), new MaterialStack(get("BlackSteel"), 4)).build());
        MATERIALS_HASH_MAP.put("BlueSteel", new MaterialsBuilder().subID(349).name("BlueSteel").textureSet(SET_METALLIC).toolSpeed(7.5F).toolDura(1024).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(100, 100, 140, 0).locName("Blue Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("RoseGold"), 1), new MaterialStack(get("Brass"), 1), new MaterialStack(get("Steel"), 2), new MaterialStack(get("BlackSteel"), 4)).build());
        MATERIALS_HASH_MAP.put("DamascusSteel", new MaterialsBuilder().subID(335).name("DamascusSteel").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1280).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(110, 110, 110, 0).locName("Damascus Steel").fuelType(0).fuelPow(0).meltAt(2000).blastAt(1500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Steel"), 1)).build());
        MATERIALS_HASH_MAP.put("TungstenSteel", new MaterialsBuilder().subID(316).name("TungstenSteel").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(2560).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 160, 0).locName("Tungstensteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("Steel"), 1), new MaterialStack(get("Tungsten"), 1)).build());
        MATERIALS_HASH_MAP.put("NitroCoalFuel", new MaterialsBuilder().subID(-1).name("NitroCoalFuel").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(50, 70, 50, 0).locName("Nitro-Coalfuel").fuelType(0).fuelPow(48).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Glyceryl"), 1), new MaterialStack(get("CoalFuel"), 4)).build());
        MATERIALS_HASH_MAP.put("NitroFuel", new MaterialsBuilder().subID(709).name("NitroFuel").textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(200, 255, 0, 0).locName("Nitro-Diesel").fuelType(0).fuelPow(512).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(get("Glyceryl"), 1), new MaterialStack(get("Fuel"), 4)).build());
        MATERIALS_HASH_MAP.put("AstralSilver", new MaterialsBuilder().subID(333).name("AstralSilver").textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(230, 230, 255, 0).locName("Astral Silver").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Silver"), 2), new MaterialStack(get("Magic"), 1)).build());
        MATERIALS_HASH_MAP.put("Midasium", new MaterialsBuilder().subID(332).name("Midasium").textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 200, 40, 0).locName("Midasium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Gold"), 2), new MaterialStack(get("Magic"), 1)).build());
        MATERIALS_HASH_MAP.put("Mithril", new MaterialsBuilder().subID(331).name("Mithril").textureSet(SET_SHINY).toolSpeed(14.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 210, 0).locName("Mithril").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Platinum"), 2), new MaterialStack(get("Magic"), 1)).build());
        MATERIALS_HASH_MAP.put("BlueAlloy", new MaterialsBuilder().subID(309).name("BlueAlloy").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(100, 180, 255, 0).locName("Blue Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Silver"), 1), new MaterialStack(get("Teslatite"), 4)).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 3)).build());
        MATERIALS_HASH_MAP.put("RedAlloy", new MaterialsBuilder().subID(308).name("RedAlloy").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(200, 0, 0, 0).locName("Red Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Metal"), 1), new MaterialStack(get("Redstone"), 4)).aspects(new TC_AspectStack(TC_Aspects.MACHINA, 3)).build());
        MATERIALS_HASH_MAP.put("CobaltBrass", new MaterialsBuilder().subID(343).name("CobaltBrass").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 160, 0).locName("Cobalt Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("Brass"), 7), new MaterialStack(get("Aluminium"), 1), new MaterialStack(get("Cobalt"), 1)).build());
        MATERIALS_HASH_MAP.put("Phosphorus", new MaterialsBuilder().subID(534).name("Phosphorus").textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | LIQUID).solidColor(255, 255, 0, 0).locName("Phosphorus").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Calcium"), 3), new MaterialStack(get("Phosphate"), 2)).build());
        MATERIALS_HASH_MAP.put("Basalt", new MaterialsBuilder().subID(844).name("Basalt").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(30, 20, 20, 0).locName("Basalt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Olivine"), 1), new MaterialStack(get("Calcite"), 3), new MaterialStack(get("Flint"), 8), new MaterialStack(get("DarkAsh"), 4)).aspects(new TC_AspectStack(TC_Aspects.TENEBRAE, 1)).build());
        MATERIALS_HASH_MAP.put("GarnetRed", new MaterialsBuilder().subID(527).name("GarnetRed").textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 80, 80, 127).locName("Red Garnet").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Pyrope"), 3), new MaterialStack(get("Almandine"), 5), new MaterialStack(get("Spessartine"), 8)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("GarnetYellow", new MaterialsBuilder().subID(528).name("GarnetYellow").textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 200, 80, 127).locName("Yellow Garnet").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Andradite"), 5), new MaterialStack(get("Grossular"), 8), new MaterialStack(get("Uvarovite"), 3)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build());
        MATERIALS_HASH_MAP.put("Marble", new MaterialsBuilder().subID(845).name("Marble").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(200, 200, 200, 0).locName("Marble").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Calcite"), 7)).aspects(new TC_AspectStack(TC_Aspects.PERFODIO, 1)).build());
        MATERIALS_HASH_MAP.put("Sugar", new MaterialsBuilder().subID(803).name("Sugar").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Sugar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Water"), 5), new MaterialStack(get("Oxygen"), 25)).aspects(new TC_AspectStack(TC_Aspects.HERBA, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.AER, 1)).build());
        MATERIALS_HASH_MAP.put("Thaumium", new MaterialsBuilder().subID(330).name("Thaumium").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(150, 100, 200, 0).locName("Thaumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Iron"), 1), new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("Vinteum", new MaterialsBuilder().subID(529).name("Vinteum").textureSet(SET_EMERALD).toolSpeed(10.0F).toolDura(128).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 200, 255, 0).locName("Vinteum").fuelType(5).fuelPow(32).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("Vis", new MaterialsBuilder().subID(-1).name("Vis").textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).solidColor(128, 0, 255, 0).locName("Vis").fuelType(5).fuelPow(32).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("Magic"), 1)).aspects(new TC_AspectStack(TC_Aspects.AURAM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build());
        MATERIALS_HASH_MAP.put("Redrock", new MaterialsBuilder().subID(846).name("Redrock").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(255, 80, 50, 0).locName("Redrock").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("Calcite"), 2), new MaterialStack(get("Flint"), 1), new MaterialStack(get("Clay"), 1)).build());
        MATERIALS_HASH_MAP.put("PotassiumFeldspar", new MaterialsBuilder().subID(847).name("PotassiumFeldspar").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(120, 40, 40, 0).locName("Potassium Feldspar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Aluminium"), 1), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 8)).build());
        MATERIALS_HASH_MAP.put("Biotite", new MaterialsBuilder().subID(848).name("Biotite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(20, 30, 20, 0).locName("Biotite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Magnesium"), 3), new MaterialStack(get("Aluminium"), 3), new MaterialStack(get("Fluorine"), 2), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Oxygen"), 10)).build());
        MATERIALS_HASH_MAP.put("GraniteBlack", new MaterialsBuilder().subID(849).name("GraniteBlack").textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | TOOL | GEAR | SGEAR).solidColor(10, 10, 10, 0).locName("Black Granite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("SiliconDioxide"), 4), new MaterialStack(get("Biotite"), 1)).aspects(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build());
        MATERIALS_HASH_MAP.put("GraniteRed", new MaterialsBuilder().subID(850).name("GraniteRed").textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | TOOL | GEAR | SGEAR).solidColor(255, 0, 128, 0).locName("Red Granite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeMagenta).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("PotassiumFeldspar"), 1), new MaterialStack(get("Oxygen"), 3)).aspects(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build());
        MATERIALS_HASH_MAP.put("Chrysotile", new MaterialsBuilder().subID(912).name("Chrysotile").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(110, 140, 110, 0).locName("Chrysotile").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Asbestos"), 1)).build());
        MATERIALS_HASH_MAP.put("Realgar", new MaterialsBuilder().subID(-1).name("Realgar").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(140, 100, 100, 0).locName("Realgar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Arsenic"), 4), new MaterialStack(get("Sulfur"), 4)).build());
        MATERIALS_HASH_MAP.put("VanadiumMagnetite", new MaterialsBuilder().subID(923).name("VanadiumMagnetite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(35, 35, 60, 0).locName("Vanadium Magnetite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Magnetite"), 1), new MaterialStack(get("Vanadium"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build()); // Mixture of Fe3O4 and V2O5
        MATERIALS_HASH_MAP.put("BasalticMineralSand", new MaterialsBuilder().subID(935).name("BasalticMineralSand").textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(40, 50, 40, 0).locName("Basaltic Mineral Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Magnetite"), 1), new MaterialStack(get("Basalt"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("GraniticMineralSand", new MaterialsBuilder().subID(936).name("GraniticMineralSand").textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(40, 60, 60, 0).locName("Granitic Mineral Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Magnetite"), 1), new MaterialStack(get("GraniteBlack"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("GarnetSand", new MaterialsBuilder().subID(938).name("GarnetSand").textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Garnet Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(get("GarnetRed"), 1), new MaterialStack(get("GarnetYellow"), 1)).build());
        MATERIALS_HASH_MAP.put("QuartzSand", new MaterialsBuilder().subID(939).name("QuartzSand").textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 200, 200, 0).locName("Quartz Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("CertusQuartz"), 1), new MaterialStack(get("Quartzite"), 1)).build());
        MATERIALS_HASH_MAP.put("Bastnasite", new MaterialsBuilder().subID(905).name("Bastnasite").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 110, 45, 0).locName("Bastnasite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Cerium"), 1), new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Fluorine"), 1), new MaterialStack(get("Oxygen"), 3)).build()); // (Ce, La, Y)CO3F
        MATERIALS_HASH_MAP.put("Pentlandite", new MaterialsBuilder().subID(909).name("Pentlandite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(165, 150, 5, 0).locName("Pentlandite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Nickel"), 9), new MaterialStack(get("Sulfur"), 8)).build()); // (Fe,Ni)9S8
        MATERIALS_HASH_MAP.put("Spodumene", new MaterialsBuilder().subID(920).name("Spodumene").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(190, 170, 170, 0).locName("Spodumene").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Lithium"), 1), new MaterialStack(get("Aluminium"), 1), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Oxygen"), 6)).build()); // LiAl(SiO3)2
        MATERIALS_HASH_MAP.put("Pollucite", new MaterialsBuilder().subID(919).name("Pollucite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 210, 210, 0).locName("Pollucite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Caesium"), 2), new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 4), new MaterialStack(get("Water"), 2), new MaterialStack(get("Oxygen"), 12)).build()); // (Cs,Na)2Al2Si4O12 2H2O (also a source of Rb)
        MATERIALS_HASH_MAP.put("Tantalite", new MaterialsBuilder().subID(921).name("Tantalite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(145, 80, 40, 0).locName("Tantalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Manganese"), 1), new MaterialStack(get("Tantalum"), 2), new MaterialStack(get("Oxygen"), 6)).build()); // (Fe, Mn)Ta2O6 (also source of Nb)
        MATERIALS_HASH_MAP.put("Lepidolite", new MaterialsBuilder().subID(907).name("Lepidolite").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 50, 140, 0).locName("Lepidolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Lithium"), 3), new MaterialStack(get("Aluminium"), 4), new MaterialStack(get("Fluorine"), 2), new MaterialStack(get("Oxygen"), 10)).build()); // K(Li,Al,Rb)3(Al,Si)4O10(F,OH)2
        MATERIALS_HASH_MAP.put("Glauconite", new MaterialsBuilder().subID(933).name("Glauconite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(130, 180, 60, 0).locName("Glauconite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Magnesium"), 2), new MaterialStack(get("Aluminium"), 4), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 12)).build()); // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
        MATERIALS_HASH_MAP.put("GlauconiteSand", new MaterialsBuilder().subID(949).name("GlauconiteSand").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(130, 180, 60, 0).locName("Glauconite Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Magnesium"), 2), new MaterialStack(get("Aluminium"), 4), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 12)).build()); // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
        MATERIALS_HASH_MAP.put("Vermiculite", new MaterialsBuilder().subID(932).name("Vermiculite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 180, 15, 0).locName("Vermiculite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Iron"), 3), new MaterialStack(get("Aluminium"), 4), new MaterialStack(get("Silicon"), 4), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Water"), 4), new MaterialStack(get("Oxygen"), 12)).build()); // (Mg+2, Fe+2, Fe+3)3 [(AlSi)4O10] (OH)2 4H2O)
        MATERIALS_HASH_MAP.put("Bentonite", new MaterialsBuilder().subID(927).name("Bentonite").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(245, 215, 210, 0).locName("Bentonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Sodium"), 1), new MaterialStack(get("Magnesium"), 6), new MaterialStack(get("Silicon"), 12), new MaterialStack(get("Hydrogen"), 6), new MaterialStack(get("Water"), 5), new MaterialStack(get("Oxygen"), 36)).build()); // (Na,Ca)0.33(Al,Mg)2(Si4O10)(OH)2 nH2O
        MATERIALS_HASH_MAP.put("FullersEarth", new MaterialsBuilder().subID(928).name("FullersEarth").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(160, 160, 120, 0).locName("Fullers Earth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Silicon"), 4), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Water"), 4), new MaterialStack(get("Oxygen"), 11)).build()); // (Mg,Al)2Si4O10(OH) 4(H2O)
        MATERIALS_HASH_MAP.put("Pitchblende", new MaterialsBuilder().subID(873).name("Pitchblende").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(200, 210, 0, 0).locName("Pitchblende").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("Uraninite"), 3), new MaterialStack(get("Thorium"), 1), new MaterialStack(get("Lead"), 1)).build());
        MATERIALS_HASH_MAP.put("Monazite", new MaterialsBuilder().subID(520).name("Monazite").textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(50, 70, 50, 0).locName("Monazite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("RareEarth"), 1), new MaterialStack(get("Phosphate"), 1)).build()); // Wikipedia: (Ce, La, Nd, Th, Sm, Gd)PO4 Monazite also smelt-extract to Helium, it is brown like the rare earth Item Monazite sand deposits are inevitably of the monazite-(Ce) composition. Typically, the lanthanides in such monazites contain about 45ִ8% cerium, about 24% lanthanum, about 17% neodymium, about 5% praseodymium, and minor quantities of samarium, gadolinium, and yttrium. Europium concentrations tend to be low, about 0.05% Thorium content of monazite is variable and sometimes can be up to 20ֳ0%
        MATERIALS_HASH_MAP.put("Malachite", new MaterialsBuilder().subID(871).name("Malachite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(5, 95, 5, 0).locName("Malachite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(get("Copper"), 2), new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 5)).build()); // Cu2CO3(OH)2
        MATERIALS_HASH_MAP.put("Mirabilite", new MaterialsBuilder().subID(-1).name("Mirabilite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 250, 210, 0).locName("Mirabilite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Sodium"), 2), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Water"), 10), new MaterialStack(get("Oxygen"), 4)).build()); // Na2SO4 10H2O
        MATERIALS_HASH_MAP.put("Mica", new MaterialsBuilder().subID(-1).name("Mica").textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(195, 195, 205, 0).locName("Mica").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Aluminium"), 3), new MaterialStack(get("Silicon"), 3), new MaterialStack(get("Fluorine"), 2), new MaterialStack(get("Oxygen"), 10)).build()); // KAl2(AlSi3O10)(F,OH)2
        MATERIALS_HASH_MAP.put("Trona", new MaterialsBuilder().subID(-1).name("Trona").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(135, 135, 95, 0).locName("Trona").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Sodium"), 3), new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Hydrogen"), 1), new MaterialStack(get("Water"), 2), new MaterialStack(get("Oxygen"), 6)).build()); // Na3(CO3)(HCO3) 2H2O
        MATERIALS_HASH_MAP.put("Barite", new MaterialsBuilder().subID(904).name("Barite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(230, 235, 255, 0).locName("Barite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Barium"), 1), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Oxygen"), 4)).build());
        MATERIALS_HASH_MAP.put("Gypsum", new MaterialsBuilder().subID(-1).name("Gypsum").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 250, 0).locName("Gypsum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Sulfur"), 1), new MaterialStack(get("Water"), 2), new MaterialStack(get("Oxygen"), 4)).build()); // CaSO4 2H2O
        MATERIALS_HASH_MAP.put("Alunite", new MaterialsBuilder().subID(-1).name("Alunite").textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(225, 180, 65, 0).locName("Alunite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Potassium"), 1), new MaterialStack(get("Aluminium"), 3), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Hydrogen"), 6), new MaterialStack(get("Oxygen"), 14)).build()); // KAl3(SO4)2(OH)6
        MATERIALS_HASH_MAP.put("Dolomite", new MaterialsBuilder().subID(-1).name("Dolomite").textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(225, 205, 205, 0).locName("Dolomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Magnesium"), 1), new MaterialStack(get("Carbon"), 2), new MaterialStack(get("Oxygen"), 6)).build()); // CaMg(CO3)2
        MATERIALS_HASH_MAP.put("Wollastonite", new MaterialsBuilder().subID(-1).name("Wollastonite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 240, 240, 0).locName("Wollastonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Calcium"), 1), new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Oxygen"), 3)).build()); // CaSiO3
        MATERIALS_HASH_MAP.put("Zeolite", new MaterialsBuilder().subID(-1).name("Zeolite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 230, 230, 0).locName("Zeolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Sodium"), 1), new MaterialStack(get("Calcium"), 4), new MaterialStack(get("Silicon"), 27), new MaterialStack(get("Aluminium"), 9), new MaterialStack(get("Water"), 28), new MaterialStack(get("Oxygen"), 72)).build()); // NaCa4(Si27Al9)O72 28(H2O)
        MATERIALS_HASH_MAP.put("Kyanite", new MaterialsBuilder().subID(-1).name("Kyanite").textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(110, 110, 250, 0).locName("Kyanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 1), new MaterialStack(get("Oxygen"), 5)).build()); // Al2SiO5
        MATERIALS_HASH_MAP.put("Kaolinite", new MaterialsBuilder().subID(-1).name("Kaolinite").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(245, 235, 235, 0).locName("Kaolinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Aluminium"), 2), new MaterialStack(get("Silicon"), 2), new MaterialStack(get("Hydrogen"), 4), new MaterialStack(get("Oxygen"), 9)).build()); // Al2Si2O5(OH)4
        MATERIALS_HASH_MAP.put("Talc", new MaterialsBuilder().subID(902).name("Talc").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(90, 180, 90, 0).locName("Talc").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Magnesium"), 3), new MaterialStack(get("Silicon"), 4), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 12)).build()); // H2Mg3(SiO3)4
        MATERIALS_HASH_MAP.put("Soapstone", new MaterialsBuilder().subID(877).name("Soapstone").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(95, 145, 95, 0).locName("Soapstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(get("Magnesium"), 3), new MaterialStack(get("Silicon"), 4), new MaterialStack(get("Hydrogen"), 2), new MaterialStack(get("Oxygen"), 12)).build()); // H2Mg3(SiO3)4
        MATERIALS_HASH_MAP.put("Concrete", new MaterialsBuilder().subID(947).name("Concrete").textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(100, 100, 100, 0).locName("Concrete").fuelType(0).fuelPow(0).meltAt(300).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Stone"), 1)).aspects(new TC_AspectStack(TC_Aspects.TERRA, 1)).build());
        MATERIALS_HASH_MAP.put("IronMagnetic", new MaterialsBuilder().subID(354).name("IronMagnetic").textureSet(SET_MAGNETIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Magnetic Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Iron"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("SteelMagnetic", new MaterialsBuilder().subID(355).name("SteelMagnetic").textureSet(SET_MAGNETIC).toolSpeed(6.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Magnetic Steel").fuelType(0).fuelPow(0).meltAt(1000).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Steel"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build());
        MATERIALS_HASH_MAP.put("NeodymiumMagnetic", new MaterialsBuilder().subID(356).name("NeodymiumMagnetic").textureSet(SET_MAGNETIC).toolSpeed(7.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Magnetic Neodymium").fuelType(0).fuelPow(0).meltAt(1297).blastAt(1297).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(get("Neodymium"), 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 3)).build());
        MATERIALS_HASH_MAP.put("TungstenCarbide", new MaterialsBuilder().subID(370).name("TungstenCarbide").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(5120).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(24, 28, 48, 0).locName("Tungsten Carbide").fuelType(0).fuelPow(0).meltAt(2460).blastAt(2460).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(get("Carbon"), 1), new MaterialStack(get("Tungsten"), 1)).build());
        MATERIALS_HASH_MAP.put("VanadiumSteel", new MaterialsBuilder().subID(371).name("VanadiumSteel").textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(2560).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(192, 192, 192, 0).locName("Vanadium Steel").fuelType(0).fuelPow(0).meltAt(1453).blastAt(1453).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Vanadium"), 1), new MaterialStack(get("Chrome"), 1), new MaterialStack(get("Steel"), 7)).build());
        MATERIALS_HASH_MAP.put("HSSG", new MaterialsBuilder().subID(372).name("HSSG").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(7680).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(153, 153, 0, 0).locName("HSS-G").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(get("TungstenSteel"), 5), new MaterialStack(get("Chrome"), 1), new MaterialStack(get("Molybdenum"), 2), new MaterialStack(get("Vanadium"), 1)).build());
        MATERIALS_HASH_MAP.put("HSSE", new MaterialsBuilder().subID(373).name("HSSE").textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(10240).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(51, 102, 0, 0).locName("HSS-E").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(get("HSSG"), 6), new MaterialStack(get("Cobalt"), 1), new MaterialStack(get("Manganese"), 1), new MaterialStack(get("Silicon"), 1)).build());
        MATERIALS_HASH_MAP.put("HSSS", new MaterialsBuilder().subID(374).name("HSSS").textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(7680).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(102, 0, 51, 0).locName("HSS-S").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(get("HSSG"), 6), new MaterialStack(get("Iridium"), 2), new MaterialStack(get("Osmium"), 1)).build());
        MATERIALS_HASH_MAP.put("Vibranium", new MaterialsBuilder().subID(970).name("Vibranium").textureSet(SET_EMERALD).toolSpeed(100.0F).toolDura(512).toolQual(6).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 128, 255, 127).locName("Vibranium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 10), new TC_AspectStack(TC_Aspects.VITREUS, 10)).build());
        MATERIALS_HASH_MAP.put("PurpleAlloy", new MaterialsBuilder().subID(521).name("PurpleAlloy").textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(225, 80, 232, 0).locName("Purple Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(get("BlueAlloy"), 1), new MaterialStack(get("RedAlloy"), 1)).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 3)).build());
    }

    /*
     * Materials which are renamed automatically
     */
    private static void initMaterialAliases() {
        MATERIALS_HASH_MAP.put("IridiumAndSodiumOxide", new MaterialsBuilder().subID(-1).name("IridiumAndSodiumOxide").unifiable(false).locName(get("IridiumSodiumOxide").getDefaultLocalName()).matInto(get("IridiumSodiumOxide").getMaterialInto()).toolTip(get("IridiumSodiumOxide").getToolTip()).textureSet(SET_NONE).build());
    }
    @Deprecated
    private static Materials Palygorskite = new Materials(get("FullersEarth"), false);
    @Deprecated
    private static Materials Adamantine = new Materials(get("Adamantium"), true);
    @Deprecated
    private static Materials Ashes = new Materials(get("Ash"), false);
    @Deprecated
    private static Materials DarkAshes = new Materials(get("DarkAsh"), false);
    @Deprecated
    private static Materials Abyssal = new Materials(get("Basalt"), false);
    @Deprecated
    private static Materials Adamant = new Materials(get("Adamantium"), true);
    @Deprecated
    private static Materials AluminumBrass = new Materials(get("AluminiumBrass"), false);
    @Deprecated
    private static Materials Aluminum = new Materials(get("Aluminium"), false);
    @Deprecated
    private static Materials NaturalAluminum = new Materials(get("Aluminium"), false);
    @Deprecated
    private static Materials NaturalAluminium = new Materials(get("Aluminium"), false);
    @Deprecated
    private static Materials Americum = new Materials(get("Americium"), false);
    @Deprecated
    private static Materials Beryl = new Materials(get("Emerald"), false);
    @Deprecated
    private static Materials BlackGranite = new Materials(get("GraniteBlack"), false);
    @Deprecated
    private static Materials CalciumCarbonate = new Materials(get("Calcite"), false);
    @Deprecated
    private static Materials CreosoteOil = new Materials(get("Creosote"), false);
    @Deprecated
    private static Materials Chromium = new Materials(get("Chrome"), false);
    @Deprecated
    private static Materials Diesel = new Materials(get("Fuel"), false);
    @Deprecated
    private static Materials Enderpearl = new Materials(get("EnderPearl"), false);
    @Deprecated
    private static Materials Endereye = new Materials(get("EnderEye"), false);
    @Deprecated
    private static Materials EyeOfEnder = new Materials(get("EnderEye"), false);
    @Deprecated
    private static Materials Eyeofender = new Materials(get("EnderEye"), false);
    @Deprecated
    private static Materials Flour = new Materials(get("Wheat"), false);
    @Deprecated
    private static Materials Meat = new Materials(get("MeatRaw"), false);
    @Deprecated
    private static Materials Garnet = new Materials(get("GarnetRed"), true);
    @Deprecated
    private static Materials Granite = new Materials(get("GraniteBlack"), false);
    @Deprecated
    private static Materials Goethite = new Materials(get("BrownLimonite"), false);
    @Deprecated
    private static Materials Kalium = new Materials(get("Potassium"), false);
    @Deprecated
    private static Materials Lapislazuli = new Materials(get("Lapis"), false);
    @Deprecated
    private static Materials LapisLazuli = new Materials(get("Lapis"), false);
    @Deprecated
    private static Materials Monazit = new Materials(get("Monazite"), false);
    @Deprecated
    private static Materials Natrium = new Materials(get("Sodium"), false);
    @Deprecated
    private static Materials Mythril = new Materials(get("Mithril"), false);
    @Deprecated
    private static Materials NitroDiesel = new Materials(get("NitroFuel"), false);
    @Deprecated
    private static Materials Naquadriah = new Materials(get("Naquadria"), false);
    @Deprecated
    private static Materials Obby = new Materials(get("Obsidian"), false);
    @Deprecated
    private static Materials Peridot = new Materials(get("Olivine"), true);
    @Deprecated
    private static Materials Phosphorite = new Materials(get("Phosphorus"), true);
    @Deprecated
    private static Materials Quarried = new Materials(get("Marble"), false);
    @Deprecated
    private static Materials Quicksilver = new Materials(get("Mercury"), true);
    @Deprecated
    private static Materials QuickSilver = new Materials(get("Mercury"), false);
    @Deprecated
    private static Materials RedRock = new Materials(get("Redrock"), false);
    @Deprecated
    private static Materials RefinedIron = new Materials(get("Iron"), false);
    @Deprecated
    private static Materials RedGranite = new Materials(get("GraniteRed"), false);
    @Deprecated
    private static Materials Sheldonite = new Materials(get("Cooperite"), false);
    @Deprecated
    private static Materials Soulsand = new Materials(get("SoulSand"), false);
    @Deprecated
    private static Materials Titan = new Materials(get("Titanium"), false);
    @Deprecated
    private static Materials Uran = new Materials(get("Uranium"), false);
    @Deprecated
    private static Materials Wolframite = new Materials(get("Tungstate"), false);
    @Deprecated
    private static Materials Wolframium = new Materials(get("Tungsten"), false);
    @Deprecated
    private static Materials Wolfram = new Materials(get("Tungsten"), false);

    static {
        initDirectElements();
        initRandomMaterialOnes();
        initIdontCareSection();
        initUnknownMaterialComponents();
        initCircuitryBaterisAndOtherTechnicalThins();
        initUndeterminedExactComponents();
        initSomeOther();
        initFirstDegreeCompounds();
        initSecondDegreeCompounds();
        initMaterialAliases();
        initSubTags();
        Materials anyIron = get("AnyIron");
        get("Iron").mOreReRegistrations.add(anyIron);
        get("PigIron").mOreReRegistrations.add(anyIron);
        get("WroughtIron").mOreReRegistrations.add(anyIron);

        Materials anyCopper = get("AnyCopper");
        get("Copper").mOreReRegistrations.add(anyCopper);
        get("AnnealedCopper").mOreReRegistrations.add(anyCopper);

        get("Bronze").mOreReRegistrations.add(get("AnyBronze"));

        Materials wood = get("Wood");
        get("Peanutwood").setMaceratingInto(wood);
        get("WoodSealed").setMaceratingInto(wood);
        get("NetherBrick").setMaceratingInto(get("Netherrack"));

        Materials neodymium = get("Neodymium");
        get("NeodymiumMagnetic").setSmeltingInto(neodymium).setMaceratingInto(neodymium).setArcSmeltingInto(neodymium);
        Materials steel = get("Steel");
        get("SteelMagnetic").setSmeltingInto(steel).setMaceratingInto(steel).setArcSmeltingInto(steel);
        Materials iron = get("Iron");
        Materials wroughtIron = get("WroughtIron");
        iron.setSmeltingInto(iron).setMaceratingInto(iron).setArcSmeltingInto(wroughtIron);
        get("AnyIron").setSmeltingInto(iron).setMaceratingInto(iron).setArcSmeltingInto(wroughtIron);
        get("PigIron").setSmeltingInto(iron).setMaceratingInto(iron).setArcSmeltingInto(wroughtIron);
        wroughtIron.setSmeltingInto(iron).setMaceratingInto(iron).setArcSmeltingInto(wroughtIron);
        get("IronMagnetic").setSmeltingInto(iron).setMaceratingInto(iron).setArcSmeltingInto(wroughtIron);
        Materials copper = get("Copper");
        Materials annealedCopper = get("AnnealedCopper");
        copper.setSmeltingInto(copper).setMaceratingInto(copper).setArcSmeltingInto(annealedCopper);
        get("AnyCopper").setSmeltingInto(copper).setMaceratingInto(copper).setArcSmeltingInto(annealedCopper);
        annealedCopper.setSmeltingInto(copper).setMaceratingInto(copper).setArcSmeltingInto(annealedCopper);
        get("Netherrack").setSmeltingInto(get("NetherBrick"));
        get("MeatRaw").setSmeltingInto(get("MeatCooked"));
        get("Sand").setSmeltingInto(get("Glass"));
        get("Ice").setSmeltingInto(get("Water"));
        get("Snow").setSmeltingInto(get("Water"));
    }

    static {
        get("Mercury").add(SubTag.SMELTING_TO_GEM);
        get("Cinnabar").setDirectSmelting(get("Mercury")).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT).add(SubTag.SMELTING_TO_GEM);
        /*get("Tetrahedrite").setDirectSmelting(copper).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("Chalcopyrite").setDirectSmelting(copper).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("Malachite").setDirectSmelting(copper).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("Pentlandite").setDirectSmelting(get("Nickel")).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("Sphalerite").setDirectSmelting(get("Zinc")).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("Pyrite").setDirectSmelting(iron).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("BasalticMineralSand").setDirectSmelting(iron).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("GraniticMineralSand").setDirectSmelting(iron).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("YellowLimonite").setDirectSmelting(iron).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        get("BrownLimonite").setDirectSmelting(iron);
        get("BandedIron").setDirectSmelting(iron);
        Materials tin = get("Tin);
        get("Cassiterite").setDirectSmelting(get("tin");
        get("CassiteriteSand").setDirectSmelting(tin);
        get("Chromite")..setDirectSmelting(get("Chrome"));
        get("Garnierite").setDirectSmelting(get("Nickel"));
        get("Cobaltite").setDirectSmelting(get("Cobalt"));
        get("Stibnite").setDirectSmelting(get("Antimony"));
        get("Cooperite").setDirectSmelting(get("Platinum"));
        get("Pyrolusite").setDirectSmelting(get("Manganese"));
        get("Magnesite").setDirectSmelting(get("Magnesium"));
        get("Molybdenite").setDirectSmelting(get("Molybdenum"));*/
    }

    static {
        get("Amber").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedAir").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedFire").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedEarth").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedWater").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedEntropy").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedOrder").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedVis").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("InfusedDull").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("Salt").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("RockSalt").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("Scheelite").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("Tungstate").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("Cassiterite").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("CassiteriteSand").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("NetherQuartz").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("CertusQuartz").setOreMultiplier(2).setSmeltingMultiplier(2);
        get("Phosphorus").setOreMultiplier(3).setSmeltingMultiplier(3);
        get("Saltpeter").setOreMultiplier(4).setSmeltingMultiplier(4);
        get("Apatite").setOreMultiplier(4).setSmeltingMultiplier(4).setByProductMultiplier(2);
        get("Teslatite").setOreMultiplier(5).setSmeltingMultiplier(5);
        get("Redstone").setOreMultiplier(5).setSmeltingMultiplier(5);
        get("Glowstone").setOreMultiplier(5).setSmeltingMultiplier(5);
        get("Lapis").setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        get("Sodalite").setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        get("Lazurite").setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        get("Monazite").setOreMultiplier(8).setSmeltingMultiplier(8).setByProductMultiplier(2);
    }

    static {
        get("Plastic").setEnchantmentForTools(Enchantment.knockback, 1);
        get("Rubber").setEnchantmentForTools(Enchantment.knockback, 2);
        get("InfusedAir").setEnchantmentForTools(Enchantment.knockback, 2);

        get("IronWood").setEnchantmentForTools(Enchantment.fortune, 1);
        get("Steeleaf").setEnchantmentForTools(Enchantment.fortune, 2);
        get("Midasium").setEnchantmentForTools(Enchantment.fortune, 2);
        get("Mithril").setEnchantmentForTools(Enchantment.fortune, 3);
        get("Vinteum").setEnchantmentForTools(Enchantment.fortune, 1);
        get("Thaumium").setEnchantmentForTools(Enchantment.fortune, 2);
        get("InfusedWater").setEnchantmentForTools(Enchantment.fortune, 3);
        get("Vibranium").setEnchantmentForTools(Enchantment.fortune, 5);

        get("Flint").setEnchantmentForTools(Enchantment.fireAspect, 1);
        get("DarkIron").setEnchantmentForTools(Enchantment.fireAspect, 2);
        get("Firestone").setEnchantmentForTools(Enchantment.fireAspect, 3);
        get("FierySteel").setEnchantmentForTools(Enchantment.fireAspect, 3);
        get("Pyrotheum").setEnchantmentForTools(Enchantment.fireAspect, 3);
        get("Blaze").setEnchantmentForTools(Enchantment.fireAspect, 3);
        get("InfusedFire").setEnchantmentForTools(Enchantment.fireAspect, 3);

        get("Amber").setEnchantmentForTools(Enchantment.silkTouch, 1);
        get("EnderPearl").setEnchantmentForTools(Enchantment.silkTouch, 1);
        get("Enderium").setEnchantmentForTools(Enchantment.silkTouch, 1);
        get("NetherStar").setEnchantmentForTools(Enchantment.silkTouch, 1);
        get("InfusedOrder").setEnchantmentForTools(Enchantment.silkTouch, 1);

        get("BlackBronze").setEnchantmentForTools(Enchantment.smite, 2);
        get("Gold").setEnchantmentForTools(Enchantment.smite, 3);
        get("RoseGold").setEnchantmentForTools(Enchantment.smite, 4);
        get("Platinum").setEnchantmentForTools(Enchantment.smite, 5);
        get("InfusedVis").setEnchantmentForTools(Enchantment.smite, 5);

        get("Lead").setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        get("Nickel").setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        get("Invar").setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        get("Antimony").setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        get("BatteryAlloy").setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        get("Bismuth").setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        get("BismuthBronze").setEnchantmentForTools(Enchantment.baneOfArthropods, 5);
        get("InfusedEarth").setEnchantmentForTools(Enchantment.baneOfArthropods, 5);

        get("Iron").setEnchantmentForTools(Enchantment.sharpness, 1);
        get("Bronze").setEnchantmentForTools(Enchantment.sharpness, 1);
        get("Brass").setEnchantmentForTools(Enchantment.sharpness, 2);
        get("HSLA").setEnchantmentForTools(Enchantment.sharpness, 2);
        get("Steel").setEnchantmentForTools(Enchantment.sharpness, 2);
        get("WroughtIron").setEnchantmentForTools(Enchantment.sharpness, 2);
        get("StainlessSteel").setEnchantmentForTools(Enchantment.sharpness, 3);
        get("Knightmetal").setEnchantmentForTools(Enchantment.sharpness, 3);
        get("ShadowIron").setEnchantmentForTools(Enchantment.sharpness, 3);
        get("ShadowSteel").setEnchantmentForTools(Enchantment.sharpness, 4);
        get("BlackSteel").setEnchantmentForTools(Enchantment.sharpness, 4);
        get("RedSteel").setEnchantmentForTools(Enchantment.sharpness, 4);
        get("BlueSteel").setEnchantmentForTools(Enchantment.sharpness, 5);
        get("DamascusSteel").setEnchantmentForTools(Enchantment.sharpness, 5);
        get("InfusedEntropy").setEnchantmentForTools(Enchantment.sharpness, 5);
        get("TungstenCarbide").setEnchantmentForTools(Enchantment.sharpness, 5);
        get("HSSE").setEnchantmentForTools(Enchantment.sharpness, 5);
        get("HSSG").setEnchantmentForTools(Enchantment.sharpness, 4);
        get("HSSS").setEnchantmentForTools(Enchantment.sharpness, 5);

        get("InfusedAir").setEnchantmentForArmors(Enchantment.respiration, 3);

        get("InfusedFire").setEnchantmentForArmors(Enchantment.featherFalling, 4);

        get("Steeleaf").setEnchantmentForArmors(Enchantment.protection, 2);
        get("Knightmetal").setEnchantmentForArmors(Enchantment.protection, 1);
        get("InfusedEarth").setEnchantmentForArmors(Enchantment.protection, 4);

        get("InfusedEntropy").setEnchantmentForArmors(Enchantment.thorns, 3);

        get("InfusedWater").setEnchantmentForArmors(Enchantment.aquaAffinity, 1);
        get("IronWood").setEnchantmentForArmors(Enchantment.aquaAffinity, 1);

        get("InfusedOrder").setEnchantmentForArmors(Enchantment.projectileProtection, 4);

        get("InfusedDull").setEnchantmentForArmors(Enchantment.blastProtection, 4);

        get("InfusedVis").setEnchantmentForArmors(Enchantment.protection, 4);

        get("Vibranium").setEnchantmentForTools(Enchantment.looting, 5);
    }

    static {
        get("FryingOilHot").setHeatDamage(1.0F);
        get("Lava").setHeatDamage(3.0F);
        get("Firestone").setHeatDamage(5.0F);
        get("Pyrotheum").setHeatDamage(5.0F);

        get("Chalcopyrite").addOreByProducts(get("Pyrite"), get("Cobalt"), get("Cadmium"), get("Gold"));
        get("Sphalerite").addOreByProducts(get("GarnetYellow"), get("Cadmium"), get("Gallium"), get("Zinc"));
        get("MeteoricIron").addOreByProducts(get("Iron"), get("Nickel"), get("Iridium"), get("Platinum"));
        get("GlauconiteSand").addOreByProducts(get("Sodium"), get("Aluminium"), get("Iron"));
        get("Glauconite").addOreByProducts(get("Sodium"), get("Aluminium"), get("Iron"));
        get("Vermiculite").addOreByProducts(get("Iron"), get("Aluminium"), get("Magnesium"));
        get("FullersEarth").addOreByProducts(get("Aluminium"), get("Silicon"), get("Magnesium"));
        get("Bentonite").addOreByProducts(get("Aluminium"), get("Calcium"), get("Magnesium"));
        get("Uraninite").addOreByProducts(get("Uranium"), get("Thorium"), get("Uranium235"));
        get("Pitchblende").addOreByProducts(get("Thorium"), get("Uranium"), get("Lead"));
        get("Galena").addOreByProducts(get("Sulfur"), get("Silver"), get("Lead"));
        get("Lapis").addOreByProducts(get("Lazurite"), get("Sodalite"), get("Pyrite"));
        get("Pyrite").addOreByProducts(get("Sulfur"), get("Phosphorus"), get("Iron"));
        get("Copper").addOreByProducts(get("Cobalt"), get("Gold"), get("Nickel"));
        get("Nickel").addOreByProducts(get("Cobalt"), get("Platinum"), get("Iron"));
        get("GarnetRed").addOreByProducts(get("Spessartine"), get("Pyrope"), get("Almandine"));
        get("GarnetYellow").addOreByProducts(get("Andradite"), get("Grossular"), get("Uvarovite"));
        get("Cooperite").addOreByProducts(get("Palladium"), get("Nickel"), get("Iridium"));
        get("Cinnabar").addOreByProducts(get("Redstone"), get("Sulfur"), get("Glowstone"));
        get("Tantalite").addOreByProducts(get("Manganese"), get("Niobium"), get("Tantalum"));
        get("Pollucite").addOreByProducts(get("Caesium"), get("Aluminium"), get("Rubidium"));
        get("Chrysotile").addOreByProducts(get("Asbestos"), get("Silicon"), get("Magnesium"));
        get("Asbestos").addOreByProducts(get("Asbestos"), get("Silicon"), get("Magnesium"));
        get("Pentlandite").addOreByProducts(get("Iron"), get("Sulfur"), get("Cobalt"));
        get("Uranium").addOreByProducts(get("Lead"), get("Uranium235"), get("Thorium"));
        get("Scheelite").addOreByProducts(get("Manganese"), get("Molybdenum"), get("Calcium"));
        get("Tungstate").addOreByProducts(get("Manganese"), get("Silver"), get("Lithium"));
        get("Bauxite").addOreByProducts(get("Grossular"), get("Rutile"), get("Gallium"));
        get("QuartzSand").addOreByProducts(get("CertusQuartz"), get("Quartzite"), get("Barite"));
        get("Quartzite").addOreByProducts(get("CertusQuartz"), get("Barite"));
        get("CertusQuartz").addOreByProducts(get("Quartzite"), get("Barite"));
        get("Redstone").addOreByProducts(get("Cinnabar"), get("RareEarth"), get("Glowstone"));
        get("Monazite").addOreByProducts(get("Thorium"), get("Neodymium"), get("RareEarth"));
        get("Forcicium").addOreByProducts(get("Thorium"), get("Neodymium"), get("RareEarth"));
        get("Forcillium").addOreByProducts(get("Thorium"), get("Neodymium"), get("RareEarth"));
        get("Malachite").addOreByProducts(get("Copper"), get("BrownLimonite"), get("Calcite"));
        get("YellowLimonite").addOreByProducts(get("Nickel"), get("BrownLimonite"), get("Cobalt"));
        get("BrownLimonite").addOreByProducts(get("Malachite"), get("YellowLimonite"));
        get("Neodymium").addOreByProducts(get("Monazite"), get("RareEarth"));
        get("Bastnasite").addOreByProducts(get("Neodymium"), get("RareEarth"));
        get("Glowstone").addOreByProducts(get("Redstone"), get("Gold"));
        get("Zinc").addOreByProducts(get("Tin"), get("Gallium"));
        get("Tungsten").addOreByProducts(get("Manganese"), get("Molybdenum"));
        get("Diatomite").addOreByProducts(get("BandedIron"), get("Sapphire"));
        get("Iron").addOreByProducts(get("Nickel"), get("Tin"));
        get("Lepidolite").addOreByProducts(get("Lithium"), get("Caesium"));
        get("Gold").addOreByProducts(get("Copper"), get("Nickel"));
        get("Tin").addOreByProducts(get("Iron"), get("Zinc"));
        get("Antimony").addOreByProducts(get("Zinc"), get("Iron"));
        get("Silver").addOreByProducts(get("Lead"), get("Sulfur"));
        get("Lead").addOreByProducts(get("Silver"), get("Sulfur"));
        get("Thorium").addOreByProducts(get("Uranium"), get("Lead"));
        get("Plutonium").addOreByProducts(get("Uranium"), get("Lead"));
        get("Electrum").addOreByProducts(get("Gold"), get("Silver"));
        get("Bronze").addOreByProducts(get("Copper"), get("Tin"));
        get("Brass").addOreByProducts(get("Copper"), get("Zinc"));
        get("Coal").addOreByProducts(get("Lignite"), get("Thorium"));
        get("Ilmenite").addOreByProducts(get("Iron"), get("Rutile"));
        get("Manganese").addOreByProducts(get("Chrome"), get("Iron"));
        get("Sapphire").addOreByProducts(get("Aluminium"), get("GreenSapphire"));
        get("GreenSapphire").addOreByProducts(get("Aluminium"), get("Sapphire"));
        get("Platinum").addOreByProducts(get("Nickel"), get("Iridium"));
        get("Emerald").addOreByProducts(get("Beryllium"), get("Aluminium"));
        get("Olivine").addOreByProducts(get("Pyrope"), get("Magnesium"));
        get("Chrome").addOreByProducts(get("Iron"), get("Magnesium"));
        get("Chromite").addOreByProducts(get("Iron"), get("Magnesium"));
        get("Tetrahedrite").addOreByProducts(get("Antimony"), get("Zinc"));
        get("GarnetSand").addOreByProducts(get("GarnetRed"), get("GarnetYellow"));
        get("Magnetite").addOreByProducts(get("Iron"), get("Gold"));
        get("GraniticMineralSand").addOreByProducts(get("GraniteBlack"), get("Magnetite"));
        get("BasalticMineralSand").addOreByProducts(get("Basalt"), get("Magnetite"));
        get("Basalt").addOreByProducts(get("Olivine"), get("DarkAsh"));
        get("VanadiumMagnetite").addOreByProducts(get("Magnetite"), get("Vanadium"));
        get("Lazurite").addOreByProducts(get("Sodalite"), get("Lapis"));
        get("Sodalite").addOreByProducts(get("Lazurite"), get("Lapis"));
        get("Spodumene").addOreByProducts(get("Aluminium"), get("Lithium"));
        get("Ruby").addOreByProducts(get("Chrome"), get("GarnetRed"));
        get("Phosphorus").addOreByProducts(get("Apatite"), get("Phosphate"));
        get("Iridium").addOreByProducts(get("Platinum"), get("Osmium"));
        get("Pyrope").addOreByProducts(get("GarnetRed"), get("Magnesium"));
        get("Almandine").addOreByProducts(get("GarnetRed"), get("Aluminium"));
        get("Spessartine").addOreByProducts(get("GarnetRed"), get("Manganese"));
        get("Andradite").addOreByProducts(get("GarnetYellow"), get("Iron"));
        get("Grossular").addOreByProducts(get("GarnetYellow"), get("Calcium"));
        get("Uvarovite").addOreByProducts(get("GarnetYellow"), get("Chrome"));
        get("Calcite").addOreByProducts(get("Andradite"), get("Malachite"));
        get("NaquadahEnriched").addOreByProducts(get("Naquadah"), get("Naquadria"));
        get("Naquadah").addOreByProducts(get("NaquadahEnriched"));
        get("Pyrolusite").addOreByProducts(get("Manganese"));
        get("Molybdenite").addOreByProducts(get("Molybdenum"));
        get("Stibnite").addOreByProducts(get("Antimony"));
        get("Garnierite").addOreByProducts(get("Nickel"));
        get("Lignite").addOreByProducts(get("Coal"));
        get("Diamond").addOreByProducts(get("Graphite"));
        get("Beryllium").addOreByProducts(get("Emerald"));
        get("Apatite").addOreByProducts(get("Phosphorus"));
        get("Teslatite").addOreByProducts(get("Diamond"));
        get("Magnesite").addOreByProducts(get("Magnesium"));
        get("NetherQuartz").addOreByProducts(get("Netherrack"));
        get("PigIron").addOreByProducts(get("Iron"));
        get("DeepIron").addOreByProducts(get("Iron"));
        get("ShadowIron").addOreByProducts(get("Iron"));
        get("DarkIron").addOreByProducts(get("Iron"));
        get("MeteoricIron").addOreByProducts(get("Iron"));
        get("Steel").addOreByProducts(get("Iron"));
        get("HSLA").addOreByProducts(get("Iron"));
        get("Mithril").addOreByProducts(get("Platinum"));
        get("Midasium").addOreByProducts(get("Gold"));
        get("AstralSilver").addOreByProducts(get("Silver"));
        get("Graphite").addOreByProducts(get("Carbon"));
        get("Netherrack").addOreByProducts(get("Sulfur"));
        get("Flint").addOreByProducts(get("Obsidian"));
        get("Cobaltite").addOreByProducts(get("Cobalt"));
        get("Cobalt").addOreByProducts(get("Cobaltite"));
        get("Sulfur").addOreByProducts(get("Sulfur"));
        get("Saltpeter").addOreByProducts(get("Saltpeter"));
        get("Endstone").addOreByProducts(get("Helium_3"));
        get("Osmium").addOreByProducts(get("Iridium"));
        get("Magnesium").addOreByProducts(get("Olivine"));
        get("Aluminium").addOreByProducts(get("Bauxite"));
        get("Titanium").addOreByProducts(get("Almandine"));
        get("Obsidian").addOreByProducts(get("Olivine"));
        get("Ash").addOreByProducts(get("Carbon"));
        get("DarkAsh").addOreByProducts(get("Carbon"));
        get("Redrock").addOreByProducts(get("Clay"));
        get("Marble").addOreByProducts(get("Calcite"));
        get("Clay").addOreByProducts(get("Clay"));
        get("Cassiterite").addOreByProducts(get("Tin"));
        get("CassiteriteSand").addOreByProducts(get("Tin"));
        get("GraniteBlack").addOreByProducts(get("Biotite"));
        get("GraniteRed").addOreByProducts(get("PotassiumFeldspar"));
        get("Phosphate").addOreByProducts(get("Phosphor"));
        get("Phosphor").addOreByProducts(get("Phosphate"));
        get("Tanzanite").addOreByProducts(get("Opal"));
        get("Opal").addOreByProducts(get("Tanzanite"));
        get("Amethyst").addOreByProducts(get("Amethyst"));
        get("FoolsRuby").addOreByProducts(get("Jasper"));
        get("Amber").addOreByProducts(get("Amber"));
        get("Topaz").addOreByProducts(get("BlueTopaz"));
        get("BlueTopaz").addOreByProducts(get("Topaz"));
        get("Niter").addOreByProducts(get("Saltpeter"));
        get("Vinteum").addOreByProducts(get("Vinteum"));
        get("Dilithium").addOreByProducts(get("Dilithium"));
        get("Neutronium").addOreByProducts(get("Neutronium"));
        get("Lithium").addOreByProducts(get("Lithium"));
        get("Silicon").addOreByProducts(get("SiliconDioxide"));
        get("Salt").addOreByProducts(get("RockSalt"));
        get("RockSalt").addOreByProducts(get("Salt"));

        get("Glue").setToolTip("No Horses were harmed for the Production");
        get("UUAmplifier").setToolTip("Accelerates the Mass Fabricator");
        get("LiveRoot").setToolTip(EMPTY_STRING);
        get("WoodSealed").setToolTip(EMPTY_STRING);
        get("Wood").setToolTip(EMPTY_STRING);
        get("FoolsRuby").setToolTip(get("Ruby").getToolTip());

        get("Naquadah").mMoltenColor.setARGB(0x00ff00);
        get("NaquadahEnriched").mMoltenColor.setARGB(0x40ff40);
        get("Naquadria").mMoltenColor.setARGB(0x80ff80);

        get("NaquadahEnriched").setToolTip("Nq+");
        get("Naquadah").setToolTip("Nq");
        get("Naquadria").setToolTip("NqX");
    }

    private int mMetaItemSubID;
    private GT_Color mSolidColor = new GT_Color(0x00ffffff);
    private GT_Color mMoltenColor = new GT_Color(0x00ffffff);
    private TextureSet mTextureSet;
    private boolean mUnificatable;
    private Materials mMaterialInto;
    private List<MaterialStack> mMaterialStackList = new ArrayList<>();
    private List<Materials> mOreByProducts = new ArrayList<>();
    private List<Materials> mOreReRegistrations = new ArrayList<>();
    private List<TC_AspectStack> mAspects = new ArrayList<>();
    private List<ItemStack> mMaterialItems = new ArrayList<>();
    private Collection<SubTag> mSubTags = new HashSet<>();
    private Enchantment mEnchantmentTools = null;
    private Enchantment mEnchantmentArmors = null;
    private byte mEnchantmentToolsLevel = 0;
    private byte mEnchantmentArmorsLevel = 0;
    private boolean mBlastFurnaceRequired = false;
    private float mToolSpeed = 1.0F;
    private float mHeatDamage = 0.0F;
    private String mChemicalFormula = "?";
    private String mName = "null";
    private String mDefaultLocalName = "null";
    private Dyes mColor = Dyes._NULL;
    private short mMeltingPoint = 0;
    private short mBlastFurnaceTemp = 0;
    private int mTypes = 0;
    private int mDurability = 16;
    private int mFuelPower = 0;
    private int mFuelType = 0;
    private int mExtraData = 0;
    private int mOreValue = 0;
    private int mOreMultiplier = 1;
    private int mByProductMultiplier = 1;
    private int mSmeltingMultiplier = 1;
    private long mDensity = MATERIAL_UNIT;
    private Element mElement = null;
    private Materials mDirectSmelting = this;
    private Materials mOreReplacement = this;
    private Materials mMacerateInto = this;
    private Materials mSmeltInto = this;
    private Materials mArcSmeltInto = this;
    private Materials mHandleMaterial = this;
    private byte mToolQuality = 0;
    private Fluid mSolid = null;
    private Fluid mFluid = null;
    private Fluid mGas = null;
    private Fluid mPlasma = null;
    /**
     * The Fluid is used as standard Unit for Molten Materials. 1296 is a Molten Block, what means 144 is one Material Unit worth
     */
    private Fluid mStandardMoltenFluid = null;

    Materials() {
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID  The meta-sub identifier for items
     * @param aTextureSet     The {@link TextureSet} textures
     * @param aToolSpeed      The Speed of Tools
     * @param aToolDurability The Durability of Tools
     * @param aToolQuality    The Quality of Tools
     * @param aUnifiable      If Unifiable
     * @throws IllegalArgumentException if SubID already assigned
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aToolDurability, int aToolQuality, boolean aUnifiable) {
        MATERIALS_HASH_MAP.put(this.name(), this);
        setUnifiable(aUnifiable);
        setMaterialInto(this);
        setSubID(aMetaItemSubID);
        setToolQuality((byte) aToolQuality);
        setToolDurability(aToolDurability);
        setToolSpeed(aToolSpeed);
        setTextureSet(aTextureSet);
        if (aMetaItemSubID >= 0) {
            if (GregTech_API.sGeneratedMaterials[aMetaItemSubID] == null) {
                GregTech_API.sGeneratedMaterials[aMetaItemSubID] = this;
            } else {
                throw new IllegalArgumentException("The Index " + aMetaItemSubID + " is already used!");
            }
        }
    }

    /**
     * Constructs an aliased {@link Materials}
     *
     * @param aMaterialInto       The {@link Materials} to unify into
     * @param aReRegisterIntoThis The {@link Materials} to re-register into
     * @deprecated by {@link MaterialsBuilder}
     */
    @Deprecated
    public Materials(Materials aMaterialInto, boolean aReRegisterIntoThis) {
        setUnifiable(false);
        setDefaultLocalName(aMaterialInto.getDefaultLocalName());
        setMaterialInto(aMaterialInto.getMaterialInto());
        if (getMaterialInto() != null
                && getMaterialInto().getOreReRegistrations() != null
                && aReRegisterIntoThis) {
            getMaterialInto().getOreReRegistrations().add(this);
        }
        setToolTip(aMaterialInto.getToolTip());
        setSubID(-1);
        setTextureSet(SET_NONE);
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID        The meta-sub identifier for items
     * @param aTextureSet           The {@link TextureSet} textures
     * @param aToolSpeed            The tool speed multiplier
     * @param aDurability           The durability
     * @param aToolQuality          The tool quality
     * @param aTypes                The flags to enable item types
     * @param aR                    The main color red component
     * @param aG                    The main color green component
     * @param aB                    The main color blue component
     * @param aA                    The main color alpha component
     * @param aLocalName            The default localized name
     * @param aFuelType             The fuel typ identifier
     * @param aFuelPower            The fuel value
     * @param aMeltingPoint         The melting point
     * @param aBlastFurnaceTemp     The blast furnace temperature
     * @param aBlastFurnaceRequired If smelting requires a blast furnace
     * @param aTransparent          If it is transparent
     * @param aOreValue             The ore value
     * @param aDensityMultiplier    The ore density multiplier
     * @param aDensityDivider       The ore density divider
     * @param aDye                  The {@link Dyes} color
     * @throws IllegalArgumentException if SubID already assigned
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S00107", // 21 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, true);
        setName(getDefaultLocalName().replaceAll(" ", EMPTY_STRING));
        setDefaultLocalName(aLocalName);
        setMeltingPoint((short) aMeltingPoint);
        setBlastFurnaceTemp((short) aBlastFurnaceTemp);
        setBlastFurnaceRequired(aBlastFurnaceRequired);
        if (aTransparent) add(TRANSPARENT);
        setFuelPower(aFuelPower);
        setFuelType(aFuelType);
        setOreValue(aOreValue);
        setDensity((MATERIAL_UNIT * aDensityMultiplier) / aDensityDivider);
        if (aDye == null) {
            setDye(Dyes._NULL);
        } else {
            setDye(aDye);
        }
        add(HAS_COLOR);
        getColor().setRGBA(aR, aG, aB, aA);
        setTypes(aTypes);
        if ((getTypes() & 2) != 0) {
            add(SMELTING_TO_FLUID);
        }
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID        The meta-sub identifier for items
     * @param aTextureSet           The {@link TextureSet} textures
     * @param aToolSpeed            The tool speed multiplier
     * @param aDurability           The durability
     * @param aToolQuality          The tool quality
     * @param aTypes                The flags to enable item types
     * @param aR                    The main color red component
     * @param aG                    The main color green component
     * @param aB                    The main color blue component
     * @param aA                    The main color alpha component
     * @param aLocalName            The default localized name
     * @param aFuelType             The fuel typ identifier
     * @param aFuelPower            The fuel value
     * @param aMeltingPoint         The melting point
     * @param aBlastFurnaceTemp     The blast furnace temperature
     * @param aBlastFurnaceRequired If smelting requires a blast furnace
     * @param aTransparent          If it is transparent
     * @param aOreValue             The ore value
     * @param aDensityMultiplier    The ore density multiplier
     * @param aDensityDivider       The ore density divider
     * @param aDye                  The {@link Dyes} color
     * @param aAspects              The {@link TC_AspectStack}'s List
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S00107", // 22 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aDye);
        getAspects().addAll(aAspects);
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID        The meta-sub identifier for items
     * @param aTextureSet           The {@link TextureSet} textures
     * @param aToolSpeed            The tool speed multiplier
     * @param aDurability           The durability
     * @param aToolQuality          The tool quality
     * @param aTypes                The flags to enable item types
     * @param aR                    The main color red component
     * @param aG                    The main color green component
     * @param aB                    The main color blue component
     * @param aA                    The main color alpha component
     * @param aLocalName            The default localized name
     * @param aFuelType             The fuel typ identifier
     * @param aFuelPower            The fuel value
     * @param aMeltingPoint         The melting point
     * @param aBlastFurnaceTemp     The blast furnace temperature
     * @param aBlastFurnaceRequired If smelting requires a blast furnace
     * @param aTransparent          If it is transparent
     * @param aOreValue             The ore value
     * @param aDensityMultiplier    The ore density multiplier
     * @param aDensityDivider       The ore density divider
     * @param aDye                  The {@link Dyes} color
     * @param aElement              The chemical {@link Element}
     * @param aAspects              The {@link TC_AspectStack}'s List
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S00107", // 23 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye, Element aElement, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aDye);
        setElement(aElement);
        getElement().mLinkedMaterials.add(this);
        String tToolTip;
        if (aElement == Element._NULL) {
            tToolTip = "Empty";
        } else {
            tToolTip = aElement.toString().replaceAll("_", "-");
        }
        setToolTip(tToolTip);
        getAspects().addAll(aAspects);
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID        The meta-sub identifier for items
     * @param aTextureSet           The {@link TextureSet} textures
     * @param aToolSpeed            The tool speed multiplier
     * @param aDurability           The durability
     * @param aToolQuality          The tool quality
     * @param aTypes                The flags to enable item types
     * @param aR                    The main color red component
     * @param aG                    The main color green component
     * @param aB                    The main color blue component
     * @param aA                    The main color alpha component
     * @param aLocalName            The default localized name
     * @param aFuelType             The fuel typ identifier
     * @param aFuelPower            The fuel value
     * @param aMeltingPoint         The melting point
     * @param aBlastFurnaceTemp     The blast furnace temperature
     * @param aBlastFurnaceRequired If smelting requires a blast furnace
     * @param aTransparent          If it is transparent
     * @param aOreValue             The ore value
     * @param aDensityMultiplier    The ore density multiplier
     * @param aDensityDivider       The ore density divider
     * @param aDye                  The {@link Dyes} color
     * @param aExtraData            The Extra Data
     * @param aMaterialStackList    The {@link MaterialStack}'s List
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S00107", // 23 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye, int aExtraData, List<MaterialStack> aMaterialStackList) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aDye, aExtraData, aMaterialStackList, null);
    }

    /**
     * Materials constructor
     *
     * @param aMetaItemSubID        The meta-sub identifier for items
     * @param aTextureSet           The {@link TextureSet} textures
     * @param aToolSpeed            The tool speed multiplier
     * @param aDurability           The durability
     * @param aToolQuality          The tool quality
     * @param aTypes                The flags to enable item types
     * @param aR                    The main color red component
     * @param aG                    The main color green component
     * @param aB                    The main color blue component
     * @param aA                    The main color alpha component
     * @param aLocalName            The default localized name
     * @param aFuelType             The fuel typ identifier
     * @param aFuelPower            The fuel value
     * @param aMeltingPoint         The melting point
     * @param aBlastFurnaceTemp     The blast furnace temperature
     * @param aBlastFurnaceRequired If smelting requires a blast furnace
     * @param aTransparent          If it is transparent
     * @param aOreValue             The ore value
     * @param aDensityMultiplier    The ore density multiplier
     * @param aDensityDivider       The ore density divider
     * @param aDye                  The {@link Dyes} color
     * @param aExtraData            The Extra Data
     * @param aMaterialStackList    The {@link MaterialStack}'s List
     * @param aAspects              The {@link TC_AspectStack}'s List
     * @deprecated by {@link MaterialsBuilder}
     */
    @SuppressWarnings({
            "squid:S00107", // 24 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye, int aExtraData, List<MaterialStack> aMaterialStackList, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aDye);
        setExtraData(aExtraData);
        getMaterialStackList().addAll(aMaterialStackList);
        StringBuilder tToolTipBuilder = new StringBuilder();
        for (MaterialStack tMaterialStack : getMaterialStackList()) {
            tToolTipBuilder.append(tMaterialStack.toString());
        }
        setToolTip(tToolTipBuilder.toString().replaceAll("_", "-"));

        int tAmountOfComponents = 0;
        int tMeltingPoint = 0;
        for (MaterialStack tMaterialStack : getMaterialStackList()) {
            tAmountOfComponents += tMaterialStack.mAmount;
            if (tMaterialStack.mMaterial.getMeltingPoint() > 0)
                tMeltingPoint += tMaterialStack.mMaterial.getMeltingPoint() * tMaterialStack.mAmount;
            if (aAspects == null)
                for (TC_Aspects.TC_AspectStack tAspect : tMaterialStack.mMaterial.getAspects())
                    tAspect.addToAspectList(getAspects());
        }

        if (getMeltingPoint() < 0) setMeltingPoint((short) (tMeltingPoint / tAmountOfComponents));

        tAmountOfComponents *= aDensityMultiplier;
        tAmountOfComponents /= aDensityDivider;
        if (aAspects == null) {
            for (TC_AspectStack tAspect : getAspects())
                tAspect.mAmount = Math.max(1, tAspect.mAmount / Math.max(1, tAmountOfComponents));
        } else getAspects().addAll(aAspects);
    }

    private static void initSubTags() {
        SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM.addTo(get("Bastnasite"), get("Monazite"), get("Forcicium"), get("Forcillium"));
        SubTag.ELECTROMAGNETIC_SEPERATION_GOLD.addTo(get("Magnetite"), get("VanadiumMagnetite"), get("BasalticMineralSand"), get("GraniticMineralSand"));
        SubTag.ELECTROMAGNETIC_SEPERATION_IRON.addTo(get("YellowLimonite"), get("BrownLimonite"), get("Pyrite"), get("BandedIron"), get("Nickel"), get("Vermiculite"), get("Glauconite"), get("GlauconiteSand"), get("Pentlandite"), get("Tin"), get("Antimony"), get("Ilmenite"), get("Manganese"), get("Chrome"), get("Chromite"), get("Andradite"));
        SubTag.BLASTFURNACE_CALCITE_DOUBLE.addTo(get("Pyrite"), get("YellowLimonite"), get("BasalticMineralSand"), get("GraniticMineralSand"));
        SubTag.BLASTFURNACE_CALCITE_TRIPLE.addTo(get("Iron"), get("PigIron"), get("DeepIron"), get("ShadowIron"), get("WroughtIron"), get("MeteoricIron"), get("BrownLimonite"));
        SubTag.WASHING_MERCURY.addTo(get("Gold"), get("Silver"), get("Osmium"), get("Mithril"), get("Platinum"), get("Midasium"), get("Cooperite"), get("AstralSilver"));
        SubTag.WASHING_SODIUMPERSULFATE.addTo(get("Zinc"), get("Nickel"), get("Copper"), get("Cobalt"), get("Cobaltite"), get("Tetrahedrite"));
        SubTag.WASHING_BLUEV.addTo(get("Copper"), get("Tetrahedrite"), get("Chalcopyrite"), get("Teslatite"), get("Gold"), get("Malachite"));
        SubTag.WASHING_GREENV.addTo(get("Chromite"), get("Chrome"), get("Ilmenite"), get("Iron"), get("Magnetite"), get("Tungstate"), get("Andradite"), get("Pyrite"));
        SubTag.WASHING_NICKELS.addTo(get("Nickel"), get("Pentlandite"));
        SubTag.METAL.addTo(get("AnyIron"), get("AnyCopper"), get("AnyBronze"), get("Metal"), get("Aluminium"), get("Americium"), get("Antimony"), get("Beryllium"), get("Bismuth"), get("Caesium"), get("Cerium"), get("Chrome"), get("Cobalt"), get("Copper"), get("Dysprosium"), get("Erbium"), get("Europium"), get("Gadolinium"), get("Gallium"), get("Gold"),
                get("Holmium"), get("Indium"), get("Iridium"), get("Iron"), get("Lanthanum"), get("Lead"), get("Lutetium"), get("Magnesium"), get("Manganese"), get("Mercury"), get("Niobium"), get("Molybdenum"), get("Neodymium"), get("Neutronium"), get("Nickel"), get("Osmium"), get("Palladium"), get("Platinum"), get("Plutonium"), get("Plutonium241"),
                get("Praseodymium"), get("Promethium"), get("Rubidium"), get("Samarium"), get("Scandium"), get("Silicon"), get("Silver"), get("Tantalum"), get("Terbium"), get("Thorium"), get("Thulium"), get("Tin"), get("Titanium"), get("Tungsten"), get("Uranium"), get("Uranium235"), get("Vanadium"), get("Ytterbium"), get("Yttrium"),
                get("Zinc"), get("Serpentine"), get("Signalum"), get("Lumium"), get("PhasedIron"), get("PhasedGold"), get("DarkSteel"), get("Terrasteel"), get("TinAlloy"), get("ConductiveIron"), get("ElectricalSteel"), get("EnergeticAlloy"), get("VibrantAlloy"),
                get("PulsatingIron"), get("Manasteel"), get("DarkThaumium"), get("ElvenElementium"), get("EnrichedCopper"), get("DiamondCopper"), get("Adamantium"), get("Amordrine"), get("Angmallen"), get("Ardite"), get("Aredrite"), get("Atlarus"), get("Blutonium"), get("Carmot"), get("Celenegil"), get("Ceruclase"), get("DarkIron"),
                get("Desh"), get("Desichalkos"), get("Duranium"), get("ElectrumFlux"), get("Enderium"), get("EnderiumBase"), get("Eximite"), get("FierySteel"), get("Haderoth"), get("Hematite"), get("Hepatizon"), get("HSLA"), get("Infuscolium"), get("InfusedGold"), get("Inolashite"), get("Mercassium"), get("MeteoricIron"),
                get("MeteoricSteel"), get("Naquadah"), get("NaquadahAlloy"), get("NaquadahEnriched"), get("Naquadria"), get("ObsidianFlux"), get("Orichalcum"), get("Osmonium"), get("Oureclase"), get("Phoenixite"), get("Prometheum"), get("Sanguinite"), get("Starconium"),
                get("Tartarite"), get("Thyrium"), get("Tritanium"), get("Vulcanite"), get("Vyroxeres"), get("Yellorium"), get("Zectium"), get("AluminiumBrass"), get("Osmiridium"), get("Sunnarium"), get("AnnealedCopper"), get("BatteryAlloy"), get("Brass"), get("Bronze"), get("ChromiumDioxide"), get("Cupronickel"), get("DeepIron"),
                get("Electrum"), get("Invar"), get("IronCompressed"), get("Kanthal"), get("Magnalium"), get("Nichrome"), get("NiobiumNitride"), get("NiobiumTitanium"), get("PigIron"), get("SolderingAlloy"), get("StainlessSteel"), get("Steel"), get("Ultimet"), get("VanadiumGallium"), get("WroughtIron"),
                get("YttriumBariumCuprate"), get("IronWood"), get("Alumite"), get("Manyullyn"), get("ShadowIron"), get("ShadowSteel"), get("Steeleaf"), get("SterlingSilver"), get("RoseGold"), get("BlackBronze"), get("BismuthBronze"), get("BlackSteel"), get("RedSteel"), get("BlueSteel"), get("DamascusSteel"),
                get("TungstenSteel"), get("AstralSilver"), get("Midasium"), get("Mithril"), get("BlueAlloy"), get("RedAlloy"), get("CobaltBrass"), get("Thaumium"), get("IronMagnetic"), get("SteelMagnetic"), get("NeodymiumMagnetic"), get("Knightmetal"), get("VanadiumSteel"), get("HSSG"), get("HSSE"), get("HSSS"));

        SubTag.FOOD.addTo(get("MeatRaw"), get("MeatCooked"), get("Ice"), get("Water"), get("Salt"), get("Chili"), get("Cocoa"), get("Cheese"), get("Coffee"), get("Chocolate"), get("Milk"), get("Honey"), get("FryingOilHot"), get("FishOil"), get("SeedOil"), get("SeedOilLin"), get("SeedOilHemp"), get("Wheat"), get("Sugar"), get("FreshWater"));

        get("Wood").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("WoodSealed").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.NO_WORKING);
        get("Peanutwood").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("LiveRoot").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        get("IronWood").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        get("Steeleaf").add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE, SubTag.NO_SMELTING);

        get("MeatRaw").add(SubTag.NO_SMASHING);
        get("MeatCooked").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Snow").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        get("Ice").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        get("Water").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        get("Sulfur").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        get("Saltpeter").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        get("Graphite").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.NO_SMELTING);

        get("Wheat").add(SubTag.FLAMMABLE, SubTag.MORTAR_GRINDABLE);
        get("Paper").add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE, SubTag.PAPER);
        get("Coal").add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        get("Charcoal").add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        get("Lignite").add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);

        get("Rubber").add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
        get("Plastic").add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY);

        get("TNT").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("Gunpowder").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("Glyceryl").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("NitroCoalFuel").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("NitroFuel").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        get("NitroCarbon").add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);

        get("Lead").add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_BAD);
        get("Tin").add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL);
        get("SolderingAlloy").add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_GOOD);

        get("Cheese").add(SMELTING_TO_FLUID);
        get("Sugar").add(SMELTING_TO_FLUID);

        get("Concrete").add(SubTag.STONE, SubTag.NO_SMASHING, SMELTING_TO_FLUID);
        get("ConstructionFoam").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.EXPLOSIVE, SubTag.NO_SMELTING);
        get("Redstone").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID, SubTag.PULVERIZING_CINNABAR);
        get("Glowstone").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID);
        get("Teslatite").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID);
        get("Netherrack").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.FLAMMABLE);
        get("Stone").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.NO_RECYCLING);
        get("Brick").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("NetherBrick").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Endstone").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Marble").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Basalt").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Redrock").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Obsidian").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Flint").add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        get("GraniteRed").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("GraniteBlack").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("Salt").add(SubTag.STONE, SubTag.NO_SMASHING);
        get("RockSalt").add(SubTag.STONE, SubTag.NO_SMASHING);

        get("Sand").add(SubTag.NO_RECYCLING);

        get("Gold").add(SubTag.MORTAR_GRINDABLE);
        get("Silver").add(SubTag.MORTAR_GRINDABLE);
        get("Iron").add(SubTag.MORTAR_GRINDABLE);
        get("IronMagnetic").add(SubTag.MORTAR_GRINDABLE);
        get("HSLA").add(SubTag.MORTAR_GRINDABLE);
        get("Steel").add(SubTag.MORTAR_GRINDABLE);
        get("SteelMagnetic").add(SubTag.MORTAR_GRINDABLE);
        get("Zinc").add(SubTag.MORTAR_GRINDABLE);
        get("Antimony").add(SubTag.MORTAR_GRINDABLE);
        get("Copper").add(SubTag.MORTAR_GRINDABLE);
        get("AnnealedCopper").add(SubTag.MORTAR_GRINDABLE);
        get("Bronze").add(SubTag.MORTAR_GRINDABLE);
        get("Nickel").add(SubTag.MORTAR_GRINDABLE);
        get("Invar").add(SubTag.MORTAR_GRINDABLE);
        get("Brass").add(SubTag.MORTAR_GRINDABLE);
        get("WroughtIron").add(SubTag.MORTAR_GRINDABLE);
        get("Electrum").add(SubTag.MORTAR_GRINDABLE);
        get("Clay").add(SubTag.MORTAR_GRINDABLE);

        get("Glass").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_RECYCLING, SMELTING_TO_FLUID);
        get("Diamond").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        get("Emerald").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Amethyst").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Tanzanite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Topaz").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("BlueTopaz").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Amber").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("GreenSapphire").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Sapphire").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Ruby").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("FoolsRuby").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Opal").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Olivine").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Jasper").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("GarnetRed").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("GarnetYellow").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Mimichite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("CrystalFlux").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Crystal").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Niter").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Apatite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        get("Lapis").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        get("Sodalite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        get("Lazurite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        get("Monazite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        get("Quartzite").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("Quartz").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("SiliconDioxide").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("Dilithium").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("NetherQuartz").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("CertusQuartz").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("Fluix").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        get("Phosphorus").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        get("Phosphate").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        get("InfusedAir").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedFire").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedEarth").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedWater").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedEntropy").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedOrder").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedVis").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("InfusedDull").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("Vinteum").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("NetherStar").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        get("EnderPearl").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        get("EnderEye").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        get("Firestone").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL, SubTag.QUARTZ, SubTag.UNBURNABLE, SubTag.BURNING);
        get("Forcicium").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        get("Forcillium").add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        get("Magic").add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);

        get("Primitive").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Basic").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Good").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Advanced").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Data").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Elite").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Master").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Ultimate").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Superconductor").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        get("Infinite").add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);

        get("Blaze").add(SubTag.MAGICAL, SubTag.NO_SMELTING, SMELTING_TO_FLUID, SubTag.MORTAR_GRINDABLE, SubTag.UNBURNABLE, SubTag.BURNING);
        get("FierySteel").add(SubTag.MAGICAL, SubTag.UNBURNABLE, SubTag.BURNING);
        get("ElvenElementium").add(SubTag.MAGICAL);
        get("DarkThaumium").add(SubTag.MAGICAL);
        get("Thaumium").add(SubTag.MAGICAL);
        get("Enderium").add(SubTag.MAGICAL);
        get("AstralSilver").add(SubTag.MAGICAL);
        get("Midasium").add(SubTag.MAGICAL);
        get("Mithril").add(SubTag.MAGICAL);
    }

    public static void initCustomMats(String aCustomOreConfigLabel) {
        //TODO MAIN: increase sGeneratedMaterials limit from 1,000 to 10,000.
        //TODO good way to only get the rest of the data (Tool qual etc) one and not twice per for loop (one for default and custom).
        //TODO split this into a new method

//.subID(912).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(110, 140, 110, 0).locName("Chrysotile").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(get("Asbestos"), 1)).build();
        for (ConfigCategory configCategory : materialsConfig.getCategory("Materials").getChildren()) {
            MaterialsBuilder materialBuilder = new MaterialsBuilder();
            String materialName = configCategory.getName();
            materialBuilder.name(materialName);
            materialBuilder.subID(configCategory.get("ID").getInt());
            if (configCategory.containsKey("textureSet")) {
                TextureSet textureSet = TextureSet.get(configCategory.get("textureSet").getString());
                if (textureSet != null) {
                    materialBuilder.textureSet(textureSet);
                }
            }
            if (configCategory.containsKey("toolSpeed")) {
                materialBuilder.toolSpeed((float) configCategory.get("toolSpeed").getDouble());
            }
            MATERIALS_HASH_MAP.put(materialName, materialBuilder.build());
        }

        for (Materials tMaterial : values()) {
            if (tMaterial.getSubID() >= 0) {
                tMaterial.setSubID(GregTech_API.sMaterialsFile.get("materials.default." + tMaterial.getDefaultLocalName(), "MaterialID", tMaterial.getSubID()));
                tMaterial.setToolDurability(GregTech_API.sMaterialsFile.get("materials.default." + tMaterial.getDefaultLocalName(), "Durability", tMaterial.getDurability()));
                if (tMaterial.name().equals("Desh")) {
                    tMaterial.setHandleMaterial(tMaterial.getHandleMaterial());
                } else if (tMaterial.name().equals("Diamond")
                        || tMaterial.name().equals("Thaumium")) {
                    tMaterial.setHandleMaterial(get("Wood"));
                } else if (tMaterial.contains(SubTag.BURNING)) {
                    tMaterial.setHandleMaterial(get("Blaze"));
                } else if (tMaterial.contains(SubTag.MAGICAL)
                        && tMaterial.contains(SubTag.CRYSTAL)
                        && Loader.isModLoaded(MOD_ID_TC)) {
                    tMaterial.setHandleMaterial(get("Thaumium"));
                } else if (tMaterial.getMass() > Element.Tc.getMass()) {
                    if (tMaterial.getMass() > Element.Tc.getMass() * 2) {
                        tMaterial.setHandleMaterial(get("TungstenSteel"));
                    } else tMaterial.setHandleMaterial(get("Steel"));
                } else {
                    if (tMaterial.getMass() > Element.Tc.getMass() * 2) {
                        tMaterial.setHandleMaterial(get("TungstenSteel"));
                    } else {
                        tMaterial.setHandleMaterial(get("Wood"));
                    }
                }
            }
        }
        //TODO make this minetweaker friendly?
        //TODO register fluids
        int i = 0;
        StringBuilder aCustomMatNum = new StringBuilder();
        for (int j = GregTech_API.sMaterialsFile.get("general", "AmountOfCustomMaterialSlots", 16); i < j; i++) {
            aCustomMatNum.append("materials.").append(aCustomOreConfigLabel).append(".custommat").append(i < 10 ? "0" : EMPTY_STRING).append(i);
            int aMetaItemSubID = GregTech_API.sMaterialsFile.get(aCustomMatNum.toString(), "MaterialID", -1);
            String aDefaultLocalName = GregTech_API.sMaterialsFile.get(aCustomMatNum.toString(), "MaterialName", "NullMat");
            if (!aDefaultLocalName.equalsIgnoreCase("NullMat")) {
                new Materials(aMetaItemSubID, SET_METALLIC, 16.0F, 1280, 4, 1 | 2 | 8 | 32 | 64 | 128, 50, 50, 255, 0, aDefaultLocalName, 0, 0, 3306, 3306, true, false, 10, 1, 1, dyeBlue);
            }
            aCustomMatNum.setLength(0);
        }
    }

    /**
     * Gets the {@link Materials} from its Name
     *
     * @param aMaterialName The {@link Materials}'s Name
     * @return the {@link Materials} or null if none found
     */
    public static Materials get(String aMaterialName) {
        Materials material;
        material = MATERIALS_HASH_MAP.get(aMaterialName);
        return material;
    }

    /**
     * Gets the Aliased real {@link Materials} from an aliased {@link Materials}
     *
     * @param aMaterialName The {@link Materials}'s Alias Name
     * @return the Aliased real {@link Materials} or null if none
     */
    public static Materials getRealMaterial(String aMaterialName) {
        return get(aMaterialName).mMaterialInto;
    }

    public static Collection<Materials> values() {
        return MATERIALS_HASH_MAP.values();
    }

    /**
     * Gets the Name of the {@link Materials}
     *
     * @return the {@link Materials}'s Name
     */
    public String name() {
        return this.mName;
    }

    /**
     * Sets the {@link Materials}'s name
     *
     * @param aName The {@link Materials}'s name
     */
    public void setName(String aName) {
        mName = aName;
    }

    /**
     * Determines if the {@link Materials} is Radioactive
     *
     * @return true if Radioactive
     */
    public boolean isRadioactive() {
        if (mElement != null) return mElement.mHalfLifeSeconds >= 0;
        for (MaterialStack tMaterial : getMaterialStackList()) {
            if (tMaterial.mMaterial.isRadioactive()) return true;
        }
        return false;
    }

    /**
     * Gets the {@link Materials}'s Protons count
     *
     * @return The Protons count
     */
    public long getProtons() {
        if (mElement != null) return mElement.getProtons();
        if ((mMaterialStackList.isEmpty())) return Element.Tc.getProtons();
        long rAmount = 0;
        long tAmount = 0;
        for (MaterialStack tMaterial : mMaterialStackList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getProtons();
        }
        if (tAmount == 0) tAmount = 1;
        return (getDensity() * rAmount) / (tAmount * MATERIAL_UNIT);
    }

    /**
     * Gets the {@link Materials}'s Neutrons count
     *
     * @return The Neutrons count
     */
    public long getNeutrons() {
        if (mElement != null) return mElement.getNeutrons();
        if (mMaterialStackList.isEmpty()) return Element.Tc.getNeutrons();
        long rAmount = 0;
        long tAmount = 0;
        for (MaterialStack tMaterial : mMaterialStackList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getNeutrons();
        }
        if (tAmount == 0) tAmount = 1;
        return (getDensity() * rAmount) / (tAmount * MATERIAL_UNIT);
    }

    /**
     * Gets the {@link Materials}'s Mass
     *
     * @return The Mass
     */
    public long getMass() {
        if (mElement != null) return mElement.getMass();
        if (mMaterialStackList.isEmpty()) return Element.Tc.getMass();
        long rAmount = 0;
        long tAmount = 0;
        for (MaterialStack tMaterial : mMaterialStackList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getMass();
        }
        if (tAmount == 0) tAmount = 1;
        return (getDensity() * rAmount) / (tAmount * MATERIAL_UNIT);
    }

    /**
     * Gets the {@link Materials}'s Density
     *
     * @return The Density
     */
    public long getDensity() {
        return mDensity;
    }

    /**
     * Set the {@link Materials}'s Density
     *
     * @param aDensity The Density
     */
    public void setDensity(long aDensity) {
        mDensity = aDensity;
    }

    /**
     * Gets the ToolTip of the {@link Materials}
     *
     * @return The ToolTip String
     */
    public String getToolTip() {
        return getToolTip(1, false);
    }

    /**
     * Sets the ToolTip's chemical formula of the {@link Materials}
     *
     * @param aToolTip The ToolTip's chemical formula of this {@link Materials}
     */
    public void setToolTip(String aToolTip) {
        this.mChemicalFormula = aToolTip;
    }

    /**
     * Gets the ToolTip of the {@link Materials}
     *
     * @param aShowQuestionMarks if Question Mark (unknown) is shown
     * @return The ToolTip String
     */
    public String getToolTip(boolean aShowQuestionMarks) {
        return getToolTip(1, aShowQuestionMarks);
    }

    /**
     * Gets the ToolTip of the {@link Materials} amount
     *
     * @param aMultiplier The {@link Materials} amount
     * @return The ToolTip String
     */
    public String getToolTip(long aMultiplier) {
        return getToolTip(aMultiplier, false);
    }

    /**
     * Gets the ToolTip's chemical formula of the {@link Materials} amount
     *
     * @param aMultiplier        The {@link Materials} amount
     * @param aShowQuestionMarks if Question Mark (unknown formula) is shown
     * @return The ToolTip String
     */
    public String getToolTip(long aMultiplier, boolean aShowQuestionMarks) {
        if (!aShowQuestionMarks && mChemicalFormula.equals("?")) return EMPTY_STRING;
        if (aMultiplier >= MATERIAL_UNIT * 2 && !mMaterialStackList.isEmpty()) {
            return ((mElement != null || (mMaterialStackList.size() < 2 && mMaterialStackList.get(0).mAmount == 1)) ? mChemicalFormula : "(" + mChemicalFormula + ")") + aMultiplier;
        }
        return mChemicalFormula;
    }

    /**
     * Adds / Associates the {@link ItemStack}s to the {@link Materials}.
     *
     * @param aStack The {@link ItemStack}s to add
     * @return the updated {@link Materials} with added {@link ItemStack}s
     */
    public Materials add(ItemStack... aStack) {
        for (ItemStack tItemStack : aStack) {
            if (tItemStack != null &&
                    !contains(tItemStack)) {
                mMaterialItems.add(tItemStack);
            }
        }
        return this;
    }

    /**
     * Adds / Associates the {@link ItemStack}s list to the {@link Materials}.
     *
     * @param aMaterialItems The {@link ItemStack}s list to add
     */
    public void setMaterialItems(List<ItemStack> aMaterialItems) {
        mMaterialItems = aMaterialItems;
    }

    /**
     * Determines if any of the {@link ItemStack} belongs to the {@link Materials}.
     *
     * @param aStacks The {@link ItemStack} to search
     * @return true if the {@link ItemStack} belongs to the {@link Materials}
     */
    public boolean contains(ItemStack... aStacks) {
        if (aStacks == null || aStacks.length <= 0) return false;
        for (ItemStack tStack : mMaterialItems) {
            for (ItemStack aStack : aStacks) {
                if (GT_Utility.areStacksEqual(aStack, tStack, !tStack.hasTagCompound())) return true;
            }
        }
        return false;
    }

    /**
     * Removes / dissociates {@link ItemStack}s from the {@link Materials}
     *
     * @param aStack the {@link ItemStack}s to remove
     * @return the {@link Materials} with removed {@link ItemStack}s
     */
    public boolean remove(ItemStack... aStack) {
        if (aStack == null) return false;
        boolean temp = false;

        int i = 0;
        while (i < mMaterialItems.size()) {
            for (ItemStack tStack : aStack) {
                if (GT_Utility.areStacksEqual(tStack, mMaterialItems.get(i))) {
                    mMaterialItems.remove(i--);
                    temp = true;
                }
                i++;
            }
        }
        return temp;
    }

    /**
     * Adds SubTags to the {@link Materials}
     *
     * @param aTags the {@link SubTag}s to be added
     * @return the {@link Materials} with the added {@link SubTag}s
     */
    @Override
    public ISubTagContainer add(SubTag... aTags) {
        if (aTags == null) return this;

        for (SubTag aTag : aTags) {
            if (aTag != null && !contains(aTag)) {
                aTag.addContainerToList(this);
                mSubTags.add(aTag);
            }
        }

        return this;
    }

    /**
     * Sets the {@link SubTag}s list of the {@link Materials}
     *
     * @param aSubTags the {@link SubTag}s list
     */
    public void setSubTags(List<SubTag> aSubTags) {
        mSubTags = aSubTags;
    }

    /**
     * Tests if the {@link Materials} has the exact {@link SubTag}
     *
     * @param aTag the {@link SubTag} to test
     * @return true if the {@link Materials} contains the {@link SubTag}
     */
    @Override
    public boolean contains(SubTag aTag) {
        return mSubTags.contains(aTag);
    }

    /**
     * Removes a SubTag from the {@link Materials}
     *
     * @param aTag the {@link SubTag} to be removed
     * @return the {@link Materials} with the removed {@link SubTag}
     */
    @Override
    public boolean remove(SubTag aTag) {
        return mSubTags.remove(aTag);
    }

    /**
     * Gets the Heat Damage for the {@link Materials} (negative = frost)
     *
     * @return The Heat Damage for the {@link Materials} (negative = frost)
     */
    public float getHeatDamage() {
        return mHeatDamage;
    }

    /**
     * Sets the Heat Damage for the {@link Materials} (negative = frost)
     *
     * @param aHeatDamage The Heat Damage (negative = frost)
     * @return The updated {@link Materials} with the new Heat Damage
     */
    public Materials setHeatDamage(float aHeatDamage) {
        mHeatDamage = aHeatDamage;
        return this;
    }

    /**
     * Adds the {@link Materials} to the List of Byproducts when grinding the Ore.
     * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Materials.
     *
     * @param aMaterial The {@link Materials} to be added as byproducts
     * @return The updated {@link Materials} with added byproducts
     */
    public Materials addOreByProduct(Materials aMaterial) {
        if (!getOreByProducts().contains(aMaterial.mMaterialInto)) getOreByProducts().add(aMaterial.mMaterialInto);
        return this;
    }

    /**
     * Gets the {@link Materials}'s Ore Multiplier
     * If the Ore gives multiple drops of its Main {@link Materials}.
     * Lapis Ore for example gives about 6 drops.
     *
     * @return The {@link Materials}'s Ore Multiplier
     */
    public int getOreMultiplier() {
        return mOreMultiplier;
    }

    /**
     * If the Ore gives multiple drops of its Main {@link Materials}.
     * Lapis Ore for example gives about 6 drops.
     *
     * @param aOreMultiplier The Main Ore Multiplier
     * @return The updated {@link Materials} with the new Ore Multiplier
     */
    public Materials setOreMultiplier(int aOreMultiplier) {
        if (aOreMultiplier > 0) mOreMultiplier = aOreMultiplier;
        return this;
    }

    /**
     * Gets the Byproduct Multiplier of the {@link Materials}
     *
     * @return The Byproduct Multiplier of the {@link Materials}
     */
    public int getByProductMultiplier() {
        return mByProductMultiplier;
    }

    /**
     * If the Ore gives multiple drops of its Byproduct Material.
     *
     * @param aByProductMultiplier The Byproduct Multiplier
     * @return The updated {@link Materials} with the new Byproduct Multiplier
     */
    public Materials setByProductMultiplier(int aByProductMultiplier) {
        if (aByProductMultiplier > 0) mByProductMultiplier = aByProductMultiplier;
        return this;
    }

    /**
     * Gets the Smelting Multiplier of the {@link Materials}
     *
     * @return The Smelting Multiplier of the {@link Materials}
     */
    public int getSmeltingMultiplier() {
        return mSmeltingMultiplier;
    }

    /**
     * If the Ore smelts into multiple drops of its Main Material.
     * Lapis Ore for example smelts into about 3 drops.
     *
     * @param aSmeltingMultiplier The Smelting Multiplier
     * @return The updated {@link Materials} with the new Smelt Multiplier
     */
    public Materials setSmeltingMultiplier(int aSmeltingMultiplier) {
        if (aSmeltingMultiplier > 0) mSmeltingMultiplier = aSmeltingMultiplier;
        return this;
    }

    /**
     * Gets replacement {@link Materials} for direct smelting
     *
     * @return The replacement {@link Materials}
     */
    public Materials getDirectSmelting() {
        return mDirectSmelting;
    }

    /**
     * The Ore should be smelted directly into an Ingot of the {@link Materials} instead of an Ingot of itself.
     *
     * @param aMaterial The Ingot's {@link Materials}
     * @return The updated {@link Materials} with the added Ingot's {@link Materials} direct smelt
     */
    public Materials setDirectSmelting(Materials aMaterial) {
        if (aMaterial != null) mDirectSmelting = aMaterial.mMaterialInto.mDirectSmelting;
        return this;
    }

    /**
     * Gets the {@link Materials}'s ore replacement {@link Materials}.
     *
     * @return The ore replacement {@link Materials}}
     */
    public Materials getOreReplacement() {
        return mOreReplacement;
    }

    /**
     * The Material should be the Main Material the Ore gets ground into.
     * Example: Chromite giving Chrome or Tungstate giving Tungsten.
     *
     * @param aMaterial The ground {@link Materials}
     * @return The updated {@link Materials} with the new ground {@link Materials}
     */
    public Materials setOreReplacement(Materials aMaterial) {
        if (aMaterial != null) mOreReplacement = aMaterial.mMaterialInto.mOreReplacement;
        return this;
    }

    /**
     * Gets smelting product {@link Materials}.
     *
     * @return The Arc Furnace's smelting product {@link Materials}.
     */
    public Materials getSmeltingInto() {
        return mArcSmeltInto;
    }

    /**
     * The {@link Materials} always smelts into an instance of aMaterial. Used for Magnets.
     *
     * @param aMaterial The always smelts into {@link Materials}
     * @return The updated {@link Materials} with the new smelt-into {@link Materials}
     */
    public Materials setSmeltingInto(Materials aMaterial) {
        if (aMaterial != null) mSmeltInto = aMaterial.mMaterialInto.mSmeltInto;
        return this;
    }

    /**
     * Gets the Arc Furnace's smelting product {@link Materials}.
     *
     * @return The Arc Furnace's smelting product {@link Materials}.
     */
    public Materials getArcSmeltingInto() {
        return mArcSmeltInto;
    }

    /**
     * In an Arc Furnace, the {@link Materials} always smelt-into an instance of {@link Materials}.
     * Used for Wrought Iron.
     *
     * @param aMaterial The Arc Furnace always smelt-into {@link Materials}
     * @return The updated {@link Materials} with the new Arc Furnace always smelt-into {@link Materials}
     */
    public Materials setArcSmeltingInto(Materials aMaterial) {
        if (aMaterial != null) mArcSmeltInto = aMaterial.mMaterialInto.mArcSmeltInto;
        return this;
    }

    /**
     * Gets the maceration product {@link Materials}.
     *
     * @return The maceration product {@link Materials}
     */
    public Materials getMaceratingInto() {
        return mMacerateInto;
    }

    /**
     * The {@link Materials} always macerates into an instance of {@link Materials}.
     *
     * @param aMaterial The maceration output {@link Materials}
     * @return The updated {@link Materials} with the new maceration output {@link Materials}
     */
    public Materials setMaceratingInto(Materials aMaterial) {
        if (aMaterial != null) mMacerateInto = aMaterial.mMaterialInto.mMacerateInto;
        return this;
    }

    /**
     * Gets the {@link Enchantment} for Tools made of the {@link Materials}
     *
     * @return The {@link Enchantment} for Tools made of the {@link Materials}
     */
    public Enchantment getEnchantmentForTools() {
        return mEnchantmentTools;
    }

    /**
     * Sets the {@link Enchantment} for Tools made of the {@link Materials}
     *
     * @param aEnchantment The {@link Enchantment}
     */
    public void setEnchantmentForTools(Enchantment aEnchantment) {
        this.mEnchantmentTools = aEnchantment;
    }

    /**
     * Sets the {@link Enchantment} and Level for Tools made of the {@link Materials}
     *
     * @param aEnchantment      The {@link Enchantment}
     * @param aEnchantmentLevel The {@link Enchantment}'s Level
     * @return @return The updated {@link Materials} with the new {@link Enchantment} for Tools
     */
    public Materials setEnchantmentForTools(Enchantment aEnchantment, int aEnchantmentLevel) {
        setEnchantmentForTools(aEnchantment);
        setEnchantmentLevelForTools((byte) aEnchantmentLevel);
        return this;
    }

    /**
     * Gets the {@link Enchantment}'s Level for Tools made of the {@link Materials}
     *
     * @return The {@link Enchantment}'s Level
     */
    public byte getEnchantmentLevelForTools() {
        return mEnchantmentToolsLevel;
    }

    /**
     * Sets the {@link Enchantment}'s Level for Tools made of the {@link Materials}
     *
     * @param aEnchantmentLevel The {@link Enchantment}'s Level
     */
    public void setEnchantmentLevelForTools(byte aEnchantmentLevel) {
        this.mEnchantmentToolsLevel = aEnchantmentLevel;
    }

    /**
     * Gets the {@link Enchantment} for Armors made of the {@link Materials}
     *
     * @return The {@link Enchantment} for Armors made of the {@link Materials}
     */
    public Enchantment getEnchantmentForArmors() {
        return mEnchantmentArmors;
    }

    /**
     * Sets the {@link Enchantment} for Armors made of the {@link Materials}
     *
     * @param aEnchantment The {@link Enchantment}
     */
    public void setEnchantmentForArmors(Enchantment aEnchantment) {
        this.mEnchantmentArmors = aEnchantment;
    }

    /**
     * Sets the {@link Enchantment} for Armors made of the {@link Materials}
     *
     * @param aEnchantment      the {@link Enchantment}
     * @param aEnchantmentLevel the {@link Enchantment}'s Level
     * @return @return The updated {@link Materials} with the new {@link Enchantment} for Armors
     */
    public Materials setEnchantmentForArmors(Enchantment aEnchantment, int aEnchantmentLevel) {
        setEnchantmentForArmors(aEnchantment);
        setEnchantmentLevelForArmors((byte) aEnchantmentLevel);
        return this;
    }

    /**
     * Gets the {@link Enchantment}'s Level for Armors made of the {@link Materials}
     *
     * @return The {@link Enchantment}'s Level
     */
    public byte getEnchantmentLevelForArmors() {
        return mEnchantmentArmorsLevel;
    }

    /**
     * Sets the {@link Enchantment}'s Level for Armors made of the {@link Materials}
     *
     * @param aEnchantmentLevel The {@link Enchantment}'s Level
     */
    public void setEnchantmentLevelForArmors(byte aEnchantmentLevel) {
        this.mEnchantmentArmorsLevel = aEnchantmentLevel;
    }

    /**
     * Gets {@link GT_FluidStack} Fluid state amount from the Solid state of the {@link Materials}
     *
     * @param aAmount The amount of Fluid in Liters
     * @return The {@link GT_FluidStack} Fluid state amount from the Solid state of the {@link Materials}
     */
    public FluidStack getSolid(long aAmount) {
        if (mSolid == null) return null;
        return new GT_FluidStack(mSolid, (int) aAmount);
    }

    /**
     * Gets {@link GT_FluidStack} Fluid state amount of the {@link Materials}
     *
     * @param aAmount The amount of Fluid in Liters
     * @return The {@link GT_FluidStack} Fluid state amount of the {@link Materials}
     */
    public FluidStack getFluid(long aAmount) {
        if (mFluid == null) return null;
        return new GT_FluidStack(mFluid, (int) aAmount);
    }

    /**
     * Gets {@link GT_FluidStack} Gaseous state amount of the {@link Materials}
     *
     * @param aAmount The amount Gas in Liters
     * @return The {@link GT_FluidStack} Gaseous state amount of the {@link Materials}
     */
    public FluidStack getGas(long aAmount) {
        if (mGas == null) return null;
        return new GT_FluidStack(mGas, (int) aAmount);
    }

    /**
     * Gets {@link GT_FluidStack} Plasma state amount of the {@link Materials}
     *
     * @param aAmount The amount Plasma in Liters
     * @return The {@link GT_FluidStack} Plasma state amount of the {@link Materials}
     */
    public FluidStack getPlasma(long aAmount) {
        if (mPlasma == null) return null;
        return new GT_FluidStack(mPlasma, (int) aAmount);
    }

    /**
     * Gets {@link GT_FluidStack} Molten state amount of the {@link Materials}
     *
     * @param aAmount The amount Molten in Liters
     * @return The {@link GT_FluidStack} Molten state amount of the {@link Materials}
     */
    public FluidStack getMolten(long aAmount) {
        if (mStandardMoltenFluid == null) return null;
        return new GT_FluidStack(mStandardMoltenFluid, (int) aAmount);
    }

    /**
     * Gets {@link Materials}'s color components as a short array
     *
     * @return The short {@link Materials}'s RGBA color components array
     */
    public short[] getRGBa() {
        return mSolidColor.getRGBA();
    }

    /**
     * Sets the {@link Materials}'s color components as a short array
     *
     * @param aRGBa The short {@link Materials}'s RGBA color components array
     */
    public void setRGBa(short[] aRGBa) {
        mSolidColor.setRGBA(aRGBa);
    }

    /**
     * Gets the {@link Materials}'s color as an {@link GT_Color}
     *
     * @return The {@link GT_Color}
     */
    public GT_Color getColor() {
        return mSolidColor;
    }

    /**
     * Sets the {@link Materials}'s color as an {@link GT_Color}
     *
     * @param aColor The {@link GT_Color}
     */
    public void setColor(GT_Color aColor) {
        mSolidColor = aColor;
    }

    /**
     * Gets the {@link Materials}'s Molten color as an {@link GT_Color}
     *
     * @return the {@link GT_Color}
     */
    public GT_Color getMoltenColor() {
        return mMoltenColor;
    }

    /**
     * Sets the {@link Materials}'s Molten color as an {@link GT_Color}
     *
     * @param aMoltenColor The {@link GT_Color}
     */
    public void setMoltenColor(GT_Color aMoltenColor) {
        mMoltenColor = aMoltenColor;
    }

    /**
     * Gets the {@link Materials}'s Molten color components as short array
     *
     * @return The short RGBA molten color components array
     */
    public short[] getMoltenRGBa() {
        return mMoltenColor.getRGBA();
    }

    /**
     * Sets the {@link Materials}'s Molten color components as short array
     *
     * @param aMoltenRGBa The short RGBA molten color components array
     */
    public void setMoltenRGBa(short[] aMoltenRGBa) {
        mMoltenColor.setRGBA(aMoltenRGBa);
    }

    /**
     * Gets the {@link Materials}'s {@link TextureSet}
     *
     * @return The {@link Materials}'s {@link TextureSet}
     */
    public TextureSet getTextureSet() {
        return mTextureSet;
    }

    /**
     * Sets the {@link Materials}'s {@link TextureSet}
     *
     * @param aTextureSet The {@link Materials}'s {@link TextureSet}
     */
    public void setTextureSet(TextureSet aTextureSet) {
        mTextureSet = aTextureSet;
    }

    /**
     * Gets the {@link Materials}'s Meta SubID
     *
     * @return The {@link Materials}'s Meta SubID
     */
    public int getSubID() {
        return mMetaItemSubID;
    }

    /**
     * Sets the {@link Materials}'s Meta SubID
     *
     * @param aSubID The {@link Materials}'s Meta SubID
     */
    public void setSubID(int aSubID) {
        mMetaItemSubID = aSubID;
    }

    /**
     * Tells if the {@link Materials} is unifiable
     *
     * @return The unifiable status
     */
    public boolean isUnifiable() {
        return mUnificatable;
    }

    /**
     * Sets if the {@link Materials} is unifiable
     *
     * @param aUnifiable The Unifiable status
     */
    public void setUnifiable(boolean aUnifiable) {
        mUnificatable = aUnifiable;
    }

    /**
     * Gets the {@link Materials} the {@link Materials} to unify into
     *
     * @return The {@link Materials} to unify into
     */
    public Materials getMaterialInto() {
        return mMaterialInto;
    }

    /**
     * Sets the {@link Materials} the {@link Materials} to unify into
     *
     * @param aMaterialInto The {@link Materials} to unify into
     */
    public void setMaterialInto(Materials aMaterialInto) {
        mMaterialInto = aMaterialInto;
    }

    /**
     * Gets the {@link MaterialStack} list
     *
     * @return The {@link MaterialStack} list
     */
    public List<MaterialStack> getMaterialStackList() {
        return mMaterialStackList;
    }

    /**
     * Sets the {@link MaterialStack} list
     *
     * @param aMaterialStackList The {@link MaterialStack} list
     */
    public void setMaterialStackList(List<MaterialStack> aMaterialStackList) {
        mMaterialStackList = aMaterialStackList;
    }

    /**
     * Adds multiple {@link MaterialStack}s to the {@link MaterialStack} List of the {@link Materials}
     *
     * @param aMaterialStack The {@link MaterialStack}s to be added to the {@link MaterialStack} list of the {@link Materials}
     */
    public void addMaterialStack(MaterialStack... aMaterialStack) {
        mMaterialStackList.addAll(Arrays.asList(aMaterialStack));
    }

    /**
     * Gets the ore byproducts {@link Materials} list
     *
     * @return The ore byproducts {@link Materials} list
     */
    public List<Materials> getOreByProducts() {
        return mOreByProducts;
    }

    /**
     * Sets the ore byproducts {@link Materials} list
     *
     * @param aOreByProductList The byproducts {@link Materials} list
     */
    public void setOreByProducts(List<Materials> aOreByProductList) {
        this.mOreByProducts = aOreByProductList;
    }

    /**
     * Adds multiple {@link Materials} to the List of Byproducts when grinding the Ore.
     * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Materials.
     *
     * @param aMaterials The {@link Materials}s to be added as byproducts
     * @return The updated {@link Materials} with the added byproducts
     */
    public Materials addOreByProducts(Materials... aMaterials) {
        for (Materials tMaterial : aMaterials) if (tMaterial != null) addOreByProduct(tMaterial);
        return this;
    }

    /**
     * Gets the ore reregistrations {@link Materials} list
     *
     * @return The ore reregistrations {@link Materials} list
     */
    public List<Materials> getOreReRegistrations() {
        return mOreReRegistrations;
    }

    /**
     * Sets the ore reregistrations {@link Materials} list
     *
     * @param aOreReRegistrationMaterialList The ore reregistrations {@link Materials} list
     */
    public void setOreReRegistrations(List<Materials> aOreReRegistrationMaterialList) {
        this.mOreReRegistrations = aOreReRegistrationMaterialList;
    }

    /**
     * Gets the {@link Materials}'s {@link TC_AspectStack} List
     *
     * @return The {@link Materials}'s {@link TC_AspectStack} List
     */
    public List<TC_AspectStack> getAspects() {
        return mAspects;
    }

    /**
     * Sets the {@link Materials}'s {@link TC_AspectStack} List
     *
     * @param aAspectStackList The {@link Materials}'s {@link TC_AspectStack} List
     */
    public void setAspects(List<TC_AspectStack> aAspectStackList) {
        this.mAspects = aAspectStackList;
    }

    /**
     * If the {@link Materials} needs a Blast Furnace for smelting
     *
     * @return Blast Furnace for smelting requirement
     */
    public boolean isBlastFurnaceRequired() {
        return mBlastFurnaceRequired;
    }

    /**
     * Sets if the {@link Materials} needs a Blast Furnace for smelting
     *
     * @param aBlastFurnaceRequired The Blast Furnace requirement for smelting
     */
    public void setBlastFurnaceRequired(boolean aBlastFurnaceRequired) {
        this.mBlastFurnaceRequired = aBlastFurnaceRequired;
    }

    /**
     * Gets the Speed of Tools made of the {@link Materials}
     *
     * @return The Speed of Tools made of the {@link Materials}
     */
    public float getToolSpeed() {
        return mToolSpeed;
    }

    /**
     * Sets the Speed of Tools made of the {@link Materials}
     *
     * @param aToolSpeed The Speed of Tools made of the {@link Materials}
     */
    public void setToolSpeed(float aToolSpeed) {
        this.mToolSpeed = aToolSpeed;
    }

    /**
     * Gets the {@link Materials}'s default Local Name
     *
     * @return The {@link Materials}'s default Local Name
     */
    public String getDefaultLocalName() {
        return mDefaultLocalName;
    }

    /**
     * Sets the {@link Materials}'s default Local Name
     *
     * @param aDefaultLocalName The {@link Materials}'s default Local Name
     */
    public void setDefaultLocalName(String aDefaultLocalName) {
        this.mDefaultLocalName = aDefaultLocalName;
    }

    /**
     * Gets the {@link Materials}'s {@link Dyes} property
     *
     * @return The {@link Materials}'s {@link Dyes} property
     */
    public Dyes getDye() {
        return mColor;
    }

    /**
     * Sets the {@link Materials}'s {@link Dyes} property
     *
     * @param aDye The {@link Materials}'s {@link Dyes} property
     */
    public void setDye(Dyes aDye) {
        this.mColor = aDye;
    }

    /**
     * Gets the {@link Materials}'s Melting Point in Kelvins
     *
     * @return The {@link Materials}'s Melting Point in Kelvins
     */
    public short getMeltingPoint() {
        return mMeltingPoint;
    }

    /**
     * Sets the {@link Materials}'s Melting Point in Kelvins
     *
     * @param aKelvins The {@link Materials}'s Melting Point in Kelvins
     */
    public void setMeltingPoint(short aKelvins) {
        this.mMeltingPoint = aKelvins;
    }

    /**
     * Gets the {@link Materials}'s Blast Furnace's required Heating Capacity in Kelvins
     *
     * @return The {@link Materials}'s Blast Furnace's required Heating Capacity in Kelvins
     */
    public short getBlastFurnaceTemp() {
        return mBlastFurnaceTemp;
    }

    /**
     * Sets the {@link Materials}'s Blast Furnace's required Heating Capacity in Kelvins
     *
     * @param aKelvins {@link Materials}'s Blast Furnace's required Heating Capacity in Kelvins
     */
    public void setBlastFurnaceTemp(short aKelvins) {
        this.mBlastFurnaceTemp = aKelvins;
    }

    /**
     * Gets the Type of the {@link Materials}
     *
     * @return The Type of the {@link Materials}
     */
    public int getTypes() {
        return mTypes;
    }

    /**
     * Sets the Type of the {@link Materials}
     *
     * @param aType The Type of the {@link Materials}
     */
    public void setTypes(int aType) {
        this.mTypes = aType;
    }

    /**
     * Gets the {@link Materials}'s Durability
     *
     * @return The {@link Materials}'s Durability
     */
    public int getDurability() {
        return mDurability;
    }

    /**
     * Sets the {@link Materials}'s Durability
     *
     * @param aDurability The {@link Materials}'s Durability
     */
    public void setToolDurability(int aDurability) {
        this.mDurability = aDurability;
    }

    /**
     * Gets the {@link Materials}'s Fuel Value
     *
     * @return The {@link Materials}'s Fuel Value
     */
    public int getFuelPower() {
        return mFuelPower;
    }

    /**
     * Sets the {@link Materials}'s Fuel Value
     *
     * @param aFuelValue The {@link Materials}'s Fuel Value
     */
    public void setFuelPower(int aFuelValue) {
        this.mFuelPower = aFuelValue;
    }

    /**
     * Gets the {@link Materials}'s Fuel Type
     *
     * @return The {@link Materials}'s Fuel Type
     */
    public int getFuelType() {
        return mFuelType;
    }

    /**
     * Sets the {@link Materials}'s Fuel Type
     *
     * @param aFuelType The {@link Materials}'s Fuel Type
     */
    public void setFuelType(int aFuelType) {
        this.mFuelType = aFuelType;
    }

    /**
     * Gets the {@link Materials}'s Extra Data
     *
     * @return The {@link Materials}'s Extra Data
     */
    public int getExtraData() {
        return mExtraData;
    }

    /**
     * Sets the {@link Materials}'s Extra Data
     *
     * @param aExtraData The {@link Materials}'s Extra Data
     */
    public void setExtraData(int aExtraData) {
        this.mExtraData = aExtraData;
    }

    /**
     * Gets the {@link Materials}'s Ore Value
     *
     * @return The {@link Materials}'s Ore Value
     */
    public int getOreValue() {
        return mOreValue;
    }

    /**
     * Sets the {@link Materials}'s Ore Value
     *
     * @param aOreValue The {@link Materials}'s Ore Value
     */
    public void setOreValue(int aOreValue) {
        this.mOreValue = aOreValue;
    }

    /**
     * Gets the {@link Materials}'s Chemical {@link Element}
     *
     * @return The {@link Materials}'s Chemical {@link Element}
     */
    public Element getElement() {
        return mElement;
    }

    /**
     * Sets the {@link Materials}'s Chemical {@link Element}
     *
     * @param aElement The {@link Materials}'s Chemical {@link Element}
     */
    public void setElement(Element aElement) {
        this.mElement = aElement;
    }

    /**
     * Gets the Handle's {@link Materials} for tools made of the {@link Materials}
     *
     * @return The Handle's {@link Materials} for tools made of the {@link Materials}
     */
    public Materials getHandleMaterial() {
        return mHandleMaterial;
    }

    /**
     * Sets the Handle's {@link Materials} for tools made of the {@link Materials}
     *
     * @param aHandleMaterial The Handle's {@link Materials} for tools made of the {@link Materials}
     */
    public void setHandleMaterial(Materials aHandleMaterial) {
        this.mHandleMaterial = aHandleMaterial;
    }

    /**
     * Gets the Quality of Tools made of the {@link Materials}
     *
     * @return The Quality of Tools made of the {@link Materials}
     */
    public byte getToolQuality() {
        return mToolQuality;
    }

    /**
     * Sets the Quality of Tools made of the {@link Materials}
     *
     * @param aToolQuality The Quality of Tools made of the {@link Materials}
     */
    public void setToolQuality(byte aToolQuality) {
        this.mToolQuality = aToolQuality;
    }

    /**
     * Gets the {@link Fluid} from the Solid state of the {@link Materials}
     *
     * @return The {@link Fluid} from the Solid state of the {@link Materials}
     */
    public Fluid getSolid() {
        return mSolid;
    }

    /**
     * Sets the {@link Fluid} from the Solid state of the {@link Materials}
     *
     * @param aFluid {@link Fluid} from the Solid state of the {@link Materials}
     */
    public void setSolid(Fluid aFluid) {
        this.mSolid = aFluid;
    }

    /**
     * Gets the {@link Fluid} of the {@link Materials}
     *
     * @return The {@link Fluid} of the {@link Materials}
     */
    public Fluid getFluid() {
        return mFluid;
    }

    /**
     * Sets the {@link Fluid} of the {@link Materials}
     *
     * @param aFluid The {@link Fluid} of the {@link Materials}
     */
    public void setFluid(Fluid aFluid) {
        this.mFluid = aFluid;
    }

    /**
     * Gets the Gas {@link Fluid} of the {@link Materials}
     *
     * @return The Gas {@link Fluid} of the {@link Materials}
     */
    public Fluid getGas() {
        return mGas;
    }

    /**
     * Sets the Gas {@link Fluid} of the {@link Materials}
     *
     * @param aFluid The Gas {@link Fluid} of the {@link Materials}
     */
    public void setGas(Fluid aFluid) {
        this.mGas = aFluid;
    }

    /**
     * Gets the Plasma {@link Fluid} of the {@link Materials}
     *
     * @return The Plasma {@link Fluid} of the {@link Materials}
     */
    public Fluid getPlasma() {
        return mPlasma;
    }

    /**
     * Sets the Plasma {@link Fluid} of the {@link Materials}
     *
     * @param aFluid The Plasma {@link Fluid} of the {@link Materials}
     */
    public void setPlasma(Fluid aFluid) {
        this.mPlasma = aFluid;
    }

    /**
     * Gets the Standard Molten {@link Fluid} of the {@link Materials}
     *
     * @return The Standard Molten {@link Fluid} of the {@link Materials}
     */
    public Fluid getStandardMoltenFluid() {
        return mStandardMoltenFluid;
    }

    /**
     * Sets the Standard Molten {@link Fluid} of the {@link Materials}
     *
     * @param aFluid The Standard Molten {@link Fluid} of the {@link Materials}
     */
    public void setStandardMoltenFluid(Fluid aFluid) {
        this.mStandardMoltenFluid = aFluid;
    }

    /**
     * Implicit Name of the {@link Materials}
     *
     * @return The {@link Materials}'s Name
     */
    @Override
    public String toString() {
        return name();
    }
}
