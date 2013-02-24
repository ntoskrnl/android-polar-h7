package info.danshin.android.polarh7;

import info.danshin.android.polarh7.util.HeartRateDataHandler;
import info.danshin.android.polarh7.util.HeartRateDataItem;
import info.danshin.android.polarh7.util.IMonitors;
import info.danshin.android.polarh7.util.Tools;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.motorola.bluetoothle.BluetoothGatt;
import com.motorola.bluetoothle.hrm.IBluetoothHrm;
import com.motorola.bluetoothle.hrm.IBluetoothHrmCallback;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	public static final ParcelUuid HRM = ParcelUuid
			.fromString("0000180D-0000-1000-8000-00805f9b34fb");
	private static final int CONNECTED = 1;
	private static final int CONNECTING = 2;
	private static final int DISCONNECTED = 3;
	private static final int DISCONNECTING = 4;
	private static final int INIT = -1;
	
	// Bluetooth Intent request codes
    private static final int REQUEST_ENABLE_BT = 2;

	private boolean bleSupported = false;
	private boolean bleBound = false;
	private boolean leDisconnected = true;
	public int leState = INIT;
	private IBluetoothHrm hrmService;
	private String hrmUUID;
	private IntentFilter filter_scan;
	private Context context;
	private static int sensorLocation = -1;
	private static int energyExpended = 0;
	private static int heartBeatsPerMinute = 0, previous_heartBeatsPerMinute = 0;
	boolean flag_leRcvrReg = false;
	callback callback1;
	AlertDialog.Builder alert_paired;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	ProgressDialog aDialog;
	
	private BluetoothDevice device;
	private BluetoothAdapter bluetoothAdapter;
	
	private TextView tvHeartRate;
	private TextView tvDevice;
	private TextView tvDeviceLocation;
	private TextView tvStatus;
	private Button btConnect;
	private Button btDisconnect;
	AlertDialog alertDialog;
	
	private BlockingQueue<HeartRateDataItem> heartRateDataQueue;
	private Thread heartRateDataHandler;

	private Handler mUIUpdateHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			updateUI();
		};
	};
	
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			hrmService = IBluetoothHrm.Stub.asInterface(service);
			bleBound = true;
		}

		public void onServiceDisconnected(ComponentName arg0) {
			hrmService = null;
			bleBound = false;
		}
	};
	
	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, "broadcastReceiver.onReceive(): some broadcast, action is: " + action);
			
			if (BluetoothGatt.CONNECT_COMPLETE.equals(action)) {
				Log.d(TAG, "broadcastReceiver.onReceive(): LE connection complete - ");

				int status = intent.getIntExtra("status", BluetoothGatt.FAILURE);
				
				String service = intent.getStringExtra("uuid");// Todo
				Log.d(TAG, "broadcastReceiver.onReceive(): service uuid="+service);
				if (status == BluetoothGatt.SUCCESS) {
					Log.d(TAG, "broadcastReceiver.onReceive(): Connected successfully ! Service: "
							+ service);

					leDisconnected = false;

					leState = CONNECTED;
					requestLocation();

				} else if (status != BluetoothGatt.SUCCESS) {
					Log.d(TAG, "broadcastReceiver.onReceive(): Connection failed. Service: "
							+ service);
					leState = DISCONNECTED;
					mUIUpdateHandler.sendEmptyMessage(0);
					leDisconnected = true;
					try {
						hrmService.disconnectLe(device, hrmUUID);
					} catch (RemoteException e) {
						Log.e(TAG, "", e);
					}
				}
			} else if (action.equals(BluetoothGatt.DISCONNECT_COMPLETE)) {
				int status = intent
						.getIntExtra("status", BluetoothGatt.FAILURE);
				String service = intent.getStringExtra("uuid");
				if (status == BluetoothGatt.SUCCESS) {
					leState = DISCONNECTED;
					device = null;
				} else if (status != BluetoothGatt.SUCCESS) {
					leState = DISCONNECTED;
					device = null;
				}
				
				mUIUpdateHandler.sendEmptyMessage(0);

			}

			else if ((BluetoothGatt.GET_COMPLETE).equals(action)) {
				int status = intent
						.getIntExtra("status", BluetoothGatt.FAILURE);
				String service = intent.getStringExtra("uuid");// Todo
				int length = intent.getIntExtra("length", 0);
				if (status == BluetoothGatt.SUCCESS) {
					if ((length >= 0) && service.equalsIgnoreCase(hrmUUID)) {
						Log.d(TAG, "broadcastReceiver.onReceive(): received "+length+" bytes of data");
						byte[] data = new byte[length];
						data = intent.getByteArrayExtra("data");
						sensorLocation = data[0];
						Log.v(TAG, "onReceive GET_COMPLETE data first byte "
								+ data[0]);

						Log.d(TAG, "broadcastReceiver.onReceive(): Sensor Location returned by GET_COMPLETE: "
										+ getStringSensorLocation(sensorLocation));
						mUIUpdateHandler.sendEmptyMessage(0);
					}
				} else {
					Toast.makeText(context, "Sensor query failed! ",
							Toast.LENGTH_LONG).show();
				}
			} else if (action.equals(BluetoothGatt.SET_COMPLETE)) {
				Log.d(TAG, "broadcastReceiver.onReceive(): SET COMPLETE received, action is: "
						+ action);
				Log.e(TAG, "SET COMPLETE received: " + action);

				int status = intent
						.getIntExtra("status", BluetoothGatt.FAILURE);
				String service = intent.getStringExtra("uuid");// Todo
				if (status == BluetoothGatt.SUCCESS
						&& service.equalsIgnoreCase(hrmUUID)) {
				} else if (status != BluetoothGatt.SUCCESS
						&& service.equalsIgnoreCase(hrmUUID)) {
					Toast.makeText(context, "Notification enabling failed! ",
							Toast.LENGTH_LONG).show();
				}
			}

		}

	};

	@Override
	@SuppressLint("ShowToast")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		
		// Set up the window layout
		setContentView(R.layout.main);

		Log.d(TAG, "onCrate(): checking for Bluetooth LE support...");
		bleSupported = Tools.isBLESupported();
		if (!bleSupported) {
			Log.d(TAG, "Bluetooth LE is not supported.");
			Toast.makeText(getApplicationContext(),
					"This device doesn't support GATT service.",
					Toast.LENGTH_SHORT);
			finish();
		} else {
			Log.d(TAG, "Requesting access to Bluetooth adapter...");
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bluetoothAdapter == null) {
				Log.d(TAG, "Bluetooth adapter is not available.");
				Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
				finish();
				return;
			}
			
			tvHeartRate = (TextView)findViewById(R.id.heart_rate);
			tvDevice = (TextView)findViewById(R.id.device);
			tvDeviceLocation = (TextView)findViewById(R.id.device_location);
			tvStatus = (TextView)findViewById(R.id.status);
			btConnect = (Button)findViewById(R.id.btn_connect);
			btDisconnect = (Button)findViewById(R.id.btn_disconnect);
			
			btDisconnect.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (!leDisconnected) {
						if (leState == CONNECTED) {
							if (device != null) {
								leState = DISCONNECTING;
								Log.i(TAG, "disconnecting LE");
								mUIUpdateHandler.sendEmptyMessage(0);
								try {
									hrmService.disconnectLe(device, hrmUUID);
								} catch (RemoteException e) {
									Log.e(TAG, "", e);
									leState = DISCONNECTED;
								}
							}
						}
						leDisconnected = true;
					}
					mUIUpdateHandler.sendEmptyMessage(0);
				}
			});
			
			btConnect.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.i(TAG, "btConnect.onClick()");
					showPairedDeviceSelectDialog();
				}
			});
			
			hrmUUID = HRM.toString();
			
			filter_scan = new IntentFilter(BluetoothGatt.CONNECT_COMPLETE);
			filter_scan.addAction(BluetoothGatt.DISCONNECT_COMPLETE);
			filter_scan.addAction(BluetoothGatt.GET_COMPLETE);
			filter_scan.addAction(BluetoothGatt.SET_COMPLETE);
			
			registerReceiver(broadcastReceiver, filter_scan);
			flag_leRcvrReg = true;
			
			Intent intent1 = new Intent(IBluetoothHrm.class.getName());
			getApplicationContext().bindService(intent1, mConnection,
					Context.BIND_AUTO_CREATE);
			
			Log.d(TAG, "Request IBluetoothHrm service binding...");

			Intent intent2 = new Intent(BluetoothGatt.ACTION_START_LE);
			intent2.putExtra(BluetoothGatt.EXTRA_PRIMARY_UUID, hrmUUID);
			sendBroadcast(intent2);

			callback1 = new callback(hrmUUID);
			
			heartRateDataQueue = new SynchronousQueue<HeartRateDataItem>(true);
			
			heartRateDataHandler = new HeartRateDataHandler(context, heartRateDataQueue);
			heartRateDataHandler.start();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
			case R.id.menu_exit: 
				finish();
				return true;
			case R.id.menu_account_settings:
				startActivity(new Intent(this, AccountSettingsActivity.class));
		}
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	    Log.i(TAG, "onStart()");

		if (!bluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		} else {
			// TODO: Set up BT HR Monitor
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        Log.i(TAG, "onResume()");

		// TODO: If BT HR Monitor is setup but not started, then start it.
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
	    Log.i(TAG, "onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy()");

		if (!leDisconnected) {
			if (leState == CONNECTED) {
				if (device != null) {
					leState = DISCONNECTING;
					Log.i(TAG, "disconnecting LE");
					try {
						hrmService.disconnectLe(device, hrmUUID);
					} catch (RemoteException e) {
						Log.e(TAG, "", e);
						leState = DISCONNECTED;
					}
				}
			}
			leDisconnected = true;
		}
		if (flag_leRcvrReg) {
			flag_leRcvrReg = false;
			unregisterReceiver(broadcastReceiver);
		}

		if (hrmService != null) {
			Log.i(TAG, "unbinding service");
			getApplicationContext().unbindService(mConnection);
		}

		if (mConnection != null)
			mConnection = null;
		if (callback1 != null)
			callback1 = null;
		if (hrmService != null)
			hrmService = null;
	}
	
	public void requestLocation() {
		try {
			if(hrmService != null)
				hrmService.getLeData(device, hrmUUID, BluetoothGatt.OPERATION_READ_SENSOR_LOCATION);
		} catch (Exception e) {
			Log.e(TAG, "pull sensor location ", e);
		}
	}
	
	public static String getStringSensorLocation(int intLocation) {
		String strLocation = null;
		switch (intLocation) {
		case 0:
			strLocation = "Other";
			break;
		case 1:
			strLocation = "Chest";
			break;
		case 2:
			strLocation = "Wrist";
			break;
		case 3:
			strLocation = "Finger";
			break;
		case 4:
			strLocation = "Hand";
			break;
		case 5:
			strLocation = "Ear Lobe";
			break;
		case 6:
			strLocation = "Foot";
			break;
		case 100:
			strLocation = "No Skin Contact";
		default:
			strLocation = "Unknown";
			break;
		}
		Log.v(TAG, "getStringSensorLocation" + strLocation);
		return strLocation;
	}
	
	public static String getStatusAsText(int leState) {
		String strStatus = "N/A";
		switch (leState) {
		case INIT:
			strStatus = "Connection Status";
			break;
		case CONNECTED:
			strStatus = "Connected";
			break;
		case CONNECTING:
			strStatus = "Connecting";
			break;
		case DISCONNECTING:
			strStatus = "Disconnecting";
			break;
		case DISCONNECTED:
			strStatus = "Disconnected";
			break;
		default:
			strStatus = "N/A";
			break;
		}
		return strStatus;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Log.i(TAG, "onActivityResult() code: " + resultCode);

		switch (requestCode) {

		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				// TODO: Set up BT HR Monitor
			} else {
				Log.e(TAG, "onActivityResult(): BT not enabled");
				Toast.makeText(this, "Bluetooth is not enabled. Leaving..",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		}
	}
	
	private void parseData(int length, byte[] data) {
		Log.d(TAG, "parseData(): length=" + data.length);
		
		heartBeatsPerMinute = 0;
		if (data[1] != 0) {
			heartBeatsPerMinute = ((data[0] & 0xFF) << 8) + (data[1] & 0xFF);
		} else {
			heartBeatsPerMinute = data[0] & 0xFF;
		}

		// correct the raw data from LE call back that if>128, it becomes
		// negative
		heartBeatsPerMinute = (data[0] < 0) ? (128 + (128 + heartBeatsPerMinute))
				: (heartBeatsPerMinute);

		energyExpended |= data[2] & 0xFF;
		energyExpended |= ((data[3] & 0xFF) << 8);
		
		Log.i(TAG, "parseData(): energyExpended="+energyExpended);
		short[] rrIntervals = getRRIntervals(data);
		Log.i(TAG, "parseData(): RR intervals: "+Tools.convertToString(rrIntervals));
		saveHeartRateData(heartBeatsPerMinute, energyExpended, rrIntervals);
		mUIUpdateHandler.sendEmptyMessage(0);

	}
	
	private void saveHeartRateData(int heartBeatsPerSecond, int energyExpended, short[] rrIntervals) {
		try {
			for (short rr: rrIntervals) {
				heartRateDataQueue.put(new HeartRateDataItem(heartBeatsPerSecond, (int) (rr*(1.0/1024*1000))));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private short[] getRRIntervals(byte[] data) {
		if(data.length <= 4) {
			return new short[]{};
		}
		short[] result = new short[(data.length-4)/2];
		for(int i = 4; i < data.length; i+=2) {
			result[(i-4)/2] = (short)(((data[i+1] & 0xFF) << 8) + (data[i] & 0xFF));
		}
		return result;
	}
	
	private void updateUI() {
		if(!leDisconnected) {
			previous_heartBeatsPerMinute = heartBeatsPerMinute;
			tvHeartRate.setText(String.valueOf(heartBeatsPerMinute)+" bpm");
			tvDevice.setText(device != null ? device.getName() : "Not Connected");
			tvDeviceLocation.setText(getStringSensorLocation(sensorLocation));
			if(heartBeatsPerMinute > 120) {
				tvHeartRate.setTextColor(Color.RED);
			} else {
				tvHeartRate.setTextColor(Color.BLACK);
			}
		} else {
			tvHeartRate.setText(String.valueOf(0)+" bpm");
			tvDevice.setText("Not Connected");
			tvDeviceLocation.setText("Not Connected");
			tvHeartRate.setTextColor(Color.BLACK);
		}
		btConnect.setEnabled(leState != CONNECTED);
		btDisconnect.setEnabled(leState == CONNECTED);
		tvStatus.setText(getStatusAsText(leState));
		
		if (leState == CONNECTING) {
			btConnect.setEnabled(false);
			btDisconnect.setEnabled(true);
		}
		if(leState == DISCONNECTING) {
			btConnect.setEnabled(true);
			btDisconnect.setEnabled(false);
		}
	}
	
	private void showPairedDeviceSelectDialog() {

		OnItemClickListener mPairedListClickListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			    bluetoothAdapter.cancelDiscovery();

				String info = ((TextView) v).getText().toString();
				Log.d(TAG, "mPairedListClickListener.onItemClick(): the total length is " + info.length());
				String deviceAddress = info.substring(info.length() - 17);

				device = bluetoothAdapter.getRemoteDevice(deviceAddress);

				if (device != null && callback1 != null && hrmUUID != null) {
					((DialogInterface) alertDialog).cancel();
					try {
						int status = hrmService.connectLe(device, hrmUUID,
								 callback1);
						//int status = mHrmService.connectLe(device1,
						//		"0000180d00001000800000805f9b34fb", callback1);
						if (status == BluetoothGatt.SUCCESS) {
							Log.d(TAG, "mPairedListClickListener.onItemClick(): connectLe sent out succesfully.");
							leState = CONNECTING;
						} else {
							Log.d(TAG, "mPairedListClickListener.onItemClick(): connectLe sent out but failed.");
							leState = INIT;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						leState = DISCONNECTED;
						leDisconnected = true;
					}
					mUIUpdateHandler.sendEmptyMessage(0);
				}

			}
		};

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout_listview = inflater.inflate(R.layout.device_list,
				(ViewGroup) findViewById(R.id.root_device_list));

		alert_paired = new AlertDialog.Builder(this);
		alert_paired.setView(layout_listview);

		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.device_name);
		ListView pairedListView = (ListView) layout_listview
				.findViewById(R.id.paired_devices);
		mPairedDevicesArrayAdapter.clear();
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mPairedListClickListener);

		Set<BluetoothDevice> pairedDevices = bluetoothAdapter
				.getBondedDevices();

		if (pairedDevices.size() > 0) {
			boolean foundDevices = false;

			if (pairedDevices.size() > 0) {

				// foundDevices = false;
				for (BluetoothDevice device : pairedDevices) {
					for (String s : IMonitors.MonitorNamesPatternLE) {
						Pattern p_le = Pattern.compile(s);
						if (device.getName().matches(p_le.pattern())) {
							mPairedDevicesArrayAdapter.add(device.getName()
									+ "\n" + device.getAddress());
							foundDevices = true;
						}
					}
				}
			}

			if (foundDevices) {
				layout_listview.findViewById(R.id.title_paired_devices)
						.setVisibility(View.VISIBLE);
			} else {
				layout_listview.findViewById(R.id.title_paired_devices)
						.setVisibility(View.GONE);
				String noDevices = "None devices have been paired";
				mPairedDevicesArrayAdapter.add(noDevices);
			}
		} else {
			layout_listview.findViewById(R.id.title_paired_devices)
					.setVisibility(View.GONE);
			String noDevices ="None devices have been paired";
			mPairedDevicesArrayAdapter.add(noDevices);
		}

		alert_paired.setTitle("Select paired device for LE connect");
		alertDialog = alert_paired.show();
	}
	
	private class callback extends IBluetoothHrmCallback.Stub {
		private String service;

		public callback(String serv) {
			service = serv;
		}

		public void indicationLeCb(BluetoothDevice device, String service,
				int length, byte[] data) {
			Log.i(TAG, "indicationLeCb");
			parseData(length, data);
		}

		public void notificationLeCb(BluetoothDevice device, String service,
				int length, byte[] data) {
			Log.i(TAG, "notificationLeCb");
			parseData(length, data);
		}
	}

}
