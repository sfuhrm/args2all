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
package de.sfuhrm.args2all;

import de.sfuhrm.args2all.model.ModelBase;
import de.sfuhrm.args2all.model.ModelParameter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for the {@linkplain ClassInspector}.
 * @author Stephan Fuhrmann
 * */
@Slf4j
public final class ClassInspectorTest {

    @Test
    public void testInspectWithArgs4j() throws NoSuchFieldException {
        ClassInspector classInspector = new ClassInspector();
        ModelBase modelBase = classInspector.inspect(Args4jExample.class);

        List<? super ModelParameter> expected = new ArrayList<>();
        ModelParameter modelParameter;
        modelParameter = new ModelParameter(Args4jExample.class.getDeclaredField("myField"));
        modelParameter.setNames(Arrays.asList("-mars"));
        modelParameter.setValueClass(String.class);
        expected.add(modelParameter);
        modelParameter = new ModelParameter(Args4jExample.class.getDeclaredField("myField2"));
        modelParameter.setNames(Arrays.asList("-saturn", "-uranus", "-jupiter"));
        modelParameter.setValueClass(Integer.class);
        expected.add(modelParameter);
        modelParameter = new ModelParameter(Args4jExample.class.getDeclaredField("arguments"));
        modelParameter.setDescription("The list of planets to visit");
        modelParameter.setValueClass(List.class);
        modelParameter.setOrder(0);
        expected.add(modelParameter);

        assertEquals(Args4jExample.class, modelBase.getReference());
        assertEquals(expected, modelBase.getParameters());
    }

    @Test
    public void testInspectWithJCommander() throws NoSuchFieldException {
        ClassInspector classInspector = new ClassInspector();
        ModelBase modelBase = classInspector.inspect(JCommanderExample.class);

        List<? super ModelParameter> expected = new ArrayList<>();
        ModelParameter modelParameter = new ModelParameter(JCommanderExample.class.getDeclaredField("myField"));
        modelParameter.setNames(Arrays.asList("-mars"));
        modelParameter.setValueClass(String.class);
        modelParameter.setOrder(1);
        expected.add(modelParameter);
        modelParameter = new ModelParameter(JCommanderExample.class.getDeclaredField("myField2"));
        modelParameter.setNames(Arrays.asList("--saturn", "--uranus", "--jupiter"));
        modelParameter.setValueClass(Integer.class);
        modelParameter.setOrder(2);
        expected.add(modelParameter);
        modelParameter = new ModelParameter(JCommanderExample.class.getDeclaredField("arguments"));
        modelParameter.setDescription("The list of planets to visit");
        modelParameter.setValueClass(List.class);
        modelParameter.setOrder(3);
        expected.add(modelParameter);

        assertEquals(JCommanderExample.class, modelBase.getReference());
        assertEquals(expected, modelBase.getParameters());
    }
}
