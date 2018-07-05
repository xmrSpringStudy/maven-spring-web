package first.com.cn.myMavenTest.test.spittr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import first.com.cn.myMavenTest.spittr.Spittle;
import first.com.cn.myMavenTest.spittr.data.SpittleRepository;
import first.com.cn.myMavenTest.spittr.web.SpittleController;


public class SpittleControllerTest {
	  
  @Test
  public void houldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(20);
    SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
    Mockito.when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);

    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/spittles"));
    action.andExpect(MockMvcResultMatchers.view().name("spittles"));
    action.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"));
    action.andExpect(MockMvcResultMatchers.model().attribute("spittleList", expectedSpittles));
  }

  @Test
  public void shouldShowPagedSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(20);
    SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);

    Mockito.when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    ResultActions action =  mockMvc.perform(MockMvcRequestBuilders.get("/spittles"));
    action.andExpect(MockMvcResultMatchers.view().name("spittles"));
    action.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"));
    action.andExpect(MockMvcResultMatchers.model().attribute("spittleList", expectedSpittles));
  }
  
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle("Hello", new Date());
    SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
    Mockito.when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/spittles/12345"));
    action.andExpect(MockMvcResultMatchers.view().name("spittle"));
    action.andExpect(MockMvcResultMatchers.model().attributeExists("spittle"));
    action.andExpect(MockMvcResultMatchers.model().attribute("spittle", expectedSpittle));
  }

  @Test
  public void saveSpittle() throws Exception {
    SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post("/spittles")
           .param("message", "Hello World") // this works, but isn't really testing what really happens
           .param("longitude", "-81.5811668")
           .param("latitude", "28.4159649")
           );
    action.andExpect(MockMvcResultMatchers.redirectedUrl("/spittles"));
    
    Mockito.verify(mockRepository, Mockito.atLeastOnce()).save(new Spittle(null, "Hello World", new Date(), -81.5811668, 28.4159649));
  }
  
  private List<Spittle> createSpittleList(int count) {
    List<Spittle> spittles = new ArrayList<Spittle>();
    for (int i=0; i < count; i++) {
      spittles.add(new Spittle("Spittle " + i, new Date()));
    }
    return spittles;
  }
}
