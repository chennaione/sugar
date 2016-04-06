package com.example.sugartest;


import com.example.models.PrimaryKeyNotationBooleanFieldAnnotatedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;


import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith (RobolectricGradleTestRunner.class)
@Config (sdk = 18)
public class PrimaryKeyNotationBooleanFieldTests{

    @Test
    public void primaryKeyNullBooleanExtendedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel());
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void primaryKeynullRawBooleanExtendedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel());
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(false, model.getRawBoolean());
    }

    @Test
    public void primaryKeyNotationNullBooleanAnnotatedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel());
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertNull(model.getBoolean());
    }

    @Test
    public void primaryKeyNotationNullRawBooleanAnnotatedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel());
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(false, model.getRawBoolean());
    }

    @Test
    public void primaryKeyNotationObjectBooleanExtendedTest(){
        Boolean objectBoolean = new Boolean(true);
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel(objectBoolean));
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(objectBoolean, model.getBoolean());
    }

    @Test
    public void primaryKeyNotationRawBooleanExtendedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel(true));
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(true, model.getRawBoolean());
    }

    @Test
    public void primaryKeyNotationObjectBooleanAnnotatedTest(){
        Boolean objectBoolean = new Boolean(true);
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel(objectBoolean));
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(objectBoolean, model.getBoolean());
    }

    @Test
    public void primaryKeyNotationRawBooleanAnnotatedTest(){
        save(new PrimaryKeyNotationBooleanFieldAnnotatedModel(true));
        PrimaryKeyNotationBooleanFieldAnnotatedModel model = SugarRecord.findById(PrimaryKeyNotationBooleanFieldAnnotatedModel.class, 1);
        assertEquals(true, model.getRawBoolean());
    }
}
