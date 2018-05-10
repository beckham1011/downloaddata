package com.beck.download.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.beck.download.entity.TbColumn;
import com.beck.download.entity.TbName;

@Repository
public class DAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	// Get Table Name
	public List<TbName> getTbNameList(){
		String sql=" SELECT   tablename   FROM   pg_tables  WHERE   tablename   NOT   LIKE   'pg%'   AND tablename NOT LIKE 'sql_%' ORDER   BY   tablename ";
		 return jdbcTemplate.query(sql,new Object[] { }, new RowMapper<TbName>() {

			@Override
			public TbName mapRow(ResultSet result, int arg1) throws SQLException {
				TbName tbName = new TbName() ;
				tbName.setName(result.getString(1));
				return tbName;
			}
		}) ;
	}
	
	// Get Table Column
	public List<TbColumn> getTbColumnList(String tbName){
		
		StringBuffer buf = new StringBuffer(2000);
		buf.append(" SELECT a.attnum, a.attname AS field,t.typname AS type,  a.attlen AS length, a.atttypmod AS lengthvar, a.attnotnull AS notnull, b.description AS comment ") ;
		buf.append("   FROM pg_class c, pg_attribute a LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid, pg_type t ") ;
		buf.append("  WHERE c.relname = ? and a.attnum > 0 and a.attrelid = c.oid and a.atttypid = t.oid ORDER BY a.attnum ") ;
		
		return jdbcTemplate.query(buf.toString() ,new Object[] { tbName }, new RowMapper<TbColumn>() {
			@Override
			public TbColumn mapRow(ResultSet r, int arg1) throws SQLException {
				
				TbColumn column = new TbColumn() ;
				column.setName(r.getString(2));
				column.setType(r.getString(3));
				column.setLength1(r.getString(4));
				column.setLength2(r.getString(5));
				return column;
			}
		}) ;
	}
	

	public List<String> generateInsertSQL(String sqlStr, final List<TbColumn> columns) {
		return jdbcTemplate.query(sqlStr ,new Object[] {}, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet r, int arg1) throws SQLException {
				StringBuffer resultBuf = new StringBuffer() ;
				int size = columns.size();
				for(int i=0;i<size;i++){
					resultBuf.append(r.getString(i+1));
					if(i<size){
						resultBuf.append("$");
					}
				}
				return resultBuf.toString();
			}
		}) ;
	}
	
}
