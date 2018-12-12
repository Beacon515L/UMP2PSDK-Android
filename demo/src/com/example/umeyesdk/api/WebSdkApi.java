package com.example.umeyesdk.api;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.Player.Source.TAlarmSetInfor;
import com.Player.Source.TDevNodeInfor;
import com.Player.web.request.P2pConnectInfo;
import com.Player.web.response.DevState;
import com.Player.web.response.ResponseCommon;
import com.Player.web.response.ResponseDevList;
import com.Player.web.response.ResponseDevState;
import com.Player.web.response.ResponseGetServerList;
import com.Player.web.response.ResponseQueryAlarm;
import com.Player.web.response.ResponseQueryAlarmSettings;
import com.Player.web.response.ResponseResetPwd;
import com.Player.web.response.ServerListInfo;
import com.Player.web.websocket.ClientCore;
import com.example.umeyesdk.entity.PlayNode;
import com.example.umeyesdk.utils.Constants;
import com.example.umeyesdk.utils.Show;
import com.example.umeyesdk.utils.Utility;
import com.getui.demo.AlarmUtils;

import java.util.List;

public class WebSdkApi {
	public static final String WebSdkApi = "WebSdkApi";
	 
    /**
     * �����붨�壺
     * ������������붨��ο�HttpErrorCode
     */
	
