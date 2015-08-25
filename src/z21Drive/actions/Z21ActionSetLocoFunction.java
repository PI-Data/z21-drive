package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;

/**
 * Sent to z21 to change a loco function. Supports functions from F0 to F12.
 * With some more work it could be made to allow use of more functions.
 * Supports loco addresses from 1 to 63.
 */
public class Z21ActionSetLocoFunction extends Z21Action{
    public Z21ActionSetLocoFunction(int locoAddress, int functionNo, boolean on) throws LocoAddressOutOfRangeException{
        byteRepresentation.add(Byte.decode("0x40"));
        byteRepresentation.add(Byte.decode("0x00"));
        if (locoAddress < 1 || locoAddress > 63)
            throw new LocoAddressOutOfRangeException(locoAddress);
        addDataToByteRepresentation(new Object[]{Integer.valueOf(locoAddress), functionNo, Boolean.valueOf(on)});
        addLenByte();
    }

    @Override
    public void addDataToByteRepresentation(Object[] objs) {
        byteRepresentation.add((byte)0xE4);
        byteRepresentation.add((byte)0xF8);
        byte Adr_MSB;
        byte Adr_LSB;
        //Made working in attempt number 3
        String binary = String.format("%16s", Integer.toBinaryString((Integer) objs[0])).replace(' ', '0');
        String binaryMSB = binary.substring(0, 8);
        String binaryLSB = binary.substring(8);

        if (binary.replaceFirst ("^0*", "").toCharArray().length <= 8)
            Adr_MSB = 0;
        else
            Adr_MSB = (byte) Integer.parseInt(binaryMSB, 2);
        Adr_LSB = (byte) Integer.parseInt(binaryLSB, 2);
        byteRepresentation.add(Adr_MSB);
        byteRepresentation.add(Adr_LSB);

        //Generate data byte
        boolean [] dataByte = new boolean[8];
        if ((Boolean) objs[2]){
            dataByte[0] = false;
            dataByte[1] = true;
        }else {
            dataByte[0] = false;
            dataByte[1] = false;
        }
        switch ((Integer) objs [0]){
            case 0:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = false;
                dataByte[6] = false;
                dataByte[7] = false;
                break;
            case 1:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = false;
                dataByte[6] = false;
                dataByte[7] = true;
                break;
            case 2:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = false;
                dataByte[6] = true;
                dataByte[7] = false;
                break;
            case 3:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = false;
                dataByte[6] = true;
                dataByte[7] = true;
                break;
            case 4:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = true;
                dataByte[6] = false;
                dataByte[7] = false;
                break;
            case 5:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = true;
                dataByte[6] = false;
                dataByte[7] = true;
                break;
            case 6:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = true;
                dataByte[6] = true;
                dataByte[7] = false;
                break;
            case 7:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = false;
                dataByte[5] = true;
                dataByte[6] = true;
                dataByte[7] = true;
                break;
            case 8:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = true;
                dataByte[5] = false;
                dataByte[6] = false;
                dataByte[7] = false;
                break;
            case 9:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = true;
                dataByte[5] = false;
                dataByte[6] = false;
                dataByte[7] = true;
                break;
            case 10:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = true;
                dataByte[5] = false;
                dataByte[6] = true;
                dataByte[7] = false;
                break;
            case 11:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = true;
                dataByte[5] = false;
                dataByte[6] = true;
                dataByte[7] = true;
                break;
            case 12:
                dataByte[2] = false;
                dataByte[3] = false;
                dataByte[4] = true;
                dataByte[5] = true;
                dataByte[6] = false;
                dataByte[7] = false;
                break;
        }

        String s = "";
        for (boolean aDataByte : dataByte) {
            if (aDataByte) {
                s = s.concat("1");
            } else {
                s = s.concat("0");
            }
        }
        byteRepresentation.add((byte)Integer.parseInt(s, 2));

        //Add the XOR byte
        byte xor;
        xor = (byte) (byteRepresentation.get(2) ^ byteRepresentation.get(3) ^ byteRepresentation.get(4) ^ byteRepresentation.get(5) ^ byteRepresentation.get(6));
        byteRepresentation.add(xor);
    }
}
