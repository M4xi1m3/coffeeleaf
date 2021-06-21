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
	private UMLMethod method;
	private UMLConstructor constructor;

	public UMLParameter(Parameter p, UMLMethod m) {
		this(p.getType(), p.getName(), p.isVarArgs(), m);
	}
	
	public UMLParameter(Class<?> type, String name, boolean variable, UMLMethod method) {
		this.type = type;
		this.name = name;
		this.variable = variable;
		this.method = method;
	}
	
	public UMLParameter(Class<?> type, String name, boolean variable, UMLConstructor constructor) {
		this.type = type;
		this.name = name;
		this.variable = variable;
		this.constructor = constructor;
	}
	
	public UMLParameter(Parameter p, UMLConstructor c) {
		this(p.getType(), p.getName(), p.isVarArgs(), c);
	}

	public UMLMethod getMethod() {
		return method;
	}
	
	public UMLConstructor getConstructor() {
		return constructor;
	}
}
