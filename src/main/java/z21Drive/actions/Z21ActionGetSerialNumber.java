package z21Drive.actions;

import z21Drive.Z21;

/**
 * Mostly used to poll the keepAlive timer, but feel free to use it if you really want to know
 * what is the serial number of your z21...
 */
public class Z21ActionGetSerialNumber extends Z21Action {

  public Z21ActionGetSerialNumber( Z21 z21 ) {
    super( z21 );
    byteRepresentation.add( (byte) 0x10 );
    byteRepresentation.add( (byte) 0x00 );
    addLenByte();
  }

  //Not necessary here
  @Override
  public void addDataToByteRepresentation( Object[] objs ) {
  }
}
