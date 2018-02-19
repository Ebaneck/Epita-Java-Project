
package fr.epita.iam.service;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;


public interface IdentityDAO {

	public void create(Identity identity) throws IdentityCreationException;

	public void update(Identity identity);

	public void delete(Identity identity) throws IdentityCreationException;

	public List<Identity> search();
	
	

}
