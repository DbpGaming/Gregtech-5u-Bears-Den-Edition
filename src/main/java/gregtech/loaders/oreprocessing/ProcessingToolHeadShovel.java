package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.item.ItemStack;

public class ProcessingToolHeadShovel implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingToolHeadShovel() {
        OrePrefixes.toolHeadShovel.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(4, 1, aMaterial, aMaterial.getHandleMaterial(), null), new Object[]{aOreDictName, OrePrefixes.stick.get(aMaterial.getHandleMaterial())});
    }
}
