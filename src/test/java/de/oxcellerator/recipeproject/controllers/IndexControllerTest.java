package de.oxcellerator.recipeproject.controllers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.oxcellerator.recipeproject.domain.Recipe;
import de.oxcellerator.recipeproject.services.RecipeService;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

/**
 * @author Olexiy Sokurenko
 **/
public class IndexControllerTest {

  private IndexController indexController;

  @Mock
  private RecipeService recipeService;

  @Mock
  private Model model;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  public void testMockMVC() throws Exception{
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
    .andExpect(view().name("index"));
  }
  @Test
  public void getIndexPage() {
    //given
    Set<Recipe> recipes = new HashSet<>();
    Recipe recipe1 = new Recipe();
    recipe1.setId(1L);
    recipes.add(recipe1);
    Recipe recipe2 = new Recipe();
    recipe1.setId(2L);
    recipes.add(recipe2);
    when(recipeService.getRecipes()).thenReturn(recipes);
    ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);



    //when
    String resultTemplate = indexController.getIndexPage(model);

    //then
    assertEquals(resultTemplate, "index");
    verify(recipeService, times((1))).getRecipes();
    verify(model, times((1))).addAttribute(eq("recipes"), argumentCaptor.capture());
    Set<Recipe> setInController = argumentCaptor.getValue();
    assertEquals(2, setInController.size());
  }
}