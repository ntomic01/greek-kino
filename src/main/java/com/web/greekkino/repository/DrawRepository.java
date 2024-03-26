package com.web.greekkino.repository;


import com.web.greekkino.domain.Draw;
import com.web.greekkino.enums.DrawStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawRepository extends JpaRepository<Draw, Long> {

    List<Draw> findByStatus(DrawStatus status);

}
