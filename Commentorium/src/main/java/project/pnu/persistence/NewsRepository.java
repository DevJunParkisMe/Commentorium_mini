package project.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import project.pnu.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
