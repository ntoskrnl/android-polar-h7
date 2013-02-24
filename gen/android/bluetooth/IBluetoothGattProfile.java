/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\danon\\workspace\\PolarH7forMotorola\\src\\android\\bluetooth\\IBluetoothGattProfile.aidl
 */
package android.bluetooth;
/**
 * System private API for Bluetooth GATT service
 *
 * {@hide}
 */
public interface IBluetoothGattProfile extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.bluetooth.IBluetoothGattProfile
{
private static final java.lang.String DESCRIPTOR = "android.bluetooth.IBluetoothGattProfile";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.bluetooth.IBluetoothGattProfile interface,
 * generating a proxy if needed.
 */
public static android.bluetooth.IBluetoothGattProfile asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.bluetooth.IBluetoothGattProfile))) {
return ((android.bluetooth.IBluetoothGattProfile)iin);
}
return new android.bluetooth.IBluetoothGattProfile.Stub.Proxy(obj);
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
case TRANSACTION_onDiscoverCharacteristicsResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onDiscoverCharacteristicsResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSetCharacteristicValueResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onSetCharacteristicValueResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSetCharacteristicCliConfResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onSetCharacteristicCliConfResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onUpdateCharacteristicValueResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onUpdateCharacteristicValueResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onValueChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onValueChanged(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.bluetooth.IBluetoothGattProfile
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
@Override public void onDiscoverCharacteristicsResult(java.lang.String path, boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onDiscoverCharacteristicsResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSetCharacteristicValueResult(java.lang.String path, boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onSetCharacteristicValueResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSetCharacteristicCliConfResult(java.lang.String path, boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onSetCharacteristicCliConfResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onUpdateCharacteristicValueResult(java.lang.String path, boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onUpdateCharacteristicValueResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onValueChanged(java.lang.String path, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_onValueChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onDiscoverCharacteristicsResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSetCharacteristicValueResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSetCharacteristicCliConfResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onUpdateCharacteristicValueResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onValueChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void onDiscoverCharacteristicsResult(java.lang.String path, boolean result) throws android.os.RemoteException;
public void onSetCharacteristicValueResult(java.lang.String path, boolean result) throws android.os.RemoteException;
public void onSetCharacteristicCliConfResult(java.lang.String path, boolean result) throws android.os.RemoteException;
public void onUpdateCharacteristicValueResult(java.lang.String path, boolean result) throws android.os.RemoteException;
public void onValueChanged(java.lang.String path, java.lang.String value) throws android.os.RemoteException;
}
