package com.example.xuxiaopeng002.myapplication.util.finger;

public interface IonFingerCallback
{

	void onSucceed();

	void onFailed();

	void onHelp(String help);

	void onError(String error);

	void onCancel();
}
