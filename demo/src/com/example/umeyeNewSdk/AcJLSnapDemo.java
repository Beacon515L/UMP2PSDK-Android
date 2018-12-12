package com.example.umeyeNewSdk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.Player.Core.PlayerClient;
import com.Player.Core.PlayerCore;
import com.Player.Core.CoustomFun.Entity.CFResponse;
import com.Player.Core.UserImg.UserImgEntity.AddImgStruct;
import com.Player.Core.UserImg.UserImgEntity.DelImgStruct;
import com.Player.Core.UserImg.UserImgEntity.UserImgCompareInfo;
import com.Player.Core.UserImg.UserImgEntity.UserImgSnapImgInfo;
import com.Player.Core.UserImg.UserImgEntity.UserImgStruct;
import com.Player.Source.OnP2PDataListener;
import com.example.umeyesdk.R;
import com.example.umeyesdk.entity.PlayNode;
import com.igexin.sdk.PushManager;
import com.Player.Core.Utils.CommenUtil;
import java.util.ArrayList;

public class AcJLSnapDemo extends Activity{
	private Button mBack;
	private String conn_parm = "VendorId=1009,DevId=uvks026d9sux,DevIp=,DevPort=0,DevUserName=admin,DevUserPwd=123,DevChNo=0,DevStreamNo=1";//�˲���ΪplayNode��connparm��������Ϊ��ʾ��ָ���̶�����
	//Ҳ���ɸ���������ָ����umspЭ�� VendorId=1009 �������豸Ϊ2060
	private ImageView imgDown,imgCompare,imgSnap;
	private ImageView videoImg;
	public static final int  NPC_D_UMSP_CUSTOM_FUNCID_CMP_DATA = 65541;//�Ա�����
	public static final int  NPC_D_UMSP_CUSTOM_FUNCID_CAP_JPG = 65542;//�ϴ�ʵʱץ��ͼƬ
	public static final int  REQUEST_WHITE_USER = 2;//������
	public static final int  REQUEST_BLACK_USER = 1;//������
	public static final int  REQUEST_NO_WHITE_BLACK_USER = 3;//�Ǻڰ�����

	//��Ӵ��������
	public static final int	 WB_OK = 1;                                 //ͼƬ��ӳɹ�
	public static final int	 WB_FAILE = -1;	                            //ͼƬ���ʧ��
	public static final int	 WB_COLLECT_ERROR = -2;                     //ͼƬ��ȡ����ֵʧ��
	public static final int	 WB_FILEINDEX_ERROR = -3;                   //ͼƬ���ƻ����ظ�
	public static final int  WB_LIB_FULL = -4;                          //�������������޷����
	public static final int	 WB_ADD_TIME_OUT = -5;                      //ͼƬ��ӳ�ʱ
	public static final int	 WB_PARA_ERROR	= -6;                       //��������
	public static final int	 WB_FILE_BIG		= -7;                	//ͼƬ�������ʧ�ܣ�����960*960��
	public static final int	 WB_SPACE_ERROR	= -8;                       //�豸�洢�ռ䲻��
	public static final int	 WB_FILE_OPEN_ERROR	= -9;                   //�ļ���ʧ��
	public static final int	 WB_NO_DBFILE	= -10;                      //δ��⵽���ݿ�
	public static final int	 WB_FILE_ERROR	= -11;                      //ͼƬ��ȡʧ��
	public static final int	 WB_DBFILE_BAD	= -12;                      //���ݿ��ļ���
	public static final int	 WB_PIC_QUALITY_ERROR = -13;                //ͼƬ������޷����
	public static final int	 WB_FILE_WHSIZE_ERROR = -14;                //ͼƬ��߲���Ϊ����
	public static final int	 WB_FILE_FACE_ERROR	 = -15;                 //�������ʧ�ܣ������������������
	public static final int	 WB_PIC_FORMAT_ERROR = -16;                 //ͼƬ��ʽ����֧��JPG��
	
	//�������Ͷ���
	public int NPC_D_MON_CSD_ALARM_EVENT_BLACK_MODE			=			22;              //����������
	public int NPC_D_MON_CSD_ALARM_EVENT_WHILE_MODE			=			23;              //����������
	public int NPC_D_MON_CSD_ALARM_EVENT_NON_WHILE_MODE		=			24;              //�ǰ���������
	
	//�������ù���id����
	public static final int JL_JSON_SET_CONFIG = 65790; // ��������
	public static final int JL_JSON_GET_CONFIG = 65791;	// ��ȡ����
	
