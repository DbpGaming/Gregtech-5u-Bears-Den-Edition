package gregtech.api.enums;

import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.objects.GT_Color;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
    public static final Collection<Materials> VALUES = new HashSet<>();
    public static volatile int VERSION = 508;
    /**
     * This is the Default Material returned in case no Material has been found or a NullPointer has been inserted at a location where it shouldn't happen.
     */
    public static Materials _NULL = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(0x00FFFFFF).locName("NULL").fuelType(0).fuelPow(0).meltAt(0).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 1)).build();
    /**
     * Direct Elements
     */
    public static Materials Aluminium = new Materials.Builder().subID(19).textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(128).toolQual(2).type(DUST | SOLID | ORE | PLATE | STICK | RING | BOLT | FOIL | SCREW | GEAR | SGEAR | FWIRE | ROTOR | DPLATE | LIQUID | FRAME).solidColor(0x0080C8F0).locName("Aluminium").fuelType(0).fuelPow(0).meltAt(933).blastAt(1700).needBlast(true).oreVal(3).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Al).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VOLATUS, 1)).build();
    public static Materials Americium = new Materials.Builder().subID(103).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | SOLID | LIQUID).solidColor(0x00C8C8C8).locName("Americium").fuelType(0).fuelPow(0).meltAt(1449).blastAt(0).needBlast(false).oreVal(3).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Am).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Antimony = new Materials.Builder().subID(58).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00DCDCF0).locName("Antimony").fuelType(0).fuelPow(0).meltAt(903).blastAt(0).needBlast(false).oreVal(2).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Sb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.AQUA, 1)).build();
    public static Materials Argon = new Materials.Builder().subID(24).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(CELL | CFLUID).solidColor(0x00FF00F00).locName("Argon").fuelType(0).fuelPow(0).meltAt(83).blastAt(0).needBlast(false).subTags(TRANSPARENT).oreVal(5).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.Ar).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build();
    public static Materials Arsenic = new Materials.Builder().subID(39).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00FFFFFF).locName("Arsenic").fuelType(0).fuelPow(0).meltAt(1090).blastAt(0).needBlast(false).oreVal(3).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.As).aspects(new TC_AspectStack(TC_Aspects.VENENUM, 3)).build();
    public static Materials Barium = new Materials.Builder().subID(63).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00777777).locName("Barium").fuelType(0).fuelPow(0).meltAt(1000).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ba).aspects(new TC_AspectStack(TC_Aspects.VINCULUM, 3)).build();
    public static Materials Beryllium = new Materials.Builder().subID(8).textureSet(SET_METALLIC).toolSpeed(14.0F).toolDura(64).toolQual(2).type(DUST | SOLID | ORE | PLATE | DPLATE | LIQUID).solidColor(0x0064B464).locName("Beryllium").fuelType(0).fuelPow(0).meltAt(1560).blastAt(0).needBlast(false).oreVal(6).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.Be).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build();
    public static Materials Bismuth = new Materials.Builder().subID(90).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | SOLID | ORE | LIQUID).solidColor(0x0064A0A0).locName("Bismuth").fuelType(0).fuelPow(0).meltAt(544).blastAt(0).needBlast(false).oreVal(2).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.Bi).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Boron = new Materials.Builder().subID(9).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | SOLID | LIQUID).solidColor(0x00AAAAAA).locName("Boron").fuelType(0).fuelPow(0).meltAt(2349).blastAt(0).needBlast(false).oreVal(1).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.B).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Caesium = new Materials.Builder().subID(62).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(0x00FFC83C).locName("Caesium").fuelType(0).fuelPow(0).meltAt(301).blastAt(0).needBlast(false).oreVal(4).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Cs).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Calcium = new Materials.Builder().subID(26).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(0x00FFF5F5).locName("Calcium").fuelType(0).fuelPow(0).meltAt(1115).blastAt(0).needBlast(false).oreVal(4).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Ca).aspects(new TC_AspectStack(TC_Aspects.SANO, 1), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build();

    //TODO: set density of liquid for correct materials
    public static Materials Carbon = new Materials.Builder().subID(10).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | PLASMA | TOOL | GEAR | SGEAR).solidColor(20, 20, 20, 0).locName("Carbon").fuelType(0).fuelPow(0).meltAt(3800).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.C).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials Cadmium = new Materials.Builder().subID(55).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(50, 50, 60, 0).locName("Cadmium").fuelType(0).fuelPow(0).meltAt(594).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Cd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)).build();
    public static Materials Cerium = new Materials.Builder().subID(65).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Cerium").fuelType(0).fuelPow(0).meltAt(1068).blastAt(1068).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ce).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Chlorine = new Materials.Builder().subID(23).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 255, 0).locName("Chlorine").fuelType(0).fuelPow(0).meltAt(171).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.Cl).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.PANNUS, 1)).build();
    public static Materials Chrome = new Materials.Builder().subID(30).textureSet(SET_SHINY).toolSpeed(11.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 230, 230, 0).locName("Chrome").fuelType(0).fuelPow(0).meltAt(2180).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Cr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build();
    public static Materials Cobalt = new Materials.Builder().subID(33).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(80, 80, 250, 0).locName("Cobalt").fuelType(0).fuelPow(0).meltAt(1768).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Co).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Copper = new Materials.Builder().subID(35).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | GEAR | SGEAR).solidColor(255, 100, 0, 0).locName("Copper").fuelType(0).fuelPow(0).meltAt(1357).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.Cu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PERMUTATIO, 1)).build();
    public static Materials Deuterium = new Materials.Builder().subID(2).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Deuterium").fuelType(0).fuelPow(0).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.D).aspects(new TC_AspectStack(TC_Aspects.AQUA, 3)).build();
    public static Materials Dysprosium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Dysprosium").fuelType(0).fuelPow(0).meltAt(1680).blastAt(1680).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Dy).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build();
    public static Materials Empty = new Materials.Builder().subID(0).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 255).locName("Empty").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 2)).build();
    public static Materials Erbium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Erbium").fuelType(0).fuelPow(0).meltAt(1802).blastAt(1802).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Er).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Europium = new Materials.Builder().subID(70).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Europium").fuelType(0).fuelPow(0).meltAt(1099).blastAt(1099).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Eu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Fluorine = new Materials.Builder().subID(14).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 255, 127).locName("Fluorine").fuelType(0).fuelPow(0).meltAt(53).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.F).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 2)).build();
    public static Materials Gadolinium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Gadolinium").fuelType(0).fuelPow(0).meltAt(1585).blastAt(1585).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Gd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Gallium = new Materials.Builder().subID(37).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(220, 220, 255, 0).locName("Gallium").fuelType(0).fuelPow(0).meltAt(302).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Ga).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build();
    public static Materials Gold = new Materials.Builder().subID(86).textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 255, 30, 0).locName("Gold").fuelType(0).fuelPow(0).meltAt(1337).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.Au).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 2)).build();
    public static Materials Holmium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Holmium").fuelType(0).fuelPow(0).meltAt(1734).blastAt(1734).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ho).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Hydrogen = new Materials.Builder().subID(1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 0, 255, 240).locName("Hydrogen").fuelType(1).fuelPow(15).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.H).aspects(new TC_AspectStack(TC_Aspects.AQUA, 1)).build();
    public static Materials Helium = new Materials.Builder().subID(4).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Helium").fuelType(0).fuelPow(0).meltAt(1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.He).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build();
    public static Materials Helium_3 = new Materials.Builder().subID(5).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 255, 0, 240).locName("Helium-3").fuelType(0).fuelPow(0).meltAt(1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.He_3).aspects(new TC_AspectStack(TC_Aspects.AER, 3)).build();
    public static Materials Indium = new Materials.Builder().subID(56).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(64, 0, 128, 0).locName("Indium").fuelType(0).fuelPow(0).meltAt(429).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.In).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Iridium = new Materials.Builder().subID(84).textureSet(SET_DULL).toolSpeed(6.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(240, 240, 245, 0).locName("Iridium").fuelType(0).fuelPow(0).meltAt(2719).blastAt(2719).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Ir).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build();
    public static Materials Iron = new Materials.Builder().subID(32).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Fe).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build();
    public static Materials Lanthanum = new Materials.Builder().subID(64).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(216, 224, 255, 0).locName("Lanthanum").fuelType(0).fuelPow(0).meltAt(1193).blastAt(1193).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.La).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Lead = new Materials.Builder().subID(89).textureSet(SET_DULL).toolSpeed(8.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(140, 100, 140, 0).locName("Lead").fuelType(0).fuelPow(0).meltAt(600).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Pb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)).build();
    public static Materials Lithium = new Materials.Builder().subID(6).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(225, 220, 255, 0).locName("Lithium").fuelType(0).fuelPow(0).meltAt(454).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Li).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build();
    public static Materials Lutetium = new Materials.Builder().subID(78).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Lutetium").fuelType(0).fuelPow(0).meltAt(1925).blastAt(1925).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Lu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Magic = new Materials.Builder().subID(-128).textureSet(SET_SHINY).toolSpeed(8.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | GEM | BGEM | ORE | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(100, 0, 200, 0).locName("Magic").fuelType(5).fuelPow(32).meltAt(5000).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Ma).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 4)).build();
    public static Materials Magnesium = new Materials.Builder().subID(18).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 200, 200, 0).locName("Magnesium").fuelType(0).fuelPow(0).meltAt(923).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).elem(Element.Mg).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)).build();
    public static Materials Manganese = new Materials.Builder().subID(31).textureSet(SET_DULL).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(250, 250, 250, 0).locName("Manganese").fuelType(0).fuelPow(0).meltAt(1519).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Mn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build();
    public static Materials Mercury = new Materials.Builder().subID(87).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(0).type(LIQUID | CELL | PLASMA).solidColor(255, 220, 220, 0).locName("Mercury").fuelType(5).fuelPow(32).meltAt(234).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Hg).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)).build();
    public static Materials Molybdenum = new Materials.Builder().subID(48).textureSet(SET_SHINY).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(180, 180, 220, 0).locName("Molybdenum").fuelType(0).fuelPow(0).meltAt(2896).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Mo).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Neodymium = new Materials.Builder().subID(67).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Neodymium").fuelType(0).fuelPow(0).meltAt(1297).blastAt(1297).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Nd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 2)).build();
    public static Materials Neutronium = new Materials.Builder().subID(129).textureSet(SET_DULL).toolSpeed(24.0F).toolDura(655360).toolQual(6).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(250, 250, 250, 0).locName("Neutronium").fuelType(0).fuelPow(0).meltAt(10000).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Nt).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.ALIENIS, 2)).build();
    public static Materials Nickel = new Materials.Builder().subID(34).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(200, 200, 250, 0).locName("Nickel").fuelType(0).fuelPow(0).meltAt(1728).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).elem(Element.Ni).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials Niobium = new Materials.Builder().subID(47).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(190, 180, 200, 0).locName("Niobium").fuelType(0).fuelPow(0).meltAt(2750).blastAt(2750).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Nb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build();
    public static Materials Nitrogen = new Materials.Builder().subID(12).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 150, 200, 240).locName("Nitrogen").fuelType(0).fuelPow(0).meltAt(63).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).elem(Element.N).aspects(new TC_AspectStack(TC_Aspects.AER, 2)).build();
    public static Materials Osmium = new Materials.Builder().subID(83).textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(50, 50, 255, 0).locName("Osmium").fuelType(0).fuelPow(0).meltAt(3306).blastAt(3306).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Os).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build();
    public static Materials Oxygen = new Materials.Builder().subID(13).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(0, 100, 200, 240).locName("Oxygen").fuelType(0).fuelPow(0).meltAt(54).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.O).aspects(new TC_AspectStack(TC_Aspects.AER, 1)).build();
    public static Materials Palladium = new Materials.Builder().subID(52).textureSet(SET_SHINY).toolSpeed(8.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Palladium").fuelType(0).fuelPow(0).meltAt(1828).blastAt(1828).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Pd).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3)).build();
    public static Materials Phosphor = new Materials.Builder().subID(21).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(255, 255, 0, 0).locName("Phosphor").fuelType(0).fuelPow(0).meltAt(317).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.P).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 2), new TC_AspectStack(TC_Aspects.POTENTIA, 1)).build();
    public static Materials Platinum = new Materials.Builder().subID(85).textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(255, 255, 200, 0).locName("Platinum").fuelType(0).fuelPow(0).meltAt(2041).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).elem(Element.Pt).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build();
    public static Materials Plutonium = new Materials.Builder().subID(100).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(240, 50, 50, 0).locName("Plutonium 244").fuelType(0).fuelPow(0).meltAt(912).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).elem(Element.Pu).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)).build();
    public static Materials Plutonium241 = new Materials.Builder().subID(101).textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(250, 70, 70, 0).locName("Plutonium 241").fuelType(0).fuelPow(0).meltAt(912).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).elem(Element.Pu_241).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 3)).build();
    public static Materials Potassium = new Materials.Builder().subID(25).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | PLASMA).solidColor(250, 250, 250, 0).locName("Potassium").fuelType(0).fuelPow(0).meltAt(336).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.K).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1)).build();
    public static Materials Praseodymium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Praseodymium").fuelType(0).fuelPow(0).meltAt(1208).blastAt(1208).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Pr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Promethium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Promethium").fuelType(0).fuelPow(0).meltAt(1315).blastAt(1315).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Pm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Radon = new Materials.Builder().subID(93).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 0, 255, 240).locName("Radon").fuelType(0).fuelPow(0).meltAt(202).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Rn).aspects(new TC_AspectStack(TC_Aspects.AER, 1), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Rubidium = new Materials.Builder().subID(43).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(240, 30, 30, 0).locName("Rubidium").fuelType(0).fuelPow(0).meltAt(312).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).elem(Element.Rb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build();
    public static Materials Samarium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Samarium").fuelType(0).fuelPow(0).meltAt(1345).blastAt(1345).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Sm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Scandium = new Materials.Builder().subID(27).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Scandium").fuelType(0).fuelPow(0).meltAt(1814).blastAt(1814).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.Sc).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Silicon = new Materials.Builder().subID(20).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(60, 60, 80, 0).locName("Silicon").fuelType(0).fuelPow(0).meltAt(1687).blastAt(1687).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.Si).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TENEBRAE, 1)).build();
    public static Materials Silver = new Materials.Builder().subID(54).textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(220, 220, 255, 0).locName("Silver").fuelType(0).fuelPow(0).meltAt(1234).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Ag).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build();
    public static Materials Sodium = new Materials.Builder().subID(17).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLASMA).solidColor(0, 0, 150, 0).locName("Sodium").fuelType(0).fuelPow(0).meltAt(370).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).elem(Element.Na).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.LUX, 1)).build();
    public static Materials Strontium = new Materials.Builder().subID(44).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(200, 200, 200, 0).locName("Strontium").fuelType(0).fuelPow(0).meltAt(1050).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).elem(Element.Sr).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.STRONTIO, 1)).build();
    public static Materials Sulfur = new Materials.Builder().subID(22).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | PLASMA).solidColor(200, 200, 0, 0).locName("Sulfur").fuelType(0).fuelPow(0).meltAt(388).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).elem(Element.S).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials Tantalum = new Materials.Builder().subID(80).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Tantalum").fuelType(0).fuelPow(0).meltAt(3290).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Ta).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VINCULUM, 1)).build();
    public static Materials Tellurium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Tellurium").fuelType(0).fuelPow(0).meltAt(722).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).elem(Element.Te).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Terbium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Terbium").fuelType(0).fuelPow(0).meltAt(1629).blastAt(1629).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Tb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Thorium = new Materials.Builder().subID(96).textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(0, 30, 0, 0).locName("Thorium").fuelType(0).fuelPow(0).meltAt(2115).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.Th).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Thulium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Thulium").fuelType(0).fuelPow(0).meltAt(1818).blastAt(1818).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Tm).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Tin = new Materials.Builder().subID(57).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | GEAR | SGEAR).solidColor(220, 220, 220, 0).locName("Tin").fuelType(0).fuelPow(0).meltAt(505).blastAt(505).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Sn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build();
    public static Materials Titanium = new Materials.Builder().subID(28).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(1600).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(220, 160, 240, 0).locName("Titanium").fuelType(0).fuelPow(0).meltAt(1941).blastAt(1940).needBlast(true).density(MATERIAL_UNIT).dye(dyePurple).elem(Element.Ti).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build();
    public static Materials Tritium = new Materials.Builder().subID(3).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(LIQUID | CELL | PLASMA).solidColor(255, 0, 0, 240).locName("Tritium").fuelType(0).fuelPow(0).meltAt(14).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).elem(Element.T).aspects(new TC_AspectStack(TC_Aspects.AQUA, 4)).build();
    public static Materials Tungsten = new Materials.Builder().subID(81).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL | GEAR | SGEAR).solidColor(50, 50, 50, 0).locName("Tungsten").fuelType(0).fuelPow(0).meltAt(3695).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.W).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build();
    public static Materials Uranium = new Materials.Builder().subID(98).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(50, 240, 50, 0).locName("Uranium 238").fuelType(0).fuelPow(0).meltAt(1405).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.U).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Uranium235 = new Materials.Builder().subID(97).textureSet(SET_SHINY).toolSpeed(6.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA | TOOL).solidColor(70, 250, 70, 0).locName("Uranium 235").fuelType(0).fuelPow(0).meltAt(1405).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).elem(Element.U_235).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)).build();
    public static Materials Vanadium = new Materials.Builder().subID(29).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(50, 50, 50, 0).locName("Vanadium").fuelType(0).fuelPow(0).meltAt(2183).blastAt(2183).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).elem(Element.V).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Ytterbium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(255, 255, 255, 0).locName("Ytterbium").fuelType(0).fuelPow(0).meltAt(1097).blastAt(1097).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Yb).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Yttrium = new Materials.Builder().subID(45).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(220, 250, 220, 0).locName("Yttrium").fuelType(0).fuelPow(0).meltAt(1799).blastAt(1799).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).elem(Element.Y).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)).build();
    public static Materials Zinc = new Materials.Builder().subID(36).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | PLASMA).solidColor(250, 240, 240, 0).locName("Zinc").fuelType(0).fuelPow(0).meltAt(692).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).elem(Element.Zn).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)).build();
    /**
     * The "Random Material" ones.
     */

    //TODO: bring back unifiable param!
    public static Materials Organic = new Materials.Builder().subID(-1).textureSet(SET_LEAF).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build();
    public static Materials AnyCopper = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build();
    public static Materials AnyBronze = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build();
    public static Materials AnyIron = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build();
    public static Materials Crystal = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).build();
    public static Materials Quartz = new Materials.Builder().subID(-1).textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build();
    public static Materials Metal = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build();
    public static Materials Unknown = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).build();
    public static Materials Cobblestone = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build();
    public static Materials Brick = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build();
    public static Materials BrickNether = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).build();
    /**
     * The "I don't care" Section, everything I don't want to do anything with right now, is right here. Just to make the Material Finder shut up about them.
     * But I do see potential uses in some of these Materials.
     */
    public static Materials TarPitch = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Tar Pitch").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Serpentine = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Serpentine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Flux = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Flux").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials RedstoneAlloy = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Redstone Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials OsmiumTetroxide = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Osmium Tetroxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials NitricAcid = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Nitric Acid").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials RubberTreeSap = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Rubber Tree Sap").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials AquaRegia = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Aqua Regia").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials SolutionBlueVitriol = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Blue Vitriol Solution").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials SolutionNickelSulfate = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Nickel Sulfate Solution").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Signalum = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Signalum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Lumium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Lumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials PhasedIron = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Phased Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials PhasedGold = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Phased Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Soularium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Soularium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Endium = new Materials.Builder().subID(770).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(165, 220, 250, 0).locName("Endium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Prismarine = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Prismarine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials GraveyardDirt = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Graveyard Dirt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials DarkSteel = new Materials.Builder().subID(364).textureSet(SET_DULL).toolSpeed(8.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(80, 70, 80, 0).locName("Dark Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build();
    public static Materials Terrasteel = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Terrasteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials ConductiveIron = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Conductive Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials ElectricalSteel = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Electrical Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials EnergeticAlloy = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Energetic Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials VibrantAlloy = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Vibrant Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials PulsatingIron = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Pulsating Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Graphene = new Materials.Builder().subID(819).textureSet(SET_DULL).toolSpeed(6.0F).toolDura(32).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Graphene").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)).build();
    public static Materials Bedrockium = new Materials.Builder().subID(-1).textureSet(SET_FINE).toolSpeed(8.0F).toolDura(8196).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID | PLASMA | TOOL | GEAR | SGEAR).solidColor(39, 39, 39, 0).locName("Bedrockium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(Carbon, 63), new MaterialStack(Carbon, 56)).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 8), new TC_AspectStack(TC_Aspects.TUTAMEN, 3)).build();
    public static Materials Fluix = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Fluix").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Manasteel = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Manasteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Tennantite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Tennantite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials DarkThaumium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Dark Thaumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Alfium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Alfium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Ryu = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ryu").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Mutation = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Mutation").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Aquamarine = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM).solidColor(255, 255, 255, 0).locName("Aquamarine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Ender = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ender").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials ElvenElementium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Elven Elementium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials EnrichedCopper = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Enriched Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials DiamondCopper = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Diamond Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials SodiumPeroxide = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Sodium Peroxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials IridiumSodiumOxide = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Iridium Sodium Oxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    //public static Materials PlatinumGroupSludge = new Materials.Builder().subID( -1).textureSet(SET_POWDER ).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST ,0, 30,0,0,	"Platinum Group Sludge" ,0,0, -1,0, false, false,3,1,1,.dye(Dyes._NULL .build();
    public static Materials Fairy = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Fairy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Ludicrite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Ludicrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Pokefennium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Pokefennium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Draconium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Draconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials DraconiumAwakened = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Awakened Draconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials InfusedTeslatite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(100, 180, 255, 0).locName("Infused Teslatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    /**
     * Unknown Material Components. Dead End Section.
     */
    public static Materials Adamantium = new Materials.Builder().subID(319).textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 255, 0).locName("Adamantium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build();
    public static Materials Adamite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Adamite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build();
    public static Materials Adluorite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Adluorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Agate = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Agate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Alduorite = new Materials.Builder().subID(485).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE | LIQUID).solidColor(159, 180, 180, 0).locName("Alduorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Amber = new Materials.Builder().subID(514).textureSet(SET_RUBY).toolSpeed(4.0F).toolDura(128).toolQual(2).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 128, 0, 127).locName("Amber").fuelType(5).fuelPow(3).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.VINCULUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build();
    public static Materials Ammonium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Ammonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Amordrine = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 255, 255, 0).locName("Amordrine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Andesite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Andesite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Angmallen = new Materials.Builder().subID(958).textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(128).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(215, 225, 138, 0).locName("Angmallen").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Ardite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 0, 0, 0).locName("Ardite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Aredrite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 0, 0, 0).locName("Aredrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Atlarus = new Materials.Builder().subID(965).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Atlarus").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Bitumen = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Bitumen").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Black = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(0, 0, 0, 0).locName("Black").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Blizz = new Materials.Builder().subID(851).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(220, 233, 255, 0).locName("Blizz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Blueschist = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Blue Schist").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).build();
    public static Materials Bluestone = new Materials.Builder().subID(813).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Bluestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build();
    public static Materials Bloodstone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Bloodstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Blutonium = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(0, 0, 255, 0).locName("Blutonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build();
    public static Materials Carmot = new Materials.Builder().subID(962).textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(128).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(217, 205, 140, 0).locName("Carmot").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Celenegil = new Materials.Builder().subID(964).textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(4096).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(148, 204, 72, 0).locName("Celenegil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials CertusQuartz = new Materials.Builder().subID(516).textureSet(SET_QUARTZ).toolSpeed(5.0F).toolDura(32).toolQual(1).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(210, 210, 230, 0).locName("Certus Quartz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build();
    public static Materials Ceruclase = new Materials.Builder().subID(952).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(140, 189, 208, 0).locName("Ceruclase").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Citrine = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Citrine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials CobaltHexahydrate = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(80, 80, 250, 0).locName("Cobalt Hexahydrate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build();
    public static Materials ConstructionFoam = new Materials.Builder().subID(854).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(128, 128, 128, 0).locName("Construction Foam").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build();
    public static Materials Chert = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chert").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Chimerite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chimerite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Coral = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 128, 255, 0).locName("Coral").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials CrudeOil = new Materials.Builder().subID(858).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(10, 10, 10, 0).locName("Crude Oil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Chrysocolla = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Chrysocolla").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials CrystalFlux = new Materials.Builder().subID(-1).textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(3).type(DUST | GEM | BGEM).solidColor(100, 50, 100, 0).locName("Flux Crystal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Cyanite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Cyanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).build();
    public static Materials Dacite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Dacite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build();
    public static Materials DarkIron = new Materials.Builder().subID(342).textureSet(SET_DULL).toolSpeed(7.0F).toolDura(384).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(55, 40, 60, 0).locName("Dark Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build();
    public static Materials DarkStone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Dark Stone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Demonite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Demonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Desh = new Materials.Builder().subID(884).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(1280).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(40, 40, 40, 0).locName("Desh").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Desichalkos = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(1280).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 255, 255, 0).locName("Desichalkos").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Dilithium = new Materials.Builder().subID(515).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(255, 250, 250, 127).locName("Dilithium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials Draconic = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Draconic").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Drulloy = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(255, 255, 255, 0).locName("Drulloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Duranium = new Materials.Builder().subID(328).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Duranium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build();
    public static Materials Eclogite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Eclogite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials ElectrumFlux = new Materials.Builder().subID(320).textureSet(SET_SHINY).toolSpeed(16.0F).toolDura(512).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 120, 0).locName("Fluxed Electrum").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Emery = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Emery").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Enderium = new Materials.Builder().subID(321).textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(89, 145, 135, 0).locName("Enderium").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build();
    public static Materials EnderiumBase = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(89, 145, 135, 0).locName("Enderium Base").fuelType(0).fuelPow(0).meltAt(3000).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build();
    public static Materials Energized = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Energized").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Epidote = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Epidote").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Eximite = new Materials.Builder().subID(959).textureSet(SET_METALLIC).toolSpeed(5.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(124, 90, 150, 0).locName("Eximite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials FierySteel = new Materials.Builder().subID(346).textureSet(SET_FIERY).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID | TOOL | GEAR | SGEAR).solidColor(64, 0, 0, 0).locName("Fiery Steel").fuelType(5).fuelPow(2048).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 3), new TC_AspectStack(TC_Aspects.CORPUS, 3)).build();
    public static Materials Firestone = new Materials.Builder().subID(347).textureSet(SET_QUARTZ).toolSpeed(6.0F).toolDura(1280).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 20, 0, 0).locName("Firestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Fluorite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Fluorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build();
    public static Materials FoolsRuby = new Materials.Builder().subID(512).textureSet(SET_RUBY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM | ORE).solidColor(255, 100, 100, 127).locName("Ruby").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build();
    public static Materials Forcicium = new Materials.Builder().subID(518).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(50, 50, 70, 0).locName("Forcicium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build();
    public static Materials Forcillium = new Materials.Builder().subID(519).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE | LIQUID).solidColor(50, 50, 70, 0).locName("Forcillium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build();
    public static Materials Gabbro = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Gabbro").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Glowstone = new Materials.Builder().subID(811).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID).solidColor(255, 255, 0, 0).locName("Glowstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.LUX, 2), new TC_AspectStack(TC_Aspects.SENSUS, 1)).build();
    public static Materials Gneiss = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Gneiss").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Graphite = new Materials.Builder().subID(865).textureSet(SET_DULL).toolSpeed(5.0F).toolDura(32).toolQual(2).type(DUST | ORE | LIQUID | TOOL).solidColor(128, 128, 128, 0).locName("Graphite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials Greenschist = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Green Schist").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build();
    public static Materials Greenstone = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Greenstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build();
    public static Materials Greywacke = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Greywacke").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build();
    public static Materials Haderoth = new Materials.Builder().subID(963).textureSet(SET_METALLIC).toolSpeed(10.0F).toolDura(3200).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(119, 52, 30, 0).locName("Haderoth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Hematite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Hematite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Hepatizon = new Materials.Builder().subID(957).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(128).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(117, 94, 117, 0).locName("Hepatizon").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials HSLA = new Materials.Builder().subID(322).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(500).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("HSLA Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)).build();
    public static Materials Ignatius = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(512).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID).solidColor(255, 169, 83, 0).locName("Ignatius").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Infernal = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(0).solidColor(255, 255, 255, 0).locName("Infernal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Infuscolium = new Materials.Builder().subID(490).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(146, 33, 86, 0).locName("Infuscolium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials InfusedGold = new Materials.Builder().subID(323).textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 200, 60, 0).locName("Infused Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials InfusedAir = new Materials.Builder().subID(540).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 0, 0).locName("Aer").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AER, 2)).build();
    public static Materials InfusedFire = new Materials.Builder().subID(541).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 0, 0, 0).locName("Ignis").fuelType(5).fuelPow(320).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build();
    public static Materials InfusedEarth = new Materials.Builder().subID(542).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(256).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(0, 255, 0, 0).locName("Terra").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.TERRA, 2)).build();
    public static Materials InfusedWater = new Materials.Builder().subID(543).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(0, 0, 255, 0).locName("Aqua").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AQUA, 2)).build();
    public static Materials InfusedEntropy = new Materials.Builder().subID(544).textureSet(SET_SHARDS).toolSpeed(32.0F).toolDura(64).toolQual(4).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(62, 62, 62, 0).locName("Perditio").fuelType(5).fuelPow(320).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 2)).build();
    public static Materials InfusedOrder = new Materials.Builder().subID(545).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(252, 252, 252, 0).locName("Ordo").fuelType(5).fuelPow(240).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.ORDO, 2)).build();
    public static Materials InfusedVis = new Materials.Builder().subID(-1).textureSet(SET_SHARDS).toolSpeed(8.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(255, 0, 255, 0).locName("Auram").fuelType(5).fuelPow(240).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AURAM, 2)).build();
    public static Materials InfusedDull = new Materials.Builder().subID(-1).textureSet(SET_SHARDS).toolSpeed(32.0F).toolDura(64).toolQual(3).type(DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Vacuus").fuelType(5).fuelPow(160).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.VACUOS, 2)).build();
    public static Materials Inolashite = new Materials.Builder().subID(954).textureSet(SET_NONE).toolSpeed(8.0F).toolDura(2304).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(148, 216, 187, 0).locName("Inolashite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Invisium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST).solidColor(255, 255, 255, 0).locName("Invisium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Jade = new Materials.Builder().subID(537).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | ORE).solidColor(0, 100, 0, 0).locName("Jade").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Jasper = new Materials.Builder().subID(511).textureSet(SET_EMERALD).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | GEM | BGEM | ORE).solidColor(200, 80, 80, 100).locName("Jasper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build();
    public static Materials Kalendrite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(5.0F).toolDura(2560).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | LIQUID).solidColor(170, 91, 189, 0).locName("Kalendrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Komatiite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Komatiite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Lava = new Materials.Builder().subID(700).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(255, 64, 0, 0).locName("Lava").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build();
    public static Materials Lemurite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | LIQUID).solidColor(219, 219, 219, 0).locName("Lemurite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Limestone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Limestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Lodestone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Lodestone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Luminite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(250, 250, 250, 0).locName("Luminite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials Magma = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 64, 0, 0).locName("Magma").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build();
    public static Materials Mawsitsit = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Mawsitsit").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Mercassium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Mercassium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials MeteoricIron = new Materials.Builder().subID(340).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(100, 50, 80, 0).locName("Meteoric Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials MeteoricSteel = new Materials.Builder().subID(341).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(768).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(50, 25, 40, 0).locName("Meteoric Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)).build();
    public static Materials Meteorite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(80, 35, 60, 0).locName("Meteorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build();
    public static Materials Meutoite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE | LIQUID).solidColor(95, 82, 105, 0).locName("Meutoite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Migmatite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Migmatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Mimichite = new Materials.Builder().subID(-1).textureSet(SET_GEM_VERTICAL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE).solidColor(255, 255, 255, 0).locName("Mimichite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Moonstone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Moonstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build();
    public static Materials Naquadah = new Materials.Builder().subID(324).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(50, 50, 50, 0).locName("Naquadah").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build();
    public static Materials NaquadahAlloy = new Materials.Builder().subID(325).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(5120).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(40, 40, 40, 0).locName("Naquadah Alloy").fuelType(0).fuelPow(0).meltAt(7200).blastAt(7200).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)).build();
    public static Materials NaquadahEnriched = new Materials.Builder().subID(326).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1280).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(50, 50, 50, 0).locName("Enriched Naquadah").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 2)).build();
    public static Materials Naquadria = new Materials.Builder().subID(327).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(512).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(30, 30, 30, 0).locName("Naquadria").fuelType(0).fuelPow(0).meltAt(9000).blastAt(9000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.RADIO, 3), new TC_AspectStack(TC_Aspects.NEBRISUM, 3)).build();
    public static Materials Nether = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Nether").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials NetherBrick = new Materials.Builder().subID(814).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(100, 0, 0, 0).locName("Nether Brick").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials NetherQuartz = new Materials.Builder().subID(522).textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(32).toolQual(1).type(DUST | GEM | BGEM | ORE | TOOL).solidColor(230, 210, 210, 0).locName("Nether Quartz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)).build();
    public static Materials NetherStar = new Materials.Builder().subID(506).textureSet(SET_NETHERSTAR).toolSpeed(1.0F).toolDura(5120).toolQual(4).type(DUST | GEM | BGEM | TOOL).solidColor(255, 255, 255, 0).locName("Nether Star").fuelType(5).fuelPow(50000).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials Teslatite = new Materials.Builder().subID(812).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(60, 180, 200, 0).locName("Teslatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 2)).build();
    public static Materials ObsidianFlux = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(80, 50, 100, 0).locName("Fluxed Obsidian").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build();
    public static Materials Oilsands = new Materials.Builder().subID(878).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(10, 10, 10, 0).locName("Oilsands").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Onyx = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Onyx").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Orichalcum = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(4.5F).toolDura(3456).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(84, 122, 56, 0).locName("Orichalcum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Osmonium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Osmonium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).build();
    public static Materials Oureclase = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(1920).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(183, 98, 21, 0).locName("Oureclase").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Painite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Painite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Peanutwood = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Peanut Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Petroleum = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Petroleum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Pewter = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Pewter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Phoenixite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(6.0F).toolDura(64).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 255, 0).locName("Phoenixite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Potash = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Potash").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Prometheum = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(512).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(90, 129, 86, 0).locName("Prometheum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Quartzite = new Materials.Builder().subID(523).textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | GEM | BGEM | ORE).solidColor(210, 230, 210, 0).locName("Quartzite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials Quicklime = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Quicklime").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Randomite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Randomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Rhyolite = new Materials.Builder().subID(875).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Rhyolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Rubracium = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE | LIQUID).solidColor(151, 45, 45, 0).locName("Rubracium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Sand = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Sanguinite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(3.0F).toolDura(4480).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(185, 0, 0, 0).locName("Sanguinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Siltstone = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Siltstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Spinel = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 0).locName("Spinel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Starconium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Starconium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Sugilite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Sugilite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Sunstone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | ORE).solidColor(255, 255, 255, 0).locName("Sunstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)).build();
    public static Materials Tar = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(10, 10, 10, 0).locName("Tar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Tartarite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(20.0F).toolDura(7680).toolQual(5).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID).solidColor(255, 118, 60, 0).locName("Tartarite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Tapazite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Tapazite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build();
    public static Materials Thyrium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Thyrium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Tourmaline = new Materials.Builder().subID(-1).textureSet(SET_RUBY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Tourmaline").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Tritanium = new Materials.Builder().subID(329).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(2560).toolQual(4).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Tritanium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 2)).build();
    public static Materials Turquoise = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(255, 255, 255, 0).locName("Turquoise").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials UUAmplifier = new Materials.Builder().subID(721).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(96, 0, 128, 0).locName("UU-Amplifier").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build();
    public static Materials UUMatter = new Materials.Builder().subID(703).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | LIQUID | CELL).solidColor(128, 0, 196, 0).locName("UU-Matter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build();
    public static Materials Void = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 200).locName("Void").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.VACUOS, 1)).build();
    public static Materials Voidstone = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(0).solidColor(255, 255, 255, 200).locName("Voidstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.VACUOS, 1)).build();
    public static Materials Vulcanite = new Materials.Builder().subID(489).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | LIQUID | TOOL).solidColor(255, 132, 72, 0).locName("Vulcanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Vyroxeres = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(9.0F).toolDura(768).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(85, 224, 1, 0).locName("Vyroxeres").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).build();
    public static Materials Wimalite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ORE).solidColor(255, 255, 255, 0).locName("Wimalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Yellorite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ORE).solidColor(255, 255, 255, 0).locName("Yellorite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Yellorium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 255, 0).locName("Yellorium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Zectium = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE).solidColor(255, 255, 255, 0).locName("Zectium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    /**
     * Circuitry, Batteries and other Technical things
     */
    public static Materials Primitive = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Primitive").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1)).build();
    public static Materials Basic = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Basic").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2)).build();
    public static Materials Good = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Good").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 3)).build();
    public static Materials Advanced = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Advanced").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4)).build();
    public static Materials Data = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Data").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 5)).build();
    public static Materials Elite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Elite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 6)).build();
    public static Materials Master = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Master").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 7)).build();
    public static Materials Ultimate = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Ultimate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8)).build();
    public static Materials Superconductor = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Superconductor").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8)).build();
    public static Materials Infinite = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Infinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).build();
    /**
     * Not possible to determine exact Components
     */
    public static Materials Antimatter = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Antimatter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 9), new TC_AspectStack(TC_Aspects.PERFODIO, 8)).build();
    public static Materials BioFuel = new Materials.Builder().subID(705).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Biofuel").fuelType(0).fuelPow(6).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build();
    public static Materials Biomass = new Materials.Builder().subID(704).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(0, 255, 0, 0).locName("Biomass").fuelType(3).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).build();
    public static Materials Cheese = new Materials.Builder().subID(894).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | ORE).solidColor(255, 255, 0, 0).locName("Cheese").fuelType(0).fuelPow(0).meltAt(320).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Chili = new Materials.Builder().subID(895).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(200, 0, 0, 0).locName("Chili").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Chocolate = new Materials.Builder().subID(886).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(190, 95, 0, 0).locName("Chocolate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    public static Materials Cluster = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 127).locName("Cluster").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials CoalFuel = new Materials.Builder().subID(710).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(50, 50, 70, 0).locName("Coalfuel").fuelType(0).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Cocoa = new Materials.Builder().subID(887).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(190, 95, 0, 0).locName("Cocoa").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    public static Materials Coffee = new Materials.Builder().subID(888).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 75, 0, 0).locName("Coffee").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    public static Materials Creosote = new Materials.Builder().subID(712).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(128, 64, 0, 0).locName("Creosote").fuelType(3).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    public static Materials Ethanol = new Materials.Builder().subID(706).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Ethanol").fuelType(0).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).build();
    public static Materials FishOil = new Materials.Builder().subID(711).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 196, 0, 0).locName("Fish Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2)).build();
    public static Materials Fuel = new Materials.Builder().subID(708).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 255, 0, 0).locName("Diesel").fuelType(0).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Glue = new Materials.Builder().subID(726).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 196, 0, 0).locName("Glue").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 2)).build();
    public static Materials Gunpowder = new Materials.Builder().subID(800).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(128, 128, 128, 0).locName("Gunpowder").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build();
    public static Materials FryingOilHot = new Materials.Builder().subID(727).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 196, 0, 0).locName("Hot Frying Oil").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials Honey = new Materials.Builder().subID(725).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(210, 200, 0, 0).locName("Honey").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Leather = new Materials.Builder().subID(-1).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 150, 80, 127).locName("Leather").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).build();
    public static Materials LimePure = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Pure Lime").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).build();
    public static Materials Lubricant = new Materials.Builder().subID(724).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(255, 196, 0, 0).locName("Lubricant").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build();
    public static Materials McGuffium239 = new Materials.Builder().subID(999).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(200, 50, 150, 0).locName("Mc Guffium 239").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).aspects(new TC_AspectStack(TC_Aspects.ALIENIS, 8), new TC_AspectStack(TC_Aspects.PERMUTATIO, 8), new TC_AspectStack(TC_Aspects.SPIRITUS, 8), new TC_AspectStack(TC_Aspects.AURAM, 8), new TC_AspectStack(TC_Aspects.VITIUM, 8), new TC_AspectStack(TC_Aspects.RADIO, 8), new TC_AspectStack(TC_Aspects.MAGNETO, 8), new TC_AspectStack(TC_Aspects.ELECTRUM, 8), new TC_AspectStack(TC_Aspects.NEBRISUM, 8), new TC_AspectStack(TC_Aspects.STRONTIO, 8)).build();
    public static Materials MeatRaw = new Materials.Builder().subID(892).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 100, 100, 0).locName("Raw Meat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build();
    public static Materials MeatCooked = new Materials.Builder().subID(893).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(150, 60, 20, 0).locName("Cooked Meat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).build();
    public static Materials Milk = new Materials.Builder().subID(885).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID).solidColor(254, 254, 254, 0).locName("Milk").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 2)).build();
    public static Materials Mud = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Mud").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    public static Materials Oil = new Materials.Builder().subID(707).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(10, 10, 10, 0).locName("Oil").fuelType(3).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).build();
    public static Materials Paper = new Materials.Builder().subID(879).textureSet(SET_PAPER).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(250, 250, 250, 0).locName("Paper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1)).build();
    public static Materials Peat = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Peat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build();
    public static Materials Quantum = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Quantum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    public static Materials RareEarth = new Materials.Builder().subID(891).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(128, 128, 100, 0).locName("Rare Earth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.LUCRUM, 1)).build();
    public static Materials Red = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 0, 0, 0).locName("Red").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials Reinforced = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("Reinforced").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).build();
    public static Materials SeedOil = new Materials.Builder().subID(713).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_AspectStack(TC_Aspects.GRANUM, 2)).build();
    public static Materials SeedOilHemp = new Materials.Builder().subID(722).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Hemp Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)).build();
    public static Materials SeedOilLin = new Materials.Builder().subID(723).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST | LIQUID | CELL).solidColor(196, 255, 0, 0).locName("Lin Seed Oil").fuelType(3).fuelPow(2).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)).build();
    public static Materials Stone = new Materials.Builder().subID(299).textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(32).toolQual(1).type(DUST | TOOL | GEAR | SGEAR).solidColor(205, 205, 205, 0).locName("Stone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.TERRA, 1)).build();
    public static Materials TNT = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(0).solidColor(255, 255, 255, 0).locName("TNT").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 7), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build();
    public static Materials Unstable = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(4).type(0).solidColor(255, 255, 255, 127).locName("Unstable").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)).build();
    public static Materials Unstableingot = new Materials.Builder().subID(-1).textureSet(SET_NONE).toolSpeed(1.0F).toolDura(0).toolQual(4).type(0).solidColor(255, 255, 255, 127).locName("Unstable").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)).build();
    public static Materials Wheat = new Materials.Builder().subID(881).textureSet(SET_POWDER).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 255, 196, 0).locName("Wheat").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).aspects(new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 2)).build();
    /**
     * TODO: This
     */
    public static Materials AluminiumBrass = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Aluminium Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Osmiridium = new Materials.Builder().subID(317).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(1600).toolQual(3).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 255, 0).locName("Osmiridium").fuelType(0).fuelPow(0).meltAt(3333).blastAt(2500).needBlast(true).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1)).build();
    public static Materials Sunnarium = new Materials.Builder().subID(318).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(255, 255, 0, 0).locName("Sunnarium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Endstone = new Materials.Builder().subID(808).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(DUST).solidColor(232, 232, 168, 0).locName("Endstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).build();
    public static Materials Netherrack = new Materials.Builder().subID(807).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(200, 0, 0, 0).locName("Netherrack").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).build();
    public static Materials SoulSand = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(DUST).solidColor(255, 255, 255, 0).locName("Soulsand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).build();
    /**
     * First Degree Compounds, Begin Centrifuge and Electrolyzing recipes
     */
    public static Materials Methane = new Materials.Builder().subID(715).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255, 0).locName("Methane").fuelType(1).fuelPow(45).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeMagenta).matList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4)).build();
    public static Materials CarbonDioxide = new Materials.Builder().subID(497).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID  | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Carbon Dioxide").fuelType(0).fuelPow(0).meltAt(25).blastAt(1).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials NobleGases = new Materials.Builder().subID(496).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID  | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Noble Gases").fuelType(0).fuelPow(0).meltAt(4).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(CarbonDioxide, 21), new MaterialStack(Helium, 9), new MaterialStack(Methane, 3), new MaterialStack(Deuterium, 1)).build();
    public static Materials Air = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID  | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("Air").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)).build();
    public static Materials LIQUIDAir = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | LIQUID  | CELL | PLASMA).solidColor(169, 208, 245, 240).locName("LIQUID Air").fuelType(0).fuelPow(0).meltAt(4).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)).build();
    public static Materials Almandine = new Materials.Builder().subID(820).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 0, 0, 0).locName("Almandine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials Andradite = new Materials.Builder().subID(821).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 120, 0, 0).locName("Andradite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Calcium, 3), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials AnnealedCopper = new Materials.Builder().subID(345).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | GEAR | SGEAR).solidColor(255, 120, 20, 0).locName("Annealed Copper").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Copper, 1)).build();
    public static Materials Asbestos = new Materials.Builder().subID(946).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 230, 0).locName("Asbestos").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)).build(); // Mg3Si2O5(OH)4
    public static Materials Ash = new Materials.Builder().subID(815).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(150, 150, 150, 0).locName("Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.PERDITIO, 1)).build();
    public static Materials BandedIron = new Materials.Builder().subID(917).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(145, 90, 90, 0).locName("Banded Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)).build();
    public static Materials BatteryAlloy = new Materials.Builder().subID(315).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(156, 124, 160, 0).locName("Battery Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1)).build();
    public static Materials BlueTopaz = new Materials.Builder().subID(513).textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(0, 0, 255, 127).locName("Blue Topaz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build();
    public static Materials Bone = new Materials.Builder().subID(806).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Bone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Calcium, 1)).aspects(new TC_AspectStack(TC_Aspects.MORTUUS, 2), new TC_AspectStack(TC_Aspects.CORPUS, 1)).build();
    public static Materials Brass = new Materials.Builder().subID(301).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(96).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 180, 0, 0).locName("Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Bronze = new Materials.Builder().subID(300).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(192).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 128, 0, 0).locName("Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials BrownLimonite = new Materials.Builder().subID(930).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Brown Limonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)).build(); // FeO(OH)
    public static Materials Calcite = new Materials.Builder().subID(823).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 230, 220, 0).locName("Calcite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)).build();
    public static Materials Cassiterite = new Materials.Builder().subID(824).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ORE).solidColor(220, 220, 220, 0).locName("Cassiterite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials CassiteriteSand = new Materials.Builder().subID(937).textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ORE).solidColor(220, 220, 220, 0).locName("Cassiterite Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Chalcopyrite = new Materials.Builder().subID(855).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(160, 120, 40, 0).locName("Chalcopyrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)).build();
    public static Materials Chalk = new Materials.Builder().subID(-1).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Chalk").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)).build();
    public static Materials Charcoal = new Materials.Builder().subID(536).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(100, 70, 70, 0).locName("Charcoal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build();
    public static Materials Chromite = new Materials.Builder().subID(825).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(35, 20, 15, 0).locName("Chromite").fuelType(0).fuelPow(0).meltAt(1700).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4)).build();
    public static Materials ChromiumDioxide = new Materials.Builder().subID(361).textureSet(SET_DULL).toolSpeed(11.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(230, 200, 200, 0).locName("Chromium Dioxide").fuelType(0).fuelPow(0).meltAt(650).blastAt(650).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Chrome, 1), new MaterialStack(Oxygen, 2)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)).build();
    public static Materials Cinnabar = new Materials.Builder().subID(826).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 0, 0, 0).locName("Cinnabar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1)).build();
    public static Materials Water = new Materials.Builder().subID(701).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build();
    public static Materials Clay = new Materials.Builder().subID(805).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(200, 200, 220, 0).locName("Clay").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Water, 6)).build();
    public static Materials Coal = new Materials.Builder().subID(535).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(70, 70, 70, 0).locName("Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build();
    public static Materials Cobaltite = new Materials.Builder().subID(827).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(80, 80, 250, 0).locName("Cobaltite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1)).build();
    public static Materials Cooperite = new Materials.Builder().subID(828).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 200, 0).locName("Sheldonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1)).build();
    public static Materials Cupronickel = new Materials.Builder().subID(310).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(227, 150, 128, 0).locName("Cupronickel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1)).build();
    public static Materials DarkAsh = new Materials.Builder().subID(816).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(50, 50, 50, 0).locName("Dark Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.IGNIS, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 1)).build();
    public static Materials DeepIron = new Materials.Builder().subID(829).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(150, 140, 140, 0).locName("Deep Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Iron, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials Diamond = new Materials.Builder().subID(500).textureSet(SET_DIAMOND).toolSpeed(8.0F).toolDura(1280).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL | GEAR | SGEAR).solidColor(200, 255, 255, 127).locName("Diamond").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 4)).build();
    public static Materials Electrum = new Materials.Builder().subID(303).textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL | GEAR | SGEAR).solidColor(255, 255, 100, 0).locName("Electrum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1)).build();
    public static Materials Emerald = new Materials.Builder().subID(501).textureSet(SET_EMERALD).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(80, 255, 80, 127).locName("Emerald").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 5)).build();
    public static Materials FreshWater = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Fresh Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build();
    public static Materials Galena = new Materials.Builder().subID(830).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(100, 60, 100, 0).locName("Galena").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2)).build();
    public static Materials Garnierite = new Materials.Builder().subID(906).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(50, 200, 70, 0).locName("Garnierite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1)).build();
    public static Materials Glyceryl = new Materials.Builder().subID(714).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 150, 150, 0).locName("Glyceryl Trinitrate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9)).build();
    public static Materials GreenSapphire = new Materials.Builder().subID(504).textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 200, 130, 127).locName("Green Sapphire").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Grossular = new Materials.Builder().subID(831).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Grossular").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials HolyWater = new Materials.Builder().subID(729).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Holy Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.AURAM, 1)).build();
    public static Materials Ice = new Materials.Builder().subID(702).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID).solidColor(200, 200, 255, 0).locName("Ice").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.GELUM, 2)).build();
    public static Materials Ilmenite = new Materials.Builder().subID(918).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(70, 55, 50, 0).locName("Ilmenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)).build();
    public static Materials Rutile = new Materials.Builder().subID(375).textureSet(SET_GEM_HORIZONTAL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(212, 13, 92, 0).locName("Rutile").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Bauxite = new Materials.Builder().subID(822).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Bauxite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11)).build();
    public static Materials Titaniumtetrachloride = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(212, 13, 92, 0).locName("Titaniumtetrachloride").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Titanium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Chlorine, 2)).build();
    public static Materials Magnesiumchloride = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID).solidColor(212, 13, 92, 0).locName("Magnesiumchloride").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2)).build();
    public static Materials Invar = new Materials.Builder().subID(302).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 120, 0).locName("Invar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.GELUM, 1)).build();
    public static Materials IronCompressed = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(96).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Compressed Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Iron, 1)).build();
    public static Materials Kanthal = new Materials.Builder().subID(312).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(194, 210, 223, 0).locName("Kanthal").fuelType(0).fuelPow(0).meltAt(1800).blastAt(1800).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1)).build();
    public static Materials Lazurite = new Materials.Builder().subID(524).textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(100, 120, 255, 0).locName("Lazurite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8)).build();
    public static Materials Magnalium = new Materials.Builder().subID(313).textureSet(SET_DULL).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 190, 255, 0).locName("Magnalium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2)).build();
    public static Materials Magnesite = new Materials.Builder().subID(908).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 180, 0).locName("Magnesite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)).build();
    public static Materials Magnetite = new Materials.Builder().subID(870).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(30, 30, 30, 0).locName("Magnetite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials Molybdenite = new Materials.Builder().subID(942).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(25, 25, 25, 0).locName("Molybdenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2)).build(); // MoS2 (also source of Re)
    public static Materials Nichrome = new Materials.Builder().subID(311).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(205, 206, 246, 0).locName("Nichrome").fuelType(0).fuelPow(0).meltAt(2700).blastAt(2700).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1)).build();
    public static Materials NiobiumNitride = new Materials.Builder().subID(359).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(29, 41, 29, 0).locName("Niobium Nitride").fuelType(0).fuelPow(0).meltAt(2573).blastAt(2573).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Niobium, 1), new MaterialStack(Nitrogen, 1)).build(); // Anti-Reflective Material
    public static Materials NiobiumTitanium = new Materials.Builder().subID(360).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(29, 29, 41, 0).locName("Niobium-Titanium").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1)).build();
    public static Materials NitroCarbon = new Materials.Builder().subID(716).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 75, 100, 0).locName("Nitro-Carbon").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Nitrogen, 1), new MaterialStack(Carbon, 1)).build();
    public static Materials NitrogenDioxide = new Materials.Builder().subID(717).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(100, 175, 255, 0).locName("Nitrogen Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Obsidian = new Materials.Builder().subID(804).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST).solidColor(80, 50, 100, 0).locName("Obsidian").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8)).build();
    public static Materials Phosphate = new Materials.Builder().subID(833).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE | LIQUID).solidColor(255, 255, 0, 0).locName("Phosphate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Phosphor, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials PigIron = new Materials.Builder().subID(307).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(200, 180, 180, 0).locName("Pig Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Iron, 1)).build();
    public static Materials Plastic = new Materials.Builder().subID(874).textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Polyethylene").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Epoxid = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 140, 20, 0).locName("Epoxid").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Silicone = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(3.0F).toolDura(128).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(220, 220, 220, 0).locName("Polysiloxane").fuelType(0).fuelPow(0).meltAt(900).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Polycaprolactam = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(50, 50, 50, 0).locName("Polycaprolactam").fuelType(0).fuelPow(0).meltAt(500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Polytetrafluoroethylene = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(3.0F).toolDura(32).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Polytetrafluoroethylene").fuelType(0).fuelPow(0).meltAt(1400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Powellite = new Materials.Builder().subID(883).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 0, 0).locName("Powellite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials Pumice = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(230, 185, 185, 0).locName("Pumice").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Stone, 1)).build();
    public static Materials Pyrite = new Materials.Builder().subID(834).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(150, 120, 40, 0).locName("Pyrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)).build();
    public static Materials Pyrolusite = new Materials.Builder().subID(943).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(150, 150, 170, 0).locName("Pyrolusite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Pyrope = new Materials.Builder().subID(835).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(120, 50, 100, 0).locName("Pyrope").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials RockSalt = new Materials.Builder().subID(944).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(240, 200, 200, 0).locName("Rock Salt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1)).build();
    public static Materials Rubber = new Materials.Builder().subID(880).textureSet(SET_SHINY).toolSpeed(1.5F).toolDura(16).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(0, 0, 0, 0).locName("Rubber").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials RawRubber = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST).solidColor(204, 199, 137, 0).locName("Raw Rubber").fuelType(0).fuelPow(0).meltAt(400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)).aspects(new TC_AspectStack(TC_Aspects.MOTUS, 2)).build();
    public static Materials Ruby = new Materials.Builder().subID(502).textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 100, 100, 127).locName("Ruby").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build();
    public static Materials Salt = new Materials.Builder().subID(817).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 250, 0).locName("Salt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1)).build();
    public static Materials Saltpeter = new Materials.Builder().subID(836).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 230, 0).locName("Saltpeter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)).build();
    public static Materials SaltWater = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(0, 0, 255, 0).locName("Salt Water").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.AQUA, 2)).build();
    public static Materials Sapphire = new Materials.Builder().subID(503).textureSet(SET_GEM_VERTICAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 100, 200, 127).locName("Sapphire").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Scheelite = new Materials.Builder().subID(910).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(200, 140, 20, 0).locName("Scheelite").fuelType(0).fuelPow(0).meltAt(2500).blastAt(2500).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4)).build();
    public static Materials SiliconDioxide = new Materials.Builder().subID(837).textureSet(SET_QUARTZ).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID).solidColor(200, 200, 200, 0).locName("Silicon Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Snow = new Materials.Builder().subID(728).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | LIQUID).solidColor(250, 250, 250, 0).locName("Snow").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)).aspects(new TC_AspectStack(TC_Aspects.GELUM, 1)).build();
    public static Materials Sodalite = new Materials.Builder().subID(525).textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(20, 20, 255, 0).locName("Sodalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1)).build();
    public static Materials SodiumPersulfate = new Materials.Builder().subID(718).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST).solidColor(255, 255, 255, 0).locName("Sodium Persulfate").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Sodium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials SodiumSulfide	= new Materials.Builder().subID( -1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255,0).locName("Sodium Sulfide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange ).matList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1)).build();
    //public static Materials HydricSulfide	= new Materials( -1 SET_FLUID,1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL, 255, 255, 255,0,	"Hydrogen Sulfide",0,0, -1,0, false, false,1,1,1,.dye(dyeOrange ).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1)).build();
//public static Materials OilHeavy = new Materials.Builder().subID( 730).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Heavy Oil" ,3, 32, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//public static Materials OilMedium = new Materials.Builder().subID( 731).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Raw Oil" ,3, 24, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//public static Materials OilLight = new Materials.Builder().subID( 732).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 10, 10, 10,0).locName("Light Oil" ,3, 16, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//public static Materials NatruralGas = new Materials.Builder().subID( 733).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(1, 16, 255, 255, 255,0).locName("Natural Gas"	,1, 15, -1,0, false, false,3,1,1,.dye(dyeWhite	.build();
//public static Materials SulfuricGas = new Materials.Builder().subID( 734).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(1, 16, 255, 255, 255,0).locName("Sulfuric Gas"	,1, 20, -1,0, false, false,3,1,1,.dye(dyeWhite	.build();
    public static Materials Gas = new Materials.Builder().subID(735).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 255, 255, 0).locName("Refinery Gas").fuelType(1).fuelPow(128).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).build();
    //public static Materials SulfuricNaphtha	= new Materials.Builder().subID( 736).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Naphtha",1, 32, -1,0, false, false,1,1,1,.dye(dyeYellow	.build();
