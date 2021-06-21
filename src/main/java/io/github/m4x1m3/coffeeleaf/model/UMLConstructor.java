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

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import io.github.m4x1m3.coffeeleaf.utils.ReflectUtil;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLConstructor {

	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	public UMLClass getClazz() {
		return clazz;
	}

	public List<UMLParameter> getParams() {
		return new ArrayList<UMLParameter>(params);
	}

	private UMLAccessLevel accessLevel;
	private UMLClass clazz;
	private List<UMLParameter> params;

	public UMLConstructor(Constructor<?> c, UMLClass clazz) {
		this(ReflectUtil.getAccessLevel(c), clazz);

		for (Parameter p : c.getParameters()) {
			this.addParam(new UMLParameter(p, this));
		}
	}

	private void addParam(UMLParameter umlParameter) {
		this.params.add(umlParameter);
	}

	public UMLConstructor(UMLAccessLevel accessLevel, UMLClass clazz) {
		this.accessLevel = accessLevel;
		this.clazz = clazz;
		this.params = new ArrayList<UMLParameter>();
	}
}
