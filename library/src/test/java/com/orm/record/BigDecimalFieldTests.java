package com.orm.record;

import com.orm.ClientApp;
import com.orm.RobolectricGradleTestRunner;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.models.BigDecimalFieldAnnotatedModel;
import com.orm.models.BigDecimalFieldExtendedModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;

import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk=18, application = ClientApp.class)
public class BigDecimalFieldTests {

    @Test
    public void nullBigDecimalExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BigDecimalFieldExtendedModel());
        BigDecimalFieldExtendedModel model =
                SugarRecord.findById(BigDecimalFieldExtendedModel.class, 1);
        assertNull(model.getBigDecimal());
    }

    @Test
    public void nullBigDecimalAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        save(new BigDecimalFieldAnnotatedModel());
        BigDecimalFieldAnnotatedModel model =
                SugarRecord.findById(BigDecimalFieldAnnotatedModel.class, 1);
        assertNull(model.getBigDecimal());
    }

    @Test
    public void bigDecimalExtendedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        BigDecimal decimal = new BigDecimal(1234.5678901234567890123456789);
        save(new BigDecimalFieldExtendedModel(decimal));
        BigDecimalFieldExtendedModel model = SugarRecord.findById(BigDecimalFieldExtendedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }

    @Test
    public void bigDecimalAnnotatedTest() {
        SugarContext.init(RuntimeEnvironment.application);
        BigDecimal decimal = new BigDecimal(1234.5678901234567890123456789);
        save(new BigDecimalFieldAnnotatedModel(decimal));
        BigDecimalFieldAnnotatedModel model =
                SugarRecord.findById(BigDecimalFieldAnnotatedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }
}
