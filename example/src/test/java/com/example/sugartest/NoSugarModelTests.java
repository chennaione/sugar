package com.example.sugartest;

import com.example.models.NoSugarModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class NoSugarModelTests {
    @Test
    public void deleteTest() throws Exception {
        NoSugarModel model = new NoSugarModel();
        assertFalse(SugarRecord.delete(model));
    }

    @Test
    public void saveInTransactionTest() throws Exception {
        SugarRecord.saveInTx(new NoSugarModel(), new NoSugarModel());
        assertEquals(-1L, SugarRecord.count(NoSugarModel.class));
    }
}
