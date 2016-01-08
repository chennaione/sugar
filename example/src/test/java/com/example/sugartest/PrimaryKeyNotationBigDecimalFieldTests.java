package com.example.sugartest;


import com.example.models.PrimaryKeyNotationBigDecimalFieldAnnotatedModel;
import com.orm.SugarRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;


import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith (RobolectricGradleTestRunner.class)
@Config (sdk = 18)
public class PrimaryKeyNotationBigDecimalFieldTests{

    @Test
    public void primryKeyNotationNullBigDecimalExtendedTest(){
        save(new PrimaryKeyNotationBigDecimalFieldAnnotatedModel());
        PrimaryKeyNotationBigDecimalFieldAnnotatedModel model =
                SugarRecord.findById(PrimaryKeyNotationBigDecimalFieldAnnotatedModel.class, 1);
        assertNull(model.getBigDecimal());
    }

    @Test
    public void primryKeyNotationBigDecimalExtendedTest(){
        BigDecimal decimal = new BigDecimal(1234.5678901234567890123456789);
        save(new PrimaryKeyNotationBigDecimalFieldAnnotatedModel(decimal));
        PrimaryKeyNotationBigDecimalFieldAnnotatedModel model =
                SugarRecord.findById(PrimaryKeyNotationBigDecimalFieldAnnotatedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }

    @Test
    public void primryKeyNotationBigDecimalAnnotatedTest(){
        BigDecimal decimal = new BigDecimal(1234.5678901234567890123456789);
        save(new PrimaryKeyNotationBigDecimalFieldAnnotatedModel(decimal));
        PrimaryKeyNotationBigDecimalFieldAnnotatedModel model =
                SugarRecord.findById(PrimaryKeyNotationBigDecimalFieldAnnotatedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }
}