//public static Materials SulfuricLightFuel= new Materials.Builder().subID( 737).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Light Fuel" ,0, 32, -1,0, false, false,1,1,1,.dye(dyeYellow	.build();
//public static Materials SulfuricHeavyFuel= new Materials.Builder().subID( 738).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Sulfuric Heavy Fuel" ,3, 32, -1,0, false, false,1,1,1,.dye(dyeBlack	.build();
//public static Materials Naphtha = new Materials.Builder().subID( 739).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Naphtha" ,1, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build();
//public static Materials LightFuel = new Materials.Builder().subID( 740).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Light Fuel"	,0, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build();
//public static Materials HeavyFuel = new Materials.Builder().subID( 741).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("Heavy Fuel"	,3, 192, -1,0, false, false,1,1,1,.dye(dyeBlack).build();
//public static Materials LPG= new Materials.Builder().subID( 742).textureSet(SET_FLUID,1.0F).toolSpeed(0).solidColor(0, 16, 255, 255,0,0).locName("LPG",1, 256, -1,0, false, false,1,1,1,.dye(dyeYellow).build();
    public static Materials SolderingAlloy = new Materials.Builder().subID(314).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(220, 220, 230, 0).locName("Soldering Alloy").fuelType(0).fuelPow(0).meltAt(400).blastAt(400).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1)).build();
    public static Materials Spessartine = new Materials.Builder().subID(838).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(255, 100, 100, 0).locName("Spessartine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials Sphalerite = new Materials.Builder().subID(839).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(255, 255, 255, 0).locName("Sphalerite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1)).build();
    public static Materials StainlessSteel = new Materials.Builder().subID(306).textureSet(SET_SHINY).toolSpeed(7.0F).toolDura(480).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 220, 0).locName("Stainless Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1)).build();
    public static Materials Steel = new Materials.Builder().subID(305).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Steel").fuelType(0).fuelPow(0).meltAt(1811).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)).build();
    public static Materials Stibnite = new Materials.Builder().subID(945).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(70, 70, 70, 0).locName("Stibnite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3)).build();
    public static Materials SulfuricAcid = new Materials.Builder().subID(720).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(255, 128, 0, 0).locName("Sulfuric Acid").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials SulfurDioxide = new Materials.Builder().subID(795).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(186, 155, 46, 0).locName("Sulfur Dioxide").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Tanzanite = new Materials.Builder().subID(508).textureSet(SET_GEM_VERTICAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(64, 0, 200, 127).locName("Tanzanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Tetrahedrite = new Materials.Builder().subID(840).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 32, 0, 0).locName("Tetrahedrite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1)).build(); //Cu3SbS3 + x(Fe,Zn)6Sb2S9
    public static Materials TinAlloy = new Materials.Builder().subID(363).textureSet(SET_METALLIC).toolSpeed(6.5F).toolDura(96).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Tin Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Tin, 1), new MaterialStack(Iron, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Topaz = new Materials.Builder().subID(507).textureSet(SET_GEM_HORIZONTAL).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(255, 128, 0, 127).locName("Topaz").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build();
    public static Materials Tungstate = new Materials.Builder().subID(841).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(55, 50, 35, 0).locName("Tungstate").fuelType(0).fuelPow(0).meltAt(2500).blastAt(2500).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4)).build();
    public static Materials Ultimet = new Materials.Builder().subID(344).textureSet(SET_SHINY).toolSpeed(9.0F).toolDura(2048).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 230, 0).locName("Ultimet").fuelType(0).fuelPow(0).meltAt(2700).blastAt(2700).needBlast(true).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1)).build(); // 54% Cobalt, 26% Chromium, 9% Nickel, 5% Molybdenum, 3% Iron, 2% Tungsten, 0.8% Manganese, 0.3% Silicon, 0.08% Nitrogen and 0.06% Carbon
    public static Materials Uraninite = new Materials.Builder().subID(922).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(35, 35, 35, 0).locName("Uraninite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2)).build();
    public static Materials Uvarovite = new Materials.Builder().subID(842).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(180, 255, 180, 0).locName("Uvarovite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(Calcium, 3), new MaterialStack(Chrome, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)).build();
    public static Materials VanadiumGallium = new Materials.Builder().subID(357).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(128, 128, 140, 0).locName("Vanadium-Gallium").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1)).build();
    public static Materials Wood = new Materials.Builder().subID(809).textureSet(SET_WOOD).toolSpeed(2.0F).toolDura(16).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 50, 0, 0).locName("Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2)).build();
    public static Materials WroughtIron = new Materials.Builder().subID(304).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 180, 180, 0).locName("Wrought Iron").fuelType(0).fuelPow(0).meltAt(1811).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightGray).matList(new MaterialStack(Iron, 1)).build();
    public static Materials Wulfenite = new Materials.Builder().subID(882).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(255, 128, 0, 0).locName("Wulfenite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials YellowLimonite = new Materials.Builder().subID(931).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 200, 0, 0).locName("Yellow Limonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)).build(); // FeO(OH) + a bit Ni and Co
    public static Materials YttriumBariumCuprate = new Materials.Builder().subID(358).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(80, 64, 70, 0).locName("Yttrium Barium Cuprate").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7)).build();
    /**
     * Second Degree Compounds
     */
    public static Materials WoodSealed = new Materials.Builder().subID(889).textureSet(SET_WOOD).toolSpeed(3.0F).toolDura(24).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(80, 40, 0, 0).locName("Sealed Wood").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Wood, 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.FABRICO, 1)).build();
    public static Materials LiveRoot = new Materials.Builder().subID(832).textureSet(SET_WOOD).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(220, 200, 0, 0).locName("Liveroot").fuelType(5).fuelPow(16).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Wood, 3), new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.VICTUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build();
    public static Materials IronWood = new Materials.Builder().subID(338).textureSet(SET_WOOD).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(150, 140, 110, 0).locName("Ironwood").fuelType(5).fuelPow(8).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBrown).matList(new MaterialStack(Iron, 9), new MaterialStack(LiveRoot, 9), new MaterialStack(Gold, 1)).build();
    public static Materials Glass = new Materials.Builder().subID(890).textureSet(SET_GLASS).toolSpeed(1.0F).toolDura(4).toolQual(0).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(250, 250, 250, 220).locName("Glass").fuelType(0).fuelPow(0).meltAt(1500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(SiliconDioxide, 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2)).build();
    public static Materials Perlite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(30, 20, 30, 0).locName("Perlite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Obsidian, 2), new MaterialStack(Water, 1)).build();
    public static Materials Borax = new Materials.Builder().subID(-1).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(250, 250, 250, 0).locName("Borax").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Sodium, 2), new MaterialStack(Boron, 4), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 7)).build();
    public static Materials Lignite = new Materials.Builder().subID(538).textureSet(SET_LIGNITE).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(100, 70, 70, 0).locName("Lignite Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1)).build();
    public static Materials Olivine = new Materials.Builder().subID(505).textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(150, 255, 150, 127).locName("Olivine").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)).build();
    public static Materials Opal = new Materials.Builder().subID(510).textureSet(SET_OPAL).toolSpeed(7.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(0, 0, 255, 0).locName("Opal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(SiliconDioxide, 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Amethyst = new Materials.Builder().subID(509).textureSet(SET_FLINT).toolSpeed(7.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(210, 50, 210, 127).locName("Amethyst").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)).build();
    public static Materials Redstone = new Materials.Builder().subID(810).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 0, 0, 0).locName("Redstone").fuelType(0).fuelPow(0).meltAt(500).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Silicon, 1), new MaterialStack(Pyrite, 5), new MaterialStack(Ruby, 1), new MaterialStack(Mercury, 3)).aspects(new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)).build();
    public static Materials Lapis = new Materials.Builder().subID(526).textureSet(SET_LAPIS).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(70, 70, 220, 0).locName("Lapis").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 1)).build();
    public static Materials Blaze = new Materials.Builder().subID(801).textureSet(SET_POWDER).toolSpeed(2.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | TOOL).solidColor(255, 200, 0, 0).locName("Blaze").fuelType(0).fuelPow(0).meltAt(6400).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 4)).build();
    public static Materials EnderPearl = new Materials.Builder().subID(532).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(108, 220, 200, 0).locName("Enderpearl").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5), new MaterialStack(Magic, 6)).aspects(new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2)).build();
    public static Materials EnderEye = new Materials.Builder().subID(533).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(16).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM).solidColor(160, 250, 230, 0).locName("Endereye").fuelType(5).fuelPow(10).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 4), new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 2)).build();
    public static Materials Flint = new Materials.Builder().subID(802).textureSet(SET_FLINT).toolSpeed(2.5F).toolDura(64).toolQual(1).type(ELEC | CENT | DUST | TOOL).solidColor(0, 32, 64, 0).locName("Flint").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(SiliconDioxide, 1)).aspects(new TC_AspectStack(TC_Aspects.TERRA, 1), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)).build();
    public static Materials Diatomite = new Materials.Builder().subID(948).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(225, 225, 225, 0).locName("Diatomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Flint, 8), new MaterialStack(BandedIron, 1), new MaterialStack(Sapphire, 1)).build();
    public static Materials VolcanicAsh = new Materials.Builder().subID(-1).textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST).solidColor(60, 50, 50, 0).locName("Volcanic Ashes").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Flint, 6), new MaterialStack(Iron, 1), new MaterialStack(Magnesium, 1)).build();
    public static Materials Niter = new Materials.Builder().subID(531).textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(255, 200, 200, 0).locName("Niter").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Saltpeter, 1)).build();
    public static Materials Pyrotheum = new Materials.Builder().subID(843).textureSet(SET_FIERY).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(255, 128, 0, 0).locName("Pyrotheum").fuelType(2).fuelPow(62).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Coal, 1), new MaterialStack(Redstone, 1), new MaterialStack(Blaze, 1)).aspects(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)).build();
    public static Materials HydratedCoal = new Materials.Builder().subID(818).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(70, 70, 100, 0).locName("Hydrated Coal").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Coal, 8), new MaterialStack(Water, 1)).build();
    public static Materials Apatite = new Materials.Builder().subID(530).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(200, 200, 255, 0).locName("Apatite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)).aspects(new TC_AspectStack(TC_Aspects.MESSIS, 2)).build();
    public static Materials Alumite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.5F).toolDura(64).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Alumite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Aluminium, 5), new MaterialStack(Iron, 2), new MaterialStack(Obsidian, 2)).aspects(new TC_AspectStack(TC_Aspects.STRONTIO, 2)).build();
    public static Materials Manyullyn = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.5F).toolDura(64).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(255, 255, 255, 0).locName("Manyullyn").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Cobalt, 1), new MaterialStack(Aredrite, 1)).aspects(new TC_AspectStack(TC_Aspects.STRONTIO, 2)).build();
    public static Materials ShadowIron = new Materials.Builder().subID(336).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(384).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(120, 120, 120, 0).locName("Shadowiron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Iron, 3), new MaterialStack(Magic, 1)).build();
    public static Materials ShadowSteel = new Materials.Builder().subID(337).textureSet(SET_METALLIC).toolSpeed(6.0F).toolDura(768).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(90, 90, 90, 0).locName("Shadowsteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Steel, 3), new MaterialStack(Magic, 1)).build();
    public static Materials Steeleaf = new Materials.Builder().subID(339).textureSet(SET_LEAF).toolSpeed(8.0F).toolDura(768).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(50, 127, 50, 0).locName("Steeleaf").fuelType(5).fuelPow(24).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(Steel, 1), new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.HERBA, 2), new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build();
    public static Materials Knightmetal = new Materials.Builder().subID(362).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1024).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(210, 240, 200, 0).locName("Knightmetal").fuelType(5).fuelPow(24).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(Steel, 2), new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.LUCRUM, 1), new TC_AspectStack(TC_Aspects.METALLUM, 2)).build();
    public static Materials SterlingSilver = new Materials.Builder().subID(350).textureSet(SET_SHINY).toolSpeed(13.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(250, 220, 225, 0).locName("Sterling Silver").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1700).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4)).build();
    public static Materials RoseGold = new Materials.Builder().subID(351).textureSet(SET_SHINY).toolSpeed(14.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(255, 230, 30, 0).locName("Rose Gold").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1600).needBlast(true).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4)).build();
    public static Materials BlackBronze = new Materials.Builder().subID(352).textureSet(SET_DULL).toolSpeed(12.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 50, 125, 0).locName("Black Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(2000).needBlast(true).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Copper, 3)).build();
    public static Materials BismuthBronze = new Materials.Builder().subID(353).textureSet(SET_DULL).toolSpeed(8.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 125, 125, 0).locName("Bismuth Bronze").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1100).needBlast(true).density(MATERIAL_UNIT).dye(dyeCyan).matList(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)).build();
    public static Materials BlackSteel = new Materials.Builder().subID(334).textureSet(SET_METALLIC).toolSpeed(6.5F).toolDura(768).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(100, 100, 100, 0).locName("Black Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1200).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Nickel, 1), new MaterialStack(BlackBronze, 1), new MaterialStack(Steel, 3)).build();
    public static Materials RedSteel = new Materials.Builder().subID(348).textureSet(SET_METALLIC).toolSpeed(7.0F).toolDura(896).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(140, 100, 100, 0).locName("Red Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1300).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(SterlingSilver, 1), new MaterialStack(BismuthBronze, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)).build();
    public static Materials BlueSteel = new Materials.Builder().subID(349).textureSet(SET_METALLIC).toolSpeed(7.5F).toolDura(1024).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(100, 100, 140, 0).locName("Blue Steel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(1400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(RoseGold, 1), new MaterialStack(Brass, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)).build();
    public static Materials DamascusSteel = new Materials.Builder().subID(335).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(1280).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL).solidColor(110, 110, 110, 0).locName("Damascus Steel").fuelType(0).fuelPow(0).meltAt(2000).blastAt(1500).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Steel, 1)).build();
    public static Materials TungstenSteel = new Materials.Builder().subID(316).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(2560).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 160, 0).locName("Tungstensteel").fuelType(0).fuelPow(0).meltAt(-1).blastAt(3000).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1)).build();
    public static Materials NitroCoalFuel = new Materials.Builder().subID(-1).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(50, 70, 50, 0).locName("Nitro-Coalfuel").fuelType(0).fuelPow(48).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Glyceryl, 1), new MaterialStack(CoalFuel, 4)).build();
    public static Materials NitroFuel = new Materials.Builder().subID(709).textureSet(SET_FLUID).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | LIQUID | CELL).solidColor(200, 255, 0, 0).locName("Nitro-Diesel").fuelType(0).fuelPow(512).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLime).matList(new MaterialStack(Glyceryl, 1), new MaterialStack(Fuel, 4)).build();
    public static Materials AstralSilver = new Materials.Builder().subID(333).textureSet(SET_SHINY).toolSpeed(10.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(230, 230, 255, 0).locName("Astral Silver").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Silver, 2), new MaterialStack(Magic, 1)).build();
    public static Materials Midasium = new Materials.Builder().subID(332).textureSet(SET_SHINY).toolSpeed(12.0F).toolDura(64).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 200, 40, 0).locName("Midasium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Gold, 2), new MaterialStack(Magic, 1)).build();
    public static Materials Mithril = new Materials.Builder().subID(331).textureSet(SET_SHINY).toolSpeed(14.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | ORE | TOOL).solidColor(255, 255, 210, 0).locName("Mithril").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Platinum, 2), new MaterialStack(Magic, 1)).build();
    public static Materials BlueAlloy = new Materials.Builder().subID(309).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(100, 180, 255, 0).locName("Blue Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Silver, 1), new MaterialStack(Teslatite, 4)).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 3)).build();
    public static Materials RedAlloy = new Materials.Builder().subID(308).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(200, 0, 0, 0).locName("Red Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Metal, 1), new MaterialStack(Redstone, 4)).aspects(new TC_AspectStack(TC_Aspects.MACHINA, 3)).build();
    public static Materials CobaltBrass = new Materials.Builder().subID(343).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(180, 180, 160, 0).locName("Cobalt Brass").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(Brass, 7), new MaterialStack(Aluminium, 1), new MaterialStack(Cobalt, 1)).build();
    public static Materials Phosphorus = new Materials.Builder().subID(534).textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | LIQUID).solidColor(255, 255, 0, 0).locName("Phosphorus").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2)).build();
    public static Materials Basalt = new Materials.Builder().subID(844).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(30, 20, 20, 0).locName("Basalt").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)).aspects(new TC_AspectStack(TC_Aspects.TENEBRAE, 1)).build();
    public static Materials GarnetRed = new Materials.Builder().subID(527).textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 80, 80, 127).locName("Red Garnet").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Pyrope, 3), new MaterialStack(Almandine, 5), new MaterialStack(Spessartine, 8)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials GarnetYellow = new Materials.Builder().subID(528).textureSet(SET_RUBY).toolSpeed(7.0F).toolDura(128).toolQual(2).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 200, 80, 127).locName("Yellow Garnet").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Andradite, 5), new MaterialStack(Grossular, 8), new MaterialStack(Uvarovite, 3)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 3)).build();
    public static Materials Marble = new Materials.Builder().subID(845).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(200, 200, 200, 0).locName("Marble").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)).aspects(new TC_AspectStack(TC_Aspects.PERFODIO, 1)).build();
    public static Materials Sugar = new Materials.Builder().subID(803).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(250, 250, 250, 0).locName("Sugar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)).aspects(new TC_AspectStack(TC_Aspects.HERBA, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.AER, 1)).build();
    public static Materials Thaumium = new Materials.Builder().subID(330).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(256).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(150, 100, 200, 0).locName("Thaumium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Iron, 1), new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build();
    public static Materials Vinteum = new Materials.Builder().subID(529).textureSet(SET_EMERALD).toolSpeed(10.0F).toolDura(128).toolQual(3).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(100, 200, 255, 0).locName("Vinteum").fuelType(5).fuelPow(32).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeLightBlue).matList(new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build();
    public static Materials Vis = new Materials.Builder().subID(-1).textureSet(SET_SHINY).toolSpeed(1.0F).toolDura(0).toolQual(3).type(0).solidColor(128, 0, 255, 0).locName("Vis").fuelType(5).fuelPow(32).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(Magic, 1)).aspects(new TC_AspectStack(TC_Aspects.AURAM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)).build();
    public static Materials Redrock = new Materials.Builder().subID(846).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(255, 80, 50, 0).locName("Redrock").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(Calcite, 2), new MaterialStack(Flint, 1), new MaterialStack(Clay, 1)).build();
    public static Materials PotassiumFeldspar = new Materials.Builder().subID(847).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(120, 40, 40, 0).locName("Potassium Feldspar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePink).matList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8)).build();
    public static Materials Biotite = new Materials.Builder().subID(848).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(20, 30, 20, 0).locName("Biotite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10)).build();
    public static Materials GraniteBlack = new Materials.Builder().subID(849).textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | TOOL | GEAR | SGEAR).solidColor(10, 10, 10, 0).locName("Black Granite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Biotite, 1)).aspects(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build();
    public static Materials GraniteRed = new Materials.Builder().subID(850).textureSet(SET_ROUGH).toolSpeed(4.0F).toolDura(64).toolQual(3).type(ELEC | CENT | DUST | TOOL | GEAR | SGEAR).solidColor(255, 0, 128, 0).locName("Red Granite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeMagenta).matList(new MaterialStack(Aluminium, 2), new MaterialStack(PotassiumFeldspar, 1), new MaterialStack(Oxygen, 3)).aspects(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)).build();
    public static Materials Chrysotile = new Materials.Builder().subID(912).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(110, 140, 110, 0).locName("Chrysotile").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Asbestos, 1)).build();
    public static Materials Realgar = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(140, 100, 100, 0).locName("Realgar").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 4)).build();
    public static Materials VanadiumMagnetite = new Materials.Builder().subID(923).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(35, 35, 60, 0).locName("Vanadium Magnetite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build(); // Mixture of Fe3O4 and V2O5
    public static Materials BasalticMineralSand = new Materials.Builder().subID(935).textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(40, 50, 40, 0).locName("Basaltic Mineral Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Magnetite, 1), new MaterialStack(Basalt, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials GraniticMineralSand = new Materials.Builder().subID(936).textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(40, 60, 60, 0).locName("Granitic Mineral Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Magnetite, 1), new MaterialStack(GraniteBlack, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials GarnetSand = new Materials.Builder().subID(938).textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 100, 0, 0).locName("Garnet Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeOrange).matList(new MaterialStack(GarnetRed, 1), new MaterialStack(GarnetYellow, 1)).build();
    public static Materials QuartzSand = new Materials.Builder().subID(939).textureSet(SET_SAND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(200, 200, 200, 0).locName("Quartz Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(CertusQuartz, 1), new MaterialStack(Quartzite, 1)).build();
    public static Materials Bastnasite = new Materials.Builder().subID(905).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 110, 45, 0).locName("Bastnasite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3)).build(); // (Ce, La, Y)CO3F
    public static Materials Pentlandite = new Materials.Builder().subID(909).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(165, 150, 5, 0).locName("Pentlandite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8)).build(); // (Fe,Ni)9S8
    public static Materials Spodumene = new Materials.Builder().subID(920).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(190, 170, 170, 0).locName("Spodumene").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6)).build(); // LiAl(SiO3)2
    public static Materials Pollucite = new Materials.Builder().subID(919).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 210, 210, 0).locName("Pollucite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12)).build(); // (Cs,Na)2Al2Si4O12 2H2O (also a source of Rb)
    public static Materials Tantalite = new Materials.Builder().subID(921).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(145, 80, 40, 0).locName("Tantalite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Manganese, 1), new MaterialStack(Tantalum, 2), new MaterialStack(Oxygen, 6)).build(); // (Fe, Mn)Ta2O6 (also source of Nb)
    public static Materials Lepidolite = new Materials.Builder().subID(907).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 50, 140, 0).locName("Lepidolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)).build(); // K(Li,Al,Rb)3(Al,Si)4O10(F,OH)2
    public static Materials Glauconite = new Materials.Builder().subID(933).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(130, 180, 60, 0).locName("Glauconite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)).build(); // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
    public static Materials GlauconiteSand = new Materials.Builder().subID(949).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(130, 180, 60, 0).locName("Glauconite Sand").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)).build(); // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
    public static Materials Vermiculite = new Materials.Builder().subID(932).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(200, 180, 15, 0).locName("Vermiculite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Iron, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 12)).build(); // (Mg+2, Fe+2, Fe+3)3 [(AlSi)4O10] (OH)2 4H2O)
    public static Materials Bentonite = new Materials.Builder().subID(927).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(245, 215, 210, 0).locName("Bentonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Sodium, 1), new MaterialStack(Magnesium, 6), new MaterialStack(Silicon, 12), new MaterialStack(Hydrogen, 6), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 36)).build(); // (Na,Ca)0.33(Al,Mg)2(Si4O10)(OH)2 nH2O
    public static Materials FullersEarth = new Materials.Builder().subID(928).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(160, 160, 120, 0).locName("Fullers Earth").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Magnesium, 1), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 11)).build(); // (Mg,Al)2Si4O10(OH) 4(H2O)
    public static Materials Pitchblende = new Materials.Builder().subID(873).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(3).type(ELEC | CENT | DUST | ORE).solidColor(200, 210, 0, 0).locName("Pitchblende").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1)).build();
    public static Materials Monazite = new Materials.Builder().subID(520).textureSet(SET_DIAMOND).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | GEM | BGEM | ORE).solidColor(50, 70, 50, 0).locName("Monazite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(RareEarth, 1), new MaterialStack(Phosphate, 1)).build(); // Wikipedia: (Ce, La, Nd, Th, Sm, Gd)PO4 Monazite also smelt-extract to Helium, it is brown like the rare earth Item Monazite sand deposits are inevitably of the monazite-(Ce) composition. Typically, the lanthanides in such monazites contain about 45ִ8% cerium, about 24% lanthanum, about 17% neodymium, about 5% praseodymium, and minor quantities of samarium, gadolinium, and yttrium. Europium concentrations tend to be low, about 0.05% Thorium content of monazite is variable and sometimes can be up to 20ֳ0%
    public static Materials Malachite = new Materials.Builder().subID(871).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(5, 95, 5, 0).locName("Malachite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGreen).matList(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5)).build(); // Cu2CO3(OH)2
    public static Materials Mirabilite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 250, 210, 0).locName("Mirabilite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4)).build(); // Na2SO4 10H2O
    public static Materials Mica = new Materials.Builder().subID(-1).textureSet(SET_FINE).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(195, 195, 205, 0).locName("Mica").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)).build(); // KAl2(AlSi3O10)(F,OH)2
    public static Materials Trona = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(135, 135, 95, 0).locName("Trona").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6)).build(); // Na3(CO3)(HCO3) 2H2O
    public static Materials Barite = new Materials.Builder().subID(904).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(230, 235, 255, 0).locName("Barite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)).build();
    public static Materials Gypsum = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(230, 230, 250, 0).locName("Gypsum").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4)).build(); // CaSO4 2H2O
    public static Materials Alunite = new Materials.Builder().subID(-1).textureSet(SET_METALLIC).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(225, 180, 65, 0).locName("Alunite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14)).build(); // KAl3(SO4)2(OH)6
    public static Materials Dolomite = new Materials.Builder().subID(-1).textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(225, 205, 205, 0).locName("Dolomite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6)).build(); // CaMg(CO3)2
    public static Materials Wollastonite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 240, 240, 0).locName("Wollastonite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3)).build(); // CaSiO3
    public static Materials Zeolite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(240, 230, 230, 0).locName("Zeolite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72)).build(); // NaCa4(Si27Al9)O72 28(H2O)
    public static Materials Kyanite = new Materials.Builder().subID(-1).textureSet(SET_FLINT).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(110, 110, 250, 0).locName("Kyanite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5)).build(); // Al2SiO5
    public static Materials Kaolinite = new Materials.Builder().subID(-1).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(245, 235, 235, 0).locName("Kaolinite").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)).build(); // Al2Si2O5(OH)4
    public static Materials Talc = new Materials.Builder().subID(902).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(2).type(ELEC | CENT | DUST | ORE).solidColor(90, 180, 90, 0).locName("Talc").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)).build(); // H2Mg3(SiO3)4
    public static Materials Soapstone = new Materials.Builder().subID(877).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST | ORE).solidColor(95, 145, 95, 0).locName("Soapstone").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(Dyes._NULL).matList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)).build(); // H2Mg3(SiO3)4
    public static Materials Concrete = new Materials.Builder().subID(947).textureSet(SET_ROUGH).toolSpeed(1.0F).toolDura(0).toolQual(1).type(ELEC | CENT | DUST).solidColor(100, 100, 100, 0).locName("Concrete").fuelType(0).fuelPow(0).meltAt(300).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Stone, 1)).aspects(new TC_AspectStack(TC_Aspects.TERRA, 1)).build();
    public static Materials IronMagnetic = new Materials.Builder().subID(354).textureSet(SET_MAGNETIC).toolSpeed(6.0F).toolDura(256).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(200, 200, 200, 0).locName("Magnetic Iron").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Iron, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials SteelMagnetic = new Materials.Builder().subID(355).textureSet(SET_MAGNETIC).toolSpeed(6.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(128, 128, 128, 0).locName("Magnetic Steel").fuelType(0).fuelPow(0).meltAt(1000).blastAt(1000).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Steel, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 1)).build();
    public static Materials NeodymiumMagnetic = new Materials.Builder().subID(356).textureSet(SET_MAGNETIC).toolSpeed(7.0F).toolDura(512).toolQual(2).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(100, 100, 100, 0).locName("Magnetic Neodymium").fuelType(0).fuelPow(0).meltAt(1297).blastAt(1297).needBlast(true).density(MATERIAL_UNIT).dye(dyeGray).matList(new MaterialStack(Neodymium, 1)).aspects(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 3)).build();
    public static Materials TungstenCarbide = new Materials.Builder().subID(370).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(5120).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(24, 28, 48, 0).locName("Tungsten Carbide").fuelType(0).fuelPow(0).meltAt(2460).blastAt(2460).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlack).matList(new MaterialStack(Carbon, 1), new MaterialStack(Tungsten, 1)).build();
    public static Materials VanadiumSteel = new Materials.Builder().subID(371).textureSet(SET_METALLIC).toolSpeed(8.0F).toolDura(2560).toolQual(3).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(192, 192, 192, 0).locName("Vanadium Steel").fuelType(0).fuelPow(0).meltAt(1453).blastAt(1453).needBlast(true).density(MATERIAL_UNIT).dye(dyeWhite).matList(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7)).build();
    public static Materials HSSG = new Materials.Builder().subID(372).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(7680).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(153, 153, 0, 0).locName("HSS-G").fuelType(0).fuelPow(0).meltAt(4500).blastAt(4500).needBlast(true).density(MATERIAL_UNIT).dye(dyeYellow).matList(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1)).build();
    public static Materials HSSE = new Materials.Builder().subID(373).textureSet(SET_METALLIC).toolSpeed(12.0F).toolDura(10240).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(51, 102, 0, 0).locName("HSS-E").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeBlue).matList(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1), new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1)).build();
    public static Materials HSSS = new Materials.Builder().subID(374).textureSet(SET_METALLIC).toolSpeed(16.0F).toolDura(7680).toolQual(4).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME | TOOL | GEAR | SGEAR).solidColor(102, 0, 51, 0).locName("HSS-S").fuelType(0).fuelPow(0).meltAt(5400).blastAt(5400).needBlast(true).density(MATERIAL_UNIT).dye(dyeRed).matList(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1)).build();
    public static Materials Vibranium = new Materials.Builder().subID(970).textureSet(SET_EMERALD).toolSpeed(100.0F).toolDura(512).toolQual(6).type(ELEC | CENT | DUST | GEM | BGEM | ORE | TOOL).solidColor(200, 128, 255, 127).locName("Vibranium").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).aspects(new TC_AspectStack(TC_Aspects.SENSUS, 10), new TC_AspectStack(TC_Aspects.VITREUS, 10)).build();
    public static Materials PurpleAlloy = new Materials.Builder().subID(521).textureSet(SET_DULL).toolSpeed(1.0F).toolDura(0).toolQual(0).type(ELEC | CENT | DUST | PLATE | STICK | RING | BOLT | FWIRE | ROTOR | FRAME).solidColor(225, 80, 232, 0).locName("Purple Alloy").fuelType(0).fuelPow(0).meltAt(-1).blastAt(0).needBlast(false).density(MATERIAL_UNIT).dye(dyePurple).matList(new MaterialStack(BlueAlloy, 1), new MaterialStack(RedAlloy, 1)).aspects(new TC_AspectStack(TC_Aspects.ELECTRUM, 3)).build();

    /**
     * Materials which are renamed automatically
     */
    @Deprecated
    public static Materials IridiumAndSodiumOxide = new Materials(IridiumSodiumOxide, false);
    @Deprecated
    public static Materials Palygorskite = new Materials(FullersEarth, false);
    @Deprecated
    public static Materials Adamantine = new Materials(Adamantium, true);
    @Deprecated
    public static Materials Ashes = new Materials(Ash, false);
    @Deprecated
    public static Materials DarkAshes = new Materials(DarkAsh, false);
    @Deprecated
    public static Materials Abyssal = new Materials(Basalt, false);
    @Deprecated
    public static Materials Adamant = new Materials(Adamantium, true);
    @Deprecated
    public static Materials AluminumBrass = new Materials(AluminiumBrass, false);
    @Deprecated
    public static Materials Aluminum = new Materials(Aluminium, false);
    @Deprecated
    public static Materials NaturalAluminum = new Materials(Aluminium, false);
    @Deprecated
    public static Materials NaturalAluminium = new Materials(Aluminium, false);
    @Deprecated
    public static Materials Americum = new Materials(Americium, false);
    @Deprecated
    public static Materials Beryl = new Materials(Emerald, false);
    @Deprecated
    public static Materials BlackGranite = new Materials(GraniteBlack, false);
    @Deprecated
    public static Materials CalciumCarbonate = new Materials(Calcite, false);
    @Deprecated
    public static Materials CreosoteOil = new Materials(Creosote, false);
    @Deprecated
    public static Materials Chromium = new Materials(Chrome, false);
    @Deprecated
    public static Materials Diesel = new Materials(Fuel, false);
    @Deprecated
    public static Materials Enderpearl = new Materials(EnderPearl, false);
    @Deprecated
    public static Materials Endereye = new Materials(EnderEye, false);
    @Deprecated
    public static Materials EyeOfEnder = new Materials(EnderEye, false);
    @Deprecated
    public static Materials Eyeofender = new Materials(EnderEye, false);
    @Deprecated
    public static Materials Flour = new Materials(Wheat, false);
    @Deprecated
    public static Materials Meat = new Materials(MeatRaw, false);
    @Deprecated
    public static Materials Garnet = new Materials(GarnetRed, true);
    @Deprecated
    public static Materials Granite = new Materials(GraniteBlack, false);
    @Deprecated
    public static Materials Goethite = new Materials(BrownLimonite, false);
    @Deprecated
    public static Materials Kalium = new Materials(Potassium, false);
    @Deprecated
    public static Materials Lapislazuli = new Materials(Lapis, false);
    @Deprecated
    public static Materials LapisLazuli = new Materials(Lapis, false);
    @Deprecated
    public static Materials Monazit = new Materials(Monazite, false);
    @Deprecated
    public static Materials Natrium = new Materials(Sodium, false);
    @Deprecated
    public static Materials Mythril = new Materials(Mithril, false);
    @Deprecated
    public static Materials NitroDiesel = new Materials(NitroFuel, false);
    @Deprecated
    public static Materials Naquadriah = new Materials(Naquadria, false);
    @Deprecated
    public static Materials Obby = new Materials(Obsidian, false);
    @Deprecated
    public static Materials Peridot = new Materials(Olivine, true);
    @Deprecated
    public static Materials Phosphorite = new Materials(Phosphorus, true);
    @Deprecated
    public static Materials Quarried = new Materials(Marble, false);
    @Deprecated
    public static Materials Quicksilver = new Materials(Mercury, true);
    @Deprecated
    public static Materials QuickSilver = new Materials(Mercury, false);
    @Deprecated
    public static Materials RedRock = new Materials(Redrock, false);
    @Deprecated
    public static Materials RefinedIron = new Materials(Iron, false);
    @Deprecated
    public static Materials RedGranite = new Materials(GraniteRed, false);
    @Deprecated
    public static Materials Sheldonite = new Materials(Cooperite, false);
    @Deprecated
    public static Materials Soulsand = new Materials(SoulSand, false);
    @Deprecated
    public static Materials Titan = new Materials(Titanium, false);
    @Deprecated
    public static Materials Uran = new Materials(Uranium, false);
    @Deprecated
    public static Materials Wolframite = new Materials(Tungstate, false);
    @Deprecated
    public static Materials Wolframium = new Materials(Tungsten, false);
    @Deprecated
    public static Materials Wolfram = new Materials(Tungsten, false);

    static {
        initSubTags();
        Iron.mOreReRegistrations.add(AnyIron);
        PigIron.mOreReRegistrations.add(AnyIron);
        WroughtIron.mOreReRegistrations.add(AnyIron);

        Copper.mOreReRegistrations.add(AnyCopper);
        AnnealedCopper.mOreReRegistrations.add(AnyCopper);

        Bronze.mOreReRegistrations.add(AnyBronze);

        Peanutwood.setMaceratingInto(Wood);
        WoodSealed.setMaceratingInto(Wood);
        NetherBrick.setMaceratingInto(Netherrack);


        NeodymiumMagnetic.setSmeltingInto(Neodymium).setMaceratingInto(Neodymium).setArcSmeltingInto(Neodymium);
        SteelMagnetic.setSmeltingInto(Steel).setMaceratingInto(Steel).setArcSmeltingInto(Steel);
        Iron.setSmeltingInto(Iron).setMaceratingInto(Iron).setArcSmeltingInto(WroughtIron);
        AnyIron.setSmeltingInto(Iron).setMaceratingInto(Iron).setArcSmeltingInto(WroughtIron);
        PigIron.setSmeltingInto(Iron).setMaceratingInto(Iron).setArcSmeltingInto(WroughtIron);
        WroughtIron.setSmeltingInto(Iron).setMaceratingInto(Iron).setArcSmeltingInto(WroughtIron);
        IronMagnetic.setSmeltingInto(Iron).setMaceratingInto(Iron).setArcSmeltingInto(WroughtIron);
        Copper.setSmeltingInto(Copper).setMaceratingInto(Copper).setArcSmeltingInto(AnnealedCopper);
        AnyCopper.setSmeltingInto(Copper).setMaceratingInto(Copper).setArcSmeltingInto(AnnealedCopper);
        AnnealedCopper.setSmeltingInto(Copper).setMaceratingInto(Copper).setArcSmeltingInto(AnnealedCopper);
        Netherrack.setSmeltingInto(NetherBrick);
        MeatRaw.setSmeltingInto(MeatCooked);
        Sand.setSmeltingInto(Glass);
        Ice.setSmeltingInto(Water);
        Snow.setSmeltingInto(Water);

        Mercury.add(SubTag.SMELTING_TO_GEM);
        Cinnabar.setDirectSmelting(Mercury).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT).add(SubTag.SMELTING_TO_GEM);
        /*Tetrahedrite			.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Chalcopyrite			.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Malachite				.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Pentlandite				.setDirectSmelting(Nickel		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Sphalerite				.setDirectSmelting(Zinc			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Pyrite					.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        BasalticMineralSand		.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        GraniticMineralSand		.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        YellowLimonite			.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        BrownLimonite			.setDirectSmelting(Iron			);
        BandedIron				.setDirectSmelting(Iron			);
        Cassiterite				.setDirectSmelting(Tin			);
        CassiteriteSand			.setDirectSmelting(Tin			);
        Chromite				.setDirectSmelting(Chrome		);
        Garnierite				.setDirectSmelting(Nickel		);
        Cobaltite				.setDirectSmelting(Cobalt		);
        Stibnite				.setDirectSmelting(Antimony		);
        Cooperite				.setDirectSmelting(Platinum		);
        Pyrolusite				.setDirectSmelting(Manganese	);
        Magnesite				.setDirectSmelting(Magnesium	);
        Molybdenite				.setDirectSmelting(Molybdenum	);*/

        Amber.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedAir.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedFire.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedEarth.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedWater.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedEntropy.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedOrder.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedVis.setOreMultiplier(2).setSmeltingMultiplier(2);
        InfusedDull.setOreMultiplier(2).setSmeltingMultiplier(2);
        Salt.setOreMultiplier(2).setSmeltingMultiplier(2);
        RockSalt.setOreMultiplier(2).setSmeltingMultiplier(2);
        Scheelite.setOreMultiplier(2).setSmeltingMultiplier(2);
        Tungstate.setOreMultiplier(2).setSmeltingMultiplier(2);
        Cassiterite.setOreMultiplier(2).setSmeltingMultiplier(2);
        CassiteriteSand.setOreMultiplier(2).setSmeltingMultiplier(2);
        NetherQuartz.setOreMultiplier(2).setSmeltingMultiplier(2);
        CertusQuartz.setOreMultiplier(2).setSmeltingMultiplier(2);
        Phosphorus.setOreMultiplier(3).setSmeltingMultiplier(3);
        Saltpeter.setOreMultiplier(4).setSmeltingMultiplier(4);
        Apatite.setOreMultiplier(4).setSmeltingMultiplier(4).setByProductMultiplier(2);
        Teslatite.setOreMultiplier(5).setSmeltingMultiplier(5);
        Redstone.setOreMultiplier(5).setSmeltingMultiplier(5);
        Glowstone.setOreMultiplier(5).setSmeltingMultiplier(5);
        Lapis.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Sodalite.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Lazurite.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Monazite.setOreMultiplier(8).setSmeltingMultiplier(8).setByProductMultiplier(2);

        Plastic.setEnchantmentForTools(Enchantment.knockback, 1);
        Rubber.setEnchantmentForTools(Enchantment.knockback, 2);
        InfusedAir.setEnchantmentForTools(Enchantment.knockback, 2);

        IronWood.setEnchantmentForTools(Enchantment.fortune, 1);
        Steeleaf.setEnchantmentForTools(Enchantment.fortune, 2);
        Midasium.setEnchantmentForTools(Enchantment.fortune, 2);
        Mithril.setEnchantmentForTools(Enchantment.fortune, 3);
        Vinteum.setEnchantmentForTools(Enchantment.fortune, 1);
        Thaumium.setEnchantmentForTools(Enchantment.fortune, 2);
        InfusedWater.setEnchantmentForTools(Enchantment.fortune, 3);
        Vibranium.setEnchantmentForTools(Enchantment.fortune, 5);

        Flint.setEnchantmentForTools(Enchantment.fireAspect, 1);
        DarkIron.setEnchantmentForTools(Enchantment.fireAspect, 2);
        Firestone.setEnchantmentForTools(Enchantment.fireAspect, 3);
        FierySteel.setEnchantmentForTools(Enchantment.fireAspect, 3);
        Pyrotheum.setEnchantmentForTools(Enchantment.fireAspect, 3);
        Blaze.setEnchantmentForTools(Enchantment.fireAspect, 3);
        InfusedFire.setEnchantmentForTools(Enchantment.fireAspect, 3);

        Amber.setEnchantmentForTools(Enchantment.silkTouch, 1);
        EnderPearl.setEnchantmentForTools(Enchantment.silkTouch, 1);
        Enderium.setEnchantmentForTools(Enchantment.silkTouch, 1);
        NetherStar.setEnchantmentForTools(Enchantment.silkTouch, 1);
        InfusedOrder.setEnchantmentForTools(Enchantment.silkTouch, 1);

        BlackBronze.setEnchantmentForTools(Enchantment.smite, 2);
        Gold.setEnchantmentForTools(Enchantment.smite, 3);
        RoseGold.setEnchantmentForTools(Enchantment.smite, 4);
        Platinum.setEnchantmentForTools(Enchantment.smite, 5);
        InfusedVis.setEnchantmentForTools(Enchantment.smite, 5);

        Lead.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        Nickel.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        Invar.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        Antimony.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        BatteryAlloy.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        Bismuth.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        BismuthBronze.setEnchantmentForTools(Enchantment.baneOfArthropods, 5);
        InfusedEarth.setEnchantmentForTools(Enchantment.baneOfArthropods, 5);

        Iron.setEnchantmentForTools(Enchantment.sharpness, 1);
        Bronze.setEnchantmentForTools(Enchantment.sharpness, 1);
        Brass.setEnchantmentForTools(Enchantment.sharpness, 2);
        HSLA.setEnchantmentForTools(Enchantment.sharpness, 2);
        Steel.setEnchantmentForTools(Enchantment.sharpness, 2);
        WroughtIron.setEnchantmentForTools(Enchantment.sharpness, 2);
        StainlessSteel.setEnchantmentForTools(Enchantment.sharpness, 3);
        Knightmetal.setEnchantmentForTools(Enchantment.sharpness, 3);
        ShadowIron.setEnchantmentForTools(Enchantment.sharpness, 3);
        ShadowSteel.setEnchantmentForTools(Enchantment.sharpness, 4);
        BlackSteel.setEnchantmentForTools(Enchantment.sharpness, 4);
        RedSteel.setEnchantmentForTools(Enchantment.sharpness, 4);
        BlueSteel.setEnchantmentForTools(Enchantment.sharpness, 5);
        DamascusSteel.setEnchantmentForTools(Enchantment.sharpness, 5);
        InfusedEntropy.setEnchantmentForTools(Enchantment.sharpness, 5);
        TungstenCarbide.setEnchantmentForTools(Enchantment.sharpness, 5);
        HSSE.setEnchantmentForTools(Enchantment.sharpness, 5);
        HSSG.setEnchantmentForTools(Enchantment.sharpness, 4);
        HSSS.setEnchantmentForTools(Enchantment.sharpness, 5);

        InfusedAir.setEnchantmentForArmors(Enchantment.respiration, 3);

        InfusedFire.setEnchantmentForArmors(Enchantment.featherFalling, 4);

        Steeleaf.setEnchantmentForArmors(Enchantment.protection, 2);
        Knightmetal.setEnchantmentForArmors(Enchantment.protection, 1);
        InfusedEarth.setEnchantmentForArmors(Enchantment.protection, 4);

        InfusedEntropy.setEnchantmentForArmors(Enchantment.thorns, 3);

        InfusedWater.setEnchantmentForArmors(Enchantment.aquaAffinity, 1);
        IronWood.setEnchantmentForArmors(Enchantment.aquaAffinity, 1);

        InfusedOrder.setEnchantmentForArmors(Enchantment.projectileProtection, 4);

        InfusedDull.setEnchantmentForArmors(Enchantment.blastProtection, 4);

        InfusedVis.setEnchantmentForArmors(Enchantment.protection, 4);

        Vibranium.setEnchantmentForTools(Enchantment.looting, 5);

        FryingOilHot.setHeatDamage(1.0F);
        Lava.setHeatDamage(3.0F);
        Firestone.setHeatDamage(5.0F);
        Pyrotheum.setHeatDamage(5.0F);

        Chalcopyrite.addOreByProducts(Pyrite, Cobalt, Cadmium, Gold);
        Sphalerite.addOreByProducts(GarnetYellow, Cadmium, Gallium, Zinc);
        MeteoricIron.addOreByProducts(Iron, Nickel, Iridium, Platinum);
        GlauconiteSand.addOreByProducts(Sodium, Aluminium, Iron);
        Glauconite.addOreByProducts(Sodium, Aluminium, Iron);
        Vermiculite.addOreByProducts(Iron, Aluminium, Magnesium);
        FullersEarth.addOreByProducts(Aluminium, Silicon, Magnesium);
        Bentonite.addOreByProducts(Aluminium, Calcium, Magnesium);
        Uraninite.addOreByProducts(Uranium, Thorium, Uranium235);
        Pitchblende.addOreByProducts(Thorium, Uranium, Lead);
        Galena.addOreByProducts(Sulfur, Silver, Lead);
        Lapis.addOreByProducts(Lazurite, Sodalite, Pyrite);
        Pyrite.addOreByProducts(Sulfur, Phosphorus, Iron);
        Copper.addOreByProducts(Cobalt, Gold, Nickel);
        Nickel.addOreByProducts(Cobalt, Platinum, Iron);
        GarnetRed.addOreByProducts(Spessartine, Pyrope, Almandine);
        GarnetYellow.addOreByProducts(Andradite, Grossular, Uvarovite);
        Cooperite.addOreByProducts(Palladium, Nickel, Iridium);
        Cinnabar.addOreByProducts(Redstone, Sulfur, Glowstone);
        Tantalite.addOreByProducts(Manganese, Niobium, Tantalum);
        Pollucite.addOreByProducts(Caesium, Aluminium, Rubidium);
        Chrysotile.addOreByProducts(Asbestos, Silicon, Magnesium);
        Asbestos.addOreByProducts(Asbestos, Silicon, Magnesium);
        Pentlandite.addOreByProducts(Iron, Sulfur, Cobalt);
        Uranium.addOreByProducts(Lead, Uranium235, Thorium);
        Scheelite.addOreByProducts(Manganese, Molybdenum, Calcium);
        Tungstate.addOreByProducts(Manganese, Silver, Lithium);
        Bauxite.addOreByProducts(Grossular, Rutile, Gallium);
        QuartzSand.addOreByProducts(CertusQuartz, Quartzite, Barite);
        Quartzite.addOreByProducts(CertusQuartz, Barite);
        CertusQuartz.addOreByProducts(Quartzite, Barite);
        Redstone.addOreByProducts(Cinnabar, RareEarth, Glowstone);
        Monazite.addOreByProducts(Thorium, Neodymium, RareEarth);
        Forcicium.addOreByProducts(Thorium, Neodymium, RareEarth);
        Forcillium.addOreByProducts(Thorium, Neodymium, RareEarth);
        Malachite.addOreByProducts(Copper, BrownLimonite, Calcite);
        YellowLimonite.addOreByProducts(Nickel, BrownLimonite, Cobalt);
        BrownLimonite.addOreByProducts(Malachite, YellowLimonite);
        Neodymium.addOreByProducts(Monazite, RareEarth);
        Bastnasite.addOreByProducts(Neodymium, RareEarth);
        Glowstone.addOreByProducts(Redstone, Gold);
        Zinc.addOreByProducts(Tin, Gallium);
        Tungsten.addOreByProducts(Manganese, Molybdenum);
        Diatomite.addOreByProducts(BandedIron, Sapphire);
        Iron.addOreByProducts(Nickel, Tin);
        Lepidolite.addOreByProducts(Lithium, Caesium);
        Gold.addOreByProducts(Copper, Nickel);
        Tin.addOreByProducts(Iron, Zinc);
        Antimony.addOreByProducts(Zinc, Iron);
        Silver.addOreByProducts(Lead, Sulfur);
        Lead.addOreByProducts(Silver, Sulfur);
        Thorium.addOreByProducts(Uranium, Lead);
        Plutonium.addOreByProducts(Uranium, Lead);
        Electrum.addOreByProducts(Gold, Silver);
        Bronze.addOreByProducts(Copper, Tin);
        Brass.addOreByProducts(Copper, Zinc);
        Coal.addOreByProducts(Lignite, Thorium);
        Ilmenite.addOreByProducts(Iron, Rutile);
        Manganese.addOreByProducts(Chrome, Iron);
        Sapphire.addOreByProducts(Aluminium, GreenSapphire);
        GreenSapphire.addOreByProducts(Aluminium, Sapphire);
        Platinum.addOreByProducts(Nickel, Iridium);
        Emerald.addOreByProducts(Beryllium, Aluminium);
        Olivine.addOreByProducts(Pyrope, Magnesium);
        Chrome.addOreByProducts(Iron, Magnesium);
        Chromite.addOreByProducts(Iron, Magnesium);
        Tetrahedrite.addOreByProducts(Antimony, Zinc);
        GarnetSand.addOreByProducts(GarnetRed, GarnetYellow);
        Magnetite.addOreByProducts(Iron, Gold);
        GraniticMineralSand.addOreByProducts(GraniteBlack, Magnetite);
        BasalticMineralSand.addOreByProducts(Basalt, Magnetite);
        Basalt.addOreByProducts(Olivine, DarkAsh);
        VanadiumMagnetite.addOreByProducts(Magnetite, Vanadium);
        Lazurite.addOreByProducts(Sodalite, Lapis);
        Sodalite.addOreByProducts(Lazurite, Lapis);
        Spodumene.addOreByProducts(Aluminium, Lithium);
        Ruby.addOreByProducts(Chrome, GarnetRed);
        Phosphorus.addOreByProducts(Apatite, Phosphate);
        Iridium.addOreByProducts(Platinum, Osmium);
        Pyrope.addOreByProducts(GarnetRed, Magnesium);
        Almandine.addOreByProducts(GarnetRed, Aluminium);
        Spessartine.addOreByProducts(GarnetRed, Manganese);
        Andradite.addOreByProducts(GarnetYellow, Iron);
        Grossular.addOreByProducts(GarnetYellow, Calcium);
        Uvarovite.addOreByProducts(GarnetYellow, Chrome);
        Calcite.addOreByProducts(Andradite, Malachite);
        NaquadahEnriched.addOreByProducts(Naquadah, Naquadria);
        Naquadah.addOreByProducts(NaquadahEnriched);
        Pyrolusite.addOreByProducts(Manganese);
        Molybdenite.addOreByProducts(Molybdenum);
        Stibnite.addOreByProducts(Antimony);
        Garnierite.addOreByProducts(Nickel);
        Lignite.addOreByProducts(Coal);
        Diamond.addOreByProducts(Graphite);
        Beryllium.addOreByProducts(Emerald);
        Apatite.addOreByProducts(Phosphorus);
        Teslatite.addOreByProducts(Diamond);
        Magnesite.addOreByProducts(Magnesium);
        NetherQuartz.addOreByProducts(Netherrack);
        PigIron.addOreByProducts(Iron);
        DeepIron.addOreByProducts(Iron);
        ShadowIron.addOreByProducts(Iron);
        DarkIron.addOreByProducts(Iron);
        MeteoricIron.addOreByProducts(Iron);
        Steel.addOreByProducts(Iron);
        HSLA.addOreByProducts(Iron);
        Mithril.addOreByProducts(Platinum);
        Midasium.addOreByProducts(Gold);
        AstralSilver.addOreByProducts(Silver);
        Graphite.addOreByProducts(Carbon);
        Netherrack.addOreByProducts(Sulfur);
        Flint.addOreByProducts(Obsidian);
        Cobaltite.addOreByProducts(Cobalt);
        Cobalt.addOreByProducts(Cobaltite);
        Sulfur.addOreByProducts(Sulfur);
        Saltpeter.addOreByProducts(Saltpeter);
        Endstone.addOreByProducts(Helium_3);
        Osmium.addOreByProducts(Iridium);
        Magnesium.addOreByProducts(Olivine);
        Aluminium.addOreByProducts(Bauxite);
        Titanium.addOreByProducts(Almandine);
        Obsidian.addOreByProducts(Olivine);
        Ash.addOreByProducts(Carbon);
        DarkAsh.addOreByProducts(Carbon);
        Redrock.addOreByProducts(Clay);
        Marble.addOreByProducts(Calcite);
        Clay.addOreByProducts(Clay);
        Cassiterite.addOreByProducts(Tin);
        CassiteriteSand.addOreByProducts(Tin);
        GraniteBlack.addOreByProducts(Biotite);
        GraniteRed.addOreByProducts(PotassiumFeldspar);
        Phosphate.addOreByProducts(Phosphor);
        Phosphor.addOreByProducts(Phosphate);
        Tanzanite.addOreByProducts(Opal);
        Opal.addOreByProducts(Tanzanite);
        Amethyst.addOreByProducts(Amethyst);
        FoolsRuby.addOreByProducts(Jasper);
        Amber.addOreByProducts(Amber);
        Topaz.addOreByProducts(BlueTopaz);
        BlueTopaz.addOreByProducts(Topaz);
        Niter.addOreByProducts(Saltpeter);
        Vinteum.addOreByProducts(Vinteum);
        Dilithium.addOreByProducts(Dilithium);
        Neutronium.addOreByProducts(Neutronium);
        Lithium.addOreByProducts(Lithium);
        Silicon.addOreByProducts(SiliconDioxide);
        Salt.addOreByProducts(RockSalt);
        RockSalt.addOreByProducts(Salt);

        Glue.mChemicalFormula = "No Horses were harmed for the Production";
        UUAmplifier.mChemicalFormula = "Accelerates the Mass Fabricator";
        LiveRoot.mChemicalFormula = "";
        WoodSealed.mChemicalFormula = "";
        Wood.mChemicalFormula = "";
        FoolsRuby.mChemicalFormula = Ruby.mChemicalFormula;

        Naquadah.mMoltenColor.setARGB(0x00ff00);
        NaquadahEnriched.mMoltenColor.setARGB(0x40ff40);
        Naquadria.mMoltenColor.setARGB(0x80ff80);

        NaquadahEnriched.mChemicalFormula = "Nq+";
        Naquadah.mChemicalFormula = "Nq";
        Naquadria.mChemicalFormula = "NqX";
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
    private List<TC_Aspects.TC_AspectStack> mAspects = new ArrayList<>();
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

    private Materials() {
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
     * @deprecated by {@link Materials.Builder}
     */
    @SuppressWarnings({
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aToolDurability, int aToolQuality, boolean aUnifiable) {
        VALUES.add(this);
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
     * @deprecated by {@link Materials.Builder}
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
     * @deprecated by {@link Materials.Builder}
     */
    @SuppressWarnings({
            "squid:S00107", // 21 Parameters is the reason for deprecating
            "squid:S1133",  // Known deprecation, method to be removed
    })
    @Deprecated
    public Materials(int aMetaItemSubID, TextureSet aTextureSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aDye) {
        this(aMetaItemSubID, aTextureSet, aToolSpeed, aDurability, aToolQuality, true);
        setName(getDefaultLocalName().replaceAll(" ", ""));
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
     * @deprecated by {@link Materials.Builder}
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
     * @deprecated by {@link Materials.Builder}
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
     * @deprecated by {@link Materials.Builder}
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
     * @deprecated by {@link Materials.Builder}
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
        SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM.addTo(Bastnasite, Monazite, Forcicium, Forcillium);
        SubTag.ELECTROMAGNETIC_SEPERATION_GOLD.addTo(Magnetite, VanadiumMagnetite, BasalticMineralSand, GraniticMineralSand);
        SubTag.ELECTROMAGNETIC_SEPERATION_IRON.addTo(YellowLimonite, BrownLimonite, Pyrite, BandedIron, Nickel, Vermiculite, Glauconite, GlauconiteSand, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Chromite, Andradite);
        SubTag.BLASTFURNACE_CALCITE_DOUBLE.addTo(Pyrite, YellowLimonite, BasalticMineralSand, GraniticMineralSand);
        SubTag.BLASTFURNACE_CALCITE_TRIPLE.addTo(Iron, PigIron, DeepIron, ShadowIron, WroughtIron, MeteoricIron, BrownLimonite);
        SubTag.WASHING_MERCURY.addTo(Gold, Silver, Osmium, Mithril, Platinum, Midasium, Cooperite, AstralSilver);
        SubTag.WASHING_SODIUMPERSULFATE.addTo(Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite);
        SubTag.WASHING_BLUEV.addTo(Copper, Tetrahedrite, Chalcopyrite, Teslatite, Gold, Malachite);
        SubTag.WASHING_GREENV.addTo(Chromite, Chrome, Ilmenite, Iron, Magnetite, Tungstate, Andradite, Pyrite);
        SubTag.WASHING_NICKELS.addTo(Nickel, Pentlandite);
        SubTag.METAL.addTo(AnyIron, AnyCopper, AnyBronze, Metal, Aluminium, Americium, Antimony, Beryllium, Bismuth, Caesium, Cerium, Chrome, Cobalt, Copper, Dysprosium, Erbium, Europium, Gadolinium, Gallium, Gold,
                Holmium, Indium, Iridium, Iron, Lanthanum, Lead, Lutetium, Magnesium, Manganese, Mercury, Niobium, Molybdenum, Neodymium, Neutronium, Nickel, Osmium, Palladium, Platinum, Plutonium, Plutonium241,
                Praseodymium, Promethium, Rubidium, Samarium, Scandium, Silicon, Silver, Tantalum, Terbium, Thorium, Thulium, Tin, Titanium, Tungsten, Uranium, Uranium235, Vanadium, Ytterbium, Yttrium,
                Zinc, Serpentine, Signalum, Lumium, PhasedIron, PhasedGold, DarkSteel, Terrasteel, TinAlloy, ConductiveIron, ElectricalSteel, EnergeticAlloy, VibrantAlloy,
                PulsatingIron, Manasteel, DarkThaumium, ElvenElementium, EnrichedCopper, DiamondCopper, Adamantium, Amordrine, Angmallen, Ardite, Aredrite, Atlarus, Blutonium, Carmot, Celenegil, Ceruclase, DarkIron,
                Desh, Desichalkos, Duranium, ElectrumFlux, Enderium, EnderiumBase, Eximite, FierySteel, Haderoth, Hematite, Hepatizon, HSLA, Infuscolium, InfusedGold, Inolashite, Mercassium, MeteoricIron,
                MeteoricSteel, Naquadah, NaquadahAlloy, NaquadahEnriched, Naquadria, ObsidianFlux, Orichalcum, Osmonium, Oureclase, Phoenixite, Prometheum, Sanguinite, Starconium,
                Tartarite, Thyrium, Tritanium, Vulcanite, Vyroxeres, Yellorium, Zectium, AluminiumBrass, Osmiridium, Sunnarium, AnnealedCopper, BatteryAlloy, Brass, Bronze, ChromiumDioxide, Cupronickel, DeepIron,
                Electrum, Invar, IronCompressed, Kanthal, Magnalium, Nichrome, NiobiumNitride, NiobiumTitanium, PigIron, SolderingAlloy, StainlessSteel, Steel, Ultimet, VanadiumGallium, WroughtIron,
                YttriumBariumCuprate, IronWood, Alumite, Manyullyn, ShadowIron, ShadowSteel, Steeleaf, SterlingSilver, RoseGold, BlackBronze, BismuthBronze, BlackSteel, RedSteel, BlueSteel, DamascusSteel,
                TungstenSteel, AstralSilver, Midasium, Mithril, BlueAlloy, RedAlloy, CobaltBrass, Thaumium, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, Knightmetal, VanadiumSteel, HSSG, HSSE, HSSS);

        SubTag.FOOD.addTo(MeatRaw, MeatCooked, Ice, Water, Salt, Chili, Cocoa, Cheese, Coffee, Chocolate, Milk, Honey, FryingOilHot, FishOil, SeedOil, SeedOilLin, SeedOilHemp, Wheat, Sugar, FreshWater);

        Wood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        WoodSealed.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.NO_WORKING);
        Peanutwood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        LiveRoot.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        IronWood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        Steeleaf.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE, SubTag.NO_SMELTING);

        MeatRaw.add(SubTag.NO_SMASHING);
        MeatCooked.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Snow.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Ice.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Water.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Sulfur.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Saltpeter.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Graphite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.NO_SMELTING);

        Wheat.add(SubTag.FLAMMABLE, SubTag.MORTAR_GRINDABLE);
        Paper.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE, SubTag.PAPER);
        Coal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Charcoal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Lignite.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);

        Rubber.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
        Plastic.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY);

        TNT.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        Gunpowder.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        Glyceryl.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroCoalFuel.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroFuel.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroCarbon.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);

        Lead.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_BAD);
        Tin.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL);
        SolderingAlloy.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_GOOD);

        Cheese.add(SMELTING_TO_FLUID);
        Sugar.add(SMELTING_TO_FLUID);

        Concrete.add(SubTag.STONE, SubTag.NO_SMASHING, SMELTING_TO_FLUID);
        ConstructionFoam.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.EXPLOSIVE, SubTag.NO_SMELTING);
        Redstone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID, SubTag.PULVERIZING_CINNABAR);
        Glowstone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID);
        Teslatite.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SMELTING_TO_FLUID);
        Netherrack.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.FLAMMABLE);
        Stone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.NO_RECYCLING);
        Brick.add(SubTag.STONE, SubTag.NO_SMASHING);
        NetherBrick.add(SubTag.STONE, SubTag.NO_SMASHING);
        Endstone.add(SubTag.STONE, SubTag.NO_SMASHING);
        Marble.add(SubTag.STONE, SubTag.NO_SMASHING);
        Basalt.add(SubTag.STONE, SubTag.NO_SMASHING);
        Redrock.add(SubTag.STONE, SubTag.NO_SMASHING);
        Obsidian.add(SubTag.STONE, SubTag.NO_SMASHING);
        Flint.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        GraniteRed.add(SubTag.STONE, SubTag.NO_SMASHING);
        GraniteBlack.add(SubTag.STONE, SubTag.NO_SMASHING);
        Salt.add(SubTag.STONE, SubTag.NO_SMASHING);
        RockSalt.add(SubTag.STONE, SubTag.NO_SMASHING);

        Sand.add(SubTag.NO_RECYCLING);

        Gold.add(SubTag.MORTAR_GRINDABLE);
        Silver.add(SubTag.MORTAR_GRINDABLE);
        Iron.add(SubTag.MORTAR_GRINDABLE);
        IronMagnetic.add(SubTag.MORTAR_GRINDABLE);
        HSLA.add(SubTag.MORTAR_GRINDABLE);
        Steel.add(SubTag.MORTAR_GRINDABLE);
        SteelMagnetic.add(SubTag.MORTAR_GRINDABLE);
        Zinc.add(SubTag.MORTAR_GRINDABLE);
        Antimony.add(SubTag.MORTAR_GRINDABLE);
        Copper.add(SubTag.MORTAR_GRINDABLE);
        AnnealedCopper.add(SubTag.MORTAR_GRINDABLE);
        Bronze.add(SubTag.MORTAR_GRINDABLE);
        Nickel.add(SubTag.MORTAR_GRINDABLE);
        Invar.add(SubTag.MORTAR_GRINDABLE);
        Brass.add(SubTag.MORTAR_GRINDABLE);
        WroughtIron.add(SubTag.MORTAR_GRINDABLE);
        Electrum.add(SubTag.MORTAR_GRINDABLE);
        Clay.add(SubTag.MORTAR_GRINDABLE);

        Glass.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_RECYCLING, SMELTING_TO_FLUID);
        Diamond.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Emerald.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Amethyst.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Tanzanite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Topaz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        BlueTopaz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Amber.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GreenSapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Sapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Ruby.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        FoolsRuby.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Opal.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Olivine.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Jasper.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetRed.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetYellow.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Mimichite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        CrystalFlux.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Crystal.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Niter.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Apatite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lapis.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Sodalite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lazurite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Monazite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Quartzite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Quartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        SiliconDioxide.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Dilithium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        NetherQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        CertusQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Fluix.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Phosphorus.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        Phosphate.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        InfusedAir.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedFire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedEarth.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedWater.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedEntropy.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedOrder.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedVis.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedDull.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        Vinteum.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        NetherStar.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        EnderPearl.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        EnderEye.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        Firestone.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL, SubTag.QUARTZ, SubTag.UNBURNABLE, SubTag.BURNING);
        Forcicium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        Forcillium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        Magic.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);

        Primitive.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Basic.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Good.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Advanced.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Data.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Elite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Master.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Ultimate.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Superconductor.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Infinite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);

        Blaze.add(SubTag.MAGICAL, SubTag.NO_SMELTING, SMELTING_TO_FLUID, SubTag.MORTAR_GRINDABLE, SubTag.UNBURNABLE, SubTag.BURNING);
        FierySteel.add(SubTag.MAGICAL, SubTag.UNBURNABLE, SubTag.BURNING);
        ElvenElementium.add(SubTag.MAGICAL);
        DarkThaumium.add(SubTag.MAGICAL);
        Thaumium.add(SubTag.MAGICAL);
        Enderium.add(SubTag.MAGICAL);
        AstralSilver.add(SubTag.MAGICAL);
        Midasium.add(SubTag.MAGICAL);
        Mithril.add(SubTag.MAGICAL);
    }

    public static void initCustomMats(String aCustomOreConfigLabel) {
        //TODO MAIN: increase sGeneratedMaterials limit from 1,000 to 10,000.
        //TODO good way to only get the rest of the data (Tool qual etc) one and not twice per for loop (one for default and custom).
        //TODO split this into a new method
        for (Materials tMaterial : Materials.VALUES) {
            if (tMaterial.mMetaItemSubID >= 0) {
                tMaterial.mMetaItemSubID = GregTech_API.sMaterialsFile.get("materials.default." + tMaterial.mDefaultLocalName, "MaterialID", tMaterial.mMetaItemSubID);
                tMaterial.mDurability = GregTech_API.sMaterialsFile.get("materials.default." + tMaterial.mDefaultLocalName, "Durability", tMaterial.mDurability);
                if (tMaterial == Desh) {
                    tMaterial.setHandleMaterial(tMaterial.mHandleMaterial);
                } else if (tMaterial == Diamond
                        || tMaterial == Thaumium) {
                    tMaterial.setHandleMaterial(Wood);
                } else if (tMaterial.contains(SubTag.BURNING)) {
                    tMaterial.setHandleMaterial(Blaze);
                } else if (tMaterial.contains(SubTag.MAGICAL)
                        && tMaterial.contains(SubTag.CRYSTAL)
                        && Loader.isModLoaded(MOD_ID_TC)) {
                    tMaterial.setHandleMaterial(Thaumium);
                } else if (tMaterial.getMass() > Element.Tc.getMass()) {
                    if (tMaterial.getMass() > Element.Tc.getMass() * 2) {
                        tMaterial.setHandleMaterial(TungstenSteel);
                    } else tMaterial.setHandleMaterial(Steel);
                } else {
                    if (tMaterial.getMass() > Element.Tc.getMass() * 2) {
                        tMaterial.setHandleMaterial(TungstenSteel);
                    } else {
                        tMaterial.setHandleMaterial(Wood);
                    }
                }
            }
        }
        //TODO make this minetweaker friendly?
        //TODO register fluids
        int i = 0;
        StringBuilder aCustomMatNum = new StringBuilder();
        for (int j = GregTech_API.sMaterialsFile.get("general", "AmountOfCustomMaterialSlots", 16); i < j; i++) {
            aCustomMatNum.append("materials.").append(aCustomOreConfigLabel).append(".custommat").append(i < 10 ? "0" : "").append(i);
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
        Object tObject = GT_Utility.getFieldContent(Materials.class, aMaterialName, false, false);
        if (tObject instanceof Materials) return (Materials) tObject;
        return _NULL;
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
     * Example: {@link Materials#Chromite} giving {@link Materials#Chrome} or {@link Materials#Tungstate} giving {@link Materials#Tungsten}.
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

    public static class Builder {
        private int mMetaItemSubID;
        private Materials rMaterials = new Materials();
        private GT_Color mSolidColor = new GT_Color(0x00ffffff);
        private GT_Color mMoltenColor = new GT_Color(0x00ffffff);
        private TextureSet mTextureSet;
        private boolean mUnifiable;
        private Materials mMaterialInto;
        private List<MaterialStack> mMaterialList = new ArrayList<>();
        private List<Materials> mOreByProducts = new ArrayList<>();
        private List<Materials> mOreReRegistrations = new ArrayList<>();
        private List<TC_Aspects.TC_AspectStack> mAspects = new ArrayList<>();
        private List<ItemStack> mMaterialItems = new ArrayList<>();
        private List<SubTag> mSubTags = new ArrayList<>();
        private Enchantment mEnchantmentTools = null;
        private byte mEnchantmentToolsLevel = 0;
        private Enchantment mEnchantmentArmors = null;
        private byte mEnchantmentArmorsLevel = 0;
        private boolean mBlastFurnaceRequired = false;
        private float mToolSpeed = 1.0F;
        private float mHeatDamage = 0.0F;
        private String mChemicalFormula = "?";
        private String mName = "null";
        private String mDefaultLocalName = "null";
        private Dyes mDye = Dyes._NULL;
        private short mMeltingPoint = 0;
        private short mBlastFurnaceTemp = 0;
        private int mTypes = 0;
        private int mToolDurability = 16;
        private byte mToolQuality = 0;
        private int mFuelPower = 0;
        private int mFuelType = 0;
        private int mExtraData = 0;
        private int mOreValue = 0;
        private int mOreMultiplier = 1;
        private int mByProductMultiplier = 1;
        private int mSmeltingMultiplier = 1;
        private long mDensity = MATERIAL_UNIT;
        private long mDensityMultiplier = 0;
        private long mDensityDivider = 0;
        private Element mElement = null;
        private Materials mDirectSmelting = rMaterials;
        private Materials mOreReplacement = rMaterials;
        private Materials mMacerateInto = rMaterials;
        private Materials mSmeltInto = rMaterials;
        private Materials mArcSmeltInto = rMaterials;
        private Materials mHandleMaterial = rMaterials;
        private Fluid mSolid = null;
        private Fluid mFluid = null;
        private Fluid mGas = null;
        private Fluid mPlasma = null;
        private Fluid mStandardMoltenFluid = null;

        /**
         * Sets the {@link Materials}'s meta-sub identifier
         *
         * @param aMetaItemSubID The meta-sub identifier for items
         * @return the {@link Builder}
         * @throws IllegalArgumentException if SubID already assigned
         */
        public Builder subID(int aMetaItemSubID) {
            if (aMetaItemSubID >= 0) {
                if (GregTech_API.sGeneratedMaterials[aMetaItemSubID] == null) {
                    this.mMetaItemSubID = aMetaItemSubID;
                } else {
                    throw new IllegalArgumentException("The Index " + aMetaItemSubID + " is already used!");
                }
            }
            return this;
        }

        /**
         * Set the {@link Materials}'s ARGB color
         *
         * @param aARGB integer packed ARGB.
         *              example: solidRGBa(0x00FF8020)
         *              Alpha = 0x00
         *              Red = 0xff
         *              Green = 0x80
         *              Blue = 0x20
         * @return the {@link Builder}
         */
        public Builder solidColor(int aARGB) {
            this.mSolidColor.setARGB(aARGB);
            return this;
        }

        public Builder solidColor(int r, int g, int b, int alpha) {
            this.mSolidColor.setRGBA(r, g, b, alpha);
            return this;
        }


        /**
         * Set the {@link Materials}'s molten ARGB color
         *
         * @param aARGB integer packed ARGB.
         *              example: solidRGBa(0x00FF8020)
         *              Alpha = 0x00
         *              Red = 0xff
         *              Green = 0x80
         *              Blue = 0x20
         * @return the {@link Builder}
         */
        public Builder moltenColor(int aARGB) {
            this.mMoltenColor.setARGB(aARGB);
            return this;
        }

        public Builder textureSet(TextureSet aTextureSet) {
            this.mTextureSet = aTextureSet;
            return this;
        }

        public Builder unifiable(boolean aUnifiable) {
            this.mUnifiable = aUnifiable;
            return this;
        }

        public Builder matInto(Materials aMaterialInto) {
            this.mMaterialInto = aMaterialInto;
            return this;
        }

        public Builder matList(MaterialStack... aMaterialList) {
            this.mMaterialList = Arrays.asList(aMaterialList);
            return this;
        }

        public Builder byProducts(Materials... aByProducts) {
            this.mOreByProducts = Arrays.asList(aByProducts);
            return this;
        }

        public Builder oreRegistrations(Materials... aOres) {
            this.mOreReRegistrations = Arrays.asList(aOres);
            return this;
        }

        public Builder aspects(TC_Aspects.TC_AspectStack... aAspects) {
            this.mAspects = Arrays.asList(aAspects);
            return this;
        }

        public Builder items(ItemStack... aItemsStacks) {
            this.mMaterialItems = Arrays.asList(aItemsStacks);
            return this;
        }

        public Builder subTags(SubTag... aSubTags) {
            this.mSubTags = Arrays.asList(aSubTags);
            return this;
        }

        public Builder toolsEnchant(Enchantment aEnchantment) {
            this.mEnchantmentTools = aEnchantment;
            return this;
        }

        public Builder toolsEnchantLvl(byte aEnchantmentLevel) {
            this.mEnchantmentToolsLevel = aEnchantmentLevel;
            return this;
        }

        public Builder armorEnchant(Enchantment aEnchantment) {
            this.mEnchantmentArmors = aEnchantment;
            return this;
        }

        public Builder armorEnchantLvl(byte aEnchantmentLevel) {
            this.mEnchantmentArmorsLevel = aEnchantmentLevel;
            return this;
        }

        public Builder needBlast(boolean aNeedBlastFurnace) {
            this.mBlastFurnaceRequired = aNeedBlastFurnace;
            return this;
        }

        public Builder toolSpeed(float aToolSpeed) {
            this.mToolSpeed = aToolSpeed;
            return this;
        }

        public Builder heatDamage(float aHeatDamage) {
            this.mHeatDamage = aHeatDamage;
            return this;
        }

        public Builder toolTip(String aChemicalFormula) {
            this.mChemicalFormula = aChemicalFormula;
            return this;
        }

        public Builder name(String aName) {
            this.mName = aName;
            return this;
        }

        public Builder locName(String aDefaultLocalName) {
            this.mDefaultLocalName = aDefaultLocalName;
            return this;
        }

        public Builder dye(Dyes aDye) {
            this.mDye = aDye;
            return this;
        }

        public Builder meltAt(int aKelvins) {
            this.mMeltingPoint = (short) aKelvins;
            return this;
        }

        public Builder blastAt(int aKelvin) {
            this.mBlastFurnaceTemp = (short) aKelvin;
            return this;
        }

        public Builder type(int aType) {
            this.mTypes = aType;
            return this;
        }

        public Builder toolDura(int aDurability) {
            this.mToolDurability = aDurability;
            return this;
        }

        public Builder toolQual(int aQuality) {
            this.mToolQuality = (byte) aQuality;
            return this;
        }

        public Builder fuelPow(int aBurnValue) {
            this.mFuelPower = aBurnValue;
            return this;
        }

        public Builder fuelType(int aType) {
            this.mFuelType = aType;
            return this;
        }

        public Builder eData(int aData) {
            this.mExtraData = aData;
            return this;
        }

        public Builder oreVal(int aValue) {
            this.mOreValue = aValue;
            return this;
        }

        public Builder oreMult(int aMult) {
            this.mOreMultiplier = aMult;
            return this;
        }

        public Builder byProdMult(int aMult) {
            this.mByProductMultiplier = aMult;
            return this;
        }

        public Builder smeltMult(int aMult) {
            this.mSmeltingMultiplier = aMult;
            return this;
        }

        public Builder density(long aDensity) {
            this.mDensity = aDensity;
            return this;
        }

        public Builder elem(Element aElement) {
            this.mElement = aElement;
            return this;
        }

        public Builder directSmelt(Materials aMaterial) {
            this.mDirectSmelting = aMaterial;
            return this;
        }

        public Builder oreRep(Materials aMaterial) {
            this.mOreReplacement = aMaterial;
            return this;
        }

        public Builder macerInto(Materials aMaterial) {
            this.mMacerateInto = aMaterial;
            return this;
        }

        public Builder smeltInto(Materials aMaterial) {
            this.mSmeltInto = aMaterial;
            return this;
        }

        public Builder arcSmeltInto(Materials aMaterial) {
            this.mArcSmeltInto = aMaterial;
            return this;
        }

        public Builder handleMat(Materials aMaterial) {
            this.mHandleMaterial = aMaterial;
            return this;
        }

        public Builder solid(Fluid aFluid) {
            this.mSolid = aFluid;
            return this;
        }

        public Builder fluid(Fluid aFluid) {
            this.mFluid = aFluid;
            return this;
        }

        public Builder gas(Fluid aFluid) {
            this.mGas = aFluid;
            return this;
        }

        public Builder plasma(Fluid aFluid) {
            this.mPlasma = aFluid;
            return this;
        }

        public Builder stdMolten(Fluid aFluid) {
            this.mStandardMoltenFluid = aFluid;
            return this;
        }

        public Builder densityMult(long aMultiplier) {
            this.mDensityMultiplier = aMultiplier;
            return this;
        }

        public Builder densityDiv(long aDivider) {
            this.mDensityDivider = aDivider;
            return this;
        }

        /**
         * Build the {@link Materials} from the {@link Builder}
         *
         * @return The {@link Materials}
         */
        public Materials build() {
            this.rMaterials.setSubID(this.mMetaItemSubID);
            this.rMaterials.setColor(this.mSolidColor);
            this.rMaterials.setMoltenColor(this.mMoltenColor);
            this.rMaterials.setTextureSet(this.mTextureSet);
            this.rMaterials.setUnifiable(this.mUnifiable);
            this.rMaterials.setMaterialInto(this.mMaterialInto);
            this.rMaterials.setMaterialStackList(this.mMaterialList);
            this.rMaterials.setOreByProducts(this.mOreByProducts);
            this.rMaterials.setOreReRegistrations(this.mOreReRegistrations);
            this.rMaterials.setAspects(this.mAspects);
            this.rMaterials.setMaterialItems(this.mMaterialItems);

            this.rMaterials.setSubTags(mSubTags);
            this.rMaterials.setEnchantmentForTools(mEnchantmentTools, this.mEnchantmentToolsLevel);
            this.rMaterials.setEnchantmentForArmors(this.mEnchantmentArmors, this.mEnchantmentArmorsLevel);
            this.rMaterials.setBlastFurnaceRequired(this.mBlastFurnaceRequired);
            this.rMaterials.setToolSpeed(this.mToolSpeed);
            this.rMaterials.setHeatDamage(this.mHeatDamage);
            this.rMaterials.setToolTip(this.mChemicalFormula);
            this.rMaterials.setName(this.mName);
            this.rMaterials.setDefaultLocalName(this.mDefaultLocalName);
            if (this.mDye == null || this.mDye == Dyes._NULL) {
                this.rMaterials.setDye(this.mSolidColor.getClosestDye());
            } else {
                this.rMaterials.setDye(this.mDye);
            }
            this.rMaterials.setMeltingPoint(this.mMeltingPoint);
            this.rMaterials.setBlastFurnaceTemp(this.mBlastFurnaceTemp);
            this.rMaterials.setTypes(this.mTypes);
            this.rMaterials.setToolDurability(this.mToolDurability);
            this.rMaterials.setToolQuality(this.mToolQuality);
            this.rMaterials.setFuelPower(this.mFuelPower);
            this.rMaterials.setFuelType(this.mFuelType);
            this.rMaterials.setExtraData(this.mExtraData);
            this.rMaterials.setOreValue(this.mOreValue);
            this.rMaterials.setOreMultiplier(this.mOreMultiplier);
            this.rMaterials.setByProductMultiplier(this.mByProductMultiplier);
            this.rMaterials.setSmeltingMultiplier(this.mSmeltingMultiplier);
            if (this.mDensityMultiplier != 0 && this.mDensityDivider != 0) {
                this.mDensity = ((MATERIAL_UNIT * this.mDensityMultiplier) / this.mDensityDivider);
            }
            this.rMaterials.setDensity(this.mDensity);

            this.rMaterials.setElement(this.mElement);
            this.rMaterials.setDirectSmelting(this.mDirectSmelting);
            this.rMaterials.setOreReplacement(this.mOreReplacement);
            this.rMaterials.setMaceratingInto(this.mMacerateInto);
            this.rMaterials.setSmeltingInto(this.mSmeltInto);
            this.rMaterials.setArcSmeltingInto(this.mArcSmeltInto);
            this.rMaterials.setHandleMaterial(this.mHandleMaterial);
            this.rMaterials.setSolid(this.mSolid);
            this.rMaterials.setFluid(this.mFluid);
            this.rMaterials.setGas(this.mGas);
            this.rMaterials.setPlasma(this.mPlasma);
            this.rMaterials.setStandardMoltenFluid(this.mStandardMoltenFluid);

            return this.rMaterials;
        }
    }
}
