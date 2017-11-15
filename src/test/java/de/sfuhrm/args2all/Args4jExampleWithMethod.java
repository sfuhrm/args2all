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

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.List;

/**
 * Example class for args4j with setter method.
 * @author Stephan Fuhrmann
 * */
public class Args4jExampleWithMethod {
    private String myField;

    @Option(name = "-mars")
    public void setFoo(String bar) {
        this.myField = bar;
    }
}
