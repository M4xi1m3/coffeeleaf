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
package io.github.m4x1m3.coffeeleaf.model.pkg;

import java.util.ArrayDeque;
import java.util.HashSet;

import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLTemplateClass;

/**
 * Represents a Package in the UML
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLPackage {
	/**
	 * Name of the package
	 */
	protected String name;

	/**
	 * Parent package
	 */
	private UMLPackage parent;

	/**
	 * Packages in the package
	 */
	protected HashSet<UMLPackage> subPackages;

	/**
	 * Classes in the package
	 */
	protected HashSet<UMLClass> subClasses;

	/**
	 * Create a package
	 * 
	 * @param name Name of the package
	 */
	public UMLPackage(String name) {
		this.name = name;
		this.parent = null;
		this.subPackages = new HashSet<UMLPackage>();
		this.subClasses = new HashSet<UMLClass>();
	}

	/**
	 * Looks for package name and creates it if not found
	 * 
	 * @param name Name of the package to find
	 * @return Package
	 */
	public UMLPackage findOrCreatePackage(String name) {
		UMLPackage pkg = null;

		for (UMLPackage p : subPackages) {
			if (p.getName().equals(name)) {
				pkg = p;
				break;
			}
		}

		if (pkg == null) {
			pkg = new UMLPackage(name);
			this.addPackage(pkg);
		}

		return pkg;
	}

	/**
	 * Get the name of the package
	 * 
	 * @return Name of the package
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the full name of the package
	 * 
	 * @return Full name of the package
	 */
	public String getFullName() {
		ArrayDeque<String> names = new ArrayDeque<String>();

		UMLPackage current = this;
		while (!(current instanceof UMLRootPackage)) {
			names.push(current.getName());
			current = current.parent;
		}

		return String.join(".", names);
	}

	/**
	 * Get the parent of the package
	 * 
	 * @return The parent of the package
	 */
	public UMLPackage getParent() {
		return parent;
	}

	/**
	 * Get the sub packages
	 * 
	 * @return The sub packages
	 */
	public HashSet<UMLPackage> getSubPackages() {
		return subPackages;
	}

	/**
	 * Get the sub classes
	 * 
	 * @return The sub classes
	 */
	public HashSet<UMLClass> getSubClasses() {
		return subClasses;
	}

	/**
	 * Set the name of the package
	 * 
	 * @param name New name of the package
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Check if the package has sub packages
	 * 
	 * @return True if the package has sub packages, false otherwise
	 */
	public boolean hasPackages() {
		return !subPackages.isEmpty();
	}

	/**
	 * Check if the package has classes
	 * 
	 * @return True if the package has classes, false otherwise
	 */
	public boolean hasClasses() {
		return !subClasses.isEmpty();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + parent.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLPackage) {
			UMLPackage o = (UMLPackage) other;
			return o.name.equals(this.name) && o.parent.equals(this.parent);
		} else {
			return false;
		}
	}

	/**
	 * Set the parent package
	 * 
	 * @param pkg Parent to set
	 */
	public void setParent(UMLPackage pkg) {
		this.parent = pkg;
	}

	/**
	 * Add a sub package
	 * 
	 * @param pkg Package to add
	 */
	public void addPackage(UMLPackage pkg) {
		pkg.setParent(this);
		this.subPackages.add(pkg);
	}

	/**
	 * Add a class
	 * 
	 * @param umlClass Class to add
	 */
	public void addClass(UMLClass umlClass) {
		umlClass.setParent(this);
		this.getModel().addClass(umlClass);

		for (UMLClass c : this.subClasses) {
			if (c.getName().equals(umlClass.getName())) {
				if (c instanceof UMLTemplateClass) {
					this.subClasses.remove(c);
					break;
				}
			}
		}

		this.subClasses.add(umlClass);
	}

	/**
	 * Get the root package
	 * 
	 * @return The root package
	 */
	public UMLRootPackage getRoot() {
		UMLPackage pkg = this;
		while (!(pkg instanceof UMLRootPackage) && pkg != null) {
			pkg = pkg.parent;
		}

		return (UMLRootPackage) pkg;
	}

	/**
	 * Get the model
	 * 
	 * @return The model
	 */
	public UMLModel getModel() {
		UMLRootPackage root = this.getRoot();
		if (root != null) {
			return root.getModel();
		}
		return null;
	}

}
