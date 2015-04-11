package com.example.sugartest;

import android.database.sqlite.SQLiteException;

import com.example.models.NoSugarModel;
import com.example.models.SimpleAnnotatedModel;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Shyish on 10/04/2015.
 */
@SuppressWarnings("ALL")
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class NoSugarModelTest {

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
