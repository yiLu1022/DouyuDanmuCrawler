package douyuDanmu;

/**
 * @Summary: 获取服务器弹幕信息线程
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class KeepGetMsg extends Thread {

	@Override
    public void run()
    {
		////获取弹幕客户端
    	DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
    	
    	//判断客户端就绪状态
        while(danmuClient.getReadyFlag())
        {
        	//获取服务器发送的弹幕信息
        	danmuClient.getServerMsg();;
        }
    }
}
