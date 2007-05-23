/**
 *
 */
package ucl.cs.testingEmulator.location;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London Dept. of Computer Science Gower Street London WC1E
 * 6BT United Kingdom
 * 
 * Email: M.Sama (at) cs.ucl.ac.uk
 * 
 * Group: Software Systems Engineering
 * 
 */
public class LocalizationMethodWrapper {

	/**
	 * Location method is assisted by the other party (Terminal assisted for
	 * Network based, Network assisted for terminal based). MTA_ASSISTED =
	 * 0x00040000 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTA_ASSISTED = new LocalizationMethodWrapper(
			"MTA_ASSISTED",
			262144,
			"Location method is assisted by the other party \n(Terminal assisted for Network based, Network assisted for terminal based). \nMTA_ASSISTED = 0x00040000.");

	/**
	 * Location method is unassisted. This bit and MTA_ASSISTED bit MUST NOT
	 * both be set. Only one of these bits may be set or neither to indicate
	 * that the assistance information is not known. MTA_UNASSISTED = 0x00080000
	 * See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTA_UNASSISTED = new LocalizationMethodWrapper(
			"MTA_UNASSISTED",
			524288,
			"Location method is unassisted. \nThis bit and MTA_ASSISTED bit MUST NOT both be set. \nOnly one of these bits may be set or neither to indicate that the assistance information is not known. \nMTA_UNASSISTED = 0x00080000,");

	/**
	 * Location method Angle of Arrival for cellular / terrestrial RF system.
	 * MTE_ANGLEOFARRIVAL = 0x00000020 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTE_ANGLEOFARRIVAL = new LocalizationMethodWrapper(
			"MTE_ANGLEOFARRIVAL",
			32,
			"Location method Angle of Arrival for cellular / terrestrial RF system. \nMTE_ANGLEOFARRIVAL = 0x00000020.");

	/**
	 * Location method Cell-ID for cellular (in GSM, this is the same as CGI,
	 * Cell Global Identity). MTE_CELLID = 0x00000008 See Also:Constant Field
	 * Values
	 */
	public static final LocalizationMethodWrapper MTE_CELLID = new LocalizationMethodWrapper(
			"MTE_CELLID",
			8,
			"Location method Cell-ID for cellular (in GSM, this is the same as CGI, Cell Global Identity). \nMTE_CELLID = 0x00000008.");

	/**
	 * Location method using satellites (for example, Global Positioning System
	 * (GPS)). MTE_SATELLITE = 0x00000001 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTE_SATELLITE = new LocalizationMethodWrapper(
			"MTE_SATELLITE",
			1,
			"Location method using satellites (for example, Global Positioning System (GPS)). \nMTE_SATELLITE = 0x00000001.");

	/**
	 * Location method Short-range positioning system (for example, Bluetooth
	 * LP). MTE_SHORTRANGE = 0x00000010 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTE_SHORTRANGE = new LocalizationMethodWrapper(
			"MTE_SHORTRANGE",
			16,
			"Location method Short-range positioning system (for example, Bluetooth LP). \nMTE_SHORTRANGE = 0x00000010.");

	/**
	 * Location method Time Difference for cellular / terrestrial RF system (for
	 * example, Enhanced Observed Time Difference (E-OTD) for GSM).
	 * MTE_TIMEDIFFERENCE = 0x00000002 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTE_TIMEDIFFERENCE = new LocalizationMethodWrapper(
			"MTE_TIMEDIFFERENCE",
			2,
			"Location method Time Difference for cellular / terrestrial RF system \n(for example, Enhanced Observed Time Difference (E-OTD) for GSM). \nMTE_TIMEDIFFERENCE = 0x00000002.");

	/**
	 * Location method Time of Arrival (TOA) for cellular / terrestrial RF
	 * system. MTE_TIMEOFARRIVAL = 0x00000004 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTE_TIMEOFARRIVAL = new LocalizationMethodWrapper(
			"MTE_TIMEOFARRIVAL",
			4,
			"Location method Time of Arrival (TOA) for cellular / terrestrial RF system. \nMTE_TIMEOFARRIVAL = 0x00000004.");

	/**
	 * Location method is of type network based. This means that the final
	 * location result is calculated in the network. This bit and
	 * MTY_TERMINALBASED bit MUST NOT both be set. Only one of these bits may be
	 * set or neither to indicate that it is not known where the result is
	 * calculated. MTY_NETWORKBASED = 0x00020000 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTY_NETWORKBASED = new LocalizationMethodWrapper(
			"MTY_NETWORKBASED",
			131072,
			"Location method is of type network based. \nThis means that the final location result is calculated in the network. \nThis bit and MTY_TERMINALBASED bit MUST NOT both be set. \nOnly one of these bits may be set or neither to indicate that it is not known where the result is calculated. \nMTY_NETWORKBASED = 0x00020000.");

	/**
	 * Location method is of type terminal based. This means that the final
	 * location result is calculated in the terminal. MTY_TERMINALBASED =
	 * 0x00010000 See Also:Constant Field Values
	 */
	public static final LocalizationMethodWrapper MTY_TERMINALBASED = new LocalizationMethodWrapper(
			"MTY_TERMINALBASED",
			65536,
			"Location method is of type terminal based. \nThis means that the final location result is calculated in the terminal. \nMTY_TERMINALBASED = 0x00010000.");

	private String _name = null;

	private int _value = 0;

	private String _description = null;

	/**
	 * @param name
	 * @param _value
	 * @param _description
	 */
	protected LocalizationMethodWrapper(String name, int _value,
			String _description) {
		super();
		this._name = name;
		this._value = _value;
		this._description = _description;
	}

	public static LocalizationMethodWrapper[] getAllKnownLocalizationMethod() {
		LocalizationMethodWrapper[] array = new LocalizationMethodWrapper[] {
				LocalizationMethodWrapper.MTA_ASSISTED,
				LocalizationMethodWrapper.MTA_UNASSISTED,
				LocalizationMethodWrapper.MTE_ANGLEOFARRIVAL,
				LocalizationMethodWrapper.MTE_CELLID,
				LocalizationMethodWrapper.MTE_SATELLITE,
				LocalizationMethodWrapper.MTE_SHORTRANGE,
				LocalizationMethodWrapper.MTE_TIMEDIFFERENCE,
				LocalizationMethodWrapper.MTE_TIMEOFARRIVAL,
				LocalizationMethodWrapper.MTY_NETWORKBASED,
				LocalizationMethodWrapper.MTY_TERMINALBASED };
		return array;
	}

	/**
	 * @param _name
	 *            the _name to set
	 */
	public void setName(String _name) {
		this._name = _name;
	}

	/**
	 * @return the _name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param _value
	 *            the _value to set
	 */
	public void setValue(int _value) {
		this._value = _value;
	}

	/**
	 * @return the _value
	 */
	public int getValue() {
		return _value;
	}

	/**
	 * @param _description
	 *            the _description to set
	 */
	public void setDescription(String _description) {
		this._description = _description;
	}

	/**
	 * @return the _description
	 */
	public String getDescription() {
		return _description;
	}
}
