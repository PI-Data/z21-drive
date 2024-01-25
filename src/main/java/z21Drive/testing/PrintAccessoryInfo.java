package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetLocoInfo;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXLocoInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

import java.net.InetAddress;

/**
 * Sends request for info of loco #5 and then keeps printing any changes.
 *
 * @see Z21
 */
public class PrintAccessoryInfo implements Runnable {
  boolean finished;
  int signalAdress;

  public static void main( String[] args ) {
    //Start things up
    new Thread( new PrintAccessoryInfo( Integer.parseInt( args[0] ) ) ).start();
  }

  public PrintAccessoryInfo( int signalAdress ) {
    this.signalAdress = signalAdress;
  }

  public void run() {
    Z21 z21 = null;
    try {
      InetAddress z21address = InetAddress.getByName( "192.168.188.2" );
      z21 = new Z21( z21address );
      z21.addBroadcastListener( new Z21BroadcastListener() {
        @Override
        public void onBroadCast( BroadcastTypes type, Z21Broadcast broadcast ) {
          if (type == BroadcastTypes.LAN_X_LOCO_INFO) {
            Z21BroadcastLanXLocoInfo bc = (Z21BroadcastLanXLocoInfo) broadcast;
            System.out.println( "Loco address: " + bc.getLocoAddress() );
            System.out.println( "Lights: " + bc.getFunctionState(0) );
            System.out.println( "Speed steps: " + bc.getSpeedSteps() );
            System.out.println( "Direction: " + bc.getDirection() );
            System.out.println( "Speed: " + bc.getSpeed() );
            System.out.println( "Raw data:" );
            for (byte b : bc.getByteRepresentation())
              System.out.print( b + " " );
            System.out.print( "\n" );
            System.out.println( "Array length: " + bc.getByteRepresentation().length );
            finished = true;
          } else {
            System.out.println("BroadcastType: " + type.name());
          }
        }

        @Override
        public BroadcastTypes[] getListenerTypes() {
          return new BroadcastTypes[]{BroadcastTypes.LAN_X_LOCO_INFO};
        }
      } );

      try {
        z21.sendActionToZ21( new Z21ActionGetLocoInfo( z21, signalAdress ) );
      }
      catch (LocoAddressOutOfRangeException e) {
        e.printStackTrace();
        finished = true;
      }

      while (!finished) {
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if (z21 != null) {
        z21.shutdown();
      }
    }
  }
}
