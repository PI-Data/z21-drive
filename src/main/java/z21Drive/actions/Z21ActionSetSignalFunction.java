package z21Drive.actions;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;

import java.util.logging.Logger;

/**
 * Sent to z21 to change a signal function. Supports functions from F0 to F12.
 * With some more work it could be made to allow use of more functions.
 * Supports signal addresses from 1 to 128.
 */
public class Z21ActionSetSignalFunction extends Z21Action {

  public Z21ActionSetSignalFunction(final Z21 z21, final int signalAddress, final int functionNo, final boolean on ) throws LocoAddressOutOfRangeException {
    super( z21 );
    byteRepresentation.add( Byte.decode( "0x40" ) );
    byteRepresentation.add( Byte.decode( "0x00" ) );
    if (signalAddress < 1)
      throw new LocoAddressOutOfRangeException( signalAddress );
    addDataToByteRepresentation( new Object[]{Integer.valueOf( signalAddress ), functionNo, Boolean.valueOf( on )} );
    addLenByte();
  }

  @Override
  public void addDataToByteRepresentation(final Object[] objs ) {
    byteRepresentation.add( (byte) 0x53 );
    byte Adr_MSB = (byte) (((Integer) objs[0]) >> 8);
    final byte Adr_LSB = (byte) (((Integer) objs[0]) & 0b11111111);
    if (Adr_MSB != 0) {
      Adr_MSB |= 0b11000000;
    }
    byteRepresentation.add( Adr_MSB );
    byteRepresentation.add( Adr_LSB );

    //Generate data byte
    final int dataByte = (Integer) objs[1];
//    if ((Boolean) objs[2])
//      dataByte = ((Integer) objs[1] & 63) | 128;
//    else
//      dataByte = (Integer) objs[1] & 63;

    byteRepresentation.add( (byte) dataByte );

    //Add the XOR byte
    final byte xor;
    xor = (byte) (byteRepresentation.get( 2 ) ^ byteRepresentation.get( 3 ) ^ byteRepresentation.get( 4 ) ^ byteRepresentation.get( 5 ));
    byteRepresentation.add( xor );

    Logger.getLogger( "Z21ActionSetSignalFunction" ).info( "Byte Representation:" + hexdump() );
  }
}
