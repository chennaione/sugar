package com.orm.query;

import android.content.Context;
import com.orm.SugarApp;
import com.orm.SugarRecord;

public class TestRecord extends SugarRecord<TestRecord>{

    private String name;

    public TestRecord(Context context) {
        super(new DummyContext(){
            @Override
            public Context getApplicationContext() {
                return null;
            }
        });
    }
}
