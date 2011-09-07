package test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Categories.IncludeCategory;

import com.sun.org.glassfish.external.arc.Taxonomy;
import com.wordpress.innerp.model.ConnectionManager;

public class TestMyORM {
	
    @Ignore
	@Test
	public void testInsert(){
		User u = new User();
		u.setAccount("121nerp");
		u.setPassword("21212password");
		u.setReg(new Date());
		//here wo must know the id in advance.
		//System.out.println(u.insert());
		Assert.assertEquals(109, u.insert());
		
		
		
	}
    @Ignore
	@Test
	public void testUpdate(){
		User u = new User();
		u.setId(5);
		u.setAccount("1myinnerp");
		u.setPassword("1mypassword");
		u.setReg(new Date());
		Assert.assertEquals(5, u.update());
	}
	@Ignore
	@Test
	public void testDelete(){
		User u = new User();
		u.setId(3);
		boolean result = u.delete();
		Music m = new Music();
		m.setId(1);
		Assert.assertTrue(result&&m.delete());
	}
	//娴嬭瘯鏉′欢涓烘棩鏈熸椂澶辫触銆�
	@Ignore
	@Test
	public void testQueryWithWhere(){
		
		User u = new User();
		//u.lt("reg",new Date());
		u.lt("reg",new Date());
		int size = u.find().size();
		Assert.assertEquals(5, size);
		
	}
	@Ignore
	@Test
	public void testQueryOrderBy(){
		User u = new User();
		Assert.assertEquals(8,u.find().size());
		
	}
	//PreparedStatement 涓嶆敮鎸佸儚 select * from t_user where account like '%?%'杩欐牱鐨剆ql璇彞
	
	@Ignore
	@Test
	public void testLike(){
		User u = new User();
		u.like("account", "%a%");
		int size = u.find().size();
		Assert.assertEquals(5, size);
//		Connection c = ConnectionManager.getConnection();
//		try {
//			PreparedStatement ps = c.prepareStatement("SELECT* FROM T_User WHERE account like ?");
//			ps.setObject(1,"%a%");
//			ps.executeQuery();
//		} catch (SQLException e) {
//		
//			e.printStackTrace();
//		}
		
	}
	@Ignore
	@Test
	public void testLimit(){
		User u = new User();
		u.limit(1, 3);
		int size = u.find().size();
		Assert.assertEquals(3, size);
	}
	@Ignore
	@Test
	public void testCount(){
		User u = new User();
		u.eq("account", "tai");
		int count = u.count();
		Assert.assertEquals(1, count);
	}
	@Ignore
	@Test
	public void testSelect(){
		User u = new User();
		u.select("account,password");
		List<Map<String,Object>> list =u.findColumns();
		Assert.assertEquals(3, list.size());
		
	}
	@Ignore
	@Test
	public void testFindSelf(){
		User u = new User();
		u.setId(1);
		u = u.findSelf();
		Assert.assertEquals("innerp",u.getAccount());
		System.out .println(u.getReg().getTime());
		Assert.assertNotNull(u.getReg());
		
	}
	@Ignore
	@Test
	public void testFindById(){
		User u = new User();
		u = u.findById(1);
		Assert.assertEquals("innerp",u.getAccount());
		System.out .println(u.getReg().getTime());
		Assert.assertNotNull(u.getReg());
	}
	@Ignore
	@Test
	public void testPage(){
		User u = new User();
		int size = u.page(1, 10).find().size();
		Assert.assertEquals(10, size);
	}
	@Ignore
	@Test
	public void testTotalCount(){
		User u = new User();
		int count  = u.count();
		Assert.assertEquals(103, u.count());
	}
	@Ignore
	@Test
	public void testAnd(){
		User u = new User();
		u = u.eq("account","innerp")
		     .and()
		     .eq("password", "mypassword")
		     .findOne();
		Assert.assertEquals(1, u.getId());
		
		
	}
	@Ignore
	@Test
	public void testOr(){
		User u = new User();
		u = u.eq("account","innerp")
		     .or()
		     .eq("password", "mypassword")
		     .findOne();
		Assert.assertEquals(1, u.getId());
	}
	@Ignore
	@Test
	public void testAndAll(){
		User u = new User();
		/**
		 * 漂亮的格式
		 * */
		int size  = u.eq("account","innerp")
		             .and()
		             .eq("password", "mypassword")
		             .find()
		             .size();
		Assert.assertEquals(1,size);
		
		
	}
	@Ignore
	@Test
	public void testOrAll(){
		User u = new User();
		int size = u.eq("account","innerp")
		            .or()
		            .eq("password", "mypassword")
		            .find()
		            .size();
		Assert.assertEquals(1, size);
	}
	@Ignore
	@Test
	public void testNDelete(){
		User u = new User();
		u.setId(103);
		Assert.assertTrue(u.delete());
	}
	@Ignore
	@Test
	public void testNSelect(){
		User u = new User();
		int size = u.select("account","password")
		            .findColumns()
		            .size();
		Assert.assertEquals(102, size);
	}
	/**
	 * 主要测试参数是否有' 或者''这样的单引号。
	 * 看来PreparedStatement 很强大
	 * */
	@Ignore
	@Test
	public void testSQLInjectingQuery(){
		
		User  u = new User();
		u = u.eq("account", "'tai").findOne();
		//System.out.println(u==null);
		Assert.assertNotNull(u);
	}
	@Ignore
	@Test
	public void testSQLInjectingInsert(){
		User u = new User();
		u.setAccount("'innerp");
		u.setPassword("'password'");
		u.setReg(new Date());
		Assert.assertNotSame(-1, u.insert());
	}
	@Ignore
	@Test
	public void testConditionOrder1(){
		User u = new User();
		u = u.and().eq("account", "'innerp").findOne();
		Assert.assertNotNull(u);
		Assert.assertEquals("'innerp", u.getAccount());
	}
	@Ignore
	@Test
	public void testConditionOrder2(){
		User u = new User();
		u=u.or().eq("account", "'innerp").findOne();
		Assert.assertNotNull(u);
		Assert.assertEquals("'innerp", u.getAccount());
	}
	/***
	 * 测试不通过 ，我会尽力去解决的。
	 * */
	@Ignore
	@Test
	public void testConditionOrder3(){
		User u = new User();
		/**
		 * 能有人这么来用么！
		 * */
		u=u.or().and().or().eq("account", "'innerp").findOne();
		Assert.assertNotNull(u);
		Assert.assertEquals("'innerp", u.getAccount());
	}
	
	
	
	
	
	
	

}
