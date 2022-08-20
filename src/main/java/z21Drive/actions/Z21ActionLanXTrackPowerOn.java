package z21Drive.actions;

import z21Drive.Z21;

import java.util.Arrays;

/**
 * Used to switch track power on.
 */
public class Z21ActionLanXTrackPowerOn extends Z21Action {
  //Made a static one here for better performance.
  private static final Byte[] rep = new Byte[]{0x07, 0x00, 0x40, 0x00, 0x21, (byte) 0x81, (byte) 0xa0};

  public Z21ActionLanXTrackPowerOn( Z21 z21 ) {
    super( z21 );
    byteRepresentation = Arrays.asList( rep );
  }

  /**
   * Not necessary here.
   *
   * @param objs Null or whatever.
   */
  @Override
  public void addDataToByteRepresentation( Object[] objs ) {
  }
}
