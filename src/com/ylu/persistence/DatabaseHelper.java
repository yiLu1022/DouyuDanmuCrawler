package com.ylu.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ylu.beans.Danmu;
import com.ylu.beans.RoomInfo;
import com.ylu.dao.DanmuDAO;
import com.ylu.dao.RoomInfoDAO;
import com.ylu.douyuFormat.Logger;

public class DatabaseHelper {

	private DanmuDAOMapper danmuDAOMapper ;
	private RoomInfoDaoImpl roomInfoDaoImpl;
	private ExecutorService singleThExecutor;
	
	private DatabaseHelper(){
		danmuDAOMapper = new DanmuDaoImpl();
		roomInfoDaoImpl = new RoomInfoDaoImpl();
		singleThExecutor = Executors.newSingleThreadExecutor();
	}
	
	public static DatabaseHelper getInstance(){
		return Holder.instance;
	}
	
	public static class Holder{
		private static DatabaseHelper instance= new DatabaseHelper();
		
	}
	
	public void insertRoomInfo(final RoomInfo roomInfo){
		singleThExecutor.execute(new Runnable() {
			
			public void run() {
				try{
					roomInfoDaoImpl.insert(new RoomInfoDAO(roomInfo));
				}catch(Exception e){
					e.printStackTrace();
					Logger.v("Cannot insert %s into database, skip.", roomInfo.toString());
				}
				
			}
		});
	}
	
	public void insertDanmu(final Danmu danmu){
		
		singleThExecutor.execute(new Runnable() {
			
			public void run() {
				if(danmu.getCid() != null){
					try{
						danmuDAOMapper.insert(new DanmuDAO(danmu));
					}catch(Exception e){
						Logger.v("Cannot insert %s into database, skip.", danmu.getTxt());
					}
				}	
			}
		});
		
	}
	
	public Collection<Danmu> findDanmuByCid(final String cid){
		final Collection<Danmu> messages = new ArrayList<Danmu>();

		if(cid!=null){
			messages.add(danmuDAOMapper.selectByPrimaryKey(cid).toDanmu());
		}
				
		return messages;
	}
	
	public Collection<Danmu> findDanmusByUid(final String uid){
		final Collection<Danmu> messages = new ArrayList<Danmu>();

		if(uid!=null){
			Collection<DanmuDAO> daos = danmuDAOMapper.selectByUid(uid);
			for(DanmuDAO dao : daos){
				messages.add(dao.toDanmu());
			}
		}
				
		return messages;
	}
	
	public Collection<Danmu> findDanmuByBnn(final String bnn){
		final Collection<Danmu> messages = new ArrayList<Danmu>();
			
		if(bnn!=null){
			Collection<DanmuDAO> daos = danmuDAOMapper.selectByBnn(bnn);
			for(DanmuDAO dao : daos){
				messages.add(dao.toDanmu());
			}
		}
				

		return messages;
	}
	
	public Collection<Danmu> findDanmuByLevel(final String level){
		final Collection<Danmu> messages = new ArrayList<Danmu>();

		if(level!=null){
			Collection<DanmuDAO> daos = danmuDAOMapper.selectByLevel(level);
			for(DanmuDAO dao : daos){
				messages.add(dao.toDanmu());
			}
		}

		return messages;
	}
	
	public Map<String, Long> selectTopByBnn(final int limit){
		Collection<Map<String, Object>> results = danmuDAOMapper.selectTopByBnn(limit);
		Map<String, Long> bnnRankMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("bnn") + String.valueOf( (Long)result.get("count")));
			if(result.containsKey("bnn") && result.containsKey("count")){
				bnnRankMap.put((String) result.get("bnn"),(Long) result.get("count"));
			}
		}
		return bnnRankMap;
	}
	
	public Map<String, Long> selectTopByNn(final int limit){
		Collection<Map<String, Object>> results = danmuDAOMapper.selectTopByNn(limit);
		Map<String, Long> bnnRankMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("nn") + String.valueOf( (Long)result.get("count")));
			if(result.containsKey("nn") && result.containsKey("count")){
				bnnRankMap.put((String) result.get("nn"),(Long) result.get("count"));
			}
		}
		return bnnRankMap;
	}
	
	public Map<String, Long> selectByTimeInterval(Date start,Date end,long interval){
		
		Collection<Map<String, Object>> results = danmuDAOMapper.selectByTimeInterval(new Timestamp(start.getTime()), new Timestamp(end.getTime()), interval);
		Map<String, Long> resultMap = new HashMap<String, Long>();
		for(Map<String, Object> result : results){
			Logger.v(result.get("time") + " : "+ String.valueOf( (Long)result.get("count")));
			if(result.containsKey("time") && result.containsKey("count")){
				resultMap.put((String) result.get("time"),(Long) result.get("count"));
			}
		}
		return resultMap;
	}
	
	


}
