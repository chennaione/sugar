package com.orm.record;

import com.orm.SugarContext;
import com.orm.model.BigDecimalFieldAnnotatedModel;
import com.orm.model.BigDecimalFieldExtendedModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.math.BigDecimal;

import androidx.test.core.app.ApplicationProvider;

import static com.orm.SugarRecord.findById;
import static com.orm.SugarRecord.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith( RobolectricTestRunner.class )
public final class BigDecimalFieldTests
{
    private BigDecimal decimal = new BigDecimal( 1234.5678901234567890123456789 );

    @Before
    public void setUp( )
    {
        SugarContext.init( ApplicationProvider.getApplicationContext( ) );
    }

    @Test
    public void nullBigDecimalExtendedTest( )
    {
        save( new BigDecimalFieldExtendedModel( ) );
        BigDecimalFieldExtendedModel model = findById( BigDecimalFieldExtendedModel.class, 1 );
        assertNull( model.getBigDecimal( ) );
    }

    @Test
    public void nullBigDecimalAnnotatedTest( )
    {
        save( new BigDecimalFieldAnnotatedModel( ) );
        BigDecimalFieldAnnotatedModel model = findById( BigDecimalFieldAnnotatedModel.class, 1 );
        assertNull( model.getBigDecimal( ) );
    }

    @Test
    public void bigDecimalExtendedTest( )
    {
        save( new BigDecimalFieldExtendedModel( decimal ) );
        BigDecimalFieldExtendedModel model = findById( BigDecimalFieldExtendedModel.class, 1 );
        assertEquals( decimal, model.getBigDecimal( ) );
    }

    @Test
    public void bigDecimalAnnotatedTest( )
    {
        save( new BigDecimalFieldAnnotatedModel( decimal ) );
        BigDecimalFieldAnnotatedModel model = findById( BigDecimalFieldAnnotatedModel.class, 1 );
        assertEquals( decimal, model.getBigDecimal( ) );
    }

    @After
    public void after( )
    {
        decimal = null;
    }
}
