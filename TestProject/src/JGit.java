
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class JGit {

	public static void main(String[] args) {
		Git git = null;
		try {

			git = Git.open(new java.io.File("C:/Users/msrikan7/git/TestUpload/.git"));

			// Commit Changes
			java.util.Set<String> uncommited = git.status().call().getUncommittedChanges();
			for (String uncomit : uncommited) {
				System.out.println(uncomit);
			}
			git.add().addFilepattern("/DownStreamFeed/src/com/acis/downstreemfeed/JGit.java").call();
			git.commit().setMessage("Added Commit Tags").call();

			// Add Remote 
			RemoteAddCommand remoteadd = git.remoteAdd();
			remoteadd.setName("origin");
			remoteadd.setUri(new URIish("https://github.com/Srikanth346/TestUpload.git"));
			remoteadd.call();

			/*// Push Changes to Online Repository 
			PushCommand push = git.push();
			push.setRemote("https://github.com/Srikanth346/TestUpload.git");
			push.setCredentialsProvider(
					new UsernamePasswordCredentialsProvider("srikanth.m5593@gmail.com", "Srik@nth.m5593"));
			push.call();*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * Git remoteGit = Git.cloneRepository().setURI(
 * "https://github.com/Srikanth346/TestProject.git") .setCredentialsProvider(
 * new UsernamePasswordCredentialsProvider("srikanth.m5593@gmail.com",
 * "Srik@nth.m5593")) .setDirectory(new
 * java.io.File("/DownstreemFeed/src/git/bash1")).call();
 * 
 * remoteGit.add().addFilepattern("Config.java").call();
 * remoteGit.commit().setMessage("Initial Commit"); PushCommand pushCommand =
 * remoteGit.push() .setCredentialsProvider( new
 * UsernamePasswordCredentialsProvider("srikanth.m5593@gmail.com",
 * "Srik@nth.m5593")); pushCommand.call();
 */
