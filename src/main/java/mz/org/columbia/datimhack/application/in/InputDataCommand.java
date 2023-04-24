/**
 *
 */
package mz.org.columbia.datimhack.application.in;

import mz.org.columbia.datimhack.domain.SubmissionType;

/**
 * @author Stélio Moiane
 *
 */
public class InputDataCommand {

	private String username;

	private String password;

	private String filename;

	private SubmissionType submissionType;

	public InputDataCommand(final String username, final String password, final String filename) {
		this.username = username;
		this.password = password;
		this.filename = filename;
		this.submissionType = SubmissionType.INPUT;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getFilename() {
		return this.filename;
	}

	public SubmissionType getSubmissionType() {
		return this.submissionType;
	}
}
