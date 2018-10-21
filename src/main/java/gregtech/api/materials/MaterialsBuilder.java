package gregtech.api.materials;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.TC_Aspects;
import gregtech.api.enums.TextureSet;
import gregtech.api.objects.GT_Color;
import gregtech.api.objects.MaterialStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.enums.GT_Values.MATERIAL_UNIT;

public class MaterialsBuilder {
    private int mMetaItemSubID;
    private Materials rMaterials = new Materials();
    private GT_Color mSolidColor = new GT_Color(0x00ffffff);
    private GT_Color mMoltenColor = new GT_Color(0x00ffffff);
    private TextureSet mTextureSet;
    private boolean mUnifiable;
    private Materials mMaterialInto;
    private List<MaterialStack> mMaterialList = new ArrayList<>();
    private List<Materials> mOreByProducts = new ArrayList<>();
    private List<Materials> mOreReRegistrations = new ArrayList<>();
    private List<TC_Aspects.TC_AspectStack> mAspects = new ArrayList<>();
    private List<ItemStack> mMaterialItems = new ArrayList<>();
    private List<SubTag> mSubTags = new ArrayList<>();
    private Enchantment mEnchantmentTools = null;
    private byte mEnchantmentToolsLevel = 0;
    private Enchantment mEnchantmentArmors = null;
    private byte mEnchantmentArmorsLevel = 0;
    private boolean mBlastFurnaceRequired = false;
    private float mToolSpeed = 1.0F;
    private float mHeatDamage = 0.0F;
    private String mChemicalFormula = "?";
    private String mName = "null";
    private String mDefaultLocalName = "null";
    private Dyes mDye = Dyes._NULL;
    private short mMeltingPoint = 0;
    private short mBlastFurnaceTemp = 0;
    private int mTypes = 0;
    private int mToolDurability = 16;
    private byte mToolQuality = 0;
    private int mFuelPower = 0;
    private int mFuelType = 0;
    private int mExtraData = 0;
    private int mOreValue = 0;
    private int mOreMultiplier = 1;
    private int mByProductMultiplier = 1;
    private int mSmeltingMultiplier = 1;
    private long mDensity = MATERIAL_UNIT;
    private long mDensityMultiplier = 0;
    private long mDensityDivider = 0;
    private Element mElement = null;
    private Materials mDirectSmelting = rMaterials;
    private Materials mOreReplacement = rMaterials;
    private Materials mMacerateInto = rMaterials;
    private Materials mSmeltInto = rMaterials;
    private Materials mArcSmeltInto = rMaterials;
    private Materials mHandleMaterial = rMaterials;
    private Fluid mSolid = null;
    private Fluid mFluid = null;
    private Fluid mGas = null;
    private Fluid mPlasma = null;
    private Fluid mStandardMoltenFluid = null;

    /**
     * Sets the {@link Materials}'s meta-sub identifier
     *
     * @param aMetaItemSubID The meta-sub identifier for items
     * @return the {@link MaterialsBuilder}
     * @throws IllegalArgumentException if SubID already assigned
     */
    public MaterialsBuilder subID(int aMetaItemSubID) {
        if (aMetaItemSubID >= 0) {
            if (GregTech_API.sGeneratedMaterials[aMetaItemSubID] == null) {
                this.mMetaItemSubID = aMetaItemSubID;
            } else {
                throw new IllegalArgumentException("The Index " + aMetaItemSubID + " is already used!");
            }
        }
        return this;
    }

    /**
     * Set the {@link Materials}'s ARGB color
     *
     * @param aARGB integer packed ARGB.
     *              example: solidRGBa(0x00FF8020)
     *              Alpha = 0x00
     *              Red = 0xff
     *              Green = 0x80
     *              Blue = 0x20
     * @return the {@link MaterialsBuilder}
     */
    public MaterialsBuilder solidColor(int aARGB) {
        this.mSolidColor.setARGB(aARGB);
        return this;
    }

    public MaterialsBuilder solidColor(int r, int g, int b, int alpha) {
        this.mSolidColor.setRGBA(r, g, b, alpha);
        return this;
    }


    /**
     * Set the {@link Materials}'s molten ARGB color
     *
     * @param aARGB integer packed ARGB.
     *              example: solidRGBa(0x00FF8020)
     *              Alpha = 0x00
     *              Red = 0xff
     *              Green = 0x80
     *              Blue = 0x20
     * @return the {@link MaterialsBuilder}
     */
    public MaterialsBuilder moltenColor(int aARGB) {
        this.mMoltenColor.setARGB(aARGB);
        return this;
    }

    public MaterialsBuilder textureSet(TextureSet aTextureSet) {
        this.mTextureSet = aTextureSet;
        return this;
    }

    public MaterialsBuilder unifiable(boolean aUnifiable) {
        this.mUnifiable = aUnifiable;
        return this;
    }

    public MaterialsBuilder matInto(Materials aMaterialInto) {
        this.mMaterialInto = aMaterialInto;
        return this;
    }

