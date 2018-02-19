
package fr.epita.iam.exceptions;

import java.io.Serializable;

import fr.epita.iam.datamodel.Identity;


@SuppressWarnings("serial")
public class IdentityCreationException extends Exception implements Serializable {

	private Identity faultyIdentity;

	/**
	 *
	 */
	public IdentityCreationException(Identity identity, Exception originalCause) {
		faultyIdentity = identity;
		initCause(originalCause);

	}

	@Override
	public String getMessage() {
		return "problem occured while creating that identity in the system " + faultyIdentity.toString();
	}

}
