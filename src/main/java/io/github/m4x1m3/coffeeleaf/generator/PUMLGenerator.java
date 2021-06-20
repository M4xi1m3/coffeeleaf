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
package io.github.m4x1m3.coffeeleaf.generator;

import java.io.PrintStream;

import io.github.m4x1m3.coffeeleaf.model.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.UMLRootPackage;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class PUMLGenerator {
	PrintStream out;

	public PUMLGenerator(PrintStream out) {
		this.out = out;
	}

	public void generate(UMLModel model) {
		UMLRootPackage root = model.getRootPackage();

		root.forEachPackage(p -> generatePackage(p));
		root.forEachClass(c -> generateClasses(c));
	}

	private void generatePackage(UMLPackage pkg) {
		if (pkg.hasClasses()) {
			out.println("package " + pkg.getFullName() + " {");
			pkg.forEachPackage(p -> generatePackage(p));
			pkg.forEachClass(c -> generateClasses(c));
			out.println("}");
		} else {
			pkg.forEachPackage(p -> generatePackage(p));
		}
	}

	private void generateClasses(UMLClass cls) {
		out.println("class " + cls.getName() + " {}");
	}
}
