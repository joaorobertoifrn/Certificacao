package br.edu.ifrn.turismo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import br.edu.ifrn.turismo.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	public List<Post> findByComentarioContaining(String comentario);
	
}
