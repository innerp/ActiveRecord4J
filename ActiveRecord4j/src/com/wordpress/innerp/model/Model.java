package com.wordpress.innerp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.wordpress.innerp.sql.From;
import com.wordpress.innerp.sql.Limit;
import com.wordpress.innerp.sql.OrderBy;
import com.wordpress.innerp.sql.SQL;
import com.wordpress.innerp.sql.Select;
import com.wordpress.innerp.sql.Where;
import com.wordpress.innerp.util.impl.BeanProcessorImpl;
import com.wordpress.innerp.util.impl.DBBeanProcessor;
import com.wordpress.innerp.util.impl.MapToPreparedStatementImpl;
import com.wordpress.innerp.util.impl.ResultSetProcessorImpl;

public class Model implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8308209320752993096L;
	private static Logger log = Logger.getLogger("Model");
	private long id;
	private String prefix;
	private Connection con;
	private Where where = new Where();
	private Limit limit = new Limit();
	private OrderBy orderBy = new OrderBy();
	private Select select = new Select();
    private From from = new From();
	public long insert() {
		con = ConnectionManager.getConnection();
		DBBeanProcessor dbp = new DBBeanProcessor();
		Map<String, Object> map = dbp.toInsertMap(this);
		PreparedStatement ps = new MapToPreparedStatementImpl()
				.mapToStatementForInsert(map, con);
		ResultSet rs = null;
		try {
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			return rs.next() ? rs.getLong(1) : -1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();

		}
		return -1;

	}

	public long update() {
		con = ConnectionManager.getConnection();
		DBBeanProcessor dbp = new DBBeanProcessor();
		Map<String, Object> map = dbp.toUpdate(this);
		PreparedStatement ps = new MapToPreparedStatementImpl()
				.mapToStatementForUpdate(map, con);
		try {
			int result = ps.executeUpdate();
			return result == 1 ? getId() : -1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();

		}
		return -1;
	}

	public boolean delete() {
		con = ConnectionManager.getConnection();
		DBBeanProcessor dbp = new DBBeanProcessor();
		Map<String, Object> map = dbp.toDelete(this);
		PreparedStatement ps = new MapToPreparedStatementImpl()
				.mapToStatementForDelete(map, con);
		try {
			int result = ps.executeUpdate();
			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();

		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends Model> T findOne() {
		con = ConnectionManager.getConnection();
		limit(0, 1);
		PreparedStatement ps=null;
		try {
			ps = sql(con);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			return (T) (new BeanProcessorImpl().mapToBean(
					new ResultSetProcessorImpl().resultToMap(rs), getClass()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();
		}
		return null;

	}

	public int count() {
		select("COUNT(*)");
		con = ConnectionManager.getConnection();
		PreparedStatement ps=null;
		try {
			ps = sql(con);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			return rs.next() ? rs.getInt(1) : 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();

		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	public <T extends Model> List<T> find() {

		con = ConnectionManager.getConnection();
		PreparedStatement ps=null;
		try {
			ps = sql(con);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			return (List<T>) (new BeanProcessorImpl().mapsToBeanList(
					new ResultSetProcessorImpl().resultToMaps(rs), getClass()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();
		}
		return null;

	}

	public List<Map<String, Object>> findColumns() {
		con = ConnectionManager.getConnection();
		PreparedStatement ps = null;
		try {
			ps = sql(con);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {

			rs = ps.executeQuery();
			return new ResultSetProcessorImpl().resultToMaps(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeResultSet(rs);
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeConnection();
		}
		return null;

	}

	public <T extends Model> T findSelf() {
		long id = getId();
		if (id < 0) {
			log.warning("不合法的id,请检查你调用的setId()");
			try {
				throw new Exception("不合法ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		eq("id", getId());
		return findOne();
	}

	public <T extends Model> T findById(long id) {
		if (id < 0) {
			log.warning("不合的ID id 应该>1");
			try {
				throw new Exception("不合法ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		eq("id", id);
		return findOne();
	}

	public long save() {
		if (getId() > 0) {
			return update();

		} else {
			return insert();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPrefix() {
		if (prefix == null) {
			prefix = ConnectionManager.getPreFix();
		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	private String table() {
		StringBuilder table = new StringBuilder();
		if (getPrefix() != null)table.append(getPrefix());	
		table.append(getClass().getSimpleName());
		return table.toString();
	}

	public Model page(int page, int size) {
		limit(page, size);
		return this;
	}

	/**
	 * where
	 * */
	public Model eq(String columnName, Object arg) {
		where.eq(columnName, arg);
		return this;
	}
    /**
     * condition may like this "name='tai'" or "number = 9"
     * @throws Exception 
     * 
     * */
    public Model eq(String condition) {
    	if(!condition.contains("="))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	String[] parts = condition.split("=");
    	String value =parts[1];
    	if(value.contains("'")){
    		value = value.replace("'","");
    	}
    	where.eq(parts[0], value);
    	value = null;
    	return this;
    }
	public Model and() {
		where.and();
		return this;
	}

	public Model gt(String columnName, Object arg) {
		where.gt(columnName, arg);
		return this;
	}
    public Model gt(String condition){
    	if(!condition.contains(">"))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
		String[] parts = condition.split(">");
		String value = parts[1];
		if(value.contains("'")){
			value = value.replace("'", "");
		}
		where.gt(parts[0], value);
    	return this;
    }
	public Model gtOreq(String columnName, Object arg) {
		where.gtOreq(columnName, arg);
		return this;
	}
    public Model gtOreq(String condition){
    	if(!condition.contains(">="))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
		String[] parts = condition.split(">=");
		String value = parts[1];
		if(value.contains("'")){
			value = value.replace("'", "");
		}
		where.gtOreq(parts[0], value);
    	return this;
    }
	public Model lt(String columnName, Object arg) {
		where.lt(columnName, arg);
		return this;
	}
    public Model lt(String condition){
    	if(!condition.contains("<"))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	String[] parts = condition.split("<");
    	String value =parts[1];
    	if(value.contains("'")){
    		value = value.replace("'","");
    	}
    	where.lt(parts[0], value);
    	value = null;
    	return this;
    }
	public Model ltOreq(String columnName, Object arg) {
		where.ltOreq(columnName, arg);
		return this;
	}
    public Model ltOreq(String condition){
    	if(!condition.contains("<="))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	String[] parts = condition.split("<=");
    	String value =parts[1];
    	if(value.contains("'")){
    		value = value.replace("'","");
    	}
    	where.ltOreq(parts[0], value);
    	value = null;
    	return this;
    }
	public Model or() {
		where.or();
		return this;
	}

	public Model like(String columnName, Object arg) {
		where.like(columnName, arg);
		return this;
	}
	public Model like(String condition) {
		condition = condition.toLowerCase();
		if(!condition.contains("like"))
			try {
				throw new Exception("输入不合法");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	String[] parts = condition.split("like");
    	String value =parts[1];
    	value = value.replace("'","");
    	value = value.replace("%","");
    	where.like(parts[0], value);
    	value = null;
		return this;
	}
	/**
	 * limit
	 * */

	public Model limit(int from, int offset) {
		limit.limit(from, offset);
		return this;
	}

	/**
	 * Order By
	 * */

	public Model desc(String columnName) {
		orderBy.desc(columnName);
		return this;
	}

	public Model asc(String columnName) {
		orderBy.asc(columnName);
		return this;

	}
	public Model select(String... columns) {
		StringBuilder temp = new StringBuilder();
		if (columns == null||columns.length==0) {
			return this;
		}
		int length = columns.length;
		for (int i = 0; i < length; i++) {
			temp.append(columns[i]);
			if (i < length - 1) {
				temp.append(",");
			}
		}
		select.select(temp.toString());
		temp=null;
		return this;
	}
    public Model from(String... tables){
    	StringBuilder temp = new StringBuilder();
    	if(tables==null||tables.length==0){
    		return this;
    	}
    	int length =tables.length;
    	for(int i = 0; i < length; i++){
    		temp.append(tables[i]);
    		if (i < length - 1) {
				temp.append(",");
			}
    		
    	}
    	from.from(temp.toString());
    	temp = null;
    	return this;
    }
	private PreparedStatement sql(Connection con) throws Exception {
		StringBuilder sql = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		String sqlselect = select.getColumns();
		if (sqlselect != null) {
			sql.append(SQL.SELECT);
			sql.append(SQL.BLANK);
			sql.append(sqlselect);
			sql.append(SQL.BLANK);
		} else {
			sql.append(SQL.SELECT);
			sql.append(SQL.BLANK);
			sql.append(SQL.ALL);
			sql.append(SQL.BLANK);
		}
		select.clear();
		String sqlfrom = from.getTables();
		if(sqlfrom!=null){
			sql.append(SQL.FROM);
			sql.append(SQL.BLANK);
			sql.append(sqlfrom);
		}else{
			sql.append(SQL.FROM);
			sql.append(SQL.BLANK);
			sql.append(table());
		}
		from.clear();
		StringBuilder wheresql = new StringBuilder();
		wheresql.append(SQL.BLANK);
		wheresql.append(SQL.WHERE);
		wheresql.append(SQL.BLANK);
		List<Map<String, Object>> whereList = where.getList();
		int whereSize = whereList.size();
		if (whereSize > 0) {
			sql.append(wheresql.toString());
			wheresql = null;
			for (int i = 0; i < whereSize; i++) {
				Map<String, Object> whereMap = whereList.get(i);
				int word = (Integer) whereMap.get("word");
				whereMap.remove("word");
				switch (word) {
				case Where.AND:
					if (i == 0 || i == (whereSize - 1)) {
						log.warning("and的位置错误，请检查你调用and与其他条件函数的顺序!");
						throw new Exception("and的位置错误，请检查你调用and与其他条件函数的顺序!");
						
					} else {
						sql.append(SQL.BLANK);
						sql.append("AND");
						sql.append(SQL.BLANK);
					}
					break;
				case Where.EQUAL:
					String[] keys = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys[0]);
					sql.append("=?");
					list.add(whereMap.get(keys[0]));
					break;
				case Where.GREATER_THAN:
					String[] keys1 = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys1[0]);
					sql.append(">?");
					list.add(whereMap.get(keys1[0]));
					break;
				case Where.GREATER_THAN_OR_EQUAL:
					String[] keys2 = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys2[0]);
					sql.append(">=?");
					list.add(whereMap.get(keys2[0]));
					break;
				case Where.OR:
					if (i == 0 || i == (whereSize - 1)) {
						log.warning("or的位置错误，请检查你调用and与其他条件函数的顺序!");
					    throw new Exception("or的位置错误，请检查你调用and与其他条件函数的顺序!");
					} else {
						sql.append(SQL.BLANK);
						sql.append("or");
						sql.append(SQL.BLANK);
					}
					break;
				case Where.LEASS_THAN:
					String[] keys3 = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys3[0]);
					sql.append("<?");
					list.add(whereMap.get(keys3[0]));
					break;
				case Where.LEASS_THAN_OR_EQUAL:

					String[] keys4 = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys4[0]);
					sql.append("<=?");
					list.add(whereMap.get(keys4[0]));
					break;
				case Where.LIKE:
					String[] keys5 = whereMap.keySet().toArray(
							new String[whereMap.size()]);
					sql.append(keys5[0]);
					sql.append(SQL.BLANK);
					sql.append("like  ?");
					list.add(whereMap.get(keys5[0]));
					break;

				}

			}

			where.clear();

		}
		List<Map<String, Object>> orderByList = orderBy.getList();
		int orderSize = orderByList.size();
		if (orderSize > 0) {
			sql.append(SQL.BLANK);
			sql.append(SQL.ORDERBY);
			sql.append(SQL.BLANK);
			for (int i = 0; i < orderSize; i++) {
				Map<String, Object> orderMap = orderByList.get(i);
				String[] key = orderMap.keySet().toArray(new String[1]);
				if (key[0].equals(OrderBy.ASC)) {
					sql.append("? asc ");
					list.add(orderMap.get(key));

				} else {
					sql.append("? desc ");
					list.add(orderMap.get(key));
				}
			}
			orderBy.clear();
		}
		Map<String, String> limitList = limit.getMap();
		int limitSize = limitList.size();
		if (limitSize > 0) {
			String[] key = limitList.keySet().toArray(new String[1]);
			sql.append(SQL.BLANK);
			sql.append("limit ");
			sql.append(key[0]);
			sql.append(limitList.get(key[0]));
			limit.clear();
		}
		log.info(sql.toString());
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			int objectSize = list.size();
			log.info("objectSzie:" + objectSize);
			for (int i = 0; i < objectSize; i++) {
				ps.setObject(i + 1, list.get(i));
			}
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = null;
		return null;

	}

	public Connection connection() {
		return ConnectionManager.getConnection();
	}
	
}
