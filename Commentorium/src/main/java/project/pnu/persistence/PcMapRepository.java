package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.PcMap;

public interface PcMapRepository extends JpaRepository<PcMap, Long> {

}