    public MaterialsBuilder matList(MaterialStack... aMaterialList) {
        this.mMaterialList = Arrays.asList(aMaterialList);
        return this;
    }

    public MaterialsBuilder byProducts(Materials... aByProducts) {
        this.mOreByProducts = Arrays.asList(aByProducts);
        return this;
    }

    public MaterialsBuilder oreRegistrations(Materials... aOres) {
        this.mOreReRegistrations = Arrays.asList(aOres);
        return this;
    }

    public MaterialsBuilder aspects(TC_Aspects.TC_AspectStack... aAspects) {
        this.mAspects = Arrays.asList(aAspects);
        return this;
    }

    public MaterialsBuilder items(ItemStack... aItemsStacks) {
        this.mMaterialItems = Arrays.asList(aItemsStacks);
        return this;
    }

    public MaterialsBuilder subTags(SubTag... aSubTags) {
        this.mSubTags = Arrays.asList(aSubTags);
        return this;
    }

    public MaterialsBuilder toolsEnchant(Enchantment aEnchantment) {
        this.mEnchantmentTools = aEnchantment;
        return this;
    }

    public MaterialsBuilder toolsEnchantLvl(byte aEnchantmentLevel) {
        this.mEnchantmentToolsLevel = aEnchantmentLevel;
        return this;
    }

    public MaterialsBuilder armorEnchant(Enchantment aEnchantment) {
        this.mEnchantmentArmors = aEnchantment;
        return this;
    }

    public MaterialsBuilder armorEnchantLvl(byte aEnchantmentLevel) {
        this.mEnchantmentArmorsLevel = aEnchantmentLevel;
        return this;
    }

    public MaterialsBuilder needBlast(boolean aNeedBlastFurnace) {
        this.mBlastFurnaceRequired = aNeedBlastFurnace;
        return this;
    }

    public MaterialsBuilder toolSpeed(float aToolSpeed) {
        this.mToolSpeed = aToolSpeed;
        return this;
    }

    public MaterialsBuilder heatDamage(float aHeatDamage) {
        this.mHeatDamage = aHeatDamage;
        return this;
    }

    public MaterialsBuilder toolTip(String aChemicalFormula) {
        this.mChemicalFormula = aChemicalFormula;
        return this;
    }

    public MaterialsBuilder name(String aName) {
        this.mName = aName;
        return this;
    }

    public MaterialsBuilder locName(String aDefaultLocalName) {
        this.mDefaultLocalName = aDefaultLocalName;
        return this;
    }

    public MaterialsBuilder dye(Dyes aDye) {
        this.mDye = aDye;
        return this;
    }

    public MaterialsBuilder meltAt(int aKelvins) {
        this.mMeltingPoint = (short) aKelvins;
        return this;
    }

    public MaterialsBuilder blastAt(int aKelvin) {
        this.mBlastFurnaceTemp = (short) aKelvin;
        return this;
    }

    public MaterialsBuilder type(int aType) {
        this.mTypes = aType;
        return this;
    }

    public MaterialsBuilder toolDura(int aDurability) {
        this.mToolDurability = aDurability;
        return this;
    }

    public MaterialsBuilder toolQual(int aQuality) {
        this.mToolQuality = (byte) aQuality;
        return this;
    }

    public MaterialsBuilder fuelPow(int aBurnValue) {
        this.mFuelPower = aBurnValue;
        return this;
    }

    public MaterialsBuilder fuelType(int aType) {
        this.mFuelType = aType;
        return this;
    }

    public MaterialsBuilder eData(int aData) {
        this.mExtraData = aData;
        return this;
    }

    public MaterialsBuilder oreVal(int aValue) {
        this.mOreValue = aValue;
        return this;
    }

    public MaterialsBuilder oreMult(int aMult) {
        this.mOreMultiplier = aMult;
        return this;
    }

    public MaterialsBuilder byProdMult(int aMult) {
        this.mByProductMultiplier = aMult;
        return this;
    }

    public MaterialsBuilder smeltMult(int aMult) {
        this.mSmeltingMultiplier = aMult;
        return this;
    }

    public MaterialsBuilder density(long aDensity) {
        this.mDensity = aDensity;
        return this;
    }

    public MaterialsBuilder elem(Element aElement) {
        this.mElement = aElement;
        return this;
    }

    public MaterialsBuilder directSmelt(Materials aMaterial) {
        this.mDirectSmelting = aMaterial;
        return this;
    }

    public MaterialsBuilder oreRep(Materials aMaterial) {
        this.mOreReplacement = aMaterial;
        return this;
    }

    public MaterialsBuilder macerInto(Materials aMaterial) {
        this.mMacerateInto = aMaterial;
        return this;
    }

    public MaterialsBuilder smeltInto(Materials aMaterial) {
        this.mSmeltInto = aMaterial;
        return this;
    }

