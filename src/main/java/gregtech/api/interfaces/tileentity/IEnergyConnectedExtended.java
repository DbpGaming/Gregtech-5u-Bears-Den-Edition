package gregtech.api.interfaces.tileentity;

public interface IEnergyConnectedExtended extends IEnergyConnected{
    //to fix gregtech 6 cables for gregtech 5 bullshit, damn it BDE
	/**
     * Sided Energy Input
     */
    boolean inputEnergyFrom(byte aSide);
    default boolean inputEnergyFrom(byte aSide, boolean waitForActive) {
        return inputEnergyFrom(aSide);
    }

    /**
     * Sided Energy Output
     */
    boolean outputsEnergyTo(byte aSide);
    default boolean outputsEnergyTo(byte aSide, boolean waitForActive) {
        return outputsEnergyTo(aSide);
    }
}
