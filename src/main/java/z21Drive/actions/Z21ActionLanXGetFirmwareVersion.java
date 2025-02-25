package z21Drive.actions;

import z21Drive.Z21;

import java.util.Arrays;

public class Z21ActionLanXGetFirmwareVersion extends Z21Action {
  private static final Byte[] rep = new Byte[]{0x07, 0x00, 0x40, 0x00, (byte) 0xF1, 0x0A, (byte) 0xFB};

  public Z21ActionLanXGetFirmwareVersion( Z21 z21 ) {
    super( z21 );
    byteRepresentation = Arrays.asList( rep );
  }

  /**
   * Not necessary here.
   *
   * @param objs Whatever
   */
  @Override
  public void addDataToByteRepresentation( Object[] objs ) {
  }
}
