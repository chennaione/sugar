package com.example.sugartest;

import com.example.ClientApp;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class RobolectricGradleTestRunner extends RobolectricTestRunner {
    public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override protected AndroidManifest getAppManifest(Config config) {
        String myAppPath = ClientApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String manifestPath = myAppPath + "../../../../src/main/AndroidManifest.xml";
        String resPath = myAppPath + "../../../../src/main/res";
        String assetPath = myAppPath + "../../../../src/main/assets";
        return createAppManifest(Fs.fileFromPath(manifestPath), Fs.fileFromPath(resPath), Fs.fileFromPath(assetPath));
    }
}