package dao;

import java.util.List;

import entities.Hotel;
import entities.Ville;
import jakarta.ejb.Local;

@Local
public interface IDaoLocaleHotel extends IDaoLocale<Hotel> {
	public List<Hotel> findByVille(Ville ville);

}
