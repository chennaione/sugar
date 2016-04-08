package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.StringFieldAnnotatedModel;
import com.orm.models.StringFieldAnnotatedNoIdModel;
import com.orm.models.StringFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class MultipleSaveTests {

    @Test
    public void stringMultipleSaveOriginalExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldExtendedModel model = new StringFieldExtendedModel(string);
        long id = save(model);
        StringFieldExtendedModel query = SugarRecord.findById(StringFieldExtendedModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
        }

        model.setString("Another test");
        assertEquals(id, save(model));
        assertNull(SugarRecord.findById(StringFieldExtendedModel.class, 2));
    }

    @Test
    public void stringMultipleSaveQueriedExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldExtendedModel model = new StringFieldExtendedModel(string);
        long id = save(model);
        StringFieldExtendedModel query = SugarRecord.findById(StringFieldExtendedModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
            query.setString("Another test");
            assertEquals(id, save(query));
            assertNull(SugarRecord.findById(StringFieldExtendedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel(string);
        long id = save(model);
        StringFieldAnnotatedModel query = SugarRecord.findById(StringFieldAnnotatedModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
            model.setString("Another test");
            assertEquals(id, save(model));
            assertNull(SugarRecord.findById(StringFieldAnnotatedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel(string);
        long id = save(model);
        StringFieldAnnotatedModel query = SugarRecord.findById(StringFieldAnnotatedModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
            query.setString("Another test");
            assertEquals(id, save(query));
            assertNull(SugarRecord.findById(StringFieldAnnotatedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedNoIdTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel(string);
        long id = save(model);
        StringFieldAnnotatedNoIdModel query =
                SugarRecord.findById(StringFieldAnnotatedNoIdModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
            model.setString("Another test");
            assertEquals(id, save(model));
            assertNull(SugarRecord.findById(StringFieldAnnotatedNoIdModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedNoIdTest() {
        SugarContext.init(RuntimeEnvironment.application);
        String string = "Test String";
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel(string);
        long id = save(model);
        StringFieldAnnotatedNoIdModel query =
                SugarRecord.findById(StringFieldAnnotatedNoIdModel.class, id);

        if (null != query) {
            assertEquals(string, query.getString());
            query.setString("Another test");
            assertEquals(id, save(query));
            assertNull(SugarRecord.findById(StringFieldAnnotatedNoIdModel.class, 2));
        }
    }
}
