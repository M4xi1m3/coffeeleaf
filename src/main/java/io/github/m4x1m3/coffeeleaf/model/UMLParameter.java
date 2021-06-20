/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 *
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.model;

import java.lang.reflect.Parameter;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLParameter {
	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isVariable() {
		return variable;
	}

	private Class<?> type;
	private String name;
	private boolean variable;

	public UMLParameter(Parameter p) {
		this(p.getType(), p.getName(), p.isVarArgs());
	}
	
	public UMLParameter(Class<?> type, String name, boolean variable) {
		this.type = type;
		this.name = name;
		this.variable = variable;
	}
}
