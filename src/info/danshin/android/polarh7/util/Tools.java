package info.danshin.android.polarh7.util;

public class Tools {
	public static boolean isBLESupported() {
		try {
			Class.forName("android.bluetooth.BluetoothGattService");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String convertToString(byte[] data) {
		if(data == null)
			return String.valueOf(data);
		StringBuilder sb = new StringBuilder().append("{");
		for(byte b: data) {
			int i = b;
			sb.append((i + 256)%256).append(" ");
		}
		return sb.append("}").toString();
	}
	
	public static String convertToString(short[] data) {
		if(data == null)
			return String.valueOf(data);
		StringBuilder sb = new StringBuilder().append("{");
		for(short b: data) {
			int i = b;
			sb.append((i + Short.MAX_VALUE)%Short.MAX_VALUE).append(" ");
		}
		return sb.append("}").toString();
	}
}
