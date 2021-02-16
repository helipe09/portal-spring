package br.org.isac.portaltransparencia.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.isac.portaltransparencia.portal.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
	

}