    public MaterialsBuilder arcSmeltInto(Materials aMaterial) {
        this.mArcSmeltInto = aMaterial;
        return this;
    }

    public MaterialsBuilder handleMat(Materials aMaterial) {
        this.mHandleMaterial = aMaterial;
        return this;
    }

    public MaterialsBuilder solid(Fluid aFluid) {
        this.mSolid = aFluid;
        return this;
    }

    public MaterialsBuilder fluid(Fluid aFluid) {
        this.mFluid = aFluid;
        return this;
    }

    public MaterialsBuilder gas(Fluid aFluid) {
        this.mGas = aFluid;
        return this;
    }

    public MaterialsBuilder plasma(Fluid aFluid) {
        this.mPlasma = aFluid;
        return this;
    }

    public MaterialsBuilder stdMolten(Fluid aFluid) {
        this.mStandardMoltenFluid = aFluid;
        return this;
    }

    public MaterialsBuilder densityMult(long aMultiplier) {
        this.mDensityMultiplier = aMultiplier;
        return this;
    }

    public MaterialsBuilder densityDiv(long aDivider) {
        this.mDensityDivider = aDivider;
        return this;
    }

    /**
     * Build the {@link Materials} from the {@link MaterialsBuilder}
     *
     * @return The {@link Materials}
     */
    public Materials build() {
        this.rMaterials.setSubID(this.mMetaItemSubID);
        this.rMaterials.setColor(this.mSolidColor);
        this.rMaterials.setMoltenColor(this.mMoltenColor);
        this.rMaterials.setTextureSet(this.mTextureSet);
        this.rMaterials.setUnifiable(this.mUnifiable);
        this.rMaterials.setMaterialInto(this.mMaterialInto);
        this.rMaterials.setMaterialStackList(this.mMaterialList);
        this.rMaterials.setOreByProducts(this.mOreByProducts);
        this.rMaterials.setOreReRegistrations(this.mOreReRegistrations);
        this.rMaterials.setAspects(this.mAspects);
        this.rMaterials.setMaterialItems(this.mMaterialItems);

        this.rMaterials.setSubTags(mSubTags);
        this.rMaterials.setEnchantmentForTools(mEnchantmentTools, this.mEnchantmentToolsLevel);
        this.rMaterials.setEnchantmentForArmors(this.mEnchantmentArmors, this.mEnchantmentArmorsLevel);
        this.rMaterials.setBlastFurnaceRequired(this.mBlastFurnaceRequired);
        this.rMaterials.setToolSpeed(this.mToolSpeed);
        this.rMaterials.setHeatDamage(this.mHeatDamage);
        this.rMaterials.setToolTip(this.mChemicalFormula);
        this.rMaterials.setName(this.mName);
        this.rMaterials.setDefaultLocalName(this.mDefaultLocalName);
        if (this.mDye == null || this.mDye == Dyes._NULL) {
            this.rMaterials.setDye(this.mSolidColor.getClosestDye());
        } else {
            this.rMaterials.setDye(this.mDye);
        }
        this.rMaterials.setMeltingPoint(this.mMeltingPoint);
        this.rMaterials.setBlastFurnaceTemp(this.mBlastFurnaceTemp);
        this.rMaterials.setTypes(this.mTypes);
        this.rMaterials.setToolDurability(this.mToolDurability);
        this.rMaterials.setToolQuality(this.mToolQuality);
        this.rMaterials.setFuelPower(this.mFuelPower);
        this.rMaterials.setFuelType(this.mFuelType);
        this.rMaterials.setExtraData(this.mExtraData);
        this.rMaterials.setOreValue(this.mOreValue);
        this.rMaterials.setOreMultiplier(this.mOreMultiplier);
        this.rMaterials.setByProductMultiplier(this.mByProductMultiplier);
        this.rMaterials.setSmeltingMultiplier(this.mSmeltingMultiplier);
        if (this.mDensityMultiplier != 0 && this.mDensityDivider != 0) {
            this.mDensity = ((MATERIAL_UNIT * this.mDensityMultiplier) / this.mDensityDivider);
        }
        this.rMaterials.setDensity(this.mDensity);

        this.rMaterials.setElement(this.mElement);
        this.rMaterials.setDirectSmelting(this.mDirectSmelting);
        this.rMaterials.setOreReplacement(this.mOreReplacement);
        this.rMaterials.setMaceratingInto(this.mMacerateInto);
        this.rMaterials.setSmeltingInto(this.mSmeltInto);
        this.rMaterials.setArcSmeltingInto(this.mArcSmeltInto);
        this.rMaterials.setHandleMaterial(this.mHandleMaterial);
        this.rMaterials.setSolid(this.mSolid);
        this.rMaterials.setFluid(this.mFluid);
        this.rMaterials.setGas(this.mGas);
        this.rMaterials.setPlasma(this.mPlasma);
        this.rMaterials.setStandardMoltenFluid(this.mStandardMoltenFluid);

        return this.rMaterials;
    }
}
