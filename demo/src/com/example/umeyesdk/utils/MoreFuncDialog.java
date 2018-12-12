package com.example.umeyesdk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.Player.web.response.ResponseCommon;
import com.Player.web.response.ResponseGetInfomation;
import com.Player.web.websocket.ClientCore;
import com.example.umeyesdk.AppMain;
import com.example.umeyesdk.api.WebSdkApi;
import com.example.umeyesdk.entity.PlayNode;
import com.getui.demo.AlarmUtils;

/**
 * ���๦��
 * 
 * @author Administrator
 * 
 */
public class MoreFuncDialog extends AlertDialog.Builder {
	ClientCore clientCore;
	Activity activity;
	Handler handler;
	public String[] funcArray = { "����豸", "ˢ���б�", "�޸�����", "�����ʼ���������", "��ѯ������¼",	"ɾ�����б���", "ע��" };

	public MoreFuncDialog(Activity arg0, ClientCore clientCore,Handler handler) {
		super(arg0);
		this.activity = arg0;
		this.clientCore = clientCore;
		this.handler = handler;
		setItems();
	}

	public void setItems() {
		setItems(funcArray, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					// ���4·��������dvr
					WebSdkApi.addNodeInfo(activity, clientCore,
							String.valueOf(System.currentTimeMillis()), "", 1, 2, 1009,
							Constants.UMID, "", 0, Constants.user,
							Constants.password, 8, 0, 1, 0, 0, handler);
					break;
				case 1:
					// ���»�ȡ�б�
					WebSdkApi.getNodeList(activity, clientCore, "", 0, 0,handler);
					break;
				case 2:
					// �޸ĵ�¼�û�����
					WebSdkApi.modifyUserPassword(activity, clientCore, "1234","123456");
					break;
				case 3:
					// �����������뵽ע��ʱ���������
					WebSdkApi.resetUserPassword(activity, clientCore, "yin", 2);
					break;
				case 4:
					// ��ѯ������¼
					WebSdkApi.queryAlarmList(clientCore);
					break;
				case 5:
					// ɾ�����б���
					WebSdkApi.deleteAllAlarm(clientCore);
					break;
				case 6:
					// ע��
					WebSdkApi.logoutServer(clientCore, 1);
					activity.finish();
					break;
				default:
					break;
				}
				dialog.dismiss();
			}
		});
	}
}
