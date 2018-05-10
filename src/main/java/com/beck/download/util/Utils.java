package com.beck.download.util;

import java.util.List;

import com.beck.download.entity.TbColumn;

public class Utils {

	//Result:  create  table Name ( *** varchar(20), ......);
	public static String generateTableSQL(String tbName , List<TbColumn>  tbColumns){
		StringBuffer buf = new StringBuffer(2000) ;
		buf.append("drop table ").append(tbName).append("; ").append("\r\n\r\n") ;
//		buf.append("create table ").append(tbName).append(" ( ") ;
//		TbColumn tb = null ;
//		for(int length=0; length < tbColumns.size(); length ++){
//			tb = tbColumns.get(length);
//			buf.append(tb.getName()).append(" ").append(getType(tb)) ;
//			if(length < tbColumns.size() - 1 ){
//				buf.append(", ");
//			}
//		}
//		buf.append(" ) ; ").append("\r\n");
		return buf.toString() ;
	}

	public static StringBuffer getType(TbColumn col){
		StringBuffer realType = new StringBuffer(1000) ;
		String type = col.getType() ;
		
		if("int2".equals(type)){
			realType.append("smallint") ;
		}else if("int4".equals(type)){
			realType.append("integer") ;
		}else if("int8".equals(type)){
			realType.append("bigint") ;
		}else if("bool".equals(type)){
			realType.append("boolean") ;
		}else if("varchar".equals(type)){
			realType.append("character varying ").append(" ( ").append(getLength(col.getLength1(),col.getLength2())).append(" ) ") ;
		}else if("time".equals(type)){
			realType.append("time") ;
		}else if("timestamp".equals(type)){
			realType.append("timestamp without time zone") ;
		}else if("timestamptz".equals(type)){
			realType.append("timestamp(0) without time zone") ;			
		}else if("bpchar".equals(type)){
			realType.append("character").append(" ( ").append(getLength(col.getLength1(),col.getLength2())).append(" ) ")  ;
		}else if("float4".equals(type)){
			realType.append("real") ;
		}else if("date".equals(type)){
			realType.append("date") ;
		}else if("text".equals(type)){
			realType.append("text") ;
		}else if("numeric".equals(type)){
			realType.append("numeric") ;
		}
		return realType ;
	}
	
	public static int getLength(String l1 , String l2){
		if(Integer.valueOf(l1).intValue() > 0)
			return Integer.valueOf(l1).intValue() ;
		if(Integer.valueOf(l2).intValue() > 0)
			return Integer.valueOf(l2).intValue() - 4;
		return 100 ;
	}
	
	//Result:  Select  *****    from table Name
	public static String generateInsertSQL(String tbName , List<TbColumn>  tbColumns){
		StringBuffer buf = new StringBuffer(2000) ;
		buf.append(" select ") ;
		TbColumn column = null ;
		for(int length=0; length < tbColumns.size(); length ++){
			column = tbColumns.get(length);
			buf.append(column.getName()).append(" ");
			if(length < tbColumns.size() -1 ){
				buf.append(", ");
			}
		}
//		buf.append(" from ").append(tbName).append(" limit ").append(Constant.LIMIT).append(" offset ").append( Constant.OFFSET );
//		buf.append(" from ").append(tbName).append(" limit 300000 offset 300000 ");
		buf.append(" from ").append(tbName);
		return buf.toString() ;
	}

	public static String generateInsertSQLColumn(List<TbColumn> columns){
		StringBuffer columnBuf = new StringBuffer(500) ;
		columnBuf.append("(") ;
		for(int i = 0; i < columns.size(); i ++){
			columnBuf.append(columns.get(i).getName());
			if(i < columns.size() - 1){
				columnBuf.append(", ");
			}
		}
		columnBuf.append(" ) ") ;
		return columnBuf.toString() ;
	}
	
	public static String convert(String s){
		String result = null ;
		if("null".equals(s)){
			result = "" ;
		}else{
			result = s ;
		}
		return result ;
	}
	
	
}
