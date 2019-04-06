package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.StringFieldAnnotatedModel;
import com.orm.model.StringFieldAnnotatedNoIdModel;
import com.orm.model.StringFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.findById;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class MultipleSaveTests {
    private String testString = "Test String";
    private String anotherString = "Another test";

    @Test
    public void stringMultipleSaveOriginalExtendedTest() {
        StringFieldExtendedModel model = new StringFieldExtendedModel(testString);
        long id = save(model);
        StringFieldExtendedModel query = findById(StringFieldExtendedModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
        }

        model.setString(anotherString);

        assertEquals(id, save(model));
        assertNull(findById(StringFieldExtendedModel.class, 2));
    }

    @Test
    public void stringMultipleSaveQueriedExtendedTest() {
        StringFieldExtendedModel model = new StringFieldExtendedModel(testString);
        long id = save(model);
        StringFieldExtendedModel query = findById(StringFieldExtendedModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
            query.setString(anotherString);
            assertEquals(id, save(query));
            assertNull(findById(StringFieldExtendedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedTest() {
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel(testString);
        long id = save(model);
        StringFieldAnnotatedModel query = findById(StringFieldAnnotatedModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
            model.setString(anotherString);
            assertEquals(id, save(model));
            assertNull(findById(StringFieldAnnotatedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedTest() {
        StringFieldAnnotatedModel model = new StringFieldAnnotatedModel(testString);
        long id = save(model);
        StringFieldAnnotatedModel query = findById(StringFieldAnnotatedModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
            query.setString(anotherString);
            assertEquals(id, save(query));
            assertNull(findById(StringFieldAnnotatedModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveOriginalAnnotatedNoIdTest() {
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel(testString);
        long id = save(model);
        StringFieldAnnotatedNoIdModel query = findById(StringFieldAnnotatedNoIdModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
            model.setString(anotherString);
            assertEquals(id, save(model));
            assertNull(findById(StringFieldAnnotatedNoIdModel.class, 2));
        }
    }

    @Test
    public void stringMultipleSaveQueriedAnnotatedNoIdTest() {
        StringFieldAnnotatedNoIdModel model = new StringFieldAnnotatedNoIdModel(testString);
        long id = save(model);
        StringFieldAnnotatedNoIdModel query = findById(StringFieldAnnotatedNoIdModel.class, id);

        if (null != query) {
            assertEquals(testString, query.getString());
            query.setString(anotherString);
            assertEquals(id, save(query));
            assertNull(findById(StringFieldAnnotatedNoIdModel.class, 2));
        }
    }
}
