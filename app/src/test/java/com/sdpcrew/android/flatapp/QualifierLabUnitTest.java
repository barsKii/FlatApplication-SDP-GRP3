package com.sdpcrew.android.flatapp;

import com.sdpcrew.android.flatapp.TasksManager.Qualifier;
import com.sdpcrew.android.flatapp.TasksManager.QualifierLab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class QualifierLabUnitTest {
    QualifierLab lab;

    @Before
    public void setUp() {
        lab = QualifierLab.get(null);
    }

    @Test
    public void testAddQualifier() {
        Qualifier c = new Qualifier();
        Assert.assertTrue(lab.addQualifier(c));
    }

    @Test
    public void testRemoveQualifier() {
        Qualifier c = new Qualifier("test");
        lab.addQualifier(c);
        Assert.assertTrue(lab.removeQualifier(c));
    }

    @Test
    public void testGetQualifiersList() {
        Qualifier test1 = new Qualifier("test1");
        Qualifier test2 = new Qualifier("test2");
        Qualifier test3 = new Qualifier("test3");
        lab.addQualifier(test3);
        lab.addQualifier(test2);
        lab.addQualifier(test1);
        List<Qualifier> expResult = lab.getQualifiers();
        boolean exists1= expResult.contains(test1);
        boolean exists2= expResult.contains(test2);
        boolean exists3= expResult.contains(test3);
        boolean contains = false;
        if( exists1  && exists2 && exists3){
            contains = true;
        }
        Assert.assertTrue(contains);

    }

    @Test
    public void testGetQualifierByStringName() {
        Qualifier c = new Qualifier("testGet");
        lab.addQualifier(c);
        Qualifier result = lab.getQualifier("testGet");
        Assert.assertEquals(c, result);
    }
}
