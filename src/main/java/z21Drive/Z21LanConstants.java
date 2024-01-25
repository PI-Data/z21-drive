package z21Drive;

public class Z21LanConstants {
  public static final String LAN_X_Zero = "0x00";  //not in Spezifikation!
  public static final String LAN_X_Header = "0x40";  //not in Spezifikation!
  public static final String LAN_GET_SERIAL_NUMBER = "0x10";
  public static final String LAN_GET_CODE = "0x18";        //SW Feature-Umfang der Z21
  public static final String LAN_LOGOFF = "0x30";
  public static final String LAN_X_GET_SETTING = "0x21";
  public static final String LAN_X_BC_TRACK_POWER = "0x61";
  public static final String LAN_X_UNKNOWN_COMMAND = "0x61";
  public static final String LAN_X_STATUS_CHANGED = "0x62";
  public static final String LAN_X_GET_VERSION = "0x63";        //AW: X-Bus Version 090040006321301260
  public static final String LAN_X_SET_STOP = "0x80";  //AW: LAN_X_BC_STOPPED
  public static final String LAN_X_BC_STOPPED = "0x81";
  public static final String LAN_X_GET_FIRMWARE_VERSION = "0xF1";  //AW: 0xF3
  public static final String LAN_SET_BROADCASTFLAGS = "0x50";
  public static final String LAN_GET_BROADCASTFLAGS = "0x51";
  public static final String LAN_SYSTEMSTATE_DATACHANGED = "0x84";
  public static final String LAN_SYSTEMSTATE_GETDATA = "0x85";  //AW: LAN_SYSTEMSTATE_DATACHANGED
  public static final String LAN_GET_HWINFO = "0x1A";
  public static final String LAN_GET_LOCOMODE = "0x60";
  public static final String LAN_SET_LOCOMODE = "0x61";
  public static final String LAN_GET_TURNOUTMODE = "0x70";
  public static final String LAN_SET_TURNOUTMODE = "0x71";
  public static final String LAN_X_GET_LOCO_INFO = "0xE3";
  public static final String LAN_X_SET_LOCO = "0xE4";  //X-Header
  public static final String LAN_X_SET_LOCO_FUNCTION = "0xF8";  //DB0
  public static final String LAN_X_SET_LOCO_BINARY_STATE = "0xE5";  //X-Header
  public static final String LAN_X_LOCO_INFO = "0xEF";
  public static final String LAN_X_GET_TURNOUT_INFO = "0x43";
  public static final String LAN_X_SET_TURNOUT = "0x53";
  public static final String LAN_X_TURNOUT_INFO = "0x43";
  public static final String LAN_X_SET_EXT_ACCESSORY = "0x54";        //new: 1.10
  public static final String LAN_X_GET_EXT_ACCESSORY_INFO = "0x44";        //new: 1.10
  public static final String LAN_X_CV_READ = "0x23";
  public static final String LAN_X_CV_WRITE = "0x24";
  public static final String LAN_X_CV_NACK_SC = "0x61";
  public static final String LAN_X_CV_NACK = "0x61";
  public static final String LAN_X_CV_RESULT = "0x64";
  public static final String LAN_RMBUS_DATACHANGED = "0x80";
  public static final String LAN_RMBUS_GETDATA = "0x81";
  public static final String LAN_RMBUS_PROGRAMMODULE = "0x82";

  public static final String LAN_RAILCOM_DATACHANGED = "0x88";
  public static final String LAN_RAILCOM_GETDATA = "0x89";

  public static final String LAN_LOCONET_Z21_RX = "0xA0";
  public static final String LAN_LOCONET_Z21_TX = "0xA1";
  public static final String LAN_LOCONET_FROM_LAN = "0xA2";
  public static final String LAN_LOCONET_DISPATCH_ADDR = "0xA3";
  public static final String LAN_LOCONET_DETECTOR = "0xA4";

  public static final String LAN_CAN_DETECTOR = "0xC4";

  public static final String LAN_X_CV_POM = "0xE6";  //X-Header
  public static final String LAN_X_CV_POM_WRITE_BYTE = "0xEC";  //DB3 Option
  public static final String LAN_X_CV_POM_WRITE_BIT = "0xE8";  //DB3 Option
  public static final String LAN_X_CV_POM_READ_BYTE = "0xE4";  //DB3 Option
  public static final String LAN_X_CV_POM_ACCESSORY_WRITE_BYTE = "0xEC";        //DB3 Option
  public static final String LAN_X_CV_POM_ACCESSORY_WRITE_BIT = "0xE8";        //DB3 Option
  public static final String LAN_X_CV_POM_ACCESSORY_READ_BYTE = "0xE4";        //DB3 Option

  //ab Z21 FW Version 1.23
  public static final String LAN_X_MM_WRITE_BYTE = "0x24";

  //ab Z21 FW Version 1.25
  public static final String LAN_X_DCC_READ_REGISTER = "0x22";
  public static final String LAN_X_DCC_WRITE_REGISTER = "0x23";

}
