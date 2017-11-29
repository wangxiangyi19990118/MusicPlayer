import Httpconnection.DownloadMusic;
import Httpconnection.MusicSheetUploader;
import dao.MusicDao;
import dao.MusicDaoImpl;
import dao.MusicSheetDaoImpl;
import data.Music;
import data.MusicSheet;
import data.MusicSheetToMusic;
import org.json.JSONException;
import service.MusicService;
import service.MusicSheetService;
import service.SokectService;
import sokect.DownloadResource;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static Httpconnection.GetMusicSheet.doGetMethod;
import static Httpconnection.MusicSheetUploader.uploadMusicSheet;

public class Service {
    public static void main(String[] args) throws Exception {
        MusicSheetService musicSheetService=new MusicSheetService();
        //创建歌单 DONE
        musicSheetService.createMusicSheet("Test1", "16020031078", "WangXiangyi", "");
//		musicSheetService.createMusicSheet("歌单测试1", "16020031066", "宿天宇", "");
//		musicSheetService.createMusicSheet("歌单测试1", "16020031066", "宿天宇", "");
        //删除歌单 DONE
        //musicSheetService.deleteMusicSheet(1);


        //测试添加歌曲
        MusicService musicService=new MusicService();
//		System.out.println(musicService.insert("1-3", "/home/sky/netEasyMusic/1-3.mp3", "skyy"));
//		System.out.println(musicService.insert("在他乡", "/home/sky/netEasyMusic/水木年华 - 在他乡.mp3", "sky"));

        //删除歌曲
    //    System.out.println(musicService.delete(1));

        //查询歌曲BY ID/md5
        //System.out.println(musicService.getById(1).getName());
       // System.out.println(musicService.getByMd5Value("0d5c934b24145c1d57f3881e5306a9f4").getName());

        // 添加歌曲到歌单，msid=2；
		//musicSheetService.addMusicsToMusicSheet(1, musicService.getById(2));
        // 从歌单移除歌曲
        //musicSheetService.removeMusicFromMusicSheet(2);

        //获取歌单
//		MusicSheet musicSheet=musicSheetService.getMusicSheet(2);
//		System.out.println(musicSheet.getName());
//		List<MusicSheet> msls=musicSheetService.getMusicSheetsByCreatorId("16020031066");
//		for (MusicSheet musicSheet2 : msls) {
//			System.out.println(musicSheet2.getId());
//		}
        //获取歌单歌曲
//		List<Music> tmpm=musicSheetService.getMusicByMusicSheetId(2);
//		System.out.println(tmpm.size());
//		for (Music m : tmpm) {
//			System.out.println(m.getName());
//		}

        //删除歌单
//		System.out.println(musicSheetService.deleteMusicSheet(3));

        //播放歌单全部本地歌曲//不能实现！！！！！
//		musicSheetService.playAllSongs(2);

        //上传歌单//
		//MusicSheet my= musicSheetService.getMusicSheet(1);
		//my.setPicture("E:\\BaiduNetdiskDownload\\b.jpg");
		//SokectService.uploadMusicSheet(my);
        //SokectService sokectService = new SokectService();

        // 获取歌单
        //List<MusicSheet> online = SokectService.queryMusicSheets();
        // for (MusicSheet ms : online) {
        // System.out.println(ms.getUuid()+"\t"+
        // //ms.getMusicItems()+"\t"+
        // ms.getCreator());
        // }

        // 下载歌曲
        // SokectService.downloadMusic(online.get(0).getMusicItems());

        // 下载图片
        //SokectService.downloadPicture(online.get(3).getPicture());
//		DownloadResource.downloadPicture("2a31e7b1bc9b4b4d8f920c08540c4a00");


        // 播放网络歌单
    //    musicSheetService.playAllSongs(online.get(0).getMusicItems());
    }

}

