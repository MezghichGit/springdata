package com.sip.ams.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.sip.ams.entities.Article;
import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ArticleRepository;
import com.sip.ams.repositories.ProviderRepository;
@Controller
@RequestMapping("/article/")
public class ArticleController {
	private final ArticleRepository articleRepository;
	private final ProviderRepository providerRepository;
    @Autowired
    public ArticleController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
        this.articleRepository = articleRepository;
        this.providerRepository = providerRepository;
    }
    
    @GetMapping("list")
    public String listProviders(Model model) {
    	//model.addAttribute("articles", null);
    	List<Article> la = (List<Article>) articleRepository.findAll();
    	if(la.size()==0) 
    		la = null;
        model.addAttribute("articles",la);
        return "article/listArticles";
    }
    
    @GetMapping("add")
    public String showAddArticleForm(Model model) {
    	
    	model.addAttribute("providers", providerRepository.findAll());
    	model.addAttribute("article", new Article());
        return "article/addArticle";
    }
    
    @PostMapping("add")
    //@ResponseBody
    //public String addArticle(@Valid Article article, BindingResult result, @RequestParam(name = "providerId", required = true) Long p) {
    	
    public String addArticle(@Valid Article article, BindingResult result) {
    	
    	/*Provider provider = providerRepository.findById(p)
                .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + p));
    	article.setProvider(provider);*/
    	
    	System.out.println(article);
    	 articleRepository.save(article);
    	 return "redirect:list";
    	
    	//return article.getLabel() + " " +article.getPrice() + " " + p.toString();
    }
    
    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
        Article artice = articleRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
        
        articleRepository.delete(artice);
        model.addAttribute("articles", articleRepository.findAll());
        return "article/listArticles";
    }
    
    @GetMapping("edit/{id}")
    public String showArticleFormToUpdate(@PathVariable("id") long id, Model model) {
    	Article article = articleRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
    	
        model.addAttribute("article", article);
        model.addAttribute("providers", providerRepository.findAll());
        model.addAttribute("idProvider", article.getProvider().getId());
        
        return "article/updateArticle";
    }
    @PostMapping("edit/{id}")
    public String updateArticle(@PathVariable("id") long id, @Valid Article article, BindingResult result,
        Model model, @RequestParam(name = "providerId", required = false) Long p) {
        if (result.hasErrors()) {
        	article.setId(id);
            return "article/updateArticle";
        }
        
        Provider provider = providerRepository.findById(p)
                .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + p));
    	article.setProvider(provider);
    	
        articleRepository.save(article);
        model.addAttribute("articles", articleRepository.findAll());
        return "article/listArticles";
    }

}
