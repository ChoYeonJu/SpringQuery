package org.zerock.persistence;


import java.util.Collection;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{
	
	public List<Board> findBoardByTitle(String title);
	
	public Collection<Board> findByWriter(String writer);
	
	// 작성자에 대한 like % 키워드 %
	public Collection<Board> findByWriterContatining(String writer);

	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);

	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, Long num);
	
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	// 페이징 처리
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);
	
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);


	@Query("select b from board b where b.title like %?1% and b.bno > 0 order by b.bno desc")
	public List<Board> findByTitle(String title);
	
	@Query("select b from board b where b.content like %:content% and b.bno > 0 order by b.bno desc")
	public List<Board> findByContent(@Param("content") String content);
	
	@Query("select b.bno, b.title, b.writer, b.regdate " + " from board b where b.title like %?1% and b.bno > 0 order by b.bno desc")
	public List<Object[]> findByTitle2(String title);
	
	@Query(value = "select bno, title, writer from tbl_boards where title like concat('%', ?1, '%') and bno > 0 order by bno desc", nativeQuery=true)
	List<Object[]> findByTitle3(String title);
	
	@Query("select b from board b where b.bno > 0 order by b.bno desc")
	public List<Board> findBypage(Pageable pageable);

}
