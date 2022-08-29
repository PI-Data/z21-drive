package z21Drive.broadcasts;

/**
 * Probably the most important broadcast, because it represents the current state of a loco.
 * Supports up to 28 functions.
 */
public class Z21BroadcastLanXLocoInfo extends Z21Broadcast{
    public static final int FUNCTION_COUNT = 29;
    private int locoAddress;
    private boolean locoInUse;
    private int speedSteps;
    /**
     * Represents direction in which loco is driving.
     * true = forward, false = backward
     */
    private boolean direction;
    private int speed;
//    private boolean f0On, f1On, f2On, f3On, f4On, f5On, f6On, f7On, f8On, f9On, f10On, f11On, f12On, f13On,
//            f14On, f15On, f16On, f17On, f18On, f19On, f20On, f21On, f22On, f23On, f24On, f25On, f26On, f27On, f28On;

    private boolean[] functionStates = new boolean[FUNCTION_COUNT];

    public Z21BroadcastLanXLocoInfo(byte[] initArray) {
        super(initArray);
        boundType = BroadcastTypes.LAN_X_LOCO_INFO;
        if (byteRepresentation != null)
            populateFields();
        else
            for (int i = 0; i < FUNCTION_COUNT; i ++)
                functionStates[i] = false;
    }

    private void populateFields(){
        byte adr_MSB = byteRepresentation [5];
        byte adr_LSB = byteRepresentation [6];
        locoAddress = (adr_MSB & 0x3F) << 8 | adr_LSB;

        boolean [] db2bits = fromByte(byteRepresentation[7]);
        locoInUse = db2bits[4];
        String binary = String.format("%8s", Integer.toBinaryString(byteRepresentation[7])).replace(' ', '0');
        if (binary.equals("00000000") || binary.equals("00001000"))
            speedSteps = 14;
        else if (binary.equals("00000010") || binary.equals("00001010"))
            speedSteps = 28;
        else if (binary.equals("00000100") || binary.equals("00001100"))
            speedSteps = 128;

        boolean [] db3bits = fromByte(byteRepresentation[8]);
        direction = byteRepresentation[8] < 0;
        boolean [] speedArray = db3bits.clone();

        if (direction) {
            speedArray = fromByte((byte) (byteRepresentation[8] + 128));
        }
        speedArray [0] = false;
        speed = ((speedArray[0]?1<<7:0) + (speedArray[1]?1<<6:0) + (speedArray[2]?1<<5:0) +
                (speedArray[3]?1<<4:0) + (speedArray[4]?1<<3:0) + (speedArray[5]?1<<2:0) +
                (speedArray[6]?1<<1:0) + (speedArray[7]?1:0));

        //Set all functions.
        //Not really a good design choice having so many variables...
        //// FIXME: 19.2.2016 one day when I have too much time change this into an array
        boolean [] db4bits = fromByte(byteRepresentation[9]);
        functionStates[0] = db4bits[3];
        functionStates[1] = db4bits[7];
        functionStates[2] = db4bits[6];
        functionStates[3] = db4bits[5];
        functionStates[4] = db4bits[4];
        boolean [] db5bits = fromByte(byteRepresentation[10]);
        functionStates[5] = db5bits[0];
        functionStates[6] = db5bits[1];
        functionStates[7] = db5bits[2];
        functionStates[8] = db5bits[3];
        functionStates[9] = db5bits[4];
        functionStates[10] = db5bits[5];
        functionStates[11] = db5bits[6];
        functionStates[12] = db5bits[7];
        boolean [] db6bits = fromByte(byteRepresentation[11]);
        functionStates[13] = db6bits[0];
        functionStates[14] = db6bits[1];
        functionStates[15] = db6bits[2];
        functionStates[16] = db6bits[3];
        functionStates[17] = db6bits[4];
        functionStates[18] = db6bits[5];
        functionStates[19] = db6bits[6];
        functionStates[20] = db6bits[7];
        boolean [] db7bits = fromByte(byteRepresentation[12]);
        functionStates[21] = db7bits[0];
        functionStates[22] = db7bits[1];
        functionStates[23] = db7bits[2];
        functionStates[24] = db7bits[3];
        functionStates[25] = db7bits[4];
        functionStates[26] = db7bits[5];
        functionStates[27] = db7bits[6];
        functionStates[28] = db7bits[7];
    }


    //Getters for all locomotive properties
    public boolean isLocoInUse(){
        return locoInUse;
    }

    public int getLocoAddress() {
        return locoAddress;
    }

    public int getSpeedSteps() {
        return speedSteps;
    }

    /**
     * Represents direction in which loco is driving.
     * true = forward, false = backward
     * @return boolean following those rules
     */
    public boolean getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    //Getters for all them functions
    public boolean getFunctionState(int index) {
        return functionStates[index];
    }

/*
    public boolean isF0On() {
        return functionStates[0];
    }

    public boolean isF1On() {
        return functionStates[1];
    }

    public boolean isF2On() {
        return functionStates[2];
    }

    public boolean isF3On() {
        return functionStates[3];
    }

    public boolean isF4On() {
        return functionStates[4];
    }

    public boolean isF5On() {
        return functionStates[5];
    }

    public boolean isF6On() {
        return functionStates[6];
    }

    public boolean isF7On() {
        return functionStates[7];
    }

    public boolean isF8On() {
        return functionStates[8];
    }

    public boolean isF9On() {
        return functionStates[9];
    }

    public boolean isF10On() {
        return functionStates[10];
    }

    public boolean isF11On() {
        return functionStates[11];
    }

    public boolean isF12On() {
        return functionStates[12];
    }

    public boolean isF13On() {
        return functionStates[13];
    }

    public boolean isF14On() {
        return functionStates[14];
    }

    public boolean isF15On() {
        return functionStates[15];
    }

    public boolean isF16On() {
        return functionStates[16];
    }

    public boolean isF17On() {
        return functionStates[17];
    }

    public boolean isF18On() {
        return functionStates[18];
    }

    public boolean isF19On() {
        return functionStates[19];
    }

    public boolean isF20On() {
        return functionStates[20];
    }

    public boolean isF21On() {
        return functionStates[21];
    }

    public boolean isF22On() {
        return functionStates[22];
    }

    public boolean isF23On() {
        return functionStates[23];
    }

    public boolean isF24On() {
        return functionStates[24];
    }

    public boolean isF25On() {
        return functionStates[25];
    }

    public boolean isF26On() {
        return functionStates[26];
    }

    public boolean isF27On() {
        return functionStates[27];
    }

    public boolean isF28On() {
        return functionStates[28];
    }
*/

    /**
     * Array of functions F0 to F12. I was too lazy to add all other functions.
     * @return Array of function values.
     */
/*
    public boolean [] getFunctionsAsArray(){
        boolean [] array = new boolean[13];
        array [0] = functionStates[0];
        array [1] = functionStates[1];
        array [2] = functionStates[2];
        array [3] = functionStates[3];
        array [4] = functionStates[4];
        array [5] = functionStates[5];
        array [6] = functionStates[6];
        array [7] = functionStates[7];
        array [8] = functionStates[8];
        array [9] = functionStates[9];
        array [10] = functionStates[10];
        array [11] = functionStates[11];
        array [12] = functionStates[12];
        return array;
    }
*/
}
