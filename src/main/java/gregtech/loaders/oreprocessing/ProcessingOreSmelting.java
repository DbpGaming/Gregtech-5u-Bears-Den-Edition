package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

public class ProcessingOreSmelting implements gregtech.api.interfaces.IOreRecipeRegistrator {
    private final OrePrefixes[] mSmeltingPrefixes = {OrePrefixes.crushed, OrePrefixes.crushedPurified, OrePrefixes.crushedCentrifuged, OrePrefixes.dustImpure, OrePrefixes.dustPure, OrePrefixes.dustRefined};

    public ProcessingOreSmelting() {
        for (OrePrefixes tPrefix : this.mSmeltingPrefixes) tPrefix.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeFurnaceSmelting(aStack);
        if (!aMaterial.contains(SubTag.NO_SMELTING)) {
            if ((aMaterial.isBlastFurnaceRequired()) || (aMaterial.getDirectSmelting().isBlastFurnaceRequired())) {
                RECIPE_ADDER_INSTANCE.addBlastRecipe(GT_Utility.copyAmount(1L, aStack), null, null, null, aMaterial.getBlastFurnaceTemp() > 1750 ? GT_OreDictUnificator.get(OrePrefixes.ingotHot, aMaterial, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), 1L) : GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), null, (int) Math.max(aMaterial.getMass() / 4L, 1L) * aMaterial.getBlastFurnaceTemp(), 120, aMaterial.getBlastFurnaceTemp());
                if (aMaterial.getBlastFurnaceTemp() <= 1000)
                    GT_ModHandler.addRCBlastFurnaceRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), aMaterial.getBlastFurnaceTemp() * 2);
            } else {
                switch (aPrefix) {
                    case crushed:
                    case crushedPurified:
                    case crushedCentrifuged:
                        ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial.getDirectSmelting(), aMaterial.getDirectSmelting() == aMaterial ? 10L : 3L);
                        if (tStack == null)
                            tStack = GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.getDirectSmelting(), 1L);
                        if ((tStack == null) && (!aMaterial.contains(SubTag.SMELTING_TO_GEM)))
                            tStack = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.getDirectSmelting(), 1L);
                        GT_ModHandler.addSmeltingRecipe(aStack, tStack);
                        break;
                    default:
                        GT_ModHandler.addSmeltingRecipe(aStack, GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.getDirectSmelting(), 1L));
                }
            }
        }
    }
}
