package gregtech.loaders.oreprocessing;

import static gregtech.api.enums.GT_Values.RECIPE_ADDER_INSTANCE;

import appeng.api.config.TunnelType;
import appeng.core.Api;
import gregtech.GT5_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWire01 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire01() {
        OrePrefixes.wireGt501.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt501, aMaterial, 1L), new Object[]{aOreDictName, OrePrefixes.plate.get(Materials.Rubber)});
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            RECIPE_ADDER_INSTANCE.addBenderRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.springSmall, aMaterial, 2L), 100, 8);
            RECIPE_ADDER_INSTANCE.addWiremillRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L), 200, 8);
        }
        RECIPE_ADDER_INSTANCE.addBoxingRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt501, aMaterial, 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt501, aMaterial, 1L), GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), 100, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GT_OreDictUnificator.get(OrePrefixes.wireGt502, aMaterial, 1L), 150, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_Utility.copyAmount(4L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), GT_OreDictUnificator.get(OrePrefixes.wireGt504, aMaterial, 1L), 200, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_Utility.copyAmount(8L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), GT_OreDictUnificator.get(OrePrefixes.wireGt508, aMaterial, 1L), 300, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_Utility.copyAmount(12L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 12L), GT_OreDictUnificator.get(OrePrefixes.wireGt512, aMaterial, 1L), 400, 8);
        RECIPE_ADDER_INSTANCE.addAssemblerRecipe(GT_Utility.copyAmount(16L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 16L), GT_OreDictUnificator.get(OrePrefixes.wireGt516, aMaterial, 1L), 500, 8);

        if (GT5_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(GT_OreDictUnificator.get(OrePrefixes.cableGt501, aMaterial, 1L), TunnelType.IC2_POWER);
        }
    }
}
