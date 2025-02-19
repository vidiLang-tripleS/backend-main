package TripleS.VidiLang.folder.repository;

import TripleS.VidiLang.folder.entity.Folder;
import TripleS.VidiLang.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByMemberEmail(@Param("email") String email);
}
