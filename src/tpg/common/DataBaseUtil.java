package tpg.common;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.mysql.jdbc.PreparedStatement;

public class DataBaseUtil {
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/well_done";
	private String username = "root";
	private String password = "root";
	
	private Connection conn = null;
	
	/**
	 * 构造方法，加载驱动，获取连接对象
	 */
	public DataBaseUtil(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**关闭连接
	 * @throws SQLException
	 */
	public void closeConnection(){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**根据传入的sql语句和参数数组查询，返回一个结果列表
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return 
	 */
	public <T> List<T> query(String sql, Object[] params, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData();
			int paramsCount = pmd.getParameterCount();
			for(int i=0; i<paramsCount; i++){
				ps.setObject(i+1, params[i]);//参数的索引从1开始,数组下标从0开始
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next()){
				T obj = clazz.newInstance();
				for(int i=0; i<rsmd.getColumnCount(); i++){
					String colName = rsmd.getColumnName(i+1);
					Object colValue = rs.getObject(colName);
					BeanUtils.setProperty(obj, colName, colValue);
				}
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**根据传入的sql语句和参数数组更新(增/删/改)，返回受影响的行数
	 * @param sql sql语句
	 * @param params 参数数组
	 * @return 返回受影响的行数
	 */
	public int update(String sql, Object[] params){
		int row = 0;
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData();
			int paramsCount = pmd.getParameterCount();
			for(int i=0; i<paramsCount; i++){
				ps.setObject(i+1, params[i]);//参数的索引从1开始,数组下标从0开始
			}
			row = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
	
}