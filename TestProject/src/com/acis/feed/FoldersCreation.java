package com.acis.feed;

import java.util.EnumSet;

public class FoldersCreation {

	public enum Enum_Feeds {
		RXSolution("rxsolutions"), UP2S("alliance"), ECAP("ecap"), AUP("aup");
		private String folderName;

		private Enum_Feeds(String str_Value) {
			this.folderName = str_Value;
		}
		public String getFolderName() {
			return folderName;
		}
		@Override
		public String toString() {
			return folderName;
		}
	}

	private static String str_maindir = "C:/Users/msrikan7/git/TestUpload/TestProject/feeds/";

	/**
	 * Function Name : verifyFolderPath Description : This function is used to
	 * verify all folder presence in Specified Drive
	 **/
	public static boolean verifyFolderPath() {
		boolean checkflag = false;
		try {
			java.io.File file = new java.io.File(str_maindir);
			// Verify for Main Directory
			if (!file.isDirectory()) {
				// Create acisat7 Folder in
				// C:/Users/msrikan7/git/TestUpload/TestProject/
				new java.io.File(str_maindir).mkdirs();
			}
			// Verify for Folder in
			// "C:/Users/msrikan7/git/TestUpload/TestProject/";
			String folderPath = "";
			for (Enum_Feeds folder : EnumSet.allOf(Enum_Feeds.class)) {
				folderPath = str_maindir + folder.getFolderName();
				if (!new java.io.File(folderPath).isDirectory()) {
					System.out
							.println("Created New Directory " + folder.getFolderName() + "in the path: " + folderPath);
					// Create Folder if it doesn't Exist
					new java.io.File(folderPath).mkdirs();
				}
				if (!new java.io.File(folderPath + "/backup").isDirectory()) {
					new java.io.File(folderPath + "/backup").mkdir();
					System.out.println("Created a backup Folder in Path " + folderPath);
				}
			}
			checkflag = true;
		} catch (Exception exception) {
			System.out.println("Unable to create Folders");
		}
		return checkflag;
	}

	public static String getOutputFolderPath(Enum_Feeds feedType) {
		// Verify all Folders are Present
		verifyFolderPath();
		boolean verifyFolderPaths = verifyFolderPath();
		String filePath = str_maindir;
		if (verifyFolderPaths) {
			filePath = filePath + feedType.getFolderName() + "/";
		}
		return filePath;
	}
}
