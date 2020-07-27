package com.cuichuang.common.api;


import android.text.TextUtils;

import com.cuichuang.common.base.cookie.CookieManger;
import com.cuichuang.common.base.gson.DoubleDefaultAdapter;
import com.cuichuang.common.base.gson.IntegerDefaultAdapter;
import com.cuichuang.common.base.gson.LongDefaultAdapter;
import com.cuichuang.common.base.gson.StringNullAdapter;
import com.cuichuang.common.base.myApplication;
import com.cuichuang.common.base.progress.ProgressResponseBody;
import com.cuichuang.common.base.view.BaseView;
import com.cuichuang.common.util.Consts;
import com.cuichuang.common.util.NetWorkUtils;
import com.cuichuang.common.util.ULog;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * File descripition:   创建Retrofit
 *
 * @author CC
 * @date 2018/6/19
 */

public class ApiRetrofit {
    private String TAG = "ApiRetrofit %s";
    private static ApiRetrofit mApiRetrofit;
    private Retrofit retrofit;
    private ApiServer apiServer;

    private Gson gson;
    private static final int DEFAULT_TIMEOUT = 15;

    private static List<Retrofit> mRetrofitList = new ArrayList<>();
    private static List<ApiRetrofit> mApiRetrofitList = new ArrayList<>();
    public static String mBaseUrl = Consts.BASE_URL;

    private static BaseView mBaseView = null;

    private static volatile Type mType = Type.BASE;

    public enum Type {
        FILE,
        BASE,
        BASE_URL,
    }

    public Type getType() {
        return mType;
    }

    public static void setType(Type type) {
        mType = type;
    }

    /**
     * 文件处理
     *
     * @param httpClientBuilder
     */
    public void initFileClient(OkHttpClient.Builder httpClientBuilder) {
        /**
         * 处理文件下载进度展示所需
         */
        httpClientBuilder.addNetworkInterceptor(new ProgressInterceptor());
    }

    /**
     * 默认所需
     *
     * @param httpClientBuilder
     */
    public void initDefaultClient(OkHttpClient.Builder httpClientBuilder) {
        /**
         * 处理一些识别识别不了 ipv6手机，如小米  实现方案  将ipv6与ipv4置换位置，首先用ipv4解析
         */
        httpClientBuilder.dns(new ApiDns());

        /**
         * 添加cookie管理
         * 方法1：第三方框架
         */
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(myApplication.getContext()));
        httpClientBuilder.cookieJar(cookieJar);

        /**
         * 添加cookie管理
         * 方法2：手动封装cookie管理
         */
//        httpClientBuilder.cookieJar(new CookieManger(App.getContext()));

        /**
         * 添加日志拦截
         */
        httpClientBuilder.addInterceptor(new JournalInterceptor());

        /**
         * 添加日志拦截  第三方框架方案
         */
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClientBuilder.addInterceptor(logInterceptor);
        /**
         * 添加请求头
         */
//        httpClientBuilder.addInterceptor(new HeadUrlInterceptor());
    }


    public ApiRetrofit() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                .cookieJar(new CookieManger(myApplication.getContext()))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重联

        switch (getType()) {
            case FILE:
                initFileClient(httpClientBuilder);
                break;
            case BASE:
            case BASE_URL:
                initDefaultClient(httpClientBuilder);
                break;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))//添加json转换框架(正常转换框架)
