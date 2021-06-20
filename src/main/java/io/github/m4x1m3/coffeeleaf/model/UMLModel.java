/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 * 
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.model;

import java.util.ArrayDeque;

/**
 * Represents a UML Model
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLModel {
	private String name;

	private UMLRootPackage rootpkg;

	public UMLModel(String name) {
		this.name = name;
		this.rootpkg = new UMLRootPackage();
	}

	public void addClass(Class<? extends Object> clazz) {
		String name = clazz.getCanonicalName();

		ArrayDeque<String> names = new ArrayDeque<String>();
		for (String s : name.split("\\.")) {
			names.add(s);
		}

		UMLPackage current = rootpkg;

		while (names.size() > 1) {
			current = current.findOrCreatePackage(names.pop());
		}

		new UMLClass(names.pop(), current);
	}

	public void debug() {
		System.out.println("[MDL] " + name);
		rootpkg.debug(0);
	}
}
