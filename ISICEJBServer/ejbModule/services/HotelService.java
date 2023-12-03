package services;

import java.util.List;

import dao.IDaoLocale;
import dao.IDaoLocaleHotel;
import dao.IDaoRemote;
import entities.Hotel;
import entities.Ville;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless(name = "hotelService")
public class HotelService implements IDaoRemote<Hotel>, IDaoLocaleHotel {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Hotel create(Hotel o) {
		em.persist(o);
		return o;
	}

	@Override
	public boolean delete(Hotel o) {
		em.remove(em.contains(o) ? o : em.merge(o));
		return true;
	}

	@Override
	public Hotel update(Hotel updatedHotel) {
		try {
			if (!em.contains(updatedHotel)) {
				updatedHotel = em.merge(updatedHotel);
			}
			Hotel existingHotel = em.find(Hotel.class, updatedHotel.getId());

			if (existingHotel != null) {
				existingHotel.setNom(updatedHotel.getNom());
				existingHotel.setAdresse(updatedHotel.getAdresse());
				existingHotel.setTelephone(updatedHotel.getTelephone());
				existingHotel.setVille(updatedHotel.getVille());
				return existingHotel;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Hotel findById(int id) {
		return em.find(Hotel.class, id);
	}

	@Override
	public List<Hotel> findAll() {
		Query query = em.createQuery("select v from Hotel v");
		return query.getResultList();
	}

	@Override
	public List<Hotel> findByVille(Ville ville) {
	    Query query = em.createQuery("SELECT h FROM Hotel h WHERE h.ville = :villeParam");
	    query.setParameter("villeParam", ville);
	    return query.getResultList();
	}
}
