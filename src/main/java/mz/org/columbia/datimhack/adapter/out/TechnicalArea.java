/**
 *
 */
package mz.org.columbia.datimhack.adapter.out;

/**
 * @author Stélio Moiane
 *
 */
public enum TechnicalArea {

	VMMC_CIRC("#PEPFAR_Tabs_vertical_1"),

	PREP_NEW("#PEPFAR_Tabs_vertical_1"),

	PREP_CT("#PEPFAR_Tabs_vertical_1"),

	KP_PREV("#PEPFAR_Tabs_vertical_1"),

	PP_PREV("#PEPFAR_Tabs_vertical_1"),

	TB_PREV("#PEPFAR_Tabs_vertical_1"),

	OVC_SERV("#PEPFAR_Tabs_vertical_1"),

	GEND_GBV("#PEPFAR_Tabs_vertical_1"),

	HTS_TST("#PEPFAR_Tabs_vertical_2"),

	HTS_RECENT("#PEPFAR_Tabs_vertical_3"),

	HTS_INDEX("#PEPFAR_Tabs_vertical_4"),

	HTS_SELF("#PEPFAR_Tabs_vertical_4"),

	PMTCT_STAT("#PEPFAR_Tabs_vertical_4"),

	PMTCT_EID("#PEPFAR_Tabs_vertical_4"),

	PMTCT_HEI_POS("#PEPFAR_Tabs_vertical_4"),

	TB_STAT("#PEPFAR_Tabs_vertical_4"),

	CXCA_SCRN("#PEPFAR_Tabs_vertical_4"),

	OVC_HIVSTAT("#PEPFAR_Tabs_vertical_4"),

	TX_NEW("#PEPFAR_Tabs_vertical_5"),

	TX_CURR("#PEPFAR_Tabs_vertical_5"),

	TX_RTT("#PEPFAR_Tabs_vertical_5"),

	TX_ML("#PEPFAR_Tabs_vertical_5"),

	PMTCT_ART("#PEPFAR_Tabs_vertical_5"),

	TX_TB("#PEPFAR_Tabs_vertical_5"),

	CXCA_TX("#PEPFAR_Tabs_vertical_5"),

	TX_PVLS("#PEPFAR_Tabs_vertical_6"),

	LAB_PTCQI("#PEPFAR_Tabs_vertical_7");

	private final String techArea;

	TechnicalArea(final String techArea) {
		this.techArea = techArea;
	}

	public String getTechArea() {
		return this.techArea;
	}
}
