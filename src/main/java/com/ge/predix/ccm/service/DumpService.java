package com.ge.predix.ccm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.predix.ccm.db.DAO;
import com.ge.predix.ccm.entity.TbColumn;
import com.ge.predix.ccm.entity.TbName;
import com.ge.predix.ccm.util.Constant;
import com.ge.predix.ccm.util.Utils;

@RestController
public class DumpService {

	Logger log = Logger.getLogger(DumpService.class) ;
	
    @Autowired
    private DAO dao ;
    
    @Value("${sqlpath}")
	private String sqlPath;
    
    public String geneerateSQLFile() throws IOException{
    	List<TbName> tbNames = dao.getTbNameList() ;
    	
    	String str = null;
    	
    	try {
			str =  gernateFile(tbNames);
		} catch (Exception e) {
			log.info("------------geneerateSQLFile--------------------------");
			log.info(e.getMessage());
			e.printStackTrace();
		}
    	log.info("------------geneerateSQLFile  SUCCESS --------------------------");
    	return str;
    }

	private String gernateFile(List<TbName> tbNames)  {

		StringBuffer finalSQL = new StringBuffer(120000000) ;
//		StringBuffer finalSQL = new StringBuffer(200) ;
		List<TbColumn> columns = null ;
		List<String> datas = null;
		StringBuffer insertSQL = null ;
		StringBuffer insertValueSQL = null ;

//		for(TbName tbName :tbNames){
//	        try {
//	        	String tableName = tbName.getName() ;
		
		for(String tbName : Constant.tables){
	        try {
	        	String tableName = tbName ;
	        	columns = dao.getTbColumnList(tableName);
//	        	String createTableSql = Utils.generateTableSQL(tableName, columns) ;
//	        	finalSQL.append(createTableSql);
				
	            String sqlStr = Utils.generateInsertSQL(tableName, columns) ;
	            datas = dao.generateInsertSQL(sqlStr , columns) ;

	            insertSQL = new StringBuffer(500);
				if(datas.size()>0){
					insertSQL.append(" insert into ").append(tableName).append(Utils.generateInsertSQLColumn(columns)).append(" values ") ;
				}
				
				finalSQL.append(insertSQL) ;
				
				
				for(int index =0; index< datas.size(); index ++){
					finalSQL.append("\r\n") ;
					insertValueSQL = new StringBuffer(200);
					insertValueSQL.append(" ( ");
					String[] columnDatas = datas.get(index).split("\\$") ;
					for(int i = 0; i < columnDatas.length; i ++){
						insertValueSQL.append("'").append(Utils.convert(columnDatas[i])).append("'") ;
						if(i < columnDatas.length - 1){
							insertValueSQL.append(" , ") ;
						}
					}
					insertValueSQL.append(" ) ");
					if(index < datas.size() - 1){
						insertValueSQL.append(" , ");
					}else{
						insertValueSQL.append(" ; ");
					}
					finalSQL.append(insertValueSQL) ;
				}
//				
				finalSQL.append("\r\n\r\n") ;
	        } catch (Exception e) {
	        	log.info("-------------- Dump Exception -----------------");
	        	log.info(e.toString());
	            System.out.println(e.toString());  
	        }
		}
		return finalSQL.toString();
	}
    
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile( Long id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", sqlPath));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
        String str =  geneerateSQLFile();
        
        ByteArrayInputStream byteStream = new ByteArrayInputStream(str.getBytes()) ;
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(str.getBytes().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))  
                .body(new InputStreamResource(byteStream));
    }
	
}
