package z21Drive.actions;

import z21Drive.Z21;

import java.util.ArrayList;
import java.util.List;

public abstract class Z21Action {

    private Z21 z21;

    public Z21Action( Z21 z21 ) {
        this.z21 = z21;
    }

    public Z21 getZ21() {
        return z21;
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
