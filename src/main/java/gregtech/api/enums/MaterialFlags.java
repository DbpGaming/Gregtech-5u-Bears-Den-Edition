package gregtech.api.enums;

public class MaterialFlags {
    /**
     * Dusts of all kinds
     */
    public static final int DUST = 1; // 1

    /**
     * Ingots, Blocks
     */
    public static final int SOLID = 1 << 1; // 2

    /**
     * Big Gems, Lenses (if transparent)
     */
    public static final int BGEM = 1 << 2; // 4

    /**
     * Gems
     */
    public static final int GEM = 1 << 3; // 8

    /**
     * Ores
     */
    public static final int ORE = 1 << 4; // 16

    /**
     * Cells
     */
    public static final int CELL = 1 << 5; // 32

    /**
     * Plasma Cells
     */
    public static final int PLASMA = 1 << 6; // 64

    /**
     * Tool Heads
     */
    public static final int TOOL = 1 << 7; // 128

    /**
     * Plates
     */
    public static final int PLATE = 1 << 8; // 256

    /**
     * Sticks
     */
    public static final int STICK = 1 << 9; // 512

    /**
     * Rings
     */
    public static final int RING = 1 << 10; // 1024

    /**
     * Bolts
     */
    public static final int BOLT = 1 << 11; // 2048

    /**
     * Foils
     */
    public static final int FOIL = PLATE | 1 << 12; // 4096

    /**
     * Screws
     */
    public static final int SCREW = BOLT | 1 << 13; // 10240

    /**
     * Gears
     */
    public static final int GEAR = 1 << 14; // 16384

    /**
     * Small Gears
     */
    public static final int SGEAR = 1 << 15; // 32768

    /**
     * Fine Wires
     */
    public static final int FWIRE = 1 << 16; // 65536

    /**
     * Rotors
     */
    public static final int ROTOR = 1 << 17; // 131072

    /**
     * Dense Plates
     */
    public static final int DPLATE = PLATE | 1 << 18; // 262400

    /**
     * Springs
     */
    public static final int SPRING = 1 << 19; // 524288

    /**
     * Hot Ingots
     */
    public static final int HINGOT = 1 << 20; // 1048578

    /**
     * Add Electrolyzer recipes for this Material
     */
    public static final int ELEC = 1 << 21; // 2097152

    /**
     * Add Centrifuging recipes for this Material
     */
    public static final int CENT = 1 << 22; // 4194304

    /**
     * Add Cracking recipes for this Material
     */
    public static final int CRACK = 1 << 23; // 8388608

    /**
     * Add a Fluid for this Material
     */
    public static final int CFLUID = 1 << 24; // 16777216

    /**
     * Add a Gas for this Material
     */
    public static final int CGAS = 1 << 25; // 33554432

    /**
     * Add a Liquid for this Material
     */
    public static final int LIQUID = 1 << 26; // 67108864

    /**
     * Add Frame Blocks
     */
    public static final int FRAME = 1 << 27; // 134217728
}