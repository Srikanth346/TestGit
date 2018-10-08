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

	private static Git initGit() {
		try {
			jgit = Git.open(new java.io.File(localRepository));
		} catch (IOException ioEx) {
			System.out.println("Local Repository Not Found in the Path: " + localRepository);
		}
		return jgit;
	}

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
			git.commit().setMessage("Added New Line").call();
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

	public static void main(String[] args) {
		boolean condition = JGit.pullChangesFromGit();
		System.out.println("Pull Request " + condition);
	}
}
