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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import io.github.m4x1m3.coffeeleaf.utils.ReflectUtil;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLMethod {
	public String getName() {
		return name;
	}

	public List<UMLParameter> getParams() {
		return new ArrayList<UMLParameter>(params);
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	private String name;
	private List<UMLParameter> params;
	private Class<?> returnType;

	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	private UMLAccessLevel accessLevel;
	private boolean isAbstract;
	private boolean isStatic;
	private boolean isFinal;

	public UMLMethod(Method m) {
		this(m.getName(), m.getReturnType(), ReflectUtil.getAccessLevel(m), (m.getModifiers() & Modifier.ABSTRACT) != 0,
				(m.getModifiers() & Modifier.STATIC) != 0, (m.getModifiers() & Modifier.FINAL) != 0);
	}

	public UMLMethod(String name, Class<?> returnType, UMLAccessLevel accessLevel, boolean isAbstract, boolean isStatic,
			boolean isFinal) {
		this.name = name;
		this.returnType = returnType;
		this.params = new ArrayList<UMLParameter>();
		this.accessLevel = accessLevel;
		this.isAbstract = isAbstract;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
	}

	public void addParam(UMLParameter param) {
		this.params.add(param);
	}
}
