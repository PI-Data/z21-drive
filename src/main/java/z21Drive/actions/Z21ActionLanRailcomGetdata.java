package z21Drive.actions;

import z21Drive.Z21;

/**
 * Requests Railcom Data from the z21
 *
 * @author sven
 */
public class Z21ActionLanRailcomGetdata extends Z21Action {

  public Z21ActionLanRailcomGetdata( Z21 z21 ) {
    super( z21 );
    byteRepresentation.add( (byte) 0x89 );
    byteRepresentation.add( (byte) 0x00 );
    addLenByte();
  }

  //Not necessary here
  @Override
  public void addDataToByteRepresentation( Object[] objs ) {
  }
}