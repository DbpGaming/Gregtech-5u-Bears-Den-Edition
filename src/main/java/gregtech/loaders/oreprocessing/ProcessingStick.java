package gregtech.loaders.oreprocessing;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingStick implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingStick() {
        OrePrefixes.stick.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING))
            RECIPE_ADDER_INSTANCE.addCutterRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 4L), null, (int) Math.max(aMaterial.getMass() * 2L, 1L), 4);
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            RECIPE_ADDER_INSTANCE.addForgeHammerRecipe(GT_Utility.copyAmount(2L, aStack), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), (int) Math.max(aMaterial.getMass(), 1L), 16);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_Utility.copyAmount(1L, aStack), GT_Utility.copy(GT_OreDictUnificator.get(OrePrefixes.wireGt501, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L)), 50, 4);
        }
    }
}
