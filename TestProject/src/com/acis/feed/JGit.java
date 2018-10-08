package com.acis.feed;

import java.io.IOException;
import java.net.URISyntaxException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class JGit {

	private static final String localRepository = "C:/Users/msrikan7/git/TestUpload/.git";
	private static final String remoteURL = "https://github.com/Srikanth346/TestUpload.git";
	private static Git jgit = null;

	/**
	 * Function Name : initGit() Description : This function is used to
	 * initialize Local Git Repository
	 **/
	private static Git initGit() {
		try {
			jgit = Git.open(new java.io.File(localRepository));
		} catch (IOException ioEx) {
			System.out.println("Local Repository Not Found in the Path: " + localRepository);
		}
		return jgit;
	}

	/**
	 * Function Name : addRemote Description : This function is used to add a
	 * remote Repository to Local Git
	 **/
	private static void addRemote(Git git) {
		try {
			// Add Remote
			RemoteAddCommand remoteadd = git.remoteAdd();
			remoteadd.setName("origin");
			remoteadd.setUri(new URIish(remoteURL));
			remoteadd.call();
		} catch (Exception exception) {
			if (exception instanceof URISyntaxException) {
				System.out.println("Invalid URL " + remoteURL);
			}
			if (exception instanceof GitAPIException) {
				System.out.println("Invalid Git Call");
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Function Name : pushChangesToGit Description : This function is used to
	 * update Changes to Online Git Repository
	 **/
	public static void pushChangesToGit() {
		Git git = null;
		try {
			git = initGit();
			// Commit Changes
			java.util.Set<String> uncommited = git.status().call().getUncommittedChanges();
			for (String uncomit : uncommited) {
				System.out.println(uncomit);
			}
			// Add File Pattern
			git.add().addFilepattern(".").call();
			git.commit().setMessage("uploaded AsoClientPricing feed").call();
			// Add Remote
			addRemote(git);
			// Push Changes to Online Repository
			PushCommand push = git.push();
			// push.setRemote("https://github.com/Srikanth346/TestUpload.git");
			push.setCredentialsProvider(
					new UsernamePasswordCredentialsProvider("srikanth.m5593@gmail.com", "Srik@nth.m5593"));
			push.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function Name : pullChangesFromGit Description : This function is used to
	 * get Update's from Git before pushing any Change's to Git Repository
	 **/
	public static boolean pullChangesFromGit() {
		Git git = null;
		try {
			git = initGit();
			// Add Remote
			addRemote(git);
			// pull from Remote Origin
			PullCommand pull = git.pull();
			pull.call();
		} catch (Exception ex) {
			if (ex instanceof IOException) {
				System.out.println("Local Repository Not Found in the Path: " + localRepository);
			}
			if (ex instanceof GitAPIException) {
				System.out.println("Invalid Git Call");
			} else
				ex.printStackTrace();
		}
		return true;
	}
}