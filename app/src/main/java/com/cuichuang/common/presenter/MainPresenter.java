package com.cuichuang.common.presenter;


import com.cuichuang.common.api.ApiRetrofit;
import com.cuichuang.common.api.ApiServer;
import com.cuichuang.common.base.BaseObserver;
import com.cuichuang.common.base.BasePresenter;
import com.cuichuang.common.base.FileObserver;
import com.cuichuang.common.base.myApplication;
import com.cuichuang.common.base.progress.ProgressRequestBody;
import com.cuichuang.common.base.view.BaseModel;
import com.cuichuang.common.base.view.MainBean;
import com.cuichuang.common.base.view.MainView;
import com.cuichuang.common.bean.LoginBean;
import com.cuichuang.common.bean.TextBean;
import com.cuichuang.common.util.AES64;
import com.cuichuang.common.util.Consts;
import com.cuichuang.common.util.RetrofitUtil;
import com.cuichuang.common.util.ToastUtils;
import com.cuichuang.common.util.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * File descripition:
 *
 * @author CC
 * @date 2018/6/19
 */

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView baseView) {
        super(baseView);
    }


    /**
     * 客户标签的筛选项
     */
//    public void getTableListApi() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("token", "032cc080947549c83c3296026b5963a2");
//        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance("http://selfec.qhzx.online/").getApiService();
//        addDisposable(apiServer.getTableList(params), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onTableListSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 当前城市限行
     */
//    public void getRestrictionsApi() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("city_code", "131");
//        params.put("day", "20190802");
//        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance("http://www.qichexiaobaomu.com/").getApiService();
//        addDisposable(apiServer.getRestrictions(params), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onLoginSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }


    /**
     * 登录接口
     */
    public void getLoginInfo() {
        String strUser;
        String strPWD;
        try {
            strUser = new AES64().encrypt("xuming");
            strPWD = new AES64().encrypt("123456");
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("account", strUser);
        params.put("upwd", strPWD);
        params.put("atoken", String.valueOf(Utils.getToken(myApplication.getContext())));
        params.put("versionno", String.valueOf(Utils.getVersionCode(myApplication.getContext())));
        params.put("versioncode", String.valueOf(Utils.getVersionName(myApplication.getContext())));
        params.put("sys", String.valueOf("1"));
        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance(Consts.BASE_URL).getApiService();
        addDisposable(apiServer.getLoginInfo(params), new BaseObserver(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.onLoginSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }


    /**
     * 测试
     */
//    public void getCheShiApi() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("type", "1");
//        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance("http://www.energy-link.com.cn/").getApiService();
//        addDisposable(apiServer.getCeShi(params), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onCheShiSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }


    /**
     * 写法好多种  怎么顺手怎么来
     */
//    public void getManApi() {
//        addDisposable(apiServer.getMain("year"), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onMainSuccess((BaseModel<List<MainBean>>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 写法好多种  怎么顺手怎么来
     */
//    public void getMan2Api() {
//        addDisposable(apiServer.getMain2("year"), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onMainSuccess((BaseModel<List<MainBean>>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 写法好多种  怎么顺手怎么来
     */
//    public void getMan3Api() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("time", "year");
//        addDisposable(apiServer.getMain3(params), new BaseObserver(baseView) {
//
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onMainSuccess((BaseModel<List<MainBean>>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 写法好多种  怎么顺手怎么来
     */
//    public void getTextApi() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("type", "junshi");
//        params.put("key", "2c1cb93f8c7430a754bc3ad62e0fac06");
//        addDisposable(apiServer.getText(params), new BaseObserver(baseView) {
//
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onTextSuccess((BaseModel<TextBean>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  图片上传  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    /**
     * 演示单图上传
     *
     * @param parts
     */
//    public void upLoadImgApi(MultipartBody.Part parts) {
//        addDisposable(apiServer.upLoadImg(parts), new BaseObserver(baseView) {
//
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onUpLoadImgSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }


    /**
     * 演示多图上传
     *
     * @param parts
     */
//    public void upLoadImgApi(List<MultipartBody.Part> parts) {
//        addDisposable(apiServer.upHeadImg(parts), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onUpLoadImgSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 演示 图片和字段一起上传
     *
     * @param title
     * @param content
     * @param parts
     */
//    public void upLoadImgApi(String title, String content, List<MultipartBody.Part> parts) {
//        HashMap<String, RequestBody> params = new HashMap<>();
//        params.put("title", RetrofitUtil.convertToRequestBody(title));
//        params.put("content", RetrofitUtil.convertToRequestBody(content));
//        addDisposable(apiServer.expressAdd(params, parts), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onUpLoadImgSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 演示 文件上传进度监听
     *
     * @param url
     */
//    public void upLoadVideoApi(String url) {
//        HashMap<String, RequestBody> params = new HashMap<>();
//        params.put("fileType", RetrofitUtil.convertToRequestBody("video"));
//
//        MultipartBody.Part parts = MultipartBody.Part.createFormData("file", new File(url).getName(), new ProgressRequestBody(new File(url),"video/mpeg", baseView));
//
//        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance("https://bjlzbt.com/").getApiService();
//        addFileDisposable(apiServer.getUpload(params, parts), new FileObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.onUpLoadImgSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 单个文件上传
     *
     * @param parts 文件流
     * @return
     */
//    public void getUploadApi(HashMap<String, RequestBody> params, MultipartBody.Part parts) {
//        ApiServer apiServer = ApiRetrofit.getBaseUrlInstance("https://bjlzbt.com/").getApiService();
//        addDisposable(apiServer.getUpload(params, parts), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(BaseModel o) {
//                baseView.onUpLoadImgSuccess((BaseModel<Object>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }
}
