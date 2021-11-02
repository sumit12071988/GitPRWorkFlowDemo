package com.qa.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String ACCOUNTS_PAGE_TITLE="My Account";
	public static final String ACCOUNTS_PAGE_BREADCRUMB="Account";
	
	public static List<String> getAccountSectionsList() {
		List<String> list = new ArrayList<String>(Arrays.asList("My Account","My Orders","My Affiliate Account", "Newsletter"));
		return list;
	}
}
