package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionSetLocoFunction;

import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Used to test if setLocoFunction works.
 * If it's working properly, loco keeps it's headlights flashing.
 *
 * @author grizeldi
 * @see z21Drive.actions.Z21ActionSetLocoFunction
 */
public class FlashHeadLights implements Runnable {
  int locoAdress;
  private boolean exit;

  public static void main( String[] args ) {
    new Thread( new FlashHeadLights( Integer.parseInt( args[0] ) ) ).start();
  }

  private FlashHeadLights(int locoAdress) {
    this.locoAdress = locoAdress;
    new Thread( () -> {
      try {
        Thread.sleep( 30000 );
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      exit = true;
    } ).start();
  }

  @Override
  public void run() {
    Z21 z21 = null;
    try {
      InetAddress z21address = InetAddress.getByName( "192.168.188.2" );
      z21 = new Z21( z21address );
      while (!exit) {
        try {
          Logger.getLogger( "FlashHeadLights" ).info( "Headlight on." );
          z21.sendActionToZ21( new Z21ActionSetLocoFunction( z21, locoAdress, 0, true ) );
          Thread.sleep( 2000 );
          Logger.getLogger( "FlashHeadLights" ).info( "Headlight off." );
          z21.sendActionToZ21( new Z21ActionSetLocoFunction( z21, locoAdress, 0, false ) );
          Thread.sleep( 2000 );
        }
        catch (LocoAddressOutOfRangeException e) {
          e.printStackTrace();
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
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
