package z21Drive.actions;

import z21Drive.Z21;

import java.util.ArrayList;
import java.util.List;

public abstract class Z21Action {

    public static final char [] hexdigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private Z21 z21;

    public Z21Action(final Z21 z21 ) {
        this.z21 = z21;
    }

    public Z21 getZ21() {
        return z21;
    }

    public String hexdump() {
        final StringBuilder bld = new StringBuilder();
        for (final Byte b : byteRepresentation) {
            final int v = (b + 256) % 256; // convert to unsigned
            toHex(bld, v);
        }
        return bld.toString();
    }

    public static void toHex(final StringBuilder bld, final int value) {
        bld.append(' ');
        bld.append(hexdigits[value / 16]);
        bld.append(hexdigits[value % 16]);
    }

    public static String toHex(final int value) {
        final StringBuilder bld = new StringBuilder();
        toHex(bld, value);
        return bld.toString();
    }

    /**
     * Represents the message in z21 understandable form.
     */
    protected List<Byte> byteRepresentation = new ArrayList<Byte>();

    public List<Byte> getByteRepresentation(){
        return byteRepresentation;
    }

    /**
     * Here actual conversion to bytes happens
     * @param objs Whatever objects you might need to determine the bytes.
     */
    protected abstract void addDataToByteRepresentation(Object[] objs);

    /**
     * Adds the required length of message bytes.
     */
    protected void addLenByte(){
        byte len = (byte) byteRepresentation.size();
        len += 2;
        byteRepresentation.add(0, (byte) 0);
        byteRepresentation.add(0, len);
    }
}
