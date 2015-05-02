package com.iiitd.hostel;

import junit.framework.TestCase;

public class ListStudentsTest extends TestCase {

    public void testGetItems() throws Exception {
        ListStudents ls = new ListStudents();
        String result = ls.getItems();
        //System.out.print(result);
        assertTrue(ls.getItems().length()>50);
    }
}