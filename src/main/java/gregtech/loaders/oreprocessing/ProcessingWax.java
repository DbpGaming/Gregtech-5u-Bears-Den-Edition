package gregtech.loaders.oreprocessing;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWax implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWax() {
        OrePrefixes.wax.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("waxMagical"))
            RECIPE_ADDER_INSTANCE.addFuel(GT_Utility.copyAmount(1L, aStack), null, 6, 5);
    }
}