//                .addConverterFactory(MyGsonConverterFactory.create(buildGson()))//添加json自定义（根据需求，此种方法是拦截gson解析所做操作）
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        apiServer = retrofit.create(ApiServer.class);
        mRetrofitList.add(retrofit);
    }


    /**
     * 增加后台返回""和"null"的处理,如果后台返回格式正常
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     * 4.String=>""
     *
     * @return
     */
    public Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(String.class, new StringNullAdapter())
                    .create();
        }
        return gson;
    }

    /**
     * 默认使用方式
     *
     * @return
     */
    public static ApiRetrofit getInstance() {
        setType(Type.BASE);
        mBaseView = null;
        mBaseUrl = Consts.BASE_URL;

        return initRetrofit();
    }

    /**
     * 文件下载使用方式
     *
     * @param baseView
     * @return
     */
    public static ApiRetrofit getFileInstance(BaseView baseView) {
        setType(Type.FILE);
        mBaseView = baseView;
        mBaseUrl = Consts.BASE_URL + "file/";

        return initRetrofit();
    }

    /**
     * 动态改变baseUrl使用方式
     *
     * @param baseUrl
     * @return
     */
    public static ApiRetrofit getBaseUrlInstance(String baseUrl) {
        setType(Type.BASE_URL);
        mBaseView = null;
        if (!TextUtils.isEmpty(baseUrl)) {
            mBaseUrl = baseUrl;
        } else {
            mBaseUrl = Consts.BASE_URL;
        }
        return initRetrofit();
    }

    private static ApiRetrofit initRetrofit() {
        int mIndex = -1;
        for (int i = 0; i < mRetrofitList.size(); i++) {
            if (mBaseUrl.equals(mRetrofitList.get(i).baseUrl().toString())) {
                mIndex = i;
                break;
            }
        }

        //新的baseUrl
        if (mIndex == -1) {
            synchronized (Object.class) {
                mApiRetrofit = new ApiRetrofit();
                mApiRetrofitList.add(mApiRetrofit);
                return mApiRetrofit;
            }
        } else {
            //以前已经创建过的baseUrl
            return mApiRetrofitList.get(mIndex);
        }
    }


    public ApiServer getApiService() {
        return apiServer;
    }


    /**
     * 请求访问quest    打印日志
     * response拦截器
     */
    public class JournalInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            try {
                long startTime = System.currentTimeMillis();
                Response response = chain.proceed(request);
                if (response == null) {
                    return chain.proceed(request);
                }
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                MediaType mediaType = response.body().contentType();
                String content = response.body().string();

                ULog.w(TAG, "----------Request Start----------------");
                ULog.e(TAG, "| " + request.toString() + "===========" + request.headers().toString());
                ULog.e(content);
                ULog.w(TAG, "----------Request End:" + duration + "毫秒----------");
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
                return chain.proceed(request);
            }
        }
    }

    /**
     * 添加  请求头
     */

    public class HeadUrlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader("Content-Type", "text/html; charset=UTF-8")
