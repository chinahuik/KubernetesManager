package com.chinahuik.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {
	public static Long parseCpuLong(String input) {
		if (input == null || input.trim().isEmpty()) {
			return 0L;
		}
		input = input.replaceAll(",", "");
		final Pattern pattern = Pattern.compile("[\\d]+");
		final Matcher matcher = pattern.matcher(input);
		Long          ret     = 0L;
		if (matcher.find()) {
			final String numString  = matcher.group(0);
			final String leftString = input.substring(numString.length());
			ret = Long.parseLong(numString);
			if (!leftString.equalsIgnoreCase("m")) {
				ret = ret * 1000;
			}
		}
		return ret;
	}

	public static Long parseLong(String input) {
		if (input == null || input.trim().isEmpty()) {
			return 0L;
		}
		input = input.replaceAll(",", "");
		final Pattern pattern = Pattern.compile("[\\d]+");
		final Matcher matcher = pattern.matcher(input);
		Long          ret     = 0L;
		if (matcher.find()) {
			final String numString  = matcher.group(0);
			final String leftString = input.substring(numString.length());
			ret = Long.parseLong(numString);
			if (leftString.equalsIgnoreCase("Mi")) {
				ret = ret * 1024 * 1024;
			} else if (leftString.equalsIgnoreCase("Ki")) {
				ret = ret * 1024;
			} else if (leftString.equalsIgnoreCase("Gi")) {
				ret = ret * 1024 * 1024 * 1024;
			}
		}
		return ret;
	}
}
