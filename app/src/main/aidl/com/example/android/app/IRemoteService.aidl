// IRemoteService.aidl
package com.example.android.app;

// Declare any non-default types here with import statements

import com.example.android.app.IRemoteServiceCallback;

interface IRemoteService {

    void registerCallback(IRemoteServiceCallback cb);
    void unregisterCallback(IRemoteServiceCallback cb);
}
