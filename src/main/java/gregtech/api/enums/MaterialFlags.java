package gregtech.api.enums;

import static gregtech.api.enums.GT_Values.BITS_32;

@SuppressWarnings({
        "unused",                   // Because it is an API
        "WeakerAccess",             // Because it is an API
        "SpellCheckingInspection",  // Gregtech dictionary
})
public class MaterialFlags {
    /**
     * Dusts of all kinds
     */
    public static final int DUST = BITS_32[0]; // 1

    /**
     * Ingots, Blocks
     */
    public static final int SOLID = BITS_32[1]; // 2

    /**
     * Big Gems, Lenses (if transparent)
     */
    public static final int BGEM = BITS_32[2]; // 4

    /**
     * Gems
     */
    public static final int GEM = BITS_32[3]; // 8

    /**
     * Ores
     */
    public static final int ORE = BITS_32[4]; // 16

    /**
     * Cells
     */
    public static final int CELL = BITS_32[5]; // 32

    /**
     * Plasma Cells
     */
    public static final int PLASMA = BITS_32[6]; // 64

    /**
     * Tool Heads
     */
    public static final int TOOL = BITS_32[7]; // 128

    /**
     * Plates
     */
    public static final int PLATE = BITS_32[8]; // 256

    /**
     * Sticks
     */
    public static final int STICK = BITS_32[9]; // 512

    /**
     * Rings
     */
    public static final int RING = BITS_32[10]; // 1024

    /**
     * Bolts
     */
    public static final int BOLT = BITS_32[11]; // 2048

    /**
     * Foils
     */
    public static final int FOIL = PLATE | BITS_32[12]; // 4096

    /**
     * Screws
     */
    public static final int SCREW = BOLT | BITS_32[13]; // 10240

    /**
     * Gears
     */
    public static final int GEAR = BITS_32[14]; // 16384

    /**
     * Small Gears
     */
    public static final int SGEAR = BITS_32[15]; // 32768

    /**
     * Fine Wires
     */
    public static final int FWIRE = BITS_32[16]; // 65536

    /**
     * Rotors
     */
    public static final int ROTOR = BITS_32[17]; // 131072

    /**
     * Dense Plates
     */
    public static final int DPLATE = PLATE | BITS_32[18]; // 262400

    /**
     * Springs
     */
    public static final int SPRING = BITS_32[19]; // 524288

    /**
     * Hot Ingots
     */
    public static final int HINGOT = BITS_32[20]; // 1048578

    /**
     * Add Electrolyzer recipes for this Material
     */
    public static final int ELEC = BITS_32[21]; // 2097152

    /**
     * Add Centrifuging recipes for this Material
     */
    public static final int CENT = BITS_32[22]; // 4194304

    /**
     * Add Cracking recipes for this Material
     */
    public static final int CRACK = BITS_32[23]; // 8388608

    /**
     * Add a Fluid for this Material
     */
    public static final int CFLUID = BITS_32[24]; // 16777216

    /**
     * Add a Gas for this Material
     */
    public static final int CGAS = BITS_32[25]; // 33554432

    /**
     * Add a Liquid for this Material
     */
    public static final int LIQUID = BITS_32[26]; // 67108864

    /**
     * Add Frame Blocks
     */
    public static final int FRAME = BITS_32[27]; // 134217728

    /**
     * Prevents illegal instantiation of this utility class
     * See: <a href="https://sbforge.org/sonar/rules/show/squid:S1118">squid:S1118</a>
     * @throws IllegalStateException if instantiated
     */
    private MaterialFlags() {
        throw new IllegalStateException("Utility class");
    }
}