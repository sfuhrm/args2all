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
package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import org.kohsuke.args4j.Option;

import java.util.Arrays;

/**
 * Mapping for {@linkplain Option}.
 * @author Stephan Fuhrmann
 */
final class Args4jOptionMapping extends Mapping<Option> {

    /** Creates a new instance. */
    Args4jOptionMapping() {
        super(Option.class);
    }

    @Override
    public ModelParameter createFrom(final ModelParameter modelParameter,
                                     final Option parameter) {
        modelParameter.getNames().add(parameter.name());
        if (parameter.aliases() != null) {
            modelParameter.getNames().addAll(
                    Arrays.asList(parameter.aliases()));
        }
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        modelParameter.setHidden(parameter.hidden());
        modelParameter.setHelp(parameter.help());
        return modelParameter;
    }
}
