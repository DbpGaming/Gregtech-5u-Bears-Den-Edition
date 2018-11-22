package gregtech.loaders.oreprocessing;

import appeng.api.config.TunnelType;
import appeng.core.Api;
import gregtech.GT5_Mod;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWire16 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire16() {
        OrePrefixes.wireGt516.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt501, aMaterial, 16L), new Object[]{aOreDictName});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, aStack), new Object[]{OrePrefixes.wireGt508.get(aMaterial), OrePrefixes.wireGt508.get(aMaterial)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, aStack), new Object[]{OrePrefixes.wireGt512.get(aMaterial), OrePrefixes.wireGt504.get(aMaterial)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, aStack), new Object[]{OrePrefixes.wireGt504.get(aMaterial), OrePrefixes.wireGt504.get(aMaterial),OrePrefixes.wireGt504.get(aMaterial), OrePrefixes.wireGt504.get(aMaterial)});
        if (GT5_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
        }
    }
}
