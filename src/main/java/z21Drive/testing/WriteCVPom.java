package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionLanXCVPomWriteByte;
import z21Drive.actions.Z21ActionLanXCVWrite;
import z21Drive.actions.Z21ActionLanXTrackPowerOff;
import z21Drive.actions.Z21ActionLanXTrackPowerOn;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseLanXCVResult;
import z21Drive.responses.Z21ResponseListener;

import java.net.InetAddress;

/**
 * BE CAREFUL!
 * <p>
 * This Class writes a new Loco ID to CV 1
 * <p>
 * BE CAREFUL!
 */
public class WriteCVPom implements Runnable {

  public static void main( String[] args ) {
    //Start things up
    new Thread( new WriteCVPom() ).start();
  }

  public void run() {
    Z21 z21 = null;
    try {
      InetAddress z21address = InetAddress.getByName( "192.168.188.20" );
      z21 = new Z21( z21address );
      z21.sendActionToZ21( new Z21ActionLanXTrackPowerOn( z21 ) );
      Z21 finalZ2 = z21;
      z21.addResponseListener( new Z21ResponseListener() {
        @Override
        public void responseReceived( ResponseTypes type, Z21Response response ) {
          if (type == ResponseTypes.LAN_X_CV_RESULT) {
            // Output of the Results Response
            Z21ResponseLanXCVResult bc = (Z21ResponseLanXCVResult) response;
            String o = Integer.toBinaryString( bc.getValue() );
            while (o.length() < 8) {
              o = "0" + o;
            }
            System.out.println( String.format( "%3d: %3d %s", bc.getCVadr(), bc.getValue(), o ) );
          }
          else if (type == ResponseTypes.LAN_X_CV_NACK) {
            System.out.println( "Write CV failed." );
          }
          // Active Track Power
          finalZ2.sendActionToZ21( new Z21ActionLanXTrackPowerOn( finalZ2 ) );
          System.exit( 0 );
        }

        @Override
        public ResponseTypes[] getListenerTypes() {
          return new ResponseTypes[]{ResponseTypes.LAN_X_CV_RESULT, ResponseTypes.LAN_X_CV_NACK};
        }
      } );
      sendNext( z21 );
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

  private void sendNext( Z21 z21 ) {
    try {
      z21.sendActionToZ21( new Z21ActionLanXCVPomWriteByte( z21, 2, 1, 2 ) ); // Change loco addr from 2 to 2 ...
    }
    catch (LocoAddressOutOfRangeException e) {
      e.printStackTrace();
    }
  }
}
