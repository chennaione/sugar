package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.IntegerFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.listAll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class ListAllOrderByTests {

    @Test
    public void listAllOrderByEmptyTest() {
        final List<IntegerFieldExtendedModel> list = listAll(IntegerFieldExtendedModel.class, "id");
        assertEquals(0L, list.size());
    }

    @Test
    public void listAllOrderByIdTest() {
        for (int i = 1; i <= 100; i++) {
            save(new IntegerFieldExtendedModel(i));
        }

        List<IntegerFieldExtendedModel> models = listAll(IntegerFieldExtendedModel.class, "id");
        assertEquals(100L, models.size());

        Long id = models.get(0).getId();

        for (int i = 1; i < 100; i++) {
            assertTrue(id < models.get(i).getId());
        }
    }

    @Test
    public void listAllOrderByFieldTest() {
        for (int i = 1; i <= 100; i++) {
            save(new IntegerFieldExtendedModel(i));
        }

        List<IntegerFieldExtendedModel> models = listAll(IntegerFieldExtendedModel.class, "raw_integer");

        assertEquals(100L, models.size());

        int raw = models.get(0).getInt();

        for (int i = 1; i < 100; i++) {
            assertTrue(raw < models.get(i).getInt());
        }
    }
}
