package z21Drive.broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 * Supports up to 28 functions.
 */
public class Z21BroadcastLanXSignalInfo extends Z21Broadcast{
//    public static final int FUNCTION_COUNT = 29;

    private int signalAddress;
    private boolean signalInUse;

    private boolean [] db2bits;

//    private boolean[] functionStates = new boolean[FUNCTION_COUNT];

    public Z21BroadcastLanXSignalInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_SIGNAL_INFO;
        if (byteRepresentation != null) {
          populateFields();
        } else {
//            for (int i = 0; i < FUNCTION_COUNT; i ++) {
//              functionStates[i] = false;
//            }
        }
    }

    private void populateFields(){
        byte adr_MSB = byteRepresentation [5];
        byte adr_LSB = byteRepresentation [6];
        signalAddress = (adr_MSB & 0x3F) << 8 | adr_LSB;

        db2bits = fromByte(byteRepresentation[7]);
//        signalInUse = db2bits[4];
//        String binary = String.format("%8s", Integer.toBinaryString(byteRepresentation[7])).replace(' ', '0');
//        if (binary.equals("00000000") || binary.equals("00001000"))
//            speedSteps = 14;
//        else if (binary.equals("00000010") || binary.equals("00001010"))
//            speedSteps = 28;
//        else if (binary.equals("00000100") || binary.equals("00001100"))
//            speedSteps = 128;
//
//        boolean [] db3bits = fromByte(byteRepresentation[8]);
//        direction = byteRepresentation[8] < 0;
//        boolean [] speedArray = db3bits.clone();
//
//        if (direction) {
//            speedArray = fromByte((byte) (byteRepresentation[8] + 128));
//        }
//        speedArray [0] = false;
//        speed = ((speedArray[0]?1<<7:0) + (speedArray[1]?1<<6:0) + (speedArray[2]?1<<5:0) +
//                (speedArray[3]?1<<4:0) + (speedArray[4]?1<<3:0) + (speedArray[5]?1<<2:0) +
//                (speedArray[6]?1<<1:0) + (speedArray[7]?1:0));
    }


    //Getters for all signal properties
    public int getSignalAddress() {
        return signalAddress;
    }

    public boolean isSignalInUse(){
        return signalInUse;
    }

    public boolean[] getDb2bits() {
        return db2bits;
    }

    //Getters for all the functions
//    public boolean getFunctionState(int index) {
//        return functionStates[index];
//    }
}