//                    .addHeader("Vary", "Accept-Encoding")
//                    .addHeader("Server", "Apache")
//                    .addHeader("Pragma", "no-cache")
//                    .addHeader("Cookie", "add cookies here")
//                    .addHeader("_identity",  cookie_value)
                    .build();
            return chain.proceed(request);
        }
    }


    /**
     * 文件下载进度拦截
     */
    public class ProgressInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (mBaseView != null) {
                Response response = chain.proceed(request);
                return response.newBuilder().body(new ProgressResponseBody(response.body(),
                        new ProgressResponseBody.ProgressListener() {
                            @Override
                            public void onProgress(long totalSize, long downSize) {
                                int progress = (int) (downSize * 100 / totalSize);
                                if (mBaseView != null) {
                                    mBaseView.onProgress(progress);
                                    ULog.e("文件下载速度 === " + progress);
                                }
                            }
                        })).build();
            } else {
                return chain.proceed(request);
            }
        }
    }

    /**
     * 获取HTTP 添加公共参数的拦截器
     * 暂时支持get、head请求&Post put patch的表单数据请求
     *
     * @return
     */
    public class HttpParamsInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (request.method().equalsIgnoreCase("GET") || request.method().equalsIgnoreCase("HEAD")) {
                HttpUrl httpUrl = request.url().newBuilder()
                        .addQueryParameter("version", "1.1.0")
                        .addQueryParameter("devices", "android")
                        .build();
                request = request.newBuilder().url(httpUrl).build();
            } else {
                RequestBody originalBody = request.body();
                if (originalBody instanceof FormBody) {
                    FormBody.Builder builder = new FormBody.Builder();
                    FormBody formBody = (FormBody) originalBody;
                    for (int i = 0; i < formBody.size(); i++) {
                        builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                    FormBody newFormBody = builder
                            .addEncoded("version", "1.1.0")
                            .addEncoded("devices", "android")
                            .build();
                    if (request.method().equalsIgnoreCase("POST")) {
                        request = request.newBuilder().post(newFormBody).build();
                    } else if (request.method().equalsIgnoreCase("PATCH")) {
                        request = request.newBuilder().patch(newFormBody).build();
                    } else if (request.method().equalsIgnoreCase("PUT")) {
                        request = request.newBuilder().put(newFormBody).build();
                    }

                } else if (originalBody instanceof MultipartBody) {

                }

            }
            return chain.proceed(request);
        }
    }

    /**
     * 获得HTTP 缓存的拦截器
     *
     * @return
     */
    public class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 无网络时，始终使用本地Cache
            if (!NetWorkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetWorkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                return response.newBuilder()
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    /**
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
     */

    /**
     * 特殊返回内容  处理方案
     */
    public class MockInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Gson gson = new Gson();
            Response response = null;
            Response.Builder responseBuilder = new Response.Builder()
                    .code(200)
                    .message("")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .addHeader("content-type", "application/json");
            Request request = chain.request();
            if (request.url().toString().contains(Consts.BASE_URL)) { //拦截指定地址
                String responseString = "{\n" +
                        "\t\"success\": true,\n" +
                        "\t\"data\": [{\n" +
                        "\t\t\"id\": 6,\n" +
                        "\t\t\"type\": 2,\n" +
                        "\t\t\"station_id\": 1,\n" +
                        "\t\t\"datatime\": 1559491200000,\n" +
                        "\t\t\"factors\": [{\n" +
                        "\t\t\t\"id\": 11,\n" +
                        "\t\t\t\"history_id\": 6,\n" +
                        "\t\t\t\"station_id\": 1,\n" +
                        "\t\t\t\"factor_id\": 6,\n" +
                        "\t\t\t\"datatime\": 1559491200000,\n" +
                        "\t\t\t\"value_check\": 2.225,\n" +
                        "\t\t\t\"value_span\": 5.0,\n" +
                        "\t\t\t\"value_standard\": 4.0,\n" +
                        "\t\t\t\"error_difference\": -1.775,\n" +
                        "\t\t\t\"error_percent\": -44.38,\n" +
                        "\t\t\t\"accept\": false\n" +
                        "\t\t}, {\n" +
                        "\t\t\t\"id\": 12,\n" +
                        "\t\t\t\"history_id\": 6,\n" +
                        "\t\t\t\"station_id\": 1,\n" +
                        "\t\t\t\"factor_id\": 7,\n" +
                        "\t\t\t\"datatime\": 1559491200000,\n" +
                        "\t\t\t\"value_check\": 1.595,\n" +
                        "\t\t\t\"value_span\": 0.5,\n" +
                        "\t\t\t\"value_standard\": 1.6,\n" +
                        "\t\t\t\"error_difference\": -0.005,\n" +
                        "\t\t\t\"error_percent\": -0.31,\n" +
                        "\t\t\t\"accept\": true\n" +
                        "\t\t}, {\n" +
                        "\t\t\t\"id\": 13,\n" +
                        "\t\t\t\"history_id\": 6,\n" +
                        "\t\t\t\"station_id\": 1,\n" +
                        "\t\t\t\"factor_id\": 8,\n" +
                        "\t\t\t\"datatime\": 1559491200000,\n" +
                        "\t\t\t\"value_check\": 8.104,\n" +
                        "\t\t\t\"value_span\": 20.0,\n" +
                        "\t\t\t\"value_standard\": 8.0,\n" +
                        "\t\t\t\"error_difference\": 0.104,\n" +
                        "\t\t\t\"error_percent\": 1.3,\n" +
                        "\t\t\t\"accept\": true\n" +
                        "\t\t},null]\n" +
                        "\t}],\n" +
                        "\t\"additional_data\": {\n" +
                        "\t\t\"totalPage\": 0,\n" +
                        "\t\t\"startPage\": 1,\n" +
                        "\t\t\"limit\": 30,\n" +
                        "\t\t\"total\": 0,\n" +
                        "\t\t\"more_items_in_collection\": false\n" +
                        "\t},\n" +
                        "\t\"related_objects\": [{\n" +
                        "\t\t\"id\": 6,\n" +
                        "\t\t\"name\": \"氨氮\",\n" +
                        "\t\t\"unit\": \"mg/L\",\n" +
                        "\t\t\"db_field\": \"nh3n\",\n" +
                        "\t\t\"qa_ratio\": true\n" +
                        "\t}, {\n" +
                        "\t\t\"id\": 7,\n" +
                        "\t\t\"name\": \"总磷\",\n" +
                        "\t\t\"unit\": \"mg/L\",\n" +
                        "\t\t\"db_field\": \"tp\",\n" +
                        "\t\t\"qa_ratio\": true\n" +
                        "\t}, {\n" +
                        "\t\t\"id\": 8,\n" +
                        "\t\t\"name\": \"总氮\",\n" +
                        "\t\t\"unit\": \"mg/L\",\n" +
                        "\t\t\"db_field\": \"tn\",\n" +
                        "\t\t\"qa_ratio\": true\n" +
                        "\t}, {\n" +
                        "\t\t\"id\": 9,\n" +
                        "\t\t\"name\": \"CODMn\",\n" +
                        "\t\t\"unit\": \"mg/L\",\n" +
                        "\t\t\"db_field\": \"codmn\",\n" +
                        "\t\t\"qa_ratio\": true\n" +
                        "\t}],\n" +
                        "\t\"request_time\": \"2019-06-05T16:40:14.915+08:00\"\n" +
                        "}";
                responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()));//将数据设置到body中
                response = responseBuilder.build(); //builder模式构建response
            } else {
                response = chain.proceed(request);
            }
            return response;
        }
    }

}
