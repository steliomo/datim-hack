/**
 *
 */
package mz.org.columbia.datimhack.application.out;

import java.util.List;

import mz.org.columbia.datimhack.domain.GenericObject;

/**
 * @author Stélio Moiane
 *
 */
public interface InputDataPort {

	int inputData(String url, List<GenericObject> data);

}
