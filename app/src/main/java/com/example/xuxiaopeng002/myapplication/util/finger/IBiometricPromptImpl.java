package com.example.xuxiaopeng002.myapplication.util.finger;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;

public interface IBiometricPromptImpl {
    void authenticate(@NonNull CancellationSignal cancel);
}
