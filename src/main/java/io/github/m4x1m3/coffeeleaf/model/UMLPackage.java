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

import java.util.HashMap;

/**
 * Represents a Package iin the UML
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLPackage {
	private String name;
	private UMLPackage parent;

	protected HashMap<String, UMLPackage> subPackages;
	protected HashMap<String, UMLClass> subClasses;

	public UMLPackage(String name, UMLPackage parent) {
		this.name = name;
		this.parent = parent;
		this.subPackages = new HashMap<String, UMLPackage>();
		this.subClasses = new HashMap<String, UMLClass>();
	}

	public UMLPackage findOrCreatePackage(String name) {
		if (subPackages.containsKey(name)) {
			return subPackages.get(name);
		} else {
			UMLPackage pkg = new UMLPackage(name, this);
			subPackages.put(name, pkg);
			return pkg;
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		return hash;
	}

	public void addClass(UMLClass umlClass) {
		this.subClasses.put(umlClass.getName(), umlClass);
	}

	public void debug(int depth) {
		System.out.println(" ".repeat(depth * 4) + "[PKG] " + name);

		subPackages.forEach((n, p) -> {
			p.debug(depth + 1);
		});

		subClasses.forEach((n, c) -> {
			c.debug(depth + 1);
		});
	}
}
