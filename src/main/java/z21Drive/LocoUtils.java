package z21Drive;

import z21Drive.actions.Z21ActionGetLocoInfo;
import z21Drive.actions.Z21ActionSetLocoDrive;
import z21Drive.actions.Z21ActionSetLocoFunction;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

/**
 * A group of useful functions for loco manipulation
 */
public class LocoUtils {

  public static void stopLoco( Z21 z21, int locoAddress ) {
    z21.addBroadcastListener( new Z21BroadcastListener() {
      @Override
      public void onBroadCast( BroadcastTypes type, Z21Broadcast broadcast ) {
        Z21BroadcastLanXLocoInfo bcast = (Z21BroadcastLanXLocoInfo) broadcast;
        byte speedStepsId = (byte) bcast.getSpeedSteps();
        boolean direction = bcast.getDirection();
        Z21ActionSetLocoDrive action;
        try {
          action = new Z21ActionSetLocoDrive( z21, locoAddress, 0, speedStepsId, direction );
        }
        catch (LocoAddressOutOfRangeException e) {
          System.err.println( "Idiot detected." );
          e.printStackTrace();
          return;
        }
        z21.sendActionToZ21( action );
      }

      @Override
      public BroadcastTypes[] getListenerTypes() {
        return new BroadcastTypes[]{BroadcastTypes.LAN_X_LOCO_INFO};
      }
    } );
    try {
      z21.sendActionToZ21( new Z21ActionGetLocoInfo( z21, locoAddress ) );
    }
    catch (LocoAddressOutOfRangeException e) {
      System.err.println( "Idiot detected." );
      e.printStackTrace();
    }
  }

  public static void activateFunction( Z21 z21, int locoAddress, int function ) {
    try {
      Z21ActionSetLocoFunction action = new Z21ActionSetLocoFunction( z21, locoAddress, function, true );
      z21.sendActionToZ21( action );
      Thread.sleep( 500 );
      action = new Z21ActionSetLocoFunction( z21, locoAddress, function, false );
      z21.sendActionToZ21( action );
    }
    catch (LocoAddressOutOfRangeException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
