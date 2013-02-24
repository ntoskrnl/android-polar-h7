/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\danon\\workspace\\PolarH7forMotorola\\src\\com\\motorola\\bluetoothle\\hrm\\IBluetoothHrmCallback.aidl
 */
package com.motorola.bluetoothle.hrm;
/**
 * System private API for  HRM Service
 *
 *
 */
public interface IBluetoothHrmCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.motorola.bluetoothle.hrm.IBluetoothHrmCallback
{
private static final java.lang.String DESCRIPTOR = "com.motorola.bluetoothle.hrm.IBluetoothHrmCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.motorola.bluetoothle.hrm.IBluetoothHrmCallback interface,
 * generating a proxy if needed.
 */
public static com.motorola.bluetoothle.hrm.IBluetoothHrmCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.motorola.bluetoothle.hrm.IBluetoothHrmCallback))) {
return ((com.motorola.bluetoothle.hrm.IBluetoothHrmCallback)iin);
}
return new com.motorola.bluetoothle.hrm.IBluetoothHrmCallback.Stub.Proxy(obj);
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
case TRANSACTION_indicationLeCb:
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
int _arg2;
_arg2 = data.readInt();
byte[] _arg3;
_arg3 = data.createByteArray();
this.indicationLeCb(_arg0, _arg1, _arg2, _arg3);
return true;
}
case TRANSACTION_notificationLeCb:
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
int _arg2;
_arg2 = data.readInt();
byte[] _arg3;
_arg3 = data.createByteArray();
this.notificationLeCb(_arg0, _arg1, _arg2, _arg3);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.motorola.bluetoothle.hrm.IBluetoothHrmCallback
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
@Override public void indicationLeCb(android.bluetooth.BluetoothDevice device, java.lang.String service, int length, byte[] data) throws android.os.RemoteException
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
_data.writeString(service);
_data.writeInt(length);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_indicationLeCb, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void notificationLeCb(android.bluetooth.BluetoothDevice device, java.lang.String service, int length, byte[] data) throws android.os.RemoteException
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
_data.writeString(service);
_data.writeInt(length);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_notificationLeCb, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_indicationLeCb = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_notificationLeCb = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void indicationLeCb(android.bluetooth.BluetoothDevice device, java.lang.String service, int length, byte[] data) throws android.os.RemoteException;
public void notificationLeCb(android.bluetooth.BluetoothDevice device, java.lang.String service, int length, byte[] data) throws android.os.RemoteException;
}
