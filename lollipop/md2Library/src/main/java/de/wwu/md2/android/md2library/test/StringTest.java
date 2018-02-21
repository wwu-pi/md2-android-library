package de.wwu.md2.android.md2library.test;

import de.wwu.md2.android.md2library.model.type.implementation.Md2String;

/**
 * Created by Fabian on 12.07.2015.
 */
public class StringTest {

    /**
     * Create instance.
     */
    @org.junit.Test
    public void createInstance() {

        Md2String str = new Md2String("test");
        org.junit.Assert.assertNotNull(str);
        org.junit.Assert.assertEquals("Should be the same string", "test", str.getPlatformValue());
    }
}
