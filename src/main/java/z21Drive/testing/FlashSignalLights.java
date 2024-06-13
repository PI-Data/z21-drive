package z21Drive.testing;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionSetLocoFunction;
import z21Drive.actions.Z21ActionSetSignalFunction;

import java.net.InetAddress;
import java.util.logging.Logger;

/**
 * Used to test if setLocoFunction works.
 * If it's working properly, loco keeps it's headlights flashing.
 *
 * @author grizeldi
 * @see Z21ActionSetLocoFunction
 */
public class FlashSignalLights implements Runnable {
  int signalAddress;
  int functionNo;
  private boolean exit;

  public static void main(final String[] args ) {
    if (args.length < 1) {
      System.err.println("Error: Please specify DCC address.");
//        System.err.println("Error: Please specify DCC address and function number.");
      System.exit(1);
    }
    new Thread( new FlashSignalLights( Integer.parseInt( args[0] ), 7 ) ).start();
//    new Thread( new FlashSignalLights( Integer.parseInt( args[0] ), Integer.parseInt( args[1] ) ) ).start();
  }

  private FlashSignalLights(final int locoAdress, final int functionNo) {
    this.signalAddress = locoAdress;
    this.functionNo = functionNo;
    new Thread( () -> {
      try {
        Thread.sleep( 30000 );
      }
      catch (final InterruptedException e) {
        e.printStackTrace();
      }
      exit = true;
    } ).start();
  }

  @Override
  public void run() {
    Z21 z21 = null;
    try {
      final InetAddress z21address = InetAddress.getByName( "192.168.188.2" );
      z21 = new Z21( z21address );
      final int [][] values = {
              {0, 0x88}, // stop
              {0, 0x89}, // go
              {1, 0x89}, // go 40
              {1, 0x88}  // shunting
      };
      while (!exit) {
        try {
          for (int i = 0; i < values.length; i ++) {
            final int address_offset = values[i][0];
            final int value = values[i][1];
            if (i > 0) {
              Thread.sleep(5000);
            }
//            System.out.println();
//            System.out.println( "FlashSignalLights  : " + (signalAddress + address_offset) + Z21Action.toHex(value) );
            Logger.getLogger( "FlashSignalLights" ).info( "Light: " + (signalAddress + address_offset) + " -> " + Z21Action.toHex(value) );
            z21.sendActionToZ21( new Z21ActionSetSignalFunction( z21, signalAddress + address_offset, value, true ) );
//            int read = System.in.read();
//            System.out.println(read);
          }

          exit = true;
        }
        catch (final LocoAddressOutOfRangeException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    catch (final Exception ex) {
      ex.printStackTrace();
    }
    finally {
      if (z21 != null) {
        z21.shutdown();
      }
    }
  }
}
