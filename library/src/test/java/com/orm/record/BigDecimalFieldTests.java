package com.orm.record;

import com.orm.app.ClientApp;
import com.orm.dsl.BuildConfig;
import com.orm.model.BigDecimalFieldAnnotatedModel;
import com.orm.model.BigDecimalFieldExtendedModel;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.math.BigDecimal;

import static com.orm.SugarRecord.save;
import static com.orm.SugarRecord.findById;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, application = ClientApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public final class BigDecimalFieldTests {
    private BigDecimal decimal = new BigDecimal(1234.5678901234567890123456789);

    @Test
    public void nullBigDecimalExtendedTest() {
        save(new BigDecimalFieldExtendedModel());
        BigDecimalFieldExtendedModel model = findById(BigDecimalFieldExtendedModel.class, 1);
        assertNull(model.getBigDecimal());
    }

    @Test
    public void nullBigDecimalAnnotatedTest() {
        save(new BigDecimalFieldAnnotatedModel());
        BigDecimalFieldAnnotatedModel model = findById(BigDecimalFieldAnnotatedModel.class, 1);
        assertNull(model.getBigDecimal());
    }

    @Test
    public void bigDecimalExtendedTest() {
        save(new BigDecimalFieldExtendedModel(decimal));
        BigDecimalFieldExtendedModel model = findById(BigDecimalFieldExtendedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }

    @Test
    public void bigDecimalAnnotatedTest() {
        save(new BigDecimalFieldAnnotatedModel(decimal));
        BigDecimalFieldAnnotatedModel model = findById(BigDecimalFieldAnnotatedModel.class, 1);
        assertEquals(decimal, model.getBigDecimal());
    }

    @After
    public void after() {
        decimal = null;
    }
}
