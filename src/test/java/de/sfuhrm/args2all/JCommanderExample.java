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

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Example class for JCommander.
 * @author Stephan Fuhrmann
 * */
class JCommanderExample {
    @Getter @Setter
    @Parameter(names = "-mars", order = 1)
    private String myField;

    @Getter @Setter
    @Parameter(names = {"--saturn", "--uranus", "--jupiter"}, order = 2)
    private Integer myField2;

    // unnamed
    @Getter @Setter
    @Parameter(description = "The list of planets to visit", order = 3)
    private List<String> arguments;
}
