package first.com.cn.myMavenTest.spittr.data;

import first.com.cn.myMavenTest.spittr.Spitter;

public interface SpitterRepository {

	  Spitter save(Spitter spitter);
	  
	  Spitter findByUsername(String username);

}
