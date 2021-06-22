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
package io.github.m4x1m3.coffeeleaf.loader.java;

import java.util.HashSet;
import java.util.Set;

import io.github.m4x1m3.coffeeleaf.loader.ILoader;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLMethod;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.pri.Primitives;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelation;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationDirection;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationType;

/**
 * Load from Java sourcecode
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class JavaLoader implements ILoader {

	@Override
	public Set<UMLModel> load() {
		UMLModel model = new UMLModel("default");

		UMLPackage pkg = model.getRootPackage().findOrCreatePackage("fr").findOrCreatePackage("m4x1m3")
				.findOrCreatePackage("test");

		UMLClass main = new UMLClass("Main", UMLAccessLevel.PUBLIC, UMLClassType.CLASS, false);

		UMLMethod mainmeth = new UMLMethod("main", Primitives.VOID, UMLAccessLevel.PUBLIC, false, true, false);

		UMLClass test = new UMLClass("Test", UMLAccessLevel.PUBLIC, UMLClassType.CLASS, false);

		UMLRelation rel = new UMLRelation(main, test, UMLRelationType.USE, UMLRelationDirection.RIGHT);

		main.addMethod(mainmeth);

		pkg.addClass(main);
		pkg.addClass(test);
		model.addRelation(rel);

		HashSet<UMLModel> models = new HashSet<UMLModel>();
		models.add(model);
		return models;
	}

}
