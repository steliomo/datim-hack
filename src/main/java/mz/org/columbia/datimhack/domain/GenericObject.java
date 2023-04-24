/**
 *
 */
package mz.org.columbia.datimhack.domain;

import java.util.HashMap;

/**
 * @author Stélio Moiane
 *
 */
public class GenericObject {

	private final HashMap<String, Object> data;

	public GenericObject() {
		this.data = new HashMap<>();
	}

	public void putValue(final String key, final Object value) {
		this.data.put(key, value);
	}

	public Object getValue(final String key) {
		return this.data.get(key);
	}
}
