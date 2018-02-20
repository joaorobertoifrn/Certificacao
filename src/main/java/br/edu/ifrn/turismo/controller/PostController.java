package br.edu.ifrn.turismo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.turismo.filter.PostFilter;
import br.edu.ifrn.turismo.model.Post;
import br.edu.ifrn.turismo.service.PostService;

@Controller
@RequestMapping("/blog")
public class PostController {
	
	private static final String CADASTRO_VIEW = "CadastroComentario";
	
	@Autowired
	private PostService PostService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Post());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Post post, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try {
			PostService.salvar(post);
			attributes.addFlashAttribute("mensagem", "Comentário Salvo com sucesso!");
			return "redirect:/blog/novo";
		} catch (Exception e) {
			errors.rejectValue("comentario", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Post post) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(post);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Integer id, RedirectAttributes attributes) {
		PostService.excluir(id);
		
		attributes.addFlashAttribute("mensagem", "Comentário Excluido com sucesso!");
		return "redirect:/titulos";
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") PostFilter filtro) {
		List<Post> todosPosts = PostService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaBlog");
		mv.addObject("posts", todosPosts);
		return mv;
	}
		
}
