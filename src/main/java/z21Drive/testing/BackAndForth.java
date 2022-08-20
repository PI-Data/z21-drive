package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21ActionSetLocoDrive;

import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Used to test loco driving functionality.
 * Loco is supposed to drive forward and backward for 30 seconds.
 *
 * @see z21Drive.actions.Z21ActionSetLocoDrive
 */
public class BackAndForth implements Runnable {
  public static final int LOCO_ADDRESS = 3;
  public static final String z21Addr = "192.168.188.20";
  private boolean exit;

  public static void main( String[] yoMama ) {
    new Thread( new BackAndForth() ).start();
  }

  private BackAndForth() {
    new Thread( new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep( 30000 );
          exit = true;
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } ).start();
  }

  @Override
  public void run() {
    Z21 z21 = null;
    try {
      InetAddress z21address = InetAddress.getByName( z21Addr );
      z21 = new Z21( z21address );
      while (!exit) {
        try {
          int speed = 20;
          int speedStepsID = 3;
          Logger.getLogger( "BackAndForth" ).info( "Drive backward." );
          z21.sendActionToZ21( new Z21ActionSetLocoDrive( z21, LOCO_ADDRESS, speed, speedStepsID, true ) );
          Thread.sleep( 3000 );
          Logger.getLogger( "BackAndForth" ).info( "Drive forward." );
          z21.sendActionToZ21( new Z21ActionSetLocoDrive( z21, LOCO_ADDRESS, speed, speedStepsID, false ) );
          Thread.sleep( 3000 );
        }
        catch (LocoAddressOutOfRangeException e) {
          e.printStackTrace();
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      try {
        z21.sendActionToZ21( new Z21ActionSetLocoDrive( z21, LOCO_ADDRESS, 0, 3, false ) );
      }
      catch (LocoAddressOutOfRangeException e) {
        e.printStackTrace();
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
