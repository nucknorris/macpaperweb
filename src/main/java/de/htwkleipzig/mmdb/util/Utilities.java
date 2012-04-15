package de.htwkleipzig.mmdb.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Utilities {

	public static final List<File> allFiles() {
		File file = new File("/tmp/uploads");
		
		return Arrays.asList(file.listFiles());
	}
}
