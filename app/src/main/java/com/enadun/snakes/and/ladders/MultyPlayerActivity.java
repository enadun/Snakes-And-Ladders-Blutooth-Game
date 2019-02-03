package com.enadun.snakes.and.ladders;

import java.util.Random;

import com.enadun.snakes.and.ladders.util.MyAnimations;
import com.enadun.snakes.and.ladders.util.MyBluetoothService;
import com.enadun.snakes.and.ladders.util.MyString;
import com.enadun.snakes.and.ladders.util.MyViewManager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.Toast;

public class MultyPlayerActivity extends GameActivity implements
AnimationListener {

	// Message types sent from the MyBluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the MyBluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the bluetooth services
	private MyBluetoothService mBluetoothService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialization();
	}

	private void initialization() {
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		rollButton.setVisibility(Button.GONE);
		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.bt_not_available, Toast.LENGTH_LONG)
			.show();
			activity.finish();
			return;
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		// If BT is not on, request that it be enabled.
		// setupGame() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the game session
		} else {
			if (mBluetoothService == null) {
				setupGame();
			}
		}
	}

	private void setupGame() {

		// Initialize the MyBluetoothService to perform bluetooth connections
		mBluetoothService = new MyBluetoothService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	// The Handler that gets information back from the MyBluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case MyBluetoothService.STATE_CONNECTED:
					mTitle.setText(MyString.getStringById(
							R.string.title_connected_to, mConnectedDeviceName));
					player1.setText(MyString.getStringById(
							R.string.game_dash_board_multy_player_one_name,
							mBluetoothAdapter.getName()));
					player2.setText(mConnectedDeviceName);
					break;
				case MyBluetoothService.STATE_CONNECTING:
					mTitle.setText(MyString
							.getStringById(R.string.title_connecting));
					break;
				case MyBluetoothService.STATE_LISTEN:
				case MyBluetoothService.STATE_NONE:
					mTitle.setText(MyString
							.getStringById(R.string.title_not_connected));
					break;
				}
				break;
			case MESSAGE_WRITE:
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				if(readBuf.length > 0){
					String readMessage = new String(readBuf, 0, msg.arg1);
					int temp = Integer.parseInt(readMessage.split(":")[1]);
					myTurn = true;
					currentValue = temp;
					moveRedPiece();
				}
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						MyString.getStringById(R.string.title_connected_to, mConnectedDeviceName),
						Toast.LENGTH_SHORT).show();
				rollButton.setVisibility(Button.VISIBLE);
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				if (msg.getData()
						.getString(TOAST)
						.equals(MyString
								.getStringById(R.string.device_connection_lost))) {
					gameBoard.resetgame(rollButton);
					rollButton.setVisibility(Button.GONE);
					player1.setText(MyString
							.getStringById(R.string.game_dash_board_player_one_name));
					player2.setText(MyString
							.getStringById(R.string.game_dash_board_player_two_name));
				}
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mBluetoothService.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a game session
				setupGame();
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(
						this,
						MyString.getStringById(R.string.bt_not_enabled_leaving,
								MyString.getStringById(R.string.app_name)),
								Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectionRequest() {
		Intent serverIntent = new Intent(this, DeviceListActivity.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	public void connectDevice(View v) {
		if(checkAdapter()){
			connectionRequest();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu_multy_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(checkAdapter()){
			switch (item.getItemId()) {
			case R.id.scan:
				// Launch the DeviceListActivity to see devices and do scan
				connectionRequest();
				return true;
			case R.id.discoverable:
				// Ensure this device is discoverable by others
				ensureDiscoverable();
				return true;
			}
		}
		return false;
	}

	private boolean checkAdapter() {
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			return false;
		}
		return true;
	}

	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mBluetoothService.getState() != MyBluetoothService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
			.show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the MyBluetoothService to write
			byte[] send = message.getBytes();
			mBluetoothService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			// mOutEditText.setText(mOutStringBuffer);
		}
	}

	public void rollTheDice(View v) {
		super.rollTheDice(v);
		myTurn = false;
		currentValue = new Random().nextInt(6) + 1;
		moveBluePiece();
		sendMessage(1 + ":" + currentValue);
	}

	@Override
	public void onBackPressed() {
		MyViewManager.showExitAlertDialog();
		// super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		if (mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.disable();
		}
		super.onDestroy();
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		MyAnimations.finalCheck(gameBoard, currentPiece, currentValue,
				rollButton);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
	}

}
