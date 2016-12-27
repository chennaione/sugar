package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.NoSugarModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.delete;
import static com.orm.SugarRecord.count;
import static com.orm.SugarRecord.saveInTx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class NoSugarModelTests {

    @Test
    public void deleteTest() throws Exception {
        NoSugarModel model = new NoSugarModel();
        assertFalse(delete(model));
    }

    @Test
    public void saveInTransactionTest() throws Exception {
        saveInTx(new NoSugarModel(), new NoSugarModel());
        assertEquals(-1L, count(NoSugarModel.class));
    }
}