	/**
	 * ��½
	 * 
	 * @param pc
	 * @param userName �û�ID ����  24λ , �޶���ĸ�����֣��»���
	 *            ����
	 * @param password
	 *            ����   ���� ����         20λ , �޶���ĸ�����֣��»���
	 **/
	public static void loginServerAtUserId(final Context context,
			final ClientCore clientCore, String userName, String password,
			final Handler handler) {
		clientCore.loginServerAtUserId(context, userName, password,
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						ResponseCommon responseCommon = (ResponseCommon) msg.obj;
						if (responseCommon != null && responseCommon.h != null) {
							if (responseCommon.h.e == 200) {
								handler.sendEmptyMessage(Constants.LOGIN_OK);
							} else if (responseCommon.h.e == 406) {
								handler.sendEmptyMessage(Constants.LOGIN_USER_OR_PWD_ERROR);
							} else {
								Log.e(WebSdkApi, "��¼ʧ��!code=" + responseCommon.h.e);
								handler.sendEmptyMessage(Constants.LOGIN_FAILED);
							}
						} else {
							Log.e(WebSdkApi, "��¼ʧ��! error=" + msg.what);
							handler.sendEmptyMessage(Constants.LOGIN_FAILED);
						}
						super.handleMessage(msg);
					}

				});
	}

	/**
	 * ע���˻�
	 * 
	 * @param pc
	 * @param aUserId
	 *            �û�ID ����  24λ , �޶���ĸ�����֣��»���
	 * @param aPassword
	 *            ���� ����         20λ , �޶���ĸ�����֣��»���
	 * @param user_email
	 *            ���� ����	 32λ , �Ϸ�����	
	 * @param nickName
	 *            �ǳ�                   32λ , �޶�Ӣ�ģ����֣��»���   
	 * @param user_phone
	 *            �ֻ�����         32λ , �޶�����
	 * @param user_id_card
	 *            �û����֤id   24λ
	 */
	public static void registeredUser(final Context context,
			final ClientCore clientCore, String aUserId, String aPassword,
			String user_email, String nickName, String user_phone,
			String user_id_card) {
		clientCore.registeredUser(aUserId, aPassword, user_email, nickName,
				user_phone, user_id_card, new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						ResponseCommon responseCommon = (ResponseCommon) msg.obj;
						if (responseCommon != null && responseCommon.h != null) {
							if (responseCommon.h.e == 200) {
								Show.toast(context, "ע��ɹ�");
							} else {
								Log.e(WebSdkApi, "ע��ʧ��!code=" + responseCommon.h.e);
								Show.toast(context, "ע��ʧ��");
							}

						} else {
							Log.e(WebSdkApi, "ע��ʧ��! error=" + msg.what);
							Show.toast(context, "ע��ʧ��");
						}
						super.handleMessage(msg);
					}

				});
	}

	/**
	 * ע��
	 * 
	 * @param pc
	 * @param disableAlarm
	 *            �Ƿ�ȡ����������
	 * @param handler
	 */
	public static void logoutServer(final ClientCore clientCore, int disableAlarm) {
		clientCore.logoutServer(disableAlarm, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null && responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						Log.e(WebSdkApi, "ע���ɹ�!code=" + responseCommon.h.e);
					} else {
						Log.e(WebSdkApi, "ע��ʧ��!code=" + responseCommon.h.e);
					}

				} else {
					Log.e(WebSdkApi, "ע��ʧ��! error=" + msg.what);
				}
				super.handleMessage(msg);
			}
		});
	}

	/**
	 * �޸�����
	 * 
	 * @param pc
	 * @param oldPassword   20λ , �޶���ĸ�����֣��»���
	 *            ������
	 * @param newPassword   20λ , �޶���ĸ�����֣��»���
	 *            ������
	 */
	public static void modifyUserPassword(final Context context,
			final ClientCore clientCore, String oldPassword, String newPassword) {
		clientCore.modifyUserPassword(oldPassword, newPassword, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null && responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						Show.toast(context, "�޸�����ɹ�");
					} else {
						Log.e(WebSdkApi, "�޸�����ʧ��!code=" + responseCommon.h.e);
						Show.toast(context, "�޸�����ʧ��");
					}
				} else {
					Log.e(WebSdkApi, "�޸�����ʧ��! error=" + msg.what);
					Show.toast(context, "�޸�����ʧ��");
				}
				super.handleMessage(msg);
			}

		});

	}

	/**
	 * �������������ʼ�
	 * 
	 * @param pc
	 * @param user_id
	 *            ��Ҫ����������û���
	 * @param language
	 *            ���ԣ�1��ʾӢ��(English)��2��ʾ��������(SimpChinese)�� 3��ʾ��������(TradChinese)
	 * 
	 */
	public static void resetUserPassword(final Context context,
			final ClientCore clientCore, String user_id, int language) {
		clientCore.resetUserPassword(user_id, language, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseResetPwd responseResetPwd = (ResponseResetPwd) msg.obj;
				if (responseResetPwd != null && responseResetPwd.h != null) {
					if (responseResetPwd.h.e == 200) {
						Show.toast(context, "������������ɹ����Ժ�����գ�");
					} else {
						Log.e(WebSdkApi, "������������ʧ��!code="
								+ responseResetPwd.h.e);
						Show.toast(context, "������������ʧ��");
					}
				} else {
					Log.e(WebSdkApi, "������������ʧ��! error=" + msg.what);
					Show.toast(context, "������������ʧ��");
				}
				super.handleMessage(msg);
			}

		});

	}

	/**
	 * ��ȡ�豸�б�
	 * 
	 * @param pc
	 * @param parent_node_id
	 *            ���ڵ�ID
	 * @param page_index
	 *            ��ҳ���ܣ�ָ���ӵڼ�ҳ��ʼ���ǿ�ѡ�ģ�Ĭ�ϲ���ҳ��
	 * @param page_size
	 *            ��ҳ���ܣ�ÿҳ���صļ�¼�����ǿ�ѡ�ģ�Ĭ�ϲ���ҳ��
	 * @param handler
	 */
	public static void getNodeList(final Context context,
			final ClientCore clientCore, String parent_node_id, int page_index,
			int page_size, final Handler handler) {
		clientCore.getNodeList(parent_node_id, page_index, page_size,
				new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						ResponseDevList responseDevList = (ResponseDevList) msg.obj;
						if (responseDevList != null
								&& responseDevList.h != null) {
							if (responseDevList.h.e == 200) {
								handler.sendMessage(Message.obtain(handler,
										Constants.GET_DEVLIST_S,
										responseDevList));
							} else {
								Log.e(WebSdkApi, "��ȡ�豸�б�ʧ��!code="
										+ responseDevList.h.e);
								handler.sendEmptyMessage(Constants.GET_DEVLIST_F);
							}
						} else {
							Log.e(WebSdkApi, "��ȡ�豸�б�ʧ��! error="
									+ msg.what);
							handler.sendEmptyMessage(Constants.GET_DEVLIST_F);
						}
						super.handleMessage(msg);
					}
				});
	}

	/**
	 * ����豸�ڵ�
	 * 
	 * @param pc
	 * @param node_name    		���� 28 �޶����ģ���ĸ�����֣��»���
	 *            ����
	 * @param parent_node_id	
	 *            ���ڵ�ID
	 * @param node_type
	 *            �ڵ����� : 0��ʾĿ¼�ڵ㡢1��ʾ�豸�ڵ㡢2��ʾ������ڵ㣻��Ϊ����ӽڵ�ʱ��ͨ����Ϊ0
	 * @param conn_mode
	 *            ��node_type��Ϊ0��ʱ���Ǳ���� ����ģʽ: 0��ʾֱ��ģʽ��1��ʾ��ý�������ģʽ��
	 *            2��ʾP2P��ģʽ��3��ʾ����ý��ģʽ��
	 * 
	 * @param vendor_id
	 * 
	 *            ����ID����node_type��Ϊ0��ʱ���Ǳ���ģ�ȡֵ��Χ��
	 *            1001��ʾ������ý��Э�飨bit����1002��ʾ������ý��Э�飨XML����
	 *            1003��ʾ������ý��Э�飨JSON����1004��ʾ������ý��Э�飨OWSP����
	 *            1005��ʾԭ������ý�������Э�飨HKSP����1006��ʾ�ļ������Э�飨FCAM����
	 *            1007��ʾ���Ƽ��ͨѶЭ�飨HMCP����1008��ʾ�����豸����Э�飨HDTP����
	 *            1009��ʾUMSP��1010��ʾEPMY��1011��ʾRTSP��
	 *            1012��ʾHTTP��1013��ʾSIP��1014��ʾRTMP��
	 *            2010��ʾ���ݺ�����2011��ʾ���ݺ�����ģʽ��2020��ʾ���ݴ󻪡�
	 *            2030��ʾ����������2040��ʾ���ڻƺӡ�2050��ʾ���ں��
	 *            2060��ʾ����������2070��ʾ��ɽ������2080��ʾ�ɶ�������
	 *            2090��ʾ�Ϻ��ά��2100��ʾ��������2110��ʾ�Ϻ�ͨ����
	 *            2120��ʾ�����ɳۡ�2130��ʾ����ͨ��2140��ʾ������á�
	 *            2150��ʾ�������ӡ�2160��ʾ���ݼѿɡ�2170��ʾ�����캲�� 2180��ʾ������ӡ�
	 * @param dev_umid          ����28 �޶���ĸ�����֣��»���
	 *            �豸umid   
	 * @param dev_addr
	 *            �豸IP��������   ����127
	 * @param dev_port
	 *            �豸�˿�                  ����10
	 * @param dev_user
	 *            �豸�û���   ����60 ������ĸ�����֣��»���
	 * @param dev_passwd 
	 *            �豸����        ����60 ������ĸ�����֣��»���
 	 * @param dev_ch_num
	 *            �豸ͨ���� dvr/nvrͨ����
	 * @param dev_ch_no
	 *            �豸ͨ���� node_typeΪ2ʱ��Ч�����dvr/nvrָ����ͨ����
	 * @param dev_stream_no
	 *            �豸�������� 0:��������1��������
	 * @param only_umid
	 *            �豸UMID�Ƿ���Ψһ���
	 *@param limit_appid
	 *            �Ƿ�ֻ����APP���ҵ��豸�������
	 * @param handler
	 */
	public static void addNodeInfo(final Context context,
			final ClientCore clientCore, String node_name, String parent_node_id,
			int node_type, int conn_mode, int vendor_id, String dev_umid,
			String dev_addr, int dev_port, String dev_user, String dev_passwd,
			int dev_ch_num, int dev_ch_no, int dev_stream_no, int only_umid,int limit_appid,
								   final Handler handler) {
		clientCore.addNodeInfo(node_name, parent_node_id, node_type, conn_mode,
				vendor_id, dev_umid, dev_addr, dev_port, dev_user, dev_passwd,
				dev_ch_num, dev_ch_no, dev_stream_no, only_umid, limit_appid, new Handler() {

					@Override
					public void handleMessage(Message msg) {
						ResponseCommon responseCommon = (ResponseCommon) msg.obj;
						if (responseCommon != null && responseCommon.h != null) {
							if (responseCommon.h.e == 200) {
								handler.sendEmptyMessage(Constants.ADD_DEV_S);
							} else {
								Log.e(WebSdkApi, "����豸ʧ��!code="	+ responseCommon.h.e);
								handler.sendEmptyMessage(Constants.ADD_DEV_F);
							}
						} else {
							Log.e(WebSdkApi, "����豸ʧ��! error=" + msg.what);
							handler.sendEmptyMessage(Constants.ADD_DEV_F);
						}
						super.handleMessage(msg);
					}

				});

	}

	/**
	 * ɾ���豸
	 * 
	 * @param pc
	 * @param node_id
	 *            �ڵ�ID
	 * @parm node_type
	 * 			      �ڵ�����
	 * @param id_type 
	 * 			  ID���ͣ�0���û�id,1:�豸��id,2:��Ȩ�豸��Ĭ����д0
	 * @param handler
	 */
	public static void deleteNodeInfo(final Context context,
			final ClientCore clientCore, String node_id, int node_type,
			int id_type, final Handler handler) {
		clientCore.deleteNodeInfo(node_id, node_type, id_type, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null && responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						handler.sendEmptyMessage(Constants.DELETE_DEV_S);
					} else {
						Log.e(WebSdkApi, " ɾ���豸�豸ʧ��!code="
								+ responseCommon.h.e);
						handler.sendEmptyMessage(Constants.DELETE_DEV_F);
					}
				} else {
					Log.e(WebSdkApi, " ɾ���豸�豸ʧ��! error=" + msg.what);
					handler.sendEmptyMessage(Constants.DELETE_DEV_F);
				}
				super.handleMessage(msg);
			}

		});

	}






	/**
	 * ɾ���豸����
	 *
	 * @param node_ids
	 *            �ڵ�ID
	 * @parm node_type
	 * 			      �ڵ�����
	 * @param id_type
	 * 			  ID���ͣ�0���û�id,1:�豸��id,2:��Ȩ�豸��Ĭ����д0
	 * @param handler
	 */
	public static void deleteNodeInfos(final Context context,
									  final ClientCore clientCore, String[] node_ids, int node_type,
									  int id_type, final Handler handler) {
		clientCore.deleteNodeInfos(node_ids, node_type, id_type, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null && responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						handler.sendEmptyMessage(Constants.DELETE_DEV_S);
					} else {
						Log.e(WebSdkApi, " ɾ���豸�豸����ʧ��!code="
								+ responseCommon.h.e);
						handler.sendEmptyMessage(Constants.DELETE_DEV_F);
					}
				} else {
					Log.e(WebSdkApi, " ɾ���豸�豸����ʧ��! error=" + msg.what);
					handler.sendEmptyMessage(Constants.DELETE_DEV_F);
				}
				super.handleMessage(msg);
			}

		});

	}






	/**
	 * �޸��豸
	 * 
	 * @param pc
	 * @param node_id
	 *            �ڵ�ID
	 * @param node_name       28λ �޶����ģ���ĸ�����֣��»���
	 *            ����
	 * @param dev_umid
	 *            �豸umid    28λ �޶���ĸ�����֣��»���
	 * @param dev_addr
	 *            �豸IP��ַ/���� 
	 * @param dev_port
	 *            �豸�˿�
	 * @param dev_user
	 *            �豸�û���     60λ �޶���ĸ�����֣��»��� 
	 * @param dev_passwd
	 *            �豸����          
	 * @param dev_ch_no
	 *            ������ڵ����Ч�� nvr/dvr��ͨ����
	 * @param dev_stream_no
	 *            ����
	 * @param handler
	 */
	public static void modifyNodeInfo(final Context context,
			final ClientCore clientCore, String node_id, String node_name,
			int node_type, int vendor_id, String dev_umid, String dev_addr,
			int dev_port, String dev_user, String dev_passwd, int dev_ch_no,
			int dev_stream_no, final Handler handler) {
		clientCore.modifyNodeInfo(node_id, node_name, node_type, 0, vendor_id,
				dev_umid, dev_addr, dev_port, dev_user, dev_passwd, dev_ch_no,
				dev_stream_no, new Handler() {

					@Override
					public void handleMessage(Message msg) {
						ResponseCommon responseCommon = (ResponseCommon) msg.obj;
						if (responseCommon != null && responseCommon.h != null) {
							if (responseCommon.h.e == 200) {
								handler.sendEmptyMessage(Constants.MODIFY_DEV_S);
							} else {
								Log.e(WebSdkApi, " �޸��豸�豸ʧ��!code="	+ responseCommon.h.e);
								handler.sendEmptyMessage(Constants.MODIFY_DEV_F);
							}
						} else {
							Log.e(WebSdkApi, " �޸��豸�豸ʧ��! error="	+ msg.what);
							handler.sendEmptyMessage(Constants.MODIFY_DEV_F);
						}
						super.handleMessage(msg);
					}
				});

	}

	/**
	 * �����豸id��ѯ�豸����״̬
	 * @param devs �豸umid�б�
	 */
     public static void getDeviceStateByUmid(ClientCore clientCore,List<String> devs){
		clientCore.getDevState(devs, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseDevState responseDevState = (ResponseDevState) msg.obj;
				if (responseDevState != null
						&& responseDevState.h != null
						&& responseDevState.h.e == 200
						&& responseDevState.b != null
						&& responseDevState.b.devs != null) {
					List<DevState> devStates = responseDevState.b.devs;
					for(int i = 0; i < devStates.size();i++){
						Log.i("state",devStates.get(i).dev_id + devStates.get(i).state);
					}
				}else{
					Log.i("state","get state fail");
				}
				super.handleMessage(msg);
			}
		});
     }
	
	
	/**
	 * ��ѯ������¼
	 */
	public static void queryAlarmList(ClientCore clientCore) {
		clientCore.queryAlarmList(new Handler() {

			@Override
			public void handleMessage(Message msg) {
				ResponseQueryAlarm responseQueryAlarm = (ResponseQueryAlarm) msg.obj;
				if (responseQueryAlarm != null && responseQueryAlarm.h != null) {
					if (responseQueryAlarm.h.e == 200) {
						if (responseQueryAlarm.b != null
								&& responseQueryAlarm.b.alarms != null) {
							for (int i = 0; i < responseQueryAlarm.b.alarms.length; i++) {
								Log.i("alarms", responseQueryAlarm.b.alarms[i].toString());
							}
							Log.e(WebSdkApi, "��ѯ�����ɹ�");
						} else {
							Log.e(WebSdkApi, "��������");
						}

					} else {
						Log.e(WebSdkApi, " ��ѯ����ʧ��!code="+ responseQueryAlarm.h.e);
					}
				} else {
					Log.e(WebSdkApi, " ��ѯ����ʧ��! error=" + msg.what);
				}
				super.handleMessage(msg);
			}

		});
	}
	
	/**
	 * ɾ�����б�����¼
	 */

	public static void deleteAllAlarm(ClientCore clientCore){
		clientCore.deleteAllAlarm(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null
						&& responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						Log.i(WebSdkApi, "ɾ��������Ϣ�ɹ���");
					} else {
						Log.e(WebSdkApi,"ɾ�����б�����Ϣʧ��!code="	+ responseCommon.h.e);
					}
				} else {
					Log.e(WebSdkApi,	"ɾ�����б�����Ϣʧ��! error=" + msg.what);
				}
				super.handleMessage(msg);
			}
		});
	}
	
	/**
	 * ɾ��ָ��id������¼
	 * @param alarms_ids alarmid ����
	 */

	public static void deleteAlarmByIds(ClientCore clientCore,String[] alarm_ids){
		clientCore.deleteAlarm(alarm_ids,new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null
						&& responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						Log.i(WebSdkApi, "ɾ��������Ϣ�ɹ���");
					} else {
						Log.e(WebSdkApi,"ɾ�����б�����Ϣʧ��!code="	+ responseCommon.h.e);
					}
				} else {
					Log.e(WebSdkApi,	"ɾ�����б�����Ϣʧ��! error=" + msg.what);
				}
				super.handleMessage(msg);
			}
		});
	}
	/**
	 * ���ݿͻ��˶��Ʊ�ʶ��ȡ�������б�
	 * 
	 * @param pc
	 */
	public static void getServerList(final Context context,
			final ClientCore clientCore) {
		clientCore.getServerList(new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				final ResponseGetServerList responseGetServerList = (ResponseGetServerList) msg.obj;
				if (responseGetServerList != null
						&& responseGetServerList.h != null) {
					if (responseGetServerList.h.e == 200
							&& responseGetServerList.b.srvs != null) {
						ServerListInfo serverListInfos[] = responseGetServerList.b.srvs;
						for (int i = 0; i < serverListInfos.length; i++) {
							Log.d("ServerListInfo",	"" + serverListInfos[i].toString());
						}
					} else {
						Log.e(WebSdkApi, "��ȡ�������б�ʧ��! code="
								+ responseGetServerList.h.e);
					}
				} else {
					Log.e(WebSdkApi, "��ȡ�������б�ʧ��! error=" + msg.what);
				}

				super.handleMessage(msg);
			}

		});
	}

	/**
	 * �޸��豸ͨ����
	 * @param node_id �ڵ�id
	 * @param dev_id  �豸id��playNode.node.sDevId ���� DevItemInfo.dev_id
	 * @param dev_ch_num �豸ͨ����
	 */
	
	public static void modifyDevNum(ClientCore clientCore, String node_id,
			String dev_id, int dev_ch_num, final Handler handler) {
		clientCore.modifyDevNum(node_id, dev_id, dev_ch_num, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				ResponseCommon responseCommon = (ResponseCommon) msg.obj;
				if (responseCommon != null && responseCommon.h != null) {
					if (responseCommon.h.e == 200) {
						handler.sendEmptyMessage(Constants.MODIFY_DEV_NUM_S);
					} else {
						Log.e(WebSdkApi, " �޸��豸ͨ����ʧ��!code=" + responseCommon.h.e);
						handler.sendEmptyMessage(Constants.MODIFY_DEV_NUM_F);
					}
				} else {
					Log.e(WebSdkApi, " �޸��豸ͨ����! error=" + msg.what);
					handler.sendEmptyMessage(Constants.MODIFY_DEV_NUM_F);
				}
				super.handleMessage(msg);
			}
		});
	}
	
	/**
	 * �����û����� 
	 * @param enable_push�����û�������ͣ�1 ��ʾ���ã�0 ��ʾ���ã��Ǳ���ģ�
	 * @param client_lang �ͻ������ԣ��μ����Ժ궨��
	 * @param client_token ���Ƹ�����clientId
	 * @param disable_push_other_users
	 *            �Ƿ��ֹ����ͬ�ͻ���ID ��ͻ���Token�������û��������ͣ�1��ʾ��ֹ��0��ʾ����
	 * 
	 * @param unread_count
	 *            �����͵�δ����¼������Ϊָ����ֵ��Ĭ����0
	 */
	public static void setUserPush(ClientCore clientCore,int enable_push, int client_lang,
			String client_token, int disable_push_other_users,int unread_count){
		clientCore.setUserPush(enable_push, client_lang,client_token, disable_push_other_users, unread_count, new Handler() {
					@Override
					public void handleMessage(Message msg) {
						ResponseCommon responseCommon = (ResponseCommon) msg.obj;
						if (responseCommon != null && responseCommon.h != null && responseCommon.h.e == 200) {
							Log.i("setUserPush", "�����û����ͳɹ�");
						} else {
							Log.i("setUserPush", "�����û�����ʧ��");
						}
					}
				});
	}
	
	/**
	 * �豸�˷��������в��� ����Ҫ�������½ģʽ�·�����������
	 * @param opCode        �����룬1 ��ʾ����/����֪ͨ��2 ��ʾ������3 ��ʾȡ������֪ͨ
	 * @param client_token ����clientId
	 * @param alarm_events �����¼��������飬�ο� 
	 * @param devName      �豸����
	 * @param devUmid      �豸umid
	 * @param devUser      �豸�û���
	 * @param devPassword  �豸����
	 * @param iChNo        �豸ͨ���� 
	 */
	
	public static void setDeviceAlarm(ClientCore clientCore,final int opCode,String client_token,int [] alarm_events,
			String devName, String devUmid, String devUser, String devPassword,
			int iChNo){
		P2pConnectInfo p2pConnectInfo = createConnectInfo1(clientCore, devName,devUmid, devUser, devPassword, iChNo);
		P2pConnectInfo[] p2pConnectInfos = { p2pConnectInfo };
		clientCore.alarmSettings(p2pConnectInfos);
		clientCore.alarmSettings(opCode, client_token, alarm_events,
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						ResponseCommon commonSocketText = (ResponseCommon) msg.obj;
						if (commonSocketText != null
								&& commonSocketText.h.e == 200) {
							if (opCode == 1) {
								Log.i(WebSdkApi, "�����ɹ�");
							} else if (opCode == 2) {
								Log.i(WebSdkApi, "�����ɹ�");
							}
						} else {
							if (opCode == 1) {
								Log.i(WebSdkApi, "����ʧ��");
							} else if (opCode == 2) {
								Log.i(WebSdkApi, "����ʧ��");
							}
						}
					}
				}, "");
		
	}
	
	/**
	 * �豸�˷��������в���(�����ڵ�¼ģʽ�½��з���������)
	 * @param playNode      ���Žڵ�����
	 * @param opCode        Ϊ1ʱ���� Ϊ2ʱ���� Ϊ4���������豸����
	 * @param client_token  ���Ƶ�client_id
	 * @param alarm_events  �����¼����ͣ��μ��������ͺ궨��
	 */
	public static void setDeviceAlarm(ClientCore clientCore, PlayNode node, final int opCode,String client_token,int [] alarm_events) {
		P2pConnectInfo p2pConnectInfo = createConnectInfo(clientCore, node);
		P2pConnectInfo[] p2pConnectInfos = { p2pConnectInfo };
		clientCore.alarmSettings(p2pConnectInfos);//���½���ṩ�豸����
		clientCore.alarmSettings(opCode, client_token, alarm_events,
				new Handler() {

					@Override
					public void handleMessage(Message msg) {
						ResponseCommon commonSocketText = (ResponseCommon) msg.obj;
						if (commonSocketText != null
								&& commonSocketText.h.e == 200) {
							if (opCode == 1) {
								Log.i(WebSdkApi, "�����ɹ�");
							} else if (opCode == 2) {
								Log.i(WebSdkApi, "�����ɹ�");
							}
						} else {
							if (opCode == 1) {
								Log.i(WebSdkApi, "����ʧ��");
							} else if (opCode == 2) {
								Log.i(WebSdkApi, "����ʧ��");
							}
						}
					}
				}, node.node.sDevId);
	}
	
	/**
	 * ��ѯ�豸����״̬
	 * @param sDevId �豸id playNode.node.sdevId  ���� devItemInfo.dev_id
	 * @param client_token ����clientId
	 */
	public static void getDeviceAlarm(ClientCore clientCore,String sDevId,final String client_token){
		clientCore.queryAlarmSettings(sDevId, new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ResponseQueryAlarmSettings responseQueryAlarmSettings = (ResponseQueryAlarmSettings) msg.obj;
				if (responseQueryAlarmSettings != null
						&& responseQueryAlarmSettings.h != null) {
					if (responseQueryAlarmSettings.h.e == 200
							&& responseQueryAlarmSettings.b != null
							&& responseQueryAlarmSettings.b.devs != null
							&& responseQueryAlarmSettings.b.devs.length > 0) {
						TAlarmSetInfor alarmInfo = TAlarmSetInfor.toTAlarmSetInfor(responseQueryAlarmSettings.b.devs[0]);
						boolean isNotifyToPhone = false;
						if (alarmInfo.bIfSetAlarm == 1) {							
								if (alarmInfo != null) {
									if (alarmInfo != null && alarmInfo.notifies != null) {
										for (int i = 0; i < alarmInfo.notifies.length; i++) {
											Log.w("alarmInfo", alarmInfo.notifies[i].notify_type + "," + alarmInfo.notifies[i].notify_param);
											if (alarmInfo.notifies[i].notify_type == 1 && client_token.equals(alarmInfo.notifies[i].notify_param)) {
												isNotifyToPhone = true;
												break;
											}
										}
									}
								}
						} else {
							isNotifyToPhone = false;
						}
						if(isNotifyToPhone){
							Log.e(WebSdkApi, "�豸��������������");
						}else{
							Log.e(WebSdkApi, "�豸û����������");
						}
					} else {
						Log.e("queryAlarmSettings", "��ѯ��������ʧ��!code=" + responseQueryAlarmSettings.h.e);
					}
				} else {
					Log.e("queryAlarmSettings", "��ѯ��������ʧ��! error=" + msg.what);
				}
				super.handleMessage(msg);
			}
		});
	}
	
	/**
	 * umidֱ�� �豸ͨ������
	 * 
	 * @param clientCore
	 * @param node
	 * @return
	 */
	public static P2pConnectInfo createConnectInfo1(ClientCore clientCore,
			String devName, String devUmid, String devUser, String devPassword,
			int iChNo) {
		P2pConnectInfo p2pConnectInfo = new P2pConnectInfo();

		p2pConnectInfo = new P2pConnectInfo();
		p2pConnectInfo.umid = devUmid;
		p2pConnectInfo.user = devUser;
		p2pConnectInfo.passwd = devPassword;
		// �����豸������umid ��ͨ������ɣ����Զ���
		p2pConnectInfo.dev_name = devName;
		String sDevId = clientCore.encryptDevId("", devUmid, iChNo);
		p2pConnectInfo.dev_id = sDevId;
		p2pConnectInfo.channel = iChNo;
		return p2pConnectInfo;
	}
	
	/**
	 * ���¼���� �豸ͨ������
	 * 
	 * @param clientCore
	 * @param node
	 * @return
	 */
	public static P2pConnectInfo createConnectInfo(ClientCore clientCore,
			PlayNode node) {
		P2pConnectInfo p2pConnectInfo = new P2pConnectInfo();
		if (node != null) {
			// �������Ӳ���
			TDevNodeInfor info = TDevNodeInfor.changeToTDevNodeInfor(
					node.getDeviceId(), node.node.iConnMode);
			if (info != null) {
				p2pConnectInfo = new P2pConnectInfo();
				p2pConnectInfo.umid = info.pDevId;
				p2pConnectInfo.user = info.pDevUser;
				p2pConnectInfo.passwd = info.pDevPwd;
				p2pConnectInfo.dev_name = node.getName();

				/**
				 * ���½ģʽ�� node.node.sDevId�� String sDevId
				 * =clientCore.encryptDevId
				 * (String.valueOf(node.node.dwNodeId),info.pDevId, info.iChNo);
				 */
				String sDevId = node.node.sDevId;
				p2pConnectInfo.dev_id = sDevId;
				p2pConnectInfo.channel = info.iChNo;
			}
		}
		return p2pConnectInfo;

	}
}
