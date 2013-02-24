/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\danon\\workspace\\PolarH7forMotorola\\src\\com\\motorola\\bluetooth\\bluetoothle\\IBluetoothGattCallback.aidl
 */
package com.motorola.bluetooth.bluetoothle;
/**
 * System private API for Bluetooth GATT Service 
 *
 *
 */
public interface IBluetoothGattCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback
{
private static final java.lang.String DESCRIPTOR = "com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback interface,
 * generating a proxy if needed.
 */
public static com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback))) {
return ((com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback)iin);
}
return new com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_indicationGattCb:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String[] _arg3;
_arg3 = data.createStringArray();
this.indicationGattCb(_arg0, _arg1, _arg2, _arg3);
return true;
}
case TRANSACTION_notificationGattCb:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
byte[] _arg3;
_arg3 = data.createByteArray();
this.notificationGattCb(_arg0, _arg1, _arg2, _arg3);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.motorola.bluetooth.bluetoothle.IBluetoothGattCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void indicationGattCb(android.bluetooth.BluetoothDevice device, java.lang.String uuid, java.lang.String char_handle, java.lang.String[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(uuid);
_data.writeString(char_handle);
_data.writeStringArray(data);
mRemote.transact(Stub.TRANSACTION_indicationGattCb, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void notificationGattCb(android.bluetooth.BluetoothDevice device, java.lang.String uuid, java.lang.String char_handle, byte[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeString(uuid);
_data.writeString(char_handle);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_notificationGattCb, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_indicationGattCb = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_notificationGattCb = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void indicationGattCb(android.bluetooth.BluetoothDevice device, java.lang.String uuid, java.lang.String char_handle, java.lang.String[] data) throws android.os.RemoteException;
public void notificationGattCb(android.bluetooth.BluetoothDevice device, java.lang.String uuid, java.lang.String char_handle, byte[] data) throws android.os.RemoteException;
}
