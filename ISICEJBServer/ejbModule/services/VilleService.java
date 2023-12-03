package services;

import java.util.List;

import dao.IDaoLocale;
import dao.IDaoRemote;
import entities.Ville;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless(name = "villeService")
public class VilleService implements IDaoRemote<Ville>, IDaoLocale<Ville> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Ville create(Ville o) {
		em.persist(o);
		return o;
	}

	@Override
	public boolean delete(Ville o) {
		em.remove(em.contains(o) ? o : em.merge(o));
		return true;
	}

	@Override
	public Ville update(Ville updatedVille) {
		try {
			if (!em.contains(updatedVille)) {
				updatedVille = em.merge(updatedVille);
			}
			Ville existingVille = em.find(Ville.class, updatedVille.getId());

			if (existingVille != null) {
				existingVille.setNom(updatedVille.getNom());
				return existingVille;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Ville findById(int id) {
		return em.find(Ville.class, id);
	}

	@Override
	public List<Ville> findAll() {
		Query query = em.createQuery("select v from Ville v");
		return query.getResultList();
	}

}
