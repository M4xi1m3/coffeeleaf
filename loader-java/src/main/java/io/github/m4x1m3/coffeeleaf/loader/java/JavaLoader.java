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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import io.github.m4x1m3.coffeeleaf.loader.ILoader;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;

/**
 * Load from Java sourcecode
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class JavaLoader implements ILoader {

	private UMLAccessLevel getAccessLevel(AccessSpecifier a) {
		switch (a) {
		case PACKAGE_PRIVATE:
			return UMLAccessLevel.PACKAGE;
		case PRIVATE:
			return UMLAccessLevel.PRIVATE;
		case PROTECTED:
			return UMLAccessLevel.PROTECTED;
		default:
			return UMLAccessLevel.PUBLIC;
		}
	}

	public UMLClassType getClassType(ClassOrInterfaceDeclaration d) {
		if (d.isAnnotationDeclaration())
			return UMLClassType.ANNOTATION;
		if (d.isInterface())
			return UMLClassType.INTERFACE;
		if (d.isAbstract())
			return UMLClassType.ABSTRACT;
		if (d.isEnumDeclaration())
			return UMLClassType.ENUM;
		return UMLClassType.CLASS;
	}

	@Override
	public Set<UMLModel> load() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();

		UMLModel m = new UMLModel("defaut");

		try {
			for (Path p : Files.walk(Paths.get("../../../eclipse/Fourmis")).filter(p -> p.toString().endsWith("java"))
					.filter(Files::isRegularFile).toList()) {

				try {
					// Load the java file and parse it
					CompilationUnit unit = null;
					System.err.println("[SCANNING] " + p.toString());

					try {
						unit = StaticJavaParser.parse(p);
					} catch (ParseProblemException e2) {
						System.err.println("[WARNING] Error occured: " + e2.getMessage().split("\n")[0]);
						continue;
					}

					// Get the package name
					String packageName = "";

					if (unit.getPackageDeclaration().isPresent())
						packageName = unit.getPackageDeclaration().get().getNameAsString();

					UMLPackage pkg = m.findPackageOrCreate(packageName);

					// Get the primary type and scan it
					unit.getPrimaryType().ifPresent(t -> {
						if (t.isClassOrInterfaceDeclaration()) {
							ClassOrInterfaceDeclaration d = t.asClassOrInterfaceDeclaration();
							UMLAccessLevel level = getAccessLevel(t.getAccessSpecifier());
							UMLClassType type = getClassType(d);
							boolean isFinal = d.isFinal();
							UMLClass c = new UMLClass(t.getNameAsString(), level, type, isFinal);
							pkg.addClass(c);
						}
					});

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashSet<UMLModel> models = new HashSet<UMLModel>();

		models.add(m);

		return models;
	}

}
