package gregtech.loaders.oreprocessing;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingToolHeadArrow implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingToolHeadArrow() {
        OrePrefixes.toolHeadArrow.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial.mStandardMoltenFluid != null)
            RECIPE_ADDER_INSTANCE.addFluidSolidifierRecipe(ItemList.Shape_Mold_Arrow.get(0L), aMaterial.getMolten(36L), GT_Utility.copyAmount(1L, aStack), 16, 4);
    }
}
