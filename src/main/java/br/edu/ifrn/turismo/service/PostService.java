package br.edu.ifrn.turismo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrn.turismo.filter.PostFilter;
import br.edu.ifrn.turismo.model.Post;
import br.edu.ifrn.turismo.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository posts;
	
	public void salvar(Post post) {
		try {
			posts.save(post);
		} catch (Exception e) {
			throw new IllegalArgumentException("Não foi possivel salvar o comentário");
		}
	}
	
	public List<Post> filtrar(PostFilter filtro) {
		String comentario = filtro.getComentario() == null ? "%" : filtro.getComentario();
		return posts.findByComentarioContaining(comentario);
	}
	
	public void excluir(Integer id) {
		posts.delete(id);
	}

}
