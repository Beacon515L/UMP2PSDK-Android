package com.example.umeyesdk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.Player.Source.TDevNodeInfor;
import com.Player.Source.TFileListNode;
import com.Player.web.request.P2pConnectInfo;
import com.Player.web.response.ResponseCommon;
import com.Player.web.response.ResponseQueryAlarmSettings;
import com.Player.web.websocket.ClientCore;
import com.example.umeyesdk.api.WebSdkApi;
import com.example.umeyesdk.entity.PlayNode;
import com.getui.demo.AlarmUtils;

/**
 * ���๦��
 * 
 * @author Administrator
 * 
 */
public class EditDevDialog extends AlertDialog.Builder {
	ClientCore clientCore;
	Activity activity;
	Handler handler;
	public String[] funcArray = { "�޸��豸", "ɾ���豸", "��ѯ����", "���ò���", "��������","�޸�ͨ����" };
	PlayNode node;

	public EditDevDialog(Activity arg0, ClientCore clientCore, PlayNode node,
			Handler handler) {
		super(arg0);
		this.node = node;
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
				TFileListNode tFileListNode = node.node;
				switch (which) {

				case 0:
					// �޸��豸���Ӳ�����ͨ�����ơ��豸�û������롢�������������ͨ����
					WebSdkApi.modifyNodeInfo(activity, clientCore,
							node.node.dwNodeId, "test_modify",
							2, tFileListNode.usVendorId, node.umid, "", 0,
							"admin", "", 0, 1, handler);
					break;
				case 1:
					WebSdkApi.deleteNodeInfo(activity, clientCore,
							String.valueOf(node.node.dwNodeId),
							node.node.ucNodeKind, node.node.id_type, handler);
					break;
				case 2:
					/**
					 * ֻ��ͨ��umid��ӵ��������ͷ������camrea ����dvr �µľ�ͷ ���Խ��в�ѯ���ż�¼
					 */
					if (node.IsDvr() || node.IsDirectory() || tFileListNode.iConnMode != 2) {
						Show.toast(activity, "ֻ�����umid��ipc���豸��ͨ���ſ��Գ���");
						break;
					}
					WebSdkApi.getDeviceAlarm(clientCore, tFileListNode.sDevId, AlarmUtils.GETUI_CID);
					break;
				case 3:
					/**
					 * ֻ��ͨ��umid��ӵ��������ͷ������camrea ����dvr �µľ�ͷ ���Խ��в���
					 */
					if (node.IsDvr() || node.IsDirectory()
							|| tFileListNode.iConnMode != 2) {
						Show.toast(activity, "ֻ�����umid��ipc���豸��ͨ���ſ��Բ���");
						break;
					}
					WebSdkApi.setDeviceAlarm(clientCore, node, 1,  AlarmUtils.GETUI_CID, new int[]{1,2,3,4,5});
					break;
				case 4:
					/**
					 * ֻ��ͨ��umid��ӵ��������ͷ������camrea ����dvr �µľ�ͷ ���Խ��в�����
					 */
					if (node.IsDvr() || node.IsDirectory()
							|| tFileListNode.iConnMode != 2) {
						Show.toast(activity, "ֻ�����umid��ipc���豸��ͨ���ſ��Գ���");
						break;
					}
					WebSdkApi.setDeviceAlarm(clientCore, node, 2,  AlarmUtils.GETUI_CID, new int[]{1,2,3,4,5});
					break;
				case 5:
					/**
					 * �޸��豸ͨ���� 
					 */
					if (node.IsDvr()) {
						WebSdkApi.modifyDevNum(clientCore,
								String.valueOf(node.node.dwNodeId),
								node.node.sDevId, 16, handler);
					}
					break;

				default:
					break;

				}
				dialog.cancel();
			}
		});
	}

}
