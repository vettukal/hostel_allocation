package com.iiitd.hostel;

import junit.framework.TestCase;

public class StudentConnectorTest extends TestCase {

    public void testGetKms() throws Exception {
        assertEquals("Testing conversion to kilometers",14,StudentConnector.getKms(14325));
    }
}