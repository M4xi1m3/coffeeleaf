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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import io.github.m4x1m3.coffeeleaf.utils.ReflectUtil;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLField {
	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	private String name;
	private Class<?> type;

	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public UMLClass getClazz() {
		return clazz;
	}

	private UMLAccessLevel accessLevel;
	private boolean isStatic;
	private boolean isFinal;
	private UMLClass clazz;
	
	public UMLField(Field f, UMLClass clazz) {
		this(f.getName(), f.getType(), ReflectUtil.getAccessLevel(f), (f.getModifiers() & Modifier.FINAL) != 0, (f.getModifiers() & Modifier.STATIC) != 0, clazz);
	}
	
	public UMLField(String name, Class<?> type, UMLAccessLevel accessLevel, boolean isFinal, boolean isStatic, UMLClass clazz) {
		this.name = name;
		this.type = type;
		this.accessLevel = accessLevel;
		this.isFinal = isFinal;
		this.isStatic = isStatic;
		this.clazz = clazz;
	}
}
