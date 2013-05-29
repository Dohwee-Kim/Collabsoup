package edu.dartmouth.cscollabsoup;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

// All the global constants are put here
public abstract class Globals 
{

	// Debugging tag for the whole project
	public static final String TAG = "CollabSoup";
	
	
	//IMP:Format tablename_KEY_columname
	// Profile Table!!! 
	public static final String PROFILE_KEY_ROWID = "_id";
	public static final String PROFILE_KEY_FIRSTNAME = "fname";
	public static final String PROFILE_KEY_LASTNAME = "lname";
	public static final String PROFILE_KEY_EMAIL = "email";
	public static final String PROFILE_KEY_PASSWORD = "password";
	public static String USERNAME = "";
	public static  String PASSWORD = "";
	public static String FIRSTNAME = "";
	public static String LASTNAME = "";
	public static String PHP_FILEPATH = "";
	public static String SEND_BROADCAST = "off";
	public static final String SERVER_IP = "10.31.226.47:8080";
	public static String course1="";
	public static String course2="";
	public static String course3="";
	public static String course4="";

	public static  int JUST_PRESSED_DELETE = 0;
	
	// for holds chosen courses 
	
	public static int NUMBER_OF_COURSES = 0;
	static ArrayList<ArrayList<String>> courseInfo = new ArrayList<ArrayList<String>>();
	
//public static HashMap<string, string> courseHashMap = new HashMap<string, string="">();
//	public static boolean IS_COURSE1_ADDED = false;
//	public static String COURSE1_NAME = "";
//	public static String COURSE1_NUMBER="";
//	
//	public static boolean IS_COURSE2_ADDED = false;
//	public static String COURSE2_NAME = "";
//	public static String COURSE2_NUMBER="";
//	
//	public static boolean IS_COURSE3_ADDED = false;
//	public static String COURSE3_NAME = "";
//	public static String COURSE3_NUMBER="";
//	
//	public static boolean IS_COURSE4_ADDED = false;
//	public static String COURSE4_NAME = "";
//	public static String COURSE4_NUMBER="";
}


