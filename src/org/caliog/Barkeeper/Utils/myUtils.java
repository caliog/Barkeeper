package org.caliog.Barkeeper.Utils;

import org.bukkit.Material;

public class myUtils {
    public static int[] addElementToArray(int[] array, int element) {
	if (array != null) {
	    int[] newarray = new int[array.length + 1];
	    for (int i = 0; i < array.length; i++) {
		newarray[i] = array[i];
	    }
	    newarray[array.length] = element;
	    return newarray;
	}
	int[] newarray = { element };
	return newarray;
    }

    public static boolean isNotNegativeInteger(String string) {
	if ((isInteger(string)) && (Integer.parseInt(string) >= 0)) {
	    return true;
	}
	return false;
    }

    public static boolean isPositiveInteger(String string) {
	if ((isInteger(string)) && (Integer.parseInt(string) > 0)) {
	    return true;
	}
	return false;
    }

    public static boolean isInteger(String string) {
	if (string == null) {
	    return false;
	}
	try {
	    Integer.parseInt(string);
	} catch (NumberFormatException nfe) {
	    return false;
	}
	return true;
    }

    public static String[] removeNull(String[] a) {
	int counter = 0;
	for (int i = 0; i < a.length; i++) {
	    if (a[i] == null) {
		counter++;
	    }
	}
	String[] b = new String[a.length - counter];
	int j = 0;
	for (int i = 0; i < a.length; i++) {
	    if (a[i] != null) {
		b[j] = a[i];
		j++;
	    }
	}
	return b;
    }

    public static String readable(Material mat) {
	String[] split = mat.name().split("_");
	String name = "";
	for (String s : split) {
	    name = name + s.substring(0, 1) + s.substring(1).toLowerCase() + " ";
	}
	return name.trim();
    }
}
