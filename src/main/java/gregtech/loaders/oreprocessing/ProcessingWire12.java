package gregtech.loaders.oreprocessing;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

import appeng.api.config.TunnelType;
import appeng.core.Api;
import gregtech.GT5_Mod;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWire12 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire12() {
        OrePrefixes.wireGt512.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt512, aMaterial, 1L), 400, 8);
        RECIPE_ADDER_INSTANCE.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt512, aMaterial, 1L), GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 4L), 400, 8);
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt512, aMaterial, 1L), new Object[]{aOreDictName, OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt501, aMaterial, 12L), new Object[]{aOreDictName});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, aStack), new Object[]{OrePrefixes.wireGt508.get(aMaterial), OrePrefixes.wireGt504.get(aMaterial)});

        if (GT5_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(GT_OreDictUnificator.get(OrePrefixes.cableGt512, aMaterial, 1L), TunnelType.IC2_POWER);
        }
    }
}
