package com.mukesh.linkedin;

public class Config {

	public static String LINKEDIN_CONSUMER_KEY = "786uukggvqbvwh";
	public static String LINKEDIN_CONSUMER_SECRET = "8p4pv57NMh0V5Dsm";
	public static String scopeParams = "rw_nus+r_basicprofile";
	
	public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
	public static String OAUTH_CALLBACK_HOST = "callback";
	public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
