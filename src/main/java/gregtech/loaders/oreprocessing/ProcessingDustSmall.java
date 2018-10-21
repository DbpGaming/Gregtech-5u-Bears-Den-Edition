package gregtech.loaders.oreprocessing;

import gregtech.api.enums.ItemList;
import gregtech.api.materials.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

public class ProcessingDustSmall implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingDustSmall() {
        OrePrefixes.dustSmall.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(GT_Utility.copyAmount(4L, aStack), ItemList.Schematic_Dust.get(0L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 100, 4);
        if (!aMaterial.isBlastFurnaceRequired()) {
            GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
            if (aMaterial.getSmeltingInto().getArcSmeltingInto() != aMaterial) {
                GT_RecipeRegistrator.registerReverseArcSmelting(GT_Utility.copyAmount(1L, aStack), aMaterial, aPrefix.mMaterialAmount, null, null, null);
            }
        }
        if (aMaterial.isBlastFurnaceRequired()) {
            RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_Utility.copyAmount(4L, aStack), null, null, null, aMaterial.getBlastFurnaceTemp() > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial.getSmeltingInto(), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.getSmeltingInto(), 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.getSmeltingInto(), 1L), null, (int) Math.max(aMaterial.getMass() / 40L, 1L) * aMaterial.getBlastFurnaceTemp(), 120, aMaterial.getBlastFurnaceTemp());
        } else {
            gregtech.api.util.GT_ModHandler.addAlloySmelterRecipe(GT_Utility.copyAmount(4L, aStack), ItemList.Shape_Mold_Ingot.get(0L), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.getSmeltingInto(), 1L), 130, 3, true);
        }
    }
}
