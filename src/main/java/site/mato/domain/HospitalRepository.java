package site.mato.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//순서 주의!!!
@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Integer>{

}
