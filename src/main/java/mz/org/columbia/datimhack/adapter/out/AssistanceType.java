/**
 *
 */
package mz.org.columbia.datimhack.adapter.out;

/**
 * @author Stélio Moiane
 *
 */
public enum AssistanceType {

	DSD("#PEPFAR_Form_X_DSD"),

	TA("#PEPFAR_Form_X_TA"),

	NA("#PEPFAR_Form_7_NA");

	private String assistanceType;

	AssistanceType(final String assistanceType) {
		this.assistanceType = assistanceType;
	}

	public String getAssistanceType() {
		return this.assistanceType;
	}
}
