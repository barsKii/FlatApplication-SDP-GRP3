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
    public void testGetSingleton() {
        QualifierLab expResult = QualifierLab.sQualifierLab; // sQualifier is set public for test purpose
        QualifierLab result = QualifierLab.get(); // return the static class instance
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testAddQualifier() {
        Qualifier c = new Qualifier();
        lab.addQualifier(c);
        Assert.assertEquals(c,lab.getQualifier(c.getTitle()));
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
        List<Qualifier> expResult = lab.mQualifiers;
        List<Qualifier> result = lab.getQualifiers();
        Assert.assertEquals(expResult, result);

    }

    @Test
    public void testGetQualifierByStringName() {
        Qualifier c = new Qualifier("testGet");
        lab.addQualifier(c);
        Qualifier result = lab.getQualifier("testGet");
        Assert.assertEquals(c, result);
    }
}
