package com.linegrillpresent.studybuddy;

import junit.framework.Assert;

import org.junit.Test;

import system.Utility;

/**
 * Created by REX on 2017/11/5.
 */

public class UtilityClassUnitTest {
    @Test
    public void utility_getinstance_test() throws Exception {
        Utility u = Utility.getInstance();
        Assert.assertEquals(u,Utility.getInstance());
    }

}
