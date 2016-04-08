package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.IntegerFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class ListAllOrderByTests {

    @Test
    public void listAllOrderByEmptyTest() {
        SugarContext.init(RuntimeEnvironment.application);
        assertEquals(0L, SugarRecord.listAll(IntegerFieldExtendedModel.class, "id").size());
    }

    @Test
    public void listAllOrderByIdTest() {
        SugarContext.init(RuntimeEnvironment.application);
        for(int i = 1; i <= 100; i++) {
            save(new IntegerFieldExtendedModel(i));
        }
        List<IntegerFieldExtendedModel> models =
                SugarRecord.listAll(IntegerFieldExtendedModel.class, "id");
        assertEquals(100L, models.size());
        Long id = models.get(0).getId();
        for(int i = 1; i < 100; i++) {
            assertTrue(id <models.get(i).getId());
        }
    }

    @Test
    public void listAllOrderByFieldTest() {
        SugarContext.init(RuntimeEnvironment.application);
        for(int i = 1; i <= 100; i++) {
            save(new IntegerFieldExtendedModel(i));
        }
        List<IntegerFieldExtendedModel> models =
                SugarRecord.listAll(IntegerFieldExtendedModel.class, "raw_integer");
        assertEquals(100L, models.size());
        int raw = models.get(0).getInt();
        for(int i = 1; i < 100; i++) {
            assertTrue(raw < models.get(i).getInt());
        }
    }
}
