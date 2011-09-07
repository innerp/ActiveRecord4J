package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.wordpress.innerp.model.ConnectionManager;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		PropertyDescriptor[] descriptors=null;
//		try {
//			descriptors = Introspector.getBeanInfo(TestObject.class).getPropertyDescriptors();
//			System.out.println(descriptors[0].getName());
//		} catch (IntrospectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		TestObject to = new TestObject();
//		to.setContent("asas");
//		to.setId(99);
//		to.setName("sasa");
//		to.setPassword("sasas");
//		DBBeanProcessor bp = new DBBeanProcessor();
//		Map<String,Object> map = bp.beantoMap(to);
//		String[] keys = map.keySet().toArray(new String[map.size()]);
//		for(String key:keys){
//			System.out.println(key);
//			System.out.println(map.get(key));
//		}
//		
		try {
			
			Connection c = ConnectionManager.getConnection();
			long now = System.currentTimeMillis();
			CachedRowSet crs = new CachedRowSetImpl();
			
			//ResultSet rs = c.createStatement().executeQuery("SELECT * FROM T_USER where id=5");
			crs.setCommand("SELECT * FROM T_USER where reg<?");
			crs.setObject(1, new Date());
			crs.execute(c);
			
			System.out.println(crs.size());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
