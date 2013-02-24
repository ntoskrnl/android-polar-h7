/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\danon\\workspace\\PolarH7forMotorola\\src\\com\\motorola\\bluetoothle\\hrm\\IBluetoothHrm.aidl
 */
package com.motorola.bluetoothle.hrm;
/*
 * System private API for  HRM Service
 *
 */
public interface IBluetoothHrm extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.motorola.bluetoothle.hrm.IBluetoothHrm
{
private static final java.lang.String DESCRIPTOR = "com.motorola.bluetoothle.hrm.IBluetoothHrm";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.motorola.bluetoothle.hrm.IBluetoothHrm interface,
 * generating a proxy if needed.
 */
public static com.motorola.bluetoothle.hrm.IBluetoothHrm asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.motorola.bluetoothle.hrm.IBluetoothHrm))) {
return ((com.motorola.bluetoothle.hrm.IBluetoothHrm)iin);
}
return new com.motorola.bluetoothle.hrm.IBluetoothHrm.Stub.Proxy(obj);
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
case TRANSACTION_connectLe:
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
com.motorola.bluetoothle.hrm.IBluetoothHrmCallback _arg2;
_arg2 = com.motorola.bluetoothle.hrm.IBluetoothHrmCallback.Stub.asInterface(data.readStrongBinder());
int _result = this.connectLe(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disconnectLe:
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
int _result = this.disconnectLe(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getLeData:
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
int _result = this.getLeData(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setLeData:
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
int _arg4;
_arg4 = data.readInt();
int _result = this.setLeData(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.motorola.bluetoothle.hrm.IBluetoothHrm
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
@Override public int connectLe(android.bluetooth.BluetoothDevice device, java.lang.String service, com.motorola.bluetoothle.hrm.IBluetoothHrmCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
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
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_connectLe, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int disconnectLe(android.bluetooth.BluetoothDevice device, java.lang.String service) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
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
mRemote.transact(Stub.TRANSACTION_disconnectLe, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getLeData(android.bluetooth.BluetoothDevice device, java.lang.String uuid, int operationType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
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
_data.writeInt(operationType);
mRemote.transact(Stub.TRANSACTION_getLeData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setLeData(android.bluetooth.BluetoothDevice device, java.lang.String uuid, int operationType, byte[] data, int length) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
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
_data.writeInt(operationType);
_data.writeByteArray(data);
_data.writeInt(length);
mRemote.transact(Stub.TRANSACTION_setLeData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_connectLe = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_disconnectLe = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getLeData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setLeData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public int connectLe(android.bluetooth.BluetoothDevice device, java.lang.String service, com.motorola.bluetoothle.hrm.IBluetoothHrmCallback callback) throws android.os.RemoteException;
public int disconnectLe(android.bluetooth.BluetoothDevice device, java.lang.String service) throws android.os.RemoteException;
public int getLeData(android.bluetooth.BluetoothDevice device, java.lang.String uuid, int operationType) throws android.os.RemoteException;
public int setLeData(android.bluetooth.BluetoothDevice device, java.lang.String uuid, int operationType, byte[] data, int length) throws android.os.RemoteException;
}
