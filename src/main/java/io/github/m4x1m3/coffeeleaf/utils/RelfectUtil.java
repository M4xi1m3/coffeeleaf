package io.github.m4x1m3.coffeeleaf.utils;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import io.github.m4x1m3.coffeeleaf.annotations.GenUML;

public class RelfectUtil {
	private static Reflections reflections = new Reflections("", new SubTypesScanner(false), new TypeAnnotationsScanner());
	
	public static Set<Class<?>> getGenUMLClasses() {
		return reflections.getTypesAnnotatedWith(GenUML.class);
	}
}
