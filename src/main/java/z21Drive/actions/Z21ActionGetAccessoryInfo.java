package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;

import static z21Drive.Z21LanConstants.*;

/**
 * Used to retrieve loco status from z21.
 * Supports loco addresses up to 128.
 */
public class Z21ActionGetAccessoryInfo extends Z21Action {

  /**
   * @param locoAddress Address of the loco to request info of.
   * @throws LocoAddressOutOfRangeException Thrown if loco address is out of supported range.
   */
  public Z21ActionGetAccessoryInfo(Z21 z21, int locoAddress ) throws LocoAddressOutOfRangeException {
    super( z21 );
    byteRepresentation.add( Byte.decode( LAN_X_Header ) );
    byteRepresentation.add( Byte.decode( LAN_X_Zero ) );
    if (locoAddress < 1)
      throw new LocoAddressOutOfRangeException( locoAddress );
    addDataToByteRepresentation( new Object[]{locoAddress} );
    addLenByte();
  }

  @Override
  public void addDataToByteRepresentation( Object[] objs ) {
    //Add all the data
    byteRepresentation.add( (byte) (int) Integer.decode( LAN_X_GET_EXT_ACCESSORY_INFO ) );
    byteRepresentation.add( (byte) (int) Integer.decode( "0xF0" ) );
    byte Adr_MSB = (byte) (((Integer) objs[0]) >> 8);
    byte Adr_LSB = (byte) (((Integer) objs[0]) & 0b11111111);
    if (Adr_MSB != 0) {
      Adr_MSB |= 0b11000000;
    }

    byteRepresentation.add( Adr_MSB );
    byteRepresentation.add( Adr_LSB );
    byteRepresentation.add( (byte) (byteRepresentation.get( 2 ) ^ byteRepresentation.get( 3 ) ^ byteRepresentation.get( 4 ) ^ byteRepresentation.get( 5 )) );
  }
}
