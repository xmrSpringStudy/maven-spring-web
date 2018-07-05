package first.com.cn.myMavenTest.spittr.data;

import java.util.List;

import first.com.cn.myMavenTest.spittr.Spittle;

public interface SpittleRepository {

	  List<Spittle> findRecentSpittles();

	  List<Spittle> findSpittles(long max, int count);
	  
	  Spittle findOne(long id);

	  void save(Spittle spittle);

	}