	private PlayerCore player;
	private PlayerClient pc;
	private String TAG = "img_function";
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch(msg.what){
				case 0x11:
					imgDown.setImageBitmap((Bitmap) msg.obj);
					break;
				case 0x12:
					imgCompare.setImageBitmap((Bitmap)msg.obj);
					break;
				case 0x13:
					imgSnap.setImageBitmap((Bitmap) msg.obj);
					break;
			}
			return false;
		}
	});
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_img_test);
		mBack = (Button) findViewById(R.id.back_btn);
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		imgDown = (ImageView) findViewById(R.id.test_get_img);
		imgSnap = (ImageView) findViewById(R.id.test_snap_img);
		imgCompare = (ImageView) findViewById(R.id.test_compare_img);
		videoImg = (ImageView) findViewById(R.id.video_img);
		player = new PlayerCore(AcJLSnapDemo.this);
		player.setP2pPortDataCallback(new OnP2PDataListener() {//���ûص�����
			@Override
			public void callBackData(int camrea, int function, int pData, int size) {
				Log.i(TAG,"camrea->" + camrea +  " function->" + function + " size is->"  + size);
				switch (function){
					case NPC_D_UMSP_CUSTOM_FUNCID_CMP_DATA://ת�Ա����ݽṹ��
						UserImgCompareInfo userImgCompareInfo = pc.JLImageCompareInfo(pData,size);
						if(userImgCompareInfo != null){
							Log.i(TAG,"compare change success");
							Bitmap bitmap = CommenUtil.getBitmapFromByte(userImgCompareInfo.i_snapImg);
							if(bitmap != null){
								Message message = new Message();
								message.what = 0x12;
								message.obj = bitmap;
								handler.sendMessage(message);
							}
						}
						break;
					case NPC_D_UMSP_CUSTOM_FUNCID_CAP_JPG://תץ��ͼƬ�ṹ��
						UserImgSnapImgInfo userImgSnapImgInfo = pc.JLImageSnapInfo(pData,size);
						if(userImgSnapImgInfo != null){
							Log.i(TAG,"snap info change success");
							Bitmap bitmap = CommenUtil.getBitmapFromByte(userImgSnapImgInfo.i_faceImg);
							if(bitmap != null){
								Message message = new Message();
								message.what = 0x13;
								message.obj = bitmap;
								handler.sendMessage(message);
							}
						}
						break;
				}
			}
		});
		player.InitParam(conn_parm, -1, videoImg);
		player.Play();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				pc = new PlayerClient();
				int ret = pc.CameraConnect(conn_parm);//�����豸
				if(ret == 1){//�豸���ӳɹ�
					ArrayList<UserImgStruct> list =  pc.getAllUserImg(conn_parm,REQUEST_WHITE_USER);//������
					if(list != null && list.size() > 0){
						Log.i(TAG,list.size() + "");
						UserImgStruct userImgStruct = list.get(0);
						userImgStruct.iBWMode = REQUEST_WHITE_USER;//ԭʼ���ݽṹ����δ�����͸�ֵ���ֶ���ֵ
						Bitmap bitmap = pc.getUserImgByIndex(conn_parm,userImgStruct);
						if(bitmap != null){
							Message message = new Message();
							message.what = 0x11;
							message.obj = bitmap;
							handler.sendMessage(message);
						}

						DelImgStruct delImgStruct = new DelImgStruct();
						delImgStruct.i_iBWMode = list.get(0).iBWMode;
						delImgStruct.i_iFileIndex = list.get(0).iFileIndex;
						delImgStruct.i_iLibIndex = list.get(0).iLibIndex;
						delImgStruct.i_sFileName = list.get(0).sFileName;
						int delRet = pc.deleteUserImg(conn_parm,delImgStruct);
						Log.i("img_function_del_ret",delRet + "");
						ArrayList<UserImgStruct> listDel =  pc.getAllUserImg(conn_parm,REQUEST_WHITE_USER);
						if(list != null){
							Log.i("img_function",listDel.size() + "");
						}
					}
					AddImgStruct addImgStruct = new AddImgStruct();
					addImgStruct.i_iCtrlType = 0;//�ͻ����·�
					addImgStruct.i_iBWMode = REQUEST_WHITE_USER;//������
					addImgStruct.i_sImgId = "sadsdasa";
					addImgStruct.i_sImgName = "android����05";
					int addRet = pc.addUserImg(conn_parm,addImgStruct,"/sdcard/android_test.jpg");//���ͼƬ�϶�Ҫ��,�ο�������
					Log.i(TAG,addRet + "");//�Աȴ�����
					ArrayList<UserImgStruct> listadd =  pc.getAllUserImg(conn_parm,REQUEST_WHITE_USER);//���»�ȡ
					if(listadd != null){
						Log.i(TAG,listadd.size() + "");
					}
					
					//�������ã��豸���������ô˽ӿڣ��������ݲο������豸ͨѶЭ��
					CFResponse cfResponse = pc.CallCustomFuncExExHaveConnect(conn_parm, JL_JSON_GET_CONFIG,new String("{\"Name\":\"SmartFace.Param\"}").getBytes());//�����Զ���json����
					if(cfResponse.ret == 0){
						Log.i(TAG,"��ȡ�ɹ�");
						Log.i(TAG,cfResponse.responseJson);
					}else{
						Log.i(TAG,"��ȡʧ��");
					}
					
					//���������������ο���������ģ�飬ֻ�Ǵ��ڱ������͵ĸı�
			        pc.CameraDisconnect();//�Ͽ��豸
				}else{//�豸����ʧ��
					Log.i(TAG,"�豸���ߣ�����ʧ��!");
				}
			}
		});
		t.start();
	}
	
}
