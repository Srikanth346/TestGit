package com.acis.feed;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReportGenerator {

	private static String fileName = "C:/Users/msrikan7/git/TestUpload/TestProject/TestReports/TestReport.html";
	private static FileWriter fileWriter = null;
	private static BufferedWriter bufferedWriter = null;
	@SuppressWarnings("unused")
	private static int pass = 0, fail = 0, warning = 0, steps = 1, info = 0;
	private static java.time.Instant start;
	private static java.time.Instant end;

	public enum LOG_STATUS {
		PASS("pass"), WARNING("warning"), INFO("info"), FAIL("fail");
		private String status;

		private LOG_STATUS(String str_Value) {
			this.status = str_Value;
		}

		public String getStatus() {
			return status;
		}
	}

	private static void initReport() throws IOException {
		// Start Time
		start = Instant.now();
		fileWriter = new FileWriter(fileName);
		bufferedWriter = new BufferedWriter(fileWriter);
		//Add HTML head 
		bufferedWriter
				.write("<!DOCTYPE html><html><head><title>HTML Test Report</title><script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>"
						+ "<script>$('a').click(function(){$('html,body').animate({scrollTop: $('#scrollDown').offset().top},'slow');});"
							+ "	</script><meta http-equiv='Content-Type' content='text/html;charset=utf-8'><style>div#footer { padding: 0.5px; color: white; background-color: #696969; margin-top: 15px;}"
								+ "table { border: 1px solid black; width: 100%; }table.headerTable { border: 0px solid white;width: 60%; }"
									+ "tr#dummy, td#dummy, th#dummy { font-size: 16.8px;font-weight: bold	; color:#2E8B57 }td { text-align: left; padding: 8px; max-height: 100px;}"
										+ "td#statuscells { text-align: center; padding: 8px; }th { text-align: center; padding: 8px; }tr:nth-child(even) { background-color: #f2f2f2 }"
											+ "th { background-color: #008B8B; color: white; } /* #3CB371 */</style></head>");
		//Add HTML body 
		bufferedWriter
				.write("<body><div style='margin-left: 20px;margin-right: 20px;'><h2 style='color:#FFA07A'> OPTUM<span style='font-size: 16px;'>&reg;</span> | ACIS</h2>"
							+ "<table class='headerTable' ><tr id='dummy'><td id='dummy'> Test Report Name: DownStream Feed </td><td id='dummy'>Date: "
								+ new SimpleDateFormat("dd MMM, yy").format(new Date())
									+ " </td></tr><tr id='dummy'><td id='dummy'>Host Address: "
										+ InetAddress.getLocalHost().getHostName() + " </td><td id='dummy'>Operating System: "
											+ System.getProperty("os.name") + "</td></tr><tr id='dummy'><td id='dummy'>User Name: "
												+ System.getProperty("user.name") + " </td></tr></table>"
													+ "<h2 style='text-shadow:black;color:#F08080;text-decoration:underline;' id='htmlheader'><a>HTML Test Report: </a></h2><table><tr><th style='width: 10px;'>Step</th>"
														+ "<th style='width: 35px;'>Description</th><th style='width: 35px;'>Details</th><th style='width: 10px;'>Status</th><th style='width:10px;'>Time</th></tr>");
	}

	public static void log(String strDescription, String strExpectedMessage, LOG_STATUS status) {
		String logStatus = status.getStatus();
		String timeStamp = ReportGenerator.getCurrentTimeStamp();
		//Add Step to HTML Report 
		try {
			switch (logStatus) {
			case "info":
				bufferedWriter.write( "<tr><td id='statuscells'> " + steps
							+ " </td><td>Create New Business</td><td> " + strExpectedMessage + " </td>"
								+ "<td id='statuscells' style='color:grey;font-weight: bolder;'>Info</td><td id='statuscells'> " + timeStamp + " </td> <!-- #FF6347 --></tr>");
				info += 1;
				break;
			case "pass":
				bufferedWriter.write( "<tr><td id='statuscells'> " + steps
							+ " </td><td>Create New Business</td><td> " + strExpectedMessage + "</td>" 
								+ "<td id='statuscells' style='color:green;font-weight: bolder;'>Pass</td><td id='statuscells'> " + timeStamp + " </td> <!-- #FF6347 --></tr>");
				pass += 1;
				break;
			case "warning":
				bufferedWriter.write("<tr><td id='statuscells'> " + steps
							+ " </td><td>Create New Business</td><td> " + strExpectedMessage + "</td>" 
								+ "<td id='statuscells' style='color:orange;font-weight: bolder;'>Warning</td><td id='statuscells'> " + timeStamp + " </td> <!-- #FF6347 --></tr>");
				warning += 1;
				break;
			case "fail":
				bufferedWriter.write("<tr><td id='statuscells'> " + steps
							+ " </td><td>Create New Business</td><td> " + strExpectedMessage + " </td>"
								+ "<td id='statuscells' style='color:red;font-weight: bolder;'>Fail</td><td id='statuscells'> " + timeStamp + " </td> <!-- #FF6347 --></tr>");
				fail += 1;
				break;
			}
			steps += 1;
		} catch (IOException ioException) {
			System.out.println("Unable to Log Message on Report");
			ioException.printStackTrace();
		}
	}

	private static void closeReport() {
		end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		try {
			bufferedWriter
					.write("</table><table class='headerTable' id='scrollDown'><tr id='dummy'><td id='dummy'>Overall Status : <span style='color: black'>Pass</span></td></tr><tr id='dummy'>"
							+ "<td id='dummy'><span style='color: green'>Pass : " + pass
								+ " </span> </td></tr><tr id='dummy'><td id='dummy'><span style='color: red'>Fail : " + fail
									+ " </span></td></tr><tr id='dummy'>"
										+ "<td id='dummy'><span style='color: orange'>Warning : " + warning
											+ " </span></td></tr><tr id='dummy'><td id='dummy'>Total time taken  : "
												+ timeElapsed.toMillis() + "ms </td></tr></table>"
													+ "<div id='footer'><p style='margin-left: 20px;'>&copy; Optum Global Technologies.</p></div></div></body></html>");
			// Close Buffered Writer & FileWriter Object's
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException exception) {
			System.out.println("Unable to close HTML Report");
			exception.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static String getCurrentTimeStamp() {
		SimpleDateFormat dateformatter = new SimpleDateFormat("HH:mm:ss a");
		Date todaysDate = new Date();
		String str_date = dateformatter.format(todaysDate);
		return str_date.toString();
	}

	public static void main(String[] args) {
		try {
			ReportGenerator.initReport();
			ReportGenerator.log("", "", LOG_STATUS.PASS);
			ReportGenerator.log("", "", LOG_STATUS.FAIL);
			ReportGenerator.log("", "", LOG_STATUS.INFO);
			ReportGenerator.log("", "", LOG_STATUS.WARNING);
			ReportGenerator.closeReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
