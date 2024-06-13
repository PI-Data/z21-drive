package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionGetSignalInfo;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastLanXSignalInfo;
import z21Drive.broadcasts.Z21BroadcastListener;

import java.net.InetAddress;

/**
 * Sends request for info of loco #5 and then keeps printing any changes.
 *
 * @see Z21
 */
public class PrintSignalInfo implements Runnable {
  boolean finished;
  int signalAdress;

  public static void main( String[] args ) {
    if (args.length == 0) {
      System.err.println("Error: Please specify DCC address.");
      System.exit(1);
    }
    //Start things up
    new Thread( new PrintSignalInfo( Integer.parseInt( args[0] ) ) ).start();
  }

  public PrintSignalInfo( int signalAdress ) {
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
          if (type == BroadcastTypes.LAN_X_SIGNAL_INFO) {
            Z21BroadcastLanXSignalInfo bc = (Z21BroadcastLanXSignalInfo) broadcast;
            System.out.println( "Signal address: " + bc.getSignalAddress() );
            System.out.println( "Array length: " + bc.getByteRepresentation().length );
            System.out.println( "Array content:" );
            for (byte b : bc.getByteRepresentation())
              System.out.print( b + " " );
            System.out.print( "\n" );
            System.out.println( "Data byte:" );
            System.out.println( bc.getByteRepresentation()[7] );
            if (bc.getSignalAddress() > signalAdress) {
              finished = true;
            }
          } else {
            System.out.println("BroadcastType: " + type.name());
          }
        }

        @Override
        public BroadcastTypes[] getListenerTypes() {
          return new BroadcastTypes[]{BroadcastTypes.LAN_X_SIGNAL_INFO};
        }
      } );

      try {
        z21.sendActionToZ21( new Z21ActionGetSignalInfo( z21, signalAdress ) );
        Thread.sleep(1000);
        z21.sendActionToZ21( new Z21ActionGetSignalInfo( z21, signalAdress + 1 ) );
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
