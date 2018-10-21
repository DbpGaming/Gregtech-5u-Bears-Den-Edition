package gregtech.api.enums;

import gregtech.api.materials.Materials;

/**
 * Experimental Class for later
 */
public class Tier {
    public static final Tier[]
            ELECTRIC = new Tier[]{
            new Tier(SubTag.ENERGY_ELECTRICITY, 0, 8, 1, 1, 1, Materials.get("WroughtIron"), ItemList.Hull_ULV, OrePrefixes.cableGt01.get(Materials.get("Lead")), OrePrefixes.cableGt04.get(Materials.get("Lead")), OrePrefixes.circuit.get(Materials.get("Primitive")), OrePrefixes.circuit.get(Materials.get("Basic"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 1, 32, 1, 1, 1, Materials.get("Steel"), ItemList.Hull_LV, OrePrefixes.cableGt01.get(Materials.get("Tin")), OrePrefixes.cableGt04.get(Materials.get("Tin")), OrePrefixes.circuit.get(Materials.get("Basic")), OrePrefixes.circuit.get(Materials.get("Good"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 2, 128, 1, 1, 1, Materials.get("Aluminium"), ItemList.Hull_MV, OrePrefixes.cableGt01.get(Materials.get("AnyCopper")), OrePrefixes.cableGt04.get(Materials.get("AnyCopper")), OrePrefixes.circuit.get(Materials.get("Good")), OrePrefixes.circuit.get(Materials.get("Advanced"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 3, 512, 1, 1, 1, Materials.get("StainlessSteel"), ItemList.Hull_HV, OrePrefixes.cableGt01.get(Materials.get("Gold")), OrePrefixes.cableGt04.get(Materials.get("Gold")), OrePrefixes.circuit.get(Materials.get("Advanced")), OrePrefixes.circuit.get(Materials.get("Elite"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 4, 2048, 1, 1, 1, Materials.get("Titanium"), ItemList.Hull_EV, OrePrefixes.cableGt01.get(Materials.get("Aluminium")), OrePrefixes.cableGt04.get(Materials.get("Aluminium")), OrePrefixes.circuit.get(Materials.get("Elite")), OrePrefixes.circuit.get(Materials.get("Master"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 5, 8192, 1, 1, 1, Materials.get("TungstenSteel"), ItemList.Hull_IV, OrePrefixes.cableGt01.get(Materials.get("Tungsten")), OrePrefixes.cableGt04.get(Materials.get("Tungsten")), OrePrefixes.circuit.get(Materials.get("Master")), OrePrefixes.circuit.get(Materials.get("Ultimate"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 6, 32768, 1, 1, 1, Materials.get("Chrome"), ItemList.Hull_LuV, OrePrefixes.cableGt01.get(Materials.get("Osmium")), OrePrefixes.cableGt04.get(Materials.get("Osmium")), OrePrefixes.circuit.get(Materials.get("Ultimate")), OrePrefixes.circuit.get(Materials.get("Ultimate"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 7, 131072, 1, 1, 1, Materials.get("Iridium"), ItemList.Hull_ZPM, OrePrefixes.cableGt04.get(Materials.get("Osmium")), OrePrefixes.wireGt16.get(Materials.get("Osmium")), OrePrefixes.circuit.get(Materials.get("Ultimate")), OrePrefixes.circuit.get(Materials.get("Ultimate"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 8, 524288, 1, 1, 1, Materials.get("Osmium"), ItemList.Hull_UV, OrePrefixes.wireGt16.get(Materials.get("Osmium")), OrePrefixes.wireGt01.get(Materials.get("Superconductor")), OrePrefixes.circuit.get(Materials.get("Ultimate")), OrePrefixes.circuit.get(Materials.get("Ultimate"))),
            new Tier(SubTag.ENERGY_ELECTRICITY, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.get("Neutronium"), ItemList.Hull_MAX, OrePrefixes.wireGt01.get(Materials.get("Superconductor")), OrePrefixes.wireGt04.get(Materials.get("Superconductor")), OrePrefixes.circuit.get(Materials.get("Ultimate")), OrePrefixes.circuit.get(Materials.get("Ultimate"))),
    }, ROTATIONAL = new Tier[]{
            new Tier(SubTag.ENERGY_ROTATIONAL, 1, 32, 1, 1, 1, Materials.get("Wood"), OrePrefixes.frameGt.get(Materials.get("Wood")), OrePrefixes.stick.get(Materials.get("Wood")), OrePrefixes.ingot.get(Materials.get("Wood")), OrePrefixes.gearGt.get(Materials.get("Wood")), OrePrefixes.gearGt.get(Materials.get("Stone"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 1, 32, 1, 2, 2, Materials.get("WoodSealed"), OrePrefixes.frameGt.get(Materials.get("WoodSealed")), OrePrefixes.stick.get(Materials.get("WoodSealed")), OrePrefixes.ingot.get(Materials.get("WoodSealed")), OrePrefixes.gearGt.get(Materials.get("WoodSealed")), OrePrefixes.gearGt.get(Materials.get("Stone"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 2, 128, 1, 1, 1, Materials.get("Stone"), OrePrefixes.frameGt.get(Materials.get("Stone")), OrePrefixes.stick.get(Materials.get("Stone")), OrePrefixes.ingot.get(Materials.get("Stone")), OrePrefixes.gearGt.get(Materials.get("Stone")), OrePrefixes.gearGt.get(Materials.get("Bronze"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 2, 128, 1, 2, 2, Materials.get("IronWood"), OrePrefixes.frameGt.get(Materials.get("IronWood")), OrePrefixes.stick.get(Materials.get("IronWood")), OrePrefixes.ingot.get(Materials.get("IronWood")), OrePrefixes.gearGt.get(Materials.get("IronWood")), OrePrefixes.gearGt.get(Materials.get("Bronze"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 3, 512, 1, 1, 1, Materials.get("Bronze"), OrePrefixes.frameGt.get(Materials.get("Bronze")), OrePrefixes.stick.get(Materials.get("Bronze")), OrePrefixes.ingot.get(Materials.get("Bronze")), OrePrefixes.gearGt.get(Materials.get("Bronze")), OrePrefixes.gearGt.get(Materials.get("Steel"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 3, 512, 1, 2, 2, Materials.get("Brass"), OrePrefixes.frameGt.get(Materials.get("Brass")), OrePrefixes.stick.get(Materials.get("Brass")), OrePrefixes.ingot.get(Materials.get("Brass")), OrePrefixes.gearGt.get(Materials.get("Brass")), OrePrefixes.gearGt.get(Materials.get("Steel"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 4, 2048, 1, 1, 1, Materials.get("Steel"), OrePrefixes.frameGt.get(Materials.get("Steel")), OrePrefixes.stick.get(Materials.get("Steel")), OrePrefixes.ingot.get(Materials.get("Steel")), OrePrefixes.gearGt.get(Materials.get("Steel")), OrePrefixes.gearGt.get(Materials.get("TungstenSteel"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 4, 2048, 1, 2, 2, Materials.get("Titanium"), OrePrefixes.frameGt.get(Materials.get("Titanium")), OrePrefixes.stick.get(Materials.get("Titanium")), OrePrefixes.ingot.get(Materials.get("Titanium")), OrePrefixes.gearGt.get(Materials.get("Titanium")), OrePrefixes.gearGt.get(Materials.get("TungstenSteel"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 5, 8192, 1, 1, 1, Materials.get("TungstenSteel"), OrePrefixes.frameGt.get(Materials.get("TungstenSteel")), OrePrefixes.stick.get(Materials.get("TungstenSteel")), OrePrefixes.ingot.get(Materials.get("TungstenSteel")), OrePrefixes.gearGt.get(Materials.get("TungstenSteel")), OrePrefixes.gearGt.get(Materials.get("Iridium"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 6, 32768, 1, 1, 1, Materials.get("Iridium"), OrePrefixes.frameGt.get(Materials.get("Iridium")), OrePrefixes.stick.get(Materials.get("Iridium")), OrePrefixes.ingot.get(Materials.get("Iridium")), OrePrefixes.gearGt.get(Materials.get("Iridium")), OrePrefixes.gearGt.get(Materials.get("Neutronium"))),
            new Tier(SubTag.ENERGY_ROTATIONAL, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.get("Neutronium"), OrePrefixes.frameGt.get(Materials.get("Neutronium")), OrePrefixes.stick.get(Materials.get("Neutronium")), OrePrefixes.ingot.get(Materials.get("Neutronium")), OrePrefixes.gearGt.get(Materials.get("Neutronium")), OrePrefixes.gearGt.get(Materials.get("Neutronium"))),
    }, STEAM = new Tier[]{
            new Tier(SubTag.ENERGY_STEAM, 1, 32, 1, 1, 1, Materials.get("Bronze"), OrePrefixes.frameGt.get(Materials.get("Bronze")), OrePrefixes.pipeMedium.get(Materials.get("Bronze")), OrePrefixes.pipeHuge.get(Materials.get("Bronze")), OrePrefixes.pipeMedium.get(Materials.get("Bronze")), OrePrefixes.pipeLarge.get(Materials.get("Bronze"))),
            new Tier(SubTag.ENERGY_STEAM, 2, 128, 1, 1, 1, Materials.get("Steel"), OrePrefixes.frameGt.get(Materials.get("Steel")), OrePrefixes.pipeMedium.get(Materials.get("Steel")), OrePrefixes.pipeHuge.get(Materials.get("Steel")), OrePrefixes.pipeMedium.get(Materials.get("Steel")), OrePrefixes.pipeLarge.get(Materials.get("Steel"))),
            new Tier(SubTag.ENERGY_STEAM, 3, 512, 1, 1, 1, Materials.get("Titanium"), OrePrefixes.frameGt.get(Materials.get("Titanium")), OrePrefixes.pipeMedium.get(Materials.get("Titanium")), OrePrefixes.pipeHuge.get(Materials.get("Titanium")), OrePrefixes.pipeMedium.get(Materials.get("Titanium")), OrePrefixes.pipeLarge.get(Materials.get("Titanium"))),
            new Tier(SubTag.ENERGY_STEAM, 4, 2048, 1, 1, 1, Materials.get("TungstenSteel"), OrePrefixes.frameGt.get(Materials.get("TungstenSteel")), OrePrefixes.pipeMedium.get(Materials.get("TungstenSteel")), OrePrefixes.pipeHuge.get(Materials.get("TungstenSteel")), OrePrefixes.pipeMedium.get(Materials.get("TungstenSteel")), OrePrefixes.pipeLarge.get(Materials.get("TungstenSteel"))),
            new Tier(SubTag.ENERGY_STEAM, 5, 8192, 1, 1, 1, Materials.get("Iridium"), OrePrefixes.frameGt.get(Materials.get("Iridium")), OrePrefixes.pipeMedium.get(Materials.get("Iridium")), OrePrefixes.pipeHuge.get(Materials.get("Iridium")), OrePrefixes.pipeMedium.get(Materials.get("Iridium")), OrePrefixes.pipeLarge.get(Materials.get("Iridium"))),
            new Tier(SubTag.ENERGY_STEAM, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.get("Neutronium"), OrePrefixes.frameGt.get(Materials.get("Neutronium")), OrePrefixes.pipeMedium.get(Materials.get("Neutronium")), OrePrefixes.pipeHuge.get(Materials.get("Neutronium")), OrePrefixes.pipeMedium.get(Materials.get("Neutronium")), OrePrefixes.pipeLarge.get(Materials.get("Neutronium"))),
    };
    /**
     * Used for Crafting Recipes
     */
    public final Object mHullObject, mConductingObject, mLargerConductingObject, mManagingObject, mBetterManagingObject;
    private final SubTag mType;
    private final byte mRank;
    private final long mPrimaryValue, mSecondaryValue, mSpeedMultiplier, mEnergyCostMultiplier;
    private final Materials mMaterial;

    public Tier(SubTag aType, int aRank, long aPrimaryValue, long aSecondaryValue, long aSpeedMultiplier, long aEnergyCostMultiplier, Materials aMaterial, Object aHullObject, Object aConductingObject, Object aLargerConductingObject, Object aManagingObject, Object aBetterManagingObject) {
        mType = aType;
        mRank = (byte) aRank;
        mPrimaryValue = aPrimaryValue;
        mSecondaryValue = aSecondaryValue;
        mSpeedMultiplier = aSpeedMultiplier;
        mEnergyCostMultiplier = Math.max(mSpeedMultiplier, aEnergyCostMultiplier);
        mMaterial = aMaterial;

        mHullObject = aHullObject;
        mConductingObject = aConductingObject;
        mManagingObject = aManagingObject;
        mBetterManagingObject = aBetterManagingObject;
        mLargerConductingObject = aLargerConductingObject;
    }

    public byte getRank() {
        return mRank;
    }

    public SubTag getEnergyType() {
        return mType;
    }

    public long getEnergyPrimary() {
        return mPrimaryValue;
    }

    public long getEnergySecondary() {
        return mSecondaryValue;
    }

    public long getSpeedMultiplier() {
        return mSpeedMultiplier;
    }

    public long getEnergyCostMultiplier() {
        return mEnergyCostMultiplier;
    }

    public Materials getMaterial() {
        return mMaterial;
    }
}
