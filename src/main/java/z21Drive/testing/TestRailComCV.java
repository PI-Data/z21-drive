package z21Drive.testing;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionLanXCVPomReadByte;
import z21Drive.actions.Z21ActionLanXTrackPowerOn;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseLanXCVResult;
import z21Drive.responses.Z21ResponseListener;

/**
 * Reads the CV Number of the Loco via RailCom
 *
 * @see z21Drive.Z21
 */
public class TestRailComCV implements Runnable {

  ArrayList<Integer> cvs = new ArrayList<Integer>( Arrays.asList( new Integer[]{1, 28, 29, 30} ) );

  public static void main( String[] args ) {
    //Start things up
    new Thread( new TestRailComCV() ).start();
    while (true) {
    }
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
            Z21ResponseLanXCVResult bc = (Z21ResponseLanXCVResult) response;
            String o = Integer.toBinaryString( bc.getValue() );
            while (o.length() < 8) {
              o = "0" + o;
            }
            System.out.println( String.format( "%3d: %3d %s", bc.getCVadr(), bc.getValue(), o ) );
            sendNext( finalZ2 );
          }
          else if (type == ResponseTypes.LAN_X_CV_NACK) {
            System.out.println( "Read CV failed." );
            System.exit( -1 );
          }
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
    if (cvs.size() == 0) {
      z21.sendActionToZ21( new Z21ActionLanXTrackPowerOn( z21 ) );
      System.exit( 1 );
    }
    Integer cv = cvs.get( 0 );
    cvs.remove( 0 );
    try {
      z21.sendActionToZ21( new Z21ActionLanXCVPomReadByte( z21, 7, cv ) );
    }
    catch (LocoAddressOutOfRangeException e) {
      e.printStackTrace();
    }
  }
}
