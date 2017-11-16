/*
 * Copyright (C) 2017 Stephan Fuhrmann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.sfuhrm.args2all.model;

import de.sfuhrm.args2all.Args4jExampleWithFields;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@linkplain ModelBase#COMPARATOR}.
 * @author Stephan Fuhrmann
 */
@Slf4j
public final class ModelBaseTest {

    @Test
    public void testComparatorWithNoChange() throws NoSuchFieldException {

        List<ModelParameter> unsorted = new ArrayList<>();
        ModelParameter first = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField"));
        first.setNames(Collections.singletonList("-mars"));
        first.setValueClass(String.class);

        unsorted.add(first);

        ModelParameter second = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField2"));
        second.setNames(Arrays.asList("-saturn", "--uranus", "--jupiter"));
        second.setValueClass(Integer.class);
        unsorted.add(first);

        List<ModelParameter> sortMe = new ArrayList<>(unsorted);
        sortMe.sort(ModelBase.COMPARATOR);

        // expecting no change, -mars is before -saturn in alphabet
        assertEquals(unsorted, sortMe);
    }

    @Test
    public void testComparatorWithChange() throws NoSuchFieldException {

        List<ModelParameter> unsorted = new ArrayList<>();
        ModelParameter first = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField"));
        first.setNames(Collections.singletonList("-zodiak"));
        first.setValueClass(String.class);

        unsorted.add(first);

        ModelParameter second = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField2"));
        second.setNames(Arrays.asList("-saturn", "-uranus", "-jupiter"));
        second.setValueClass(Integer.class);
        unsorted.add(second);

        List<ModelParameter> sortMe = new ArrayList<>(unsorted);
        sortMe.sort(ModelBase.COMPARATOR);

        List<ModelParameter> expected = Arrays.asList(second, first);
        // expecting first saturn, then zodiak
        assertEquals(expected, sortMe);
    }

    @Test
    public void testComparatorWithOrder() throws NoSuchFieldException {

        List<ModelParameter> unsorted = new ArrayList<>();
        ModelParameter first = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField"));
        first.setNames(Collections.singletonList("-zodiak"));
        first.setValueClass(String.class);
        first.setOrder(0);

        unsorted.add(first);

        ModelParameter second = new ModelParameter(Args4jExampleWithFields.class.getDeclaredField("myField2"));
        second.setNames(Arrays.asList("-saturn", "-uranus", "-jupiter"));
        second.setValueClass(Integer.class);
        second.setOrder(1);
        unsorted.add(second);

        List<ModelParameter> sortMe = new ArrayList<>(unsorted);
        sortMe.sort(ModelBase.COMPARATOR);

        List<ModelParameter> expected = Arrays.asList(first, second);
        // expecting first zodiak (by order), then saturn
        assertEquals(expected, sortMe);
    }
}
